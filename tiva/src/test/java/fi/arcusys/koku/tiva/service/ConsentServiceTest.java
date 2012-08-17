package fi.arcusys.koku.tiva.service;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fi.arcusys.koku.common.service.CalendarUtil;
import fi.arcusys.koku.common.service.ConsentDAO;
import fi.arcusys.koku.common.service.datamodel.Consent;
import fi.arcusys.koku.common.soa.Organization;
import fi.arcusys.koku.tiva.soa.ActionPermittedTO;
import fi.arcusys.koku.tiva.soa.ActionRequestSummary;
import fi.arcusys.koku.tiva.soa.ActionRequestTO;
import fi.arcusys.koku.tiva.soa.ConsentApprovalStatus;
import fi.arcusys.koku.tiva.soa.ConsentCreateType;
import fi.arcusys.koku.tiva.soa.ConsentCriteria;
import fi.arcusys.koku.tiva.soa.ConsentExternalGivenTo;
import fi.arcusys.koku.tiva.soa.ConsentForReplyTO;
import fi.arcusys.koku.tiva.soa.ConsentKksExtraInfo;
import fi.arcusys.koku.tiva.soa.ConsentQuery;
import fi.arcusys.koku.tiva.soa.ConsentReceipientsType;
import fi.arcusys.koku.tiva.soa.ConsentSearchCriteria;
import fi.arcusys.koku.tiva.soa.ConsentShortSummary;
import fi.arcusys.koku.tiva.soa.ConsentSourceInfo;
import fi.arcusys.koku.tiva.soa.ConsentStatus;
import fi.arcusys.koku.tiva.soa.ConsentSummary;
import fi.arcusys.koku.tiva.soa.ConsentTO;
import fi.arcusys.koku.tiva.soa.ConsentTemplateTO;
import fi.arcusys.koku.tiva.soa.KksFormField;
import fi.arcusys.koku.tiva.soa.KksFormInstance;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Aug 23, 2011
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/test-tiva-context.xml"})
public class ConsentServiceTest {

    @Autowired
    private ConsentServiceFacade service;

    @Autowired
    private ConsentDAO consentDao;

    @Test
    public void createConsentTemplate() {
        final ConsentTemplateTO template = createTestTemplate("template");

        final Long templateId = service.createConsentTemplate(template);

        final ConsentTemplateTO templateById = service.getConsentTemplate(templateId);

        assertEquals(templateId, templateById.getConsentTemplateId());
        assertEquals(template.getTitle(), templateById.getTitle());
        assertEquals(template.getCreatorUid(), templateById.getCreatorUid());
        assertEquals(template.getDescription(), templateById.getDescription());
        assertEquals(template.getActions().size(), templateById.getActions().size());
    }

    @Test
    public void searchConsentTemplate() {
        service.createConsentTemplate(createTestTemplate("testSearch1"));
        service.createConsentTemplate(createTestTemplate("testSearch2"));

        assertEquals(1, service.searchConsentTemplates("testSearch", 1).size());
        assertEquals(2, service.searchConsentTemplates("testSearch", 2).size());
        assertEquals(1, service.searchConsentTemplates("testSearch1", 2).size());
        assertEquals(1, service.searchConsentTemplates("testSearch2", 2).size());
        assertEquals(0, service.searchConsentTemplates("testSearch12", 2).size());
    }

    @Test
    public void requestForConsentWithApprovalAndDeclining() {
        // new consent
        final Long templateId = service.createConsentTemplate(createTestTemplate("templateForApproveAndDecline"));
        final String parentForApprove = "Kalle Kuntalainen";
        final String parentForDecline = "Kirsi Kuntalainen";

        final String employeeUid = "Ville Virkamies";
        final Long consentId = service.requestForConsent(templateId, employeeUid,
                "Lassi Lapsi", Arrays.asList(parentForApprove, parentForDecline), null, null, null, Boolean.FALSE, null, null, null);

        ConsentQuery processedConcentsQuery = new ConsentQuery(1, 10);

        final List<ConsentSummary> beforeProcessed = service.getProcessedConsents(employeeUid, processedConcentsQuery);

        // KOKU-1297: Sent Consents should be visible in Loora after creation (previously not visible by design)
        assertNotNull(getById(consentId, beforeProcessed));

        // KOKU-1297: Filtered results should have the sent consents
        ConsentCriteria templateCriteria = new ConsentCriteria();
        templateCriteria.setConsentTemplateId(templateId);
        processedConcentsQuery.setCriteria(templateCriteria);
        assertEquals(1, beforeProcessed.size());
        assertNotNull(getById(consentId, beforeProcessed));

        final ConsentTO combinedBefore = service.getCombinedConsentById(consentId);
        assertEquals("New Consents should be Open", ConsentStatus.Open, combinedBefore.getStatus());
        assertEquals("New Consent's approval status should be Undecided", ConsentApprovalStatus.Undecided, combinedBefore.getApprovalStatus());

        // KOKU-1297: New consent authorization approval status should be Undecided
        assertConsentActions("After creation", combinedBefore, 3, 3, 0, 0); // 3 undecided actions

        // first parent's approval
        final List<ConsentShortSummary> consentsForApprove = service.getAssignedConsents(parentForApprove, 1, 10);
        assertNotNull(getById(consentId, consentsForApprove));
        final ConsentForReplyTO consentForApprove = service.getConsentForReply(consentId, parentForApprove);
        assertNotNull(consentForApprove);
        service.giveConsent(consentForApprove.getConsentId(), parentForApprove, getTestActionsPermitted(),
                CalendarUtil.getXmlDate(new Date()), "consent given");
        assertNull("already processed consent: ", getById(consentId, service.getAssignedConsents(parentForApprove, 1, 10)));

        assertNotNull(getById(consentId, service.getProcessedConsents(employeeUid, new ConsentQuery(1, 10))));
        final ConsentTO afterApprove = service.getConsentById(consentId, parentForApprove);
        final ConsentTO combinedAfterApprove = service.getCombinedConsentById(consentId);
        assertNotNull(combinedAfterApprove);
        assertConsentStatus("(parent) After approval", afterApprove, ConsentStatus.Valid, ConsentApprovalStatus.Approved);
        assertConsentStatus("(employee) After approval", combinedAfterApprove, ConsentStatus.PartiallyGiven, ConsentApprovalStatus.Approved);
        assertConsentActions("(parent) After approval", afterApprove, 3, 0, 1, 2); // 1 Declined, 2 Given
        assertConsentActions("(employee) After approval", combinedAfterApprove, 3, 0, 1, 2); // 1 Declined, 2 Given

        // second parent's declining
        final List<ConsentShortSummary> consentsForDecline = service.getAssignedConsents(parentForDecline, 1, 10);
        assertNotNull(getById(consentId, consentsForDecline));
        final ConsentForReplyTO consentForDecline = service.getConsentForReply(consentId, parentForDecline);
        assertNotNull(consentForDecline);
        assertNull(getById(consentId, service.getOldConsents(parentForDecline, 1, 10)));
        service.declineConsent(consentForDecline.getConsentId(), parentForDecline, "consent declined");
        assertNull("already processed consent: ", getById(consentId, service.getAssignedConsents(parentForDecline, 1, 10)));
        assertNotNull(getById(consentId, service.getOldConsents(parentForDecline, 1, 10)));
        final ConsentTO afterDecline = service.getConsentById(consentId, parentForDecline);
        final ConsentTO combinedAfterDecline = service.getCombinedConsentById(consentId);
        assertConsentStatus("(approved-parent) After decline", service.getConsentById(consentId, parentForApprove), ConsentStatus.Valid, ConsentApprovalStatus.Approved);
        assertConsentStatus("(parent) After decline", afterDecline, ConsentStatus.Declined, ConsentApprovalStatus.Declined);
        assertConsentStatus("(employee) After decline", combinedAfterDecline, ConsentStatus.Declined, ConsentApprovalStatus.Declined);
        assertConsentActions("(parent) After decline", afterDecline, 3, 0, 3, 0); // 3 Declined
        assertConsentActions("(employee) After decline", combinedAfterDecline, 3, 0, 3, 0); // 3 Declined

        // first parent's update
        final List<ConsentSummary> ownConsents = service.getOwnConsents(parentForApprove, 1, 10);
        assertNotNull(getById(consentId, ownConsents));
        final XMLGregorianCalendar currentDate = CalendarUtil.getXmlDate(new Date());
        currentDate.setYear(currentDate.getYear() + 1);
        final XMLGregorianCalendar newDate = CalendarUtil.getXmlDate(currentDate.toGregorianCalendar().getTime());
        service.updateConsent(consentId, parentForApprove, newDate, "extended consent");
        assertEquals(newDate, getById(consentId, service.getOwnConsents(parentForApprove, 1, 10)).getValidTill());
        final ConsentTO afterUpdate = service.getConsentById(consentId, parentForApprove);
        final ConsentTO combinedAfterUpdate = service.getCombinedConsentById(consentId);
        assertConsentStatus("(parent) After update", afterUpdate, ConsentStatus.Valid, ConsentApprovalStatus.Approved);
        assertConsentStatus("(employee) After update", combinedAfterUpdate, ConsentStatus.Declined, ConsentApprovalStatus.Declined);
        assertConsentActions("(parent) After update", afterUpdate, 3, 0, 1, 2); // 1 Declined, 2 Given
        assertConsentActions("(employee) After update", combinedAfterUpdate, 3, 0, 3, 0); // 3 Declined

        // first parent's revoking
        assertNotNull(getById(consentId, service.getOwnConsents(parentForApprove, 1, 10)));
        assertNull(getById(consentId, service.getOldConsents(parentForApprove, 1, 10)));
        service.revokeConsent(consentId, parentForApprove, "revoked consent");
        assertNull(getById(consentId, service.getOwnConsents(parentForApprove, 1, 10)));
        assertNotNull(getById(consentId, service.getOldConsents(parentForApprove, 1, 10)));
        final ConsentTO afterRevoke = service.getConsentById(consentId, parentForApprove);
        final ConsentTO combinedAfterRevoke = service.getCombinedConsentById(consentId);
        assertConsentStatus("(parent) After update", afterRevoke, ConsentStatus.Revoked, ConsentApprovalStatus.Declined);
        assertConsentStatus("(employee) After update", combinedAfterRevoke, ConsentStatus.Declined, ConsentApprovalStatus.Declined);
        assertConsentActions("(parent) After revoke", afterRevoke, 3, 0, 3, 0); // 3 Declined
        assertConsentActions("(employee) After revoke", combinedAfterRevoke, 3, 0, 3, 0); // 3 Declined
    }

    private void assertConsentStatus(final String message, ConsentTO consent, ConsentStatus status,
            ConsentApprovalStatus approvalStatus) {

        assertEquals(String.format("%s concent status should be %s", message, status), status, consent.getStatus());
        assertEquals(String.format("%s concent approval status should be %s", message, approvalStatus), approvalStatus, consent.getApprovalStatus());
    }

    private void assertConsentActions(final String message, ConsentTO consent, int actionCount,
            int actualUndecided, int actualDeclided, int actualGiven) {

        final List<ActionRequestSummary> actionRequests = consent.getActionRequests();

        int undecided = 0, declined = 0, given = 0;

        assertEquals(String.format("Expected %d action requests", actionCount), actionCount, actionRequests.size());

        for (ActionRequestSummary a : actionRequests) {
            switch (a.getStatus()) {
                case Undecided:
                    undecided++;
                    break;

                case Declined:
                    declined++;
                    break;

                case Given:
                    given++;
                    break;

                default:
                    break;
            }
        }

        assertEquals(String.format("%s %d actions should be undecided", message, actualUndecided), actualUndecided, undecided);
        assertEquals(String.format("%s %d actions should be declined", message, actualDeclided), actualDeclided, declined);
        assertEquals(String.format("%s %d actions should be approved", message, actualGiven), actualGiven, given);
    }

    @Test
    public void testTotals() {
        final Long templateId = service.createConsentTemplate(createTestTemplate("templateForCountingTotals"));
        final String parent = "testTotalsParent";
        final String employee = "testTotalsEmployee";

        final Long consentId = service.requestForConsent(templateId, employee,
                "Lassi Lapsi", Arrays.asList(parent), null, null, null, Boolean.FALSE, null, null, null);

        ConsentCriteria totalProcessedCriteria = new ConsentCriteria();
        totalProcessedCriteria.setConsentTemplateId(templateId);

        assertEquals(1, service.getTotalAssignedConsents(parent));
        assertEquals(0, service.getTotalOwnConsents(parent));
        assertEquals(1, service.getTotalProcessedConsents(employee, null)); // KOKU-1297: Sent Consents should be visible in Loora after creation
        assertEquals(1, service.getTotalProcessedConsents(employee, totalProcessedCriteria));  // KOKU-1297

        service.giveConsent(consentId, parent, Collections.<ActionPermittedTO>emptyList(), null, "");

        assertEquals(0, service.getTotalAssignedConsents(parent));
        assertEquals(1, service.getTotalOwnConsents(parent));
        assertEquals(1, service.getTotalProcessedConsents(employee, null));
        assertEquals(1, service.getTotalProcessedConsents(employee, totalProcessedCriteria));
    }

    @Test
    public void searchByTemplateAndUserUid() {
        // new consent
        final Long templateId = service.createConsentTemplate(createTestTemplate("templateForSearchTesting"));
        final String parent1 = "searchByTemplateParent1";
        final String parent2 = "searchByTemplateParent2";

        final String employeeUid = "Ville Virkamies";
        final Long consentId = service.requestForConsent(templateId, employeeUid,
                "Lassi Lapsi", Arrays.asList(parent1, parent2), null, null, null, Boolean.FALSE, null, null, null);

        // KOKU-1297: Sent Consents should be visible in Loora
        final ConsentQuery query = new ConsentQuery(1, 25);
        assertNotNull(getById(consentId, service.getProcessedConsents(employeeUid, query)));

        final ActionPermittedTO actionPermittedTO = new ActionPermittedTO();
        actionPermittedTO.setActionRequestNumber(1);
        actionPermittedTO.setPermitted(true);

        service.giveConsent(consentId, parent1, Collections.singletonList(actionPermittedTO), null, "");
        assertNotNull(getById(consentId, service.getProcessedConsents(employeeUid, query)));

        query.setCriteria(new ConsentCriteria());
        // filter by templateId
        query.getCriteria().setConsentTemplateId(0L);
        assertNull("not found by wrong template id: ", getById(consentId, service.getProcessedConsents(employeeUid, query)));
        query.getCriteria().setConsentTemplateId(templateId);
        assertNotNull("found by template id: ", getById(consentId, service.getProcessedConsents(employeeUid, query)));
        // filter by userid
        query.getCriteria().setReceipientUid(parent2);
        assertNull("not found by wrong receipient uid: ", getById(consentId, service.getProcessedConsents(employeeUid, query)));
        query.getCriteria().setReceipientUid(parent1);
        assertNotNull("found by receipient uid: ", getById(consentId, service.getProcessedConsents(employeeUid, query)));
    }

    @Test
    public void createConsentOnBehalf() {
        final Long templateId = service.createConsentTemplate(createTestTemplate("templateForCreationOnBehalf"));
        final String parent1 = "parent1";
        final List<String> recipients = Arrays.asList(parent1);

        final String employeeUid = "Ville Virkamies";

        final ConsentSourceInfo sourceInfo = new ConsentSourceInfo();
        sourceInfo.setRepository("testRepository");
        sourceInfo.setAttachmentUrl("http://attachment.org");
        final Long consentId = service.writeConsentOnBehalf(templateId, employeeUid,
                ConsentCreateType.PaperBased, "Lassi Lapsi", recipients, null, null, getTestActionsPermitted(),
                sourceInfo, "given on behalf", null, null);

        final ConsentQuery query = new ConsentQuery(1, 25);
        final ConsentSummary autoApproved = getById(consentId, service.getProcessedConsents(employeeUid, query));
        assertNotNull("Already processed consent for employee: ", autoApproved);
        assertEquals(ConsentStatus.Valid, autoApproved.getStatus());

        assertNotNull("Already processed consent for parent1: ", getById(consentId, service.getOwnConsents(parent1, 1, 10)));

        service.revokeConsent(consentId, parent1, "no comments");
        assertEquals(ConsentStatus.Revoked, getById(consentId, service.getProcessedConsents(employeeUid, query)).getStatus());
    }

    @Test
    public void autoCancelConsent() {
        // request for consent
        final Long templateId = service.createConsentTemplate(createTestTemplate("templateForAutocancel"));
        final String parent = "testParentAutoCancel";
        final String parent2 = "testParentAutoCancel2";
        final String employee = "testEmployee";
        final List<String> parents = Arrays.asList(parent, parent2);

        final Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);

        final Long consentId = service.requestForConsent(templateId, employee,
                "Lassi Lapsi", parents, ConsentReceipientsType.AnyParent, CalendarUtil.getXmlDate(calendar.getTime()), null, Boolean.FALSE, null, null, null);

        final Long consentBothParentsId = service.requestForConsent(templateId, employee,
                "Lassi Lapsi", parents, ConsentReceipientsType.BothParents, CalendarUtil.getXmlDate(calendar.getTime()), null, Boolean.FALSE, null, null, null);
        // auto cancel for skip
        service.cancellationOfOutdatedConsents();
        // check status
        assertEquals(ConsentStatus.Open, service.getCombinedConsentById(consentId).getStatus());
        assertEquals(ConsentStatus.Open, service.getCombinedConsentById(consentBothParentsId).getStatus());

        // update date
        updateReplyTillEqToday(consentId);
        updateReplyTillEqToday(consentBothParentsId);
        service.giveConsent(consentBothParentsId, parent, getTestActionsPermitted(), null, "valid from " + parent);

        // auto cancel for cancel
        assertEquals("Auto-cancelled both consents: ", 2, service.cancellationOfOutdatedConsents());

        // check status
        assertEquals(ConsentStatus.Declined, service.getCombinedConsentById(consentId).getStatus());
        assertEquals(ConsentStatus.Declined, service.getCombinedConsentById(consentBothParentsId).getStatus());

        assertEquals("Consent given: ", ConsentStatus.Valid, service.getConsentById(consentBothParentsId, parent).getStatus());
        assertEquals("Auto-declined consent: ", ConsentStatus.Declined, service.getConsentById(consentBothParentsId, parent2).getStatus());
    }

    private void updateReplyTillEqToday(final Long consentId) {
        final Consent consentDO = consentDao.getById(consentId);
        consentDO.setReplyTill(CalendarUtil.getXmlDate(new Date()).toGregorianCalendar().getTime());
        consentDao.update(consentDO);
    }

    @Test
    public void tivaToKksConsents() {
        // new consent
        final Long templateId = service.createConsentTemplate(createTestTemplate("kks"));
        final String parent1 = "kksParent1";
        final String parent2 = "kksParent2";
        final String targetPersonUid = "Lassi Lapsi";

        final String employeeUid = "Ville Virkamies";

        final ConsentKksExtraInfo extraInfo = new ConsentKksExtraInfo();
        final String informationTargetId = "info1";
        final ConsentExternalGivenTo givenToParty = new ConsentExternalGivenTo();
        givenToParty.setPartyId("partyId");
        givenToParty.setPartyName("partyName");
        final List<ConsentExternalGivenTo> givenTo = Collections.singletonList(givenToParty);
        extraInfo.setInformationTargetId(informationTargetId);
        extraInfo.setGivenTo(givenTo);

        final Long consentId = service.requestForConsent(templateId, employeeUid,
                targetPersonUid, Arrays.asList(parent1, parent2), null, null, null, Boolean.FALSE, extraInfo, null, null);

        final ConsentSearchCriteria query = new ConsentSearchCriteria();
        query.setGivenTo(Collections.singletonList("partyId"));
        query.setInformationTargetId(informationTargetId);
        query.setTargetPerson(targetPersonUid);
        query.setTemplateNamePrefix("kks");

        final ConsentTO requestedConsent = getById(consentId, service.searchConsents(query));
        assertNotNull(requestedConsent);
        assertEquals(ConsentStatus.Open, requestedConsent.getStatus());

        final ActionPermittedTO actionPermittedTO = new ActionPermittedTO();
        actionPermittedTO.setActionRequestNumber(1);
        actionPermittedTO.setPermitted(true);

        service.giveConsent(consentId, parent1, Collections.singletonList(actionPermittedTO), null, "");
        final ConsentTO partiallyGivenConsent = getById(consentId, service.searchConsents(query));
        assertNotNull(partiallyGivenConsent);
        assertEquals(ConsentStatus.PartiallyGiven, partiallyGivenConsent.getStatus());

        service.giveConsent(consentId, parent2, Collections.singletonList(actionPermittedTO), null, "");

        final ConsentTO validConsent = getById(consentId, service.searchConsents(query));
        assertNotNull(validConsent);
        assertEquals(ConsentStatus.Valid, validConsent.getStatus());
    }

    @Test
    public void tivaToKksConsentsNew() {
        // new consent
        final Long templateId = service.createConsentTemplate(createTestTemplate("kks-new"));
        final String parent1 = "kksParent1";
        final String parent2 = "kksParent2";
        final String targetPersonUid = "Lassi Lapsi";

        final String employeeUid = "Ville Virkamies";

        final KksFormInstance formInstance = new KksFormInstance();
        formInstance.setInstanceId("1");
        formInstance.setInstanceName("Form 1");

        final List<KksFormField> fields = new ArrayList<KksFormField>();
        for (int i = 1; i < 5; i++) {
            final KksFormField field = new KksFormField();
            field.setFieldId(Integer.toString(i));
            field.setFieldName("Field "+Integer.toString(i));
            fields.add(field);
        }
        formInstance.setFields(fields);

        final List<Organization> givenTo = new ArrayList<Organization>();
        final List<String> givenToIds = new ArrayList<String>();
        for (int i = 1; i < 4; i++) {
            final Organization organization = new Organization();
            organization.setOrganizationId(Integer.toString(i));
            organization.setOrganizationName("Org "+Integer.toString(i));
            givenTo.add(organization);
            givenToIds.add(organization.getOrganizationId());
        }

        final Long consentId = service.requestForConsent(templateId, employeeUid,
                targetPersonUid, Arrays.asList(parent1, parent2), null, null, null, Boolean.FALSE, null, formInstance, givenTo);

        final ConsentSearchCriteria query = new ConsentSearchCriteria();
        query.setGivenTo(givenToIds);
        query.setFormInstanceId(formInstance.getInstanceId());
        query.setTargetPerson(targetPersonUid);
        query.setTemplateNamePrefix("kks");

        final ConsentTO requestedConsent = getById(consentId, service.searchConsents(query));
        assertNotNull(requestedConsent);
        assertEquals(ConsentStatus.Open, requestedConsent.getStatus());

        final ActionPermittedTO actionPermittedTO = new ActionPermittedTO();
        actionPermittedTO.setActionRequestNumber(1);
        actionPermittedTO.setPermitted(true);

        service.giveConsent(consentId, parent1, Collections.singletonList(actionPermittedTO), null, "");
        final ConsentTO partiallyGivenConsent = getById(consentId, service.searchConsents(query));
        assertNotNull(partiallyGivenConsent);
        assertEquals(ConsentStatus.PartiallyGiven, partiallyGivenConsent.getStatus());

        service.giveConsent(consentId, parent2, Collections.singletonList(actionPermittedTO), null, "");

        final ConsentTO validConsent = getById(consentId, service.searchConsents(query));
        assertNotNull(validConsent);
        assertEquals(ConsentStatus.Valid, validConsent.getStatus());
    }

    private List<ActionPermittedTO> getTestActionsPermitted() {
        final List<ActionPermittedTO> actionPermits = new ArrayList<ActionPermittedTO>();
        for (int i = 1; i <= 2; i++) {
            final ActionPermittedTO actionPermittedTO = new ActionPermittedTO();
            actionPermittedTO.setActionRequestNumber(i);
            actionPermittedTO.setPermitted(true);
            actionPermits.add(actionPermittedTO);
        }
        return actionPermits;
    }

    /**
     * @param consentId
     * @param consentsForApprove
     * @return
     */
    private <V extends ConsentShortSummary> V getById(final Long objectId, final List<V> collection) {
        for (final V member : collection) {
            if (member.getConsentId().equals(objectId)) {
                return member;
            }
        }
        return null;
    }

    private ConsentTemplateTO createTestTemplate(final String title) {
        final ConsentTemplateTO template = new ConsentTemplateTO();
        template.setTitle(title);
        template.setCode(title);
        template.setCreatorUid("Ville Virkamies");
        template.setDescription("test template description");
        final List<ActionRequestTO> actions = new ArrayList<ActionRequestTO>();
        for (int i = 1; i <= 3; i++) {
            final ActionRequestTO actionRequest = new ActionRequestTO();
            actionRequest.setNumber(i);
            actionRequest.setName("action " + i);
            actionRequest.setDescription("description " + i);
            actions.add(actionRequest);
        }
        template.setActions(actions);
        return template;
    }
}

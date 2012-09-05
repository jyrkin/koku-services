package fi.arcusys.koku.tiva.soa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;

import fi.arcusys.koku.common.soa.Organization;
import fi.arcusys.koku.common.soa.UsersAndGroupsService;
import fi.arcusys.koku.tiva.service.ConsentServiceFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation of TIVA-to-KKS interface for accessing Consents from KKS component.
 *
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Oct 3, 2011
 */
@Stateless
@WebService(serviceName = "KokuTivaToKksService", portName = "KokuTivaToKksServicePort",
        endpointInterface = "fi.arcusys.koku.tiva.soa.KokuTivaToKksService",
        targetNamespace = "http://services.koku.fi/entity/tiva/v1")
public class KokuTivaToKksServiceImpl implements KokuTivaToKksService {

    private static final Logger logger = LoggerFactory.getLogger(KokuTivaToKksServiceImpl.class);

    @EJB
    private ConsentServiceFacade consentServiceFacade;

    @EJB
    private UsersAndGroupsService usersService;

    /**
     * @param prefix
     * @param limit
     * @return
     */
    @Override
    public List<ConsentTemplateShort> queryConsentTemplates(String prefix, Integer limit) {
        final List<ConsentTemplateShort> result = new ArrayList<ConsentTemplateShort>();
        for (final ConsentTemplateTO templateTO : consentServiceFacade.getConsentTemplates(prefix, limit)) {
            final ConsentTemplateShort templateShort = new ConsentTemplateShort();
            templateShort.setConsentTemplateId(templateTO.getConsentTemplateId());
            templateShort.setTemplateName(templateTO.getTitle());
            result.add(templateShort);
        }
        return result;
    }

    /**
     * @param consent
     */
    @Override
    public void createConsent(ConsentExternal consent) {
        final List<String> receipients = new ArrayList<String>();
        for (final String hetu : consent.getConsentProviders()) {
            receipients.add(usersService.getUserUidByKunpoSsn(hetu));
        }

        final ConsentKksExtraInfo extraInfo = new ConsentKksExtraInfo();
        extraInfo.setInformationTargetId(consent.getInformationTargetId());
        extraInfo.setGivenTo(consent.getGivenTo());
        extraInfo.setMetaInfo(consent.getMetaInfo());

        List<Organization> givenTo = new ArrayList<Organization>();
        for (KksOrganization kksOrg : consent.getKksGivenTo()) {
            final Organization org = new Organization();
            org.setOrganizationId(kksOrg.getOrganizationId());
            org.setOrganizationName(kksOrg.getOrganizationName());
            givenTo.add(org);
        }

        consentServiceFacade.requestForConsent(
                consent.getTemplate().getConsentTemplateId(),
                usersService.getUserUidByEmployeeSsn(consent.getConsentRequestor()),
                usersService.getUserUidByKunpoSsn(consent.getTargetPerson()),
                receipients, ConsentReceipientsType.BothParents,
                null, consent.getValidTill(), Boolean.TRUE, extraInfo, consent.getKksFormInstance(), givenTo);
    }

    /**
     * @param query
     * @return
     */
    @Override
    public List<ConsentExternal> queryConsents(ConsentSearchCriteria query) {
        final List<ConsentExternal> result = new ArrayList<ConsentExternal>();
        if (query.getTargetPerson() != null) {
            query.setTargetPerson(usersService.getUserUidByKunpoSsn(query.getTargetPerson()));
        }

        final Map<String, String> ssnCache = new HashMap<String, String>();
        final List<ConsentTO> consents = consentServiceFacade.searchConsents(query);

        for (final ConsentTO consent : consents) {
            final ConsentExternal consentExternal = new ConsentExternal();
            consentExternal.setConsentId(consent.getConsentId());

            final List<String> consentProviders = new ArrayList<String>();
            for (final String userUid : consent.getReceipients()) {
                consentProviders.add(getSsnByLdapNameCached(ssnCache, userUid));
            }

            consentExternal.setConsentProviders(consentProviders);
            consentExternal.setConsentRequestor(getSsnByLdapNameCached(ssnCache, consent.getRequestor()));
            consentExternal.setDescription(consent.getTemplateDescription());
            consentExternal.setGivenAt(consent.getGivenAt());
            consentExternal.setGivenTo(consent.getGivenToParties());
            consentExternal.setInformationTargetId(consent.getInformationTargetId());
            consentExternal.setMetaInfo(consent.getMetaInfo());
            consentExternal.setStatus(consent.getStatus());
            consentExternal.setTargetPerson(getSsnByLdapNameCached(ssnCache, consent.getTargetPersonUid()));
            final ConsentTemplateShort template = new ConsentTemplateShort();
            template.setConsentTemplateId(consent.getTemplateId());
            template.setTemplateName(consent.getTemplateName());
            consentExternal.setTemplate(template);
            consentExternal.setValidTill(consent.getValidTill());
            consentExternal.setKksFormInstance(consent.getKksFormInstance());

            List<KksOrganization> givenTo = new ArrayList<KksOrganization>();

            if (consent.getKksGivenTo() != null) {
                for (Organization org : consent.getKksGivenTo()) {
                    final KksOrganization kksOrg = new KksOrganization();
                    kksOrg.setOrganizationId(org.getOrganizationId());
                    kksOrg.setOrganizationName(org.getOrganizationName());
                    givenTo.add(kksOrg);
                }
            }

            consentExternal.setKksGivenTo(givenTo);
            result.add(consentExternal);
        }

        return result;
    }

    /**
     * @param cache
     * @param userName
     * @return
     */
    private String getSsnByLdapNameCached(Map<String, String> cache, String userName) {
        String ret = cache.get(userName);

        if (ret == null) {
            ret = usersService.getSsnByLdapName(userName);
            cache.put(userName, ret);
        }

        return ret;
    }

}

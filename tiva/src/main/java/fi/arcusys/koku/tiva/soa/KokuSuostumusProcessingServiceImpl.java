package fi.arcusys.koku.tiva.soa;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.jws.WebService;
import javax.xml.datatype.XMLGregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fi.arcusys.koku.tiva.service.ConsentServiceFacade;

/**
 * Implementation of TIVA-Suostumus-processing operations, called from the TIVA-Suostumus Intalio process.
 * 
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Aug 9, 2011
 */
@Stateless
@WebService(serviceName = "KokuSuostumusProcessingService", portName = "KokuSuostumusProcessingServicePort", 
        endpointInterface = "fi.arcusys.koku.tiva.soa.KokuSuostumusProcessingService",
        targetNamespace = "http://soa.tiva.koku.arcusys.fi/")
@Interceptors(KokuSuostumusInterceptor.class)
public class KokuSuostumusProcessingServiceImpl implements KokuSuostumusProcessingService {

    private static final Logger logger = LoggerFactory.getLogger(KokuSuostumusProcessingServiceImpl.class);
    
    @EJB
    private ConsentServiceFacade serviceFacade;
    
    /**
     * @param consentTemplate
     * @return
     */
    @Override
    public Long createConsentTemplate(ConsentTemplateTO consentTemplate) {
        return serviceFacade.createConsentTemplate(consentTemplate);
    }

    /**
     * @param searchString
     * @param limit
     * @return
     */
    @Override
    public List<ConsentTemplateTO> getConsentTemplates(String searchString, int limit) {
        return serviceFacade.getConsentTemplates(searchString, limit);
    }

    /**
     * @param consentTemplateId
     * @param senderUid
     * @param receivers
     * @return
     */
    @Override
    public Long requestForConsent(long consentTemplateId, String senderUid, final String targetPersonUid,
            List<String> receivers, final ConsentReceipientsType type, final XMLGregorianCalendar replyTillDate, final XMLGregorianCalendar endDate, final Boolean isMandatory) {
        return serviceFacade.requestForConsent(consentTemplateId, senderUid, targetPersonUid, receivers, type, replyTillDate, endDate, isMandatory, null);
    }

    /**
     * @param consentId
     * @param replierUid
     * @param endDate
     * @param comment
     */
    @Override
    public void giveConsent(long consentId, String replierUid, 
            final List<ActionPermittedTO> actions,
            XMLGregorianCalendar endDate,
            String comment) {        
        serviceFacade.giveConsent(consentId, replierUid, actions, endDate, comment);
    }

    /**
     * @param consentId
     * @param replierUid
     * @param comment
     */
    @Override
    public void declineConsent(long consentId, String replierUid, String comment) {
        serviceFacade.declineConsent(consentId, replierUid, comment);
    }

    /**
     * @param consentId
     * @param replierUid
     * @param endDate
     * @param comment
     */
    @Override
    public void updateConsent(long consentId, String replierUid, XMLGregorianCalendar endDate,
            String comment) {
        serviceFacade.updateConsent(consentId, replierUid, endDate, comment);
    }

    /**
     * @param consentId
     * @param replierUid
     * @param comment
     */
    @Override
    public void revokeConsent(long consentId, String replierUid, String comment) {
        serviceFacade.revokeConsent(consentId, replierUid, comment);
    }

    /**
     * @param consentId
     * @param userUid
     * @return
     */
    @Override
    public ConsentForReplyTO getConsentForReply(long consentId, String userUid) {
        return serviceFacade.getConsentForReply(consentId, userUid);
    }

    /**
     * @param consentTemplateId
     * @param senderUid
     * @param consentType
     * @param targetPersonUid
     * @param receivers
     * @return
     */
    @Override
    public Long writeConsentOnBehalf(long consentTemplateId, String senderUid,
            ConsentCreateType consentType, String targetPersonUid, List<String> receivers, final XMLGregorianCalendar endDate,
            final XMLGregorianCalendar givenDate,
            final List<ActionPermittedTO> actions, final ConsentSourceInfo sourceInfo, final String comment) {
        return serviceFacade.writeConsentOnBehalf(consentTemplateId, senderUid, consentType, targetPersonUid, receivers, endDate, givenDate, actions, sourceInfo, comment);
    }

    /**
     * @param consentTemplateId
     * @return
     */
    @Override
    public ConsentTemplateTO getConsentTemplateById(long consentTemplateId) {
        return serviceFacade.getConsentTemplate(consentTemplateId);
    }

}

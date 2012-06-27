package fi.arcusys.koku.common.external;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fi.arcusys.koku.common.service.datamodel.User;
import fi.arcusys.koku.common.soa.UserInfo;
import fi.koku.services.entity.customercommunication.v1.CustomerCommunicationServiceFactory;
import fi.koku.settings.KoKuPropertiesUtil;
import fi.tampere.contract.municipalityportal.ccs.CustomerCommunicationServicePortType;
import fi.tampere.schema.municipalityportal.ccs.ObjectFactory;
import fi.tampere.schema.municipalityportal.ccs.SendEmailMessageResponseType;
import fi.tampere.schema.municipalityportal.ccs.SendEmailMessageType;

/**
 * DAO implementation for sending email notifications to citizens through Gofore's provided email service.
 *
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Jan 2, 2012
 */
@Stateless(mappedName = "TampereEmailServiceDAO")
public class TampereEmailServiceDAOImpl implements EmailServiceDAO {

    private static final Logger logger = LoggerFactory.getLogger(TampereEmailServiceDAOImpl.class);

    @EJB
    CustomerServiceDAO customerDao;

    private CustomerCommunicationServicePortType communicationService;
    private final ObjectFactory objectFactory = new ObjectFactory();

    private String communicationServiceUserUid;
    private String communicationServiceUserPwd;
    private String communicationServiceEndpoint = KoKuPropertiesUtil.get("customercommunication.service.endpointaddress.full.url");

    @PostConstruct
    public void init() {
        try {
            final InitialContext ctx = new InitialContext();
            communicationServiceEndpoint = (String) ctx.lookup("koku/urls/customercommunicationservice-baseurl");
            logger.debug("Overwrite communicationServiceEndpoint with " + communicationServiceEndpoint);
        } catch (NamingException e) {
            logger.error(null, e);
        }
        try {
            CustomerCommunicationServiceFactory communicationServiceFactory = new CustomerCommunicationServiceFactory(
                    communicationServiceUserUid, communicationServiceUserPwd, communicationServiceEndpoint);
            communicationService = communicationServiceFactory.getCustomerCommunicationService();
        } catch(Exception re) {
            logger.error(null, re);
        }
    }

    /**
     * @param toUser
     * @param subject
     * @param content
     */
    @Override
    public boolean sendMessage(final String receiverSSN, final String subject, final String content) {
        UserInfo info = customerDao.getKunpoUserInfoBySsn(receiverSSN);

        if (info == null)
            info = customerDao.getKunpoUserInfoBySsn(receiverSSN);

        if (info == null) {
            logger.info("No citizen user data found by SSN '"+receiverSSN+"'");
            return false;
        }

        try {
            final SendEmailMessageType createSendEmailMessageType = objectFactory.createSendEmailMessageType();
            createSendEmailMessageType.setSsn(receiverSSN);
            createSendEmailMessageType.setSubject(subject);
            createSendEmailMessageType.setContent(content);
            final SendEmailMessageResponseType sendingResult = communicationService.sendEmailMessage(createSendEmailMessageType);
            if (sendingResult == SendEmailMessageResponseType.EMAIL_SENT) {
                logger.debug("EMail delivered successully to user " + receiverSSN);
                return true;
            } else {
                logger.info("Failed to send email to user " + receiverSSN + ". Return code from service: " + sendingResult);
                return false;
            }
        } catch (Exception e) {
            logger.info("Failed to send email to user " + receiverSSN + ": " + e.getMessage());
            return false;
        }
    }

}

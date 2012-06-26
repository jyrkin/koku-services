/**
 *
 */
package fi.arcusys.koku.common.service.impl;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fi.arcusys.koku.common.external.CustomerServiceDAO;
import fi.arcusys.koku.common.external.EmailServiceDAO;
import fi.arcusys.koku.common.external.SMSServiceDAO;
import fi.arcusys.koku.common.service.GenericNotificationService;
import fi.arcusys.koku.common.service.datamodel.User;
import fi.arcusys.koku.common.soa.UserInfo;
import fi.koku.settings.KoKuPropertiesUtil;

/**
 * Implementation of generic notification service
 *
 * Takes care of sending messages via SMS, email, or any other methods
 * that are not covered by the internal messaging system
 *
 * @author Mikhail Kapitonov (mikhail.kapitonov@arcusys.fi)
 * @see    GenericNotificationService
 * Jun 26, 2012
 */
@Stateless
public class GenericNotificationServiceImpl implements GenericNotificationService {
    private final static Logger logger = LoggerFactory.getLogger(GenericNotificationServiceImpl.class);

    @EJB
    private CustomerServiceDAO customerDao;

    private EmailServiceDAO emailDao;
    private SMSServiceDAO smsDao;

    private static final String EMAIL_SERVICE_KEY = "customercommunication.service.email.jndi";
    private static final String SMS_SERVICE_KEY = "customercommunication.service.sms.jndi";

    private static final String EMAIL_SERVICE_DUMMY = "arcusys-koku-0.1-SNAPSHOT/DummyEmailServiceDAOImpl/local";
    private static final String SMS_SERVICE_DUMMY = "arcusys-koku-0.1-SNAPSHOT/DummySMSServiceDAOImpl/local";

    /**
     * Returns bean or null if not found
     *
     * @return Bean from JNDI
     */
    protected Object safeContextLookup(final Context ctx, final String name) {
        try {
            return ctx.lookup(name);
        } catch (NamingException ignored) {
            return null;
        }
    }

    /**
     * Returns configured EmailServiceDAO
     *
     * @return EmailServiceDAO
     */
    protected EmailServiceDAO getEmailDAO() {
        final String emailName = KoKuPropertiesUtil.get(EMAIL_SERVICE_KEY);
        EmailServiceDAO dao = null;
        Context ctx = null;

        try {
            ctx = new InitialContext();

        } catch (NamingException e) {
            logger.warn("Could not create InitialContext");

        }

        if (ctx != null) {
            logger.info("Using EmailServiceDAO binding: " + emailName);

            if (emailName != null)
                dao = (EmailServiceDAO) safeContextLookup(ctx, emailName);

            if (dao == null) {
                logger.warn("Could not get EmailServiceDAO from JNDI or no binding found in koku-setings.properties ("+EMAIL_SERVICE_KEY+")");
                logger.info("Using dummy EmailServiceDAO implementation (from JNDI)");
                dao = (EmailServiceDAO) safeContextLookup(ctx, EMAIL_SERVICE_DUMMY);
            }
        }

        return dao;
    }

    /**
     * Returns configured SMSServiceDAO
     *
     * @return SMSServiceDAO
     */
    protected SMSServiceDAO getSMSDAO() {
        final String smsName = KoKuPropertiesUtil.get(SMS_SERVICE_KEY);
        SMSServiceDAO dao = null;
        Context ctx = null;

        try {
            ctx = new InitialContext();

        } catch (NamingException e) {
            logger.warn("Could not create InitialContext");

        }

        if (ctx != null) {
            logger.info("Using SMSServiceDAO binding: " + smsName);

            if (smsName != null)
                dao = (SMSServiceDAO) safeContextLookup(ctx, smsName);

            if (dao == null) {
                logger.warn("Could not get SMSServiceDAO from JNDI or no binding found in koku-setings.properties ("+SMS_SERVICE_KEY+")");
                logger.info("Using dummy SMSServiceDAO implementation (from JNDI)");
                dao = (SMSServiceDAO) safeContextLookup(ctx, SMS_SERVICE_DUMMY);
            }
        }

        return dao;
    }

    /**
     * Initializes bean
     */
    @PostConstruct
    public void init() {
        emailDao = getEmailDAO();
        smsDao = getSMSDAO();
    }

    /**
     * Sends the message using all configured means of delivery
     *
     * @param toUser     Receiver of the message
     * @param subject    Message subject
     * @param content    Message body
     * @return           True if all of the notification methods succeed
     */
    @Override
    public boolean sendMessage(User toUser, String subject, String content) {
        boolean delivered = true;

        UserInfo info = customerDao.getUserInfo(toUser);

        final String email = info.getEmail();
        final String phoneNumber = info.getPhoneNumber();

        if (emailDao != null && email != null && email.trim().length() > 0)
            if (!emailDao.sendMessage(toUser, subject, content))
                delivered = false;

        if (smsDao != null && phoneNumber != null && phoneNumber.trim().length() > 0)
            if (!smsDao.sendMessage(toUser, subject, content))
                delivered = false;

        return delivered;
    }

}

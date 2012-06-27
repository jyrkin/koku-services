package fi.arcusys.koku.common.external;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fi.arcusys.koku.common.service.datamodel.User;
import fi.arcusys.koku.common.soa.UserInfo;

/**
 * Dummy email DAO implementation.
 *
 * @author Mikhail Kapitonov (mikhail.kapitonov@arcusys.fi)
 * Jun 25, 2012
 */
@Stateless(mappedName = "DummyEmailServiceDAO")
public class DummyEmailServiceDAOImpl implements EmailServiceDAO {

    private static final String emailProperty = "mail";

    private static final Logger logger = LoggerFactory.getLogger(DummyEmailServiceDAOImpl.class);

    @EJB
    private LdapDAO ldapDao;

    @Override
    public boolean sendMessage(final String receiverSSN, final String subject, final String content) {
        if (receiverSSN == null)
            return false;

        String email = ldapDao.getKunpoUserPropertyBySsn(receiverSSN, emailProperty);

        if (email == null)
            email = ldapDao.getLooraUserPropertyBySsn(receiverSSN, emailProperty);

        if (email == null) {
            logger.info("No email data found for SSN '"+receiverSSN+"'");
            return false;
        }

        logger.info("sendMessage email = '"+email+"' ssn = '"+receiverSSN+"' subject = '"+subject+"'");
        return true;
    }

}

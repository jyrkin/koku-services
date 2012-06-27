package fi.arcusys.koku.common.external;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fi.arcusys.koku.common.service.datamodel.User;
import fi.arcusys.koku.common.soa.UserInfo;

/**
 * Dummy SMS DAO implementation.
 *
 * @author Mikhail Kapitonov (mikhail.kapitonov@arcusys.fi)
 * Jun 25, 2012
 */
@Stateless(mappedName = "DummySMSServiceDAO")
public class DummySMSServiceDAOImpl implements SMSServiceDAO {

    private static final String phoneProperty = "telephoneNumber";

    private static final Logger logger = LoggerFactory.getLogger(DummySMSServiceDAOImpl.class);

    @EJB
    private LdapDAO ldapDao;

    @Override
    public boolean sendMessage(final String receiverSSN, final String subject, final String content) {
        if (receiverSSN == null)
            return false;

        String phoneNumber = ldapDao.getKunpoUserPropertyBySsn(receiverSSN, phoneProperty);

        if (phoneNumber == null)
            phoneNumber = ldapDao.getLooraUserPropertyBySsn(receiverSSN, phoneProperty);

        if (phoneNumber == null) {
            logger.info("No phone number data found for SSN '"+receiverSSN+"'");
            return false;
        }

        logger.info("sendMessage phone = '"+phoneNumber+"' ssn = '"+receiverSSN+"' subject = '"+subject+"'");
        return true;
    }

}

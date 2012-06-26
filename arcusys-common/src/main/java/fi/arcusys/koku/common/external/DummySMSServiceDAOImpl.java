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

    private static final Logger logger = LoggerFactory.getLogger(DummySMSServiceDAOImpl.class);

    @EJB
    private CustomerServiceDAO customerDao;

    @Override
    public boolean sendMessage(User toUser, String subject, String content) {
        UserInfo info = customerDao.getUserInfo(toUser);

        final String phoneNumber = info.getPhoneNumber();

        logger.info("sendMessage phone = '"+phoneNumber+"' user = '"+toUser.getUid()+"' subject = '"+subject+"'");
        return true;
    }

}

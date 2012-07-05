package fi.arcusys.koku.common.external;

import fi.arcusys.koku.common.service.datamodel.User;

/**
 * DAO interface to SMS service.
 *
 * @author Mikhail Kpaitonov (mikhail.kapitonov@arcusys.fi)
 * Jun 25, 2012
 */
public interface SMSServiceDAO {

    /**
     * Sends the message using SMS
     *
     * @param receiverSSN    Receiver of the message (SSN)
     * @param subject        Message subject
     * @param content        Message body
     * @return               True if the sending succeeds
     */
    boolean sendMessage(String receiverSSN, String subject, String content);
}
package fi.arcusys.koku.common.external;

import fi.arcusys.koku.common.service.GenericNotificationService;
import fi.arcusys.koku.common.service.datamodel.User;

/**
 * DAO interface to email service.
 *
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Jan 2, 2012
 */
public interface EmailServiceDAO {

    /**
     * Sends the message using email
     *
     * @param toUser     Receiver of the message
     * @param subject    Message subject
     * @param content    Message body
     * @return           True if the sending succeeds
     */
    boolean sendMessage(User toUser, String subject, String content);
}

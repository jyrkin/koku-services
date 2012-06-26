/**
 *
 */
package fi.arcusys.koku.common.service;

import fi.arcusys.koku.common.service.datamodel.User;

/**
 * Interface to generic notification service
 *
 * Takes care of sending messages via SMS, email, or any other methods
 * that are not covered by the internal messaging system
 *
 * @author Mikhail Kapitonov (mikhail.kapitonov@arcusys.fi)
 * Jun 26, 2012
 */
public interface GenericNotificationService {

    /**
     * Sends the message using all configured means of delivery
     *
     * @param toUser     Receiver of the message
     * @param subject    Message subject
     * @param content    Message body
     * @return           True if all of the notification methods succeed
     */
    boolean sendMessage(User toUser, String subject, String content);
}

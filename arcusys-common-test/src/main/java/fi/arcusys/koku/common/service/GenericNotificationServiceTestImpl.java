/**
 *
 */
package fi.arcusys.koku.common.service;

import javax.ejb.EJB;

import fi.arcusys.koku.common.external.DummyEmailServiceDAOImpl;
import fi.arcusys.koku.common.external.DummySMSServiceDAOImpl;
import fi.arcusys.koku.common.external.EmailServiceDAO;
import fi.arcusys.koku.common.external.SMSServiceDAO;
import fi.arcusys.koku.common.service.impl.GenericNotificationServiceImpl;

/**
 * Mock implementation of generic notification service
 *
 * Uses dummy classes for email and SMS messages
 *
 * @author Mikhail Kapitonov (mikhail.kapitonov@arcusys.fi)
 * @see    GenericNotificationServiceImpl
 * Jun 26, 2012
 */
public class GenericNotificationServiceTestImpl extends GenericNotificationServiceImpl {

    @EJB
    private EmailServiceDAO emailDAO;

    @EJB
    private SMSServiceDAO smsDAO;

    @Override
    protected EmailServiceDAO getEmailDAO() {
        return emailDAO;
    }

    @Override
    protected SMSServiceDAO getSMSDAO() {
        return smsDAO;
    }

}

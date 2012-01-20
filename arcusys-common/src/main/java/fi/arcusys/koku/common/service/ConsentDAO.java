package fi.arcusys.koku.common.service;

import java.util.Date;
import java.util.List;

import fi.arcusys.koku.common.service.datamodel.Consent;
import fi.arcusys.koku.common.service.datamodel.User;
import fi.arcusys.koku.common.service.dto.ConsentDTOCriteria;
import fi.arcusys.koku.common.service.dto.ConsentExtDtoCriteria;

/**
 * DAO interface for CRUD operations with 'Consent' Entity
 * 
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Aug 23, 2011
 */
public interface ConsentDAO extends AbstractEntityDAO<Consent> {

    /**
     * @param orCreateUser
     * @param startNum
     * @param maxNum
     * @return
     */
    List<Consent> getAssignedConsents(final User user, final int startNum, final int maxNum);

    /**
     * @param orCreateUser
     * @return
     */
    Long getTotalAssignedConsents(User user);

    /**
     * @param replier
     * @param startNum
     * @param i
     * @return
     */
    List<Consent> getProcessedConsents(User user, ConsentDTOCriteria criteria, int startNum, int maxNum);

    /**
     * @param orCreateUser
     * @return
     */
    Long getTotalProcessedConsents(final User user, ConsentDTOCriteria criteria);

    /**
     * @param dtoCriteria
     * @return
     */
    List<Consent> searchConsents(ConsentExtDtoCriteria dtoCriteria);

    /**
     * @param date
     * @return
     */
    List<Consent> getOpenConsentsByReplyTillDate(final Date replyTill);
}

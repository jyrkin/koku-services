package fi.arcusys.koku.common.external;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fi.arcusys.koku.common.soa.Group;
import fi.arcusys.koku.common.soa.UserInfo;

/**
 * DAO implementation for accessing user-visible groups-related information.
 * 
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Oct 17, 2011
 */
@Stateless
public class GroupsDAOImpl implements GroupsDAO {

    private final static Logger logger = LoggerFactory.getLogger(GroupsDAOImpl.class); 
    
    @EJB
    private CustomerServiceDAO customerDao;
    
    @EJB
    private LdapDAO ldapDao;
    
    /**
     * @param searchString
     * @param limit
     * @return
     */
    @Override
    public List<Group> searchGroups(String searchString, int limit) {
        if (limit <= 0) {
            return Collections.emptyList();
        }
        
        final Map<String, String> groups = ldapDao.searchGroups(searchString);
        final List<Group> result = new ArrayList<Group>();
        for (final Map.Entry<String, String> entry : groups.entrySet()) {
            final Group group = new Group();
            group.setGroupUid(entry.getKey());
            group.setGroupName(entry.getValue());
            result.add(group);
            if (result.size() >= limit) {
                return result;
            }
        }
        return result;
    }

    /**
     * @param groupUid
     * @return
     */
    @Override
    public List<UserInfo> getUsersByGroupUid(String groupUid) {
        final List<UserInfo> result = new ArrayList<UserInfo>();
        
        for (final String memberName : ldapDao.getGroupMembers(groupUid)) {
            result.add(customerDao.getKunpoUserInfoBySsn(customerDao.getSsnByKunpoName(memberName)));
        }
        return result;
    }

}

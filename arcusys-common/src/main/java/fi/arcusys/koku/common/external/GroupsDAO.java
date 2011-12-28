package fi.arcusys.koku.common.external;

import java.util.List;

import fi.arcusys.koku.common.soa.Group;
import fi.arcusys.koku.common.soa.UserInfo;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Oct 17, 2011
 */
public interface GroupsDAO {

    public List<Group> searchGroups(String searchString, int limit);

    public List<UserInfo> getUsersByGroupUid(String groupUid);
}

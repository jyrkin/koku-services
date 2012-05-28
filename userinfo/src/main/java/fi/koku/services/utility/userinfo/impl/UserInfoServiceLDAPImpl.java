package fi.koku.services.utility.userinfo.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.SearchControls;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DirContextProcessor;
import org.springframework.ldap.core.simple.ParameterizedContextMapper;
import org.springframework.ldap.core.simple.SimpleLdapTemplate;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.ldap.filter.OrFilter;

import fi.koku.services.utility.user.v1.UserIdsQueryParamType;
import fi.koku.services.utility.user.v1.UserPicsQueryParamType;
import fi.koku.services.utility.user.v1.UserType;
import fi.koku.services.utility.user.v1.UsersType;
import fi.koku.services.utility.userinfo.impl.model.LDAPUser;

import fi.koku.services.utility.user.v1.GroupIdsQueryParamType;
import fi.koku.services.utility.user.v1.GroupsType;
import fi.koku.services.utility.user.v1.GroupType;
import fi.koku.services.utility.user.v1.UserGroupsIdsQueryParamType;
import fi.koku.services.utility.user.v1.UserGroupsPicsQueryParamType;
import fi.koku.services.utility.userinfo.impl.model.LDAPGroup;

/**
 * KoKu userInfo service LDAP implementation class.
 * 
 * @author hanhian
 * @author hekkata
 */
public class UserInfoServiceLDAPImpl implements UserInfoService {

  private Logger logger = LoggerFactory.getLogger(UserInfoServiceLDAPImpl.class);
  private SimpleLdapTemplate ldapTemplate;

  @Override
  public UsersType getUsersByIds(UserIdsQueryParamType idsType) {
    List<LDAPUser> users = getUsers(getUserQuery(idsType.getId(), "cn"));
    // TODO the group check is not efficient.
    // use member overlay in the LDAP and take it to use in here to speed things
    // up
    return checkGroup(users, idsType.getDomain());
  }

  @Override
  public UsersType getUsersByPics(UserPicsQueryParamType picsType) {
    List<LDAPUser> users = getUsers(getUserQuery(picsType.getPic(), "uid"));
    // TODO the group check is not efficient.
    // use member overlay in the LDAP and take it to use in here to speed things
    // up
    return checkGroup(users, picsType.getDomain());
  }

  public void setLdapTemplate(SimpleLdapTemplate ldapTemplate) {
    this.ldapTemplate = ldapTemplate;
  }

  @Override
  public GroupsType getGroupsByIds(GroupIdsQueryParamType idsType) {

    boolean allGroups = false;

    // check query for all groups
    if (idsType.getGroupId().get(0).equals("*")) {
      allGroups = true;
    }

    return checkUserGroup(getSpecificGroupQuery(idsType.getGroupId(), idsType.getDomain(), allGroups));
  }

  @Override
  public GroupsType getUserGroupsByIds(UserGroupsIdsQueryParamType idsType) {

    String query = getUserIdGroupQuery(idsType.getId(), idsType.getDomain(), false);

    return checkUserIdGroup(query);

  }

  @Override
  public GroupsType getUserGroupsByPics(UserGroupsPicsQueryParamType picsType) {

    List<LDAPUser> ldapUsers = getUsers(getUserQuery(picsType.getPic(), "uid"));

    List<String> users = new ArrayList<String>();

    for (int i = 0; i < ldapUsers.size(); i++) {
      users.add(i, ldapUsers.get(i).getDn());
    }

    String query = getUserIdGroupQuery(users, picsType.getDomain(), true);

    return checkUserIdGroup(query);
  }

  private List<LDAPUser> getUsers(String query) {
    SearchControls ctrl = new SearchControls();
    ctrl.setReturningAttributes(new String[] { "cn", "givenName", "sn", "uid", "mail" });
    ctrl.setSearchScope(SearchControls.SUBTREE_SCOPE);
    logger.debug("getUser: query: " + query.toString());

    return ldapTemplate.search("", query, ctrl, new LdapPersonMapper(), new DirContextProcessorNoop());
  }

  private UsersType checkGroup(List<LDAPUser> users, String group) {
    SearchControls ctrl = new SearchControls();
    ctrl.setReturningAttributes(new String[] { "member" });
    ctrl.setSearchScope(SearchControls.SUBTREE_SCOPE);

    String query = getGroupQuery(group);
    logger.debug("getGroup: query: " + query.toString());

    UsersType usersType = new UsersType();
    List<UserType> userTypes = usersType.getUser();

    List<List<String>> groups = ldapTemplate.search("", query, ctrl, new LdapGroupMapper(),
        new DirContextProcessorNoop());

    if (groups != null) {
      List<String> members = groups.get(0);
      if (members != null) {
        if (users != null) {
          for (LDAPUser ldapUser : users) {
            if (members.contains(ldapUser.getDn())) {
              userTypes.add(ldapUser);
            }
          }
        }
      }
    }
    return usersType;
  }

  private GroupsType checkUserGroup(String query) {
    SearchControls ctrl = new SearchControls();
    ctrl.setSearchScope(SearchControls.SUBTREE_SCOPE);

    GroupsType groupsType = new GroupsType();
    List<GroupType> groupTypes = groupsType.getGroup();

    //restrict search by first parameter
    List<LDAPGroup> groups = ldapTemplate.search("ou=Groups,ou=KokuCommunities", query, ctrl,
        new LdapGroupTypeMapper(), new DirContextProcessorNoop());

    if (groups != null) {
      for (LDAPGroup ldapGroup : groups) {
        GroupType group = new GroupType();
        group.setGroupId(ldapGroup.getGroupId());

        groupTypes.add(group);

        List<LDAPUser> users = ldapGroup.getUsers();

        if (users != null) {
          for (LDAPUser ldapUser : users) {
            UserType user = new UserType();

            String userDn = ldapUser.getDn();
            String parts[] = userDn.split(",");
            String userId = parts[0].substring(3);
            user.setUserId(userId);
            group.getMembers().add(user);
          }
        }
      }

    }
    return groupsType;
  }

  private String getUserQuery(List<String> criteria, String queryType) {
    // Add criteria to OR filter
    OrFilter orFilter = new OrFilter();
    for (String crit : criteria) {
      orFilter.or(new EqualsFilter(queryType, crit));
    }

    // Combine filters to query
    AndFilter mainFilter = new AndFilter();
    mainFilter.and(new EqualsFilter("objectclass", "inetorgperson"));
    mainFilter.and(orFilter);

    return mainFilter.encode();
  }

  private String getGroupQuery(String group) {
    // create group filter to fetch all members of the group
    // this might cause trouble if there is lots of members.
    AndFilter groupFilter = new AndFilter();
    groupFilter.and(new EqualsFilter("objectclass", "groupOfNames"));

    // add group check
    if (UserInfoServiceConstants.USER_INFO_SERVICE_DOMAIN_CUSTOMER.equals(group)) {
      groupFilter.and(new EqualsFilter("cn", "kuntalainen"));
    } else if (UserInfoServiceConstants.USER_INFO_SERVICE_DOMAIN_OFFICER.equals(group)) {
      groupFilter.and(new EqualsFilter("cn", "virkailija"));
    }

    return groupFilter.encode();
  }

  private String getSpecificGroupQuery(List<String> groupId, String domain, boolean allGroups) {

    AndFilter groupFilter = new AndFilter();

    if (!allGroups) {
      // Add criteria to OR filter
      OrFilter orFilter = new OrFilter();
      for (String crit : groupId) {
        orFilter.or(new EqualsFilter("cn", crit));
      }

      groupFilter.and(new EqualsFilter("objectclass", "groupOfNames"));
      groupFilter.and(orFilter);
    } else {
      groupFilter.and(new EqualsFilter("objectclass", "groupOfNames"));
    }

    if (!domain.equals("virkailija")) {
      // customer won't do this query
      return "";
    }

    return groupFilter.encode();
  }

  private GroupsType checkUserIdGroup(String query) {

    return checkUserGroup(query);
  }

  private String getUserIdGroupQuery(List<String> userId, String domain, boolean picQuery) {

    // Add criteria to OR filter
    OrFilter orFilter = new OrFilter();
    for (String crit : userId) {
      if (!picQuery) {
        crit = "cn=" + crit + ",ou=People,o=koku,dc=example,dc=org";
      }
      orFilter.or(new EqualsFilter("member", crit));
    }

    AndFilter userGroupFilter = new AndFilter();
    userGroupFilter.and(new EqualsFilter("objectclass", "groupOfNames"));
    userGroupFilter.and(orFilter);

    if (!domain.equals("virkailija")) {
      // customer won't do this query
      return "";
    }

    return userGroupFilter.encode();
  }

  private class LdapPersonMapper implements ParameterizedContextMapper<LDAPUser> {
    @Override
    public LDAPUser mapFromContext(Object ctx) {
      DirContextAdapter a = (DirContextAdapter) ctx;
      LDAPUser emp = new LDAPUser();
      emp.setUserId(a.getStringAttribute("cn"));
      emp.setFirstname(a.getStringAttribute("givenName"));
      emp.setLastname(a.getStringAttribute("sn"));
      emp.setPic(a.getStringAttribute("uid"));
      emp.setEmail(a.getStringAttribute("mail"));
      emp.setDn(a.getNameInNamespace());
      return emp;
    }
  }

  private class LdapGroupMapper implements ParameterizedContextMapper<List<String>> {
    @Override
    public List<String> mapFromContext(Object ctx) {
      DirContextAdapter a = (DirContextAdapter) ctx;
      return Arrays.asList(a.getStringAttributes("member"));
    }
  }

  private class LdapGroupTypeMapper implements ParameterizedContextMapper<LDAPGroup> {
    @Override
    public LDAPGroup mapFromContext(Object ctx) {
      DirContextAdapter a = (DirContextAdapter) ctx;

      LDAPGroup group = new LDAPGroup();
      group.setGroupId(a.getStringAttribute("cn"));

      List<String> members = Arrays.asList(a.getStringAttributes("member"));
      List<LDAPUser> users = new ArrayList<LDAPUser>();

      for (String dnName : members) {
        LDAPUser user = new LDAPUser();
        user.setDn(dnName);
        users.add(user);
      }

      group.setUsers(users);
      return group;
    }
  }

  private static class DirContextProcessorNoop implements DirContextProcessor {
    @Override
    public void postProcess(DirContext ctx) throws NamingException {
    }

    @Override
    public void preProcess(DirContext ctx) throws NamingException {
    }
  }
}
package fi.koku.services.utility.authorization.impl;

import static junit.framework.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import fi.koku.services.utility.authorization.v1.GroupQueryCriteriaType;
import fi.koku.services.utility.authorization.v1.GroupType;
import fi.koku.services.utility.authorization.v1.GroupsType;
import fi.koku.services.utility.authorization.v1.MemberPicsType;


public class GroupServiceLDAPImplTest {
  private static GroupServiceLDAPImpl cut;
  
  @BeforeClass
  public static void init() throws Exception {
    cut = new GroupServiceLDAPImpl();
  }
  
  @Test
  public void testGetGroups() throws Exception {
      GroupQueryCriteriaType c = new GroupQueryCriteriaType();
      c.setDomain("kks");
      c.setGroupClass("registry");
      c.setMemberPics(new MemberPicsType());
      c.getMemberPics().getMemberPic().add("292929-2929");
      c.getMemberPics().getMemberPic().add("202020-2001");
      c.getMemberPics().getMemberPic().add("202020-2002");
      GroupsType groups = cut.getGroups(c);
      for(GroupType g : groups.getGroup()) {
        System.out.println("g: "+g.getId()+": "+g.getMemberPics().getMemberPic().size());
      }
  }
  
  @Test
  public void testGetGroupsQuery() {
    List<GroupServiceLDAPImpl.LdapPerson> p1 = Arrays.asList(
        new GroupServiceLDAPImpl.LdapPerson("cn=kirsi.kuntalainen,ou=People,o=koku,dc=example,dc=org", "222222-2222")
        );
    List<GroupServiceLDAPImpl.LdapPerson> p2 = Arrays.asList(
        new GroupServiceLDAPImpl.LdapPerson("cn=kirsi.kuntalainen,ou=People,o=koku,dc=example,dc=org", "222222-2222"),
        new GroupServiceLDAPImpl.LdapPerson("cn=kalle.kuntalainen,ou=People,o=koku,dc=example,dc=org", "111111-1111")
        );
    assertEquals("(&(objectclass=groupofnames)(member=cn=kirsi.kuntalainen,ou=People,o=koku,dc=example,dc=org))", cut.getGroupsQuery(p1));
    assertEquals("(&(objectclass=groupofnames)(|(member=cn=kirsi.kuntalainen,ou=People,o=koku,dc=example,dc=org)(member=cn=kalle.kuntalainen,ou=People,o=koku,dc=example,dc=org)))",
        cut.getGroupsQuery(p2));
  }
  
  @Test
  public void testGgetPersonsQuery() {
    assertEquals("(&(objectclass=inetorgperson)(|(uid=a)(uid=b)))", cut.getPersonsQuery(Arrays.asList("a", "b")));
    assertEquals("(&(objectclass=inetorgperson)(uid=a))", cut.getPersonsQuery(Arrays.asList("a")));
  }

}

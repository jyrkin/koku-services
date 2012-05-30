package fi.koku.services.utility.userinfo.impl.model;

import fi.koku.services.utility.user.v1.GroupType;
import java.util.List;

/**
 * Extension for GroupType to contain also cn.
 * 
 * @author hekkata
 */


public class LDAPGroup extends GroupType{
  
  private String cn;
  private List<LDAPUser> users;

  public String getcn() {
    return cn;
  }

  public void setCn(String cn) {
    this.cn = cn;
  }
  
  public List<LDAPUser> getUsers() {
    return users;
  }

  public void setUsers(List<LDAPUser> users) {
    this.users = users;
  }
  
}
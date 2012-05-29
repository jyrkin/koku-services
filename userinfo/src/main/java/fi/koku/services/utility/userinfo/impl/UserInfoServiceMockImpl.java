package fi.koku.services.utility.userinfo.impl;

import java.awt.RenderingHints.Key;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fi.koku.services.utility.user.v1.GroupIdsQueryParamType;
import fi.koku.services.utility.user.v1.GroupsType;
import fi.koku.services.utility.user.v1.UserGroupsIdsQueryParamType;
import fi.koku.services.utility.user.v1.UserGroupsPicsQueryParamType;
import fi.koku.services.utility.user.v1.UserIdsQueryParamType;
import fi.koku.services.utility.user.v1.UserPicsQueryParamType;
import fi.koku.services.utility.user.v1.UserType;
import fi.koku.services.utility.user.v1.UsersType;
import fi.koku.services.utility.user.v1.GroupType;
import fi.koku.services.utility.user.v1.GroupsType;
import fi.koku.services.utility.userinfo.impl.model.LDAPGroup;
import fi.koku.services.utility.userinfo.impl.model.LDAPUser;

/**
 * KoKu userInfo service Mock implementation class. Uses the same data format as the Kahva mock service.
 * 
 * @author hanhian
 */
public class UserInfoServiceMockImpl implements UserInfoService {

  private final Logger LOG = LoggerFactory.getLogger(UserInfoServiceMockImpl.class);
  
  @Override
  public UsersType getUsersByIds(UserIdsQueryParamType idsType) {
    
   // TODO HANDLE THE DOMAIN: idsType.getDomain()
    
    UsersType usersType = new UsersType();

    for (String id : idsType.getId()) {
      UserType emp = getUserById(id);

      if (emp != null) {
        usersType.getUser().add(emp);
      }
    }
    return usersType;
  }

  @Override
  public UsersType getUsersByPics(UserPicsQueryParamType picsType) {
    
    // TODO HANDLE THE DOMAIN: idsType.getDomain()
    
    UsersType usersType = new UsersType();

    for (String pic : picsType.getPic()) {
      UserType emp = getUserByPic(pic);

      if (emp != null) {
        usersType.getUser().add(emp);
      }
    }
    return usersType;
  } 

  public GroupsType getGroupsByIds(GroupIdsQueryParamType idsType){
    
    GroupsType groupsType = new GroupsType();
    
    for (String id : idsType.getGroupId()) {
      GroupType emp = getGroupById(id);

      if (emp != null) {
        groupsType.getGroup().add(emp);
      }
    }
    
    return groupsType;
  }
  
  public GroupsType getUserGroupsByIds(UserGroupsIdsQueryParamType idsType){

    GroupsType groupsType = new GroupsType();
/*
    for (String id : idsType.getId()) {
      GroupType emp = getUserGroupById(id);

      if (emp != null) {
        groupsType.getGroup().add(emp);
      }
    }
    */
    return groupsType;
  }
  
  public GroupsType getUserGroupsByPics(UserGroupsPicsQueryParamType picsType){

    GroupsType groupsType = new GroupsType();
/*
    for (String id : picsType.getPic()) {
      GroupType emp = getUserGroupByPic(id);

      if (emp != null) {
        groupsType.getGroup().add(emp);
      }
    }
*/
    return groupsType;
  }
  
  
  private UserType getUserById(String id) {
    // Currently supported (and required) user information:
    // userId,ssn,firstName,lastName,email.
    // Example row:
    // toivo.toivola=toivo.toivola,111111-1111,Toivo,Toivola,toivo.toivola@localhost.fi
    Properties props = load("/getUsersByIdMock.properties");

    return getEmp(id, props);
  }

  private UserType getUserByPic(String pic) {
    // Currently supported (and required) user information:
    // userId,ssn,firstName,lastName,email.
    // Example row:
    // 111111-1111=toivo.toivola,111111-1111,Toivo,Toivola,toivo.toivola@localhost.fi
    Properties props = load("/getUsersByPicMock.properties");

    return getEmp(pic, props);
  }

  private GroupType getGroupById(String id) {
    // Currently supported (and required) user information:
    // groupId, member.
    // Example row:
    // Vallilan Päiväkoti Oravat=kaisa.kuntalainen,keijo.kuntalainen
 
    //tee uusi group-tiedosto
    Properties props = load("/getGroupsByIdMock.properties");
         
    return getGroupEmp(id, props);
  }

  
  private UserType getEmp(String key, Properties props) {
    UserType emp = null;
    if (props != null) {
      LOG.info("props=" + props.toString());
      String property = props.getProperty(key);
      if (property == null) {
        LOG.info("could not find person with key " + key);
      } else {
        property = property.trim();
      }

      LOG.info("used property=" + property);
      if (property != null && !"".equals(property)) {
        emp = new UserType();
        // Put values from props-file to User object
        String[] p = property.split(",");
        emp.setUserId(p[0]);
        emp.setPic(p[1]);
        emp.setFirstname(p[2]);
        emp.setLastname(p[3]);
        emp.setEmail(p[4]);
      }
    }
    return emp;
  }

  private GroupType getGroupEmp(String key, Properties props) {
    GroupType emp = null;
    if (props != null) {
      LOG.info("props=" + props.toString());
      String property = props.getProperty(key);
      if (property == null) {
        LOG.info("could not find group with key " + key);
      } else {
        property = property.trim();
      }

      LOG.info("used property=" + property);
      if (property != null && !"".equals(property)) {
        emp = new GroupType();
        // Put values from props-file to Group object
        String[] p = property.split(",");
        //emp.setGroupId(p[0]);
        emp.setGroupId(key);
        
        for(int i=0;i<2;i++)
        {
        UserType user = new UserType();
        user.setUserId(p[i]);
        emp.getMembers().add(user);
        }                
      }
    }
    return emp;
  }

  
  
  private Properties load(String propsName) {
    LOG.info("Trying to load properties propsName=" + propsName);
    Properties props = new Properties();
    try {
      URL in = this.getClass().getClassLoader().getResource(propsName);
      props.load(in.openStream());

    } catch (IOException e) {
      LOG.error("Failed to load properties file with propsName=" + propsName);
    }
    return props;
  }
}
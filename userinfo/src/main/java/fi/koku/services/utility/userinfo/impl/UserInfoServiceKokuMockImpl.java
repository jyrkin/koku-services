package fi.koku.services.utility.userinfo.impl;

import javax.ejb.EJB;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fi.koku.services.utility.user.v1.GroupIdsQueryParamType;
import fi.koku.services.utility.user.v1.GroupsType;
import fi.koku.services.utility.user.v1.PortalUserQueryParamType;
import fi.koku.services.utility.user.v1.PortalUserType;
import fi.koku.services.utility.user.v1.PortalUserUpdateType;
import fi.koku.services.utility.user.v1.UserGroupsIdsQueryParamType;
import fi.koku.services.utility.user.v1.UserGroupsPicsQueryParamType;
import fi.koku.services.utility.user.v1.UserIdsQueryParamType;
import fi.koku.services.utility.user.v1.UserPicsQueryParamType;
import fi.koku.services.utility.user.v1.UsersType;
import fi.koku.services.utility.user.v1.VoidType;

public class UserInfoServiceKokuMockImpl  implements UserInfoService  {

  private final Logger LOG = LoggerFactory.getLogger(UserInfoServiceMockImpl.class);
  
  @EJB
  private UserInfoServiceDAO userInfoServiceDAOBean;
  private UserInfoService userInfoMockService;
  private PortalUserConverter portalConverter;
  
  

  public UserInfoServiceKokuMockImpl() {
    portalConverter = new PortalUserConverter();
  }
  
  public UserInfoService getUserInfoMockService() {
    return userInfoMockService;
  }

  public void setUserInfoMockService(UserInfoService userInfoMockService) {
    this.userInfoMockService = userInfoMockService;
  }

  public UserInfoServiceDAO getUserInfoServiceDAOBean() {
    return userInfoServiceDAOBean;
  }

  public void setUserInfoServiceDAOBean(UserInfoServiceDAO userInfoServiceDAOBean) {
    this.userInfoServiceDAOBean = userInfoServiceDAOBean;
  }


  @Override
  public UsersType getUsersByIds(UserIdsQueryParamType idsType) {
    
    if ( UserInfoServiceConstants.USER_INFO_SERVICE_DOMAIN_OFFICER.equals(  idsType.getDomain().trim() ) ) {
      LOG.debug("Searching user from officer side " + idsType.getDomain() );
      return userInfoMockService.getUsersByIds(idsType);  
    } else {
      LOG.debug("Searching user from customer side " + idsType.getDomain() );
      UsersType tmp = userInfoMockService.getUsersByIds(idsType);  
      
      if ( tmp.getUser().size() > 0 ) {
        return tmp;
      }
      return userInfoServiceDAOBean.getUsersByIds(idsType);    
    }
  }

  @Override
  public UsersType getUsersByPics(UserPicsQueryParamType picsType) {
    
    if ( UserInfoServiceConstants.USER_INFO_SERVICE_DOMAIN_OFFICER.equals(  picsType.getDomain().trim() ) ) {
      LOG.debug("Searching user from officer side " + picsType.getDomain() );
      return userInfoMockService.getUsersByPics(picsType);
    } else {
      LOG.debug("Searching user from customer side " + picsType.getDomain() );
      
      UsersType tmp = userInfoMockService.getUsersByPics(picsType);  
      
      if ( tmp.getUser().size() > 0 ) {
        return tmp;
      }
      
      return userInfoServiceDAOBean.getUsersByPics(picsType);    
    }
    
  }

  public GroupsType getGroupsByIds(GroupIdsQueryParamType idsType) {
    return userInfoMockService.getGroupsByIds(idsType);
  }

  public GroupsType getUserGroupsByIds(UserGroupsIdsQueryParamType idsType) {
    return userInfoMockService.getUserGroupsByIds(idsType);
  }

  public GroupsType getUserGroupsByPics(UserGroupsPicsQueryParamType picsType) {
    return userInfoMockService.getUserGroupsByPics(picsType);
  }
 
  public VoidType addPortalUser(PortalUserType portalUser) {
    LOG.info("addPortalUser (impl: " + userInfoServiceDAOBean + ")");
    userInfoServiceDAOBean.insertPortalUser(portalConverter.fromWsType(portalUser));
    return new VoidType();

  } 
  public boolean updatePortalUser(PortalUserUpdateType portalUser) {
    LOG.info("updatePortalUser (impl: " + userInfoServiceDAOBean + ")");
    return userInfoServiceDAOBean.updatePortalUser(portalUser);
  }
 
  public boolean authenticatePortalUser(PortalUserQueryParamType portalUser) {
    LOG.info("authenticatePortalUser (impl: " + userInfoServiceDAOBean + ")");
    return userInfoServiceDAOBean.authenticatePortalUser(portalUser);
  }
}

/*
 * Copyright 2012 Ixonos Plc, Finland. All rights reserved.
 * 
 * This file is part of Kohti kumppanuutta.
 *
 * This file is licensed under GNU LGPL version 3.
 * Please see the 'license.txt' file in the root directory of the package you received.
 * If you did not receive a license, please contact the copyright holder
 * (kohtikumppanuutta@ixonos.com).
 *
 */
package fi.koku.services.utility.userinfo.impl;

import java.util.Date;

import javax.ejb.EJB;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fi.koku.calendar.CalendarUtil;
import fi.koku.services.utility.user.v1.ContactInfoType;
import fi.koku.services.utility.user.v1.GroupIdsQueryParamType;
import fi.koku.services.utility.user.v1.GroupsType;
import fi.koku.services.utility.user.v1.PortalUserAllType;
import fi.koku.services.utility.user.v1.PortalUserQueryParamType;
import fi.koku.services.utility.user.v1.PortalUserType;
import fi.koku.services.utility.user.v1.PortalUserUpdateType;
import fi.koku.services.utility.user.v1.UserGroupsIdsQueryParamType;
import fi.koku.services.utility.user.v1.UserGroupsPicsQueryParamType;
import fi.koku.services.utility.user.v1.UserIdsQueryParamType;
import fi.koku.services.utility.user.v1.UserPicsQueryParamType;
import fi.koku.services.utility.user.v1.UsersType;
import fi.koku.services.utility.user.v1.VoidType;

/**
 * @author hekkata
 * 
 */
public class UserInfoServiceKokuImpl implements UserInfoService {

  private Logger logger = LoggerFactory.getLogger(UserInfoServiceKokuImpl.class);

  @EJB
  private UserInfoServiceDAO userInfoServiceDAOBean;

  public UserInfoServiceDAO getUserInfoServiceDAOBean() {
    return userInfoServiceDAOBean;
  }

  public void setUserInfoServiceDAOBean(UserInfoServiceDAO userInfoServiceDAOBean) {
    this.userInfoServiceDAOBean = userInfoServiceDAOBean;
  }

  private PortalUserConverter portalConverter;

  public UserInfoServiceKokuImpl() {
    portalConverter = new PortalUserConverter();
  }

  @Override
  public VoidType addPortalUser(PortalUserType portalUser) {
    logger.info("addPortalUser (impl: " + userInfoServiceDAOBean + ")");
    userInfoServiceDAOBean.insertPortalUser(portalConverter.fromWsType(portalUser));
    return new VoidType();

  }

  @Override
  public boolean updatePortalUser(PortalUserUpdateType portalUser) {
    logger.info("updatePortalUser (impl: " + userInfoServiceDAOBean + ")");
    return userInfoServiceDAOBean.updatePortalUser(portalUser);
  }

  @Override
  public boolean authenticatePortalUser(PortalUserQueryParamType portalUser) {
    logger.info("authenticatePortalUser (impl: " + userInfoServiceDAOBean + ")");
    return userInfoServiceDAOBean.authenticatePortalUser(portalUser);

  }

  @Override
  public UsersType getUsersByIds(UserIdsQueryParamType ids) {
    logger.info("getUsersByIds (impl: " + userInfoServiceDAOBean + ")");
    return userInfoServiceDAOBean.getUsersByIds(ids);    
  }

  @Override
  public UsersType getUsersByPics(UserPicsQueryParamType pics) {
    logger.info("getUsersByPics (impl: " + userInfoServiceDAOBean + ")");    
    return userInfoServiceDAOBean.getUsersByPics(pics);
  }

  @Override
  public GroupsType getGroupsByIds(GroupIdsQueryParamType ids) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public GroupsType getUserGroupsByIds(UserGroupsIdsQueryParamType ids) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public GroupsType getUserGroupsByPics(UserGroupsPicsQueryParamType pics) {
    // TODO Auto-generated method stub
    return null;
  }

}

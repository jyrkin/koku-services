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

import javax.ejb.Local;

import fi.koku.services.utility.user.v1.PortalUserQueryParamType;
import fi.koku.services.utility.user.v1.PortalUserUpdateType;
import fi.koku.services.utility.user.v1.UserIdsQueryParamType;
import fi.koku.services.utility.user.v1.UserPicsQueryParamType;
import fi.koku.services.utility.user.v1.UsersType;

/**
 * UserInfoService related data access facilities.
 * 
 * @author hekkata
 */
@Local
public interface UserInfoServiceDAO {

  void insertPortalUser(PortalUser user);

  boolean updatePortalUser(PortalUserUpdateType user);

  boolean authenticatePortalUser(PortalUserQueryParamType user);

  UsersType getUsersByIds(UserIdsQueryParamType ids);
  
  UsersType getUsersByPics(UserPicsQueryParamType pics);
}

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
package fi.koku.services.utility.portaluser.impl;

import javax.ejb.Local;

import fi.koku.services.entity.customer.v1.CustomerType;
import fi.koku.services.utility.portal.v1.PortalUserAllType;
import fi.koku.services.utility.portal.v1.PortalUserPicQueryParamType;
import fi.koku.services.utility.portal.v1.PortalUserUpdateType;

/**
 * PortalUserService related data access facilities.
 * 
 * @author hekkata
 */
@Local
public interface PortalUserServiceDAO {

  /**
   * Gets the user by pic.
   *
   * @param pic the pic
   * @return the user by pic
   */
  PortalUserAllType getUserByPic(PortalUserPicQueryParamType pic);
  
  /**
   * Insert portal user.
   *
   * @param user portal user
   * @param cust customer type
   * @param pic the pic
   */
  void insertPortalUser(PortalUser user, CustomerType cust, String pic);

  /**
   * Update portal user.
   *
   * @param user portal user
   */
  void updatePortalUser(PortalUserUpdateType user);

  /**
   * Authenticate portal user.
   *
   * @param user the user
   * @return the portal user
   */
  PortalUserAllType authenticatePortalUser(PortalUserPicQueryParamType user);
  
  /**
   * Disable portal user.
   *
   * @param user the user
   */
  void disablePortalUser(PortalUserPicQueryParamType user);
  
  /**
   * Removes the portal user.
   *
   * @param user the user
   */
  void removePortalUser(PortalUserPicQueryParamType user);
}

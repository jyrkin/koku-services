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

import fi.koku.services.utility.portal.v1.PortalUserAllType;
import fi.koku.services.utility.portal.v1.PortalUserPicQueryParamType;
import fi.koku.services.utility.portal.v1.PortalUserType;
import fi.koku.services.utility.portal.v1.PortalUserUpdateType;
import fi.koku.services.utility.portal.v1.VoidType;

/**
 * PortalUser service interface.
 * 
 * @author hekkata
 */
@Local
public interface PortalUserService {

  /**
   * Returns the User by UserID.
   * 
   * @param id
   *          Pic of the user that is searched for.
   * @return the user whose pic matches the given pic.
   */
  PortalUserAllType getPortalUserByPic(PortalUserPicQueryParamType id);  
  /**
   * Add new portal user.
   * 
   * @param portalUser
   *          portalUser-object to be added to database.
   * @return none.
   */
  VoidType addPortalUser(PortalUserType portalUser);
  /**
   * Update portal user's data.
   * 
   * @param portalUser
   *          portalUser-object to be added to database.
   * @return update status.
   */
  VoidType updatePortalUser(PortalUserUpdateType userParam);
  /**
   * Authenticate portal user.
   * 
   * @param portalUser
   *          username and password to be authenticated.
   * @return authentication status.
   */
  PortalUserAllType authenticatePortalUser(PortalUserPicQueryParamType userParam);
  /**
   * Authenticate portal user.
   * 
   * @param portalUser
   *          username and password to be authenticated.
   * @return authentication status.
   */
  VoidType removePortalUser(PortalUserPicQueryParamType userParam);
  /**
   * Authenticate portal user.
   * 
   * @param portalUser
   *          username and password to be authenticated.
   * @return authentication status.
   */
  VoidType disablePortalUser(PortalUserPicQueryParamType userParam);
}

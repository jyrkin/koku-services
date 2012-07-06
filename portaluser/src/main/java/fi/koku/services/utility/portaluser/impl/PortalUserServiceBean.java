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

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fi.koku.services.entity.customer.v1.CustomerType;
import fi.koku.services.utility.portal.v1.PortalUserAllType;
import fi.koku.services.utility.portal.v1.PortalUserPicQueryParamType;
import fi.koku.services.utility.portal.v1.PortalUserType;
import fi.koku.services.utility.portal.v1.PortalUserUpdateType;
import fi.koku.services.utility.portal.v1.VoidType;

/**
 * Implements PortalUserService interface.
 * 
 * @author hekkata
 * 
 */
@Stateless
public class PortalUserServiceBean implements PortalUserService {

  private Logger logger = LoggerFactory.getLogger(PortalUserServiceBean.class);

  @EJB
  private PortalUserServiceDAO portalUserServiceDAOBean;
  
  private PortalUserConverter portalConverter;

  /**
   * Instantiates a new portal user service bean.
   */
  public PortalUserServiceBean() {
    portalConverter = new PortalUserConverter();
  }

  @Override
  public VoidType addPortalUser(PortalUserType portalUser) {
    logger.info("addPortalUser (impl: " + portalUserServiceDAOBean + ")");
    
    PortalUser user = new PortalUser();
    CustomerType cust = new CustomerType();
    user = portalConverter.fromWsType(portalUser);
    cust = portalConverter.ToCustomerWsType(portalUser);
        
    portalUserServiceDAOBean.insertPortalUser(user, cust, portalUser.getPic());
    
    return new VoidType();

  }

  @Override
  public VoidType updatePortalUser(PortalUserUpdateType portalUser) {
    logger.info("updatePortalUser (impl: " + portalUserServiceDAOBean + ")");
    
    //PortalUserType user = portalConverter.UpdateTypeToUserType(portalUser);
    //get customer from customer service
       
    //CustomerType cust = portalConverter.UpdateTypeToCustomerType(portalUser);
    
    //portalUserServiceDAOBean.updatePortalUser(portalUser, cust);
    portalUserServiceDAOBean.updatePortalUser(portalUser);
    
    return new VoidType();
  }

  @Override
  public PortalUserAllType authenticatePortalUser(PortalUserPicQueryParamType portalUser) {
    logger.info("authenticatePortalUser (impl: " + portalUserServiceDAOBean + ")");
    return portalUserServiceDAOBean.authenticatePortalUser(portalUser);    
  }

  @Override
  public VoidType removePortalUser(PortalUserPicQueryParamType portalUser) {
    logger.info("removePortalUser (impl: " + portalUserServiceDAOBean + ")");
    portalUserServiceDAOBean.removePortalUser(portalUser);
    
    return new VoidType();
  }

  @Override
  public VoidType disablePortalUser(PortalUserPicQueryParamType portalUser) {
    logger.info("disablePortalUser (impl: " + portalUserServiceDAOBean + ")");
    portalUserServiceDAOBean.disablePortalUser(portalUser);
    
    return new VoidType();
  }
  
  @Override
  public PortalUserAllType getPortalUserByPic(PortalUserPicQueryParamType pic) {
    logger.info("getPortalUserByPic (impl: " + portalUserServiceDAOBean + ")");
    return portalUserServiceDAOBean.getUserByPic(pic);    
  }

}

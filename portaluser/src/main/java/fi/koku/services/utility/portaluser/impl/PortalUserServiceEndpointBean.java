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

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fi.koku.services.utility.portal.v1.PortalUserAllType;
import fi.koku.services.utility.portal.v1.PortalUserPicQueryParamType;
import fi.koku.services.utility.portal.v1.PortalUserServicePortType;
import fi.koku.services.utility.portal.v1.PortalUserType;
import fi.koku.services.utility.portal.v1.PortalUserUpdateType;
import fi.koku.services.utility.portal.v1.ServiceFault;
import fi.koku.services.utility.portal.v1.VoidType;

/**
 * KoKu portalUser service endpoint implementation class.
 * 
 * @author hekkata
 */
@Stateless
@WebService(wsdlLocation = "META-INF/wsdl/portalUserService.wsdl",
  endpointInterface = "fi.koku.services.utility.portal.v1.PortalUserServicePortType",
  targetNamespace = "http://services.koku.fi/utility/portal/v1",
  portName = "portalUserService-soap11-port",
  serviceName = "portalUserService")
@RolesAllowed("koku-role")
public class PortalUserServiceEndpointBean implements PortalUserServicePortType {
  private Logger logger = LoggerFactory.getLogger(PortalUserServiceEndpointBean.class);

  @EJB
  private PortalUserService portalUserService;
      
  public PortalUserServiceEndpointBean() {  
  }
     
  @Override
  public PortalUserAllType opGetPortalUserByPic(PortalUserPicQueryParamType portalUserPicQueryParam) throws ServiceFault {
    logger.info("opAddPortalUser (impl: " + portalUserService + ")");
    return portalUserService.getPortalUserByPic(portalUserPicQueryParam);
  }
            
  @Override
  public VoidType opAddPortalUser(PortalUserType addPortalUserParam) throws ServiceFault {
    logger.info("opAddPortalUser (impl: " + portalUserService + ")");
    return portalUserService.addPortalUser(addPortalUserParam);
  }
  
  @Override
  public VoidType opUpdatePortalUser(PortalUserUpdateType updatePortalUserParam) throws ServiceFault {
    logger.info("opUpdatePortalUser (impl: " + portalUserService + ")");
    return portalUserService.updatePortalUser(updatePortalUserParam);
  }
  
  @Override
  public PortalUserAllType opAuthenticatePortalUser(PortalUserPicQueryParamType authenticatePortalUserParam)
      throws ServiceFault {
    logger.info("opAuthenticatePortalUser (impl: " + portalUserService + ")");
    return portalUserService.authenticatePortalUser(authenticatePortalUserParam);
  }

  @Override
  public VoidType opDisablePortalUser(PortalUserPicQueryParamType disablePortalUserParam)
      throws ServiceFault {
    logger.info("opDisablePortalUser (impl: " + portalUserService + ")");
    return portalUserService.disablePortalUser(disablePortalUserParam);
  }
  
  @Override
  public VoidType opRemovePortalUser(PortalUserPicQueryParamType removePortalUserParam)
      throws ServiceFault {
    logger.info("opRemovePortalUser (impl: " + portalUserService + ")");
    return portalUserService.removePortalUser(removePortalUserParam);
  }
  
}

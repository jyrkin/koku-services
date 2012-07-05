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

import fi.koku.services.entity.customer.v1.CustomerServiceFactory;
import fi.koku.services.entity.customer.v1.CustomerServicePortType;
import fi.koku.settings.KoKuPropertiesUtil;

/**
 * Container for outside services used in the portal user service
 * 
 * @author Ixonos / hekkata
 * 
 */
public class PortalUserServiceContainer {

  public static final String CUSTOMER_ENDPOINT = KoKuPropertiesUtil.get("customer.service.endpointaddress");
  public static final String CUSTOMER_SERVICE_USER_ID = KoKuPropertiesUtil.get("portaluserservice.customer.service.user.id");
  public static final String CUSTOMER_SERVICE_PASSWORD = KoKuPropertiesUtil.get("portaluserservice.customer.service.password");
  
  private CustomerServicePortType customerService;
  
  private static PortalUserServiceContainer serviceContainer;

  /**
   * Instantiates a new portal user service container.
   */
  private PortalUserServiceContainer() {
    customerService = createCustomerService();    
  }

  /**
   * Gets the service.
   *
   * @return the service
   */
  public static synchronized PortalUserServiceContainer getService() {
    if (serviceContainer == null) {
      serviceContainer = new PortalUserServiceContainer();
    }
    return serviceContainer;
  }

  /**
   * Creates the customer service.
   *
   * @return the customer service port type
   */
  private CustomerServicePortType createCustomerService() {
    CustomerServiceFactory csf = new CustomerServiceFactory(CUSTOMER_SERVICE_USER_ID, CUSTOMER_SERVICE_PASSWORD,
        CUSTOMER_ENDPOINT);
    return csf.getCustomerService();
  }

  /**
   * Customer.
   *
   * @return the customer service port type
   */
  public CustomerServicePortType customer() {
    return customerService;
  }
  
}

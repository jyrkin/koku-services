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

import javax.ejb.Stateless;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fi.koku.services.entity.customer.v1.AuditInfoType;
import fi.koku.services.entity.customer.v1.CustomerQueryCriteriaType;
import fi.koku.services.entity.customer.v1.CustomerType;
import fi.koku.services.entity.customer.v1.CustomersType;
import fi.koku.services.entity.customer.v1.ServiceFault;
import fi.koku.services.entity.customer.v1.VoidType;

/**
 * Implements customer services for portal user service
 * 
 * @author Ixonos / hekkata
 */
@Stateless
public class PortalCustomerBean implements PortalCustomer {

  private static final Logger LOG = LoggerFactory.getLogger(PortalCustomerBean.class);
  
  @Override
  public String addCustomer(CustomerType customer, String pic) {
    
    String added= new String();
    
    try {
      added = PortalUserServiceContainer.getService().customer().opAddCustomer(customer, getPortalUserAuditInfo(pic));
    } catch (ServiceFault e) {
      LOG.error("Failed to add customer", e);
    }
    return added;
  }
  
  @Override
  public CustomerType getCustomer(String pic) {
        
    CustomerType cust = new CustomerType();
    
    try {
      cust = PortalUserServiceContainer.getService().customer().opGetCustomer(pic, getPortalUserAuditInfo(pic));
    } catch (ServiceFault e) {
      LOG.error("Failed to get customer", e);
    }  
    return cust;
  }
  
  @Override
  public VoidType updateCustomer(CustomerType customer, String pic) {
    
    try {
      PortalUserServiceContainer.getService().customer().opUpdateCustomer(customer, getPortalUserAuditInfo(pic));
    } catch (ServiceFault e) {
      LOG.error("Failed to update customer", e);
    }
    return new VoidType();
  }
  
  @Override
  public VoidType deleteCustomer(String pic) {
    
    try {
      PortalUserServiceContainer.getService().customer().opDeleteCustomer(pic, getPortalUserAuditInfo(pic));
    } catch (ServiceFault e) {
      LOG.error("Failed to delete customer", e);
    }
    return new VoidType();
  }
  
  @Override
  public CustomersType queryCustomers(CustomerQueryCriteriaType criteria, String pic) {
    
    CustomersType customers = new CustomersType();
    
    try {
      customers = PortalUserServiceContainer.getService().customer().opQueryCustomers(criteria, getPortalUserAuditInfo(pic));
    } catch (ServiceFault e) {
      LOG.error("Failed to query customers", e);
    }
    return customers;
  }

  /**
   * Gets audit info
   * 
   * @param user
   * @return  audit info
   */
  private AuditInfoType getPortalUserAuditInfo(String user) {
    AuditInfoType a = new AuditInfoType();    
    a.setComponent("PortalUser");
    a.setUserId(user);
    return a;
  }
}

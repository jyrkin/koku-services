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

import fi.koku.services.entity.customer.v1.CustomerQueryCriteriaType;
import fi.koku.services.entity.customer.v1.CustomerType;
import fi.koku.services.entity.customer.v1.CustomersType;
import fi.koku.services.entity.customer.v1.VoidType;

/**
 * Customer interface provides customer related services for other portaluser-service classes. 
 * 
 * @author Ixonos / hekkata
 */
@Local
public interface PortalCustomer {

  /**
   * Add customer
   * 
   * @param customer
   * @param auditing info
   * @return customer id 
   */
  public String addCustomer(CustomerType customer, String pic);
    
  /**
   * Get customer
   * 
   * @param customer pic
   * @param auditing info
   * @return found customer
   */
  public CustomerType getCustomer(String pic);
  
  /**
   * Update customer
   * 
   * @param customer
   * @param auditing info 
   */
  public VoidType updateCustomer(CustomerType customer, String pic);
  
  /**
   * Delete customer
   * 
   * @param customer pic
   * @param auditing info
   */
  public VoidType deleteCustomer(String pic);
  
  /**
   * Query customer(s)
   * 
   * @param customer query criteria
   * @param auditing info
   * @return customer(s) queried
   */
  public CustomersType queryCustomers(CustomerQueryCriteriaType criteria, String pic);
  
}

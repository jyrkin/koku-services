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
package fi.koku.services.entity.customer.impl;

import java.util.Collection;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;

import fi.koku.KoKuNotAuthorizedException;
import fi.koku.services.entity.community.impl.Community;
import fi.koku.services.entity.community.impl.CommunityServiceErrorCode;

/**
 * Customer service implementation.
 * 
 * @author aspluma
 * @author laukksa
 */
@Stateless
@RolesAllowed("koku-role")
public class CustomerServiceBean implements CustomerService {
    
  @Resource
  private SessionContext ctx;
  
  @EJB
  private CustomerDAO customerDAO;

  public CustomerServiceBean() {
  }

  @Override
  public Customer get(String pic) {
    return customerDAO.findCustomer(pic);
  }
  
  @Override
  public Long add(Customer c) {
    verifyUserRole();
    return customerDAO.insertCustomer(c);
  }
  
  @Override
  public void update(Customer customer) {
    
    if ( ctx.isCallerInRole( "koku-role-customer-admin" ) ) {
      customerDAO.updateCustomer(customer);
    } else {
      customerDAO.updateCustomerElectronicContacts(customer);
    }
  }

  @Override
  public void delete(String pic) {
    verifyUserRole();    
    customerDAO.deleteCustomer(pic);
  }

  @Override
  public Collection<Customer> query(CustomerQueryCriteria c) {
    return customerDAO.queryCustomers(c);
  }
  
  /**
   * Verifies user role
   * 
   * @param c
   * @throws KoKuNotAuthorizedException if user is not in correct role
   */
  private void verifyUserRole() {
    if ( !ctx.isCallerInRole("koku-role-customer-admin") ) {
        throw new KoKuNotAuthorizedException( CustomerServiceErrorCode.UNAUTHORIZED.getValue(), 
            CustomerServiceErrorCode.UNAUTHORIZED.getDescription() );   
    }
  }
}

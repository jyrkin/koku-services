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

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fi.koku.KoKuFaultException;
import fi.koku.services.entity.customer.v1.CustomerType;
import fi.koku.services.utility.portal.v1.PortalUserAllType;
import fi.koku.services.utility.portal.v1.PortalUserPicQueryParamType;
import fi.koku.services.utility.portal.v1.PortalUserUpdateType;

/**
 * PortalUserService related data access facilities implementation. Implements
 * PortalUserServiceDAO interface.
 * 
 * @author hekkata
 */
@Stateless
public class PortalUserServiceDAOBean implements PortalUserServiceDAO {

  private Logger logger = LoggerFactory.getLogger(PortalUserServiceDAOBean.class);
  private PortalUserConverter conv = new PortalUserConverter();
  
  @PersistenceContext
  private EntityManager em;
  
  @EJB
  private PortalCustomer customerBean;

  /**
   * Instantiates a new portal user service dao bean.
   */
  public PortalUserServiceDAOBean() {
  }

  
  
  @Override
  public void insertPortalUser(PortalUser user, CustomerType cust, String pic) {
    CustomerType c = null;
    
    // check for existing user    
    if (findExistingUser(user.getUserName())) {
      PortalUserServiceErrorCode errorCode = PortalUserServiceErrorCode.PORTAL_USER_ALREADY_EXISTS;
      throw new KoKuFaultException(errorCode.getValue(), errorCode.getDescription());      
    } else {
      //get customer id for PortalUser
      try {
        //Check if customer already exists
        c = customerBean.getCustomer(pic);
      } catch (Exception e) {
        logger.error("Failed to find existing customer with given pic.", e);
      }
      if(c==null){
        //Customer not found with given pic, let's try to add new customer
        String custId = customerBean.addCustomer(cust, pic);
        logger.debug("added new customer to customerservice. Id="+custId);
      }
      
      user.setCustomerId(pic);
      em.persist(user);
    }
  }

  @Override
  public void updatePortalUser(PortalUserUpdateType user) {
        
    PortalUserPasswordEncryption encrypt = new PortalUserPasswordEncryption();
    // find portal user
    PortalUser portalUser = findPortalUser(user.getUserName());
    // and verify user's password
    boolean authenticated = encrypt
        .authenticateUser(user.getPassword(), portalUser.getPassword(), portalUser.getSalt());
        
    if (authenticated) {
      
      CustomerType cust = customerBean.getCustomer(user.getPic());
      CustomerType updateCust = conv.UpdateTypeToCustomerType(user);
      //update customer type
      cust = conv.updateCustomer(cust, updateCust);      
      
      portalUser = conv.updateFromWsType(user, portalUser);
      em.merge(portalUser);
      em.flush();
      
      customerBean.updateCustomer(cust, cust.getHenkiloTunnus());
      
      logger.debug("updatePortalUser: data updated successfully for user" + user.getUserName());
    }
    else {
      PortalUserServiceErrorCode errorCode = PortalUserServiceErrorCode.UNAUTHORIZED;
      throw new KoKuFaultException(errorCode.getValue(), errorCode.getDescription());
      }
    
    
    return;
  }

  /** 
   * Authenticates portal user
   * 
   * Compares given portal username and password parameters in PortalUserPicQueryParamType
   * to database data. If they match authentication is success else failure.
   * 
   * If PortalUserPicQueryParamType has also nonEmpty pic (hetu) service tries to get
   * customer data from customer service. Only PortalUser-information is returned if pic is null or Empty.
   * NOTE: This is because PortalCustomLoginModule can not necessarily access pic information, but 
   * username/password is enough validation at that point. Strong authentication is anyway done in 
   * portlet-filter-level.
   * 
   * 
   */
  @Override
  public PortalUserAllType authenticatePortalUser(PortalUserPicQueryParamType user) {

    PortalUserAllType pt = new PortalUserAllType();
    PortalUserPasswordEncryption encrypt = new PortalUserPasswordEncryption();

    // find portal user
    PortalUser portalUser = findPortalUser(user.getUserName());
    
    
    
    //set user exists
    boolean userExists = true;
    // and verify user's password
    boolean authenticated = encrypt
        .authenticateUser(user.getPassword(), portalUser.getPassword(), portalUser.getSalt());
        
    if (!authenticated && userExists ) {
      updateWrongPasswordCount(authenticated, portalUser);
      PortalUserServiceErrorCode errorCode = PortalUserServiceErrorCode.UNAUTHORIZED;
      throw new KoKuFaultException(errorCode.getValue(), errorCode.getDescription());
    }
    else if (!authenticated && !userExists ) {
      PortalUserServiceErrorCode errorCode = PortalUserServiceErrorCode.PORTAL_USER_NOT_FOUND;
      throw new KoKuFaultException(errorCode.getValue(), errorCode.getDescription());
    }
    else if (authenticated && userExists ){
      updateWrongPasswordCount(authenticated, portalUser);
      
      CustomerType cust = customerBean.getCustomer(portalUser.getCustomerId());
      pt = conv.toWsType(portalUser, cust);
      logger.debug("authenticatePortalUser: authenticated user: " + user.getUserName());
    }

    return pt;
  }

  private boolean isNotEmpty(String str){
    if(str!=null && !"".equals(str)) {
      return true;
    }else{
      return false;
    }
  }
  
//  @Override
//  public PortalUserAllType authenticatePortalUser(PortalUserPicQueryParamType user) {
//
//    PortalUserAllType pt = new PortalUserAllType();
//
//    PortalUserPasswordEncryption encrypt = new PortalUserPasswordEncryption();
//    // find portal user
//    PortalUser portalUser = findPortalUser(user.getUserName());
//    CustomerType cust = customerBean.getCustomer(user.getPic());
//    //set user exists
//    boolean userExists = true;
//    // and verify user's password
//    boolean authenticated = encrypt
//        .authenticateUser(user.getPassword(), portalUser.getPassword(), portalUser.getSalt());
//        
//    if (!authenticated && userExists ) {
//      updateWrongPasswordCount(authenticated, portalUser);
//      PortalUserServiceErrorCode errorCode = PortalUserServiceErrorCode.UNAUTHORIZED;
//      throw new KoKuFaultException(errorCode.getValue(), errorCode.getDescription());
//    }
//    else if (!authenticated && !userExists ) {
//      PortalUserServiceErrorCode errorCode = PortalUserServiceErrorCode.PORTAL_USER_NOT_FOUND;
//      throw new KoKuFaultException(errorCode.getValue(), errorCode.getDescription());
//    }
//    else if (authenticated && userExists ){
//      updateWrongPasswordCount(authenticated, portalUser);
//      pt = conv.toWsType(portalUser, cust);
//      logger.debug("authenticatePortalUser: authenticated user: " + user.getUserName());
//    }
//
//    return pt;
//  }

  
  
  /** 
   * Returns Portal user that has given username or pic (combines two services).
   * Password is currently ignored, because we don't want to store in session. This service-level
   * trusts that calling clients have already authenticated user and gives a simple access to data.
   *  
   * 
   */
  @Override
  public PortalUserAllType getUserByPic(PortalUserPicQueryParamType pic) {
    
    String username = pic.getUserName();
    String userpic = pic.getPic();
    PortalUserAllType picUser = new PortalUserAllType();
    PortalUser portalUser = null;
    
    if( isNotEmpty(username) ){
      portalUser = findPortalUser(username);
    }else{
        portalUser = findPortalUserByCustomerPic(userpic);
    }
    CustomerType cust = customerBean.getCustomer(portalUser.getCustomerId());
    picUser = conv.toWsType(portalUser, cust);
    
    return picUser;    
  }

  
//  @Override
//  public PortalUserAllType getUserByPic(PortalUserPicQueryParamType pic) {
//    
//    PortalUserAllType picUser = new PortalUserAllType();
//    
//    if (authenticateUser(pic)) {
//       CustomerType cust = customerBean.getCustomer(pic.getPic());
//       PortalUser portalUser = findPortalUser(pic.getUserName());
//       picUser = conv.toWsType(portalUser, cust);
//    }
//    else {
//      PortalUserServiceErrorCode errorCode = PortalUserServiceErrorCode.UNAUTHORIZED;
//      throw new KoKuFaultException(errorCode.getValue(), errorCode.getDescription());
//      }
//    
//    return picUser;    
//  }

  
  @Override
  public void disablePortalUser(PortalUserPicQueryParamType user) {
        
    if (authenticateUser(user)) {
      PortalUser userFromDb = findPortalUser(user.getUserName());
      userFromDb.setDisabled(1);
      em.merge(userFromDb);
      em.flush();      
    }
    else {
    PortalUserServiceErrorCode errorCode = PortalUserServiceErrorCode.UNAUTHORIZED;
    throw new KoKuFaultException(errorCode.getValue(), errorCode.getDescription());
    }
    
    return;
  }

  @Override
  public void removePortalUser(PortalUserPicQueryParamType pic) {
        
    if (authenticateUser(pic)) {
      //remove from portal_user-table
      PortalUser userFromDb = findPortalUser(pic.getUserName());
      em.remove(userFromDb);
      em.flush();
      //remove from customer
      customerBean.deleteCustomer(pic.getPic());
    }
    else {
      PortalUserServiceErrorCode errorCode = PortalUserServiceErrorCode.UNAUTHORIZED;
      throw new KoKuFaultException(errorCode.getValue(), errorCode.getDescription());
      }
    return;
  }
  
  private boolean authenticateUser(PortalUserPicQueryParamType user)
  {
   
  boolean authenticated = false;  
  PortalUserPasswordEncryption encrypt = new PortalUserPasswordEncryption();
  // find portal user
  PortalUser portalUser = findPortalUser(user.getUserName());
  // and verify user's password
  authenticated = encrypt
      .authenticateUser(user.getPassword(), portalUser.getPassword(), portalUser.getSalt());
  
  return authenticated;
  }
    
  private PortalUser findPortalUser(String userName) {
    Query q = em.createNamedQuery(PortalUser.NAMED_QUERY_GET_PORTAL_USER_BY_USERNAME);
    q.setParameter("userName", userName);
    logger.debug("findPortalUser: query: " + q.toString());
    try {
      return (PortalUser) q.getSingleResult();
    } catch (NoResultException e) {
      PortalUserServiceErrorCode errorCode = PortalUserServiceErrorCode.PORTAL_USER_NOT_FOUND;
      throw new KoKuFaultException(errorCode.getValue(), errorCode.getDescription(), e);
    }

  }
  
  private PortalUser findPortalUserByCustomerPic(String customerPic) {
    Query q = em.createNamedQuery(PortalUser.NAMED_QUERY_GET_PORTAL_USER_BY_CUSTOMER_PIC);
    q.setParameter("customerId", customerPic);
    logger.debug("findPortalUserByCustomerPic: query: " + q.toString());
    try {
      return (PortalUser) q.getSingleResult();
    } catch (NoResultException e) {
      PortalUserServiceErrorCode errorCode = PortalUserServiceErrorCode.PORTAL_USER_NOT_FOUND;
      throw new KoKuFaultException(errorCode.getValue(), errorCode.getDescription(), e);
    }
  }
  
  private boolean findExistingUser(String userName) {
    boolean existingUser = false;
    Query q = em.createNamedQuery(PortalUser.NAMED_QUERY_GET_PORTAL_USER_BY_USERNAME);
    q.setParameter("userName", userName);
    logger.debug("findExistingUser: query: " + q.toString());
        
    @SuppressWarnings("unchecked")
    List<PortalUser> results = (List<PortalUser>) q.getResultList();
    for (PortalUser user : results) {
      if (user.getUserName().equals(userName)) {
        existingUser = true;
        logger.debug("findExistingUser: user already exists");
        PortalUserServiceErrorCode errorCode = PortalUserServiceErrorCode.PORTAL_USER_ALREADY_EXISTS;
        throw new KoKuFaultException(errorCode.getValue(), errorCode.getDescription());
      }
    }
    
    return existingUser;
  }
  
  private void updateWrongPasswordCount(boolean succeeded, PortalUser user) {
    int count = user.getWrongPasswordCount();
    if (!succeeded) {
      count += 1;
      user.setWrongPasswordCount(count);
      em.merge(user);
      em.flush();
      logger.debug("updateWrongPasswordCount: count update to: " + count);
    } else if (succeeded && count > 0) {
      count -= 1;user.setWrongPasswordCount(count);
      em.merge(user);
      em.flush();
      logger.debug("updateWrongPasswordCount: count update to: " + count);
    }    
  }
     
}
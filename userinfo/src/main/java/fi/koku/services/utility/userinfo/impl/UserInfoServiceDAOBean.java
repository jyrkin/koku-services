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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fi.koku.KoKuFaultException;
import fi.koku.services.utility.user.v1.ContactInfoUpdateType;
import fi.koku.services.utility.user.v1.PortalUserQueryParamType;
import fi.koku.services.utility.user.v1.PortalUserUpdateType;
import fi.koku.services.utility.user.v1.UserIdsQueryParamType;
import fi.koku.services.utility.user.v1.UserPicsQueryParamType;
import fi.koku.services.utility.user.v1.UserType;
import fi.koku.services.utility.user.v1.UsersType;

/**
 * UserInfoService related data access facilities implementation. Implements
 * UserInfoServiceDAO interface.
 * 
 * @author hekkata
 */
@Stateless(name = "UserInfoServiceDBAccess", mappedName = "UserInfoServiceDBAccess")
public class UserInfoServiceDAOBean implements UserInfoServiceDAO {

  private Logger logger = LoggerFactory.getLogger(UserInfoServiceDAOBean.class);

  @PersistenceContext
  private EntityManager em;

  public UserInfoServiceDAOBean() {
  }

  @Override
  public void insertPortalUser(PortalUser user) {
    // check for existing user
    if (findExistingUser(user.getUserName())) {
      return;
    } else {
      em.persist(user);
    }
  }

  @Override
  public boolean updatePortalUser(PortalUserUpdateType user) {

    boolean updateSuccess = false;

    PortalUserQueryParamType userQuery = new PortalUserQueryParamType();
    userQuery.setUserName(user.getUserName());
    userQuery.setPassword(user.getPassword());
    boolean authenticated = authenticatePortalUser(userQuery);

    if (authenticated) {
      PortalUser userFromDb = findPortalUser(user.getUserName());
      userFromDb = updateFromWsType(user, userFromDb);
      em.merge(userFromDb);
      em.flush();
      updateSuccess = true;
      logger.debug("updatePortalUser: data updated successfully for user" + user.getUserName());
    }

    return updateSuccess;
  }

  @Override
  public boolean authenticatePortalUser(PortalUserQueryParamType user) {

    UserInfoPasswordEncryption encrypt = new UserInfoPasswordEncryption();
    // find portal user
    PortalUser portalUser = findPortalUser(user.getUserName());
    // and verify user's password
    boolean authenticated = encrypt
        .authenticateUser(user.getPassword(), portalUser.getPassword(), portalUser.getSalt());

    if (!authenticated) {
      updateWrongPasswordCount(authenticated, portalUser);
      UserInfoServiceErrorCode errorCode = UserInfoServiceErrorCode.UNAUTHORIZED;
      throw new KoKuFaultException(errorCode.getValue(), errorCode.getDescription());
    } else {
      updateWrongPasswordCount(authenticated, portalUser);
      logger.debug("authenticatePortalUser: authenticated user: " + user.getUserName());
    }

    return authenticated;
  }

  @Override
  public UsersType getUsersByIds(UserIdsQueryParamType ids) {
    
    //TODO: make this happens with named query
    
    UsersType idUser = new UsersType();
            
    for(String user : ids.getId())
    {
      PortalUser puser = findPortalUser(user);
      
      UserType newUser = new UserType();
      
      newUser.setFirstname(puser.getFirstNames());
      newUser.setLastname(puser.getSurName());
      newUser.setUserId(puser.getUserName());
      newUser.setPic(puser.getPic());
      
      List<PortalUserContactInfo> infos = puser.getPortalUserContactInfo();
      for(PortalUserContactInfo info : infos )
      {
        newUser.setEmail(info.getEmail());
      }
      idUser.getUser().add(newUser);      
          
    }
    
    return idUser;
  }

  @Override
  public UsersType getUsersByPics(UserPicsQueryParamType pics) {
        
//TODO: make this happens with named query
    
    UsersType idUser = new UsersType();
            
    for(String user : pics.getPic())
    {
      PortalUser puser = findPortalUserByPic(user);
      
      UserType newUser = new UserType();
      
      newUser.setFirstname(puser.getFirstNames());
      newUser.setLastname(puser.getSurName());
      newUser.setUserId(puser.getUserName());
      newUser.setPic(puser.getPic());
      
      List<PortalUserContactInfo> infos = puser.getPortalUserContactInfo();
      for(PortalUserContactInfo info : infos )
      {
        newUser.setEmail(info.getEmail());
      }
      idUser.getUser().add(newUser);      
          
    }
    
    return idUser;

  }
  
  private PortalUser findPortalUser(String userName) {
    Query q = em.createNamedQuery(PortalUser.NAMED_QUERY_GET_PORTAL_USER_BY_USERNAME);
    q.setParameter("userName", userName);
    logger.debug("findPortalUser: query: " + q.toString());
    try {
      return (PortalUser) q.getSingleResult();
    } catch (NoResultException e) {
      UserInfoServiceErrorCode errorCode = UserInfoServiceErrorCode.PORTAL_USER_NOT_FOUND;
      throw new KoKuFaultException(errorCode.getValue(), errorCode.getDescription(), e);
    }

  }
  
  private PortalUser findPortalUserByPic(String pic) {
    Query q = em.createNamedQuery(PortalUser.NAMED_QUERY_GET_PORTAL_USER_BY_PIC);
    q.setParameter("pic", pic);
    logger.debug("findPortalUserByPic: query: " + q.toString());
    try {
      return (PortalUser) q.getSingleResult();
    } catch (NoResultException e) {
      UserInfoServiceErrorCode errorCode = UserInfoServiceErrorCode.PORTAL_USER_NOT_FOUND;
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
        UserInfoServiceErrorCode errorCode = UserInfoServiceErrorCode.PORTAL_USER_ALREADY_EXISTS;
        throw new KoKuFaultException(errorCode.getValue(), errorCode.getDescription());
      }
    }
    return existingUser;
  }

  private PortalUser updateFromWsType(PortalUserUpdateType pu, PortalUser p) {

    UserInfoPasswordEncryption encrypt = new UserInfoPasswordEncryption();

    String salt = encrypt.getSalt();

    p.setModificationTime(new Date());
    p.setLastLoginTime(new Date());

    if (pu.getFirstNames() != null) {
      p.setFirstNames(pu.getFirstNames());
      logger.debug("updatePortalUser: first names update to: " + p.getFirstNames());
    }
    if (pu.getSurName() != null) {
      p.setSurName(pu.getSurName());
      logger.debug("updatePortalUser: surname update to: " + p.getSurName());
    }
    if (pu.getNotificationMethod() != null) {
      p.setNotificationMethod(pu.getNotificationMethod());
      logger.debug("updatePortalUser: notification mehtod update to: " + p.getNotificationMethod());
    }
    if (pu.getNewPassword() != null) {
      p.setSalt(salt);
      p.setPassword(encrypt.getEncryptedPassword(pu.getNewPassword(), salt));
      p.setPasswordChanged(new Date());
      logger.debug("updatePortalUser: password update");
    }
    if (pu.getContactInfoUpdates() != null) {
      List<PortalUserContactInfo> info = p.getPortalUserContactInfo();
      int i = 0;
      for (ContactInfoUpdateType ct : pu.getContactInfoUpdates()) {
        PortalUserContactInfo pc = info.get(i);
        i++;

        if (ct.getEmail() != null) {
          pc.setEmail(ct.getEmail());
          logger.debug("updatePortalUser: e-mail update to: " + pc.getEmail());
        }
        if (ct.getPhoneNumber() != null) {
          pc.setPhoneNumber(ct.getPhoneNumber());
          logger.debug("updatePortalUser: phone number update to: " + pc.getPhoneNumber());
        }
        if (ct.getCity() != null) {
          pc.setCity(ct.getCity());
          logger.debug("updatePortalUser: city update to: " + pc.getCity());
        }
        if (ct.getCountry() != null) {
          pc.setCountry(ct.getCountry());
          logger.debug("updatePortalUser: country update to: " + pc.getCountry());
        }
        if (ct.getPostalCode() != null) {
          pc.setPostalCode(ct.getPostalCode());
          logger.debug("updatePortalUser: postal code update to: " + pc.getPostalCode());
        }
        if (ct.getStreetAddress() != null) {
          pc.setStreetAddress(ct.getStreetAddress());
          logger.debug("updatePortalUser: street address update to: " + pc.getStreetAddress());
        }
      }
    }

    return p;
  }

  private void updateWrongPasswordCount(boolean succeeded, PortalUser user) {
    int count = user.getWrongPasswordCount();
    if (!succeeded) {
      count += 1;
      logger.debug("updateWrongPasswordCount: count update to: " + count);
    } else if (succeeded && count > 0) {
      count -= 1;
      logger.debug("updateWrongPasswordCount: count update to: " + count);
    }
    user.setWrongPasswordCount(count);
    em.merge(user);
    em.flush();
  }

}
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

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * Portal User entity.
 * 
 * @author hekkata
 */
@Entity
@NamedQueries({
    @NamedQuery(name = PortalUser.NAMED_QUERY_GET_PORTAL_USER_BY_USERNAME, query = "FROM PortalUser p WHERE p.userName =:userName ") })
@Table(name = "portal_user")
public class PortalUser {

  public static final String NAMED_QUERY_GET_PORTAL_USER_BY_USERNAME = "getPortalUserByUserName";

  @Id
  @GeneratedValue
  private int id;

  @JoinColumn(name="customer_id", nullable=false)
  private String customerId;
    
  @Column(name = "username", nullable = false, unique = true)
  private String userName;
  
  @Column(name = "notification_method", nullable = false)
  private int notificationMethod;
  
  @Column(name = "creation_time", nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date creationTime;
  
  @Column(name = "modification_time", nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date modificationTime;

  @Column(name = "last_login_time")
  @Temporal(TemporalType.TIMESTAMP)
  private Date lastLoginTime;
  
  @Column(name = "disabled")
  private int disabled;
  
  @Column(name = "locked_time")
  @Temporal(TemporalType.TIMESTAMP)
  private Date lockedTime;

  @Column(name = "password", nullable = false)
  private String password;

  @Column(name = "password_changed")
  @Temporal(TemporalType.TIMESTAMP)
  private Date passwordChanged;

  @Column(name = "salt", nullable = false)
  private String salt;
 
  @Column(name = "wrong_password_count", nullable = false)
  private int wrongPasswordCount;
  
  public int getId() {
    return id;
  }

  /**
   * Gets the modification time.
   *
   * @return the modification time
   */
  public Date getModificationTime() {
    return modificationTime;
  }

  /**
   * Sets the modification time.
   *
   * @param modificationTime the new modification time
   */
  public void setModificationTime(Date modificationTime) {
    this.modificationTime = modificationTime;
  }
  
  /**
   * Gets the creation time.
   *
   * @return the creation time
   */
  public Date getCreationTime() {
    return creationTime;
  }

  /**
   * Sets the creation time.
   *
   * @param creationTime the new creation time
   */
  public void setCreationTime(Date creationTime) {
    this.creationTime = creationTime;
  }

  /**
   * Gets the disabled.
   *
   * @return the disabled
   */
  public int getDisabled() {
    return disabled;
  }

  /**
   * Sets the disabled.
   *
   * @param disabled the new disabled
   */
  public void setDisabled(int disabled) {
    this.disabled = disabled;
  }

  /**
   * Gets the last login time.
   *
   * @return the last login time
   */
  public Date getLastLoginTime() {
    return lastLoginTime;
  }

  /**
   * Sets the last login time.
   *
   * @param lastLoginTime the new last login time
   */
  public void setLastLoginTime(Date lastLoginTime) {
    this.lastLoginTime = lastLoginTime;
  }

  /**
   * Gets the locked time.
   *
   * @return the locked time
   */
  public Date getLockedTime() {
    return lockedTime;
  }

  /**
   * Sets the locked time.
   *
   * @param lockedTime the new locked time
   */
  public void setLockedTime(Date lockedTime) {
    this.lockedTime = lockedTime;
  }

  /**
   * Gets the notification method.
   *
   * @return the notification method
   */
  public int getNotificationMethod() {
    return notificationMethod;
  }

  /**
   * Sets the notification method.
   *
   * @param notificationMethod the new notification method
   */
  public void setNotificationMethod(int notificationMethod) {
    this.notificationMethod = notificationMethod;
  }

  /**
   * Gets the password.
   *
   * @return the password
   */
  public String getPassword() {
    return password;
  }

  /**
   * Sets the password.
   *
   * @param passWord the new password
   */
  public void setPassword(String passWord) {
    this.password = passWord;
  }

  /**
   * Gets the password changed.
   *
   * @return the password changed
   */
  public Date getPasswordChanged() {
    return passwordChanged;
  }

  /**
   * Sets the password changed.
   *
   * @param passwordChanged the new password changed
   */
  public void setPasswordChanged(Date passwordChanged) {
    this.passwordChanged = passwordChanged;
  }

  /**
   * Gets the salt.
   *
   * @return the salt
   */
  public String getSalt() {
    return salt;
  }

  /**
   * Sets the salt.
   *
   * @param salt the new salt
   */
  public void setSalt(String salt) {
    this.salt = salt;
  }

  /**
   * Gets the user name.
   *
   * @return the user name
   */
  public String getUserName() {
    return userName;
  }

  /**
   * Sets the user name.
   *
   * @param userName the new user name
   */
  public void setUserName(String userName) {
    this.userName = userName;
  }

  /**
   * Gets the wrong password count.
   *
   * @return the wrong password count
   */
  public int getWrongPasswordCount() {
    return wrongPasswordCount;
  }

  /**
   * Sets the wrong password count.
   *
   * @param wrongPasswordCount the new wrong password count
   */
  public void setWrongPasswordCount(int wrongPasswordCount) {
    this.wrongPasswordCount = wrongPasswordCount;
  }

  /**
   * Gets the customer id.
   *
   * @return the customer id
   */
  public String getCustomerId() {
    return customerId;
  }

  /**
   * Sets the customer id.
   *
   * @param customerId the new customer id
   */
  public void setCustomerId(String customerId) {
    this.customerId = customerId;
  }
  
  /**
   * Sets the portaluser.
   *
   * @param portalUser the new portaluser
   */
  public void setPortaluser(PortalUser portalUser) {

    setCustomerId(portalUser.getCustomerId());
    setUserName(portalUser.getUserName());
    setNotificationMethod(portalUser.getNotificationMethod());
    setCreationTime(portalUser.getCreationTime());
    setModificationTime(portalUser.getModificationTime());
    setLastLoginTime(portalUser.getLastLoginTime());
    setDisabled(portalUser.getDisabled());    
    setLockedTime(portalUser.getLockedTime());    
    setPassword(portalUser.getPassword());
    setPasswordChanged(portalUser.getPasswordChanged());
    setSalt(portalUser.getSalt());    
    setWrongPasswordCount(portalUser.getWrongPasswordCount());
  }

}

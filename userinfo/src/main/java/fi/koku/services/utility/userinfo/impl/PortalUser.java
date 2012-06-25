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

import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.CascadeType.REMOVE;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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
    @NamedQuery(name = PortalUser.NAMED_QUERY_GET_PORTAL_USER_BY_PIC, query = "FROM PortalUser p WHERE p.pic =:pic "),
    @NamedQuery(name = PortalUser.NAMED_QUERY_GET_PORTAL_USER_BY_USERNAME, query = "FROM PortalUser p WHERE p.userName =:userName ") })
@Table(name = "portal_user")
public class PortalUser {

  public static final String NAMED_QUERY_GET_PORTAL_USER_BY_PIC = "getPortalUserByPic";
  public static final String NAMED_QUERY_GET_PORTAL_USER_BY_USERNAME = "getPortalUserByUserName";

  @Id
  @GeneratedValue
  private int id;

  @Column(name = "first_names", nullable = false)
  private String firstNames;

  @Column(name = "modification_time", nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date modificationTime;

  @Column(name = "pic", nullable = false, unique = true)
  private String pic;

  @Column(name = "surname")
  private String surName;

  @Column(name = "creation_time", nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date creationTime;

  @Column(name = "disabled")
  private int disabled;

  @Column(name = "last_login_time")
  @Temporal(TemporalType.TIMESTAMP)
  private Date lastLoginTime;

  @Column(name = "locked_time")
  @Temporal(TemporalType.TIMESTAMP)
  private Date lockedTime;

  @Column(name = "notification_method", nullable = false)
  private int notificationMethod;

  @Column(name = "password", nullable = false)
  private String password;

  @Column(name = "password_changed")
  @Temporal(TemporalType.TIMESTAMP)
  private Date passwordChanged;

  @Column(name = "salt", nullable = false)
  private String salt;

  @Column(name = "username", nullable = false, unique = true)
  private String userName;

  @Column(name = "wrong_password_count", nullable = false)
  private int wrongPasswordCount;

  @OneToMany(mappedBy = "portalUser", fetch=FetchType.EAGER, cascade = { PERSIST, REMOVE, MERGE })
  private List<PortalUserContactInfo> portalUserContactInfo;

  public List<PortalUserContactInfo> getPortalUserContactInfo() {
    return portalUserContactInfo;
  }

  public PortalUser() {
    portalUserContactInfo = new ArrayList<PortalUserContactInfo>();
  }

  public int getId() {
    return id;
  }

  public String getFirstNames() {
    return firstNames;
  }

  public void setFirstNames(String firstNames) {
    this.firstNames = firstNames;
  }

  public Date getModificationTime() {
    return modificationTime;
  }

  public void setModificationTime(Date modificationTime) {
    this.modificationTime = modificationTime;
  }

  public String getPic() {
    return pic;
  }

  public void setPic(String pic) {
    this.pic = pic;
  }

  public String getSurName() {
    return surName;
  }

  public void setSurName(String surName) {
    this.surName = surName;
  }

  public Date getCreationTime() {
    return creationTime;
  }

  public void setCreationTime(Date creationTime) {
    this.creationTime = creationTime;
  }

  public int getDisabled() {
    return disabled;
  }

  public void setDisabled(int disabled) {
    this.disabled = disabled;
  }

  public Date getLastLoginTime() {
    return lastLoginTime;
  }

  public void setLastLoginTime(Date lastLoginTime) {
    this.lastLoginTime = lastLoginTime;
  }

  public Date getLockedTime() {
    return lockedTime;
  }

  public void setLockedTime(Date lockedTime) {
    this.lockedTime = lockedTime;
  }

  public int getNotificationMethod() {
    return notificationMethod;
  }

  public void setNotificationMethod(int notificationMethod) {
    this.notificationMethod = notificationMethod;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String passWord) {
    this.password = passWord;
  }

  public Date getPasswordChanged() {
    return passwordChanged;
  }

  public void setPasswordChanged(Date passwordChanged) {
    this.passwordChanged = passwordChanged;
  }

  public String getSalt() {
    return salt;
  }

  public void setSalt(String salt) {
    this.salt = salt;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public int getWrongPasswordCount() {
    return wrongPasswordCount;
  }

  public void setWrongPasswordCount(int wrongPasswordCount) {
    this.wrongPasswordCount = wrongPasswordCount;
  }

  public void setPortaluser(PortalUser portalUser) {

    setFirstNames(portalUser.getFirstNames());
    setModificationTime(portalUser.getModificationTime());
    setPic(portalUser.getPic());
    setSurName(portalUser.getSurName());
    setCreationTime(portalUser.getCreationTime());
    setDisabled(portalUser.getDisabled());
    setLastLoginTime(portalUser.getLastLoginTime());
    setLockedTime(portalUser.getLockedTime());
    setNotificationMethod(portalUser.getNotificationMethod());
    setPassword(portalUser.getPassword());
    setPasswordChanged(portalUser.getPasswordChanged());
    setSalt(portalUser.getSalt());
    setUserName(portalUser.getUserName());
    setWrongPasswordCount(portalUser.getWrongPasswordCount());
  }
}

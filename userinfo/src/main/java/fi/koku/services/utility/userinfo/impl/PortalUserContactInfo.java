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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Portal User Contact Info entity.
 * 
 * @author hekkata
 */
@Entity
@Table(name = "portal_user_contact_info")
public class PortalUserContactInfo {
  
  @Id
  @GeneratedValue
  private int id;
  
  @Column(name="email", nullable=false)
  private String email;

  @Column(name="phone_number")
  private String phoneNumber;

  @Column(name="city", nullable=false)
  private String city;
  
  @Column(name="country", nullable=false)
  private String country;
  
  @Column(name="postal_code", nullable=false)
  private int postalCode;
  
  @Column(name="street_address", nullable=false)
  private String streetAddress;
  
  @ManyToOne(fetch=FetchType.EAGER)
  @JoinColumn(name="portal_user_id", nullable=false)
  private PortalUser portalUser;
  
  public PortalUserContactInfo() {
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }
  
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
  
  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }
  
  public PortalUser getPortalUser() {
    return portalUser;
  }

  public void setPortalUser(PortalUser portalUser) {
    this.portalUser = portalUser;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public int getPostalCode() {
    return postalCode;
  }

  public void setPostalCode(int postalCode) {
    this.postalCode = postalCode;
  }

  public String getStreetAddress() {
    return streetAddress;
  }

  public void setStreetAddress(String streetAddress) {
    this.streetAddress = streetAddress;
  }
  
  
}
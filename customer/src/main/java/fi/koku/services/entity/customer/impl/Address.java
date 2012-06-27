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

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Address entity.
 * 
 * @author Ixonos / aspluma
 */
@Entity
@Table(name = "address")
public class Address {
  
  @Id
  @GeneratedValue
  private Long id;

  @Column(nullable=false)
  private String type;
  
  @Column(name="street_address")
  private String streetAddress;

  @Column(name="postal_district")
  private String postalDistrict;

  @Column(name="postal_code")
  private String postalCode;

  @Column(name="po_box")
  private String poBox;

  @Column(name="country_code")
  private String countryCode;

  @Column(name="valid_from")
  @Temporal(TemporalType.DATE)
  private Date validFrom;

  @Column(name="valid_to")
  @Temporal(TemporalType.DATE)
  private Date validTo;

  @ManyToOne
  @JoinColumn(name="customer_id", nullable=false)
  private Customer customer;
  
  /** 
   * Class constructor.
   */
  public Address() {
  }

  /**
   * @return
   */
  public Long getId() {
    return id;
  }

  /**
   * @param id
   */
  protected void setId(Long id) {
    this.id = id;
  }

  /**
   * @return
   */
  public String getType() {
    return type;
  }

  /**
   * @param type
   */
  public void setType(String type) {
    this.type = type;
  }

  /**
   * @return
   */
  public String getStreetAddress() {
    return streetAddress;
  }

  /**
   * @param streetAddress
   */
  public void setStreetAddress(String streetAddress) {
    this.streetAddress = streetAddress;
  }

  /**
   * @return
   */
  public String getPostalDistrict() {
    return postalDistrict;
  }

  /**
   * @param postalDistrict
   */
  public void setPostalDistrict(String postalDistrict) {
    this.postalDistrict = postalDistrict;
  }

  /**
   * @return
   */
  public String getPostalCode() {
    return postalCode;
  }

  /**
   * @param postalCode
   */
  public void setPostalCode(String postalCode) {
    this.postalCode = postalCode;
  }

  /**
   * @return
   */
  public String getPoBox() {
    return poBox;
  }

  /**
   * @param poBox
   */
  public void setPoBox(String poBox) {
    this.poBox = poBox;
  }

  /**
   * @return
   */
  public String getCountryCode() {
    return countryCode;
  }

  /**
   * @param countryCode
   */
  public void setCountryCode(String countryCode) {
    this.countryCode = countryCode;
  }

  /**
   * @return
   */
  public Date getValidFrom() {
    return validFrom;
  }

  /**
   * @param validFrom
   */
  public void setValidFrom(Date validFrom) {
    this.validFrom = validFrom;
  }

  /**
   * @return
   */
  public Date getValidTo() {
    return validTo;
  }

  /**
   * @param validTo
   */
  public void setValidTo(Date validTo) {
    this.validTo = validTo;
  }

  /**
   * @return
   */
  public Customer getCustomer() {
    return customer;
  }

  /**
   * @param customer
   */
  public void setCustomer(Customer customer) {
    this.customer = customer;
  }
  
  /**
   * @param a
   */
  public void setAddress(Address a) {
    setType(a.getType());
    setStreetAddress(a.getStreetAddress());
    setPostalDistrict(a.getPostalDistrict());
    setPostalCode(a.getPostalCode());
    setPoBox(a.getPoBox());
    setCountryCode(a.getCountryCode());
    setValidFrom(a.getValidFrom());
    setValidTo(a.getValidTo());
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((type == null) ? 0 : type.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Address other = (Address) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    if (type == null) {
      if (other.type != null)
        return false;
    } else if (!type.equals(other.type))
      return false;
    return true;
  }

}

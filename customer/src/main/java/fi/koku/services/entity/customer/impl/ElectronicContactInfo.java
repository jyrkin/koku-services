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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Electronic contact info entity.
 * 
 * @author aspluma
 */
@Entity
@Table(name = "electronic_contact_info")
public class ElectronicContactInfo {
  
  @Id
  @GeneratedValue
  private Long id;

  @Column(nullable=false)
  private String type;
  
  @Column(nullable=false)
  private String contact;
  
  @ManyToOne
  @JoinColumn(name="customer_id")
  private Customer customer;

  
  /** 
   * Class constructor.
   */
  public ElectronicContactInfo() {
  }

  /**
   * Gets the id.
   *
   * @return the id
   */
  public Long getId() {
    return id;
  }

  /**
   * Sets the id.
   *
   * @param id the new id
   */
  protected void setId(Long id) {
    this.id = id;
  }

  /**
   * Gets the type.
   *
   * @return the type
   */
  public String getType() {
    return type;
  }

  /**
   * Sets the type.
   *
   * @param type the new type
   */
  public void setType(String type) {
    this.type = type;
  }

  /**
   * Gets the contact.
   *
   * @return the contact
   */
  public String getContact() {
    return contact;
  }

  /**
   * Sets the contact.
   *
   * @param contact the new contact
   */
  public void setContact(String contact) {
    this.contact = contact;
  }

  /**
   * Gets the customer.
   *
   * @return the customer
   */
  public Customer getCustomer() {
    return customer;
  }

  /**
   * Sets the customer.
   *
   * @param customer the new customer
   */
  public void setCustomer(Customer customer) {
    this.customer = customer;
  }

  /**
   * Sets the electronic contact info.
   *
   * @param electronicContactInfo the new electronic contact info
   */
  public void setElectronicContactInfo(ElectronicContactInfo electronicContactInfo) {
    setType(electronicContactInfo.getType());
    setContact(electronicContactInfo.getContact());
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
    ElectronicContactInfo other = (ElectronicContactInfo) obj;
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

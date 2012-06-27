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
 * Phone number entity.
 * 
 * @author Ixonos / aspluma
 */
@Entity
@Table(name = "phone_number")
public class PhoneNumber {
  
  @Id
  @GeneratedValue
  private Long id;

  @Column(nullable=false)
  private String type;

  /** The number class. */
  @Column(name="class", nullable=false)
  private String numberClass;

  @Column(nullable=false)
  private String number;

  @ManyToOne
  @JoinColumn(name="customer_id", nullable=false)
  private Customer customer;

  
  /** 
   * Class constructor.
   */
  public PhoneNumber() {
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
   * Gets the number class.
   *
   * @return the number class
   */
  public String getNumberClass() {
    return numberClass;
  }

  /**
   * Sets the number class.
   *
   * @param numberClass the new number class
   */
  public void setNumberClass(String numberClass) {
    this.numberClass = numberClass;
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
   * Gets the number.
   *
   * @return the number
   */
  public String getNumber() {
    return number;
  }
  
  /**
   * Sets the number.
   *
   * @param number the new number
   */
  public void setNumber(String number) {
    this.number = number;
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
   * Sets the phone number.
   *
   * @param phoneNumber the new phone number
   */
  public void setPhoneNumber(PhoneNumber phoneNumber) {
    setType(phoneNumber.getType());
    setNumber(phoneNumber.getNumber());
    setNumberClass(phoneNumber.getNumberClass());
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
    PhoneNumber other = (PhoneNumber) obj;
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

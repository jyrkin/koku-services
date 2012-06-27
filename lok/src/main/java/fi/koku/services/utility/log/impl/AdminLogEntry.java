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
package fi.koku.services.utility.log.impl;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * AdminLogEntry entity, used for admin log (LOK-4). Stores the information
 * needed for one admin log entry.
 * 
 * @author makinsu
 */
@Entity
@Table(name = "log_admin")
public class AdminLogEntry {
  
  @Id
  @GeneratedValue
  @Column(name="id", unique=true, nullable=false)
  private String id; 
  
  @Column(name="timestamp")
  @Temporal(TemporalType.TIMESTAMP)
  private Date timestamp; // timestamp
  
  @Column(name="user_pic")
  private String userPic;  // pic of the user
  
  @Column(name="customer_pic")
  private String customerPic; // pic of the child
  
  private String operation;
  
  private String message;
  
  /**
   * Gets the timestamp.
   *
   * @return the timestamp
   */
  public Date getTimestamp() {
    return timestamp;
  }
  
  /**
   * Sets the timestamp.
   *
   * @param timestamp the new timestamp
   */
  public void setTimestamp(Date timestamp) {
    this.timestamp = timestamp;
  }
  
  /**
   * Gets the user pic.
   *
   * @return the user pic
   */
  public String getUserPic() {
    return userPic;
  }
  
  /**
   * Sets the user pic.
   *
   * @param userPic the new user pic
   */
  public void setUserPic(String userPic) {
    this.userPic = userPic;
  }
  
  /**
   * Gets the customer pic.
   *
   * @return the customer pic
   */
  public String getCustomerPic() {
    return customerPic;
  }
  
  /**
   * Sets the customer pic.
   *
   * @param customerPic the new customer pic
   */
  public void setCustomerPic(String customerPic) {
    this.customerPic = customerPic;
  }
 
  /**
   * Gets the id.
   *
   * @return the id
   */
  public String getId() {
    return id;
  }

  /**
   * Sets the id.
   *
   * @param id the new id
   */
  public void setId(String id) {
    this.id = id;
  }

  /**
   * Gets the operation.
   *
   * @return the operation
   */
  public String getOperation() {
    return operation;
  }

  /**
   * Sets the operation.
   *
   * @param operation the new operation
   */
  public void setOperation(String operation) {
    this.operation = operation;
  }

  /**
   * Gets the message.
   *
   * @return the message
   */
  public String getMessage() {
    return message;
  }

  /**
   * Sets the message.
   *
   * @param message the new message
   */
  public void setMessage(String message) {
    this.message = message;
  }
}

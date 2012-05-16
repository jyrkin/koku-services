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
  
  public Date getTimestamp() {
    return timestamp;
  }
  
  public void setTimestamp(Date timestamp) {
    this.timestamp = timestamp;
  }
  
  public String getUserPic() {
    return userPic;
  }
  
  public void setUserPic(String userPic) {
    this.userPic = userPic;
  }
  
  public String getCustomerPic() {
    return customerPic;
  }
  
  public void setCustomerPic(String customerPic) {
    this.customerPic = customerPic;
  }
 
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getOperation() {
    return operation;
  }

  public void setOperation(String operation) {
    this.operation = operation;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}

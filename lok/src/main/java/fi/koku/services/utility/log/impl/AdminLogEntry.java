/*
 * Copyright 2011 Ixonos Plc, Finland. All rights reserved.
 * 
 * You should have received a copy of the license text along with this program.
 * If not, please contact the copyright holder (http://www.ixonos.com/).
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
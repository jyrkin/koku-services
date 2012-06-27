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

// TODO: Auto-generated Javadoc
/**
 * LogArchiveEntry entity, used for archiving log events (LOK-2).
 * This has exactly the same structure as LogEntry.
 * 
 * @author aspluma
 * @author makinsu
 */
@Entity
@Table(name = "log_archive")
public class LogArchiveEntry {
  @Id
  @GeneratedValue
  @Column(name="id", unique=true, nullable=false)
  private Long id; // id given by the logging system 

  @Column(name="data_item_id")
  private String dataItemId; // id of the data item that was read/written/etc.
 
  @Column(name="timestamp")
  @Temporal(TemporalType.TIMESTAMP)
  private Date timestamp; // timestamp
  
  @Column(name="user_pic")
  private String userPic;  // pic of the user
  
  @Column(name="customer_pic")
  private String customerPic; //pic of the child
  
  @Column(name="data_item_type")
  private String dataItemType;  // kks.vasu, kks.4v, family info, ..
  
  private String operation;  // create, read, write, ..
  
  @Column(name="client_system_id")
  private String clientSystemId; // pyh, kks, kunpo, pegasos..

  private String message;


  /**
   * Gets the log id.
   *
   * @return the log id
   */
  public Long getLogId() {
    return id;
  }

  /**
   * Sets the log id.
   *
   * @param logId the new log id
   */
  public void setLogId(Long logId) {
    this.id = logId;
  }
  
  /**
   * Gets the timestamp.
   *
   * @return the timestamp
   */
  public Date getTimestamp() {
    return timestamp;
  }

  // format for timestamp: yyyy-mm-dd
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
   * Gets the data item type.
   *
   * @return the data item type
   */
  public String getDataItemType() {
    return dataItemType;
  }

  /**
   * Sets the data item type.
   *
   * @param dataItemType the new data item type
   */
  public void setDataItemType(String dataItemType) {
    this.dataItemType = dataItemType;
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
   * Gets the client system id.
   *
   * @return the client system id
   */
  public String getClientSystemId() {
    return clientSystemId;
  }

  /**
   * Sets the client system id.
   *
   * @param clientSystemId the new client system id
   */
  public void setClientSystemId(String clientSystemId) {
    this.clientSystemId = clientSystemId;
  }

  /**
   * Sets the message.
   *
   * @param message the new message
   */
  public void setMessage(String message) {
    this.message = message;
  }

  /**
   * Instantiates a new log archive entry.
   */
  public LogArchiveEntry(){  
  }
  
  /**
   * Instantiates a new log archive entry.
   *
   * @param message the message
   */
  public LogArchiveEntry(String message) {
    this.message = message;
  }
  
  /**
   * Gets the data item id.
   *
   * @return the data item id
   */
  public String getDataItemId() {
    return dataItemId;
  }

  /**
   * Sets the data item id.
   *
   * @param dataItemId the new data item id
   */
  public void setDataItemId(String dataItemId) {
    this.dataItemId = dataItemId;
  }
  
  /**
   * Gets the message.
   *
   * @return the message
   */
  public String getMessage() {
  
    return message;
  }
}

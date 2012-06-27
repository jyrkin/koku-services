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

/**
 * LogQueryCriteria. Class used to describe log query criteria from UI to
 * service.
 * 
 * @author makinsu
 */
public class LogQueryCriteria {

//TODO: KOKU-1187
  private String logType;
  private String customerPic;
  private String userPic;
  private String dataItemType;
  private Date startTime;
  private Date endTime;

  /**
   * Instantiates a new log query criteria.
   *
   * @param logType the log type
   * @param customerPic the customer pic
   * @param dataItemType the data item type
   * @param userPic the user pic
   * @param startTime the start time
   * @param endTime the end time
   */
  public LogQueryCriteria(String logType, String customerPic, String dataItemType, String userPic,  Date startTime, Date endTime ) {
    this.logType = logType;
    this.customerPic = customerPic;    
    this.dataItemType = dataItemType;
    this.userPic = userPic;
    this.startTime = startTime;
    this.endTime = endTime;
  }

  /**
   * Gets the log type.
   *
   * @return the log type
   */
  public String getLogType() {
    return logType;
  }

  /**
   * Sets the log type.
   *
   * @param logType the new log type
   */
  public void setLogType(String logType) {
    this.logType = logType;
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
   * Gets the start time.
   *
   * @return the start time
   */
  public Date getStartTime() {
    return startTime;
  }

  /**
   * Sets the start time.
   *
   * @param startTime the new start time
   */
  public void setStartTime(Date startTime) {
    this.startTime = startTime;
  }

  /**
   * Gets the end time.
   *
   * @return the end time
   */
  public Date getEndTime() {
    return endTime;
  }

  /**
   * Sets the end time.
   *
   * @param endTime the new end time
   */
  public void setEndTime(Date endTime) {
    this.endTime = endTime;
  }
  

}

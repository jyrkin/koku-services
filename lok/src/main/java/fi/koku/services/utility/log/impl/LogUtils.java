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

import java.util.Calendar;
import java.util.Date;

import fi.koku.KoKuFaultException;
import fi.koku.services.utility.log.v1.LogEntryType;

/**
 * Log related utility class.
 * 
 * @author makinsu
 */
public class LogUtils {
  
  /**
   * Helper method for parsing a Date to a Calendar
   * 
   * @param date
   * @return
   */
  private Calendar parseToCal(Date date) {
    Calendar cal = null;

    if (date != null) { // if it's null, return a null value
      cal = Calendar.getInstance();
      cal.setTime(date);
    }
   
    return cal;
  }
 
  /**
   * Helper method that moves a given date one day ahead.
   * 
   * @param date
   * @return
   */
  public Date moveOneDay(Date date){
    Date newday = null;
    
    if (date != null){
      // set the end time 1 day later so that everything added on the last day will be found
      Calendar endday = parseToCal(date); 
      endday.set(Calendar.DATE, endday.get(Calendar.DATE) +1);
      newday = endday.getTime();
    }
    
    return newday;  
  }
  
  /**
   * Helper method that checks if a given date is before today.
   * @param date
   * @return
   */
  public boolean isBeforeToday(Date date) {
    
    Calendar today = Calendar.getInstance();
    today.set(Calendar.HOUR_OF_DAY, 0);
    today.set(Calendar.MINUTE, 0);
    today.set(Calendar.SECOND, 0);
    today.set(Calendar.MILLISECOND, 0);

    Calendar newDate = Calendar.getInstance();
    newDate.setTime(date);

    newDate.set(Calendar.HOUR_OF_DAY, 0);
    newDate.set(Calendar.MINUTE, 0);
    newDate.set(Calendar.SECOND, 0);
    newDate.set(Calendar.MILLISECOND, 0);

    return newDate.before(today);
  }
  
  /**
   * Checks if some of the mandatory fields in log writing is null.
   * @param entry
   * @return
   */
  public boolean validateLogEntryType(LogEntryType entry, String logtype) throws KoKuFaultException {

    // In normal log, these must not be null:
    // timestamp, user_pic, operation, data_item_type,
    // client_system_id
    // In admin log, these must not be null:
    // timestamp, user_pic, operation

    boolean inputOk = false;
    LogServiceErrorCode errorCode = null;

    if (LogConstants.LOG_ADMIN.equalsIgnoreCase(logtype) || LogConstants.LOG_NORMAL.equalsIgnoreCase(logtype)) {
      if (entry.getTimestamp() == null) {
        errorCode = LogServiceErrorCode.LOG_ERROR_MISSING_TIMESTAMP;
        throw new KoKuFaultException(errorCode.getValue(), errorCode.getDescription());
      }
      if (entry.getUserPic() == null) {
        errorCode = LogServiceErrorCode.LOG_ERROR_MISSING_USERPIC;
        throw new KoKuFaultException(errorCode.getValue(), errorCode.getDescription());
      }
      if (entry.getOperation() == null) {
        errorCode = LogServiceErrorCode.LOG_ERROR_MISSING_OPERATION;
        throw new KoKuFaultException(errorCode.getValue(), errorCode.getDescription());
      }
    }

    if (LogConstants.LOG_NORMAL.equalsIgnoreCase(logtype)) {
      if (entry.getDataItemType() == null) {
        errorCode = LogServiceErrorCode.LOG_ERROR_MISSING_DATAITEMTYPE;
        throw new KoKuFaultException(errorCode.getValue(), errorCode.getDescription());
      }
      if (entry.getClientSystemId() == null) {
        errorCode = LogServiceErrorCode.LOG_ERROR_MISSING_CLIENTSYSTEMID;
        throw new KoKuFaultException(errorCode.getValue(), errorCode.getDescription());
      }
    }
    if (errorCode == null) {
      inputOk = true;
    }

    return inputOk;
  }
}

package fi.koku.services.utility.log.impl;

import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fi.koku.KoKuFaultException;
import fi.koku.services.utility.log.v1.LogEntryType;

public class LogUtils {
  private static final Logger logger = LoggerFactory.getLogger(LogUtils.class);

  
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
  //TODO: does not throw any exceptions now. The input is null-checked earlier
  public Date moveOneDay(Date date){
    Date newday = null;
    
    if(date!=null){
      // set the end time 1 day later so that everything added on the last day will be found
      Calendar endday = parseToCal(date); 
      endday.set(Calendar.DATE, endday.get(Calendar.DATE) +1);
      newday = endday.getTime();
    }
    
    return newday;  
  }
  
  
  /**
   * Checks if some of the mandatory fields in log writing is null.
   * @param entry
   * @return
   */
  public boolean logInputOk(LogEntryType entry, String logtype) throws KoKuFaultException{
        
    //In normal log, these must not be null:
    // timestamp, user_pic, operation, data_item_id, data_item_type, client_system_id
    //In admin log, these must not be null:
    // timestamp, user_pic, operation
    
    LogServiceErrorCode errorCode = null;
    
    if(LogConstants.LOG_ADMIN.equalsIgnoreCase(logtype) || LogConstants.LOG_NORMAL.equalsIgnoreCase(logtype)){
      if(entry.getTimestamp() == null){
        errorCode = LogServiceErrorCode.LOG_ERROR_MISSING_TIMESTAMP;
        throw new KoKuFaultException(errorCode.getValue(), errorCode.getDescription());
      }
      if(entry.getUserPic() == null){
        errorCode = LogServiceErrorCode.LOG_ERROR_MISSING_USERPIC;
        throw new KoKuFaultException(errorCode.getValue(), errorCode.getDescription());
      }
      if(entry.getOperation() == null){
        errorCode = LogServiceErrorCode.LOG_ERROR_MISSING_OPERATION;
        throw new KoKuFaultException(errorCode.getValue(), errorCode.getDescription());
      }
    }
    
    if(LogConstants.LOG_NORMAL.equalsIgnoreCase(logtype)){
      if(entry.getDataItemId() == null){
        errorCode = LogServiceErrorCode.LOG_ERROR_MISSING_DATAITEMID;
        throw new KoKuFaultException(errorCode.getValue(), errorCode.getDescription());
      }
      if(entry.getDataItemType() == null){
        errorCode = LogServiceErrorCode.LOG_ERROR_MISSING_DATAITEMTYPE;
        throw new KoKuFaultException(errorCode.getValue(), errorCode.getDescription());
      }
      if(entry.getClientSystemId() == null){
        errorCode = LogServiceErrorCode.LOG_ERROR_MISSING_CLIENTSYSTEMID;
        throw new KoKuFaultException(errorCode.getValue(), errorCode.getDescription());
      }
    }
    if(errorCode == null){
      return true;
    }else{
      return false;
    }
 
  }
}

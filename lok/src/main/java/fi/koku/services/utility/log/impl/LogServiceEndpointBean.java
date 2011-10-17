package fi.koku.services.utility.log.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fi.koku.KoKuFaultException;

import fi.koku.calendar.CalendarUtil;
import fi.koku.services.utility.authorizationinfo.util.AuthUtils;
import fi.koku.services.utility.authorizationinfo.v1.AuthorizationInfoService;
import fi.koku.services.utility.authorizationinfo.v1.AuthorizationInfoServiceFactory;
import fi.koku.services.utility.log.v1.ArchivalResultsType;
import fi.koku.services.utility.log.v1.AuditInfoType;
import fi.koku.services.utility.log.v1.LogArchivalParametersType;
import fi.koku.services.utility.log.v1.LogEntriesType;
import fi.koku.services.utility.log.v1.LogEntryType;
import fi.koku.services.utility.log.v1.LogQueryCriteriaType;
import fi.koku.services.utility.log.v1.LogServicePortType;
import fi.koku.services.utility.log.v1.VoidType;
import fi.koku.settings.KoKuPropertiesUtil;
import static fi.koku.services.utility.log.impl.LogServiceErrorCode.*;

/**
 * KoKu log service implementation class.
 * 
 * @author aspluma
 * @author makinsu
 */
@Stateless
@Interceptors({LogServiceFaultInterceptor.class})
@WebService(wsdlLocation = "META-INF/wsdl/logService.wsdl",
  endpointInterface = "fi.koku.services.utility.log.v1.LogServicePortType",
  targetNamespace = "http://services.koku.fi/utility/log/v1",
  portName = "logService-soap11-port",
  serviceName = "logService")
@RolesAllowed("koku-role")
public class LogServiceEndpointBean implements LogServicePortType {
  private static final Logger logger = LoggerFactory.getLogger(LogServiceEndpointBean.class);
  SimpleDateFormat df = new SimpleDateFormat(LogConstants.DATE_FORMAT);
  @Resource
  private WebServiceContext wsCtx;

  @EJB
  private LogService logService;

  private LogConverter logConverter;

  LogUtils lu = new LogUtils();
  
  private AuthorizationInfoService authInfoService;
  
  public LogServiceEndpointBean() {
    logConverter = new LogConverter();
    String uid = KoKuPropertiesUtil.get("lok.authorizationinfo.service.user.id");
    String pwd = KoKuPropertiesUtil.get("lok.authorizationinfo.service.password");
    String ep = KoKuPropertiesUtil.get("authorizationinfo.service.endpointaddress");
    AuthorizationInfoServiceFactory f = new AuthorizationInfoServiceFactory(uid, pwd, ep);
    authInfoService = f.getAuthorizationInfoService();
  }

  /**
   * Implements the use case LOK-1 (Tallenna lokitieto).
   * 
   * @return
   */
  @Override
  public VoidType opLog(LogEntriesType logEntriesType, AuditInfoType auditInfoType) throws KoKuFaultException {
    logger.info("opLog");
    List<LogEntryType> list = logEntriesType.getLogEntry();
    
    Iterator i = list.iterator();
    while(i.hasNext()){
      LogEntryType logEntryType = (LogEntryType)i.next();
      if(logEntryType != null){
 
        // call to the actual writing

        // Use ClientSystemId to tell in which log table we write!
        if (LogConstants.LOG_WRITER_LOG.equalsIgnoreCase(logEntryType.getClientSystemId())) {
          logger.debug("write to admin log");
          if(lu.logInputOk(logEntryType, LogConstants.LOG_ADMIN)){
            logService.writeAdmin(logConverter.fromWsTypeToAdmin(logEntryType));
          }
        } else { 
          logger.debug("write to normal log");
          if(lu.logInputOk(logEntryType, LogConstants.LOG_NORMAL)){
            logService.write(logConverter.fromWsType(logEntryType));
          }
        }
      }
    }
    return new VoidType();
  }

  /**
   * Implements the use cases LOK-3 (Etsi lokitieto) and LOK-4 (Tarkista lokin käsittelyloki). 
   */
  @Override
  public LogEntriesType opQueryLog(LogQueryCriteriaType criteriaType, AuditInfoType auditInfoType) throws KoKuFaultException {
    logger.info("opQueryLog");
    LogEntriesType logEntriesType = new LogEntriesType();
    
    if(LogConstants.LOG_NORMAL.equals(criteriaType.getLogType())) {
      AuthUtils.requirePermission("AdminSystemLogFile", auditInfoType.getUserId(), authInfoService.getUsersRoles(LogConstants.COMPONENT_LOK, auditInfoType.getUserId()));
      
      List<LogEntry> entries;
    
      try {    
        // call to the log database
        entries = logService.query(logConverter.fromWsType(criteriaType));
        logger.debug("entries: " + entries.size());
     
        if (entries == null) {
          logger.debug("No entries found in log table!");
        } else {
          Iterator<LogEntry> i = entries.iterator();
          while (i.hasNext()) {
            LogEntry entry = (LogEntry) i.next();
            // convert log entry to Web Service type and add it to the
            // collection
            logEntriesType.getLogEntry().add(logConverter.toWsType(entry));
          }
        }

      } catch (ParseException e) { //TODO: Tuleeko tällaista jostain?
        //TODO: heitä KokuException, jos tämä error edes tulee ikinä!
     logger.debug("parseException: "+e.getMessage());
     
    
      
      }
      
      logger.debug("write to admin log");
      AdminLogEntry adminLogEntry = new AdminLogEntry();
      adminLogEntry.setTimestamp(Calendar.getInstance().getTime());
      adminLogEntry.setUserPic(auditInfoType.getUserId());
      adminLogEntry.setCustomerPic(criteriaType.getCustomerPic());
      adminLogEntry.setOperation(LogConstants.OPERATION_VIEW); 
     
      // set end date back to 1 day earlier so that the search criteria given by the user is written to admin log
      Calendar end = criteriaType.getEndTime().toGregorianCalendar();

      end.set(Calendar.DATE, end.get(Calendar.DATE) -1);
      criteriaType.setEndTime(CalendarUtil.getXmlDateTime(end.getTime()));
      // LOK-3: "tapahtumatietona hakuehdot"
      adminLogEntry.setMessage(criteriaType.getCustomerPic()+" "+criteriaType.getDataItemType()+" "+df.format(CalendarUtil.getDate(criteriaType.getStartTime()))+" - "+df.format(CalendarUtil.getDate(criteriaType.getEndTime())));

      logService.writeAdmin(adminLogEntry); 
      
    } else if(LogConstants.LOG_ADMIN.equals(criteriaType.getLogType())) {
      AuthUtils.requirePermission("ViewAdminLogFile", auditInfoType.getUserId(), authInfoService.getUsersRoles(LogConstants.COMPONENT_LOK, auditInfoType.getUserId()));

      List<AdminLogEntry> entries;
      
      try {     
        // call to the log database
        entries = logService.queryAdmin(logConverter.fromWsType(criteriaType));
        logger.debug("entries: " + entries.size());

        if (entries == null) {
          logger.debug("No entries found in log_admin table!");
        } else {
          Iterator<AdminLogEntry> j = entries.iterator();

          while (j.hasNext()) {
            // convert log entry to AdminLog Web Service type and 
            // add it to the collection
            AdminLogEntry entry = (AdminLogEntry)j.next();  
            logEntriesType.getLogEntry().add(logConverter.toWsFromAdminType(entry));
          }
        }
      }catch (ParseException e) {
        logger.debug("parseException: "+e.getMessage());
        LogServiceErrorCode errorCode = LogServiceErrorCode.LOG_ERROR_PARSING;
        throw new KoKuFaultException(errorCode.getValue(), errorCode.getDescription());   
      }
      // end of the actual query
      
      // log the query to normal log
      LogEntry logEntry = new LogEntry();
      logEntry.setUserPic(auditInfoType.getUserId());
     
      // set end date back to 1 day earlier so that the real query end date given by the user is written to log
      Calendar end = criteriaType.getEndTime().toGregorianCalendar();
      end.set(Calendar.DATE, end.get(Calendar.DATE) -1);
      
      // LOK-4: "Tapahtumatietona hakuehdot"
      logEntry.setMessage("start: "+df.format(CalendarUtil.getDate(criteriaType.getStartTime()))+", end: "+df.format(end.getTime()));
      logEntry.setTimestamp(Calendar.getInstance().getTime());
      logEntry.setOperation("search");
      logEntry.setClientSystemId("adminlog");
      logEntry.setDataItemType("log");
      logEntry.setDataItemId("adminlogid");
      // call to lok service
      logService.write(logEntry);
    } else {
      throw new KoKuFaultException(LOG_ERROR_INVALID_QUERY_CRITERIA.getValue(), LOG_ERROR_INVALID_QUERY_CRITERIA.getDescription()); 
    }
    
    return logEntriesType;
  }

 
  /**
   * Implements the use case LOK-2 (Arkistoi lokitietoa).
   */
  @Override
  public ArchivalResultsType opArchiveLog(LogArchivalParametersType archivalParameters, AuditInfoType auditInfoType)
      throws KoKuFaultException {
    logger.info("opArchiveLog");   
    AuthUtils.requirePermission("AdminSystemLogFile", auditInfoType.getUserId(), authInfoService.getUsersRoles(LogConstants.COMPONENT_LOK, auditInfoType.getUserId()));

    int entryCount = 0;
    
    if(archivalParameters.getEndDate() == null){
      logger.error("archival end date not found!");
    }else{

      try{
        // call to the actual archiving
        entryCount = logService.archive(CalendarUtil.getDate(archivalParameters.getEndDate()));

        if(entryCount < 1) {
          // do not throw a KoKuFaultException
          logger.info("Nothing to archive before date "+CalendarUtil.getDate(archivalParameters.getEndDate()));
        } else{ // write to admin log about the archive only if there was something to archive

          logger.info("Log was archived. Now try to write in admin log.");
          
          // log this query to admin log 
          logger.debug("write to admin log");
          AdminLogEntry adminLogEntry = new AdminLogEntry();
          adminLogEntry.setTimestamp(Calendar.getInstance().getTime());
          adminLogEntry.setUserPic(auditInfoType.getUserId());
          adminLogEntry.setOperation("archive");       
         
          //TODO: KOKU-579: Selvitä aikaisin log:n tieto ja kirjaa tähän myös alkupäivä!
          // Use the hardcoded value now:
          String startdate = "2011-10-16";
            
          adminLogEntry.setMessage("archive log from "+startdate+" to "+df.format(CalendarUtil.getDate(archivalParameters.getEndDate())));

          logService.writeAdmin(adminLogEntry);
        }
      }catch(KoKuFaultException f){
        logger.debug("endpoint: throw KoKuFaultException");
        throw f;
      }catch(Exception e){
        logger.debug("Error in archiving: "+e.getMessage());
     
        LogServiceErrorCode errorCode = LogServiceErrorCode.LOG_ERROR_ARCHIVE_LOG_NOT_AVAILABLE;
        throw new KoKuFaultException(errorCode.getValue(), errorCode.getDescription());        
      }
 
    }
    ArchivalResultsType count = new ArchivalResultsType();
    count.setLogEntryCount(entryCount);
     
    return count;
  }

 
  /**
   * Convert from internal object representation (LogEntry) to webservice type
   * (LogEntryType).
   * 
   * @author makinsu
   */
  private static class LogConverter {

    SimpleDateFormat df = new SimpleDateFormat(LogConstants.DATE_FORMAT);

    LogUtils lu = new LogUtils();
    
    public LogConverter() {
    }

    /**
     * Method converts the query criteria from WS type to service type.
     * @param type
     * @return
     */
    public LogQueryCriteria fromWsType(LogQueryCriteriaType type) {
      //criteria parameters have been null-checked on the portlet side
      LogQueryCriteria criteria = new LogQueryCriteria(type.getLogType(), type.getCustomerPic(),
          type.getDataItemType(), CalendarUtil.getDate(type.getStartTime()), CalendarUtil.getDate(type.getEndTime()));

      return criteria;
    }


    public LogEntryType toWsFromAdminType(AdminLogEntry entry) throws ParseException {
                      
      LogEntryType entryType = new LogEntryType();
      entryType.setCustomerPic(entry.getCustomerPic());
      entryType.setUserPic(entry.getUserPic());
      entryType.setOperation(entry.getOperation());
      entryType.setMessage(entry.getMessage());
      entryType.setTimestamp(CalendarUtil.getXmlDateTime(entry.getTimestamp()));
      return entryType;
    }

    public LogEntryType toWsType(LogEntry entry) throws ParseException {
      LogEntryType et = new LogEntryType();

      // TODO: onko null-tsekkaus tehty jo aiemmin?
      // voiko jotkut näistä olla tyhjiä?
      et.setClientSystemId(entry.getClientSystemId());
      et.setCustomerPic(entry.getCustomerPic());
      et.setDataItemId(entry.getDataItemId());
      et.setDataItemType(entry.getDataItemType());
      et.setMessage(entry.getMessage());
      et.setOperation(entry.getOperation());
      et.setTimestamp(CalendarUtil.getXmlDateTime(entry.getTimestamp()));
      et.setUserPic(entry.getUserPic());

      return et;
    }

    /**
     * Convert from webservice type (LogEntryType) to internal object
     * representation (LogEntry).
     * 
     * @author makinsu
     * @param logt
     * @return
     */
    public LogEntry fromWsType(LogEntryType logt) {
      LogEntry entry = new LogEntry();

      entry.setCustomerPic(logt.getCustomerPic());
      logger.debug("from WsType customer pic: "+logt.getCustomerPic());
      entry.setClientSystemId(logt.getClientSystemId());
      entry.setDataItemType(logt.getDataItemType());
      entry.setDataItemId(logt.getDataItemId());
      entry.setMessage(logt.getMessage());
      entry.setOperation(logt.getOperation());
      entry.setTimestamp(CalendarUtil.getDate(logt.getTimestamp()));
      entry.setUserPic(logt.getUserPic());

      return entry;
    }

    /**
     * Converts the LogEntryType ws type to AdminLogEntry type
     * 
     * @param logt
     * @return
     */
    public AdminLogEntry fromWsTypeToAdmin(LogEntryType logt) {
      AdminLogEntry entry = new AdminLogEntry();

      entry.setMessage(logt.getMessage());
      entry.setOperation(logt.getOperation());
      entry.setTimestamp(CalendarUtil.getDate(logt.getTimestamp()));
      entry.setUserPic(logt.getUserPic());
      entry.setCustomerPic(logt.getCustomerPic());

      return entry;
    }
  
  }

}

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

import static fi.koku.services.utility.log.impl.LogServiceErrorCode.LOG_ERROR_INVALID_LOGTYPE;

import java.text.ParseException;
import java.util.Iterator;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.jws.WebService;

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

/**
 * KoKu log service implementation class. Actual point of entry to Log service.
 * Will call LogService for database access. Uses AuthorizationInfoService for
 * access validation.
 * 
 * @author aspluma
 * @author makinsu
 */
@Stateless
@Interceptors({ LogServiceFaultInterceptor.class })
@WebService(wsdlLocation = "META-INF/wsdl/logService.wsdl", endpointInterface = "fi.koku.services.utility.log.v1.LogServicePortType", targetNamespace = "http://services.koku.fi/utility/log/v1", portName = "logService-soap11-port", serviceName = "logService")
@RolesAllowed("koku-role")
public class LogServiceEndpointBean implements LogServicePortType {
  
  private static final Logger logger = LoggerFactory.getLogger(LogServiceEndpointBean.class);
  
  @EJB
  private LogService logService;
  
  private LogConverter logConverter;

  private LogUtils logUtils;

  private AuthorizationInfoService authInfoService;

  public LogServiceEndpointBean() {
    logConverter = new LogConverter();
    logUtils = new LogUtils();
    String uid = KoKuPropertiesUtil.get("lok.authorizationinfo.service.user.id");
    String pwd = KoKuPropertiesUtil.get("lok.authorizationinfo.service.password");
    String ep = KoKuPropertiesUtil.get("authorizationinfo.service.endpointaddress");
    AuthorizationInfoServiceFactory authFactory = new AuthorizationInfoServiceFactory(uid, pwd, ep);
    authInfoService = authFactory.getAuthorizationInfoService();
  }

  /**
   * Implements the use case LOK-1 (Tallenna lokitieto).
   * 
   * @return
   */
  @Override
  public VoidType opLog(LogEntriesType logEntriesType, AuditInfoType auditInfoType) {
    List<LogEntryType> list = logEntriesType.getLogEntry();

    Iterator<LogEntryType> i = list.iterator();
    while (i.hasNext()) {
      LogEntryType logEntryType = (LogEntryType) i.next();
      if (logEntryType != null) {
        // Use ClientSystemId to tell in which log table we write!
        if (LogConstants.LOG_WRITER_LOG.equalsIgnoreCase(logEntryType.getClientSystemId())) {
          if (logUtils.validateLogEntryType(logEntryType, LogConstants.LOG_ADMIN)) {
            logService.writeAdminLogEntry(logConverter.fromWsTypeToAdmin(logEntryType));
          }
        } else {
          if (logUtils.validateLogEntryType(logEntryType, LogConstants.LOG_NORMAL)) {
            logService.writeNormalLogEntry(logConverter.fromWsType(logEntryType));
          }
        }
      }
    }
    return new VoidType();
  }

  /**
   * Implements the use cases LOK-3 (Etsi lokitieto) and LOK-4 (Tarkista lokin
   * käsittelyloki).
   */
  @Override
  public LogEntriesType opQueryLog(LogQueryCriteriaType criteriaType, AuditInfoType auditInfoType) { 
    LogEntriesType logEntriesType = new LogEntriesType();

    if (LogConstants.LOG_NORMAL.equals(criteriaType.getLogType())) {
      // Check permission
      AuthUtils.requirePermission("AdminSystemLogFile", auditInfoType.getUserId(),
          authInfoService.getUsersRoles(LogConstants.COMPONENT_LOK, auditInfoType.getUserId()));

      List<LogEntry> entries = logService.queryNormalLog(logConverter.fromWsType(criteriaType));
      if (entries == null) {
        logger.info("No entries found in log table!");
      } else {
        Iterator<LogEntry> i = entries.iterator();
        while (i.hasNext()) {
          LogEntry entry = (LogEntry) i.next();
          if (entry != null) {
            // convert log entry to log entry web Service type and add it to the collection
            logEntriesType.getLogEntry().add(logConverter.toWsType(entry));
          }
        }
      }
      // log the query event
      logService.writeAdminLogQueryEvent(criteriaType, auditInfoType);
    } else if (LogConstants.LOG_ADMIN.equals(criteriaType.getLogType())) {
      // Check permission
      AuthUtils.requirePermission("ViewAdminLogFile", auditInfoType.getUserId(),
          authInfoService.getUsersRoles(LogConstants.COMPONENT_LOK, auditInfoType.getUserId()));

      List<AdminLogEntry> entries = logService.queryAdminLog(logConverter.fromWsType(criteriaType));
      if (entries == null) {
        logger.info("No entries found in log_admin table!");
      } else {
        Iterator<AdminLogEntry> j = entries.iterator();
        while (j.hasNext()) {
          // convert log entry to admin log entry web Service type and add it to the collection
          AdminLogEntry entry = (AdminLogEntry) j.next();
          if (entry != null) {
            logEntriesType.getLogEntry().add(logConverter.toWsFromAdminType(entry));
          }
        }
      }
      // log the query event
      logService.writeNormalLogQueryEvent(criteriaType, auditInfoType);
    } else {
      throw new KoKuFaultException(LOG_ERROR_INVALID_LOGTYPE.getValue(),
          LOG_ERROR_INVALID_LOGTYPE.getDescription());
    }

    return logEntriesType;
  }

  /**
   * Implements the use case LOK-2 (Arkistoi lokitietoa).
   */
  @Override
  public ArchivalResultsType opArchiveLog(LogArchivalParametersType archivalParameters, AuditInfoType auditInfoType) {
    logger.info("opArchiveLog");
    // Check permission
    AuthUtils.requirePermission("AdminSystemLogFile", auditInfoType.getUserId(),
        authInfoService.getUsersRoles(LogConstants.COMPONENT_LOK, auditInfoType.getUserId()));

    return logService.archiveLog(archivalParameters, auditInfoType);
  }

  /**
   * Convert from internal object representation (LogEntry) to webservice type
   * (LogEntryType).
   * 
   * @author makinsu
   */
  private static class LogConverter {

    public LogConverter() {
    }

    /**
     * Method converts the query criteria from WS type to service type.
     * 
     * @param type
     * @return
     */
    public LogQueryCriteria fromWsType(LogQueryCriteriaType type) {
      // criteria parameters have been null-checked on the portlet side
      // TODO: KOKU-1187
      LogQueryCriteria criteria = new LogQueryCriteria(type.getLogType(), type.getCustomerPic(), type.getDataItemType(),
    		  type.getUserPic(), CalendarUtil.getDate(type.getStartTime()), CalendarUtil.getDate(type.getEndTime()));

      return criteria;
    }

    /**
     * Helper method that converts the Admin log entry to Web Service type
     * 
     * @param entry
     * @return
     * @throws ParseException
     */
    public LogEntryType toWsFromAdminType(AdminLogEntry entry) {

      LogEntryType entryType = new LogEntryType();
      entryType.setCustomerPic(entry.getCustomerPic());
      entryType.setUserPic(entry.getUserPic());
      entryType.setOperation(entry.getOperation());
      entryType.setMessage(entry.getMessage());
      entryType.setTimestamp(CalendarUtil.getXmlDateTime(entry.getTimestamp()));
      return entryType;
    }

    /**
     * Helper method that converts the Log entry to Web Service type
     * 
     * @param entry
     * @return
     * @throws ParseException
     */
    public LogEntryType toWsType(LogEntry entry) {
      LogEntryType et = new LogEntryType();

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

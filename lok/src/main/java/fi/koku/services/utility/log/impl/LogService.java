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

import java.util.List;

import javax.ejb.Local;

import fi.koku.services.utility.log.v1.ArchivalResultsType;
import fi.koku.services.utility.log.v1.AuditInfoType;
import fi.koku.services.utility.log.v1.LogArchivalParametersType;
import fi.koku.services.utility.log.v1.LogQueryCriteriaType;

/**
 * Service API for log service. 
 * 
 * @author makinsu
 */
@Local
public interface LogService {

  /**
   * Write admin log entry.
   *
   * @param entry the entry
   */
  void writeAdminLogEntry(AdminLogEntry entry);

  /**
   * Write normal log entry.
   *
   * @param entry the entry
   */
  void writeNormalLogEntry(LogEntry entry);

  /**
   * Write admin log query event.
   *
   * @param criteriaType the criteria type
   * @param auditInfoType the audit info type
   */
  void writeAdminLogQueryEvent(LogQueryCriteriaType criteriaType, AuditInfoType auditInfoType);

  /**
   * Write normal log query event.
   *
   * @param criteriaType the criteria type
   * @param auditInfoType the audit info type
   */
  void writeNormalLogQueryEvent(LogQueryCriteriaType criteriaType, AuditInfoType auditInfoType);

  /**
   * Query normal log.
   *
   * @param criteria the criteria
   * @return the list
   */
  List<LogEntry> queryNormalLog(LogQueryCriteria criteria);

  /**
   * Query admin log.
   *
   * @param criteria the criteria
   * @return the list
   */
  List<AdminLogEntry> queryAdminLog(LogQueryCriteria criteria);

  /**
   * Archive log.
   *
   * @param archivalParameters the archival parameters
   * @param auditInfoType the audit info type
   * @return the archival results type
   */
  ArchivalResultsType archiveLog(LogArchivalParametersType archivalParameters, AuditInfoType auditInfoType);
}

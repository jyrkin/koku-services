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

import java.util.Collection;
import java.util.Date;

import javax.ejb.Local;

/**
 * Log related data access facilities.
 * 
 * @author makinsu
 */
@Local
public interface LogDAO {
  
  /**
   * Archive log.
   *
   * @param date the date
   * @return the int
   */
  int archiveLog(Date date);
  
  /**
   * Write log.
   *
   * @param entry the entry
   */
  void writeLog(LogEntry entry);

  /**
   * Query log.
   *
   * @param criteria the criteria
   * @return the collection
   */
  Collection<LogEntry> queryLog(LogQueryCriteria criteria);
  
  /**
   * Query admin log.
   *
   * @param criteria the criteria
   * @return the collection
   */
  Collection<AdminLogEntry> queryAdminLog(LogQueryCriteria criteria);

  /**
   * Write admin log.
   *
   * @param entry the entry
   */
  void writeAdminLog(AdminLogEntry entry);

  /**
   * Gets the earliest.
   *
   * @param date the date
   * @return the earliest
   */
  Date getEarliest(Date date);
}

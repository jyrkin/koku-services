/*
 * Copyright 2011 Ixonos Plc, Finland. All rights reserved.
 * 
 * You should have received a copy of the license text along with this program.
 * If not, please contact the copyright holder (http://www.ixonos.com/).
 * 
 */
package fi.koku.services.utility.log.impl;

/**
 * Log related constans. Used troughout logService.
 * 
 * @author makinsu
 */
public class LogConstants {
  
  public static final String DATE_FORMAT = "yyyy-MM-dd";
  public static final String LOG_NORMAL = "loki"; 
  public static final String LOG_ADMIN = "seurantaloki"; 
  
  public static final String LOG_WRITER_LOG = "log";
  public static final String COMPONENT_LOK = "lok";
  
  public static final String OPERATION_VIEW = "view log";

  public static final int MAX_QUERY_RESULTS = 51;
  
  private LogConstants(){
  }
}
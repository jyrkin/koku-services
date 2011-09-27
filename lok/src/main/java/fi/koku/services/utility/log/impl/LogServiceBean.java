package fi.koku.services.utility.log.impl;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import fi.koku.services.utility.log.v1.ServiceFault;

/**
 * Log service implementation class
 * 
 * @author makinsu
 *
 */
@Stateless
public class LogServiceBean implements LogService{

  @EJB
  LogDAO logDAO;
  
  @Override
  public void archive(Date date) throws ServiceFault {
    logDAO.archiveLog(date);
  }

  @Override
  public void write(LogEntry entry) {
    logDAO.writeLog(entry);
  }

  @Override
  public List<LogEntry> query(LogQueryCriteria criteria) {
    return (List<LogEntry>) logDAO.queryLog(criteria);
  }

  @Override
  public void writeAdmin(AdminLogEntry entry) {
    logDAO.writeAdminLog(entry);
  }

  @Override
  public List<AdminLogEntry> queryAdmin(LogQueryCriteria criteria) {
   return (List<AdminLogEntry>) logDAO.queryAdminLog(criteria);
  }

}

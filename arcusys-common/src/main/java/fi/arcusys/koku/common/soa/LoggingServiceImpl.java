package fi.arcusys.koku.common.soa;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fi.arcusys.koku.common.external.LogMessage;
import fi.arcusys.koku.common.external.LogServiceDAO;
import fi.arcusys.koku.common.external.LoggedOperation;
import fi.arcusys.koku.common.external.SystemArea;


/**
 * Implementation of external Web Service interface for providing (currently) file DL/UP related logging operations
 *
 * @author tturunen
 */
@Stateless
@WebService(serviceName = "LoggingService", portName = "LoggingServicePort",
      endpointInterface = "fi.arcusys.koku.common.soa.LoggingService",
      targetNamespace = "http://soa.common.koku.arcusys.fi/")
public class LoggingServiceImpl implements LoggingService {

	private static final String FILE_DL = "file.download";
	private static final String FILE_UL = "file.upload";

    private final static Logger LOG = LoggerFactory.getLogger(LoggingServiceImpl.class);


    @EJB
    private LogServiceDAO logDao;

	@Override
	public void logFileDownload(final String uid, final String path, final String message) {
		log(uid, path, message, FILE_DL, LoggedOperation.Read);
	}

	@Override
	public void logFileUpload(final String uid, final String path, final String message) {
		log(uid, path, message, FILE_UL, LoggedOperation.Create);
	}

	private void log(final String uid, final String path, final String message, final String dataItemType, LoggedOperation operation) {
		final LogMessage msg = new LogMessage();
		msg.setSystemArea(SystemArea.FILE);
		msg.setDataItemType(dataItemType);
		// We can't use dataItemId because DB restrictions
		// msg.setDataItemId(path);
		msg.setOperation(operation);
		// NOTE: logMessage-method will change uid to SSN, so leave it as it.
		msg.setUserPic(uid);
		msg.setMessage(message+". Path: '"+path+"'");
		logDao.logMessage(msg);
	}

}

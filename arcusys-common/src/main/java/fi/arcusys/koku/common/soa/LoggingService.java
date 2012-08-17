package fi.arcusys.koku.common.soa;

import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * External Web Service interface for providing logging operations for attachment download (maybe more?) operations
 *
 * @author tturunen
 */
@WebService(targetNamespace = "http://soa.common.koku.arcusys.fi/")
public interface LoggingService {

	/**
	 * Logs file download
	 *
	 * @param uid UserID
	 * @param path Path to download file
	 * @param message Log description
	 */
    void logFileDownload(
    		@WebParam(name = "uid") final String uid,
    		@WebParam(name = "path") final String path,
    		@WebParam(name = "message") final String message);

    /**
     * Logs file upload
     *
     * @param uid UserID
     * @param path Path to uploaded file
     * @param message Log description
     */
    void logFileUpload(
    		@WebParam(name = "uid") final String uid,
    		@WebParam(name = "path") final String path,
    		@WebParam(name = "message") final String message);
}

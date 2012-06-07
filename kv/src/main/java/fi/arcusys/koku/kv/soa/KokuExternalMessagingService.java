package fi.arcusys.koku.kv.soa;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * Interface for notification service. To be used ONLY by external components.
 * 
 * @author Mikhail Kapitonov (mikhail.kapitonov@arcusys.fi)
 * Jun 05, 2012
 */

@WebService(targetNamespace = "http://services.koku.fi/entity/kv/v1")
public interface KokuExternalMessagingService {

    /**
     * This method expects fromUser and toUsers parameters to be HETU
     */
    void deliverMessage(
            @WebParam(name = "fromUser") final String fromUser, 
            @WebParam(name = "toUser") final List<String> toUsers, 
            @WebParam(name = "subject") final String subject, 
            @WebParam(name = "content") final String content);

}

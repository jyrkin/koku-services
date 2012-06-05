package fi.arcusys.koku.kv.soa;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;

import fi.arcusys.koku.common.soa.UsersAndGroupsService;
import fi.arcusys.koku.kv.service.MessageServiceFacade;

/**
 * Implementation of notification service. To be used ONLY by external components.
 * 
 * @author Mikhail Kapitonov (mikhail.kapitonov@arcusys.fi)
 * Jun 05, 2012
 */

@Stateless
@WebService(serviceName = "KokuExternalMessagingService", portName = "KokuExternalMessagingServicePort", 
            endpointInterface = "fi.arcusys.koku.kv.soa.KokuExternalMessagingService",
            targetNamespace = "http://services.koku.fi/entity/kv/v1")
public class KokuExternalMessagingServiceImpl implements KokuExternalMessagingService {

    @EJB
    private MessageServiceFacade serviceFacade;
    
    @EJB
    private UsersAndGroupsService usersService;
    
    @Override
    public void deliverMessage(String fromUser, List<String> toUsers, String subject, String content) {
        String fromUserUID = usersService.getUserUidByEmployeeSsn(fromUser);
        
        final List<String> toUsersUID = new ArrayList<String>();
        
        for (String hetu : toUsers)
            toUsersUID.add(usersService.getUserUidByKunpoSsn(hetu));
        
        serviceFacade.deliverMessage(fromUserUID, toUsersUID, subject, content);
    }

}

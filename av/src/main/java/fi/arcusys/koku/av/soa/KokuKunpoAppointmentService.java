package fi.arcusys.koku.av.soa;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Aug 19, 2011
 */
@WebService(targetNamespace = "http://soa.av.koku.arcusys.fi/")
public interface KokuKunpoAppointmentService {
    
    public int getTotalAssignedAppointments(@WebParam(name = "user") final String user);

    public int getTotalRespondedAppointments(@WebParam(name = "user") final String user);

    public List<AppointmentWithTarget>  getRespondedAppointments(
            @WebParam(name = "user") final String user,
            @WebParam(name = "startNum") int startNum, 
            @WebParam(name = "maxNum") int maxNum);

    public List<AppointmentWithTarget> getAssignedAppointments(
            @WebParam(name = "user") final String user,
            @WebParam(name = "startNum") int startNum, 
            @WebParam(name = "maxNum") int maxNum);
    
    public AppointmentRespondedTO getAppointmentRespondedById(@WebParam(name = "appointmentId") final long appointmentId);
}
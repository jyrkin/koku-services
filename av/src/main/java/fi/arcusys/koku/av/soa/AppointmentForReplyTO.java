package fi.arcusys.koku.av.soa;

import java.util.ArrayList;
import java.util.List;

/**
 * Data transfer object for communication with UI/Intalio process. Holds data about appointment for reply by recipient.
 * 
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Aug 18, 2011
 */
public class AppointmentForReplyTO extends AppointmentSummary {
    private List<AppointmentSlotTO> slots;
    private AppointmentSummaryStatus appointmentResponse;
    private int chosenSlot;

    /**
     * @return the slots
     */
    public List<AppointmentSlotTO> getSlots() {
        if (this.slots == null) {
            this.slots = new ArrayList<AppointmentSlotTO>();
        }
        return slots;
    }

    /**
     * @param slots the slots to set
     */
    public void setSlots(List<AppointmentSlotTO> slots) {
        this.slots = slots;
    }

    /**
     * @return the response
     */
    public AppointmentSummaryStatus getAppointmentResponse() {
        return appointmentResponse;
    }

    /**
     * @param appointmentResponse the appointmentResponse to set
     */
    public void setAppointmentResponse(AppointmentSummaryStatus appointmentResponse) {
        this.appointmentResponse = appointmentResponse;
    }

    /**
     * @return the chosenSlot
     */
    public int getChosenSlot() {
        return chosenSlot;
    }

    /**
     * @param chosenSlot the chosenSlot to set
     */
    public void setChosenSlot(int chosenSlot) {
        this.chosenSlot = chosenSlot;
    }
    
}

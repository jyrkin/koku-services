package fi.arcusys.koku.av.service;

import static junit.framework.Assert.*;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TimeZone;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fi.arcusys.koku.av.service.AppointmentServiceFacade;
import fi.arcusys.koku.av.soa.AppointmentCriteria;
import fi.arcusys.koku.av.soa.AppointmentForEditTO;
import fi.arcusys.koku.av.soa.AppointmentForReplyTO;
import fi.arcusys.koku.av.soa.AppointmentReceipientTO;
import fi.arcusys.koku.av.soa.AppointmentSlotTO;
import fi.arcusys.koku.av.soa.AppointmentSummary;
import fi.arcusys.koku.av.soa.AppointmentSummaryStatus;
import fi.arcusys.koku.av.soa.AppointmentTO;
import fi.arcusys.koku.av.soa.AppointmentWithTarget;
import fi.arcusys.koku.common.service.CommonTestUtil;
import fi.arcusys.koku.common.service.datamodel.Appointment;
import fi.arcusys.koku.common.service.datamodel.AppointmentResponse;
import fi.arcusys.koku.common.service.datamodel.AppointmentSlot;
import fi.arcusys.koku.common.service.datamodel.AppointmentStatus;
import fi.arcusys.koku.common.service.datamodel.User;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Jul 22, 2011
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/test-av-context.xml"})
public class AppointmentServiceTest {
	@Autowired
	private AppointmentServiceFacade serviceFacade;

	@Autowired
	private CommonTestUtil testUtil;
	
	@Test
	public void testMessageTemplate() {
	    assertEquals("Sinulle on tietopyynto \"ABC\"", MessageFormat.format("Sinulle on tietopyynto \"{0}\"", "ABC"));
        assertEquals("'app_inbox_citizen'", MessageFormat.format("''app_inbox_citizen''", new Object[] {}));
	}
	
	@Test
	public void getUserAppointments() {
		final AppointmentForEditTO newAppointment = createTestAppointment("new appointment", "appointment description", 1);
        final AppointmentReceipientTO appointmentReceipient = newAppointment.getReceipients().get(0);
        final String targetPerson = appointmentReceipient.getTargetPerson();
		final Long appointmentId = serviceFacade.storeAppointment(newAppointment);
		
		assertNotNull("Appointment is not found in user's appointments", getById(serviceFacade.getCreatedAppointments(newAppointment.getSender(), 1, 10, null), appointmentId));
		
        final AppointmentCriteria criteria = new AppointmentCriteria();
        assertNotNull("Appointment is not found in user's appointments", getById(serviceFacade.getCreatedAppointments(newAppointment.getSender(), 1, 10, criteria), appointmentId));

        criteria.setTargetPersonUid(targetPerson);
        assertNotNull("Search by target person:", getById(serviceFacade.getCreatedAppointments(newAppointment.getSender(), 1, 10, criteria), appointmentId));

        criteria.setTargetPersonUid("unknown");
        assertNull("Search by wrong target person:", getById(serviceFacade.getCreatedAppointments(newAppointment.getSender(), 1, 10, criteria), appointmentId));
	}
	
	@Test
	public void approveAndDecline() {
		final AppointmentForEditTO newAppointment = createTestAppointment("new appointment for approve & decline", "appointment description", 1);

		final AppointmentReceipientTO appointmentReceipient = newAppointment.getReceipients().get(0);
        final String receipient = appointmentReceipient.getReceipients().get(0);
        final String targetPerson = appointmentReceipient.getTargetPerson();
        final String targetPersonForDecline = newAppointment.getReceipients().get(1).getTargetPerson();
        final String receipientForDecline = newAppointment.getReceipients().get(1).getReceipients().get(1);
		final Long appointmentId = serviceFacade.storeAppointment(newAppointment);
		

		List<AppointmentWithTarget> appointments = serviceFacade.getAssignedAppointments(receipient);
		assertFalse(appointments.isEmpty());
		
		final AppointmentSummary appointmentForApprove = getById(appointments, appointmentId);
		assertEquals("Non-answered appointments should be Created", AppointmentSummaryStatus.Created, serviceFacade.getAppointmentForReply(appointmentId, targetPerson).getResponse());
        assertTrue(serviceFacade.getAppointment(appointmentForApprove.getAppointmentId()).getAcceptedSlots().isEmpty());
		serviceFacade.approveAppointment(targetPerson, receipient, appointmentForApprove.getAppointmentId(), 1, "approved");
		final AppointmentForReplyTO approvedAppointment = serviceFacade.getAppointmentForReply(appointmentId, targetPerson);
		assertEquals("Approved appointments should be Approved", AppointmentSummaryStatus.Approved, approvedAppointment.getResponse());
		assertEquals("Chosen slot should be 1", 1, approvedAppointment.getChosenSlot());

		boolean haveChosenSlot = false;
		for (final AppointmentSlotTO slot : approvedAppointment.getSlots())
		    if (slot.getSlotNumber() == 1) { haveChosenSlot = true; break; }
		assertTrue("Chosen slot must remain to ba available for choosing", haveChosenSlot);

        assertFalse(serviceFacade.getAppointment(appointmentForApprove.getAppointmentId()).getAcceptedSlots().isEmpty());
        assertEquals(AppointmentSummaryStatus.Approved, serviceFacade.getAppointmentRespondedById(appointmentForApprove.getAppointmentId(), targetPerson).getStatus());
        assertNotNull(getById(serviceFacade.getRespondedAppointments(receipient, 1, 10), appointmentId));
        assertNull(getById(serviceFacade.getOldAppointments(receipient, 1, 10), appointmentId));
		
        appointments = serviceFacade.getAssignedAppointments(receipient);
        assertFalse(appointments.isEmpty());

        final AppointmentSummary appointmentForDecline = getById(appointments, appointmentId);
        assertTrue(serviceFacade.getAppointment(appointmentForApprove.getAppointmentId()).getUsersRejected().isEmpty());
		serviceFacade.declineAppointment(targetPersonForDecline, receipientForDecline, appointmentForDecline.getAppointmentId(), "declined");
		assertEquals("Declied appointments should be Cancelled", AppointmentSummaryStatus.Cancelled, serviceFacade.getAppointmentForReply(appointmentId, targetPersonForDecline).getResponse());
        assertFalse(serviceFacade.getAppointment(appointmentForApprove.getAppointmentId()).getUsersRejected().isEmpty());
        assertTrue(serviceFacade.getAppointment(appointmentForApprove.getAppointmentId()).getUsersRejected().contains(getKunpoName(targetPersonForDecline)));
        assertNull(getById(serviceFacade.getRespondedAppointments(receipientForDecline, 1, 10), appointmentId));
        assertNotNull(getById(serviceFacade.getOldAppointments(receipientForDecline, 1, 10), appointmentId));

        assertNull("All appointments should be processed already", getById(serviceFacade.getAssignedAppointments(receipientForDecline), appointmentId));
        
        serviceFacade.approveAppointment(targetPersonForDecline, receipientForDecline, appointmentForDecline.getAppointmentId(), 1, "reconcidered, approved");

        final AppointmentTO reconcideredAppointment = serviceFacade.getAppointment(appointmentForDecline.getAppointmentId());
        assertTrue(reconcideredAppointment.getUsersRejected().isEmpty());
        assertFalse(reconcideredAppointment.getAcceptedSlots().isEmpty());
        assertEquals(AppointmentSummaryStatus.Approved, serviceFacade.getAppointmentRespondedById(reconcideredAppointment.getAppointmentId(), targetPerson).getStatus());
        assertNotNull(getById(serviceFacade.getRespondedAppointments(receipientForDecline, 1, 10), appointmentId));
        assertNull(getById(serviceFacade.getOldAppointments(receipientForDecline, 1, 10), appointmentId));

        // cancel appointment
        serviceFacade.cancelAppointment(targetPerson, receipient, appointmentForApprove.getAppointmentId(), "cancelled");
        assertEquals(AppointmentSummaryStatus.Cancelled, serviceFacade.getAppointmentRespondedById(appointmentForApprove.getAppointmentId(), targetPerson).getStatus());
	}
	
	@Test
	public void getAppointmentsWithRole() {
		final String role1 = "role1";
		final String role2 = "role2";
		
		// Spawn three users with different sets of roles
		String employee1Uid = testUtil.getUserByUidWithRoles("testEmployee1WithRole1", Collections.singletonList(role1)).getUid();
        String employee2Uid = testUtil.getUserByUidWithRoles("testEmployee2WithRole2", Collections.singletonList(role2)).getUid();
        String employee3Uid = testUtil.getUserByUidWithRoles("testEmployee3WithRole2AndRole2", Arrays.asList(role1, role2)).getUid();
		
        // Create appointments
		final List<AppointmentForEditTO> createdAppointments = Arrays.asList(
				createTestAppointmentWithRole(employee1Uid, role1, "new appointment 1", "appointment description"),
				createTestAppointmentWithRole(employee1Uid, role1, "new appointment 2", "appointment description"),
				createTestAppointmentWithRole(employee1Uid, role1, "new appointment 3", "appointment description"),
				createTestAppointmentWithRole(employee2Uid, role2, "new appointment 4", "appointment description"),
				createTestAppointmentWithRole(employee2Uid, role2, "new appointment 5", "appointment description"),
				createTestAppointmentWithRole(employee2Uid, role2, "new appointment 6", "appointment description"));
		
		// Get default recipient
		final AppointmentReceipientTO appointmentReceipient = createdAppointments.get(0).getReceipients().get(0);
		final String targetPerson = appointmentReceipient.getTargetPerson();
		
		assertEquals("Mock data should contain 2 guardians", 2, appointmentReceipient.getReceipients().size());
		
        final String guardian1 = appointmentReceipient.getReceipients().get(0);
        final String guardian2 = appointmentReceipient.getReceipients().get(1);
        
        final AppointmentCriteria criteria = new AppointmentCriteria();
        criteria.setTargetPersonUid(targetPerson);
        
        int oldAssignedRecipient;
        int newAssignedRecipient;
                
        //
        // Created Appointments
        //
        
        oldAssignedRecipient = serviceFacade.getTotalAssignedAppointments(guardian1);
        
        // Get current totals		
		final int oldCreatedEmp1 = serviceFacade.getTotalCreatedAppointments(employee1Uid, null);
		final int oldCreatedEmpC1 = serviceFacade.getTotalCreatedAppointments(employee1Uid, criteria); // with criteria
		final int oldCreatedEmpL1 = serviceFacade.getCreatedAppointments(employee1Uid, 1, 10, null).size(); // as list size
		final int oldCreatedEmpLC1 = serviceFacade.getCreatedAppointments(employee1Uid, 1, 10, criteria).size(); // as list size with criteria
		final int oldCreatedEmp2 = serviceFacade.getTotalCreatedAppointments(employee2Uid, null);
		final int oldCreatedEmpC2 = serviceFacade.getTotalCreatedAppointments(employee2Uid, criteria); // with criteria
		final int oldCreatedEmpL2 = serviceFacade.getCreatedAppointments(employee2Uid, 1, 10, null).size(); // as list size
		final int oldCreatedEmpLC2 = serviceFacade.getCreatedAppointments(employee2Uid, 1, 10, criteria).size(); // as list size with criteria
		final int oldCreatedEmp3 = serviceFacade.getTotalCreatedAppointments(employee3Uid, null);
		final int oldCreatedEmpC3 = serviceFacade.getTotalCreatedAppointments(employee3Uid, criteria); // with criteria
		final int oldCreatedEmpL3 = serviceFacade.getCreatedAppointments(employee3Uid, 1, 10, null).size(); // as list size
		final int oldCreatedEmpLC3 = serviceFacade.getCreatedAppointments(employee3Uid, 1, 10, criteria).size(); // as list size with criteria
		
		// Created appointments
		List<AppointmentWithTarget> assignedAppointments;
		
		assignedAppointments = serviceFacade.getAssignedAppointments(guardian1);
		
		// Count individual role occurrences
		int oldCountRole1 = 0;
		int oldCountRole2 = 0;
		
		for (AppointmentWithTarget appointment : assignedAppointments ) {
			if (appointment.getSenderUserInfo().getUid().equals(employee1Uid) &&
				appointment.getSenderRole().equals(role1)) oldCountRole1++;
			
			if (appointment.getSenderUserInfo().getUid().equals(employee2Uid) &&
					appointment.getSenderRole().equals(role2)) oldCountRole2++;
		}
		
		// Store appointments
		for (AppointmentForEditTO appointment : createdAppointments) 
			serviceFacade.storeAppointment(appointment);
		
		newAssignedRecipient = serviceFacade.getTotalAssignedAppointments(guardian1);
        
		// Get new totals
		final int newCreatedEmp1 = serviceFacade.getTotalCreatedAppointments(employee1Uid, null);
		final int newCreatedEmpC1 = serviceFacade.getTotalCreatedAppointments(employee1Uid, criteria); // with criteria
		final int newCreatedEmpL1 = serviceFacade.getCreatedAppointments(employee1Uid, 1, 10, null).size(); // as list size
		final int newCreatedEmpLC1 = serviceFacade.getCreatedAppointments(employee1Uid, 1, 10, criteria).size(); // as list size with criteria
		final int newCreatedEmp2 = serviceFacade.getTotalCreatedAppointments(employee2Uid, null);
		final int newCreatedEmpC2 = serviceFacade.getTotalCreatedAppointments(employee2Uid, criteria); // with criteria
		final int newCreatedEmpL2 = serviceFacade.getCreatedAppointments(employee2Uid, 1, 10, null).size(); // as list size
		final int newCreatedEmpLC2 = serviceFacade.getCreatedAppointments(employee2Uid, 1, 10, criteria).size(); // as list size with criteria
		final int newCreatedEmp3 = serviceFacade.getTotalCreatedAppointments(employee3Uid, null);
		final int newCreatedEmpC3 = serviceFacade.getTotalCreatedAppointments(employee3Uid, criteria); // with criteria
		final int newCreatedEmpL3 = serviceFacade.getCreatedAppointments(employee3Uid, 1, 10, null).size(); // as list size
		final int newCreatedEmpLC3 = serviceFacade.getCreatedAppointments(employee3Uid, 1, 10, criteria).size(); // as list size with criteria
		
		// Assert total values
		assertEquals("There should be 6 assigned appointments for this recipient", 6, newAssignedRecipient - oldAssignedRecipient);
		assertEquals("There should be 3 new appointments for employee1", 3, newCreatedEmp1 - oldCreatedEmp1);
		assertEquals("There should be 3 new appointments for employee1 (with criteria)", 3, newCreatedEmpC1 - oldCreatedEmpC1);
		assertEquals("There should be 3 new appointments for employee1 (as list size)", 3, newCreatedEmpL1 - oldCreatedEmpL1);
		assertEquals("There should be 3 new appointments for employee1 (as list size with criteria)", 3, newCreatedEmpLC1 - oldCreatedEmpLC1);		
		assertEquals("There should be 3 new appointments for employee2", 3, newCreatedEmp2 - oldCreatedEmp2);
		assertEquals("There should be 3 new appointments for employee2 (with criteria)", 3, newCreatedEmpC2 - oldCreatedEmpC2);
		assertEquals("There should be 3 new appointments for employee2 (as list size)", 3, newCreatedEmpL2 - oldCreatedEmpL2);
		assertEquals("There should be 3 new appointments for employee2 (as list size with criteria)", 3, newCreatedEmpLC2 - oldCreatedEmpLC2);
		assertEquals("There should be 6 new appointments for employee3", 6, newCreatedEmp3 - oldCreatedEmp3);
		assertEquals("There should be 6 new appointments for employee3 (with criteria)", 6, newCreatedEmpC3 - oldCreatedEmpC3);
		assertEquals("There should be 6 new appointments for employee3 (as list size)", 6, newCreatedEmpL3 - oldCreatedEmpL3);
		assertEquals("There should be 6 new appointments for employee3 (as list size with criteria)", 6, newCreatedEmpLC3 - oldCreatedEmpLC3);
		
		assignedAppointments = serviceFacade.getAssignedAppointments(guardian1);
		
		// Count individual role occurrences
		int newCountRole1 = 0;
		int newCountRole2 = 0;
			
		for (AppointmentWithTarget appointment : assignedAppointments ) {
			if (appointment.getSenderUserInfo().getUid().equals(employee1Uid) &&
				appointment.getSenderRole().equals(role1)) newCountRole1++;
			
			if (appointment.getSenderUserInfo().getUid().equals(employee2Uid) &&
				appointment.getSenderRole().equals(role2)) newCountRole2++;
		}
		
		assertEquals("There should be 3 non-processed appointments with role 'role1'", 3, newCountRole1 - oldCountRole1);
		assertEquals("There should be 3 non-processed appointments with role 'role2'", 3, newCountRole2 - oldCountRole2);
		
		//
		// Processed appointments
		//
		
		oldAssignedRecipient = serviceFacade.getTotalAssignedAppointments(guardian1);
		
		// Get current totals
		final int oldProcessedEmp1 = serviceFacade.getTotalProcessedAppointments(employee1Uid, null);
		final int oldProcessedEmpC1 = serviceFacade.getTotalProcessedAppointments(employee1Uid, criteria); // with criteria
		final int oldProcessedEmpL1 = serviceFacade.getProcessedAppointments(employee1Uid, 1, 10, null).size(); // as list size
		final int oldProcessedEmpLC1 = serviceFacade.getProcessedAppointments(employee1Uid, 1, 10, null).size(); // as list size with criteria
		final int oldProcessedEmp2 = serviceFacade.getTotalProcessedAppointments(employee2Uid, null);
		final int oldProcessedEmpC2 = serviceFacade.getTotalProcessedAppointments(employee2Uid, criteria); // with criteria
		final int oldProcessedEmpL2 = serviceFacade.getProcessedAppointments(employee2Uid, 1, 10, null).size(); // as list size
		final int oldProcessedEmpLC2 = serviceFacade.getProcessedAppointments(employee2Uid, 1, 10, null).size(); // as list size with criteria
		final int oldProcessedEmp3 = serviceFacade.getTotalProcessedAppointments(employee3Uid, null);
		final int oldProcessedEmpC3 = serviceFacade.getTotalProcessedAppointments(employee3Uid, criteria); // with criteria
		final int oldProcessedEmpL3 = serviceFacade.getProcessedAppointments(employee3Uid, 1, 10, null).size(); // as list size
		final int oldProcessedEmpLC3 = serviceFacade.getProcessedAppointments(employee3Uid, 1, 10, null).size(); // as list size with criteria
		
		assignedAppointments = serviceFacade.getAssignedAppointments(guardian1);
		
		// Count individual role occurrences
		oldCountRole1 = 0;
		oldCountRole2 = 0;
		
		for (AppointmentWithTarget appointment : assignedAppointments ) {
			if (appointment.getSenderUserInfo().getUid().equals(employee1Uid) &&
				appointment.getSenderRole().equals(role1)) oldCountRole1++;
			
			if (appointment.getSenderUserInfo().getUid().equals(employee2Uid) &&
					appointment.getSenderRole().equals(role2)) oldCountRole2++;
		}
		
		// Mark 2 appointments from each role as approved
		
		Long approveId;
		
		approveId = serviceFacade.getCreatedAppointments(employee1Uid, 1, 1, null).get(0).getAppointmentId();
		serviceFacade.approveAppointment(targetPerson, guardian1, approveId, 1, "approved");
		//serviceFacade.approveAppointment(targetPerson, guardian2, approveId, 1, "approved");
		
		approveId = serviceFacade.getCreatedAppointments(employee2Uid, 1, 1, null).get(0).getAppointmentId();
		serviceFacade.approveAppointment(targetPerson, guardian1, approveId, 1, "approved");
		//serviceFacade.approveAppointment(targetPerson, guardian2, approveId, 1, "approved");
		
		newAssignedRecipient = serviceFacade.getTotalAssignedAppointments(guardian1);
		
		// Get new totals
		final int newProcessedEmp1 = serviceFacade.getTotalProcessedAppointments(employee1Uid, null);
		final int newProcessedEmpC1 = serviceFacade.getTotalProcessedAppointments(employee1Uid, criteria); // with criteria
		final int newProcessedEmpL1 = serviceFacade.getProcessedAppointments(employee1Uid, 1, 10, null).size(); // as list size
		final int newProcessedEmpLC1 = serviceFacade.getProcessedAppointments(employee1Uid, 1, 10, null).size(); // as list size with criteria
		final int newProcessedEmp2 = serviceFacade.getTotalProcessedAppointments(employee2Uid, null);
		final int newProcessedEmpC2 = serviceFacade.getTotalProcessedAppointments(employee2Uid, criteria); // with criteria
		final int newProcessedEmpL2 = serviceFacade.getProcessedAppointments(employee2Uid, 1, 10, null).size(); // as list size
		final int newProcessedEmpLC2 = serviceFacade.getProcessedAppointments(employee2Uid, 1, 10, null).size(); // as list size with criteria
		final int newProcessedEmp3 = serviceFacade.getTotalProcessedAppointments(employee3Uid, null);
		final int newProcessedEmpC3 = serviceFacade.getTotalProcessedAppointments(employee3Uid, criteria); // with criteria
		final int newProcessedEmpL3 = serviceFacade.getProcessedAppointments(employee3Uid, 1, 10, null).size(); // as list size
		final int newProcessedEmpLC3 = serviceFacade.getProcessedAppointments(employee3Uid, 1, 10, null).size(); // as list size with criteria
		
		// Assert total values
		assertEquals("There should be 4 assigned appointments for this recipient", 4, 6 - Math.abs(newAssignedRecipient - oldAssignedRecipient));
		assertEquals("There should be 2 non-processed appointments for employee1", 2, 3 - Math.abs(newProcessedEmp1 - oldProcessedEmp1));
		assertEquals("There should be 2 non-processed appointments for employee1 (with criteria)", 2, 3 - Math.abs(newProcessedEmpC1 - oldProcessedEmpC1));
		assertEquals("There should be 2 non-processed appointments for employee1 (as list size)", 2, 3 - Math.abs(newProcessedEmpL1 - oldProcessedEmpL1));
		assertEquals("There should be 2 non-processed appointments for employee1 (as list size with criteria)", 2, 3 - Math.abs(newProcessedEmpLC1 - oldProcessedEmpLC1));		
		assertEquals("There should be 2 non-processed appointments for employee2", 2, 3 - Math.abs(newProcessedEmp2 - oldProcessedEmp2));
		assertEquals("There should be 2 non-processed appointments for employee2 (with criteria)", 2, 3 - Math.abs(newProcessedEmpC2 - oldProcessedEmpC2));
		assertEquals("There should be 2 non-processed appointments for employee2 (as list size)", 2, 3 - Math.abs(newProcessedEmpL2 - oldProcessedEmpL2));
		assertEquals("There should be 2 non-processed appointments for employee2 (as list size with criteria)", 2, 3 - Math.abs(newProcessedEmpLC2 - oldProcessedEmpLC2));
		assertEquals("There should be 4 non-processed appointments for employee3", 4, 6 - Math.abs(newProcessedEmp3 - oldProcessedEmp3));
		assertEquals("There should be 4 non-processed appointments for employee3 (with criteria)", 4, 6 - Math.abs(newProcessedEmpC3 - oldProcessedEmpC3));
		assertEquals("There should be 4 non-processed appointments for employee3 (as list size)", 4, 6 - Math.abs(newProcessedEmpL3 - oldProcessedEmpL3));
		assertEquals("There should be 4 non-processed appointments for employee3 (as list size with criteria)", 4, 6 - Math.abs(newProcessedEmpLC3 - oldProcessedEmpLC3));

		assignedAppointments = serviceFacade.getAssignedAppointments(guardian1);
		
		// Count individual role occurrences
		newCountRole1 = 0;
		newCountRole2 = 0;
			
		for (AppointmentWithTarget appointment : assignedAppointments ) {
			if (appointment.getSenderUserInfo().getUid().equals(employee1Uid) &&
				appointment.getSenderRole().equals(role1)) newCountRole1++;
			
			if (appointment.getSenderUserInfo().getUid().equals(employee2Uid) &&
				appointment.getSenderRole().equals(role2)) newCountRole2++;
		}
		
		assertEquals("There should be 2 non-processed appointments with role 'role1'", 2, 3 - Math.abs(newCountRole1 - oldCountRole1));
		assertEquals("There should be 2 non-processed appointments with role 'role2'", 2, 3 - Math.abs(newCountRole2 - oldCountRole2));
	}
	
	@Test
	public void cancelAppointment() {
        final AppointmentForEditTO newAppointment = createTestAppointment("new appointment for cancel", "appointment description", 1);

        final Long appointmentId = serviceFacade.storeAppointment(newAppointment);
        serviceFacade.cancelWholeAppointment(appointmentId, "cancelled");
        
        assertNull(getById(serviceFacade.getCreatedAppointments(newAppointment.getSender(), 1, 5, null), appointmentId));
        assertNotNull(getById(serviceFacade.getProcessedAppointments(newAppointment.getSender(), 1, 5, null), appointmentId));
	}

    private String getKunpoName(final String targetPersonForDecline) {
        return testUtil.getUserByUid(targetPersonForDecline).getCitizenPortalName();
    }
	
	@Test
	public void countTotals() {
        final AppointmentForEditTO newAppointment = createTestAppointment("new appointment for counts", "appointment description", 3);

        final AppointmentReceipientTO appointmentReceipient = newAppointment.getReceipients().get(0);
        final String uniqReceipient = appointmentReceipient.getReceipients().get(1);
        final String targetPerson = appointmentReceipient.getTargetPerson();
        final int oldAssignedTotal = serviceFacade.getTotalAssignedAppointments(uniqReceipient);
        
        final Long appointmentId = serviceFacade.storeAppointment(newAppointment);
        
        assertEquals(oldAssignedTotal + 1, serviceFacade.getTotalAssignedAppointments(uniqReceipient));

        serviceFacade.approveAppointment(targetPerson, uniqReceipient, appointmentId, 1, "approved");
        assertEquals(oldAssignedTotal, serviceFacade.getTotalAssignedAppointments(uniqReceipient));
	}
	
	@Test
	public void testSkipApprovedSlots() {
	    // create appointment
        final AppointmentForEditTO newAppointment = createTestAppointment("new appointment for counts", "appointment description", 1);
        final Long appointmentId = serviceFacade.storeAppointment(newAppointment);
	    
        final AppointmentReceipientTO appointmentReceipient = newAppointment.getReceipients().get(0);
        final String receipient = appointmentReceipient.getReceipients().get(0);
        final String targetPerson = appointmentReceipient.getTargetPerson();
        final String targetPersonForCheck = newAppointment.getReceipients().get(1).getTargetPerson();
        assertFalse(targetPerson.equals(targetPersonForCheck));

        // reply to appointment
        final int approvedSlotNumber = 1;
        serviceFacade.approveAppointment(targetPerson, receipient, appointmentId, approvedSlotNumber, "approved");

        // get for reply - approved slot is absent
        final AppointmentForReplyTO appointmentForReply = serviceFacade.getAppointmentForReply(appointmentId, targetPersonForCheck);
        for (final AppointmentSlotTO slot : appointmentForReply.getSlots()) {
            if (slot.getSlotNumber() == approvedSlotNumber) {
                fail("Already approved slot shouldn't be available for reply.");
            }
        }
	}

    @Test
    public void testTimeOffset() {
        final AppointmentForEditTO newAppointment = createTestAppointment("new appointment", "appointment description", 1);
        final XMLGregorianCalendar startTime = newAppointment.getSlots().get(0).getStartTime();
        
        assertEquals(10, startTime.getHour());
        assertEquals(0, startTime.getMinute());

        final AppointmentReceipientTO appointmentReceipient = newAppointment.getReceipients().get(0);
        final String receipient = appointmentReceipient.getReceipients().get(0);
        final String targetPerson = appointmentReceipient.getTargetPerson();
        final Long appointmentId = serviceFacade.storeAppointment(newAppointment);
        
        List<AppointmentWithTarget> appointments = serviceFacade.getAssignedAppointments(receipient);
        assertNotNull(getById(appointments, appointmentId));

        final AppointmentForReplyTO forReply = serviceFacade.getAppointmentForReply(appointmentId, targetPerson);
        final XMLGregorianCalendar forReplyStartTime = forReply.getSlots().get(0).getStartTime();
        
        assertEquals(startTime.getHour(), forReplyStartTime.getHour());
        assertEquals(startTime.getMinute(), forReplyStartTime.getMinute());
        assertEquals("timezone offset", 0, forReplyStartTime.getTimezone());
    }
    
	private <AS extends AppointmentSummary> AS getById(final List<AS> appointments, final Long appointmentId) {
		for (final AS appointment : appointments) {
			if (appointment.getAppointmentId() == appointmentId) {
				return appointment;
			}
		}
		return null;
	}
	
	private AppointmentForEditTO createTestAppointmentWithRole(final String sender, final String senderRole,
																final String testSubject, final String description)
	{
		final AppointmentForEditTO appointment = new AppointmentForEditTO();
		appointment.setSubject(testSubject);
		appointment.setDescription(description);
		appointment.setSender(sender);
		appointment.setSenderRole(senderRole);
		
		final AppointmentReceipientTO receipientTO = new AppointmentReceipientTO();
		receipientTO.setTargetPerson("testAppReceiver1");
		receipientTO.setReceipients(Arrays.asList("testGuardian1", "testGuardian2"));
        
		appointment.setReceipients(Arrays.asList(receipientTO));
		appointment.setSlots(createTestSlots(2));
		return appointment;
	}
	
	private AppointmentForEditTO createTestAppointment(final String testSubject, final String description, int numberOfSlots) {
		final AppointmentForEditTO appointment = new AppointmentForEditTO();
		appointment.setSubject(testSubject);
		appointment.setDescription(description);
		appointment.setSender("testAppSender");
		final AppointmentReceipientTO receipientTO = new AppointmentReceipientTO();
		receipientTO.setTargetPerson("testAppReceiver1");
		receipientTO.setReceipients(Arrays.asList("testGuardian1", "testGuardian2"));
        final AppointmentReceipientTO receipientTO2 = new AppointmentReceipientTO();
        receipientTO2.setTargetPerson("testAppReceiver2");
        receipientTO2.setReceipients(Arrays.asList("testGuardian1", "testGuardian3"));
        appointment.setReceipients(
		        Arrays.asList(receipientTO, receipientTO2));
		appointment.setSlots(createTestSlots(numberOfSlots));
		return appointment;
	}

	private List<AppointmentSlotTO> createTestSlots(int numberOfSlots) {
		final List<AppointmentSlotTO> slots = new ArrayList<AppointmentSlotTO>();
		
		final GregorianCalendar calendar = (GregorianCalendar)GregorianCalendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 9);
		calendar.set(Calendar.MINUTE, 30);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		for (int i = 1; i <= numberOfSlots; i++) {
			final AppointmentSlotTO slotTO = new AppointmentSlotTO();
			slotTO.setSlotNumber(i);
			try {
				final DatatypeFactory datatypeFactory = DatatypeFactory.newInstance();
				slotTO.setAppointmentDate(datatypeFactory.newXMLGregorianCalendar(calendar));
				calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) + 30);
				slotTO.setStartTime(datatypeFactory.newXMLGregorianCalendar(calendar));
				calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) + 30);
				slotTO.setEndTime(datatypeFactory.newXMLGregorianCalendar(calendar));
			} catch (DatatypeConfigurationException e) {
				throw new RuntimeException(e);
			}
			slotTO.setLocation("room" + i);
			slotTO.setComment("comment" + i);
			slots.add(slotTO);
		}
		
		return slots;
	}
}

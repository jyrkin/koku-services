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

import org.apache.openjpa.jdbc.sql.FirebirdDictionary;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.ParentRunner;
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
import fi.arcusys.koku.common.service.datamodel.FolderType;
import fi.arcusys.koku.common.service.datamodel.User;
import fi.arcusys.koku.kv.service.MessageServiceFacade;
import fi.arcusys.koku.kv.soa.MessageSummary;
import fi.arcusys.koku.kv.soa.MessageTO;

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
	private MessageServiceFacade messageService;

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
		final AppointmentForEditTO newAppointment = createTestAppointment("new appointment for approve & decline", "appointment description", 3);

		final String sender = newAppointment.getSender();

		final AppointmentReceipientTO receipientTO = newAppointment.getReceipients().get(0);
        final String firstParent = receipientTO.getReceipients().get(0);
        final String secondParent = receipientTO.getReceipients().get(1);
        final String targetPerson = receipientTO.getTargetPerson();

        final AppointmentReceipientTO receipientForDeclineTO = newAppointment.getReceipients().get(1);
        final String firstParentForDecline = receipientForDeclineTO.getReceipients().get(0);
        final String secondParentForDecline = receipientForDeclineTO.getReceipients().get(1);
        final String targetPersonForDecline = receipientForDeclineTO.getTargetPerson();

        assertEquals("In this case one parent is responsible for two kids", firstParent, firstParentForDecline);

		final Long appointmentId = serviceFacade.storeAppointment(newAppointment);

		assertFalse("There are assigned appointments for "+firstParent, serviceFacade.getAssignedAppointments(firstParent).isEmpty());
		assertFalse("There are assigned appointments for "+firstParentForDecline, serviceFacade.getAssignedAppointments(firstParentForDecline).isEmpty());

		assertEquals("Non-answered appointments should be Created", AppointmentSummaryStatus.Created, serviceFacade.getAppointmentForReply(appointmentId, targetPerson).getAppointmentResponse());
		assertEquals("Non-answered appointments should be Created", AppointmentSummaryStatus.Created, serviceFacade.getAppointmentForReply(appointmentId, targetPersonForDecline).getAppointmentResponse());

		assertTrue("There should be no accepted slots", serviceFacade.getAppointment(appointmentId).getAcceptedSlots().isEmpty());

		// firstParent approves the appointment on behalf of targetPerson
		serviceFacade.approveAppointment(targetPerson, firstParent, appointmentId, 1, "approved");

		final AppointmentForReplyTO approvedAppointment = serviceFacade.getAppointmentForReply(appointmentId, targetPerson);
		assertEquals("Approved appointments should be Approved", AppointmentSummaryStatus.Approved, approvedAppointment.getAppointmentResponse());
		assertEquals("Chosen slot should be 1", 1, approvedAppointment.getChosenSlot());

		boolean haveChosenSlot = false;
		for (final AppointmentSlotTO slot : approvedAppointment.getSlots())
		    if (slot.getSlotNumber() == 1) { haveChosenSlot = true; break; }

		assertTrue("Chosen slot must remain to be available for choosing", haveChosenSlot);

        assertFalse("There must be some accepted slots", serviceFacade.getAppointment(appointmentId).getAcceptedSlots().isEmpty());
        assertEquals("Appointment is now Approved from employee perspective", AppointmentSummaryStatus.Approved, serviceFacade.getAppointmentRespondedById(appointmentId, targetPerson).getStatus());

        final String approvalCheckMessage = "After " + firstParent + " approval on behalf of " + targetPerson;
        assertHaveAssignedRespondedOld(approvalCheckMessage, firstParent, appointmentId, true, true, false); // 1 Assigned 1 Responded
        // TODO: Second guardian must see the response in the appropriate list
        //assertHaveAssignedRespondedOld(approvalCheckMessage, secondParent, appointmentId, false, true, false); // 1 Responded
        assertHaveAssignedRespondedOld(approvalCheckMessage, firstParentForDecline, appointmentId, true, true, false); // 1 Assigned 1 Responded
        assertHaveAssignedRespondedOld(approvalCheckMessage, secondParentForDecline, appointmentId, true, false, false); // 1 Assigned

        final int messageCountBeforeDisable = messageService.getMessages(firstParent, FolderType.Inbox).size();

        // sender disables slot 1
        serviceFacade.disableSlot(appointmentId, 1);

        final List<MessageSummary> messages = messageService.getMessages(firstParent, FolderType.Inbox);
        assertEquals("There must me one new message in inbox", 1, messages.size() - messageCountBeforeDisable);

        boolean haveMessage = false;
        for (MessageSummary ms : messages) {
            if (ms.getSubject().contains("peruutettu")) {
                final MessageTO message = messageService.getMessageById(ms.getMessageId());
                final String originalContent = message.getOriginalContent();

                if (originalContent != null && originalContent.contains("ajalla") && originalContent.contains(newAppointment.getSubject()))
                    haveMessage = true;
            }
        }

        assertTrue("Must have slot disable message", haveMessage);

        final String slotRemovalCheckMessage = "After " + sender + " removes slot 1";
        assertHaveAssignedRespondedOld(slotRemovalCheckMessage, firstParent, appointmentId, true, false, true); // 1 Assigned 1 Old
        // TODO: Second guardian must see the response in the appropriate list
        //assertHaveAssignedRespondedOld(slotRemovalCheckMessage, secondParent, appointmentId, false, false, true); // 1 Old
        assertHaveAssignedRespondedOld(slotRemovalCheckMessage, firstParentForDecline, appointmentId, true, false, true); // 1 Assigned 1 Old
        assertHaveAssignedRespondedOld(slotRemovalCheckMessage, secondParentForDecline, appointmentId, true, false, false);  // 1 Assigned

        assertEquals("Appointment should be cancelled after slot removal", AppointmentSummaryStatus.Cancelled, getById(serviceFacade.getOldAppointments(firstParent, 1, 10), appointmentId).getStatus());
        //assertEquals("Appointment should be cancelled after slot removal", AppointmentSummaryStatus.Cancelled, getById(serviceFacade.getOldAppointments(secondParent, 1, 10), appointmentId).getStatus());

        assertEquals("Appointment should NOT be cancelled for unaffected targets", AppointmentSummaryStatus.Created, getById(serviceFacade.getAssignedAppointments(firstParentForDecline, 1, 10), appointmentId).getStatus());
        //assertEquals("Appointment should NOT be cancelled for unaffected targets", AppointmentSummaryStatus.Created, getById(serviceFacade.getAssignedAppointments(secondParentForDecline, 1, 10), appointmentId).getStatus());

        final String rejectedComment = serviceFacade.getAppointment(appointmentId).getUsersRejectedWithComments().get(0).getRejectComment();
        assertTrue("There must be a rejection comment", rejectedComment != null && rejectedComment.length() > 3);

        boolean haveDisabledSlot = false;
        final AppointmentForReplyTO changedAppointment = serviceFacade.getAppointmentForReply(appointmentId, targetPerson);
        for (final AppointmentSlotTO slot : changedAppointment.getSlots())
            if (slot.getSlotNumber() == 1 && slot.isDisabled()) { haveDisabledSlot = true; break; }

        assertTrue("Slot 1 must be disabled", haveDisabledSlot);
        assertTrue("There must be no accepted slots", serviceFacade.getAppointment(appointmentId).getAcceptedSlots().isEmpty());

        //assertEquals("Status (for reply) is created since the chosen slot is disabled", AppointmentSummaryStatus.Created, serviceFacade.getAppointmentForReply(appointmentId, targetPerson).getStatus());

        // Approve different slot
        serviceFacade.approveAppointment(targetPerson, firstParent, appointmentId, 2, "approved");

        assertFalse("There must be an accepted slot", serviceFacade.getAppointment(appointmentId).getAcceptedSlots().isEmpty());
        assertEquals("Slot 2 is approved", 2, serviceFacade.getAppointmentForReply(appointmentId, targetPerson).getChosenSlot());
        //assertEquals("Status after approval is approved", AppointmentSummaryStatus.Approved, serviceFacade.getAppointmentForReply(appointmentId, targetPerson).getStatus());

        final String slotReapprovalCheckMessage =  "After " + firstParent + " reapproves with slot 2 on behalf of " + targetPerson;
        assertHaveAssignedRespondedOld(slotReapprovalCheckMessage, firstParent, appointmentId, true, true, false); // 1 Assigned 1 Responded
        // TODO: Second guardian must see the response in the appropriate list
        //assertHaveAssignedRespondedOld(slotRemovalCheckMessage, secondParent, appointmentId, false, true, false); // 1 Responded
        assertHaveAssignedRespondedOld(slotReapprovalCheckMessage, firstParentForDecline, appointmentId, true, true, false); // 1 Assigned 1 Responded
        assertHaveAssignedRespondedOld(slotReapprovalCheckMessage, secondParentForDecline, appointmentId, true, false, false);  // 1 Assigned

        assertTrue("No rejected appointments after reapproval", serviceFacade.getAppointment(appointmentId).getUsersRejected().isEmpty());

        // firstParent declines the appointment on behalf of targetPersonForDecline
        serviceFacade.declineAppointment(targetPersonForDecline, firstParentForDecline, appointmentId, "declined");

		assertEquals("Declied appointments should be Cancelled", AppointmentSummaryStatus.Cancelled, serviceFacade.getAppointmentForReply(appointmentId, targetPersonForDecline).getAppointmentResponse());

        assertFalse("There should be some rejections", serviceFacade.getAppointment(appointmentId).getUsersRejected().isEmpty());
        assertTrue("There should be targetPersonForDecline in rejections", serviceFacade.getAppointment(appointmentId).getUsersRejected().contains(getKunpoName(targetPersonForDecline)));

        final String declineCheckMessage =  "After " + firstParentForDecline + " declines on behalf of " + targetPersonForDecline;
        assertHaveAssignedRespondedOld(declineCheckMessage, firstParent, appointmentId, false, true, true); // 1 Responded 1  Old
        // TODO: Second guardian must see the response in the appropriate list
        //assertHaveAssignedRespondedOld(declineCheckMessage, secondParent, appointmentId, false, true, false); // 1 Responded
        assertHaveAssignedRespondedOld(declineCheckMessage, firstParentForDecline, appointmentId, false, true, true); // 1 Responded 1 Old
        // TODO: Second guardian must see the response in the appropriate list
        //assertHaveAssignedRespondedOld(declineCheckMessage, secondParentForDecline, appointmentId, false, false, true);  // 1 Old

        assertNull("All appointments should be processed already", getById(serviceFacade.getAssignedAppointments(firstParent), appointmentId));
        assertNull("All appointments should be processed already", getById(serviceFacade.getAssignedAppointments(secondParent), appointmentId));
        assertNull("All appointments should be processed already", getById(serviceFacade.getAssignedAppointments(firstParentForDecline), appointmentId));
        assertNull("All appointments should be processed already", getById(serviceFacade.getAssignedAppointments(secondParentForDecline), appointmentId));

        // firstParent approves the appointment on behalf of targetPersonForDecline
        serviceFacade.approveAppointment(targetPersonForDecline, firstParentForDecline, appointmentId, 3, "reconcidered, approved");

        assertTrue("There are no rejections", serviceFacade.getAppointment(appointmentId).getUsersRejected().isEmpty());
        assertFalse("There are accepted slots", serviceFacade.getAppointment(appointmentId).getAcceptedSlots().isEmpty());
        assertEquals("Appointment is approved", AppointmentSummaryStatus.Approved, serviceFacade.getAppointmentRespondedById(appointmentId, targetPerson).getStatus());

        final String reconsiderCheckMessage =  "After " + firstParentForDecline + " re-considers and approves on behalf of " + targetPersonForDecline;
        assertHaveAssignedRespondedOld(reconsiderCheckMessage, firstParent, appointmentId, false, true, false); // 2 Responded
        // TODO: Second guardian must see the response in the appropriate list
        //assertHaveAssignedRespondedOld(reconsiderCheckMessage, secondParent, appointmentId, false, true, false); // 1 Responded
        assertHaveAssignedRespondedOld(reconsiderCheckMessage, firstParentForDecline, appointmentId, false, true, false); // 2 Responded
        // TODO: Second guardian must see the response in the appropriate list
        //assertHaveAssignedRespondedOld(reconsiderCheckMessage, secondParentForDecline, appointmentId, false, true, false);  // 1 Responded

        // cancel appointment
        serviceFacade.cancelAppointment(targetPerson, firstParent, appointmentId, "cancelled");
        assertEquals("Must be cancelled by targetPerson", AppointmentSummaryStatus.Cancelled, serviceFacade.getAppointmentRespondedById(appointmentId, targetPerson).getStatus());
        assertEquals("Must be approved by targetPersonForDecline", AppointmentSummaryStatus.Approved, serviceFacade.getAppointmentRespondedById(appointmentId, targetPersonForDecline).getStatus());

	}

	private void assertHaveAssignedRespondedOld(final String message, final String parentName, final Long appointmentId, boolean haveAssigned, boolean haveResponded, boolean haveOld) {
	    final String s_haveAssigned = message + " parent " + parentName + " must "+(haveAssigned?"":"not ")+"have "+appointmentId+" in assigned appointments";
        final AppointmentWithTarget o_haveAssigned = getById(serviceFacade.getAssignedAppointments(parentName, 1, 10), appointmentId);

        final String s_haveResponded = message + " parent " + parentName + " must "+(haveResponded?"":"not ")+"have "+appointmentId+" in responded appointments";
        final AppointmentWithTarget o_haveResponded = getById(serviceFacade.getRespondedAppointments(parentName, 1, 10), appointmentId);

        final String s_haveOld = message + " parent " + parentName + " must "+(haveOld?"":"not ")+"have "+appointmentId+" in old appointments";
	    final AppointmentWithTarget o_haveOld = getById(serviceFacade.getOldAppointments(parentName, 1, 10), appointmentId);

	    if (haveAssigned)
            assertNotNull(s_haveAssigned, o_haveAssigned);
        else
            assertNull(s_haveAssigned, o_haveAssigned);

	    if (haveResponded)
            assertNotNull(s_haveResponded, o_haveResponded);
        else
            assertNull(s_haveResponded, o_haveResponded);

        if (haveOld)
            assertNotNull(s_haveOld, o_haveOld);
        else
            assertNull(s_haveOld, o_haveOld);
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

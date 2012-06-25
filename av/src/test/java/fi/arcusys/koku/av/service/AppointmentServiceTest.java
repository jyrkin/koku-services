package fi.arcusys.koku.av.service;

import static junit.framework.Assert.*;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import fi.arcusys.koku.common.service.datamodel.FolderType;
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

	private static String APPOINTMENT_TARGET = "APPOINTMENT_TARGET";
	private static String APPOINTMENT_PARENT = "APPOINTMENT_PARENT";
	private static String APPOINTMENT_STATUS = "APPOINTMENT_STATUS";

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

        final List<String> firstParentKids = Arrays.asList(targetPerson, targetPersonForDecline);
        final List<String> secondParentKids = Arrays.asList(targetPerson);
        final List<String> firstParentForDeclineKids = Arrays.asList(targetPerson, targetPersonForDecline);
        final List<String> secondParentForDeclineKids = Arrays.asList(targetPersonForDecline);

		final Long appointmentId = serviceFacade.storeAppointment(newAppointment);

		final String msg_afterCreation = "After creation";

		assertKunpoAppointment(msg_afterCreation, appointmentId, firstParent, firstParentKids,
		        new HashMap<String, AppointmentSummaryStatus>() {{
		            put(targetPerson, AppointmentSummaryStatus.Created);
		            put(targetPersonForDecline, AppointmentSummaryStatus.Created);
	            }}, null, null);

		assertKunpoAppointment(msg_afterCreation, appointmentId, secondParent, secondParentKids,
                new HashMap<String, AppointmentSummaryStatus>() {{
                    put(targetPerson, AppointmentSummaryStatus.Created);
                }}, null, null);

		assertKunpoAppointment(msg_afterCreation, appointmentId, firstParentForDecline, firstParentForDeclineKids,
                new HashMap<String, AppointmentSummaryStatus>() {{
                    put(targetPerson, AppointmentSummaryStatus.Created);
                    put(targetPersonForDecline, AppointmentSummaryStatus.Created);
                }}, null, null);

        assertKunpoAppointment(msg_afterCreation, appointmentId, secondParentForDecline, secondParentForDeclineKids,
                new HashMap<String, AppointmentSummaryStatus>() {{
                    put(targetPersonForDecline, AppointmentSummaryStatus.Created);
                }}, null, null);

		assertLooraAppointment(msg_afterCreation, sender, appointmentId, true, false, AppointmentSummaryStatus.Created);
		assertLooraAppointmentDetails(msg_afterCreation, appointmentId, AppointmentSummaryStatus.Created);
		assertEditedAppointment(msg_afterCreation, appointmentId, AppointmentSummaryStatus.Created);
		assertRepliedAppointment(msg_afterCreation, appointmentId, targetPerson, AppointmentSummaryStatus.Created, 0);
		assertRepliedAppointment(msg_afterCreation, appointmentId, targetPersonForDecline, AppointmentSummaryStatus.Created, 0);

		assertTrue("There should be no accepted slots", serviceFacade.getAppointment(appointmentId).getAcceptedSlots().isEmpty());

		// firstParent approves the appointment on behalf of targetPerson
		serviceFacade.approveAppointment(targetPerson, firstParent, appointmentId, 1, "approved");

		final AppointmentForReplyTO approvedAppointment = serviceFacade.getAppointmentForReply(appointmentId, targetPerson);

		assertEquals("Chosen slot should be 1", 1, approvedAppointment.getChosenSlot());

		boolean haveChosenSlot = false;
		for (final AppointmentSlotTO slot : approvedAppointment.getSlots())
		    if (slot.getSlotNumber() == 1) { haveChosenSlot = true; break; }

		assertTrue("Chosen slot must remain to be available for choosing", haveChosenSlot);
        assertFalse("There must be some accepted slots", serviceFacade.getAppointment(appointmentId).getAcceptedSlots().isEmpty());

        final String msg_afterApproval = "After " + firstParent + " approval on behalf of " + targetPerson;

        assertKunpoAppointment(msg_afterApproval, appointmentId, firstParent, firstParentKids,
                new HashMap<String, AppointmentSummaryStatus>() {{
                    put(targetPersonForDecline, AppointmentSummaryStatus.Created);
                }},
                new HashMap<String, AppointmentSummaryStatus>() {{
                    put(targetPerson, AppointmentSummaryStatus.Approved);
                }}, null);

        assertKunpoAppointment(msg_afterApproval, appointmentId, secondParent, secondParentKids,
                null,
                new HashMap<String, AppointmentSummaryStatus>() {{
                    put(targetPerson, AppointmentSummaryStatus.Approved);
                }}, null);

        assertKunpoAppointment(msg_afterApproval, appointmentId, firstParentForDecline, firstParentForDeclineKids,
                new HashMap<String, AppointmentSummaryStatus>() {{
                    put(targetPersonForDecline, AppointmentSummaryStatus.Created);
                }},
                new HashMap<String, AppointmentSummaryStatus>() {{
                    put(targetPerson, AppointmentSummaryStatus.Approved);
                }}, null);

        assertKunpoAppointment(msg_afterApproval, appointmentId, secondParentForDecline, secondParentForDeclineKids,
                new HashMap<String, AppointmentSummaryStatus>() {{
                    put(targetPersonForDecline, AppointmentSummaryStatus.Created);
                }}, null, null);

        assertKunpoAppointmentDetails(msg_afterApproval, appointmentId, targetPerson, AppointmentSummaryStatus.Approved);
        assertLooraAppointment(msg_afterApproval, sender, appointmentId, false, true, AppointmentSummaryStatus.InProgress);
        assertLooraAppointmentDetails(msg_afterApproval, appointmentId, AppointmentSummaryStatus.InProgress);
        assertRepliedAppointment(msg_afterApproval, appointmentId, targetPerson, AppointmentSummaryStatus.Approved, 1);
        assertRepliedAppointment(msg_afterApproval, appointmentId, targetPersonForDecline, AppointmentSummaryStatus.Created, 1);

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

        final String msg_afterSlotRemoval = "After " + sender + " removes slot 1";

        assertKunpoAppointment(msg_afterSlotRemoval, appointmentId, firstParent, firstParentKids,
                new HashMap<String, AppointmentSummaryStatus>() {{
                    put(targetPersonForDecline, AppointmentSummaryStatus.Created);
                }},
                null,
                new HashMap<String, AppointmentSummaryStatus>() {{
                    put(targetPerson, AppointmentSummaryStatus.Invalidated);
                }});

        assertKunpoAppointment(msg_afterSlotRemoval, appointmentId, secondParent, secondParentKids,
                null,
                null,
                new HashMap<String, AppointmentSummaryStatus>() {{
                    put(targetPerson, AppointmentSummaryStatus.Invalidated);
                }});

        assertKunpoAppointment(msg_afterSlotRemoval, appointmentId, firstParentForDecline, firstParentForDeclineKids,
                new HashMap<String, AppointmentSummaryStatus>() {{
                    put(targetPersonForDecline, AppointmentSummaryStatus.Created);
                }},
                null,
                new HashMap<String, AppointmentSummaryStatus>() {{
                    put(targetPerson, AppointmentSummaryStatus.Invalidated);
                }});

        assertKunpoAppointment(msg_afterSlotRemoval, appointmentId, secondParentForDecline, secondParentForDeclineKids,
                new HashMap<String, AppointmentSummaryStatus>() {{
                    put(targetPersonForDecline, AppointmentSummaryStatus.Created);
                }}, null, null);

        assertKunpoAppointmentDetails(msg_afterSlotRemoval, appointmentId, targetPerson, AppointmentSummaryStatus.Invalidated);
        assertLooraAppointment(msg_afterSlotRemoval, sender, appointmentId, false, true, AppointmentSummaryStatus.InProgress);
        assertLooraAppointmentDetails(msg_afterSlotRemoval, appointmentId, AppointmentSummaryStatus.InProgress);
        assertRepliedAppointment(msg_afterSlotRemoval, appointmentId, targetPerson, AppointmentSummaryStatus.Invalidated, 1);

        /*final String rejectedComment = serviceFacade.getAppointment(appointmentId).getUsersRejectedWithComments().get(0).getRejectComment();
        assertTrue("There must be a rejection comment", rejectedComment != null && rejectedComment.length() > 3);*/

        boolean haveDisabledSlot = false;
        final AppointmentForReplyTO changedAppointment = serviceFacade.getAppointmentForReply(appointmentId, targetPerson);
        for (final AppointmentSlotTO slot : changedAppointment.getSlots())
            if (slot.getSlotNumber() == 1 && slot.isDisabled()) { haveDisabledSlot = true; break; }

        assertTrue("Slot 1 must be disabled", haveDisabledSlot);
        //assertTrue("There must be no accepted slots", serviceFacade.getAppointment(appointmentId).getAcceptedSlots().isEmpty());

        // Approve different slot
        serviceFacade.approveAppointment(targetPerson, firstParent, appointmentId, 2, "approved");

        assertFalse("There must be an accepted slot", serviceFacade.getAppointment(appointmentId).getAcceptedSlots().isEmpty());
        assertEquals("Slot 2 is approved", 2, serviceFacade.getAppointmentForReply(appointmentId, targetPerson).getChosenSlot());
        //assertEquals("Status after approval is approved", AppointmentSummaryStatus.Approved, serviceFacade.getAppointmentForReply(appointmentId, targetPerson).getStatus());

        final String msg_afterSlotReapproval =  "After " + firstParent + " reapproves with slot 2 on behalf of " + targetPerson;

        assertKunpoAppointment(msg_afterSlotReapproval, appointmentId, firstParent, firstParentKids,
                new HashMap<String, AppointmentSummaryStatus>() {{
                    put(targetPersonForDecline, AppointmentSummaryStatus.Created);
                }},
                new HashMap<String, AppointmentSummaryStatus>() {{
                    put(targetPerson, AppointmentSummaryStatus.Approved);
                }}, null);

        assertKunpoAppointment(msg_afterSlotReapproval, appointmentId, secondParent, secondParentKids,
                null,
                new HashMap<String, AppointmentSummaryStatus>() {{
                    put(targetPerson, AppointmentSummaryStatus.Approved);
                }}, null);

        assertKunpoAppointment(msg_afterSlotReapproval, appointmentId, firstParentForDecline, firstParentForDeclineKids,
                new HashMap<String, AppointmentSummaryStatus>() {{
                    put(targetPersonForDecline, AppointmentSummaryStatus.Created);
                }},
                new HashMap<String, AppointmentSummaryStatus>() {{
                    put(targetPerson, AppointmentSummaryStatus.Approved);
                }}, null);

        assertKunpoAppointment(msg_afterSlotReapproval, appointmentId, secondParentForDecline, secondParentForDeclineKids,
                new HashMap<String, AppointmentSummaryStatus>() {{
                    put(targetPersonForDecline, AppointmentSummaryStatus.Created);
                }}, null, null);

        assertKunpoAppointmentDetails(msg_afterSlotReapproval, appointmentId, targetPerson, AppointmentSummaryStatus.Approved);
        assertLooraAppointment(msg_afterSlotReapproval, sender, appointmentId, false, true, AppointmentSummaryStatus.InProgress);
        assertLooraAppointmentDetails(msg_afterSlotReapproval, appointmentId, AppointmentSummaryStatus.InProgress);
        assertRepliedAppointment(msg_afterSlotReapproval, appointmentId, targetPerson, AppointmentSummaryStatus.Approved, 2);

        assertTrue("No rejected appointments after reapproval", serviceFacade.getAppointment(appointmentId).getUsersRejected().isEmpty());

        // firstParent declines the appointment on behalf of targetPersonForDecline
        serviceFacade.declineAppointment(targetPersonForDecline, firstParentForDecline, appointmentId, "declined");

		//assertEquals("Declied appointments should be Cancelled", AppointmentSummaryStatus.Cancelled, serviceFacade.getAppointmentForReply(appointmentId, targetPersonForDecline).getAppointmentResponse());

        assertFalse("There should be some rejections", serviceFacade.getAppointment(appointmentId).getUsersRejected().isEmpty());
        assertTrue("There should be targetPersonForDecline in rejections", serviceFacade.getAppointment(appointmentId).getUsersRejected().contains(getKunpoName(targetPersonForDecline)));

        final String msg_afterDecline =  "After " + firstParentForDecline + " declines on behalf of " + targetPersonForDecline;

        assertKunpoAppointment(msg_afterDecline, appointmentId, firstParent, firstParentKids,
                null,
                new HashMap<String, AppointmentSummaryStatus>() {{
                    put(targetPerson, AppointmentSummaryStatus.Approved);
                }},
                new HashMap<String, AppointmentSummaryStatus>() {{
                    put(targetPersonForDecline, AppointmentSummaryStatus.Declined);
                }});

        assertKunpoAppointment(msg_afterDecline, appointmentId, secondParent, secondParentKids,
                null,
                new HashMap<String, AppointmentSummaryStatus>() {{
                    put(targetPerson, AppointmentSummaryStatus.Approved);
                }}, null);

        assertKunpoAppointment(msg_afterDecline, appointmentId, firstParentForDecline, firstParentForDeclineKids,
                null,
                new HashMap<String, AppointmentSummaryStatus>() {{
                    put(targetPerson, AppointmentSummaryStatus.Approved);
                }},
                new HashMap<String, AppointmentSummaryStatus>() {{
                    put(targetPersonForDecline, AppointmentSummaryStatus.Declined);
                }});

        assertKunpoAppointment(msg_afterDecline, appointmentId, secondParentForDecline, secondParentForDeclineKids,
                null,
                null,
                new HashMap<String, AppointmentSummaryStatus>() {{
                    put(targetPersonForDecline, AppointmentSummaryStatus.Declined);
                }});

        assertKunpoAppointmentDetails(msg_afterDecline, appointmentId, targetPerson, AppointmentSummaryStatus.Approved);
        assertKunpoAppointmentDetails(msg_afterDecline, appointmentId, targetPersonForDecline, AppointmentSummaryStatus.Declined);
        assertLooraAppointment(msg_afterDecline, sender, appointmentId, false, true, AppointmentSummaryStatus.InProgress);
        assertLooraAppointmentDetails(msg_afterDecline, appointmentId, AppointmentSummaryStatus.InProgress);
        assertRepliedAppointment(msg_afterDecline, appointmentId, targetPerson, AppointmentSummaryStatus.Approved, 2);
        assertRepliedAppointment(msg_afterDecline, appointmentId, targetPersonForDecline, AppointmentSummaryStatus.Declined, 0);

        /*assertNull("All appointments should be processed already", getById(serviceFacade.getAssignedAppointments(firstParent), appointmentId));
        assertNull("All appointments should be processed already", getById(serviceFacade.getAssignedAppointments(secondParent), appointmentId));
        assertNull("All appointments should be processed already", getById(serviceFacade.getAssignedAppointments(firstParentForDecline), appointmentId));
        assertNull("All appointments should be processed already", getById(serviceFacade.getAssignedAppointments(secondParentForDecline), appointmentId));*/

        // firstParent approves the appointment on behalf of targetPersonForDecline
        serviceFacade.approveAppointment(targetPersonForDecline, firstParentForDecline, appointmentId, 3, "reconcidered, approved");

        assertTrue("There are no rejections", serviceFacade.getAppointment(appointmentId).getUsersRejected().isEmpty());
        assertFalse("There are accepted slots", serviceFacade.getAppointment(appointmentId).getAcceptedSlots().isEmpty());
        assertEquals("Appointment is approved", AppointmentSummaryStatus.Approved, serviceFacade.getAppointmentRespondedById(appointmentId, targetPerson).getStatus());

        final String msg_afterReconsider =  "After " + firstParentForDecline + " re-considers and approves on behalf of " + targetPersonForDecline;

        assertKunpoAppointment(msg_afterReconsider, appointmentId, firstParent, firstParentKids,
                null,
                new HashMap<String, AppointmentSummaryStatus>() {{
                    put(targetPerson, AppointmentSummaryStatus.Approved);
                    put(targetPersonForDecline, AppointmentSummaryStatus.Approved);
                }},
                null);

        assertKunpoAppointment(msg_afterReconsider, appointmentId, secondParent, secondParentKids,
                null,
                new HashMap<String, AppointmentSummaryStatus>() {{
                    put(targetPerson, AppointmentSummaryStatus.Approved);
                }},
                null);

        assertKunpoAppointment(msg_afterReconsider, appointmentId, firstParentForDecline, firstParentForDeclineKids,
                null,
                new HashMap<String, AppointmentSummaryStatus>() {{
                    put(targetPerson, AppointmentSummaryStatus.Approved);
                    put(targetPersonForDecline, AppointmentSummaryStatus.Approved);
                }},
                null);

        assertKunpoAppointment(msg_afterReconsider, appointmentId, secondParentForDecline, secondParentForDeclineKids,
                null,
                new HashMap<String, AppointmentSummaryStatus>() {{
                    put(targetPersonForDecline, AppointmentSummaryStatus.Approved);
                }},
                null);

        assertKunpoAppointmentDetails(msg_afterReconsider, appointmentId, targetPerson, AppointmentSummaryStatus.Approved);
        assertKunpoAppointmentDetails(msg_afterReconsider, appointmentId, targetPersonForDecline, AppointmentSummaryStatus.Approved);
        assertLooraAppointment(msg_afterReconsider, sender, appointmentId, false, true, AppointmentSummaryStatus.InProgress);
        assertLooraAppointmentDetails(msg_afterReconsider, appointmentId, AppointmentSummaryStatus.InProgress);
        assertRepliedAppointment(msg_afterReconsider, appointmentId, targetPerson, AppointmentSummaryStatus.Approved, 2);
        assertRepliedAppointment(msg_afterReconsider, appointmentId, targetPersonForDecline, AppointmentSummaryStatus.Approved, 3);

        // cancel appointment
        serviceFacade.cancelWholeAppointment(appointmentId, "cancelled");
        /*assertEquals("Must be declined by targetPerson", AppointmentSummaryStatus.Declined, serviceFacade.getAppointmentRespondedById(appointmentId, targetPerson).getStatus());
        assertEquals("Must be approved by targetPersonForDecline", AppointmentSummaryStatus.Approved, serviceFacade.getAppointmentRespondedById(appointmentId, targetPersonForDecline).getStatus());*/

        final String msg_afterCancel =  "After " + sender + " cancels the whole appointment";

        assertKunpoAppointment(msg_afterCancel, appointmentId, firstParent, firstParentKids,
                null,
                null,
                new HashMap<String, AppointmentSummaryStatus>() {{
                    put(targetPerson, AppointmentSummaryStatus.Cancelled);
                    put(targetPersonForDecline, AppointmentSummaryStatus.Cancelled);
                }});

        assertKunpoAppointment(msg_afterCancel, appointmentId, secondParent, secondParentKids,
                null,
                null,
                new HashMap<String, AppointmentSummaryStatus>() {{
                    put(targetPerson, AppointmentSummaryStatus.Cancelled);
                }});

        assertKunpoAppointment(msg_afterCancel, appointmentId, firstParentForDecline, firstParentForDeclineKids,
                null,
                null,
                new HashMap<String, AppointmentSummaryStatus>() {{
                    put(targetPerson, AppointmentSummaryStatus.Cancelled);
                    put(targetPersonForDecline, AppointmentSummaryStatus.Cancelled);
                }});

        assertKunpoAppointment(msg_afterCancel, appointmentId, secondParentForDecline, secondParentForDeclineKids,
                null,
                null,
                new HashMap<String, AppointmentSummaryStatus>() {{
                    put(targetPersonForDecline, AppointmentSummaryStatus.Cancelled);
                }});

        assertKunpoAppointmentDetails(msg_afterCancel, appointmentId, targetPerson, AppointmentSummaryStatus.Cancelled);
        assertKunpoAppointmentDetails(msg_afterCancel, appointmentId, targetPersonForDecline, AppointmentSummaryStatus.Cancelled);
        assertLooraAppointment(msg_afterCancel, sender, appointmentId, false, true, AppointmentSummaryStatus.Cancelled);
        assertLooraAppointmentDetails(msg_afterCancel, appointmentId, AppointmentSummaryStatus.Cancelled);
        /*assertRepliedAppointment(msg_afterCancel, appointmentId, targetPerson, AppointmentSummaryStatus.Cancelled, 2);
        assertRepliedAppointment(msg_afterCancel, appointmentId, targetPersonForDecline, AppointmentSummaryStatus.Cancelled, 3);*/
	}

	private void assertEditedAppointment(final String message, final Long appointmentId, AppointmentSummaryStatus editStatus) {

	    final String msg = message + " must have " + appointmentId;
	    final AppointmentForEditTO editAppointment = serviceFacade.getAppointmentForEdit(appointmentId);

	    assertEquals(msg + " summary status as " + editStatus, editStatus, editAppointment.getStatus());
    }

	private void assertRepliedAppointment(final String message, final Long appointmentId, final String targetPerson,
	                                            AppointmentSummaryStatus status, int chosenSlot) {

	    final String msg = message + " (replying for " + targetPerson + ") must have " + appointmentId;
        final AppointmentForReplyTO replyAppointment = serviceFacade.getAppointmentForReply(appointmentId, targetPerson);

        assertEquals(msg + " summary status as " + status, status, replyAppointment.getStatus());

        if (status == AppointmentSummaryStatus.Approved || status == AppointmentSummaryStatus.Invalidated)
            assertEquals(msg + " must have slot " + chosenSlot + " chosen", chosenSlot, replyAppointment.getChosenSlot());
    }

	private void assertLooraAppointmentDetails(final String message, final Long appointmentId, AppointmentSummaryStatus status) {
	    final String msg = message + " appointment details (getAppointmentById) must have " + appointmentId + " in status ";

        assertEquals(msg + status, status, serviceFacade.getAppointment(appointmentId).getStatus());
    }

	private void assertLooraAppointment(final String message, final String employeeName, final Long appointmentId,
                                            boolean isCreated, boolean isProcessed,
                                            AppointmentSummaryStatus status) {
	    assertLooraAppointment(message, employeeName, appointmentId, isCreated, isProcessed, (isCreated ? status : null), (isProcessed ? status : null));
	}

	private void assertLooraAppointment(final String message, final String employeeName, final Long appointmentId,
	                                        boolean isCreated, boolean isProcessed,
                                            AppointmentSummaryStatus createdStatus, AppointmentSummaryStatus processedStatus) {
        final String s_haveCreated = message + " employee " + employeeName + " must "+(isCreated?"":"not ")+"have "+appointmentId+" in assigned appointments";
        final AppointmentTO o_haveCreated = getById(serviceFacade.getCreatedAppointments(employeeName, 1, 10, null), appointmentId);

        final String s_haveProcessed = message + " employee " + employeeName + " must "+(isProcessed?"":"not ")+"have "+appointmentId+" in responded appointments";
        final AppointmentTO o_haveProcessed = getById(serviceFacade.getProcessedAppointments(employeeName, 1, 10, null), appointmentId);

        final String s_status = message + " employee " + employeeName + " must have "+appointmentId+" with ";

        if (isCreated) {
            assertNotNull(s_haveCreated, o_haveCreated);
            assertEquals(s_status + createdStatus  + " status in created appointments", createdStatus, o_haveCreated.getStatus());
        } else
            assertNull(s_haveCreated, o_haveCreated);

        if (isProcessed) {
            assertNotNull(s_haveProcessed, o_haveProcessed);
            assertEquals(s_status + processedStatus  + " status in processed appointments", processedStatus, o_haveProcessed.getStatus());
        } else
            assertNull(s_haveProcessed, o_haveProcessed);
	}

	private void assertKunpoAppointmentDetails(final String message, final Long appointmentId, final String targetPerson, AppointmentSummaryStatus status) {
	    final String msg = message + " appointment details (getAppointmentRespondedById) must have " + appointmentId + " in status ";

        assertEquals(msg + status, status, serviceFacade.getAppointmentRespondedById(appointmentId, targetPerson).getStatus());
	}

	private void assertKunpoAppointment(final String message, final Long appointmentId, final String parentName, final List<String> allTargets,
	        final Map<String, AppointmentSummaryStatus> assigned, final Map<String, AppointmentSummaryStatus> responded,
	        final Map<String, AppointmentSummaryStatus> old) {

	    if (assigned != null) {
	        List<String> excludeTargets = new ArrayList<String>();
	        excludeTargets.addAll(allTargets);
	        excludeTargets.removeAll(assigned.keySet());

            assertKunpoAssigned(message, appointmentId, parentName, assigned, excludeTargets);
	    }

        if (responded != null) {
            List<String> excludeTargets = new ArrayList<String>();
            excludeTargets.addAll(allTargets);
            excludeTargets.removeAll(responded.keySet());

            assertKunpoResponded(message, appointmentId, parentName, responded, excludeTargets);
        }

        if (old != null) {
            List<String> excludeTargets = new ArrayList<String>();
            excludeTargets.addAll(allTargets);
            excludeTargets.removeAll(old.keySet());

            assertKunpoOld(message, appointmentId, parentName, old, excludeTargets);
        }
	}

	private void assertKunpoAssigned(final String message, final Long appointmentId, final String parentName, final Map<String, AppointmentSummaryStatus> checkList, final List<String> excludeTargets) {
	    assertKunpoAppointmentList(message+": Assigned appointments for "+parentName, appointmentId, checkList, excludeTargets, serviceFacade.getAssignedAppointments(parentName, 1, 10));
	}

	private void assertKunpoResponded(final String message, final Long appointmentId, final String parentName, final Map<String, AppointmentSummaryStatus> checkList, final List<String> excludeTargets) {
	    assertKunpoAppointmentList(message+": Responded appointments for "+parentName, appointmentId, checkList, excludeTargets, serviceFacade.getRespondedAppointments(parentName, 1, 10));
    }

	private void assertKunpoOld(final String message, final Long appointmentId, final String parentName, final Map<String, AppointmentSummaryStatus> checkList, final List<String> excludeTargets) {
	    assertKunpoAppointmentList(message+": Old appointments "+parentName, appointmentId, checkList, excludeTargets, serviceFacade.getOldAppointments(parentName, 1, 10));
    }

	private void assertKunpoAppointmentList(final String message, final Long appointmentId,
	        final Map<String, AppointmentSummaryStatus> checkList, final List<String> excludeTargets, final List<AppointmentWithTarget> appointmentList) {

	    // Make sure we have appointments for appropriate targetPersons with appropriate statuses
	    if (checkList != null) {
	        for (final String targetPerson: checkList.keySet()) {
	            AppointmentWithTarget appointment = null;

	            for (AppointmentWithTarget a : appointmentList)
	                if (a.getAppointmentId() == appointmentId && a.getTargetPerson().equals(targetPerson))
	                    appointment = a;

                assertNotNull(message + ": Cannot find appointment in list for target person "+targetPerson, appointment);

                final AppointmentSummaryStatus status = checkList.get(targetPerson);
                assertEquals(message + ": Status must be "+status+" for target person "+targetPerson, status, appointment.getStatus());
            }
	    }

	    // Make sure targetPersons are excluded from this list
	    if (excludeTargets != null)
	        for (final String targetPerson : excludeTargets)
	            for (AppointmentWithTarget a : appointmentList)
                    assertFalse(message + ": Must not contain "+targetPerson, a.getAppointmentId() == appointmentId && a.getTargetPerson().equals(targetPerson));
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

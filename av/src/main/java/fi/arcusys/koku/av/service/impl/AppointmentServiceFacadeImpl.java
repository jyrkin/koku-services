package fi.arcusys.koku.av.service.impl;

import static fi.arcusys.koku.common.service.AbstractEntityDAO.*;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.xml.datatype.XMLGregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fi.arcusys.koku.av.service.AppointmentServiceFacade;
import fi.arcusys.koku.av.soa.AppointmentForEditTO;
import fi.arcusys.koku.av.soa.AppointmentForReplyTO;
import fi.arcusys.koku.av.soa.AppointmentReceipientTO;
import fi.arcusys.koku.av.soa.AppointmentRespondedTO;
import fi.arcusys.koku.av.soa.AppointmentSlotTO;
import fi.arcusys.koku.av.soa.AppointmentSummary;
import fi.arcusys.koku.av.soa.AppointmentSummaryStatus;
import fi.arcusys.koku.av.soa.AppointmentTO;
import fi.arcusys.koku.av.soa.AppointmentWithTarget;
import fi.arcusys.koku.common.service.AbstractEntityDAO;
import fi.arcusys.koku.common.service.AppointmentDAO;
import fi.arcusys.koku.common.service.CalendarUtil;
import fi.arcusys.koku.common.service.KokuSystemNotificationsService;
import fi.arcusys.koku.common.service.TargetPersonDAO;
import fi.arcusys.koku.common.service.UserDAO;
import fi.arcusys.koku.common.service.datamodel.Appointment;
import fi.arcusys.koku.common.service.datamodel.AppointmentResponse;
import fi.arcusys.koku.common.service.datamodel.AppointmentResponseStatus;
import fi.arcusys.koku.common.service.datamodel.AppointmentSlot;
import fi.arcusys.koku.common.service.datamodel.AppointmentStatus;
import fi.arcusys.koku.common.service.datamodel.TargetPerson;
import fi.arcusys.koku.common.service.datamodel.User;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Jul 22, 2011
 */
@Stateless
public class AppointmentServiceFacadeImpl implements AppointmentServiceFacade {

    private static final String APPOINTMENT_CANCELLED_FOR_TARGET_BODY = "Aihe: {0}. Henkilön {1} tapaaminen on peruuttu käyttäjän {2} toimesta";
    private static final String APPOINTMENT_CANCELLED_WHOLE_BODY = "Tapaaminen \"{0}\" on peruutettu";
    private static final String APPOINTMENT_CANCELLED_SUBJECT = "Tapaaminen on peruutettu";

    private final static Logger logger = LoggerFactory.getLogger(AppointmentServiceFacadeImpl.class);
    
	@EJB
	private AppointmentDAO appointmentDAO;
	
	@EJB
	private UserDAO userDao;
	
	@EJB
	private TargetPersonDAO targetPersonDao;
	
	@EJB
	private KokuSystemNotificationsService notificationService;

	/**
	 * @param appointmentId
	 * @param slotNumber
	 * @param comment
	 */
	@Override
	public void approveAppointment(final String targetPersonUid, final String userUid, final Long appointmentId, final int slotNumber, final String comment) {
		final Appointment appointment = loadAppointment(appointmentId);
        
        if(appointment.getSlotByNumber(slotNumber) == null) {
            throw new IllegalStateException("There is no slot with number " + slotNumber + " in appointment id = " + appointmentId);
        }

        final AppointmentResponse response = processReply(targetPersonUid, userUid, comment, appointment);
        response.setSlotNumber(slotNumber);
        response.setStatus(AppointmentResponseStatus.Accepted);
		
		appointmentDAO.update(appointment);
	}

	/**
	 * @param appointmentId
	 * @param comment
	 */
	@Override
	public void declineAppointment(final String targetPersonUid, final String userUid, final Long appointmentId, final String comment) {
		final Appointment appointment = loadAppointment(appointmentId);
        
        final AppointmentResponse response = processReply(targetPersonUid, userUid, comment, appointment);
        response.setStatus(AppointmentResponseStatus.Rejected);
		
		appointmentDAO.update(appointment);
	}

    private AppointmentResponse processReply(final String targetPersonUid,
            final String userUid, final String comment,
            final Appointment appointment) {
        if (appointment.getStatus() != AppointmentStatus.Created) {
			throw new IllegalStateException("Appointment for approval (id = " + appointment.getId() + ") should be in status 'Created' but it has status " + appointment.getStatus());
		}
        
        for (final AppointmentResponse response : appointment.getResponses() ) {
            if (response.getTarget().getTargetUser().getUid().equals(targetPersonUid)) {
                throw new IllegalStateException("Apppointment id " + appointment.getId() + " already have response for targetPerson " + targetPersonUid);
            }
        }
		
		final TargetPerson targetPerson = appointment.getTargetPersonByUid(targetPersonUid);
		if(targetPerson == null) {
			throw new IllegalStateException("There is no target person with uid '" + userUid + "' in appointment id = " + appointment.getId());
		}
		
		final User replier = targetPerson.getGuardianByUid(userUid);
        if(replier == null) {
            throw new IllegalStateException("There is no guardian with uid '" + userUid + "' for target person " + targetPersonUid);
        }

		final AppointmentResponse response = new AppointmentResponse();
		response.setReplier(replier);
		response.setComment(comment);
		response.setTarget(targetPerson);
		response.setAppointment(appointment);
		appointment.getResponses().add(response);
        return response;
    }

	/**
	 * @param appointmentId
	 * @return
	 */
	@Override
	public AppointmentTO getAppointment(final Long appointmentId) {
		final Appointment appointment = loadAppointment(appointmentId);
		
		final AppointmentTO appointmentTO = new AppointmentTO();
		convertAppointmentToDTO(appointment, appointmentTO);
		
		appointmentTO.setStatus(AppointmentSummaryStatus.valueOf(getSummaryAppointmentStatus(appointment)));

        appointmentTO.setRecipients(getReceipientsDTOByAppointment(appointment));

		final HashMap<Integer, String> acceptedSlots = new HashMap<Integer, String>();
        final List<String> usersRejected = new ArrayList<String>();
		
		for (final AppointmentResponse response : appointment.getResponses()) {
		    final String targetPersonUid = response.getTarget().getTargetUser().getUid();
            if (response.getStatus() == AppointmentResponseStatus.Accepted) {
	            acceptedSlots.put(response.getSlotNumber(), targetPersonUid);
		    } else {
		        usersRejected.add(targetPersonUid);
		    }
		}
        appointmentTO.setAcceptedSlots(acceptedSlots);
        appointmentTO.setUsersRejected(usersRejected);
		
		appointmentTO.setSlots(getSlotTOsByAppointment(appointment));
		
		return appointmentTO;
	}

    private List<AppointmentReceipientTO> getReceipientsDTOByAppointment(
            final Appointment appointment) {
        final List<AppointmentReceipientTO> recipients = new ArrayList<AppointmentReceipientTO>();
        for (final TargetPerson receipient : appointment.getRecipients()) {
            final AppointmentReceipientTO receipientTO = new AppointmentReceipientTO();
            receipientTO.setTargetPerson(receipient.getTargetUser().getUid());
            final List<String> guardians = new ArrayList<String>();
            for (final User guardian : receipient.getGuardians()) {
                guardians.add(guardian.getUid());
            }
            receipientTO.setReceipients(guardians);
            recipients.add(receipientTO);
        }
        return recipients;
    }

	private List<AppointmentSlotTO> getSlotTOsByAppointment(final Appointment appointment) {
		final List<AppointmentSlotTO> result = new ArrayList<AppointmentSlotTO>();
		for (final AppointmentSlot slot : appointment.getSlots()) {
			result.add(getSlotTOBySlot(slot));
		}
		Collections.sort(result, new Comparator<AppointmentSlotTO>() {

            @Override
            public int compare(AppointmentSlotTO o1, AppointmentSlotTO o2) {
                final int datesCompare = o1.getAppointmentDate().compare(o2.getAppointmentDate());
                if (datesCompare != 0) {
                    return datesCompare;
                } else {
                    return o1.getStartTime().compare(o2.getStartTime());
                }
            }
        });
		return result;
	}

    private AppointmentSlotTO getSlotTOBySlot(final AppointmentSlot slot) {
        final AppointmentSlotTO slotTO = new AppointmentSlotTO();
        slotTO.setSlotNumber(slot.getSlotNumber());
        slotTO.setAppointmentDate(CalendarUtil.getXmlDate(slot.getAppointmentDate()));
        slotTO.setStartTime(CalendarUtil.getXmlTime(slot.getAppointmentDate(), slot.getStartTime()));
        slotTO.setEndTime(CalendarUtil.getXmlTime(slot.getAppointmentDate(), slot.getEndTime()));
        slotTO.setLocation(slot.getLocation());
        slotTO.setComment(slot.getComment());
        return slotTO;
    }

	/**
	 * @param userUid
	 * @param statuses
	 * @return
	 */
	@Override
	public List<AppointmentSummary> getAppointments(final String userUid, final Set<AppointmentStatus> statuses) {
		return getAppointments(userUid, statuses, FIRST_RESULT_NUMBER, FIRST_RESULT_NUMBER + MAX_RESULTS_COUNT - 1);
	}

	@Override
	public List<AppointmentSummary> getAppointments(final String userUid, final Set<AppointmentStatus> statuses, final int startNum, final int maxNum) {
		final List<AppointmentSummary> result = new ArrayList<AppointmentSummary>();
		for (final Appointment appointment : appointmentDAO.getUserAppointments(userDao.getOrCreateUser(userUid), statuses, startNum, maxNum - startNum + 1)) {
			final AppointmentSummary appointmentTO = convertAppointmentToDTO(appointment, new AppointmentSummary());
			appointmentTO.setStatus(AppointmentSummaryStatus.valueOf(getSummaryAppointmentStatus(appointment)));
            result.add(appointmentTO);
		}
		return result;
	}

	/**
	 * @param userUid
	 * @return
	 */
	@Override
	public List<AppointmentWithTarget> getAssignedAppointments(final String userUid) {
		return getAssignedAppointments(userUid, FIRST_RESULT_NUMBER, FIRST_RESULT_NUMBER + MAX_RESULTS_COUNT - 1);		
	}
	
	@Override
	public List<AppointmentWithTarget> getAssignedAppointments(final String userUid, final int startNum, final int maxNum) {
		final List<AppointmentWithTarget> result = new ArrayList<AppointmentWithTarget>();
		for (final Appointment appointment : appointmentDAO.getAssignedAppointments(userDao.getOrCreateUser(userUid), startNum, maxNum - startNum + 1)) {
		    for (final TargetPerson target : appointment.getRecipients()) {
		        if (target.getGuardianByUid(userUid) != null) {
		            final AppointmentWithTarget appointmentTO = convertAppointmentToDTO(appointment, new AppointmentWithTarget());
		            appointmentTO.setTargetPerson(target.getTargetUser().getUid());
                    result.add(appointmentTO);
		        }
		    }
		}
		return result;
	}

	private <AS extends AppointmentSummary> AS convertAppointmentToDTO(final Appointment appointment, final AS appointmentSummary) {
		appointmentSummary.setAppointmentId(appointment.getId());
		appointmentSummary.setDescription(appointment.getDescription());
		appointmentSummary.setSubject(appointment.getSubject());
		appointmentSummary.setSender(appointment.getSender().getUid());
		appointmentSummary.setStatus(AppointmentSummaryStatus.valueOf(appointment.getStatus()));
		
		return appointmentSummary;
	}

    private AppointmentStatus getSummaryAppointmentStatus(
            final Appointment appointment) {
        if (appointment.getStatus() != AppointmentStatus.Cancelled && appointment.getResponses() != null && !appointment.getResponses().isEmpty()) {
            for (final AppointmentResponse response : appointment.getResponses()) {
                if (response.getStatus() == AppointmentResponseStatus.Accepted) {
                    return AppointmentStatus.Approved;
                }
            }
            return AppointmentStatus.Cancelled;
        }
        return appointment.getStatus();
    }
	
	private Appointment fillAppointmentByDto(final AppointmentForEditTO appointmentTO, final Appointment appointment) {
		appointment.setDescription(appointmentTO.getDescription());
		appointment.setSubject(appointmentTO.getSubject());
		appointment.setSender(userDao.getOrCreateUser(appointmentTO.getSender()));
		final Set<TargetPerson> recipients = new HashSet<TargetPerson>();
		for (final AppointmentReceipientTO receipient : appointmentTO.getReceipients()) {
			recipients.add(targetPersonDao.getOrCreateTargetPerson(receipient.getTargetPerson(), receipient.getReceipients()));
		}
		appointment.setRecipients(recipients);
		
		final Map<Integer, AppointmentSlot> oldSlots = new HashMap<Integer, AppointmentSlot>();
		for (final AppointmentSlot slot : appointment.getSlots()) {
			oldSlots.put(slot.getSlotNumber(), slot);
		}
		final Set<AppointmentSlot> slots = new HashSet<AppointmentSlot>();
		for (final AppointmentSlotTO slotTO : appointmentTO.getSlots()) {
			final AppointmentSlot slot;
			if (oldSlots.containsKey(slotTO.getSlotNumber())) {
				slot = oldSlots.get(slotTO.getSlotNumber());
				oldSlots.remove(slotTO.getSlotNumber());
			} else {
				slot = new AppointmentSlot();
			}
			slot.setAppointmentDate(slotTO.getAppointmentDate().toGregorianCalendar().getTime());
			slot.setComment(slotTO.getComment());
			slot.setStartTime(getMinutes(slotTO.getStartTime()));
			slot.setEndTime(getMinutes(slotTO.getEndTime()));
			slot.setLocation(slotTO.getLocation());
			slot.setSlotNumber(slotTO.getSlotNumber());
			slots.add(slot);
		}
		appointment.setSlots(slots);
		
		return appointment;
	}

	private int getMinutes(final XMLGregorianCalendar calendar) {
		return calendar.getHour() * 60 + calendar.getMinute();
	}

	/**
	 * @param appointment
	 * @return
	 */
	@Override
	public Long storeAppointment(AppointmentForEditTO appointmentTO) {
		final Appointment appointment;
		if (appointmentTO.getAppointmentId() > 0) {
			final long appointmentId = appointmentTO.getAppointmentId();
            appointment = loadAppointment(appointmentId);
		} else {
			appointment = new Appointment();
		}
		fillAppointmentByDto(appointmentTO, appointment);
		if (appointment.getId() == null) {
			return appointmentDAO.create(appointment).getId();
		} else {
			appointmentDAO.update(appointment);
			return appointment.getId();
		}
	}

    /**
	 * @param appointmentId
	 */
	@Override
	public void removeAppointment(long appointmentId) {
		final Appointment appointment = loadAppointment(appointmentId);
		appointment.setStatus(AppointmentStatus.Cancelled);
		appointmentDAO.update(appointment);
	}

    /**
	 * @param user
	 * @param statuses
	 * @return
	 */
	@Override
	public int getTotalAppointments(String user, Set<AppointmentStatus> statuses) {
		return getIntValue(appointmentDAO.getTotalAppointments(userDao.getOrCreateUser(user), statuses));
	}

	/**
	 * @param user
	 * @return
	 */
	@Override
	public int getTotalAssignedAppointments(String user) {
		return getIntValue(appointmentDAO.getTotalAssignedAppointments(userDao.getOrCreateUser(user)));
	}

	private int getIntValue(final Long value) {
		return value != null ? value.intValue() : 0;
	}

    /**
     * @param appointmentId
     * @return
     */
    @Override
    public AppointmentForEditTO getAppointmentForEdit(Long appointmentId) {
        final AppointmentForEditTO appointmentTO = new AppointmentForEditTO();
        final Appointment appointment = fillAppointmentTOForReply(appointmentId, appointmentTO);
        
        appointmentTO.setReceipients(getReceipientsDTOByAppointment(appointment));

        return appointmentTO;
    }

    private Appointment fillAppointmentTOForReply(final Long appointmentId, final AppointmentForReplyTO appointmentTO) {
        final Appointment appointment = loadAppointment(appointmentId);
        
        convertAppointmentToDTO(appointment, appointmentTO);
        appointmentTO.setSlots(getSlotTOsByAppointment(appointment));
        return appointment;
    }

    private Appointment loadAppointment(final Long appointmentId) {
        final Appointment appointment = appointmentDAO.getById(appointmentId);
        if (appointment == null) {
            throw new IllegalArgumentException("Appointment id " + appointmentId + " not found.");
        }
        return appointment;
    }

    /**
     * @param appointmentId
     * @param targetPersonUid
     * @return
     */
    @Override
    public AppointmentForReplyTO getAppointmentForReply(Long appointmentId, String targetPersonUid) {
        final AppointmentForReplyTO appointmentTO = new AppointmentForReplyTO();
        final Appointment appointment = fillAppointmentTOForReply(appointmentId, appointmentTO);
        
        if (appointment.getTargetPersonByUid(targetPersonUid) == null) {
            throw new IllegalArgumentException("Appointment id " + appointmentId + " doesn't have target person " + targetPersonUid);
        }
        
        if (appointment.getResponseForTargetPerson(targetPersonUid) != null) {
            throw new IllegalArgumentException("Appointment id " + appointmentId + " already have response for target person " + targetPersonUid);
        }
        
        return appointmentTO;
    }

    /**
     * @param userUid
     * @param startNum
     * @param maxNum
     * @return
     */
    @Override
    public List<AppointmentWithTarget> getRespondedAppointments(String userUid, int startNum, int maxNum) {
        final List<AppointmentWithTarget> result = new ArrayList<AppointmentWithTarget>();
        for (final AppointmentResponse response : appointmentDAO.getAppointmentResponses(userDao.getOrCreateUser(userUid), startNum, maxNum - startNum + 1)) {
            final AppointmentWithTarget appointmentTO = new AppointmentWithTarget();
            convertAppointmentToDTO(response.getAppointment(), appointmentTO);
            appointmentTO.setTargetPerson(response.getTarget().getTargetUser().getUid());
            appointmentTO.setStatus(getAppointmentStatusByResponse(response));
            result.add(appointmentTO);
        }
        return result;
    }

    /**
     * @param user
     * @return
     */
    @Override
    public int getTotalRespondedAppointments(String user) {
        return getIntValue(appointmentDAO.getTotalRespondedAppointments(userDao.getOrCreateUser(user)));
    }

    /**
     * @param user
     * @return
     */
    @Override
    public int getTotalCreatedAppointments(String user) {
        return getIntValue(appointmentDAO.getTotalCreatedAppointments(userDao.getOrCreateUser(user)));
    }

    /**
     * @param user
     * @return
     */
    @Override
    public int getTotalProcessedAppointments(String user) {
        return getIntValue(appointmentDAO.getTotalProcessedAppointments(userDao.getOrCreateUser(user)));
    }

    /**
     * @param user
     * @param startNum
     * @param maxNum
     * @return
     */
    @Override
    public List<AppointmentSummary> getCreatedAppointments(String user, int startNum, int maxNum) {
        return getEmployeeSummaryByAppointments(appointmentDAO.getCreatedAppointments(userDao.getOrCreateUser(user), startNum, maxNum - startNum + 1));
    }

    /**
     * @param user
     * @param startNum
     * @param maxNum
     * @return
     */
    @Override
    public List<AppointmentSummary> getProcessedAppointments(String user, int startNum, int maxNum) {
        return getEmployeeSummaryByAppointments(appointmentDAO.getProcessedAppointments(userDao.getOrCreateUser(user), startNum, maxNum - startNum + 1));
    }

    private List<AppointmentSummary> getEmployeeSummaryByAppointments(List<Appointment> appointments) {
        final List<AppointmentSummary> result = new ArrayList<AppointmentSummary>();
        for (final Appointment appointment : appointments) {
            final AppointmentSummary appointmentTO = convertAppointmentToDTO(appointment, new AppointmentSummary());
            appointmentTO.setStatus(AppointmentSummaryStatus.valueOf(getSummaryAppointmentStatus(appointment)));
            result.add(appointmentTO);
        }
        return result;
    }

    /**
     * @param appointmentId
     * @return
     */
    @Override
    public AppointmentRespondedTO getAppointmentRespondedById(long appointmentId, final String targetPerson) {
        final AppointmentResponse response = getAppointmentResponse(appointmentId, targetPerson);
        
        final Appointment appointment = response.getAppointment();
        
        final AppointmentRespondedTO appointmentTO = new AppointmentRespondedTO();
        convertAppointmentToDTO(appointment, appointmentTO);
        appointmentTO.setTargetPerson(targetPerson);
        appointmentTO.setStatus(getAppointmentStatusByResponse(response));
        appointmentTO.setReplier(response.getReplier().getUid());
        appointmentTO.setReplierComment(response.getComment());
        appointmentTO.setApprovedSlot(getSlotTOBySlot(appointment.getSlotByNumber(response.getSlotNumber())));
        
        return appointmentTO;
    }

    private AppointmentSummaryStatus getAppointmentStatusByResponse(
            final AppointmentResponse response) {
        return response.getStatus() == AppointmentResponseStatus.Accepted && response.getAppointment().getStatus() != AppointmentStatus.Cancelled ?
                AppointmentSummaryStatus.Approved :
                AppointmentSummaryStatus.Cancelled;
    }

    private AppointmentResponse getAppointmentResponse(long appointmentId,
            final String targetPerson) {
        final Appointment appointment = loadAppointment(appointmentId);
        
        final AppointmentResponse response = appointment.getResponseForTargetPerson(targetPerson);
        if (response == null) {
            throw new IllegalArgumentException("Can't find response to appointment ID " + appointmentId + " for target person " + targetPerson);
        }
        return response;
    }

    /**
     * @param targetUser
     * @param user
     * @param appointmentId
     * @param comment
     */
    @Override
    public void cancelAppointment(String targetUser, String user, long appointmentId, String comment) {
        final AppointmentResponse response = getAppointmentResponse(appointmentId, targetUser);
        final String replierUid = response.getReplier().getUid();
        if (!replierUid.equals(user)) {
            logger.warn("Appointment id " + appointmentId + " replied by " + replierUid + " but cancelled by " + user);
        }
        
        response.setStatus(AppointmentResponseStatus.Rejected);
        response.setComment(comment);
        appointmentDAO.update(response.getAppointment());
        notificationService.sendNotification(AppointmentServiceFacadeImpl.APPOINTMENT_CANCELLED_SUBJECT, 
                Collections.singletonList(response.getAppointment().getSender().getUid()), 
                MessageFormat.format(AppointmentServiceFacadeImpl.APPOINTMENT_CANCELLED_FOR_TARGET_BODY, new Object[] {response.getAppointment().getSubject(), targetUser, user}));
    }

    /**
     * @param appointmentId
     * @param comment
     */
    @Override
    public void cancelWholeAppointment(long appointmentId, String comment) {
        final Appointment appointment = loadAppointment(appointmentId);
        
        appointment.setStatus(AppointmentStatus.Cancelled);
        appointmentDAO.update(appointment);

        final Set<String> notificationReceivers = new HashSet<String>();
        for (final TargetPerson target : appointment.getRecipients()) {
            final AppointmentResponse response = appointment.getResponseForTargetPerson(target.getTargetUser().getUid());
            if (response != null) {
                notificationReceivers.add(response.getReplier().getUid());
            } else {
                for (final User guardian : target.getGuardians()) {
                    notificationReceivers.add(guardian.getUid());
                }
            }
        }
        notificationService.sendNotification(AppointmentServiceFacadeImpl.APPOINTMENT_CANCELLED_SUBJECT, 
                new ArrayList<String>(notificationReceivers), 
                MessageFormat.format(AppointmentServiceFacadeImpl.APPOINTMENT_CANCELLED_WHOLE_BODY, new Object[] {appointment.getSubject()}));
    }
}

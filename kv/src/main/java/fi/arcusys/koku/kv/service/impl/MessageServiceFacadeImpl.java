package fi.arcusys.koku.kv.service.impl;

import static fi.arcusys.koku.common.service.AbstractEntityDAO.FIRST_RESULT_NUMBER;
import static fi.arcusys.koku.common.service.AbstractEntityDAO.MAX_RESULTS_COUNT;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.xml.datatype.XMLGregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fi.arcusys.koku.common.external.CustomerServiceDAO;
import fi.arcusys.koku.common.external.EmailServiceDAO;
import fi.arcusys.koku.common.service.CalendarUtil;
import fi.arcusys.koku.common.service.KokuSystemNotificationsService;
import fi.arcusys.koku.common.service.MessageDAO;
import fi.arcusys.koku.common.service.MessageFolderDAO;
import fi.arcusys.koku.common.service.MessageRefDAO;
import fi.arcusys.koku.common.service.RequestDAO;
import fi.arcusys.koku.common.service.RequestTemplateDAO;
import fi.arcusys.koku.common.service.ResponseDAO;
import fi.arcusys.koku.common.service.ScheduledTaskExecutor;
import fi.arcusys.koku.common.service.UserDAO;
import fi.arcusys.koku.common.service.datamodel.Folder;
import fi.arcusys.koku.common.service.datamodel.FolderType;
import fi.arcusys.koku.common.service.datamodel.Message;
import fi.arcusys.koku.common.service.datamodel.MessageRef;
import fi.arcusys.koku.common.service.datamodel.MultipleChoice;
import fi.arcusys.koku.common.service.datamodel.Question;
import fi.arcusys.koku.common.service.datamodel.QuestionType;
import fi.arcusys.koku.common.service.datamodel.Request;
import fi.arcusys.koku.common.service.datamodel.RequestTemplate;
import fi.arcusys.koku.common.service.datamodel.Response;
import fi.arcusys.koku.common.service.datamodel.User;
import fi.arcusys.koku.common.service.dto.Criteria;
import fi.arcusys.koku.common.service.dto.MessageQuery;
import fi.arcusys.koku.common.soa.Role;
import fi.arcusys.koku.common.soa.UserInfo;
import fi.arcusys.koku.common.soa.UsersAndGroupsService;
import fi.arcusys.koku.kv.service.MessageServiceFacade;
import fi.arcusys.koku.kv.soa.Answer;
import fi.arcusys.koku.kv.soa.AnswerTO;
import fi.arcusys.koku.kv.soa.MessageStatus;
import fi.arcusys.koku.kv.soa.MessageSummary;
import fi.arcusys.koku.kv.soa.MessageTO;
import fi.arcusys.koku.kv.soa.MultipleChoiceTO;
import fi.arcusys.koku.kv.soa.QuestionTO;
import fi.arcusys.koku.kv.soa.RequestProcessingTO;
import fi.arcusys.koku.kv.soa.RequestShortSummary;
import fi.arcusys.koku.kv.soa.RequestSummary;
import fi.arcusys.koku.kv.soa.RequestTO;
import fi.arcusys.koku.kv.soa.RequestTemplateExistenceStatus;
import fi.arcusys.koku.kv.soa.RequestTemplateSummary;
import fi.arcusys.koku.kv.soa.RequestTemplateTO;
import fi.arcusys.koku.kv.soa.RequestTemplateVisibility;
import fi.arcusys.koku.kv.soa.ResponseDetail;
import fi.arcusys.koku.kv.soa.ResponseSummary;
import fi.arcusys.koku.kv.soa.ResponseTO;
import fi.koku.settings.KoKuPropertiesUtil;

/**
 * Service facade implementation for all business methods in KV functional area.
 * 
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * May 18, 2011
 */
@Stateless
@Local({MessageServiceFacade.class, KokuSystemNotificationsService.class, ScheduledTaskExecutor.class})
public class MessageServiceFacadeImpl implements MessageServiceFacade, KokuSystemNotificationsService, ScheduledTaskExecutor {
	private final static Logger logger = LoggerFactory.getLogger(MessageServiceFacadeImpl.class);
	
	public static final String SYSTEM_USER_NAME_FOR_NOTIFICATIONS = "KohtiKumppanuutta";
    public static final UserInfo SYSTEM_USER_INFO = new UserInfo(SYSTEM_USER_NAME_FOR_NOTIFICATIONS, SYSTEM_USER_NAME_FOR_NOTIFICATIONS);
	
	@EJB
	private MessageDAO messageDao;

	@EJB
	private MessageRefDAO messageRefDao;

	@EJB
	private UserDAO userDao;
	
	@EJB
	private MessageFolderDAO folderDAO;
	
	@EJB
	private RequestDAO requestDAO;

    @EJB
    private RequestTemplateDAO requestTemplateDAO;

    @EJB
	private ResponseDAO responseDAO;

    @EJB
    private UsersAndGroupsService usersService;
    
    @EJB
    private CustomerServiceDAO customerDao;
    
    @EJB
    private EmailServiceDAO emailDao;
    
    private String notificationTemplate = "{2}";

    private static final String REQUEST_REPLIED_BODY = "request.replied.body";
    private static final String REQUEST_REPLIED_SUBJECT = "request.replied.subject";
    private static final String REQUEST_NOT_REPLIED_REMINDER_BODY = "request.not_replied_reminder.body";
    private static final String REQUEST_NOT_REPLIED_REMINDER_SUBJECT = "request.not_replied_reminder.subject";
    private static final String MESSAGES_ARCHIVED_BODY = "messages.archived.body";
    private static final String MESSAGES_ARCHIVED_SUBJECT = "messages.archived.subject";
    // email messages
    private static final String EMAIL_NEW_REQUEST_RECEIVED_BODY = "email.new.request.received.body";
    private static final String EMAIL_NEW_REQUEST_RECEIVED_SUBJECT = "email.new.request.received.subject";
    private static final String EMAIL_NEW_MESSAGE_RECEIVED_BODY = "email.new.message.received.body";
    private static final String EMAIL_NEW_MESSAGE_RECEIVED_SUBJECT = "email.new.message.received.subject";
    
    private final static String EMAIL_LINKS_MESSAGE_INBOX_PATH = "navigationPortlet.portlet.absolute.path";
    private final static String EMAIL_LINKS_NEW_REQUEST_PATH = "navigationPortlet.link.requests.recievedRequests";
    
    private String notificationsBundleName = "kv_messages.msg";
    private Properties messageTemplates;
    
    @PostConstruct
    public void init() {
        messageTemplates = new Properties();
        try {
            final InputStream in = getClass().getClassLoader().getResourceAsStream(notificationsBundleName + ".properties");
            try {
                messageTemplates.load(in);
            } finally {
                in.close();
            }
        } catch (IOException e) {
            throw new EJBException("Incorrect configuration, failed to load message templates:", e);
        }
    } 

    protected String getValueFromBundle(final String bundleKey) {
        return messageTemplates.getProperty(bundleKey);
    }

    @Override
    public Long sendNewMessage(final String fromUserUid, final String role, final String subject, final List<String> receipientUids, final String content,
            final boolean sendToFamilyMembers, final boolean sendToGroupSite) {
		final User fromUser = getUserByUid(fromUserUid);
		
        final MessageRef storedMessage = createNewMessageInOutbox(subject, receipientUids, content, fromUser, role, sendToFamilyMembers, sendToGroupSite);
        final String emailSubject = getValueFromBundle(EMAIL_NEW_MESSAGE_RECEIVED_SUBJECT);
        final String emailContent = MessageFormat.format(getValueFromBundle(EMAIL_NEW_MESSAGE_RECEIVED_BODY), 
                new Object[] {KoKuPropertiesUtil.get(EMAIL_LINKS_MESSAGE_INBOX_PATH)});
        sendEmailNotifications(getUsersByUids(receipientUids), storedMessage, emailSubject, emailContent);
        
		return storedMessage.getId();
	}

    private MessageRef createNewMessageInOutbox(final String subject,
            final List<String> receipientUids, final String content,
            final User fromUser, final String roleUid) {
        return createNewMessageInOutbox(subject, receipientUids, content, fromUser, roleUid, false, false);
    }

    private MessageRef createNewMessageInOutbox(final String subject,
            final List<String> receipientUids, final String content,
            final User fromUser, final String roleUid, boolean sendToFamilyMembers, boolean sendToGroupSite) {
        Message msg = new Message();
		fillMessage(msg, fromUser, roleUid, subject, receipientUids, content, sendToFamilyMembers, sendToGroupSite);
		
		msg = messageDao.create(msg);
		
		final MessageRef storedMessage = folderDAO.storeMessage(fromUser, FolderType.Outbox, msg);
		storedMessage.setRead(true);
		messageRefDao.update(storedMessage);
        return storedMessage;
    }

    private void fillMessage(Message msg,
            final User fromUser, final String roleUid, final String subject,
            final List<String> receipientUids, final String content) {
        fillMessage(msg, fromUser, roleUid, subject, receipientUids, content, false, false);
    }

    private void fillMessage(Message msg,
			final User fromUser, final String roleUid, final String subject,
			final List<String> receipientUids, final String content, boolean sendToFamilyMembers, boolean sendToGroupSite) {
		msg.setFrom(fromUser);
		msg.setFromRoleUid(roleUid);
		msg.setSubject(subject);
		msg.setReceipients(getUsersByUids(receipientUids));
		msg.setText(content);
		msg.setSendToFamilyMembers(sendToFamilyMembers);
		msg.setSendToGroupSite(sendToGroupSite);
	}

    protected Set<User> getUsersByUids(final List<String> receipientUids) {
        final Set<User> receipients = new HashSet<User>();
		for (final String receipientUid : receipientUids) {
			receipients.add(getUserByUid(receipientUid));
		}
        return receipients;
    }
	
	private User getUserByUid(final String userUid) {
		return userDao.getOrCreateUser(userUid);
	}

	/**
	 * @param userUid
	 * @return
	 */
	public List<MessageTO> getSentMessages(final String userUid) {
		final List<MessageRef> messages = folderDAO.getMessagesByUserWithRoleAndFolderType(getUserByUid(userUid), getUserRoles(userUid), FolderType.Outbox, 
				null, FIRST_RESULT_NUMBER, FIRST_RESULT_NUMBER + MAX_RESULTS_COUNT - 1);
		
		final List<MessageTO> result = new ArrayList<MessageTO>();
		for (final MessageRef messageRef : messages) {
			final MessageTO msg = new MessageTO();
			msg.setContent(messageRef.getMessage().getText());
			result.add(convertMessageToDTO(messageRef, msg));
		}
		return result;
	}

	private <M extends MessageSummary> M convertMessageToDTO(final MessageRef messageRef, final M msg) {
		final Message message = messageRef.getMessage();
		msg.setMessageId(messageRef.getId());
		msg.setSender(getDisplayName(message.getUser()));
		msg.setSenderUserInfo(getUserInfo(message.getUser()));
		msg.setSubject(message.getSubject());
		msg.setCreationDate(CalendarUtil.getXmlGregorianCalendar(message.getCreatedDate()));
		msg.setMessageStatus(MessageStatus.getStatus(messageRef.isRead()));
		msg.setMessageType(messageRef.getFolder().getFolderType());
		
		msg.setRecipients(getDisplayNamesByUsers(message.getReceipients()));
		msg.setRecipientUserInfos(getUserInfos(message.getReceipients()));
		return msg;
	}

    /**
     * @param users
     * @return
     */
    private List<UserInfo> getUserInfos(final Collection<User> users) {
        if (users == null || users.isEmpty()) {
            return Collections.emptyList();
        }
        
        final List<UserInfo> result = new ArrayList<UserInfo>(users.size());
        for (final User user : users) {
            result.add(getUserInfo(user));
        }
        return result;
    }

    /**
     * @param user
     * @return
     */
    private UserInfo getUserInfo(final User user) {
        if (user != null && SYSTEM_USER_NAME_FOR_NOTIFICATIONS.equals(user.getUid())) {
            return SYSTEM_USER_INFO;
        }
        return customerDao.getUserInfo(user);
    }

    private String getDisplayName(final User user) {
        if (user == null) {
            return "";
        }
        if (user.getCitizenPortalName() != null && !user.getCitizenPortalName().isEmpty()) {
            return user.getCitizenPortalName();
        } else {
            return user.getEmployeePortalName();
        }
    }

	private List<String> getDisplayNamesByUsers(final Set<User> receipients2) {
	    if (receipients2 == null) {
	        return null;
	    }
	    if (receipients2.isEmpty()) {
	        return Collections.emptyList();
	    }
	    
		final List<String> receipients = new ArrayList<String>();
		for (final User receipient : receipients2) {
			receipients.add(getDisplayName(receipient));
		}
		return receipients;
	}

	/**
	 * @param messageId
	 * @return
	 */
	@Override
	public MessageTO getMessageById(final Long messageRefId) {
		final MessageRef msgRef = messageRefDao.getById(messageRefId);
		if (msgRef == null) {
			logger.info("Message with ID " + messageRefId + " is not found.");
			return null;
		}
		final MessageTO msg = new MessageTO();
		msg.setContent(msgRef.getMessage().getText());
		msg.setDeliveryFailedTo(getUserInfos(msgRef.getDeliveryFailedTo()));
		return convertMessageToDTO(msgRef, msg);
	}

	/**
	 * @param userUid
	 * @param folderType
	 * @return
	 */
	@Override
	public List<MessageSummary> getMessages(final String userUid, final FolderType folderType) {
		final MessageQuery query = new MessageQuery();
		query.setStartNum(FIRST_RESULT_NUMBER);
		query.setMaxNum(FIRST_RESULT_NUMBER + MAX_RESULTS_COUNT - 1);
		return getMessages(userUid, folderType, query);
	}

	/**
	 * @param toUserUid
	 * @param messageId
	 */
	@Override
	public Long receiveMessage(final String toUserUid, final  Long messageId) {
		final MessageRef sentMessage = messageRefDao.getById(messageId);
		if (sentMessage == null) {
			throw new IllegalArgumentException("Message with ID " + messageId + " not found.");
		}

        final User toUser = getUserByUid(toUserUid);
        final Message message = sentMessage.getMessage();
        
        return doReceiveNewMessage(toUser, message).getId();
	}

    private MessageRef doReceiveNewMessage(final User toUser,
            final Message message) {
        final MessageRef msgRef = new MessageRef();
        Folder folder = getOrCreateFolder(toUser, FolderType.Inbox);

		msgRef.setFolder(folder);
        msgRef.setMessage(message);
		messageRefDao.create(msgRef);
        return msgRef;
    }

	private Folder getOrCreateFolder(final User toUser, FolderType folderType) {
		Folder folder = folderDAO.getFolderByUserAndType(toUser, folderType);
		if (folder == null) {
			folder = folderDAO.createNewFolderByUserAndType(toUser, folderType);
		}
		return folder;
	}

	/**
	 * @param messageIds
	 */
	@Override
	public void archiveMessages(final List<Long> messageRefIds) {
		final List<MessageRef> messageRefs = messageRefDao.getListByIds(messageRefIds);

		doArchiveMessages(messageRefs);
	}

    private void doArchiveMessages(final List<MessageRef> messageRefs) {
        final Map<User, Folders> folders = new HashMap<User, Folders>();
		
		for (final MessageRef messageRef : messageRefs) {
			final User user = messageRef.getFolder().getUser();
			if (!folders.containsKey(user)) {
				folders.put(user, new Folders(
						getOrCreateFolder(user, FolderType.Archive_Inbox),
						getOrCreateFolder(user, FolderType.Archive_Outbox))
						);
			}
			if (FolderType.Inbox == messageRef.getFolder().getFolderType()) {
				messageRef.setFolder(folders.get(user).archiveInbox);
			} else if (FolderType.Outbox == messageRef.getFolder().getFolderType()) {
				messageRef.setFolder(folders.get(user).archiveOutbox);
			}
		}
		messageRefDao.updateAll(messageRefs);
    }
	
	private static class Folders {
		private Folder archiveInbox;
		private Folder archiveOutbox;
		public Folders(final Folder archiveInbox, final Folder archiveOutbox) {
			this.archiveInbox = archiveInbox; 
			this.archiveOutbox = archiveOutbox;
		} 
	}

	/**
	 * @param messageIds
	 */
	@Override
	public void deleteMessages(List<Long> messageRefIds) {
		messageRefDao.deleteAll(messageRefIds);
	}

	/**
	 * @param messageId
	 * @param read
	 */
	@Override
	public void setMessageStatus(final List<Long> messageIds, MessageStatus status) {
		final List<MessageRef> messages = messageRefDao.getListByIds(messageIds);
		if (messages.isEmpty()) {
			return;
		}
		
		for (final MessageRef message : messages) {
			message.setRead(status.isRead());
		}
		messageRefDao.updateAll(messages);
	}

	/**
	 * @param userId
	 * @param folderType
	 * @return
	 */
	@Override
	public int getTotalMessagesCount(final String userId, final FolderType folderType, final Criteria criteria) {
		return getIntValue(folderDAO.getTotalMessagesCountByUserAndRoles(getUserByUid(userId), getUserRoles(userId), folderType, criteria));
	}

	/**
	 * @param userId
	 * @param folderType
	 * @return
	 */
	@Override
	public int getUnreadMessagesCount(final String userId, final FolderType folderType) {
		return getIntValue(folderDAO.getUnreadMessagesCountByUserAndRoles(getUserByUid(userId), getUserRoles(userId), folderType));
	}
	
	private int getIntValue(final Long count) {
		if (count == null) {
			return 0;
		}
		return count.intValue();
	}

	/**
	 * @param userUid
	 * @param folderType
	 * @param query
	 * @param startNum
	 * @param maxNum
	 * @return
	 */
	@Override
	public List<MessageSummary> getMessages(final String userUid, final FolderType folderType, final MessageQuery query) {
		final int startNum = query.getStartNum();
		final int maxNum = query.getMaxNum();
		validateStartAndMaxNum(startNum, maxNum);
		
		final List<MessageRef> messages = folderDAO.getMessagesByUserWithRoleAndFolderType(getUserByUid(userUid), 
		        getUserRoles(userUid), folderType, query, startNum, maxNum - startNum + 1);
		
		final List<MessageSummary> result = new ArrayList<MessageSummary>();
		for (final MessageRef messageRef : messages) {
			result.add(convertMessageToDTO(messageRef, new MessageSummary()));
		}
		return result;
	}

	private void validateStartAndMaxNum(final int startNum, final int maxNum) {
		if (startNum < 1) {
			throw new IllegalArgumentException("Incorrect number for start number: " + startNum + ", it should be greater or equal to 1.");
		}
		if (maxNum < startNum) {
			throw new IllegalArgumentException("Incorrect number for max number: " + maxNum + ", it should be greater or equal to start number.");
		}
	}

	/**
	 * @param requestId
	 * @return
	 */
	@Override
	public RequestTO getRequestById(final Long requestId) {
		final RequestTO result = new RequestTO();
		
		final Request request = requestDAO.getById(requestId);
		final List<UserInfo> receipientsNotResponded = getUserInfos(request.getReceipients());
		final List<ResponseTO> responseTOs = new ArrayList<ResponseTO>();

		fillRequestSummary(result, request, receipientsNotResponded, responseTOs);
		result.setContent("");
		result.setNotRespondedUserInfos(receipientsNotResponded);
		result.setResponses(responseTOs);

		result.setQuestions(getQuestionsTObyDTO(request.getTemplate().getQuestions()));

		return result;
	}

    private List<QuestionTO> getQuestionsTObyDTO(Set<Question> questions) {
        final List<QuestionTO> questionTOs = new ArrayList<QuestionTO>();
		for (final Question question : questions) {
			final QuestionTO questionTO = new QuestionTO();
			questionTO.setNumber(question.getIndex());
			questionTO.setDescription(question.getDescription());
			questionTO.setType(fi.arcusys.koku.kv.soa.QuestionType.valueOf(question.getType()));
			questionTOs.add(questionTO);
		}
		Collections.sort(questionTOs, new Comparator<QuestionTO>() {
            @Override
            public int compare(QuestionTO o1, QuestionTO o2) {
                return o1.getNumber() - o2.getNumber();
            }
        });
        return questionTOs;
    }

	private void fillRequestSummary(final RequestSummary result, final Request request,
			final List<UserInfo> receipientsNotResponded,
			final List<ResponseTO> responseTOs) {
		fillRequestShortSummary(result, request);
		
		for (final Response response : request.getResponses()) {
			final ResponseTO responseTO = new ResponseTO();
            responseTO.setAnswers(convertAnswersToAnswerTO(response.getAnswers()));
			responseTO.setName(getDisplayName(response.getReplier()));
			responseTO.setReplierUserInfo(getUserInfo(response.getReplier()));
			responseTO.setComment(response.getComment());
			receipientsNotResponded.remove(getUserInfo(response.getReplier()));
			responseTOs.add(responseTO);
		}
		
        result.setRespondedAmount(request.getResponses().size());
		result.setMissedAmout(receipientsNotResponded.size());
	}

    protected List<AnswerTO> convertAnswersToAnswerTO(
            final Set<fi.arcusys.koku.common.service.datamodel.Answer> answers) {
        final List<AnswerTO> result = new ArrayList<AnswerTO>();
        for (final fi.arcusys.koku.common.service.datamodel.Answer answer : answers) {
        	final AnswerTO answerTO = new AnswerTO();
        	answerTO.setAnswer(answer.getValueAsString());
        	answerTO.setComment(answer.getComment());
        	answerTO.setQuestionNumber(answer.getIndex());
        	result.add(answerTO);
        }
        return result;
    }

    protected RequestShortSummary fillRequestShortSummary(final RequestShortSummary result,
            final Request request) {
        result.setRequestId(request.getId());
		result.setSender(getDisplayName(request.getFromUser()));
		result.setSenderUserInfo(getUserInfo(request.getFromUser()));
		result.setSubject(request.getSubject());
		result.setCreationDate(CalendarUtil.getXmlGregorianCalendar(request.getCreatedDate()));
		result.setEndDate(CalendarUtil.getXmlGregorianCalendar(request.getReplyTill()));
		
		return result;
    }

	/**
	 * @param fromUserId
	 * @param subject
	 * @param receipients
	 * @param content
	 * @param questionTOs
	 * @return
	 */
	@Override
	public Long sendRequest(final String fromUserId, final String subject, final List<String> receipients, final String content, 
	        final List<QuestionTO> questionTOs, final List<MultipleChoiceTO> choices, final RequestTemplateVisibility visibility,
            final XMLGregorianCalendar replyTill, final Integer notifyBeforeDays) {
	    final RequestTemplateTO templateTO = createTemplateTO(fromUserId,
                subject, questionTOs, choices, visibility);
	    
	    final RequestProcessingTO requestTO = createRequestProcessingTO(
                fromUserId, subject, receipients, content, replyTill,
                notifyBeforeDays);
	    
		final RequestTemplate template = doCreateRequestTemplate(templateTO);

		return doCreateRequest(template, requestTO).getId();
	}

    private RequestProcessingTO createRequestProcessingTO(
            final String fromUserId, final String subject,
            final List<String> receipients, final String content,
            final XMLGregorianCalendar replyTill, final Integer notifyBeforeDays) {
        final RequestProcessingTO requestTO = new RequestProcessingTO();
	    requestTO.setContent(content);
	    // check if "default" role should be used
        requestTO.setFromRole(null);
        requestTO.setFromUserUid(fromUserId);
        requestTO.setNotifyBeforeDays(notifyBeforeDays);
        requestTO.setReceipients(receipients);
        requestTO.setReplyTill(replyTill);
        requestTO.setSubject(subject);
        return requestTO;
    }

    private RequestTemplateTO createTemplateTO(final String fromUserId,
            final String subject, final List<QuestionTO> questionTOs,
            final List<MultipleChoiceTO> choices,
            final RequestTemplateVisibility visibility) {
        final RequestTemplateTO templateTO = new RequestTemplateTO();
	    templateTO.setChoices(choices);
        templateTO.setCreatorUid(fromUserId);
        templateTO.setQuestions(questionTOs);
        templateTO.setSubject(subject);
        templateTO.setVisibility(visibility);
        return templateTO;
    }

    private Request doCreateRequest(final RequestTemplate template, final RequestProcessingTO requestTO) {
        
        final User fromUser = getUserByUid(requestTO.getFromUserUid());

        Request request = new Request();
        request.setTemplate(template);
        request.setReplyTill(CalendarUtil.getSafeDate(requestTO.getReplyTill()));
        request.setNotifyBeforeDays(requestTO.getNotifyBeforeDays());
        request.setSubject(requestTO.getSubject());
        final Set<User> recipients = getUsersByUids(requestTO.getReceipients());
        request.setReceipients(recipients);
        request.setFromUser(fromUser);
        request.setFromRoleUid(requestTO.getFromRole());

        final MessageRef msgRef = createNewMessageInOutbox(requestTO.getSubject(), requestTO.getReceipients(), requestTO.getContent(), fromUser, requestTO.getFromRole());
        
        final String emailSubject = getValueFromBundle(EMAIL_NEW_REQUEST_RECEIVED_SUBJECT);
        final String emailContent = MessageFormat.format(getValueFromBundle(EMAIL_NEW_REQUEST_RECEIVED_BODY), 
                new Object[] {KoKuPropertiesUtil.get(EMAIL_LINKS_MESSAGE_INBOX_PATH) + KoKuPropertiesUtil.get(EMAIL_LINKS_NEW_REQUEST_PATH),
                              request.getReplyTill()});

        sendEmailNotifications(recipients, msgRef, emailSubject, emailContent);
        
		request = requestDAO.create(request);
		
        return request;
    }

    private void sendEmailNotifications(final Set<User> recipients,
            final MessageRef msgRef, final String emailSubject,
            final String emailContent) {
        final Set<User> deliveryFailed = new HashSet<User>();
        for (final User recipient : recipients) {
            if (!emailDao.sendMessage(recipient, emailSubject, 
                    emailContent)) {
                deliveryFailed.add(recipient);
            } 
        }
        if (!deliveryFailed.isEmpty()) {
            // report message delivery failure
            msgRef.setDeliveryFailedTo(deliveryFailed);
            messageRefDao.update(msgRef);
        }
    }

    /**
     * @param fromUserId
     * @param subject
     * @param questionTOs
     * @param visibility 
     * @return
     */
    private RequestTemplate doCreateRequestTemplate(final RequestTemplateTO templateTO) {
        final RequestTemplate requestTemplate = new RequestTemplate();
        fillRequestTemplate(templateTO, requestTemplate);
        return requestTemplateDAO.create(requestTemplate);
    }

    private void fillRequestTemplate(final RequestTemplateTO templateTO, final RequestTemplate requestTemplate) {
        requestTemplate.setCreator(getUserByUid(templateTO.getCreatorUid()));
        requestTemplate.setSubject(templateTO.getSubject());
        final Map<Integer, Question> numberToQuestion = new HashMap<Integer, Question>();
        if (templateTO.getQuestions() != null) {
            for (final QuestionTO questionTO : templateTO.getQuestions()) {
                final Question question = new Question();
                question.setIndex(questionTO.getNumber());
                question.setDescription(questionTO.getDescription());
                question.setType(getQuestionType(questionTO));
                numberToQuestion.put(questionTO.getNumber(), question);
            }
        }
        if (templateTO.getChoices() != null) {
            for (final MultipleChoiceTO choiceTO : templateTO.getChoices()) {
                final Question question = numberToQuestion.get(choiceTO.getQuestionNumber());
                if (question.getChoices() == null) {
                    question.setChoices(new HashSet<MultipleChoice>());
                }
                final MultipleChoice multipleChoice = new MultipleChoice();
                multipleChoice.setNumber(choiceTO.getNumber());
                multipleChoice.setDescription(choiceTO.getDescription());
                question.getChoices().add(multipleChoice);
            }
        }
        requestTemplate.setQuestions(new HashSet<Question>(numberToQuestion.values()));
        requestTemplate.setVisibility(RequestTemplateVisibility.toDmType(templateTO.getVisibility() == null ? RequestTemplateVisibility.All : templateTO.getVisibility()));
    }

	private QuestionType getQuestionType(final QuestionTO questionTO) {
		return questionTO.getType().getDMQuestionType();
	}

	/**
	 * @param toUserId
	 * @param requestId
	 * @return
	 */
	@Override
	public Long receiveRequest(final String toUserId, final Long requestId, final String content) {
		final Request sentRequest = requestDAO.getById(requestId);
		if (sentRequest == null) {
			throw new IllegalArgumentException("Request with ID " + requestId + " not found.");
		}

		final User toUser = getUserByUid(toUserId);
		
        Message msg = new Message();
        fillMessage(msg, sentRequest.getFromUser(), null, sentRequest.getSubject(), Collections.singletonList(toUserId), content);
        msg = messageDao.create(msg);
        
        final MessageRef msgRef = doReceiveNewMessage(toUser, msg);

        return msgRef.getId();
	}

	/**
	 * @param toUserId
	 * @param requestId
	 * @param answers
	 * @return
	 */
	@Override
	public Long replyToRequest(final String toUserId, final Long requestId, final List<Answer> answers, final String comment) {
		final User replier = getUserByUid(toUserId);
		
		final Request sentRequest = requestDAO.getById(requestId);
		if (sentRequest == null) {
			throw new IllegalArgumentException("Request with ID " + requestId + " not found.");
		}
		
		final Response response = new Response();
		response.setReplier(replier);
		response.setRequest(sentRequest);
		response.setComment(comment);
		final Set<fi.arcusys.koku.common.service.datamodel.Answer> answersList = new HashSet<fi.arcusys.koku.common.service.datamodel.Answer>();
		for (final Answer answerSoa : answers) {
			fi.arcusys.koku.common.service.datamodel.Answer answer = new fi.arcusys.koku.common.service.datamodel.Answer();
			if (answerSoa.getValue() != null) {
				answer.setValue(answerSoa.getValue() ? "Kyllä" : "Ei");
			} else if (answerSoa.getTextValue() != null) {
				answer.setValue(answerSoa.getTextValue());
			} else {
				throw new IllegalArgumentException("Unknown answer type: value or textValue should be filled.");
			}
			answer.setComment(answerSoa.getComment());
			answer.setIndex(answerSoa.getQuestionNumber());
			answersList.add(answer); 
		}
		response.setAnswers(answersList);
		
		final Response newResponse = responseDAO.create(response);
        doDeliverMessage(SYSTEM_USER_NAME_FOR_NOTIFICATIONS, Collections.singletonList(sentRequest.getFromUser().getUid()),
                getValueFromBundle(REQUEST_REPLIED_SUBJECT), 
                MessageFormat.format(getValueFromBundle(REQUEST_REPLIED_BODY), 
                        new Object[] {sentRequest.getSubject(), getUserInfo(replier).getDisplayName()}));
        
        return newResponse.getId();
	}

	/**
	 * @param userId
	 * @param startNum
	 * @param maxNum
	 * @return
	 */
	@Override
	public List<RequestSummary> getRequests(String userId, int startNum, int maxNum) {
        validateStartAndMaxNum(startNum, maxNum);

        return convertRequestsToSummaryTO(requestDAO.getRequestsByUserAndRoles(getUserByUid(userId), getUserRoles(userId), startNum, maxNum - startNum + 1));
	}

    /**
     * @param userId
     * @return
     */
    private List<String> getUserRoles(String userId) {
        final List<String> roleUids = new ArrayList<String>();
        for (final Role userRole : usersService.getUserRoles(userId)) {
            roleUids.add(userRole.getRoleUid());
        }
        return roleUids;
    }

    protected List<RequestSummary> convertRequestsToSummaryTO(
            final List<Request> requests) {
        final List<RequestSummary> result = new ArrayList<RequestSummary>();
		for (final Request request : requests) {
			final RequestSummary requestSummary = new RequestSummary();
			
			fillRequestSummary(requestSummary, request, getUserInfos(request.getReceipients()), new ArrayList<ResponseTO>());
			result.add(requestSummary);
		}
		return result;
    }

    /**
     * @param fromUserUid
     * @param subject
     * @param receipients
     * @param content
     */
    @Override
    public void sendNotification(String subject, List<String> recipients, String content) {
        if (recipients != null) {
            // separate sending of the message to all recipients
            for (final String recipient : recipients) {
                doDeliverMessage(SYSTEM_USER_NAME_FOR_NOTIFICATIONS, Collections.singletonList(recipient), subject, content);
            }
        }
    }

    private void doDeliverMessage(final String fromUser,
            List<String> receipients, String subject, String content) {
        final String contentByTemplate = MessageFormat.format(notificationTemplate, new Object[] {getReceipientNames(receipients), subject, content});
        final Long messageId = sendNewMessage(fromUser, null, subject, receipients, contentByTemplate, false, false);
        for (final String receipient : receipients) {
            receiveMessage(receipient, messageId);
        }
    }
    
    /**
     * @param receipients
     * @return
     */
    private String getReceipientNames(List<String> receipients) {
        final StringBuilder result = new StringBuilder();
        for (final String receipient : receipients) {
            result.append(getUserInfo(getUserByUid(receipient)).getDisplayName()).append(",");
        }
        if (result.length() > 0) {
            result.setLength(result.length() - 1);
        }
        return result.toString();
    }

    public void deliverMessage(final String fromUser, final List<String> toUsers, final String subject, final String content) {
        doDeliverMessage(fromUser, toUsers, subject, content);
    }

    /**
     * @param userUid
     * @param subject
     * @param questionTOs
     */
    @Override
    public void createRequestTemplate(String userUid, String subject, List<QuestionTO> questionTOs, List<MultipleChoiceTO> choices, RequestTemplateVisibility visibility) {
        doCreateRequestTemplate(createTemplateTO(userUid, subject, questionTOs, choices, visibility));
    }

    /**
     * @param subjectPrefix
     * @param limit
     * @return
     */
    @Override
    public List<RequestTemplateSummary> getRequestTemplateSummary(final String userUid, String subjectPrefix, int limit) {
        final List<RequestTemplateSummary> result = new ArrayList<RequestTemplateSummary>();
        final User user = (userUid != null && !userUid.isEmpty()) ? userDao.getOrCreateUser(userUid) : null;
        for (final RequestTemplate template : requestTemplateDAO.searchTemplates(user, subjectPrefix, limit)) {
            final RequestTemplateSummary summaryTO = new RequestTemplateSummary();
            convertTemplateToDTO(template, summaryTO);
            result.add(summaryTO);
        }
        return result;
    }

    private <RTS extends RequestTemplateSummary> void convertTemplateToDTO(final RequestTemplate template,
            final RTS summaryTO) {
        summaryTO.setRequestTemplateId(template.getId());
        summaryTO.setSubject(template.getSubject());
    }

    /**
     * @param requestTemplateId
     * @return
     */
    @Override
    public RequestTemplateTO getRequestTemplateById(long requestTemplateId) {
        final RequestTemplate template = loadRequestTemplate(requestTemplateId);

        final RequestTemplateTO templateTO = new RequestTemplateTO();
        convertTemplateToDTO(template, templateTO);
        templateTO.setQuestions(getQuestionsTObyDTO(template.getQuestions()));
        templateTO.setChoices(getChoicesTO(template.getQuestions()));
        return templateTO;
    }

    /**
     * @param questions
     * @return
     */
    private List<MultipleChoiceTO> getChoicesTO(Set<Question> questions) {
        final List<MultipleChoiceTO> result = new ArrayList<MultipleChoiceTO>();
        for (final Question question : questions) {
            for (final MultipleChoice choice : question.getChoices()) {
                final MultipleChoiceTO choiceTO = new MultipleChoiceTO();
                choiceTO.setQuestionNumber(question.getIndex());
                choiceTO.setNumber(choice.getNumber());
                choiceTO.setDescription(choice.getDescription());
                result.add(choiceTO);
            }
        }
        Collections.sort(result, new Comparator<MultipleChoiceTO>() {
            @Override
            public int compare(MultipleChoiceTO o1, MultipleChoiceTO o2) {
                final int questionComparation = o1.getQuestionNumber() - o2.getQuestionNumber();
                if (questionComparation != 0) {
                    return questionComparation;
                } else {
                    return o1.getNumber() - o2.getNumber();
                }
            }
        });
        return result;
    }

    /**
     * @param fromUserUid
     * @param requestTemplateId
     * @param receipients
     * @param content
     * @return
     */
    @Override
    public Long sendRequestWithTemplate(String fromUserUid, long requestTemplateId, final String subject, 
            List<String> receipients, String content,
            final XMLGregorianCalendar replyTill, final Integer notifyBeforeDays) {
        final RequestTemplate template = loadRequestTemplate(requestTemplateId);
        
        return doCreateRequest(template, createRequestProcessingTO(fromUserUid, subject, receipients, content, replyTill, notifyBeforeDays)).getId();
    }

    private RequestTemplate loadRequestTemplate(long requestTemplateId) {
        final RequestTemplate template = requestTemplateDAO.getById(requestTemplateId);
        if (template == null) {
            throw new IllegalArgumentException("Request template not found: ID = " + requestTemplateId );
        }
        return template;
    }

    /**
     * @param fromUserUid
     * @param subject
     * @param toUserUid
     * @param content
     */
    @Override
    public Long receiveNewMessage(final String fromUserUid, final String subject, final String toUserUid, final String content) {
        Message msg = new Message();
        final User user = getUserByUid(fromUserUid);
        fillMessage(msg, user, null, subject, Collections.singletonList(toUserUid), content);
        msg = messageDao.create(msg);
        
        return doReceiveNewMessage(getUserByUid(toUserUid), msg).getId();
    }

    /**
     * @param userUid
     * @param subject
     * @return
     */
    @Override
    public RequestTemplateExistenceStatus isRequestTemplateExist(String userUid, String subject) {
        final RequestTemplate template = getTemplateBySubject(subject);
        if (template == null) {
            return RequestTemplateExistenceStatus.NotExists;
        } else if (!template.getCreator().getUid().equals(userUid) ||
                getIntValue(requestDAO.getTotalByTemplate(template)) > 0) {
            return RequestTemplateExistenceStatus.ExistsActive;
        } else {
            return RequestTemplateExistenceStatus.ExistsNotActive;
        }
    }

    private RequestTemplate getTemplateBySubject(String subject) {
        final List<RequestTemplate> templates = requestTemplateDAO.searchBySubject(subject);
        if (templates.size() == 0) {
            return null;
        } else {
            if (templates.size() > 1) {
                logger.warn("More then one template with subject '" + subject + "'");
            }
            return templates.get(0);
        }
    }

    /**
     * @param requestTemplateId
     * @param userUid
     * @param subject
     * @param questions
     * @param choices
     */
    @Override
    public void updateRequestTemplate(String userUid, String subject, List<QuestionTO> questions, List<MultipleChoiceTO> choices, RequestTemplateVisibility visibility) {
        final RequestTemplate template = getTemplateBySubject(subject);
        if (template == null) {
            throw new IllegalStateException("Error in update: request template with subject '" + subject + "' is not found.");
        } 
        if (!template.getCreator().getUid().equals(userUid)) {
            throw new IllegalStateException("Error in update: request template with subject '" + subject + "' is created by different user.");
        } 
        if (getIntValue(requestDAO.getTotalByTemplate(template)) > 0) {
            throw new IllegalStateException("Error in update: request template with subject '" + subject + "' is active.");
        } 

        fillRequestTemplate(createTemplateTO(userUid, subject, questions, choices, visibility), template);
        requestTemplateDAO.update(template);
    }

    /**
     * @param userUid
     * @param startNum
     * @param maxNum
     * @return
     */
    @Override
    public List<ResponseSummary> getRepliedRequests(String userUid, int startNum, int maxNum) {
        return convertResponsesToSummaryTO(responseDAO.getResponsesByUser(getUserByUid(userUid), startNum, maxNum - startNum + 1));
    }

    protected List<ResponseSummary> convertResponsesToSummaryTO(
            final List<Response> responses) {
        final List<ResponseSummary> result = new ArrayList<ResponseSummary>();
        for (final Response response : responses) {
            final ResponseSummary responseSummary = new ResponseSummary();
            fillResponseSummary(response, responseSummary);
            result.add(responseSummary);
        }
        return result;
    }

    protected void fillResponseSummary(final Response response,
            final ResponseSummary responseSummary) {
        responseSummary.setRequest(fillRequestShortSummary(new RequestShortSummary(), response.getRequest()));
        responseSummary.setResponseId(response.getId());
        responseSummary.setReplierUid(getDisplayName(response.getReplier()));
        responseSummary.setReplierUserInfo(getUserInfo(response.getReplier()));
    }

    /**
     * @param userUid
     * @param startNum
     * @param maxNum
     * @return
     */
    @Override
    public List<ResponseSummary> getOldRepliedRequests(String userUid, int startNum, int maxNum) {
        return convertResponsesToSummaryTO(responseDAO.getOldResponsesByUser(getUserByUid(userUid), startNum, maxNum - startNum + 1));
    }

    /**
     * @param userUid
     * @return
     */
    @Override
    public int getTotalRepliedRequests(String userUid) {
        return getIntValue(responseDAO.getTotalResponsesByUser(getUserByUid(userUid)));
    }

    /**
     * @param userUid
     * @return
     */
    @Override
    public int getTotalOldRepliedRequests(String userUid) {
        return getIntValue(responseDAO.getTotalOldResponsesByUser(getUserByUid(userUid)));
    }

    /**
     * @param user
     * @return
     */
    @Override
    public int getTotalRequests(String user) {
        return getIntValue(requestDAO.getTotalRequestsByUserAndRoles(getUserByUid(user), getUserRoles(user)));
    }

    /**
     * @param user
     * @param startNum
     * @param maxNum
     * @return
     */
    @Override
    public List<RequestSummary> getOldRequests(String user, int startNum, int maxNum) {
        validateStartAndMaxNum(startNum, maxNum);

        return convertRequestsToSummaryTO(requestDAO.getOldRequestsByUserAndRoles(getUserByUid(user), getUserRoles(user), startNum, maxNum - startNum + 1));
    }

    /**
     * @param user
     * @return
     */
    @Override
    public int getTotalOldRequests(String user) {
        return getIntValue(requestDAO.getTotalOldRequestsByUserAndRoles(getUserByUid(user), getUserRoles(user)));
    }

    /**
     * @param responseId
     * @return
     */
    @Override
    public ResponseDetail getResponseDetail(long responseId) {
        final Response response = responseDAO.getById(responseId);
        if (response == null) {
            throw new IllegalArgumentException("Response is not found by ID " + responseId);
        }
        
        final ResponseDetail responseDetail = new ResponseDetail();
        
        fillResponseSummary(response, responseDetail);
        responseDetail.setQuestions(getQuestionsTObyDTO(response.getRequest().getTemplate().getQuestions()));
        responseDetail.setAnswers(convertAnswersToAnswerTO(response.getAnswers()));
        responseDetail.setComment(response.getComment());
        
        return responseDetail;
    }

    /**
     * @param template
     * @param request
     * @return
     */
    @Override
    public Long sendRequest(RequestTemplateTO templateTO, RequestProcessingTO request) {
        final RequestTemplate template = doCreateRequestTemplate(templateTO);

        return doCreateRequest(template, request).getId();
    }

    /**
     * @param requestTemplateId
     * @param request
     * @return
     */
    @Override
    public Long sendRequestWithTemplate(long requestTemplateId, RequestProcessingTO request) {
        final RequestTemplate template = loadRequestTemplate(requestTemplateId);
        
        return doCreateRequest(template, request).getId();
    }

    /**
     * 
     */
    @Override
    public void performTask() {
        logger.info("Perform scheduled tasks.");
        
        logger.debug("Start deletion of old messages.");
        final int messagesDeleted = deleteOldMessages();
        logger.debug("Deleted " + messagesDeleted + " messages."); 
        
        logger.debug("Start archiving of old messages");
        final int messagesArchived = archiveOldMessages();
        logger.debug("Archived " + messagesArchived + " messages."); 
        
        logger.debug("Start reminding about requests");
        final int remindersSent = sendReminderForRequests();
        logger.debug("Sent " + remindersSent + " reminders."); 
        
    }

    /**
     * @return
     */
    @Override
    public String getTaskDescription() {
        return "Auto archiving, auto deletion, unanswered requests reminding.";
    }

    /**
     * 
     */
    @Override
    public int deleteOldMessages() {
        final Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) - 2);
        return messageRefDao.deleteOldMessages(calendar.getTime());
    }

    /**
     * @return
     */
    @Override
    public int archiveOldMessages() {
        final Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) - 1);
        final List<MessageRef> messagesForArchive = messageRefDao.getMessagesByFolderTypeAndCreateDate(Arrays.asList(FolderType.Inbox, FolderType.Outbox), calendar.getTime());
        final Map<String, Integer> msgCounts = new HashMap<String, Integer>();
        for (final MessageRef msgRef : messagesForArchive) {
            final String userUid = msgRef.getFolder().getUser().getUid();
            msgCounts.put(userUid, (msgCounts.containsKey(userUid) ? msgCounts.get(userUid) : 0) + 1);
        }
        doArchiveMessages(messagesForArchive);
        for (final Map.Entry<String, Integer> entry : msgCounts.entrySet()) {
            doDeliverMessage(SYSTEM_USER_NAME_FOR_NOTIFICATIONS, Collections.singletonList(entry.getKey()),
                    getValueFromBundle(MESSAGES_ARCHIVED_SUBJECT), 
                    MessageFormat.format(getValueFromBundle(MESSAGES_ARCHIVED_BODY), 
                            new Object[] {entry.getValue()}));
        }
        return messagesForArchive.size();
    }

    /**
     * @param userUid
     * @param folderType
     * @return
     */
    @Override
    public int archiveOldMessagesByUserAndFolderType(String userUid, FolderType folderType) {
        // messages older then 3 month
        final Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 3);
        final List<MessageRef> messagesForArchive = messageRefDao.getMessagesByUserAndFolderTypeAndCreateDate(getUserByUid(userUid), 
                Collections.singleton(folderType), calendar.getTime());
        doArchiveMessages(messagesForArchive);
        return messagesForArchive.size();
    }

    /**
     * 
     */
    @Override
    public int sendReminderForRequests() {
        int remindersSent = 0;
        final List<Request> requests = requestDAO.getOpenRequestsByNotifyDate(new Date());
        for (final Request request : requests) {
            final Set<User> receipients = new HashSet<User>(request.getReceipients());
            for (final Response response : request.getResponses()) {
                receipients.remove(response.getReplier());
            }
            for (final User user : receipients) {
                doDeliverMessage(SYSTEM_USER_NAME_FOR_NOTIFICATIONS, Collections.singletonList(user.getUid()),
                        getValueFromBundle(REQUEST_NOT_REPLIED_REMINDER_SUBJECT), 
                        MessageFormat.format(getValueFromBundle(REQUEST_NOT_REPLIED_REMINDER_BODY), 
                                new Object[] {request.getSubject()}));
                remindersSent++;
            }
        }
        return remindersSent;
    }
    
    
}

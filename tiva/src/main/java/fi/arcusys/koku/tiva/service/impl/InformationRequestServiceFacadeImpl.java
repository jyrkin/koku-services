package fi.arcusys.koku.tiva.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
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
import javax.ejb.Stateless;
import javax.xml.datatype.XMLGregorianCalendar;

import fi.arcusys.koku.common.external.CacheDAO;
import fi.arcusys.koku.common.external.CustomerServiceDAO;
import fi.arcusys.koku.common.service.CalendarUtil;
import fi.arcusys.koku.common.service.InformationRequestDAO;
import fi.arcusys.koku.common.service.KokuSystemNotificationsService;
import fi.arcusys.koku.common.service.UserDAO;
import fi.arcusys.koku.common.service.datamodel.InformationReplyStatus;
import fi.arcusys.koku.common.service.datamodel.InformationRequest;
import fi.arcusys.koku.common.service.datamodel.InformationRequestCategory;
import fi.arcusys.koku.common.service.datamodel.InformationRequestReply;
import fi.arcusys.koku.common.service.datamodel.User;
import fi.arcusys.koku.common.service.dto.InformationRequestDTOCriteria;
import fi.arcusys.koku.common.soa.UserInfo;
import fi.arcusys.koku.tiva.service.InformationRequestServiceFacade;
import fi.arcusys.koku.tiva.service.KksCollectionsDAO;
import fi.arcusys.koku.tiva.soa.InformationAccessType;
import fi.arcusys.koku.tiva.soa.InformationCategoryTO;
import fi.arcusys.koku.tiva.soa.InformationRequestCriteria;
import fi.arcusys.koku.tiva.soa.InformationRequestDetail;
import fi.arcusys.koku.tiva.soa.InformationRequestQuery;
import fi.arcusys.koku.tiva.soa.InformationRequestReplyTO;
import fi.arcusys.koku.tiva.soa.InformationRequestStatus;
import fi.arcusys.koku.tiva.soa.InformationRequestSummary;
import fi.arcusys.koku.tiva.soa.InformationRequestTO;
import fi.koku.services.entity.kks.v1.InfoGroup;

/**
 * Service facade implementation for business methods in TIVA-Tietopyyntö functional area.
 * 
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Sep 22, 2011
 */
@Stateless
public class InformationRequestServiceFacadeImpl implements InformationRequestServiceFacade {

    private final static String INFORMATION_REQUEST_CREATED_SUBJECT = "information_request.created.subject";
    private final static String INFORMATION_REQUEST_CREATED_BODY = "information_request.created.body";
    
    private final static String INFORMATION_REQUEST_APPROVED_SUBJECT = "information_request.approved.subject";
    private final static String INFORMATION_REQUEST_APPROVED_BODY = "information_request.approved.body";

    private final static String INFORMATION_REQUEST_REJECTED_SUBJECT = "information_request.rejected.subject";
    private final static String INFORMATION_REQUEST_REJECTED_BODY = "information_request.rejected.body";

    @EJB
    private InformationRequestDAO informationRequestDao;
    
    @EJB
    private UserDAO userDao; 
    
    @EJB
    private KksCollectionsDAO kksDao;
    
    @EJB
    private CustomerServiceDAO customerDao;
    
    @EJB
    private KokuSystemNotificationsService notificationService;
    
    @EJB
    private CacheDAO cacheDao;

    private String notificationsBundleName = "information_request.msg";
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

    private String getValueFromBundle(final String key) {
        return messageTemplates.getProperty(key);
    }

    /**
     * @param request
     * @return
     */
    @Override
    public Long createInformationRequest(InformationRequestTO requestTO) {
        final InformationRequest request = new InformationRequest();
        request.setAdditionalInfo(requestTO.getAdditionalInfo());
        final Set<InformationRequestCategory> categories = new HashSet<InformationRequestCategory>();
        final List<String> categoryUids = requestTO.getCategories();
        if (categoryUids != null) {
            for (final String categoryUid : categoryUids) {
                final InformationRequestCategory category = new InformationRequestCategory();
                category.setCategoryUid(categoryUid);
                category.setRequest(request);
                categories.add(category);
            }
        }
        request.setCategories(categories);
        request.setDescription(requestTO.getDescription());
        request.setLegislationInfo(requestTO.getLegislationInfo());
        if (requestTO.getReceiverUid() != null) {
            request.setReceiver(userDao.getOrCreateUser(requestTO.getReceiverUid()));
        }
        request.setReceiverRoleUid(requestTO.getReceiverRoleUid());
        request.setRequestPurpose(requestTO.getRequestPurpose());
        request.setSender(userDao.getOrCreateUser(requestTO.getSenderUid()));
        request.setTargetPerson(userDao.getOrCreateUser(requestTO.getTargetPersonUid()));
        request.setTitle(requestTO.getTitle());
        request.setValidTill(getSafeDate(requestTO.getValidTill()));
        final InformationRequest result = informationRequestDao.create(request);
        
        if (request.getReceiver() != null) {
            notificationService.sendNotification(getValueFromBundle(INFORMATION_REQUEST_CREATED_SUBJECT), 
                    Collections.singletonList(request.getReceiver().getUid()), 
                    MessageFormat.format(getValueFromBundle(INFORMATION_REQUEST_CREATED_BODY), 
                            new Object[] {request.getDescription()}));
        }

        return result.getId();
    }

    private Date getSafeDate(XMLGregorianCalendar calendar) {
        if (calendar == null) {
            return null;
        } else {
            return calendar.toGregorianCalendar().getTime();
        }
    }

    /**
     * @param replyTO
     */
    @Override
    public void approveRequest(InformationRequestReplyTO replyTO) {
        final InformationRequest request = loadRequestForReply(replyTO.getRequestId());
        
        final InformationRequestReply reply = new InformationRequestReply();
        reply.setAccessType(replyTO.getInformationAccessType() != null ? replyTO.getInformationAccessType().toDmType() : null);
        reply.setAdditionalReplyInfo(replyTO.getAdditionalInfo());
        reply.setAttachmentURL(replyTO.getAttachmentURL());
        reply.setReplyDescription(replyTO.getDescription());
        reply.setInformationDetails(replyTO.getInformationDetails());
        reply.setReplyStatus(InformationReplyStatus.Approved);
        reply.setReplyCreatedDate(new Date());
        request.setValidTill(getSafeDate(replyTO.getValidTill()));
        final Set<InformationRequestCategory> categories = new HashSet<InformationRequestCategory>();
        final List<String> categoryIds = replyTO.getCategoryIds();
        if (categoryIds != null) {
            for (final String categoryUid : categoryIds) {
                final InformationRequestCategory category = new InformationRequestCategory();
                category.setCategoryUid(categoryUid);
                category.setRequest(request);
                categories.add(category);
            }
        }
        request.setCategories(categories);
        request.setReply(reply);
        informationRequestDao.update(request);

        notificationService.sendNotification(getValueFromBundle(INFORMATION_REQUEST_APPROVED_SUBJECT), 
                Collections.singletonList(request.getSender().getUid()), 
                MessageFormat.format(getValueFromBundle(INFORMATION_REQUEST_APPROVED_BODY), 
                        new Object[] {request.getDescription()}));
    }

    private InformationRequest loadRequestForReply(final Long requestId) {
        final InformationRequest request = loadRequest(requestId);
        
        if (request.getReply() != null && request.getReply().getReplyStatus() != null) {
            throw new IllegalStateException("InformationRequest with ID " + requestId + " is already processed.");
        }
        return request;
    }

    private InformationRequest loadRequest(final Long requestId) {
        final InformationRequest request = informationRequestDao.getById(requestId);
        if (request == null) {
            throw new IllegalArgumentException("InformationRequest with ID " + requestId + " is not found.");
        }
        return request;
    }

    /**
     * @param requestId
     * @param explanation
     */
    @Override
    public void declineRequest(Long requestId, final String employeeUid, String explanation) {
        final InformationRequest request = loadRequestForReply(requestId);
        final InformationRequestReply reply = new InformationRequestReply();
        reply.setReplyStatus(InformationReplyStatus.Declined);
        reply.setReplyDescription(explanation);
        if (employeeUid != null && !employeeUid.isEmpty()) {
            reply.setReplier(userDao.getOrCreateUser(employeeUid));
        }
        request.setReply(reply);
        informationRequestDao.update(request);

        notificationService.sendNotification(getValueFromBundle(INFORMATION_REQUEST_REJECTED_SUBJECT), 
                Collections.singletonList(request.getSender().getUid()), 
                MessageFormat.format(getValueFromBundle(INFORMATION_REQUEST_REJECTED_BODY), 
                        new Object[] {request.getDescription()}));
    }

    /**
     * @return
     */
    @Override
    public InformationCategoryTO getCategories(final String employeeUid) {
        final InformationCategoryTO cachedCategories = (InformationCategoryTO)cacheDao.get(InformationRequestServiceFacadeImpl.class, "getCategories");
        if (cachedCategories != null) {
            return cachedCategories;
        }

        final InformationCategoryTO rootCategoryTO = new InformationCategoryTO();
        rootCategoryTO.setCategoryId("root");
        rootCategoryTO.setDescription("Pääkategoria");
        rootCategoryTO.setName("kaikki");
        final List<InfoGroup> infoGroups = kksDao.getInfoGroups(employeeUid);
        rootCategoryTO.setSubcategories(convertInfoGroupsToCategories(infoGroups));
        
        cacheDao.put(InformationRequestServiceFacadeImpl.class, "getCategories", rootCategoryTO);
        
        return rootCategoryTO;
    }

    private List<InformationCategoryTO> convertInfoGroupsToCategories(final List<InfoGroup> infoGroups) {
        if (infoGroups == null || infoGroups.isEmpty()) {
            return Collections.emptyList();
        }
        
        final List<InformationCategoryTO> subcategories = new ArrayList<InformationCategoryTO>();
        for (final InfoGroup group : infoGroups) {
            final InformationCategoryTO subcategoryTO = new InformationCategoryTO();
            subcategoryTO.setCategoryId(group.getId());
            subcategoryTO.setName(group.getName());
            subcategoryTO.setDescription(group.getName());
            subcategoryTO.setSubcategories(convertInfoGroupsToCategories(group.getSubGroups()));
            subcategories.add(subcategoryTO);
        }
        return subcategories;
    }

    /**
     * @param receiverUid
     * @param informationRequestQuery
     * @return
     */
    @Override
    public List<InformationRequestSummary> getRepliedRequests(String receiverUid, InformationRequestQuery query) {
        final InformationRequestDTOCriteria criteria = getDtoCriteria(query != null ? query.getCriteria() : null);
        criteria.setReceiverUid(receiverUid);
        
        final List<InformationRequestSummary> result = new ArrayList<InformationRequestSummary>();
        for (final InformationRequest request : informationRequestDao.getRepliedRequests(criteria, query.getStartNum(), query.getMaxNum() - query.getStartNum() + 1)) {
            final InformationRequestSummary requestTO = new InformationRequestSummary();
            result.add(fillRequestSummaryByDO(request, requestTO));
        }
        return result;
    }

    private InformationRequestSummary fillRequestSummaryByDO(InformationRequest request, final InformationRequestSummary requestTO) {
        if (request.getReceiver() != null) {
            requestTO.setReceiverUid(request.getReceiver().getUid());
            requestTO.setReceiverUserInfo(getUserInfo(request.getReceiver()));
        }
        requestTO.setReceiverRoleUid(request.getReceiverRoleUid());
        requestTO.setRequestId(request.getId());
        requestTO.setSenderUid(request.getSender().getUid());
        requestTO.setSenderUserInfo(getUserInfo(request.getSender()));
        requestTO.setTargetPersonUid(request.getTargetPerson().getUid());
        requestTO.setTargetPersonUserInfo(getUserInfo(request.getTargetPerson()));
        requestTO.setTitle(request.getTitle());
        requestTO.setValidTill(CalendarUtil.getXmlDate(request.getValidTill()));
        final InformationRequestReply reply = request.getReply();
        if (reply == null || reply.getReplyStatus() == null) {
            requestTO.setStatus(InformationRequestStatus.Open);
        } else {
            if (reply.getReplyStatus() == InformationReplyStatus.Declined) {
                requestTO.setStatus(InformationRequestStatus.Declined);
            } else { // status is Approved
                if (requestTO.getValidTill() == null || requestTO.getValidTill().compare(CalendarUtil.getXmlDate(new Date())) >= 0) {
                    requestTO.setStatus(InformationRequestStatus.Valid);
                } else {
                    requestTO.setStatus(InformationRequestStatus.Expired);
                }
            }
        } 
        return requestTO;
    }
    
    private UserInfo getUserInfo(final User user) {
        return customerDao.getUserInfo(user);
    }

    /**
     * @param senderUid
     * @param query
     * @return
     */
    @Override
    public List<InformationRequestSummary> getSentRequests(String senderUid, InformationRequestQuery query) {
        final InformationRequestDTOCriteria criteria = getDtoCriteria(query.getCriteria());
        criteria.setSenderUid(senderUid);
        
        final List<InformationRequestSummary> result = new ArrayList<InformationRequestSummary>();
        for (final InformationRequest request : informationRequestDao.getSentRequests(criteria, query.getStartNum(), query.getMaxNum() - query.getStartNum() + 1)) {
            final InformationRequestSummary requestTO = new InformationRequestSummary();
            result.add(fillRequestSummaryByDO(request, requestTO));
        }
        return result;
    }

    /**
     * @param receiverUid
     * @return
     */
    @Override
    public int getTotalRepliedRequests(String receiverUid, InformationRequestCriteria criteria) {
        final InformationRequestDTOCriteria dtoCriteria = getDtoCriteria(criteria);
        dtoCriteria.setReceiverUid(receiverUid);
        return getIntValue(informationRequestDao.getTotalRepliedRequests(dtoCriteria));
    }

    private int getIntValue(final Long longValue) {
        if (longValue == null) {
            return 0;
        }
        return longValue.intValue();
    }

    /**
     * @param senderUid
     * @return
     */
    @Override
    public int getTotalSentRequests(String senderUid, final InformationRequestCriteria criteria) {
        final InformationRequestDTOCriteria dtoCriteria = getDtoCriteria(criteria);
        dtoCriteria.setSenderUid(senderUid);
        return getIntValue(informationRequestDao.getTotalSentRequests(dtoCriteria));
    }

    private InformationRequestDTOCriteria getDtoCriteria(final InformationRequestCriteria criteria) {
        return criteria == null ? new InformationRequestDTOCriteria() : criteria.toDtoCriteria();
    }

    /**
     * @param requestId
     * @return
     */
    @Override
    public InformationRequestDetail getRequestDetails(long requestId) {
        final InformationRequest request = loadRequest(requestId);
        
        final InformationRequestDetail requestTO = new InformationRequestDetail();
        fillRequestSummaryByDO(request, requestTO);
        
        requestTO.setDescription(request.getDescription());
        requestTO.setRequestPurpose(request.getRequestPurpose());
        requestTO.setLegislationInfo(request.getLegislationInfo());
        requestTO.setAdditionalInfo(request.getAdditionalInfo());
        requestTO.setCreatedDate(CalendarUtil.getXmlDate(request.getCreatedDate()));
        
        // fill reply info
        final InformationRequestReply reply = request.getReply();
        if (reply != null && reply.getReplyStatus() != null) {
            requestTO.setReplyDescription(reply.getReplyDescription());
            requestTO.setInformationDetails(reply.getInformationDetails());
            requestTO.setAdditionalReplyInfo(reply.getAdditionalReplyInfo());
            requestTO.setAttachmentURL(reply.getAttachmentURL());
            requestTO.setAccessType(InformationAccessType.valueOf(reply.getAccessType()));
        }

        final List<String> categories = new ArrayList<String>();
        final Map<String, String> categoryNames = new HashMap<String, String>();
        fillCategoryNames(categoryNames, getCategories(request.getSender().getUid()));
        for (final InformationRequestCategory category : request.getCategories()) {
            final String categoryUid = category.getCategoryUid();
            categories.add(categoryNames.containsKey(categoryUid) ? categoryNames.get(categoryUid) : categoryUid);
        }
        requestTO.setCategories(categories);

        return requestTO;
    }

    private void fillCategoryNames(final Map<String, String> categoryNames,
            final InformationCategoryTO categoryTO) {
        categoryNames.put(categoryTO.getCategoryId(), categoryTO.getName());
        if (categoryTO.getSubcategories() != null) {
            for (final InformationCategoryTO childCategory : categoryTO.getSubcategories()) {
                fillCategoryNames(categoryNames, childCategory);
            }
        }
    }

    /**
     * @param criteria
     * @return
     */
    @Override
    public int getTotalRequests(InformationRequestCriteria criteria) {
        return getIntValue(informationRequestDao.getTotalRepliedRequests(getDtoCriteria(criteria)));
    }

    /**
     * @param criteria
     * @return
     */
    @Override
    public List<InformationRequestSummary> getRequests(InformationRequestQuery query) {
        final InformationRequestDTOCriteria criteria = getDtoCriteria(query.getCriteria());
        
        final List<InformationRequestSummary> result = new ArrayList<InformationRequestSummary>();
        for (final InformationRequest request : informationRequestDao.getRepliedRequests(criteria, query.getStartNum(), query.getMaxNum() - query.getStartNum() + 1)) {
            final InformationRequestSummary requestTO = new InformationRequestSummary();
            result.add(fillRequestSummaryByDO(request, requestTO));
        }
        return result;
    }

}

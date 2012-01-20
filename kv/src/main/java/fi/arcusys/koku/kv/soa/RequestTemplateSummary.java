package fi.arcusys.koku.kv.soa;

/**
 * Data transfer object for communication with UI/Intalio process. Holds summary data about request template.
 * 
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Sep 2, 2011
 */
public class RequestTemplateSummary {
    private Long requestTemplateId;
    private String subject;
    private RequestTemplateVisibility visibility;
    private String creatorUid;
    
    /**
     * @return the creatorUid
     */
    public String getCreatorUid() {
        return creatorUid;
    }
    /**
     * @param creatorUid the creatorUid to set
     */
    public void setCreatorUid(String creatorUid) {
        this.creatorUid = creatorUid;
    }
    /**
     * @return the visibility
     */
    public RequestTemplateVisibility getVisibility() {
        return visibility;
    }
    /**
     * @param visibility the visibility to set
     */
    public void setVisibility(RequestTemplateVisibility visibility) {
        this.visibility = visibility;
    }
    /**
     * @return the requestTemplateId
     */
    public Long getRequestTemplateId() {
        return requestTemplateId;
    }
    /**
     * @param requestTemplateId the requestTemplateId to set
     */
    public void setRequestTemplateId(Long requestTemplateId) {
        this.requestTemplateId = requestTemplateId;
    }
    /**
     * @return the subject
     */
    public String getSubject() {
        return subject;
    }
    /**
     * @param subject the subject to set
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }
    
    
}

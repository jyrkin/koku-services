package fi.arcusys.koku.tiva.soa;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * Data transfer object for communication with UI/Intalio process. Holds data about reply to information request (tietopyyntö).
 * 
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Aug 22, 2011
 */
public class InformationRequestReplyTO {
    private Long requestId;
    private List<String> categoryIds;
    private XMLGregorianCalendar validTill;
    private String description;
    private String informationDetails;
    private String additionalInfo;
    private String attachmentURL;
    private InformationAccessType informationAccessType;
    private String replierUid;
    
    /**
     * @return the replierUid
     */
    public String getReplierUid() {
        return replierUid;
    }
    /**
     * @param replierUid the replierUid to set
     */
    public void setReplierUid(String replierUid) {
        this.replierUid = replierUid;
    }
    /**
     * @return the infoAccessType
     */
    public InformationAccessType getInformationAccessType() {
        return informationAccessType;
    }
    /**
     * @param infoAccessType the infoAccessType to set
     */
    public void setInformationAccessType(InformationAccessType infoAccessType) {
        this.informationAccessType = infoAccessType;
    }
    /**
     * @return the requestId
     */
    public Long getRequestId() {
        return requestId;
    }
    /**
     * @param requestId the requestId to set
     */
    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }
    /**
     * @return the categoryId
     */
    public List<String> getCategoryIds() {
        return categoryIds;
    }
    /**
     * @param categoryId the categoryId to set
     */
    public void setCategoryIds(List<String> categoryIds) {
        this.categoryIds = categoryIds;
    }
    /**
     * @return the informationDetails
     */
    public String getInformationDetails() {
        return informationDetails;
    }
    /**
     * @param informationDetails the informationDetails to set
     */
    public void setInformationDetails(String informationDetails) {
        this.informationDetails = informationDetails;
    }
    /**
     * @return the validTill
     */
    @XmlElement
    @XmlSchemaType(name = "date")
    public XMLGregorianCalendar getValidTill() {
        return validTill;
    }
    /**
     * @param validTill the validTill to set
     */
    public void setValidTill(XMLGregorianCalendar validTill) {
        this.validTill = validTill;
    }
    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }
    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }
    /**
     * @return the additionalInfo
     */
    public String getAdditionalInfo() {
        return additionalInfo;
    }
    /**
     * @param additionalInfo the additionalInfo to set
     */
    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }
    /**
     * @return the attachmentURL
     */
    public String getAttachmentURL() {
        return attachmentURL;
    }
    /**
     * @param attachmentURL the attachmentURL to set
     */
    public void setAttachmentURL(String attachmentURL) {
        this.attachmentURL = attachmentURL;
    }
    
    
}

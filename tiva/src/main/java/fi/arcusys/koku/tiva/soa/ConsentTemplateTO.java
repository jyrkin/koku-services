package fi.arcusys.koku.tiva.soa;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Data transfer object for communication with UI/Intalio process. Holds data about consent template.
 * 
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Aug 9, 2011
 */
@XmlType (name = "suostumuspohja", namespace = "http://soa.tiva.koku.arcusys.fi/",
propOrder={"description", "creatorUid", "actions", "templateType"})
public class ConsentTemplateTO extends ConsentTemplateSummary {
    private String description;
    private String creatorUid;
    private AuthorizationTemplateTO templateType;
    
    private List<ActionRequestTO> actions;
    
    /**
     * @return the templateType
     */
    public AuthorizationTemplateTO getTemplateType() {
        return templateType;
    }
    /**
     * @param templateType the templateType to set
     */
    public void setTemplateType(AuthorizationTemplateTO templateType) {
        this.templateType = templateType;
    }
    /**
     * @return the actions
     */
    @XmlElement(name = "toimenpiteet")
    public List<ActionRequestTO> getActions() {
        if (actions == null) {
            actions = new ArrayList<ActionRequestTO>();
        }
        return actions;
    }
    /**
     * @param actions the actions to set
     */
    public void setActions(List<ActionRequestTO> actions) {
        this.actions = actions;
    }
    /**
     * @return the description
     */
    @XmlElement(name = "saateteksti")
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
     * @return the creatorUid
     */
    @XmlElement(name = "laatija")
    public String getCreatorUid() {
        return creatorUid;
    }
    /**
     * @param creatorUid the creatorUid to set
     */
    public void setCreatorUid(String creatorUid) {
        this.creatorUid = creatorUid;
    }
    
}

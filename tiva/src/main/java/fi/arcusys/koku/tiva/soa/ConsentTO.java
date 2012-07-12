package fi.arcusys.koku.tiva.soa;

import java.util.List;

import fi.arcusys.koku.common.soa.Organization;

/**
 * Data transfer object for communication with UI/Intalio process. Holds detailed data about consent.
 *
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Aug 11, 2011
 */
public class ConsentTO extends ConsentSummary {
    private String comment;
    private List<ActionRequestSummary> actionRequests;
    private KksFormInstance kksFormInstance;
    private List<Organization> kksGivenTo;

    /**
     * @return the comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * @param comment the comment to set
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * @return the actionRequests
     */
    public List<ActionRequestSummary> getActionRequests() {
        return actionRequests;
    }

    /**
     * @param actionRequests the actionRequests to set
     */
    public void setActionRequests(List<ActionRequestSummary> actionRequests) {
        this.actionRequests = actionRequests;
    }

    /**
     * @return the kksFormInstance
     */
    public KksFormInstance getKksFormInstance() {
        return kksFormInstance;
    }

    /**
     * @param kksFormInstance the kksFormInstance to set
     */
    public void setKksFormInstance(KksFormInstance kksFormInstance) {
        this.kksFormInstance = kksFormInstance;
    }

    /**
     * @return the kksGivenTo
     */
    public List<Organization> getKksGivenTo() {
        return kksGivenTo;
    }

    /**
     * @param kksGivenTo the kksGivenTo to set
     */
    public void setKksGivenTo(List<Organization> kksGivenTo) {
        this.kksGivenTo = kksGivenTo;
    }
}

package fi.arcusys.koku.tiva.soa;

/**
 * Data transfer object for communication with UI/Intalio process. Holds query for searching consents.
 * 
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Aug 11, 2011
 */
public class ConsentQuery {
    private int startNum;
    private int maxNum;
    
    private ConsentCriteria criteria;

    public ConsentQuery() {
    }
    
    public ConsentQuery(int startNum, int maxNum) {
        this.startNum = startNum;
        this.maxNum = maxNum;
    }
    
    /**
     * @return the startNum
     */
    public int getStartNum() {
        return startNum;
    }

    /**
     * @param startNum the startNum to set
     */
    public void setStartNum(int startNum) {
        this.startNum = startNum;
    }

    /**
     * @return the maxNum
     */
    public int getMaxNum() {
        return maxNum;
    }

    /**
     * @param maxNum the maxNum to set
     */
    public void setMaxNum(int maxNum) {
        this.maxNum = maxNum;
    }

    /**
     * @return the criteria
     */
    public ConsentCriteria getCriteria() {
        return criteria;
    }

    /**
     * @param criteria the criteria to set
     */
    public void setCriteria(ConsentCriteria criteria) {
        this.criteria = criteria;
    }
}

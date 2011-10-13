package fi.arcusys.koku.common.soa;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Sep 21, 2011
 */
public class ChildWithHetu extends Child {
    private String hetu;

    public ChildWithHetu() {
    }
    
    /**
     * @param uid
     * @param displayName
     */
    public ChildWithHetu(Child child) {
        super(child);
        setParents(child.getParents());
    }

    /**
     * @return the hetu
     */
    public String getHetu() {
        return hetu;
    }

    /**
     * @param hetu the hetu to set
     */
    public void setHetu(String hetu) {
        this.hetu = hetu;
    }
}

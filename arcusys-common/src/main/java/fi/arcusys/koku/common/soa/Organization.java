/**
 *
 */
package fi.arcusys.koku.common.soa;

/**
 * Contains organization data
 *
 * @author Mikhail Kapotonov (mikhail.kapitonov@arcusys.fi)
 * Jul 11, 2012
 */
public class Organization {
    private String organizationId;
    private String organizationName;

    /**
     * @return the kksOrganizationId
     */
    public String getOrganizationId() {
        return organizationId;
    }

    /**
     * @param organizationId the organizationId to set
     */
    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    /**
     * @return the organizationName
     */
    public String getOrganizationName() {
        return organizationName;
    }

    /**
     * @param organizationName the organizationName to set
     */
    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

}

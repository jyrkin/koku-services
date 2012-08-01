/**
 *
 */
package fi.arcusys.koku.tiva.soa;

/**
 * Defines Organization for TIVA to KKS integration WS
 *
 * @author Mikhail Kapitonov (mikhail.kapitonov@arcusys.fi)
 * Aug 1, 2012
 */
public class KksOrganization {
    private String organizationId;
    private String organizationName;

    /**
     * @return the organizationId
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

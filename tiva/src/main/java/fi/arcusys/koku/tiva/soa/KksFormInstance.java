/**
 *
 */
package fi.arcusys.koku.tiva.soa;

import java.util.List;

/**
 * Holds KKS specific data about consent
 *
 * @author Mikhail Kapotonov (mikhail.kapitonov@arcusys.fi)
 * Jul 11, 2012
 */
public class KksFormInstance {
    private String instanceId;
    private String instanceName;
    private List<KksFormField> fields;

    /**
     * @return the instanceId
     */
    public String getInstanceId() {
        return instanceId;
    }

    /**
     * @param instanceId the instanceId to set
     */
    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    /**
     * @return the instanceName
     */
    public String getInstanceName() {
        return instanceName;
    }

    /**
     * @param instanceName the instanceName to set
     */
    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }

    /**
     * @return the fields
     */
    public List<KksFormField> getFields() {
        return fields;
    }

    /**
     * @param fields the fields to set
     */
    public void setFields(List<KksFormField> fields) {
        this.fields = fields;
    }
}

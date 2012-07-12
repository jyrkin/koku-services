/**
 *
 */
package fi.arcusys.koku.tiva.soa;

/**
 * Contains KKS field data
 *
 * @author Mikhail Kapotonov (mikhail.kapitonov@arcusys.fi)
 * Jul 11, 2012
 */
public class KksFormField {
    private String fieldId;
    private String fieldName;

    /**
     * @return the fieldId
     */
    public String getFieldId() {
        return fieldId;
    }

    /**
     * @param fieldId the fieldId to set
     */
    public void setFieldId(String fieldId) {
        this.fieldId = fieldId;
    }

    /**
     * @return the fieldName
     */
    public String getFieldName() {
        return fieldName;
    }

    /**
     * @param fieldName the fieldName to set
     */
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }
}

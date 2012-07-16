/**
 *
 */
package fi.arcusys.koku.common.service.datamodel;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * Entity for representing form field which is getting consent (for KKS integration)
 *
 * @author Mikhail Kapitonov (mikhail.kapitonov@arcusys.fi)
 * Jul 12, 2012
 */
@Entity
public class ConsentFieldPermission extends AbstractEntity {
    private String fieldId;
    private String fieldName;

    @ManyToOne
    private Consent consent;

    /**
     * @return the consent
     */
    public Consent getConsent() {
        return consent;
    }

    /**
     * @param consent the consent to set
     */
    public void setConsent(Consent consent) {
        this.consent = consent;
    }

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

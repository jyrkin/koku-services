/**
 *
 */
package fi.arcusys.koku.common.external;

import java.util.HashMap;
import java.util.Map;

/**
 * Mock LDAP DAO implementation for testing
 *
 * @author Mikhail Kapitonov (mikhail.kapitonov@arcusys.fi)
 * Jun 27, 2012
 */
public class LdapDAOTestImpl extends LdapDAOImpl {

    private static final Map<String, String> ssnKunpoName = new HashMap<String, String>();
    private static final Map<String, String> ssnLooraName = new HashMap<String, String>();

    private String generateSSN() {
        return (Math.random() * 1000000 - 1) + "-" + (Math.random() * 10000 - 1);
    }

    @Override
    public String getSsnByKunpoName(String citizenPortalName) {
        if (citizenPortalName == null) {
            return null;
        }

        String ssn = ssnKunpoName.get(citizenPortalName);

        if (ssn == null) {
            ssn = generateSSN();
            ssnKunpoName.put(citizenPortalName, ssn);
        }

        return ssn;
    }

    @Override
    public String getSsnByLooraName(String employeePortalName) {
        if (employeePortalName == null) {
            return null;
        }

        String ssn = ssnLooraName.get(employeePortalName);

        if (ssn == null) {
            ssn = generateSSN();
            ssnLooraName.put(employeePortalName, ssn);
        }

        return ssn;
    }

    @Override
    public String getKunpoUserPropertyBySsn(String ssn, String property) {
        return "kp_"+property+"_"+ssn;
    }

    @Override
    public String getLooraUserPropertyBySsn(String ssn, String property) {
        return "lp_"+property+"_"+ssn;
    }

}

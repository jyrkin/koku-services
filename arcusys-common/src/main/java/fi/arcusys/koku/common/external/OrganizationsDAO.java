/**
 *
 */
package fi.arcusys.koku.common.external;

import java.util.List;

import fi.arcusys.koku.common.soa.Organization;

/**
 * DAO interface for processing organizations
 *
 * @author Mikhail Kapitonov (mikhail.kapitonov@arcusys.fi)
 * Jul 16, 2012
 */
public interface OrganizationsDAO {

    /**
     * @param  employeeName
     * @return
     */
    List<Organization> getEmployeeOrganizations(final String employeeName);

}

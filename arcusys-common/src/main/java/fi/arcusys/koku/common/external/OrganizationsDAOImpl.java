/**
 *
 */
package fi.arcusys.koku.common.external;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import fi.arcusys.koku.common.soa.Organization;

/**
 * DAO implementation for processing organizations
 *
 * @author Mikhail Kapitonov (mikhail.kapitonov@arcusys.fi)
 * Jul 16, 2012
 */
@Stateless
public class OrganizationsDAOImpl implements OrganizationsDAO {

    @EJB
    private LdapDAO ldapDao;

    /**
     * @param  employeeName
     * @return
     */
    @Override
    public List<Organization> getEmployeeOrganizations(String employeeName) {
        return ldapDao.getEmployeeOrganizations(employeeName);
    }
}

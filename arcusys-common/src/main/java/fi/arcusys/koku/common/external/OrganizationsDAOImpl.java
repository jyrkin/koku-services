/**
 *
 */
package fi.arcusys.koku.common.external;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.sound.sampled.AudioInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fi.arcusys.koku.common.service.AuthorizationDAO;
import fi.arcusys.koku.common.soa.Organization;
import fi.koku.services.entity.family.v1.FamilyService;
import fi.koku.services.utility.authorizationinfo.v1.AuthorizationInfoService;
import fi.koku.services.utility.authorizationinfo.v1.AuthorizationInfoServiceFactory;
import fi.koku.services.utility.authorizationinfo.v1.model.OrgUnit;
import fi.koku.settings.KoKuPropertiesUtil;

/**
 * DAO implementation for processing organizations
 *
 * @author Mikhail Kapitonov (mikhail.kapitonov@arcusys.fi)
 * Jul 16, 2012
 */
@Stateless
public class OrganizationsDAOImpl implements OrganizationsDAO {

	private final static Logger logger = LoggerFactory.getLogger(OrganizationsDAOImpl.class);

	@EJB
	private CustomerServiceDAO customerService;

    private AuthorizationInfoService authService;

    private final static String AUTHORIZATIONINFO_SERVICE_ENDPOINT = KoKuPropertiesUtil.get("authorizationinfo.service.endpointaddress");
    private final static String authorizationInfoServiceUserId = KoKuPropertiesUtil.get("arcusys.authorizationinfo.service.user.id");
    private final static String authorizationInfoServiceUserPwd = KoKuPropertiesUtil.get("arcusys.authorizationinfo.service.password");


    @PostConstruct
    public void init() {
        try {
            AuthorizationInfoServiceFactory factory = new AuthorizationInfoServiceFactory(authorizationInfoServiceUserId, authorizationInfoServiceUserPwd, AUTHORIZATIONINFO_SERVICE_ENDPOINT);
            authService = factory.getAuthorizationInfoService();
        } catch(Exception re) {
            logger.error(null, re);
        }
    }

    /**
     * @param  employeeName
     * @return
     */
    @Override
    public List<Organization> getEmployeeOrganizations(String employeeName) {
        final String ssn = customerService.getSsnByLooraName(employeeName);
        final List<OrgUnit> orgUnits = authService.getUsersOrgUnits("", ssn);

        final List<Organization> result = new ArrayList<Organization>();

        for (OrgUnit unit : orgUnits) {
            final Organization organization = new Organization();
            organization.setOrganizationId(unit.getId());
            organization.setOrganizationName(unit.getName());
            result.add(organization);
        }

        return result;
    }
}

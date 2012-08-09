package fi.arcusys.koku.tiva.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fi.arcusys.koku.common.external.CustomerServiceDAO;
import fi.arcusys.koku.common.service.UserDAO;
import fi.arcusys.koku.common.soa.UsersAndGroupsService;
import fi.arcusys.koku.tiva.service.KksCollectionsDAO;
import fi.arcusys.koku.tiva.soa.KksFormField;
import fi.arcusys.koku.tiva.soa.KksFormInstance;
import fi.koku.services.entity.kks.v1.AuditInfoType;
import fi.koku.services.entity.kks.v1.GroupsHelper;
import fi.koku.services.entity.kks.v1.InfoGroup;
import fi.koku.services.entity.kks.v1.KksCollectionClassType;
import fi.koku.services.entity.kks.v1.KksCollectionInstanceCriteriaType;
import fi.koku.services.entity.kks.v1.KksCollectionInstanceType;
import fi.koku.services.entity.kks.v1.KksCollectionInstancesType;
import fi.koku.services.entity.kks.v1.KksEntryClassType;
import fi.koku.services.entity.kks.v1.KksEntryClassesType;
import fi.koku.services.entity.kks.v1.KksGroupType;
import fi.koku.services.entity.kks.v1.KksGroupsType;
import fi.koku.services.entity.kks.v1.ServiceFault;
import fi.koku.settings.KoKuPropertiesUtil;

/**
 * DAO interface implementation for accessing KKS-component in TIVA-Tietopyyntö functional area.
 * 
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Oct 14, 2011
 */
@Stateless
public class KksCollectionsDAOImpl implements KksCollectionsDAO {
    
    private final static Logger logger = LoggerFactory.getLogger(KksCollectionsDAOImpl.class);

    @EJB
    private UsersAndGroupsService usersService;
    
    @EJB
    private UserDAO userDao;
    
    @EJB
    private CustomerServiceDAO customerDao;

    private String uid = KoKuPropertiesUtil.get("arcusys.kks.service.user.id");
    private String pwd = KoKuPropertiesUtil.get("arcusys.kks.service.password");
    private String component;
    private String urlBase;
    
    private GroupsHelper helper;
    
    @PostConstruct
    public void init() {
        try {
            final InitialContext ctx = new InitialContext();
            urlBase = (String) ctx.lookup("koku/urls/kksservice-baseurl");
            logger.debug("Overwrite urlBase with " + urlBase);
        } catch (NamingException e) {
            logger.error(null, e);
        }

        try {
            helper = new GroupsHelper(uid, pwd, component, urlBase);
        } catch(RuntimeException re) {
            logger.error(null, re);
        } 
    }
    
    /**
     * @param employeeUid
     * @return
     */
    @Override
    public List<InfoGroup> getInfoGroups(String employeeUid) {
        if (helper == null) {
            throw new IllegalStateException("Failed to initialize communication with KKS.");
        }
        
        final String ssnByLdapName = usersService.getSsnByLdapName(userDao.getUserByUid(employeeUid).getEmployeePortalName());
        try {
            return helper.getInfoGroups(ssnByLdapName); 
        } catch(RuntimeException re) {
            logger.error("Failed to get InfoGroups by employeeUid " + employeeUid + " and ssn " + ssnByLdapName, re);
            throw re;
        }
    }

    private Map<String, KksEntryClassType> classesLookup(KksEntryClassesType classes) {
        Map<String, KksEntryClassType> ret = new HashMap<String, KksEntryClassType>();

        for (KksEntryClassType classType : classes.getKksEntryClass()) {
            ret.put(classType.getId(), classType);

        }

        return ret;
    }

    private Map<String, KksEntryClassType> groupLookup(KksGroupsType groups) {
        Map<String, KksEntryClassType> ret = new HashMap<String, KksEntryClassType>();

        for (KksGroupType group : groups.getKksGroup()) {
            ret.putAll(classesLookup(group.getKksEntryClasses()));
            ret.putAll(groupLookup(group.getSubGroups()));

        }

        return ret;
    }

    private Map<String, KksEntryClassType> getEntryClassMap(KksCollectionClassType kksCollectionClass) {
        Map<String, KksEntryClassType> ret = new HashMap<String, KksEntryClassType>();

        ret.putAll(groupLookup(kksCollectionClass.getKksGroups()));

        return ret;
    }

    /**
     * @param kksCode
     * @param targetPersonUid
     * @return
     */
    @Override
    public List<KksFormInstance> getKksFormInstances(final String kksCode, final String targetPersonUid) {
        if (kksCode == null) {
            throw new IllegalArgumentException("Can't get KKS form instances without KKS code specified.");
        }

        if (targetPersonUid == null) {
            throw new IllegalArgumentException("Can't get KKS form instances without user uid");
        }

        String targetPersonSSN = customerDao.getSsnByUserUid(targetPersonUid);
        if (targetPersonSSN == null) {
            throw new IllegalArgumentException("Could not find ssn for specified uid '"+targetPersonUid+"'");
        }

        List<KksFormInstance> instances = new ArrayList<KksFormInstance>();

        KksCollectionInstanceCriteriaType criteria = new KksCollectionInstanceCriteriaType();
        criteria.setPic(targetPersonSSN);
        criteria.setType(kksCode);

        AuditInfoType auditHeader = new AuditInfoType();
        auditHeader.setComponent("TIVA");
        auditHeader.setUserId(targetPersonSSN);

        final KksCollectionInstancesType kksCollectionInstances = helper.getCollectionInstances(targetPersonSSN, kksCode);

        for (KksCollectionInstanceType kksCollectionInstance : kksCollectionInstances.getCollections()) {
            KksFormInstance formInstance = new KksFormInstance();
            formInstance.setInstanceId(kksCollectionInstance.getId());
            formInstance.setInstanceName(kksCollectionInstance.getName());

            KksCollectionClassType collectionClass = kksCollectionInstances.getCollectionClass();

            Map<String, KksEntryClassType> entryMap = getEntryClassMap(collectionClass);

            formInstance.setFields(new ArrayList<KksFormField>());
            for (final String fieldId : entryMap.keySet()) {
                KksFormField fieldData = new KksFormField();
                fieldData.setFieldId(fieldId);
                fieldData.setFieldName(entryMap.get(fieldId).getName());
                formInstance.getFields().add(fieldData);
            }

            instances.add(formInstance);
        }

        return instances;
    }

}

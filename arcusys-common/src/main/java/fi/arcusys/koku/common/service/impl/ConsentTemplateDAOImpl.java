package fi.arcusys.koku.common.service.impl;

import java.util.Collections;
import java.util.List;

import javax.ejb.Stateless;

import fi.arcusys.koku.common.service.ConsentTemplateDAO;
import fi.arcusys.koku.common.service.datamodel.ConsentTemplate;

/**
 * DAO implementation for CRUD operations with 'ConsentTemplate' Entity
 * 
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Aug 23, 2011
 */
@Stateless
public class ConsentTemplateDAOImpl extends AbstractEntityDAOImpl<ConsentTemplate> implements ConsentTemplateDAO {

    public ConsentTemplateDAOImpl() {
        super(ConsentTemplate.class);
    }

    /**
     * @param searchString
     * @param limit
     * @return
     */
    @Override
    public List<ConsentTemplate> searchTemplates(String searchString, int limit) {
        return super.getResultList("findTemplatesByPrefix", Collections.<String, Object>singletonMap("prefix", searchString + "%"), FIRST_RESULT_NUMBER, limit);
    }
}

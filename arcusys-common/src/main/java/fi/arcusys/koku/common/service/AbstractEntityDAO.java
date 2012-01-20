package fi.arcusys.koku.common.service;

import java.util.List;

/**
 * Common DAO interface for all other DAO interfaces with real entities. 
 * Defines common set of CRUD operations, available for all entities.   
 * 
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * May 20, 2011
 */
public interface AbstractEntityDAO<T> {
	public static final int FIRST_RESULT_NUMBER = 1; 
	public static final int MAX_RESULTS_COUNT = Integer.MAX_VALUE; 

	T create(final T entity);

	T getById(final Long entityId);

	List<T> getListByIds(final List<Long> entityIds);

	void delete(final T entity);

	int deleteAll(final List<Long> entityIds);
	
	T update(final T entity);
}
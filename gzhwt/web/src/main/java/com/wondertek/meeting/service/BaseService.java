package com.wondertek.meeting.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.exception.HibernateDaoSupportException;
import com.wondertek.meeting.exception.ServiceException;

public interface BaseService<T, PK extends Serializable> {

    /**
     * The <code>queryList(PK startRecord, PK pageSize)</code> method is query
     * objects according startRecord and pagesize're number, object type is
     * according your implements this method's define type, and implements this
     * interface abstract class must be override all method and inject entity
     * type.
     * 
     * @param startRecord
     *            Where from the beginning to show this record
     * @param pageSize
     *            The number of records per page
     * @param clazz
     *            according class
     * @return List<T> T is your inject object's type, List is query all object
     *         connection
     * 
     * @throws HibernateDaoSupportException
     *             Throws HibernateDaoSupportException when accessing and
     *             manipulating database happen exception.
     */
    public List<T> queryList(int startRecord, int pageSize)
            throws ServiceException;

    /**
     * The <code>getRecordCount()</code> method is used for getting the total
     * count of records.
     * 
     * @return PK return total of record counts
     * @throws ServiceException
     */
    public long countAll()
            throws ServiceException;

    /**
     * The <code>find(T entity)</code> method is find object according object
     * type.
     * 
     * @param entity
     *            if you want to find class condition.
     * @return List<T> according entity to find object's connection.
     * @throws HibernateDaoSupportException
     *             Throws HibernateDaoSupportException when accessing and
     *             manipulating database happen exception.
     * 
     */
    public List<T> findAll() throws ServiceException;

    /**
     * The <code>findById(PK id)</code> method is find object according
     * primary key.
     * 
     * @param id
     *            if you want to find object's primary key
     * @return T insject object
     * @throws HibernateDaoSupportException
     *             Throws HibernateDaoSupportException when accessing and
     *             manipulating database happen exception.
     */
    public T findById(PK id)
            throws ServiceException;

    /**
     * The <code>add(T entity)</code> method is add object to database.
     * 
     * @param entity
     *            if you want to add entity.
     * 
     * @throws HibernateDaoSupportException
     *             Throws HibernateDaoSupportException when accessing and
     *             manipulating database happen exception.
     */
    public PK add(T entity) throws ServiceException;
    
    /**
	 * 保存或更新
	 * @param entity
	 * @throws ServiceException
	 */
	public void saveOrUpdate(T entity) throws ServiceException;

    /**
     * The <code>delete(T entity)</code> method is delete object to database.
     * 
     * @param entity
     *            if you want to delete entity.
     * 
     * @throws HibernateDaoSupportException
     *             Throws HibernateDaoSupportException when accessing and
     *             manipulating database happen exception.
     */
    public void delete(T entity) throws ServiceException;

    /**
     * The <code>modifty(T entity)</code> method is update object to database.
     * 
     * @param entity
     *            if you want to update entity.
     * @throws HibernateDaoSupportException
     *             Throws HibernateDaoSupportException when accessing and
     *             manipulating database happen exception.
     */
    public void modify(T entity) throws ServiceException;

    /**
     * find page object's connection with class
     * 
     * @param clazz
     *            according class
     * @param currentPage
     *            current page
     * @param pageSize
     *            the number of records per page
     * @return Object's connection
     * @throws HibernateDaoSupportException
     *             when accessing and manipulating database happen exception
     */
    public Pager<T> findPager(int currentPage, int pageSize)
            throws ServiceException;

    /**
     * find page object's connection with hql and param map
     * 
     * @param hql
     *            according hql if class param is null
     * @param currentPage
     *            current page
     * @param pageSize
     *            the number of records per page
     * @param properties
     *            according param map
     * @return Object's connection
     * @throws HibernateDaoSupportException
     *             when accessing and manipulating database happen exception
     */
    public Pager<T> findPager(String hql, int currentPage, int pageSize,
            Map<String, Object> properties) throws ServiceException;

    /**
     * find object's connection with hql
     * 
     * @param hql
     *            according hql
     * @return Object's connection
     * @throws HibernateDaoSupportException
     *             when accessing and manipulating database happen exception
     */
    public List<T> getObjects(String hql) throws ServiceException;

    /**
     * find object's connection with hql and param map
     * 
     * @param hql
     *            according hql
     * @param properties
     *            according param map
     * @return Object's connection
     * @throws HibernateDaoSupportException
     *             when accessing and manipulating database happen exception
     */
    public List<T> getObjects(String hql, Map<String, Object> properties)
            throws ServiceException;

    /**
     * find object with hql and param map
     * 
     * @param hql
     *            according hql
     * @param properties
     *            according param map
     * @return Object which find
     * @throws HibernateDaoSupportException
     *             when accessing and manipulating database happen exception
     */
    public Object getUniqueBeanResult(String hql, Map<String, Object> properties)
            throws ServiceException;

    /**
     * find object with hql
     * 
     * @param hql
     *            according hql
     * @return Object which find
     * @throws HibernateDaoSupportException
     *             when accessing and manipulating database happen exception
     */
    public Object getUniqueBeanResult(String hql)
            throws ServiceException;

    /**
     * update entity with hql and param map
     * 
     * @param hql
     *            according hql
     * @param properties
     *            according param map
     * @return the count of success record
     * @throws HibernateDaoSupportException
     *             when accessing and manipulating database happen exception
     */
    public int executeUpdate(String hql, Map<String, Object> properties)
            throws ServiceException;

    /**
     * update entity with hql and param arrary
     * 
     * @param hql
     *            according hql
     * @param values
     *            according param arrary
     * @return the count of success record
     * @throws HibernateDaoSupportException
     *             when accessing and manipulating database happen exception
     */
    public int executeUpdate(String hql, Object[] values)
            throws ServiceException;

    /**
     * find object's connection with hql class and param map
     * 
     * @param hql
     *            according hql if class param is null
     * @param startRecord
     *            Where from the beginning to show this record
     * @param pageSize
     *            the number of records per page
     * @param clazz
     *            according class
     * @param properties
     *            according param map
     * @return Object's connection
     * @throws HibernateDaoSupportException
     *             when accessing and manipulating database happen exception
     */
    public List<T> queryList(String hql, int startRecord, int pageSize,
             Map<String, Object> properties)
            throws ServiceException;

    /**
     * find object's connection with class
     * 
     * @param clazz
     *            according class
     * @return Object's connection
     * @throws HibernateDaoSupportException
     *             when accessing and manipulating database happen exception
     */
    public List<T> find(Class<T> clazz) throws ServiceException;

}

package com.wirelesscity.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.wirelesscity.exception.HibernateDaoSupportException;
import com.wirelesscity.exception.ServiceException;

public interface BaseService<T, PK extends Serializable> {
	
	
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
     * 
     * @param hql
     * @param properties
     * @return
     * @throws ServiceException
     */
    public List<T> queryList(String hql,
			 Map<String, Object> properties)
			throws ServiceException;
    

}

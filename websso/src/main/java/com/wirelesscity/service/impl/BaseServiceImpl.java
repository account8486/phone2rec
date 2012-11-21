package com.wirelesscity.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.util.GenericsUtil;
import com.wirelesscity.dao.BaseDao;
import com.wirelesscity.exception.ServiceException;
import com.wirelesscity.service.BaseService;

public class BaseServiceImpl<T, PK extends Serializable> implements
		BaseService<T, PK> {

	protected BaseDao<T, PK> basicDao;

	private Class<T> entityClass;

	@SuppressWarnings("unchecked")
	public BaseServiceImpl() {
		// 构造时对泛型的类型进行初始化
		entityClass = GenericsUtil.getSuperClassGenricType(getClass());
	}

	/**
	 * 返回泛型的类型
	 * 
	 * @return
	 */
	public Class<T> getEntityClass() {
		return entityClass;
	}

	public BaseDao<T, PK> getBaseDao() {
		return basicDao;
	}

	public void setBaseDao(BaseDao<T, PK> basicDao) {
		this.basicDao = basicDao;
	}

	public Logger log = LoggerFactory.getLogger(this.getClass());

	public List<T> findAll() throws ServiceException {
		log.debug("find all record...");
		return basicDao.find(entityClass);
	}

	public List<T> find(Class<T> clazz) throws ServiceException {
		return basicDao.find(clazz);
	}

	public T findById(PK id) throws ServiceException {
		return basicDao.findById(id, entityClass);
	}

	public PK add(T entity) throws ServiceException {
		return basicDao.add(entity);
	}

	/**
	 * 保存或更新
	 * 
	 * @param entity
	 * @throws ServiceException
	 */
	public void saveOrUpdate(T entity) throws ServiceException {
		basicDao.saveOrUpdateEntity(entity);
	}

	public void delete(T entity) throws ServiceException {
		basicDao.delete(entity);
	}

	public void modify(T entity) throws ServiceException {
		basicDao.modify(entity);
	}
	
	
	/**
	 * 
	 * @param hql
	 * @param properties
	 * @return
	 * @throws ServiceException
	 */
	public List<T> queryList(String hql,
			 Map<String, Object> properties)
			throws ServiceException{
		return basicDao.queryList(hql, 1, 100, entityClass, properties);
	}
			
	public List<T> queryList(String hql, int startRecord, int pageSize,
			 Map<String, Object> properties)
			throws ServiceException {
		return basicDao.queryList(hql, startRecord, pageSize, entityClass, properties);
	}

}

package com.wondertek.meeting.service.impl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.dao.BaseDao;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.service.BaseService;
import com.wondertek.meeting.util.GenericsUtil;

public class BaseServiceImpl<T, PK extends Serializable> implements BaseService<T, PK>{

	public Logger log = LoggerFactory.getLogger(this.getClass());
	
    private Class<T> entityClass;

	@SuppressWarnings("unchecked")
	public BaseServiceImpl() {
		//构造时对泛型的类型进行初始化
		entityClass = GenericsUtil.getSuperClassGenricType(getClass());
	}

	/**
	 * 返回泛型的类型
	 * @return
	 */
	public Class<T> getEntityClass() {
		return entityClass;
	}
	
	protected BaseDao<T, PK> basicDao;

	public BaseDao<T, PK> getBaseDao() {
		return basicDao;
	}

	public void setBaseDao(BaseDao<T, PK> basicDao) {
		this.basicDao = basicDao;
	}

	
	public PK add(T entity) throws ServiceException {
		return basicDao.add(entity);
	}
	
	/**
	 * 保存或更新
	 * @param entity
	 * @throws ServiceException
	 */
	public void saveOrUpdate(T entity) throws ServiceException {
		 basicDao.saveOrUpdateEntity(entity);
	}

	
	public long countAll() throws ServiceException {
		return basicDao.getRecordCount(entityClass);
	}

	
	public void delete(T entity) throws ServiceException {
		basicDao.delete(entity);
	}

	
	public int executeUpdate(String hql, Map<String, Object> properties)
			throws ServiceException {
		return basicDao.executeUpdate(hql, properties);
	}

	
	public int executeUpdate(String hql, Object[] values)
			throws ServiceException {
		return basicDao.executeUpdate(hql, values);
	}

	
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

	
	public Pager<T> findPager(int currentPage, int pageSize)
			throws ServiceException {
		return basicDao.findPager(currentPage, pageSize, entityClass);
	}

	
	public Pager<T> findPager(String hql, int currentPage, int pageSize,
			Map<String, Object> properties) throws ServiceException {
		return basicDao.findPager(hql, currentPage, pageSize, properties);
	}

	
	public List<T> getObjects(String hql) throws ServiceException {
		return basicDao.getObjects(hql);
	}

	
	public List<T> getObjects(String hql, Map<String, Object> properties)
			throws ServiceException {
		return basicDao.getObjects(hql, properties);
	}

	
	public Object getUniqueBeanResult(String hql, Map<String, Object> properties)
			throws ServiceException {
		return basicDao.getUniqueBeanResult(hql, properties);
	}

	
	public Object getUniqueBeanResult(String hql) throws ServiceException {
		return basicDao.getUniqueBeanResult(hql);
	}

	
	public void modify(T entity) throws ServiceException {
		basicDao.modify(entity);
	}

	
	public List<T> queryList(int startRecord, int pageSize)
			throws ServiceException {
		return basicDao.queryList(startRecord, pageSize, entityClass);
	}

	
	public List<T> queryList(String hql, int startRecord, int pageSize,
			 Map<String, Object> properties)
			throws ServiceException {
		return basicDao.queryList(hql, startRecord, pageSize, entityClass, properties);
	}
	
	/**
	 * BigDecimal 求均,处理了null的情况
	 * 
	 * @param value
	 * @param count
	 * @return
	 */
	protected BigDecimal average(BigDecimal value, BigDecimal count) {
		if (null == value || BigDecimal.ZERO.equals(value)) {
			return BigDecimal.ZERO;
		}
		return value.divide(count, 4, BigDecimal.ROUND_HALF_UP).setScale(2,
				BigDecimal.ROUND_HALF_UP);
	}

	/**
	 * BigDecimal 数相加,处理了null的情况
	 * 
	 * @param current
	 * @param now
	 * @return
	 */
	protected BigDecimal addDecm(BigDecimal current, BigDecimal now) {
		if (null == current) {
			current = BigDecimal.ZERO;
		}
		if (null == now) {
			now = BigDecimal.ZERO;
		}
		return current.add(now);
	}
}

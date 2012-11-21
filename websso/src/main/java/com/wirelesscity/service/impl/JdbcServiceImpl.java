package com.wirelesscity.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.pager.Pager;
import com.wirelesscity.common.Validity;
import com.wirelesscity.exception.HibernateDaoSupportException;
import com.wirelesscity.service.JdbcService;

public class JdbcServiceImpl extends HibernateDaoSupport implements JdbcService {
	public Logger log = LoggerFactory.getLogger(this.getClass());

	JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	
	public SqlRowSet getResultSet(String sql) {
		SqlRowSet rs =null;
		try {
		    rs = jdbcTemplate.queryForRowSet(sql);
		    
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return rs;

	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List getList(String sql) {
		List<Map> list = jdbcTemplate.queryForList(sql);
		log.debug(String.valueOf(list.size()));
		for (Object obj : list) {
			Map map4entity = (Map) obj;
			log.error(String.valueOf(map4entity.get("username")));

		}

		return list;
	}
	
	public Pager findPagerBySql(String querySql, String queryCondtion,
			int currentPage, int pageSize) throws HibernateDaoSupportException{
		
		return findPagerBySql( querySql,  queryCondtion,
				 currentPage,  pageSize, null);
		
	}
	
	/**
	 * 通过sql来获取查询列表
	 * 
	 * @param querySql
	 * @param queryCondtion
	 * @param currentPage
	 * @param pageSize
	 * @param properties
	 * @return
	 * @throws HibernateDaoSupportException
	 */
	public Pager findPagerBySql(String querySql, String queryCondtion,
			int currentPage, int pageSize, final Map<String, Object> properties)
			throws HibernateDaoSupportException {
		Pager pager = new Pager();
		if (currentPage < 1) {
			currentPage = 1;
		}
		int total = this.getCountSql(querySql + queryCondtion);
		pager.setCurrentPage(currentPage);
		pager.setPageSize(pageSize);
		pager.setTotal(total);

		List list = null;
		list = this.queryListSql((querySql + queryCondtion), (currentPage - 1)
				* pageSize, pageSize, properties);

		pager.setPageRecords(list);

		return pager;
	}
	
	

	  /**
     * find object's connection with sql class and param map
     * 
     * @param sql
     *            according sql if class param is null
     * @param startRecord
     *            Where from the beginning to show this record
     * @param pageSize
     *            the number of records per page
     * @param properties
     *            according param map
     * @return Object's connection
     * @throws HibernateDaoSupportException
     *             when accessing and manipulating database happen exception
     */
    @SuppressWarnings("unchecked")
    public List queryListSql(final String sql, final int startRecord,
            final int pageSize, final Map<String, Object> properties)
            throws HibernateDaoSupportException {
        if (sql == null) {
            throw new HibernateDaoSupportException("Param(#sql#) is null");
        }
        try {
        	getHibernateTemplate().setCacheQueries(false);
            return getHibernateTemplate().executeFind(new HibernateCallback() {
                public Object doInHibernate(Session session) {
                	
                    Query query = session.createSQLQuery(sql);
                    if (!Validity.isEmpty(properties)) {
                        query.setProperties(properties);
                    }
                    if (startRecord >= 0 && pageSize >= 0) {
                        query.setFirstResult(startRecord).setMaxResults(
                                pageSize);
                        
                    
                    }
                    return query.list();
                }
            });
        } catch (DataAccessException e) {
            log.error("Error executing queryList ({}):{}", sql, e);
            throw new HibernateDaoSupportException(
                    "Error executing queryList (" + sql + "):" + e);
        }
    }
	
	
	/**
	 * 获取SQL的总数
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public int getCountSql(String sql) {
		int total = 0;
		StringBuffer querySql = new StringBuffer();
		querySql.append(" select count(1) as total from  ( ");
		querySql.append(sql);
		querySql.append(" ) ");

		List list = this.getList(querySql.toString());
		if (list != null && list.size() > 0) {
			total = Integer.parseInt(String.valueOf(((HashMap) list.get(0))
					.get("total")));
		}

		return total;
	}
	
	
	
	
	
	
	
	
	

}

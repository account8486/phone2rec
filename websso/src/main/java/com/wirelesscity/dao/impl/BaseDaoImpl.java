package com.wirelesscity.dao.impl;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import com.wirelesscity.common.Validity;
import com.wirelesscity.dao.BaseDao;
import com.wirelesscity.exception.HibernateDaoSupportException;

/**
 * 
 * @param <T>
 *            your inject object's type
 * @param <PK>
 *            prime key
 */
public abstract class BaseDaoImpl<T, PK extends Serializable> extends
        HibernateDaoSupport implements BaseDao<T, PK> {

    protected Logger log = LoggerFactory.getLogger(this.getClass());
    
    
    private boolean isCacheQueries = true;

    public boolean isCacheQueries() {
        return isCacheQueries;
    }

    public void setCacheQueries(boolean isCacheQueries) {
        this.isCacheQueries = isCacheQueries;
    }

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
    @Transactional(rollbackFor = Exception.class)
    public PK add(T entity) throws HibernateDaoSupportException {
        if (null == entity) {
            throw new HibernateDaoSupportException("Param(#"
                    + this.getClass().getName() + ") with value null");
        }

        try {
            PK pk = (PK)this.getHibernateTemplate().save(entity);
            log.debug("Executing save method is successful!");
            return pk;
        } catch (DataAccessException e) {
            log.error("Error saving entity:{}", e);
            throw new HibernateDaoSupportException("Error saving (#"
                    + this.getClass().getName() + "#):" + e);
        }
    }
    
    
    /**
     * 更新或保存实体
     * @param entity
     */
    public void saveOrUpdateEntity(T entity){
    	this.getHibernateTemplate().saveOrUpdate(entity);
    }

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
    @Transactional(rollbackFor = Exception.class)
    public void delete(T entity) throws HibernateDaoSupportException {
        if (null == entity) {
            throw new HibernateDaoSupportException("Param(#"
                    + this.getClass().getName() + ") with value null");
        }

        try {
            this.getHibernateTemplate().delete(entity);
            log.debug("Execute delete method is successful!");
        } catch (DataAccessException e) {
            log.error("Error deleting entity:{}", e);
            throw new HibernateDaoSupportException("Error deleting (#"
                    + this.getClass().getName() + "#):" + e);
        }
    }

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
    @SuppressWarnings("unchecked")
    public List<T> find(T entity) throws HibernateDaoSupportException {
        if (null == entity) {
            throw new HibernateDaoSupportException("Param(#"
                    + this.getClass().getName() + ") with value null");
        }

        List lists = null;
        try {
            if (isCacheQueries) {
                this.getHibernateTemplate().setCacheQueries(true);
            } else {
                isCacheQueries = true;
                this.getHibernateTemplate().setCacheQueries(false);
            }
            lists = (List<T>) this.getHibernateTemplate().findByExample(entity);
            log.debug("Execute find method is successful!");
        } catch (DataAccessException e) {
            log.error("Error finding entity: {}", e);
            throw new HibernateDaoSupportException("Error finding (#"
                    + this.getClass().getName() + "#):" + e);
        }

        return lists;
    }

    /**
     * find object's collection with class
     * 
     * @param clazz
     *            according class
     * @return Object's connection
     * @throws HibernateDaoSupportException
     *             when accessing and manipulating database happen exception
     */
    @SuppressWarnings("unchecked")
    public List<T> find(Class<T> clazz) throws HibernateDaoSupportException {
        if (null == clazz) {
            throw new HibernateDaoSupportException(
                    "Param(#clazz) with value null");
        }
        String hql = "FROM " + clazz.getName();

        List<T> lists = null;
        try {
            if (isCacheQueries) {
                this.getHibernateTemplate().setCacheQueries(true);
            } else {
                isCacheQueries = true;
                this.getHibernateTemplate().setCacheQueries(false);
            }
            lists = (List<T>) this.getHibernateTemplate().find(hql);
            log.debug("Execute find method is successful!");
        } catch (DataAccessException e) {
            log.error("Error finding entity:{}", e);
            throw new HibernateDaoSupportException("Error finding (#"
                    + this.getClass().getName() + "#):" + e);
        }
        return lists;
    }

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
    @SuppressWarnings("unchecked")
    public T findById(PK id, Class<T> clazz)
            throws HibernateDaoSupportException {
        if (null == id) {
            throw new HibernateDaoSupportException("PK with value null");
        }

        T entity = null;
        String hql = "FROM " + clazz.getName() + " n where n.id = ";
        
        if(id instanceof String){
        	hql +=  "'"+id+"'";
        }else{
        	hql += id;
        }
        try {
            //use 2 leave cache
            entity = (T) this.getUniqueBeanResult(hql);
            log.debug("Execute findById method is successful!");
        } catch (DataAccessException e) {
            log.error("Error finding entity:{}", e);
            throw new HibernateDaoSupportException("Error finding ("
                    + this.getClass().getName() + "):" + e);
        }
        return entity;

    }

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
    public List<T> queryList(int startRecord, int pageSize, Class<T> clazz)
            throws HibernateDaoSupportException {
        return queryList(null, startRecord, pageSize, clazz, null);
    }

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
    @SuppressWarnings("unchecked")
    public List<T> queryList(String hql, final int startRecord,
            final int pageSize, Class<T> clazz,
            final Map<String, Object> properties)
            throws HibernateDaoSupportException {
        if (hql == null && clazz == null) {
            throw new HibernateDaoSupportException(
                    "Param(#hql#) and param(#clazz#) is null");
        }
        if (clazz != null) {
            hql = "FROM " + clazz.getName();
        }
        final String queryHql = hql;
        try {
            return getHibernateTemplate().executeFind(new HibernateCallback() {
                public Object doInHibernate(Session session) {
                    Query query = session.createQuery(queryHql);
                    if (isCacheQueries) {
                        query.setCacheable(true);
                    } else {
                        isCacheQueries = true;
                        query.setCacheable(false);
                    }
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
            log.error("Error executing queryList ({}):{}", hql, e);
            throw new HibernateDaoSupportException(
                    "Error executing queryList (" + hql + "):" + e);
        }
    }

    /**
     * The <code>getRecordCount()</code> method is used for getting the total
     * count of records.
     * 
     * @return PK return total of record counts
     * @throws HibernateDaoSupportException
     *             Throws HibernateDaoSupportException when accessing and
     *             manipulating database happen exception.
     */
    public long getRecordCount(Class<T> clazz)
            throws HibernateDaoSupportException {
        String hql = "SELECT COUNT(*) FROM " + clazz.getName();
        Integer count = null;

        try {
            if (isCacheQueries) {
                this.getHibernateTemplate().setCacheQueries(true);
            } else {
                isCacheQueries = true;
                this.getHibernateTemplate().setCacheQueries(false);
            }
            count = ((Long) this.getHibernateTemplate().find(hql).iterator()
                    .next()).intValue();
            log.debug("Execute getRecordCount method is successful!");
        } catch (DataAccessException e) {
            log.error("Error getting count:{}", e);
            throw new HibernateDaoSupportException("Error getting count for ("
                    + this.getClass().getName() + "):" + e);
        }

        return count;
    }

    /**
     * get count with select hql and param map
     * 
     * @param selectHql
     *            according select hql
     * @param properties
     *            according param map
     * @return count of hql
     * @throws HibernateDaoSupportException
     *             when accessing and manipulating database happen exception
     */
    public int getRecordCount(String selectHql, Map<String, Object> properties)
            throws HibernateDaoSupportException {
        String countHql = getCountHql(selectHql);
        return ((Long) getUniqueBeanResult(countHql, properties)).intValue();
    }

    /**
     * The <code>modifty(T entity)</code> method is update object to database.
     * 
     * @param entity
     *            if you want to update entity.
     * @throws HibernateDaoSupportException
     *             Throws HibernateDaoSupportException when accessing and
     *             manipulating database happen exception.
     */
    @Transactional(rollbackFor = Exception.class)
    public void modify(T entity) throws HibernateDaoSupportException {
        if (null == entity) {
            throw new HibernateDaoSupportException("Param(#"
                    + this.getClass().getName() + ") with value null");
        }
        try {
            this.getHibernateTemplate().update(entity);
            log.debug("Execute update method is successful!");
        } catch (DataAccessException e) {
            log.error("Error updating entity:{}", e);
            throw new HibernateDaoSupportException("Error updating (#"
                    + this.getClass().getName() + "#):" + e);
        }
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
     * find object's connection with hql
     * 
     * @param hql
     *            according hql
     * @return Object's connection
     * @throws HibernateDaoSupportException
     *             when accessing and manipulating database happen exception
     */
    public List<T> getObjects(String hql) throws HibernateDaoSupportException {
        return getObjects(hql, new HashMap<String, Object>(0));
    }

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
    @SuppressWarnings("unchecked")
    public List<T> getObjects(final String hql,
            final Map<String, Object> properties)
            throws HibernateDaoSupportException {
        log.debug("hql:{}, properties:{}", hql, properties);

        try {
            return getHibernateTemplate().executeFind(new HibernateCallback() {
                public Object doInHibernate(Session session) {
                    Query query = session.createQuery(hql);
                    if (isCacheQueries) {
                        query.setCacheable(true);
                    } else {
                        isCacheQueries = true;
                        query.setCacheable(false);
                    }
                    if (!Validity.isEmpty(properties)) {
                        query.setProperties(properties);
                    }
                    List<T> list = query.list();
                    return list;
                }
            });
        } catch (DataAccessException e) {
            log.error("Error getObjects:{}", e);
            throw new HibernateDaoSupportException("Error getObjects (#"
                    + this.getClass().getName() + "#):" + e);
        }
    }

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
    public Object getUniqueBeanResult(final String hql,
            final Map<String, Object> properties)
            throws HibernateDaoSupportException {
        log.debug("hql:{}, properties:{}", hql, properties);

        try {
            return getHibernateTemplate().execute(new HibernateCallback() {
                public Object doInHibernate(Session session) {
                    Query query = session.createQuery(hql);
                    if (isCacheQueries) {
                        query.setCacheable(true);
                    } else {
                        isCacheQueries = true;
                        query.setCacheable(false);
                    }
                    if (!Validity.isEmpty(properties)) {
                        query.setProperties(properties);
                    }
                    Object object = query.uniqueResult();
                    return object;
                }
            });
        } catch (DataAccessException e) {
            log.error("Error getUniqueBeanResult:{}", e);
            throw new HibernateDaoSupportException(
                    "Error getUniqueBeanResult (#" + this.getClass().getName()
                            + "#):" + e);
        }
    }

    /**
     * find object with sql and param map
     * 
     * @param sql
     *            according sql
     * @param properties
     *            according param map
     * @return Object which find
     * @throws HibernateDaoSupportException
     *             when accessing and manipulating database happen exception
     */
    public Object getUniqueBeanResultSql(final String sql,
            final Map<String, Object> properties)
            throws HibernateDaoSupportException {
        log.debug("sql:{}, properties:{}", sql, properties);
        try {
            return getHibernateTemplate().execute(new HibernateCallback() {
                public Object doInHibernate(Session session) {
                    Query query = session.createSQLQuery(sql);
                    if (!Validity.isEmpty(properties)) {
                        query.setProperties(properties);
                    }
                    Object object = query.uniqueResult();
                    return object;
                }
            });
        } catch (DataAccessException e) {
            log.error("Error getUniqueBeanResult:{}", e);
            throw new HibernateDaoSupportException(
                    "Error getUniqueBeanResult (#" + this.getClass().getName()
                            + "#):" + e);
        }
    }
    
    /**
     * execute with sql and param arrary
     * 
     * @param sql
     *            according sql
     * @param values
     *            according param arrary
     * @return the count of success record
     * @throws HibernateDaoSupportException
     *             when accessing and manipulating database happen exception
     */
    public int executeSql(final String sql, final Map<String, Object> values)
            throws HibernateDaoSupportException {
        log.debug("hql:{}, params:{}", sql, values);
        try {
            return (Integer) getHibernateTemplate().execute(
                    new HibernateCallback() {
                        public Object doInHibernate(Session session) {
                            Query query = session.createSQLQuery(sql);
                            if (!Validity.isEmpty(values)) {
                                query.setProperties(values);
                            }
                            int i = query.executeUpdate();
                            return i;
                        }
                    });
        } catch (DataAccessException e) {
            log.error("Error executeUpdate:{}", e);
            throw new HibernateDaoSupportException("Error executeUpdate (#"
                    + this.getClass().getName() + "#):" + e);
        }
    }

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
            throws HibernateDaoSupportException {
        return getUniqueBeanResult(hql, new HashMap<String, Object>(0));
    }

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
    public int executeUpdate(final String hql,
            final Map<String, Object> properties)
            throws HibernateDaoSupportException {
        log.debug("hql:{}, properties:{}", hql, properties);

        try {
            return (Integer) getHibernateTemplate().execute(
                    new HibernateCallback() {
                        public Object doInHibernate(Session session) {
                            Query query = session.createQuery(hql);
                            if (!Validity.isEmpty(properties)) {
                                query.setProperties(properties);
                            }
                            int i = query.executeUpdate();
                            return i;
                        }
                    });
        } catch (DataAccessException e) {
            log.error("Error executeUpdate:{}", e);
            throw new HibernateDaoSupportException("Error executeUpdate (#"
                    + this.getClass().getName() + "#):" + e);
        }
    }

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
    public int executeUpdate(final String hql, final Object[] values)
            throws HibernateDaoSupportException {
        String params = converArrayToString(values);
        log.debug("hql:{}, params:{}", hql, params);

        try {
            return (Integer) getHibernateTemplate().execute(
                    new HibernateCallback() {
                        public Object doInHibernate(Session session) {
                            Query query = session.createQuery(hql);
                            setQueryParam(query, values);
                            int i = query.executeUpdate();
                            return i;
                        }
                    });
        } catch (DataAccessException e) {
            log.error("Error executeUpdate:{}", e);
            throw new HibernateDaoSupportException("Error executeUpdate (#"
                    + this.getClass().getName() + "#):" + e);
        }
    }

    /**
     * group array to string like this (a,b,c)
     * 
     * @param params
     *            array param
     * @return String like this (a,b,c)
     */
    protected String groupInParam(String[] params) {
        StringBuffer inParam = new StringBuffer();
        inParam.append(" (");
        for (int i = 0; i < params.length; i++) {
            inParam.append(params[i]);
            if (i != params.length - 1) {
                inParam.append(",");
            } else {
                inParam.append(") ");
            }
        }
        return inParam.toString();
    }

    /**
     * convert special char
     * 
     * @param value
     *            string which need to convert
     * @return converted string
     */
    protected String convertSpecialChar(String value) {
        String s = null;
        if (value != null) {
            s = value.replace("[", "\\\\[").replace("]", "\\\\]").replace("%",
                    "\\\\%").replace("_", "\\\\_").replace("^", "\\\\^")
                    .replace("\\", "").replace("'", "");
        }
        return s;
    }

    /**
     * get count hql with select hql
     * 
     * @param hql
     *            select hql
     * @return count hql
     */
    protected String getCountHql(String hql) {
        if (Validity.isEmpty(hql)) {
            log.error("Error getHqlBean because hql is empty");
            return "";
        }

        return "select count(*) "
                + hql
                        .substring(hql.indexOf("from"))
                        .replace("fetch", "")
                        .replaceAll(
                                "\\s((?mi)(left|right|inner)?\\s+join)\\s+[^\\s]*Set\\b",
                                " ").split("order by")[0];
    }

    /**
     * set param in query
     * 
     * @param query
     *            db query
     * @param values
     *            param value
     */
    private void setQueryParam(Query query, Object[] values) {
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                query.setParameter(i, values[i]);
            }
        }
    }

    /**
     * conver array to string
     * 
     * @param values
     *            array value
     * @return string value
     */
    private String converArrayToString(Object[] values) {
        if (values == null) {
            return "";
        }
        StringBuffer formatValues = new StringBuffer();
        for (Object value : values) {
            formatValues.append("{" + value + "}");
        }
        return formatValues.toString();
    }
    
    public Date getCurrentTime(){
    	String hql = "select current_timestamp() from BaseObject";
    	return null;
    }


    



    

}

package com.wondertek.meeting.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.wondertek.meeting.common.Validity;
import com.wondertek.meeting.dao.MeetingAccessLogDao;
import com.wondertek.meeting.model.MeetingAccessLog;

/**
 * 会议访问记录
 * 
 * @author 金祝华
 */
public class MeetingAccessLogDaoImpl extends BaseDaoImpl<MeetingAccessLog, Long> implements MeetingAccessLogDao {

	protected String getCountHql(String hql) {
		if (Validity.isEmpty(hql)) {
			log.error("Error getHqlBean because hql is empty");
			return "";
		}

		return "select count(1) from (" + hql + ") as temp_t";
	}

	/**
	 * 查询会议访问总统计
	 * 
	 * @param adminUserId
	 * @return
	 */
	public List querySum(final String sql, final Map<String, Object> properties) {
		try {
			return getHibernateTemplate().executeFind(new HibernateCallback() {
				public Object doInHibernate(Session session) {

					Query query = session.createSQLQuery(sql);

					if (!Validity.isEmpty(properties)) {
						query.setProperties(properties);
					}

					return query.list();
				}
			});
		} catch (DataAccessException e) {
			log.error("Error :{}", e);
		}

		return null;
	}
}

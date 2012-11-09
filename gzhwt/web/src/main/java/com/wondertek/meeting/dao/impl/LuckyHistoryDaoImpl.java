package com.wondertek.meeting.dao.impl;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.wondertek.meeting.dao.LuckyHistoryDao;
import com.wondertek.meeting.model.LuckyDraw;
import com.wondertek.meeting.model.LuckyHistory;

/**
* @ClassName: LuckyHistoryDaoImpl 
* @Description: 中奖历史信息
* @author zouxiaoming
* @date Mar 8, 2012 1:44:51 PM 
*
 */
public class LuckyHistoryDaoImpl extends BaseDaoImpl<LuckyHistory, Long> implements LuckyHistoryDao {
	Log log = LogFactory.getLog(this.getClass());

	@Override
	public void deleteLucky(final LuckyDraw lucky) {
		this.getHibernateTemplate().execute(new HibernateCallback<LuckyHistory>() {

			@Override
			public LuckyHistory doInHibernate(Session session) throws HibernateException,
					SQLException {
				String hql="delete from  LuckyHistory lh where lh.lucky.id="+lucky.getId();
				session.createQuery(hql).executeUpdate();
				return null;
			}
		});
		
	}
	
	@Override
	public void clearLuckyHistory(final Long meetingId, final Long luckyId) {
		this.getHibernateTemplate().execute(new HibernateCallback<LuckyHistory>() {

			@Override
			public LuckyHistory doInHibernate(Session session) throws HibernateException,
					SQLException {
				String hql="delete from  LuckyHistory lh where lh.lucky.id="+luckyId+" and lh.meeting.id="+meetingId;
				session.createQuery(hql).executeUpdate();
				return null;
			}
		});
		
	}

	
	
}

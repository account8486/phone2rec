package com.wondertek.meeting.dao.impl;

import org.hibernate.stat.EntityStatistics;
import org.hibernate.stat.Statistics;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.wondertek.meeting.dao.HibernateStatisticsDao;
import com.wondertek.meeting.model.Meeting;

/**
 * 
 * 
 * @author 金祝华
 */
public class HibernateStatisticsDaoImpl extends HibernateDaoSupport implements HibernateStatisticsDao {

	public double queryHitRatio() {
		Statistics stats = this.getHibernateTemplate().getSessionFactory().getStatistics();
		double queryCacheHitCount = stats.getQueryCacheHitCount();
		double queryCacheMissCount = stats.getQueryCacheMissCount();
		double queryCacheHitRatio = queryCacheHitCount / (queryCacheHitCount + queryCacheMissCount);

		// test start
		EntityStatistics entityStats = stats.getEntityStatistics(Meeting.class.getName());
		long changes = entityStats.getInsertCount() + entityStats.getUpdateCount() + entityStats.getDeleteCount();
		System.out.println(Meeting.class.getName() + " changed " + changes + "times");
		// test end

		return queryCacheHitRatio;
	}
}

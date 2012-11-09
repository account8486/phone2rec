package com.wondertek.meeting.action.statistics;

import com.wondertek.meeting.action.base.BaseAction;
import com.wondertek.meeting.dao.HibernateStatisticsDao;

/**
 * monitoring performance
 * 
 * @author 金祝华
 */
public class HibernateStatisticsAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6581213905501857163L;

	private HibernateStatisticsDao hibernateStatisticsDao;

	public String queryHitRatio() {
		double ratio = hibernateStatisticsDao.queryHitRatio();
		log.info("Query Hit ratio:" + ratio);

		return this.json2Resp(ratio);
	}

	public HibernateStatisticsDao getHibernateStatisticsDao() {
		return hibernateStatisticsDao;
	}

	public void setHibernateStatisticsDao(HibernateStatisticsDao hibernateStatisticsDao) {
		this.hibernateStatisticsDao = hibernateStatisticsDao;
	}
}

package com.wondertek.meeting.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sun.tools.hat.internal.server.HistogramQuery;
import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.dao.LuckyHistoryDao;
import com.wondertek.meeting.exception.HibernateDaoSupportException;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.LuckyHistory;
import com.wondertek.meeting.model.User;
import com.wondertek.meeting.service.LuckyHistoryService;
import com.wondertek.meeting.util.StringUtil;

/**
* @ClassName: LuckyHistoryServiceImpl 
* @Description: 中奖历史信息
* @author zouxiaoming
* @date Mar 8, 2012 2:03:41 PM 
*
 */
public class LuckyHistoryServiceImpl extends BaseServiceImpl<LuckyHistory, Long> implements LuckyHistoryService {
	private LuckyHistoryDao luckyHistoryDao;

	@Override
	public Pager<LuckyHistory> findAllLuckyHistory(Long meetingId, int currentPage,
			int pageSize,String mobile) throws ServiceException {
		String hql="from LuckyHistory gt where gt.meeting.id=:meetingId";
		if(mobile!=null&&!StringUtil.isEmpty(mobile)){
			hql=hql+" and  gt.user.mobile like "+"'%"+mobile+"%'";
		}
		hql=hql+" order by gt.id desc";
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("meetingId", meetingId);
		return this.luckyHistoryDao.findPager(hql, currentPage, pageSize, properties);
	}

	/**
	 * @param luckyHistoryDao
	 */
	public void setLuckyHistoryDao(LuckyHistoryDao luckyHistoryDao) {
		this.basicDao=luckyHistoryDao;
		this.luckyHistoryDao = luckyHistoryDao;
	}

	/**
	 * @Description
	 * @return the luckyHistoryDao
	 */
	public LuckyHistoryDao getLuckyHistoryDao() {
		return luckyHistoryDao;
	}

	@Override
	public List<LuckyHistory> findLuckyUserByMeetgingId(Long meetingId) {
		String hql="from LuckyHistory gt where gt.meeting.id="+meetingId;
		try {
			return this.luckyHistoryDao.getObjects(hql);
		} catch (HibernateDaoSupportException e) {
			e.printStackTrace();
		};
		return null;
	}

	@Override
	public void clearLuckyHistory(Long meetingId, Long luckyId) {
		this.luckyHistoryDao.clearLuckyHistory(meetingId,luckyId);
		
	}

}

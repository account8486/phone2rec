package com.wondertek.meeting.service.impl;

import java.util.HashMap;
import java.util.Map;

import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.dao.GuestDao;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.Guest;
import com.wondertek.meeting.service.GuestService;
import com.wondertek.meeting.util.StringUtil;

/**
* @ClassName: GuestServiceImpl 
* @Description: 嘉宾信息管理
* @author zouxiaoming
* @date Mar 6, 2012 1:23:18 PM 
*
 */
public class GuestServiceImpl extends BaseServiceImpl<Guest, Long> implements GuestService {
	private GuestDao guestDao;

	@Override
	public Pager<Guest> findAllGuest(Long meetingId, int currentPage,
			int pageSize,String queryName) throws ServiceException {
		String hql="from Guest gt where gt.meeting.id=:meetingId";
		if(queryName!=null&&!StringUtil.isEmpty(queryName)){
			hql=hql+" and  gt.name like "+"'%"+queryName+"%'";
		}
		hql=hql+" order by gt.id desc";
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("meetingId", meetingId);
		return this.guestDao.findPager(hql, currentPage, pageSize, properties);
	}

	/**
	 * @param guestDao
	 */
	public void setGuestDao(GuestDao guestDao) {
		this.basicDao=guestDao;
		this.guestDao = guestDao;
	}

	/**
	 * @Description
	 * @return the guestDao
	 */
	public GuestDao getGuestDao() {
		return guestDao;
	}
}

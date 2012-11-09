package com.wondertek.meeting.service.impl;

import java.util.List;

import com.wondertek.meeting.dao.LuckyUserDao;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.LuckyUser;
import com.wondertek.meeting.service.LuckyUserService;


/**
* @ClassName: LuckyUserServiceImpl 
* @Description: 可中奖人管理
* @author zouxiaoming
* @date Mar 20, 2012 5:08:41 PM 
*
 */
public class LuckyUserServiceImpl extends BaseServiceImpl<LuckyUser, Long> implements LuckyUserService {
	private LuckyUserDao luckyUserDao;

	/**
	 * @param luckyUserDao
	 */
	public void setLuckyUserDao(LuckyUserDao luckyUserDao) {
		this.basicDao=luckyUserDao;
		this.luckyUserDao = luckyUserDao;
	}

	/**
	 * @Description
	 * @return the luckyUserDao
	 */
	public LuckyUserDao getLuckyUserDao() {
		return luckyUserDao;
	}

	@Override
	public List<LuckyUser> findLuckyUserByLuckyId(Long luckyId) {
		try {
			String hql="from LuckyUser lu where lu.lucky.id="+luckyId+" group by lu.user.id";
			return this.getObjects(hql);
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public List<LuckyUser> findUserByMeetingId(Long meetingId) {
		try {
			String hql="from LuckyUser lu where lu.meeting.id="+meetingId+" group by lu.user.id";
			return this.getObjects(hql);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return null;
	}
}

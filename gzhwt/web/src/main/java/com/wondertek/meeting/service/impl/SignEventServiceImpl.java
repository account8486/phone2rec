package com.wondertek.meeting.service.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.dao.SignEventDao;
import com.wondertek.meeting.dao.UserDao;
import com.wondertek.meeting.dao.rfid.RfidSignInDao;
import com.wondertek.meeting.exception.HibernateDaoSupportException;
import com.wondertek.meeting.model.RfidSignIn;
import com.wondertek.meeting.model.SignEvent;
import com.wondertek.meeting.service.SignEventService;

/**
 * RFID签到事件管理
* @ClassName: SignEventServiceImpl 
* @Description: TODO
* @author zouxiaoming
* @date Aug 9, 2012 1:41:21 PM 
*
 */
public class SignEventServiceImpl extends BaseServiceImpl<SignEvent, Long> implements SignEventService {
	
	private SignEventDao signEventDao;
	
	private UserDao userDao;
	
	private RfidSignInDao rfidSignInDao;

	public void setSignEventDao(SignEventDao signEventDao) {
		this.basicDao=signEventDao;
		this.signEventDao = signEventDao;
	}

	public SignEventDao getSignEventDao() {
		return signEventDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	@Override
	public List<SignEvent> findByMeetingId(Long meetingId) {
		try {
			String hql="from SignEvent se where se.meetId="+meetingId+" order by se.id desc";
			return this.signEventDao.getObjects(hql);
		} catch (HibernateDaoSupportException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<SignEvent> findByMeetingId(Long meetingId, String date) {
		try {
			String hql="from SignEvent se where se.meetId=:meetingId and se.signDate=:signDate " +
					" order by se.signInBeginTime";
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("meetingId", meetingId);
			param.put("signDate", date);
			
			return this.getObjects(hql, param);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean deleteSignEvent(Long id) {
		try {
			Pager<RfidSignIn> paper=this.rfidSignInDao.findByEventId(id, 1, 1);
			if(paper!=null&&paper.getTotal()>0){ //表示有签到记录不能删除
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	public void setRfidSignInDao(RfidSignInDao rfidSignInDao) {
		this.rfidSignInDao = rfidSignInDao;
	}

	public RfidSignInDao getRfidSignInDao() {
		return rfidSignInDao;
	}

	
	
}

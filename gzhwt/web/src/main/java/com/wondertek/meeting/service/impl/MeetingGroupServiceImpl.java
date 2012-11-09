package com.wondertek.meeting.service.impl;

import java.util.HashMap;

import com.wondertek.meeting.dao.MeetingGroupDao;
import com.wondertek.meeting.dao.UserDao;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.MeetingGroup;
import com.wondertek.meeting.service.MeetingGroupService;

/**
 * 
 * @author John Tang
 */
public class MeetingGroupServiceImpl extends BaseServiceImpl<MeetingGroup, Long> implements MeetingGroupService {

	private MeetingGroupDao meetingGroupDao;
	private UserDao userDao;

	public MeetingGroupDao getMeetingGroupDao() {
		return meetingGroupDao;
	}

	public void setMeetingGroupDao(MeetingGroupDao meetingGroupDao) {
		this.basicDao = meetingGroupDao;
		this.meetingGroupDao = meetingGroupDao;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	/**更新meetingGroup信息*/
	public void updateByRelation(Long newPlanId,int type,Long relationId) throws ServiceException{
		String hql = "delete from MeetingGroup where relationId = "+relationId+" and type = "+type;
		log.info(hql);
		meetingGroupDao.executeUpdate(hql, new HashMap());
		MeetingGroup mg = new MeetingGroup();
		mg.setPlanId(newPlanId);
		mg.setRelationId(relationId);
		mg.setType(type);
		meetingGroupDao.add(mg);
	}
}

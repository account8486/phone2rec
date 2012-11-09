package com.wondertek.meeting.dao.impl;

import java.util.HashMap;

import com.wondertek.meeting.dao.UserApplyDao;
import com.wondertek.meeting.exception.HibernateDaoSupportException;
import com.wondertek.meeting.model.UserApply;

public class UserApplyDaoImpl extends BaseDaoImpl<UserApply, Long> implements
		UserApplyDao {

	
	/**
	 * 通过MEETING_ID删除会议下用户申请
	 * @param meetingId
	 * @throws HibernateDaoSupportException
	 */
	public void deleteUserApplyByMeetingId(Long meetingId) throws HibernateDaoSupportException{
		String hql=" delete from UserApply where  meetingId="+meetingId;
		log.info("通过MEETING_ID删除会议下用户申请:"+hql);
		this.executeUpdate(hql, new HashMap());
	}

}

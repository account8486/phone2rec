package com.wondertek.meeting.dao;

import com.wondertek.meeting.exception.HibernateDaoSupportException;
import com.wondertek.meeting.model.UserApply;

public interface UserApplyDao extends BaseDao<UserApply, Long> {

	/**
	 * 通过MEETING_ID删除会议下用户申请
	 * @param meetingId
	 * @throws HibernateDaoSupportException
	 */
	public void deleteUserApplyByMeetingId(Long meetingId) throws HibernateDaoSupportException;
	
}

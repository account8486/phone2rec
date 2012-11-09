package com.wondertek.meeting.dao;

import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.MeetingClientMenu;

public interface MeetingClientMenuDao extends BaseDao<MeetingClientMenu, Long> {
	
	/**
	 * 删除会议下的菜单配置
	 * @param meetingId
	 * @throws ServiceException 
	 */
	public void deleteMeetingClientMenuByMeetingId(Long meetingId) throws ServiceException;
}

package com.wondertek.meeting.service.impl;

import com.wondertek.meeting.dao.MeetingClientMenuDao;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.MeetingClientMenu;
import com.wondertek.meeting.service.MeetingClientMenuService;

public class MeetingClientMenuServiceImpl extends BaseServiceImpl<MeetingClientMenu,Long>
		implements MeetingClientMenuService {
	
	
	MeetingClientMenuDao meetingClientMenuDao;
	/**
	 * 删除会议下的菜单配置
	 * @param meetingId
	 * @throws ServiceException 
	 */
	public void deleteMeetingClientMenuByMeetingId(Long meetingId) throws ServiceException{
		meetingClientMenuDao.deleteMeetingClientMenuByMeetingId(meetingId);
	}
	public MeetingClientMenuDao getMeetingClientMenuDao() {
		return meetingClientMenuDao;
	}
	public void setMeetingClientMenuDao(MeetingClientMenuDao meetingClientMenuDao) {
		this.meetingClientMenuDao = meetingClientMenuDao;
	}

	
}

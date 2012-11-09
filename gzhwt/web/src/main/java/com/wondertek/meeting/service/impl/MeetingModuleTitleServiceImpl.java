package com.wondertek.meeting.service.impl;

import java.util.List;

import com.wondertek.meeting.dao.MeetingModuleTitleDao;
import com.wondertek.meeting.model.MeetingModuleTitle;
import com.wondertek.meeting.service.MeetingModuleTitleService;

/**
 * 用户角色
 * 
 * @author 金祝华
 */
public class MeetingModuleTitleServiceImpl extends BaseServiceImpl<MeetingModuleTitle, Long> implements MeetingModuleTitleService {

	private MeetingModuleTitleDao meetingModuleTitleDao;

	public MeetingModuleTitleDao getMeetingModuleTitleDao() {
		return meetingModuleTitleDao;
	}

	public void setMeetingModuleTitleDao(MeetingModuleTitleDao meetingModuleTitleDao) {
		this.basicDao = meetingModuleTitleDao;
		this.meetingModuleTitleDao = meetingModuleTitleDao;
	}

	@Override
	public List<MeetingModuleTitle> query(MeetingModuleTitle meetingModuleTitle) {
		return meetingModuleTitleDao.query(meetingModuleTitle);
	}

}

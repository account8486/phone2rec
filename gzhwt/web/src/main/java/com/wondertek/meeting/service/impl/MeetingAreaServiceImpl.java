package com.wondertek.meeting.service.impl;

import com.wondertek.meeting.dao.MeetingAreaDao;
import com.wondertek.meeting.model.MeetingArea;
import com.wondertek.meeting.service.MeetingAreaService;

/**
 * 会议地点
 * 
 * @author 金祝华
 */
public class MeetingAreaServiceImpl extends BaseServiceImpl<MeetingArea, String> implements MeetingAreaService {

	private MeetingAreaDao meetingAreaDao;

	public MeetingAreaDao getMeetingAreaDao() {
		return meetingAreaDao;
	}

	public void setMeetingAreaDao(MeetingAreaDao meetingAreaDao) {
		this.basicDao = meetingAreaDao;
		this.meetingAreaDao = meetingAreaDao;
	}
}

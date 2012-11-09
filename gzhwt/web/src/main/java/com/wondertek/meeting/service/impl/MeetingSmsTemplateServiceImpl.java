package com.wondertek.meeting.service.impl;

import com.wondertek.meeting.dao.MeetingSmsTemplateDao;
import com.wondertek.meeting.model.MeetingSmsTemplate;
import com.wondertek.meeting.service.MeetingSmsTemplateService;

public class MeetingSmsTemplateServiceImpl extends BaseServiceImpl<MeetingSmsTemplate, Long>
		implements MeetingSmsTemplateService {

	MeetingSmsTemplateDao meetingSmsTemplateDao;

	/**
	 * @param meetingSmsTemplateDao the meetingSmsTemplateDao to set
	 */
	public void setMeetingSmsTemplateDao(MeetingSmsTemplateDao meetingSmsTemplateDao) {
		this.meetingSmsTemplateDao = meetingSmsTemplateDao;
		super.setBaseDao(meetingSmsTemplateDao);
	}
}

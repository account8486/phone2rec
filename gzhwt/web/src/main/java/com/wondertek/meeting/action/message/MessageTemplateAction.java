package com.wondertek.meeting.action.message;

import java.util.List;

import com.wondertek.meeting.action.base.BaseAction;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.MeetingSmsTemplate;
import com.wondertek.meeting.service.MeetingSmsTemplateService;

public class MessageTemplateAction extends BaseAction {
	
	private static final long serialVersionUID = 6480909399795526873L;
	MeetingSmsTemplateService meetingSmsTemplateService;
	
	public String listTemplate(){
		try {
			final List<MeetingSmsTemplate> templates = meetingSmsTemplateService.findAll();
			getRequest().setAttribute("templates", templates);
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ERROR;
		}
		
		return SUCCESS;
	}

	/**
	 * @param meetingSmsTemplateService the meetingSmsTemplateService to set
	 */
	public void setMeetingSmsTemplateService(MeetingSmsTemplateService meetingSmsTemplateService) {
		this.meetingSmsTemplateService = meetingSmsTemplateService;
	}
}

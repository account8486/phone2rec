package com.wondertek.meeting.service;

import java.util.List;

import com.wondertek.meeting.model.MeetingMember;
import com.wondertek.meeting.model.MeetingSeat;
import com.wondertek.meeting.model.MeetingSeatTemplate;

public interface ManagerSeatService extends BaseService<MeetingSeat, Integer> {
	public List<MeetingSeat> getManageSeatByMeetingId(int meetingId);
	
	public List<MeetingMember> getMeetingMemberByMeetingId(int meetingId);	
	
	public boolean submitMeetingSeat(List<MeetingSeat> meetingSeats);	
	
	public List<MeetingSeatTemplate> getMeetingTemplate();	
	
	public List<MeetingSeatTemplate> getMeetingTemplateByName(String name);
	
	public boolean saveTemplate(List<MeetingSeatTemplate> meetingSeatTemplates);
}

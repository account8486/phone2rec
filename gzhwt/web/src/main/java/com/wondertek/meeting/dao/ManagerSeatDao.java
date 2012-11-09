package com.wondertek.meeting.dao;

import java.util.List;

import com.wondertek.meeting.model.MeetingSeat;
import com.wondertek.meeting.model.MeetingSeatImage;
import com.wondertek.meeting.model.MeetingSeatTemplate;

public interface ManagerSeatDao extends BaseDao<MeetingSeat, Integer> {
	public List<MeetingSeatTemplate> getMeetingTemplate();

	public List<MeetingSeatTemplate> getMeetingTemplateByName(String name);

	public void saveSeatTemplate(MeetingSeatTemplate seatTemplate);

	public void saveSeatImage(MeetingSeatImage msi);
}

package com.wondertek.meeting.dao;

import java.util.List;

import com.wondertek.meeting.model.MeetingAgenda;

public interface MeetingAgendaDao extends BaseDao<MeetingAgenda, Long> {
	List<MeetingAgenda> queryListByMeetingId(final Long meetingId);

	void deleteAgendaByMeetingId(final Long meetingId);
}

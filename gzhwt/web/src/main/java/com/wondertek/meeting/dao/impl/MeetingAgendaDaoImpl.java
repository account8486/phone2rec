package com.wondertek.meeting.dao.impl;

import java.util.List;

import com.wondertek.meeting.dao.MeetingAgendaDao;
import com.wondertek.meeting.model.MeetingAgenda;

/**
 * 会议议程数据操作类
 * 
 * @author tangjun
 */
public class MeetingAgendaDaoImpl extends BaseDaoImpl<MeetingAgenda, Long> implements MeetingAgendaDao {

	@SuppressWarnings("unchecked")
	public List<MeetingAgenda> queryListByMeetingId(final Long meetingId) {
		final String sql = "from MeetingAgenda where meetingId=? order by date, startTime, endTime";
		return getHibernateTemplate().find(sql, meetingId);
	}

	public void deleteAgendaByMeetingId(final Long meetingId) {
		String sql = "delete from meeting_agenda where meeting_id = :meetingId";
		getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql)
				.setLong("meetingId", meetingId).executeUpdate();
	}
}
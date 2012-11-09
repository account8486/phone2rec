package com.wondertek.meeting.service;

import java.util.List;

import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.exception.HibernateDaoSupportException;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.MeetingAgenda;

/**
 * 
 * @author tangjun
 */
public interface MeetingAgendaService extends BaseService<MeetingAgenda, Long> {

	List<MeetingAgenda> queryListByMeetingId(final Long meetingId, final Long userId);

	Pager<MeetingAgenda> queryPagerByMeetingId(final MeetingAgenda meetingAgenda, int currentPage, int pageSize)
			throws ServiceException;

	List<MeetingAgenda> queryListByDate(final Long meetingId, final String dateStr) throws ServiceException;

	void saveList(final List<MeetingAgenda> entities) throws ServiceException;

	public void deleteAgendaById(MeetingAgenda agenda) throws ServiceException;
	
	/**
	 * 通过meetingID来获取最新时间的会议
	 * 来进行下一个议程添加的时间依据
	 * @param meetingId
	 */
	public List<MeetingAgenda> getNewestAgenda(Long meetingId) throws HibernateDaoSupportException;
}

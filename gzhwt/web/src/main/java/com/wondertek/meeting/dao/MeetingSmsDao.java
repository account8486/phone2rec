/**
 * 
 */
package com.wondertek.meeting.dao;

import java.util.List;

import com.wondertek.meeting.exception.HibernateDaoSupportException;
import com.wondertek.meeting.model.MeetingSms;

/**
 * @author rain
 * 
 */
public interface MeetingSmsDao extends BaseDao<MeetingSms, Long> {
	/**
	 * 通过HQL语句获取meetingSmsLst
	 * 
	 * @param meetingId
	 * @return
	 */
	public List<MeetingSms> getMeetingSms(String queryString);

	List<MeetingSms> queryScheduledSmsList();

	void updateRecipientState(final Long id, final Long userId, final Integer state);
	void updateSmsState(final Long id, final Integer state);
	
	/**
	 * 通过会议ID删除会议下短信
	 * @param meetingId
	 * @throws HibernateDaoSupportException 
	 */
	public void deleteMeetingSmsByMeetingId(Long meetingId) throws HibernateDaoSupportException;
}

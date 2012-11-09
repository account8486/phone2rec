/**
 * 
 */
package com.wondertek.meeting.service;

import java.util.List;

import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.exception.HibernateDaoSupportException;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.MeetingSms;

/**
 * @author rain
 * 
 */
public interface MeetingSmsService extends BaseService<MeetingSms, Long> {
	/**
	 * 通过meetingId来获取列表
	 * 
	 * @param meetingId
	 * @return
	 */
	List<MeetingSms> getMeetingSmsList(Long meetingId);
	
	List<MeetingSms> queryScheduledSmsList();
	
	void updateRecipientState(final Long id, final Long userId, final Integer state);
	
	void updateSmsState(final Long id, final Integer state);

	/**
	 * 获取分页信息
	 * @param currentPage
	 * @param pageSize
	 * @param meetingId
	 * @param messages
	 * @param mobile
	 * @return
	 */
	public Pager<MeetingSms> getMeetingSmsPager(int currentPage, int pageSize, Long meetingId,String messages,String mobile,String state);

	void deleteByMeetingId(final Long meetingId) throws ServiceException;
	
	public  Pager<Object> statMeetingSmsPagers(String treeId, int currentPage, int pageSize,String meetingName) throws HibernateDaoSupportException;

}

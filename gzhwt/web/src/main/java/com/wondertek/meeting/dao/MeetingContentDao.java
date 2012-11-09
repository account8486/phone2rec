/**
 * 
 */
package com.wondertek.meeting.dao;

import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.exception.HibernateDaoSupportException;
import com.wondertek.meeting.model.MeetingContent;

/**
 * @author GuoXu
 * 会议内容DAO层
 */
public interface MeetingContentDao extends BaseDao<MeetingContent, Long> {
	
	public Pager<MeetingContent> findMeetingContentPager(Long meetingId, int type,
			int currentPage, int pageSize,String title) throws HibernateDaoSupportException;
}

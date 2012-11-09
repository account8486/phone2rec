/**
 * 
 */
package com.wondertek.meeting.service;

import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.model.MessageStatistics;

/**
 * @author rain
 *
 */
public interface MeetingStatisticsService  extends BaseService<MessageStatistics, Long> {
	public Pager<MessageStatistics> queryInteraction(final int currentPage, final int pageSize, final String treeId);
	public Pager<MessageStatistics> queryInteractionByMeetingId(final int currentPage, final int pageSize, final Long meetingId);
}

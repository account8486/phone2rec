package com.wondertek.meeting.service;

import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.Journey;

/**
 * @author John Tang
 */
public interface JourneyService extends BaseService<Journey, Long> {
	
	public Pager<Journey> queryPagerByMeetingId(Long meetingId, int currentPage, int pageSize) throws ServiceException;
}

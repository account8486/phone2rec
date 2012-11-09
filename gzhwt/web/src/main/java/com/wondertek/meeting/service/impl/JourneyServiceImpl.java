package com.wondertek.meeting.service.impl;

import java.util.HashMap;
import java.util.Map;

import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.dao.JourneyDao;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.Journey;
import com.wondertek.meeting.service.JourneyService;

/**
 * 
 * @author John Tang
 */
public class JourneyServiceImpl extends BaseServiceImpl<Journey, Long> implements JourneyService {

	private JourneyDao journeyDao;

	public JourneyDao getJourneyDao() {
		return journeyDao;
	}

	public void setJourneyDao(JourneyDao journeyDao) {
		this.basicDao = journeyDao;
		this.journeyDao = journeyDao;
	}

	public Pager<Journey> queryPagerByMeetingId(Long meetingId,
			int currentPage, int pageSize) throws ServiceException {
		final Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("meetingId", meetingId);
		final String sql = "from Journey where meetingId = :meetingId ";
		final Pager<Journey> pager = journeyDao.findPager(sql, currentPage, pageSize, properties);
		return pager;
	}
	
	
}

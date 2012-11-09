package com.wondertek.meeting.service;

import java.util.List;

import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.Vehicle;

/**
 * @author John Tang
 */
public interface VehicleService extends BaseService<Vehicle, Long> {
	
	public Pager<Vehicle> queryPagerByMeetingId(Long meetingId, int currentPage, int pageSize) throws ServiceException;
	public List<Vehicle> listByMeetingId(Long meetingId) throws ServiceException;
	
}

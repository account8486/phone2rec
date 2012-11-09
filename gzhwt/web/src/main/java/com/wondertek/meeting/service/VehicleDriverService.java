package com.wondertek.meeting.service;

import java.util.List;

import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.VehicleDriver;

/**
 * @author John Tang
 */
public interface VehicleDriverService extends BaseService<VehicleDriver, Long> {
	public Pager<VehicleDriver> queryPagerByMeetingId(Long meetingId, int currentPage, int pageSize) throws ServiceException;
	public List<VehicleDriver> listByMeetingId(Long meetingId) throws ServiceException;
}

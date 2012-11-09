package com.wondertek.meeting.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.dao.VehicleDao;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.Vehicle;
import com.wondertek.meeting.service.VehicleService;

/**
 * 
 * @author John Tang
 */
public class VehicleServiceImpl extends BaseServiceImpl<Vehicle, Long> implements VehicleService {

	private VehicleDao vehicleDao;

	public VehicleDao getVehicleDao() {
		return vehicleDao;
	}

	public void setVehicleDao(VehicleDao vehicleDao) {
		this.basicDao = vehicleDao;
		this.vehicleDao = vehicleDao;
	}

	public Pager<Vehicle> queryPagerByMeetingId(Long meetingId,
			int currentPage, int pageSize) throws ServiceException {
		final Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("meetingId", meetingId);
		final String hql = "from Vehicle where meetingId = :meetingId ";
		final Pager<Vehicle> pager = vehicleDao.findPager(hql, currentPage, pageSize, properties);
		return pager;
	}

	public List<Vehicle> listByMeetingId(Long meetingId)
			throws ServiceException {
		final Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("meetingId", meetingId);
		final String hql = "from Vehicle where meetingId = :meetingId ";
		final List<Vehicle> result = vehicleDao.getObjects(hql, properties);
		return result;
	}
	
	
}

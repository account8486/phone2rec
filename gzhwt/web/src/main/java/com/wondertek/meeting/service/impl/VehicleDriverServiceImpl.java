package com.wondertek.meeting.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.dao.VehicleDriverDao;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.VehicleDriver;
import com.wondertek.meeting.service.VehicleDriverService;

/**
 * 
 * @author John Tang
 */
public class VehicleDriverServiceImpl extends BaseServiceImpl<VehicleDriver, Long> implements VehicleDriverService {

	private VehicleDriverDao vehicleDriverDao;

	public VehicleDriverDao getVehicleDriverDao() {
		return vehicleDriverDao;
	}

	public void setVehicleDriverDao(VehicleDriverDao vehicleDriverDao) {
		this.basicDao = vehicleDriverDao;
		this.vehicleDriverDao = vehicleDriverDao;
	}

	public Pager<VehicleDriver> queryPagerByMeetingId(Long meetingId,
			int currentPage, int pageSize) throws ServiceException {
		final Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("meetingId", meetingId);
		final String sql = "from VehicleDriver where meetingId = :meetingId ";
		final Pager<VehicleDriver> pager = vehicleDriverDao.findPager(sql, currentPage, pageSize, properties);
		return pager;
	}

	public List<VehicleDriver> listByMeetingId(Long meetingId)
			throws ServiceException {
		final Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("meetingId", meetingId);
		final String sql = "from VehicleDriver where meetingId = :meetingId ";
		final List<VehicleDriver> result = vehicleDriverDao.getObjects(sql, properties);
		return result;
	}
	
	
}

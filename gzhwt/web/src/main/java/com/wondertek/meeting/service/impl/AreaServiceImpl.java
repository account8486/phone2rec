package com.wondertek.meeting.service.impl;

import com.wondertek.meeting.dao.AreaDao;
import com.wondertek.meeting.model.Area;
import com.wondertek.meeting.service.AreaService;

/**
 * 用户角色
 * 
 * @author 金祝华
 */
public class AreaServiceImpl extends BaseServiceImpl<Area, Long> implements AreaService {

	private AreaDao areaDao;

	public AreaDao getAreaDao() {
		return areaDao;
	}

	public void setAreaDao(AreaDao areaDao) {
		this.basicDao = areaDao;
		this.areaDao = areaDao;
	}
}

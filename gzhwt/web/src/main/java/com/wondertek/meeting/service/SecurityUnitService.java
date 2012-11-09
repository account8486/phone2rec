package com.wondertek.meeting.service;

import java.util.List;

import com.wondertek.meeting.exception.HibernateDaoSupportException;
import com.wondertek.meeting.model.security.SecurityUnit;
import com.wondertek.meeting.vo.SecurityUnitView;

public interface SecurityUnitService extends BaseService<SecurityUnit, Long> {

	public List<SecurityUnitView> getCascadeUnitOfMenu()
			throws HibernateDaoSupportException;

	public List<SecurityUnitView> getMenuUnitsByRoleId(Integer roleId,String meetingId,String ctx)
			throws HibernateDaoSupportException;

}

package com.guo.yf.service;

import java.util.List;

import com.guo.yf.model.security.SecurityUnit;
import com.guo.yf.vo.SecurityUnitView;
import com.wondertek.meeting.exception.HibernateDaoSupportException;
import com.wondertek.meeting.service.BaseService;

public interface SecurityUnitService extends BaseService<SecurityUnit, Long> {

	public List<SecurityUnitView> getCascadeUnitOfMenu()
			throws HibernateDaoSupportException;

	public List<SecurityUnitView> getMenuUnitsByRoleId(Integer roleId,String meetingId,String ctx)
			throws HibernateDaoSupportException;

}

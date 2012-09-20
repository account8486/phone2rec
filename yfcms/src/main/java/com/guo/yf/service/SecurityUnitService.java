package com.guo.yf.service;

import java.util.List;

import com.guo.yf.model.security.SecurityUnit;
import com.guo.yf.vo.SecurityUnitView;
import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.exception.HibernateDaoSupportException;
import com.wondertek.meeting.service.BaseService;

public interface SecurityUnitService extends BaseService<SecurityUnit, Long> {

	/**
	 * 获取基础菜单列表，带分页。
	 * 
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public Pager<SecurityUnit> getSecurityUnitPager(int currentPage,
			int pageSize, String unitName, String unitType,String unitParentId);
	
	/**
	 * 获取基础菜单中的一级菜单
	 * @return
	 */
	public List<SecurityUnit> getParentUnitList();

	public List<SecurityUnitView> getCascadeUnitOfMenu()
			throws HibernateDaoSupportException;

	public List<SecurityUnitView> getMenuUnitsByRoleId(Integer roleId,
			String meetingId, String ctx) throws HibernateDaoSupportException;

}

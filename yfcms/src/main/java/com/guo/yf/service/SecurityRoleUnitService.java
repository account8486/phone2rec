package com.guo.yf.service;

import java.util.List;

import com.guo.yf.model.security.SecurityRoleUnit;
import com.wondertek.meeting.exception.HibernateDaoSupportException;
import com.wondertek.meeting.service.BaseService;

public interface SecurityRoleUnitService extends
		BaseService<SecurityRoleUnit, Long> {

	public List<SecurityRoleUnit> getRoleUnitsByRoleId(Long roleId)
			throws HibernateDaoSupportException;

	/**
	 * 通过组合键来查询当前数据
	 * 
	 * @param roleId
	 * @param unitId
	 * @return
	 * @throws HibernateDaoSupportException
	 */
	public SecurityRoleUnit getRoleUnitByUniqueKey(Long roleId, Long unitId)
			throws HibernateDaoSupportException;

	/**
	 * 通过角色来删除对应的菜单选项
	 * 
	 * @param roleId
	 * @throws HibernateDaoSupportException
	 */
	public void deleteRoleUnitsByRoleId(Long roleId)
			throws HibernateDaoSupportException;

}

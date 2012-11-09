package com.wondertek.meeting.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wondertek.meeting.dao.SecurityRoleUnitDao;
import com.wondertek.meeting.exception.HibernateDaoSupportException;
import com.wondertek.meeting.model.security.SecurityRoleUnit;
import com.wondertek.meeting.service.SecurityRoleUnitService;

public class SecurityRoleUnitServiceImpl extends BaseServiceImpl<SecurityRoleUnit, Long>
 implements
		SecurityRoleUnitService {

	SecurityRoleUnitDao securityRoleUnitDao;

	public SecurityRoleUnitDao getSecurityRoleUnitDao() {
		return securityRoleUnitDao;
	}

	public void setSecurityRoleUnitDao(SecurityRoleUnitDao securityRoleUnitDao) {
		this.securityRoleUnitDao = securityRoleUnitDao;
		this.basicDao = securityRoleUnitDao;
	}

	public List<SecurityRoleUnit> getRoleUnitsByRoleId(Long roleId)
			throws HibernateDaoSupportException {

		StringBuilder sb = new StringBuilder();
		sb.append("  select roleUnit from SecurityRoleUnit roleUnit where roleUnit.roleId=:roleId");
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("roleId", roleId);

		List<SecurityRoleUnit> roleUnitLst = securityRoleUnitDao.getObjects(
				sb.toString(), properties);

		return roleUnitLst;
	}

	/**
	 * 通过角色来删除对应的菜单选项
	 * @param roleId
	 * @throws HibernateDaoSupportException
	 */
	public void deleteRoleUnitsByRoleId(Long roleId)
			throws HibernateDaoSupportException {
		StringBuilder sb = new StringBuilder();
		sb.append(" delete from SecurityRoleUnit roleUnit where roleUnit.roleId=:roleId");
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("roleId", roleId);
		securityRoleUnitDao.executeUpdate(sb.toString(), properties);
	}

	/**
	 * 通过组合键来查询当前数据
	 * 
	 * @param roleId
	 * @param unitId
	 * @return
	 * @throws HibernateDaoSupportException
	 */
	public SecurityRoleUnit getRoleUnitByUniqueKey(Long roleId, Long unitId)
			throws HibernateDaoSupportException {
		StringBuilder sb = new StringBuilder();
		sb.append("  select roleUnit from SecurityRoleUnit roleUnit where roleUnit.roleId=:roleId and roleUnit.unitId=:unitId");
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("roleId", roleId);
		properties.put("unitId", unitId);
		List<SecurityRoleUnit> roleUnitLst = securityRoleUnitDao.getObjects(
				sb.toString(), properties);
		if (roleUnitLst != null && roleUnitLst.size() > 0) {
			return roleUnitLst.get(0);
		}
		return null;
	}

}

package com.wondertek.meeting.service.impl;

import com.wondertek.meeting.dao.RoleDao;
import com.wondertek.meeting.model.Role;
import com.wondertek.meeting.service.RoleService;

/**
 * 角色
 * 
 * @author 金祝华
 */
public class RoleServiceImpl extends BaseServiceImpl<Role, Long> implements RoleService {

	private RoleDao roleDao;

	public RoleDao getRoleDao() {
		return roleDao;
	}

	public void setRoleDao(RoleDao roleDao) {
		this.basicDao = roleDao;
		this.roleDao = roleDao;
	}
}

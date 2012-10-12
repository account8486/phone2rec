package com.wondertek.meeting.service.impl;

import com.wondertek.meeting.dao.AdminRoleDao;
import com.wondertek.meeting.model.AdminRole;
import com.wondertek.meeting.service.AdminRoleService;

/**
 * 角色
 * 
 */
public class AdminRoleServiceImpl extends BaseServiceImpl<AdminRole, Long> implements AdminRoleService {

	private AdminRoleDao adminRoleDao;

	public AdminRoleDao getAdminRoleDao() {
		return adminRoleDao;
	}

	public void setAdminRoleDao(AdminRoleDao adminRoleDao) {
		this.basicDao = adminRoleDao;
		this.adminRoleDao = adminRoleDao;
	}
}

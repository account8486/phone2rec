package com.wondertek.meeting.service.impl;

import com.wondertek.meeting.dao.UserRoleDao;
import com.wondertek.meeting.model.UserRole;
import com.wondertek.meeting.service.UserRoleService;

/**
 * 用户角色
 * 
 * @author 金祝华
 */
public class UserRoleServiceImpl extends BaseServiceImpl<UserRole, Long> implements UserRoleService {

	private UserRoleDao userRoleDao;

	public UserRoleDao getUserRoleDao() {
		return userRoleDao;
	}

	public void setUserRoleDao(UserRoleDao userRoleDao) {
		this.basicDao = userRoleDao;
		this.userRoleDao = userRoleDao;
	}
}

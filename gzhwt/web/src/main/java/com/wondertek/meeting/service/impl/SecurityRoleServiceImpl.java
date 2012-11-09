package com.wondertek.meeting.service.impl;

import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.dao.SecurityRoleDao;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.security.SecurityRole;
import com.wondertek.meeting.service.SecurityRoleService;

public class SecurityRoleServiceImpl extends BaseServiceImpl<SecurityRole, Long> implements
		SecurityRoleService {
	
	SecurityRoleDao securityRoleDao;

	public SecurityRoleDao getSecurityRoleDao() {
		return securityRoleDao;
	}

	public void setSecurityRoleDao(SecurityRoleDao securityRoleDao) {
		this.basicDao=securityRoleDao;
		this.securityRoleDao = securityRoleDao;
	}
	
	
	
	public Pager<SecurityRole> getRolsPager(int currentPage, int pageSize){
		Pager<SecurityRole> pagerRoles=null;
		StringBuilder sb=new StringBuilder();
		sb.append("  select role from SecurityRole role ");
		
		try {
			pagerRoles=securityRoleDao.findPager(sb.toString(), currentPage, pageSize, null);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		
		return pagerRoles;
	}
	
	
	
}

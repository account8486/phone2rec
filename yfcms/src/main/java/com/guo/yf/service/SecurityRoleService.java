package com.guo.yf.service;

import com.guo.yf.model.security.SecurityRole;
import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.service.BaseService;

public interface SecurityRoleService extends BaseService<SecurityRole, Long> {
	
	public Pager<SecurityRole> getRolsPager(int currentPage, int pageSize);
	

	
}

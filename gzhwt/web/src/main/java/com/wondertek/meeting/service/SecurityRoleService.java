package com.wondertek.meeting.service;

import java.util.List;

import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.exception.HibernateDaoSupportException;
import com.wondertek.meeting.model.security.SecurityRole;
import com.wondertek.meeting.vo.SecurityUnitView;

public interface SecurityRoleService extends BaseService<SecurityRole, Long> {
	
	public Pager<SecurityRole> getRolsPager(int currentPage, int pageSize);
	

	
}

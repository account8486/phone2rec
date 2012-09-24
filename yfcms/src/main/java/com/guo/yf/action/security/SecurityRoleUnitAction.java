package com.guo.yf.action.security;

import java.util.List;

import com.guo.yf.service.SecurityUnitService;
import com.guo.yf.vo.SecurityUnitView;
import com.wondertek.meeting.action.base.BaseAction;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.AdminUser;
import com.wondertek.meeting.service.AdminUserService;

public class SecurityRoleUnitAction extends BaseAction {

	private static final long serialVersionUID = -8114515189457116178L;
	AdminUserService adminUserService;
	SecurityUnitService securityUnitService;
	
	public AdminUserService getAdminUserService() {
		return adminUserService;
	}
	public void setAdminUserService(AdminUserService adminUserService) {
		this.adminUserService = adminUserService;
	}
	public SecurityUnitService getSecurityUnitService() {
		return securityUnitService;
	}
	public void setSecurityUnitService(SecurityUnitService securityUnitService) {
		this.securityUnitService = securityUnitService;
	}
	/**
	 * 直接跳转到页面
	 * @return
	 * @throws ServiceException 
	 */
	public String toManagerMenu() throws ServiceException {
		String meetingId = this.getParameter("meetingId");
		this.getRequest().setAttribute("meetingId", meetingId);
		String ctx=this.getRequest().getContextPath();
		// TODO
		AdminUser adminUser =adminUserService.findById(this.getAdminUserIdFromSession());
		List<SecurityUnitView> lstSecurityUnitView = securityUnitService
				.getMenuUnitsByRoleId(adminUser.getRoleId(), meetingId,ctx);
		
		getRequest().setAttribute("unitViewList", lstSecurityUnitView);
		return SUCCESS;
	}
	

}

package com.wondertek.meeting.action.login;

import java.util.Random;

import com.wondertek.meeting.action.base.BaseAction;
import com.wondertek.meeting.common.SessionKeeper;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.AdminUser;
import com.wondertek.meeting.service.AdminUserService;

/**
 * 管理后台登录
 * 
 * @author 金祝华
 */
public class AdminLoginAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3102151360541524276L;

	private AdminUserService adminUserService;

	/**
	 * 管理后台登录
	 * 
	 * @return
	 */
	public String login() {
		String checkCode = this.getParameter("checkCode");// 校验码

		// 确认校验码
		String sessionCode = this.getStringFromSession(SessionKeeper.CheckCode);
		if (checkCode != null && !checkCode.equals(sessionCode)) {
			resultMap.put("result", false);
			resultMap.put("message", getText("user.login.checkCode.error"));
			return SUCCESS;
		}

		String mobile = this.getParameter("mobile");// 用户名
		String passwd = this.getParameter("password");// 密码
		AdminUser user = new AdminUser();
		user.setMobile(mobile);
		user.setPassword(passwd);

		// 登录
		AdminUser loginUser = null;
		try {
			loginUser = adminUserService.adminLogin(user, this.getLoginIP());
		} catch (ServiceException e) {
			String errCode = e.getMessage();
			log.debug("user login failed!errCode:" + errCode);
			resultMap.put("result", false);
			resultMap.put("message", getText(errCode));
			return SUCCESS;
		}

		// 将用户信息存入session
		this.putToSession(SessionKeeper.SESSION_ADMIN_USER, loginUser);

		// 修改验证码
		this.putToSession(SessionKeeper.CheckCode, new Random().nextInt(9999));

		resultMap.put("result", true);
		resultMap.put("message", getText("user.login.success"));
		return SUCCESS;
	}
	
	
	public String ssologin() {
		
		String mobile = this.getParameter("mobile");// 用户名
		String passwd = this.getParameter("password");// 密码
		AdminUser user = new AdminUser();
		user.setMobile(mobile);
		user.setPassword(passwd);

		// 登录
		AdminUser loginUser = null;
		try {
			loginUser = adminUserService.adminLogin(user, this.getLoginIP());
		} catch (ServiceException e) {
			String errCode = e.getMessage();
			log.debug("user login failed!errCode:" + errCode);
			return INPUT;
		}

		// 将用户信息存入session
		this.putToSession(SessionKeeper.SESSION_ADMIN_USER, loginUser);
		
		// 修改验证码
		this.putToSession(SessionKeeper.CheckCode, new Random().nextInt(9999));
		log.debug(getText("user.login.success"));
		
		return SUCCESS;
	}

	/**
	 * 后台注销
	 * 
	 * @return
	 */
	public String logout() {
		// AdminUser user = (AdminUser)
		// this.getSession().getAttribute(SessionKeeper.SESSION_ADMIN_USER);

		getSession().removeAttribute(SessionKeeper.SESSION_ADMIN_USER);

		return SUCCESS;
	}

	public AdminUserService getAdminUserService() {
		return adminUserService;
	}

	public void setAdminUserService(AdminUserService adminUserService) {
		this.adminUserService = adminUserService;
	}
}

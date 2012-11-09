package com.wondertek.meeting.action.login;

import com.wondertek.meeting.action.base.BaseAction;
import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.User;
import com.wondertek.meeting.model.UserLoginLog;
import com.wondertek.meeting.service.UserLoginLogService;

/**
 * 登录记录
 * 
 * @author 金祝华
 */
public class UserLoginLogAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1563459973958221846L;

	private UserLoginLogService userLoginLogService;

	private UserLoginLog loginLog;
	private User user;

	/**
	 * 查询列表
	 * 
	 * @return
	 */
	public String list() {

		if (loginLog == null) {
			loginLog = new UserLoginLog();
		}

		if (user == null) {
			user = new User();
		}

		Pager<UserLoginLog> userLoginLogPager = null;
		try {
			userLoginLogPager = userLoginLogService.findPager(loginLog, user, currentPage, pageSize);
		} catch (ServiceException e) {
			log.error("query userLoginLog list error: " + e.toString());
		}

		this.getRequest().setAttribute("pager", userLoginLogPager);

		return SUCCESS;
	}

	public UserLoginLogService getUserLoginLogService() {
		return userLoginLogService;
	}

	public void setUserLoginLogService(UserLoginLogService userLoginLogService) {
		this.userLoginLogService = userLoginLogService;
	}

	public UserLoginLog getLoginLog() {
		return loginLog;
	}

	public void setLoginLog(UserLoginLog loginLog) {
		this.loginLog = loginLog;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}

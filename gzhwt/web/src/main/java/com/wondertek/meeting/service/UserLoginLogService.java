package com.wondertek.meeting.service;

import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.User;
import com.wondertek.meeting.model.UserLoginLog;

/**
 * 用户登录记录
 * 
 * @author 金祝华
 */
public interface UserLoginLogService extends BaseService<UserLoginLog, Integer> {

	/**
	 * 查询登录记录列表
	 * 
	 * @param userLoginLog
	 * @param user
	 * @param currentPage
	 * @param pageSize
	 * @return
	 * @throws ServiceException
	 */
	public Pager<UserLoginLog> findPager(UserLoginLog userLoginLog, User user, int currentPage, int pageSize)
			throws ServiceException;
}

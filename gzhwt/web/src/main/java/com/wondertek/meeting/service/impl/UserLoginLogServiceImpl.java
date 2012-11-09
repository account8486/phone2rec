package com.wondertek.meeting.service.impl;

import java.util.HashMap;
import java.util.Map;

import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.dao.UserLoginLogDao;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.User;
import com.wondertek.meeting.model.UserLoginLog;
import com.wondertek.meeting.service.UserLoginLogService;
import com.wondertek.meeting.util.StringUtil;

/**
 * 用户登录记录
 * 
 * @author 金祝华
 */
public class UserLoginLogServiceImpl extends BaseServiceImpl<UserLoginLog, Integer> implements UserLoginLogService {

	private UserLoginLogDao userLoginLogDao;

	public UserLoginLogDao getUserLoginLogDao() {
		return userLoginLogDao;
	}

	public void setUserLoginLogDao(UserLoginLogDao userLoginLogDao) {
		this.basicDao = userLoginLogDao;
		this.userLoginLogDao = userLoginLogDao;
	}

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
			throws ServiceException {

		// 下级组织所有用户、本级低角色用户、自己
		String hql = "from UserLoginLog l left join fetch l.user u where u.mobile like '%'||:mobile||'%' "
				+ " and u.name like '%'||:name||'%' order by l.id desc";

		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("mobile", StringUtil.null2Str(user.getMobile()));
		properties.put("name", StringUtil.null2Str(user.getName()));

		try {
			return userLoginLogDao.findPager(hql, currentPage, pageSize, properties);
		} catch (Exception e) {
			final String errMsg = "Find userloginlog pager ERROR!";
			log.error(errMsg, e);
			throw new ServiceException(errMsg, e);
		}
	}
}

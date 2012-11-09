package com.wondertek.meeting.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wondertek.meeting.dao.UserLoginLogDao;
import com.wondertek.meeting.model.UserLoginLog;

/**
 * 用户登录记录
 * 
 * @author 金祝华
 */
public class UserLoginLogDaoImpl extends BaseDaoImpl<UserLoginLog, Integer> implements UserLoginLogDao {
	Logger log = LoggerFactory.getLogger(this.getClass());
}

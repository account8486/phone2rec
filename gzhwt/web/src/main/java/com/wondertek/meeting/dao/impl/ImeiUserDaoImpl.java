package com.wondertek.meeting.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wondertek.meeting.dao.ImeiUserDao;
import com.wondertek.meeting.model.ImeiUser;

/**
 * 客户端版本Dao
 * 
 * @author 金祝华
 */
public class ImeiUserDaoImpl extends BaseDaoImpl<ImeiUser, String> implements ImeiUserDao {
	Logger log = LoggerFactory.getLogger(this.getClass());
}

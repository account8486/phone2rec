package com.wondertek.meeting.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wondertek.meeting.dao.GiftDao;
import com.wondertek.meeting.model.Gift;

/**
 * 礼品Dao
 * 
 * @author 金祝华
 */
public class GiftDaoImpl extends BaseDaoImpl<Gift, Long> implements GiftDao {
	Logger log = LoggerFactory.getLogger(this.getClass());
}

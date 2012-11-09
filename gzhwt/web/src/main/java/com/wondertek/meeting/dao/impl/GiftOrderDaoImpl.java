package com.wondertek.meeting.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wondertek.meeting.dao.GiftOrderDao;
import com.wondertek.meeting.model.GiftOrder;

/**
 * 礼品订购Dao
 * 
 * @author 金祝华
 */
public class GiftOrderDaoImpl extends BaseDaoImpl<GiftOrder, Long> implements GiftOrderDao {
	Logger log = LoggerFactory.getLogger(this.getClass());
}

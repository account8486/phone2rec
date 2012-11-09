package com.wondertek.meeting.service.impl;

import java.util.HashMap;
import java.util.Map;

import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.dao.GiftOrderDao;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.GiftOrder;
import com.wondertek.meeting.service.GiftOrderService;
import com.wondertek.meeting.util.StringUtil;

/**
 * 礼物订购
 * 
 * @author 金祝华
 */
public class GiftOrderServiceImpl extends BaseServiceImpl<GiftOrder, Long> implements GiftOrderService {

	private GiftOrderDao giftOrderDao;

	public GiftOrderDao getGiftOrderDao() {
		return giftOrderDao;
	}

	public void setGiftOrderDao(GiftOrderDao giftOrderDao) {
		this.basicDao = giftOrderDao;
		this.giftOrderDao = giftOrderDao;
	}

	public Pager<GiftOrder> listPager(GiftOrder giftOrder, int currentPage, int pageSize) throws ServiceException {
		String hql = "from GiftOrder t where t.state = 1 and t.meetingId=:meetingId";
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("meetingId", giftOrder.getMeetingId());

		// 用户名
		if (giftOrder != null && giftOrder.getUser() != null && StringUtil.isNotEmpty(giftOrder.getUser().getMobile())) {
			hql += " and t.user.mobile like '%'||:mobile||'%'";
			properties.put("mobile", giftOrder.getUser().getMobile());
		}

		// 礼品名称
		if (giftOrder != null && giftOrder.getGift() != null && StringUtil.isNotEmpty(giftOrder.getGift().getName())) {
			hql += " and t.gift.name like '%'||:name||'%'";
			properties.put("name", giftOrder.getGift().getName());
		}

		hql += " order by t.modifyTime desc";

		try {
			return giftOrderDao.findPager(hql, currentPage, pageSize, properties);
		} catch (Exception e) {
			final String errMsg = "Find pager error ";
			log.error(errMsg, e);
			throw new ServiceException(errMsg, e);
		}
	}

	public int del(Long id) {
		// TODO Auto-generated method stub
		return 0;
	}
}

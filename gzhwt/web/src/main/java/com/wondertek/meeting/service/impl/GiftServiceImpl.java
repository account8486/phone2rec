package com.wondertek.meeting.service.impl;

import java.util.HashMap;
import java.util.Map;

import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.dao.GiftDao;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.Gift;
import com.wondertek.meeting.service.GiftService;

/**
 * 礼物
 * 
 * @author 金祝华
 */
public class GiftServiceImpl extends BaseServiceImpl<Gift, Long> implements GiftService {

	private GiftDao giftDao;

	public GiftDao getGiftDao() {
		return giftDao;
	}

	public void setGiftDao(GiftDao giftDao) {
		this.basicDao = giftDao;
		this.giftDao = giftDao;
	}

	public Pager<Gift> listPager(Gift gift, int currentPage, int pageSize) throws ServiceException {
		String hql = "from Gift t where t.state = 1 and t.meetingId=:meetingId";
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("meetingId", gift.getMeetingId());
		
		if (gift != null && gift.getName() != null) {
			hql += " and t.name like '%'||:name||'%'";
			properties.put("name", gift.getName());
		}
		hql += " order by t.modifyTime desc";

		try {
			return giftDao.findPager(hql, currentPage, pageSize, properties);
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

package com.wondertek.meeting.service;

import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.GiftOrder;

/**
 * 礼品订购
 * 
 * @author 金祝华
 */
public interface GiftOrderService extends BaseService<GiftOrder, Long> {

	/**
	 * 分页列表
	 * 
	 * @param user
	 * @param currentPage
	 * @param pageSize
	 * @return
	 * @throws ServiceException
	 */
	public Pager<GiftOrder> listPager(GiftOrder giftOrder, int currentPage, int pageSize)
			throws ServiceException;

	/**
	 * 删除
	 */
	public int del(Long id);
}

package com.wondertek.meeting.service;

import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.Gift;

/**
 * 礼品
 * 
 * @author 金祝华
 */
public interface GiftService extends BaseService<Gift, Long> {

	/**
	 * 分页列表
	 * 
	 * @param user
	 * @param currentPage
	 * @param pageSize
	 * @return
	 * @throws ServiceException
	 */
	public Pager<Gift> listPager(Gift gift, int currentPage, int pageSize)
			throws ServiceException;

	/**
	 * 删除
	 */
	public int del(Long id);
}

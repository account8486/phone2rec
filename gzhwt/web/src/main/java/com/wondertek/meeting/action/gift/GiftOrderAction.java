package com.wondertek.meeting.action.gift;

import com.wondertek.meeting.action.base.BaseAction;
import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.GiftOrder;
import com.wondertek.meeting.service.GiftOrderService;

/**
 * 礼品订单管理
 * 
 * @author 金祝华
 */
public class GiftOrderAction extends BaseAction {

	private static final long serialVersionUID = -3102151360541524276L;

	private GiftOrderService giftOrderService;

	private GiftOrder giftOrder;

	private Long meetingId;

	/**
	 * 查询列表
	 * 
	 * @return
	 */
	public String list() {

		if (giftOrder == null) {
			giftOrder = new GiftOrder();
		}

		giftOrder.setMeetingId(meetingId);

		Pager<GiftOrder> pager = null;
		try {
			pager = giftOrderService.listPager(giftOrder, currentPage, pageSize);
		} catch (ServiceException e) {
			log.error("query gift list error: " + e.toString());
		}

		this.getRequest().setAttribute("pager", pager);

		return SUCCESS;
	}

	public GiftOrderService getGiftOrderService() {
		return giftOrderService;
	}

	public void setGiftOrderService(GiftOrderService giftOrderService) {
		this.giftOrderService = giftOrderService;
	}

	public GiftOrder getGiftOrder() {
		return giftOrder;
	}

	public void setGiftOrder(GiftOrder giftOrder) {
		this.giftOrder = giftOrder;
	}

	public Long getMeetingId() {
		return meetingId;
	}

	public void setMeetingId(Long meetingId) {
		this.meetingId = meetingId;
	}
}

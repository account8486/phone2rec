package com.wondertek.meeting.model;

import java.util.Date;

/**
 * 礼品订购
 * 
 * @author 金祝华
 */
public class GiftOrder extends BaseObject {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	private Long meetingId;

	// 用户ID
	private User user;

	// 礼品ID
	private Gift gift;

	// 数量
	private Integer amount;

	// 总金额
	private Float totalPrice;

	// 状态 0：无效，1：有效
	private Integer state;

	// 修改时间
	private Date modifyTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Float getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Float totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Long getMeetingId() {
		return meetingId;
	}

	public void setMeetingId(Long meetingId) {
		this.meetingId = meetingId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Gift getGift() {
		return gift;
	}

	public void setGift(Gift gift) {
		this.gift = gift;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
}

/**
 * 新闻类别类
 * Author: 张国敬
 * Copyrights: 版权归上海网达软件有限公司安徽分公司所有
 * Create at: 2012-3-6
 */
package com.wondertek.meeting.model;

public class NewsKind extends BaseObject {
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String title; // 类别标题
	private Integer orderCode; // 排序码
	private Meeting meeting; //所属会议

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(Integer orderCode) {
		this.orderCode = orderCode;
	}

	public Meeting getMeeting() {
		return meeting;
	}

	public void setMeeting(Meeting meeting) {
		this.meeting = meeting;
	}
}

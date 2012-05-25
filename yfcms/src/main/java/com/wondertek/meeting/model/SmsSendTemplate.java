package com.wondertek.meeting.model;

import java.util.Date;

import com.wondertek.meeting.action.base.BaseAction;

public class SmsSendTemplate extends BaseAction {

	private static final long serialVersionUID = -3281735778693115103L;
	// 主键
	public Long id;
	// 类别
	public String type;
	// 模板标题
	public String title;
	// 内容
	public String content;
	// 状态是否可用 0表示不用 1表示在用
	public Integer state;
	// 操作信息
	public Long optuser;
	public Date optdate;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Long getOptuser() {
		return optuser;
	}

	public void setOptuser(Long optuser) {
		this.optuser = optuser;
	}

	public Date getOptdate() {
		return optdate;
	}

	public void setOptdate(Date optdate) {
		this.optdate = optdate;
	}

}

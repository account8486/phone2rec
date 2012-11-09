package com.wondertek.meeting.model;

import java.util.Date;

/**
 * RFID签到记录表
 * Author: 张国敬
 * Copyrights: 版权归上海网达软件有限公司安徽分公司所有
 * Create at: 2012-4-6
 */
public class RfidSignIn extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5046374263484265185L;

	private Long id;// id
	private Long meetingId; // 会议ID
	private User user; // 用户
	private String signDate;// 签到日期
	private String signTime;// 签到时间
	private String tagId;// 标签ID
	
	private SignEvent signEvent;  //关联的签到事件
	private Integer signType; //签到的类型，1-签到，2-签退
	private Integer signState; //签到的状态，0-无效，1-正常签到，2：正常签退，3-迟到，4-早退


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Long getMeetingId() {
		return meetingId;
	}

	public void setMeetingId(Long meetingId) {
		this.meetingId = meetingId;
	}


	public String getTagId() {
		return tagId;
	}

	public void setTagId(String tagId) {
		this.tagId = tagId;
	}

	public void setSignEvent(SignEvent signEvent) {
		this.signEvent = signEvent;
	}

	public SignEvent getSignEvent() {
		return signEvent;
	}

	public String getSignDate() {
		return signDate;
	}

	public void setSignDate(String signDate) {
		this.signDate = signDate;
	}

	public String getSignTime() {
		return signTime;
	}

	public void setSignTime(String signTime) {
		this.signTime = signTime;
	}

	public Integer getSignType() {
		return signType;
	}

	public void setSignType(Integer signType) {
		this.signType = signType;
	}

	public Integer getSignState() {
		return signState;
	}

	public void setSignState(Integer signState) {
		this.signState = signState;
	}

}

package com.wondertek.meeting.model;

import java.util.Date;

/**
 * 签到
 * 
 * @author 金祝华
 */
public class SignIn extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5046374263484265185L;

	private Long id;// 用户id
	private Long meetingId; // 会议ID
	private User user; // 用户
	private Date signTime;// 签到时间
	private String signCode;// 签到码
	private Integer portalType;// 签到门户类型

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

	public Date getSignTime() {
		return signTime;
	}

	public void setSignTime(Date signTime) {
		this.signTime = signTime;
	}

	public String getSignCode() {
		return signCode;
	}

	public void setSignCode(String signCode) {
		this.signCode = signCode;
	}

	public Integer getPortalType() {
		return portalType;
	}

	public void setPortalType(Integer portalType) {
		this.portalType = portalType;
	}
}

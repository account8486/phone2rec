package com.wondertek.meeting.model;

import java.util.Date;

import com.wondertek.meeting.util.DateUtil;

/**
 * 会议访问记录
 * 
 * @author 金祝华
 */
public class MeetingAccessLog extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9001504243292312600L;

	private Long id;

	private Meeting meeting;

	private User user;

	private Integer portalType;// 登录方式 1：web 2:wap 3:client

	private Date accessTime; // 访问时间

	private String isDistinct;// 是否去掉重复用户 0：可以重复 1：不可以重复
	private Date startTime;
	private Date endTime;
	private String treeId;// 左侧树传递过来的id，可以是组织id（以o开头）或者用户id（以u开头）

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Meeting getMeeting() {
		return meeting;
	}

	public void setMeeting(Meeting meeting) {
		this.meeting = meeting;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Integer getPortalType() {
		return portalType;
	}

	public void setPortalType(Integer portalType) {
		this.portalType = portalType;
	}

	public Date getAccessTime() {
		return accessTime;
	}

	public void setAccessTime(Date accessTime) {
		this.accessTime = accessTime;
	}

	public String getIsDistinct() {
		return isDistinct;
	}

	public void setIsDistinct(String isDistinct) {
		this.isDistinct = isDistinct;
	}

	public Date getStartTime() {
		return startTime;
	}

	public String getStartTimeS() {
		return DateUtil.getFormatDate(startTime, "yyyy-MM-dd");
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public String getEndTimeS() {
		return DateUtil.getFormatDate(endTime, "yyyy-MM-dd");
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getTreeId() {
		return treeId;
	}

	public void setTreeId(String treeId) {
		this.treeId = treeId;
	}
}

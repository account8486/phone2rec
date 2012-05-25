package com.wondertek.meeting.model;

import java.util.Date;

/**
 * 会议议程信息
 * 
 * @author tangjun
 */
public class MeetingAgenda extends BaseObject {

	private static final long serialVersionUID = 81041L;

	/** 会议议程ID */
	private Long id;

	/** 会议ID */
	private Long meetingId;

	/** 会议议程主题 */
	private String topic;

	/** 会议议程描述 */
	private String description;

	/** 日期 */
	private String date;

	/** 开始时间 */
	private String startTime;

	/** 结束时间 */
	private String endTime;

	/** 创建时间 */
	private Date createTime;

	/** 修改时间 */
	private Date modifyTime;

	/** 会议议程地点 */
	private String location;

	/** 备注 */
	private String comments;

	/** 会议议程状态 */
	private Integer state;

	/** 创建人 */
	private User creator;

	/** 修改人 */
	private User modifier;

	/** 主持人 */
	private String host;
	
	/** 分组计划 */
	private GroupPlan groupPlan;

	/**
	 * @return the host
	 */
	public String getHost() {
		return host;
	}

	/**
	 * @param host
	 *            the host to set
	 */
	public void setHost(String host) {
		this.host = host;
	}

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	public User getModifier() {
		return modifier;
	}

	public void setModifier(User modifier) {
		this.modifier = modifier;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMeetingId() {
		return meetingId;
	}

	public void setMeetingId(Long meetingId) {
		this.meetingId = meetingId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * @param date
	 *            the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * @return the startTime
	 */
	public String getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime
	 *            the startTime to set
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the endTime
	 */
	public String getEndTime() {
		return endTime;
	}

	/**
	 * @return the groupPlan
	 */
	public GroupPlan getGroupPlan() {
		return groupPlan;
	}

	/**
	 * @param groupPlan the groupPlan to set
	 */
	public void setGroupPlan(GroupPlan groupPlan) {
		this.groupPlan = groupPlan;
	}

	/**
	 * @param endTime
	 *            the endTime to set
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
}

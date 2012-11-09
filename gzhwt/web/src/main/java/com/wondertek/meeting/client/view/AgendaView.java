/**
 * 
 */
package com.wondertek.meeting.client.view;

/**
 * @author rain
 * 
 */
public class AgendaView {
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

	/** 会议议程地点 */
	private String location;

	/** 主持人 */
	private String hostName;

	/** 分组编码 */
	private Long groupId;
	
	/** 分组名称 */
	private String groupName;
	
	/** 分组详细 */
	private String groupDetail;

	/** 是否有分组 */
	private boolean hasGroup;
	
	private Long groupPlanId;
	private String groupPlanIsOpen;
	
	private String attendeeNames;

	public String getAttendeeNames() {
		return attendeeNames;
	}

	public void setAttendeeNames(String attendeeNames) {
		this.attendeeNames = attendeeNames;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the meetingId
	 */
	public Long getMeetingId() {
		return meetingId;
	}

	/**
	 * @param meetingId
	 *            the meetingId to set
	 */
	public void setMeetingId(Long meetingId) {
		this.meetingId = meetingId;
	}

	/**
	 * @return the topic
	 */
	public String getTopic() {
		return topic;
	}

	/**
	 * @param topic
	 *            the topic to set
	 */
	public void setTopic(String topic) {
		this.topic = topic;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
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
	 * @param endTime
	 *            the endTime to set
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location
	 *            the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @return the hostName
	 */
	public String getHostName() {
		return hostName;
	}

	/**
	 * @param hostName
	 *            the hostName to set
	 */
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	/**
	 * @return the hasGroup
	 */
	public boolean isHasGroup() {
		return hasGroup;
	}

	/**
	 * @param hasGroup
	 *            the hasGroup to set
	 */
	public void setHasGroup(boolean hasGroup) {
		this.hasGroup = hasGroup;
	}

	/**
	 * @return the groupId
	 */
	public Long getGroupId() {
		return groupId;
	}

	/**
	 * @param groupId
	 *            the groupId to set
	 */
	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	/**
	 * @return the groupName
	 */
	public String getGroupName() {
		return groupName;
	}

	/**
	 * @param groupName the groupName to set
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	/**
	 * @return the groupDetail
	 */
	public String getGroupDetail() {
		return groupDetail;
	}

	/**
	 * @param groupDetail the groupDetail to set
	 */
	public void setGroupDetail(String groupDetail) {
		this.groupDetail = groupDetail;
	}

	/**
	 * @return the groupPlanId
	 */
	public Long getGroupPlanId() {
		return groupPlanId;
	}

	/**
	 * @param groupPlanId the groupPlanId to set
	 */
	public void setGroupPlanId(Long groupPlanId) {
		this.groupPlanId = groupPlanId;
	}

	/**
	 * @return the groupPlanIsOpen
	 */
	public String getGroupPlanIsOpen() {
		return groupPlanIsOpen;
	}

	/**
	 * @param groupPlanIsOpen the groupPlanIsOpen to set
	 */
	public void setGroupPlanIsOpen(String groupPlanIsOpen) {
		this.groupPlanIsOpen = groupPlanIsOpen;
	}
}

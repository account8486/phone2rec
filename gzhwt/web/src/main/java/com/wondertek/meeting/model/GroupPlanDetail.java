package com.wondertek.meeting.model;

import java.util.Date;

/**
 * 分组计划分组信息表
 * 
 * @author John Tang
 */
public class GroupPlanDetail extends BaseObject {

	private static final long serialVersionUID = -2352569272010080940L;
	private Long id;
	private Long planId;
	private String groupName;
	private String groupLeader;
	private User creator;
	private Date createTime;
	private Long meetingId;
	private Integer showIndex;
	private String detail;
	private String businessPersonnel;//会务人员
	private String isOpen; //该组所在的分组模版是否公开

	public String getBusinessPersonnel() {
		return businessPersonnel;
	}

	public void setBusinessPersonnel(String businessPersonnel) {
		this.businessPersonnel = businessPersonnel;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPlanId() {
		return planId;
	}

	public void setPlanId(Long planId) {
		this.planId = planId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupLeader() {
		return groupLeader;
	}

	public void setGroupLeader(String groupLeader) {
		this.groupLeader = groupLeader;
	}

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getMeetingId() {
		return meetingId;
	}

	public void setMeetingId(Long meetingId) {
		this.meetingId = meetingId;
	}


	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getIsOpen() {
		return isOpen;
	}

	public void setIsOpen(String isOpen) {
		this.isOpen = isOpen;
	}

	public Integer getShowIndex() {
		return showIndex;
	}

	public void setShowIndex(Integer showIndex) {
		this.showIndex = showIndex;
	}

}

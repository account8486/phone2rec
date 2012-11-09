package com.wondertek.meeting.model;

import java.util.Date;
import java.util.Set;

public class MeetingTask extends BaseObject {


	private static final long serialVersionUID = 1L;
	private Long id;// 用户id
	private String taskName;
	private String taskDescription;
	private Long meetingId;
	private Long creator;
	private Date createTime;// 创建时间
	private Long modifier;
	private Date modifyTime;// 修改时间
	
	private Set<MeetingTaskDetail> meetingTaskDetails;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getTaskDescription() {
		return taskDescription;
	}
	public void setTaskDescription(String taskDescription) {
		this.taskDescription = taskDescription;
	}
	public Long getMeetingId() {
		return meetingId;
	}
	public void setMeetingId(Long meetingId) {
		this.meetingId = meetingId;
	}
	public Long getCreator() {
		return creator;
	}
	public void setCreator(Long creator) {
		this.creator = creator;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Long getModifier() {
		return modifier;
	}
	public void setModifier(Long modifier) {
		this.modifier = modifier;
	}
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	public Set<MeetingTaskDetail> getMeetingTaskDetails() {
		return meetingTaskDetails;
	}
	public void setMeetingTaskDetails(Set<MeetingTaskDetail> meetingTaskDetails) {
		this.meetingTaskDetails = meetingTaskDetails;
	}
	
	

}

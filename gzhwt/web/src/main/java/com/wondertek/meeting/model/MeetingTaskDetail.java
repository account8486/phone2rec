package com.wondertek.meeting.model;

import java.util.Date;
import java.util.List;

public class MeetingTaskDetail extends BaseObject {


	private static final long serialVersionUID = 1L;
	private Long id;// 用户id
	private String detailName;
	private String detailDescription;
	private String charge;
	private Long meetingId;

	private Date executeStartTime;
	private Date executeEndTime;
	
	private Long creator;
	private Date createTime;// 创建时间
	private Long modifier;
	private Date modifyTime;// 修改时间
	
	private Long parentId;//父任务ID,0表示为第一级人物,无父任务
	private String taskFullPath;//记录任务的路径,例如为0-1-2-3-4
	private String status;
	
	//暂时变量
	List<MeetingTaskDetail> sonMeetingTaskDetailList;
	
	public List<MeetingTaskDetail> getSonMeetingTaskDetailList() {
		return sonMeetingTaskDetailList;
	}
	public void setSonMeetingTaskDetailList(
			List<MeetingTaskDetail> sonMeetingTaskDetailList) {
		this.sonMeetingTaskDetailList = sonMeetingTaskDetailList;
	}
	public String getChargeName() {
		return chargeName;
	}
	public void setChargeName(String chargeName) {
		this.chargeName = chargeName;
	}
	private String chargeName;
	
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public String getTaskFullPath() {
		return taskFullPath;
	}
	public void setTaskFullPath(String taskFullPath) {
		this.taskFullPath = taskFullPath;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDetailName() {
		return detailName;
	}
	public void setDetailName(String detailName) {
		this.detailName = detailName;
	}
	public String getDetailDescription() {
		return detailDescription;
	}
	public void setDetailDescription(String detailDescription) {
		this.detailDescription = detailDescription;
	}
	public String getCharge() {
		return charge;
	}
	public void setCharge(String charge) {
		this.charge = charge;
	}
	public Long getMeetingId() {
		return meetingId;
	}
	public void setMeetingId(Long meetingId) {
		this.meetingId = meetingId;
	}

	public Date getExecuteStartTime() {
		return executeStartTime;
	}
	public void setExecuteStartTime(Date executeStartTime) {
		this.executeStartTime = executeStartTime;
	}
	public Date getExecuteEndTime() {
		return executeEndTime;
	}
	public void setExecuteEndTime(Date executeEndTime) {
		this.executeEndTime = executeEndTime;
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
	
	
	
	
	

}

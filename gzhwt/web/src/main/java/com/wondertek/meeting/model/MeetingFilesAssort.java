package com.wondertek.meeting.model;

import java.util.Date;

public class MeetingFilesAssort extends BaseObject {

	private static final long serialVersionUID = 6316036233924879213L;
	private Long id;// 主键ID
	private String assortName;
	private String description;
	private Long meetingId;
	private Long creator;
	private Long modifier;
	private Date modifyTime;
	
	//新增字段
	private String applyDepartment;
	private String pageImgUrl;
	private Boolean isImgFold;
	
	public String getApplyDepartment() {
		return applyDepartment;
	}

	public void setApplyDepartment(String applyDepartment) {
		this.applyDepartment = applyDepartment;
	}

	public String getPageImgUrl() {
		return pageImgUrl;
	}

	public void setPageImgUrl(String pageImgUrl) {
		this.pageImgUrl = pageImgUrl;
	}

	public Boolean getIsImgFold() {
		return isImgFold;
	}

	public void setIsImgFold(Boolean isImgFold) {
		this.isImgFold = isImgFold;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAssortName() {
		return assortName;
	}

	public void setAssortName(String assortName) {
		this.assortName = assortName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

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

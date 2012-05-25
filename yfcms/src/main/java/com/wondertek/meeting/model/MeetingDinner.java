package com.wondertek.meeting.model;

import java.util.Date;

/**
 * 会议用餐信息
 * 
 * @author John Tang
 */
public class MeetingDinner extends BaseObject {

	private static final long serialVersionUID = 4114649898L;

	private Long id;
	private Long meetingId;
	private String section;
	private String dinnerDate;
	private String startTime;
	private String endTime;
	private String type;
	private String address;
	private AdminUser creator;
	private AdminUser modifier;
	private Date createTime;
	private Date modifyTime;
	private String comments;

	public Long getMeetingId() {
		return meetingId;
	}

	public void setMeetingId(Long meetingId) {
		this.meetingId = meetingId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public String getDinnerDate() {
		return dinnerDate;
	}

	public void setDinnerDate(String dinnerDate) {
		this.dinnerDate = dinnerDate;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public AdminUser getCreator() {
		return creator;
	}

	public void setCreator(AdminUser creator) {
		this.creator = creator;
	}

	public AdminUser getModifier() {
		return modifier;
	}

	public void setModifier(AdminUser modifier) {
		this.modifier = modifier;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

}

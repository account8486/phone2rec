package com.wondertek.meeting.model;

/**
 * 司机模型
 * @author John Tang
 *
 */
public class VehicleDriver extends BaseObject {
	private static final long serialVersionUID = -2695450746158123777L;
	private Long id;
	private String name;
	private Integer gender;// '性别；0：男，1：女
	private Integer age;
	private String mobile;
	private String comments;
	private Long meetingId;
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Long getMeetingId() {
		return meetingId;
	}

	public void setMeetingId(Long meetingId) {
		this.meetingId = meetingId;
	}

}

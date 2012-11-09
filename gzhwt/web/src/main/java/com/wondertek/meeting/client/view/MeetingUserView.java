package com.wondertek.meeting.client.view;

public class MeetingUserView {

	private Long id;
	private String name;
	private String job;// 职位
	private String mobile;// 手机号码即用户登入账号
	private String mailbox;// 用户邮箱
	private Integer gender;// '性别；0：男，1：女
	private String city;// 城市
	private Integer mobileIsDisplay; //是否显示手机号码
	private String roomNo;
	private Integer roomNumberIsDisplay;//房间号
	private Integer jobIsDisplay;//是否显示职位
	private String bookJob;//通讯录职位
	private String department;//单位
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getBookJob() {
		return bookJob;
	}
	public void setBookJob(String bookJob) {
		this.bookJob = bookJob;
	}
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
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getMailbox() {
		return mailbox;
	}
	public void setMailbox(String mailbox) {
		this.mailbox = mailbox;
	}
	public Integer getGender() {
		return gender;
	}
	public void setGender(Integer gender) {
		this.gender = gender;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public Integer getMobileIsDisplay() {
		return mobileIsDisplay;
	}
	public void setMobileIsDisplay(Integer mobileIsDisplay) {
		this.mobileIsDisplay = mobileIsDisplay;
	}
	public String getRoomNo() {
		return roomNo;
	}
	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}
	public Integer getRoomNumberIsDisplay() {
		return roomNumberIsDisplay;
	}
	public void setRoomNumberIsDisplay(Integer roomNumberIsDisplay) {
		this.roomNumberIsDisplay = roomNumberIsDisplay;
	}
	public Integer getJobIsDisplay() {
		return jobIsDisplay;
	}
	public void setJobIsDisplay(Integer jobIsDisplay) {
		this.jobIsDisplay = jobIsDisplay;
	}
}

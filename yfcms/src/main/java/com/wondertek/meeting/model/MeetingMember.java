package com.wondertek.meeting.model;

import java.util.Date;

/**
 * 会议成员
 * 
 * @author 金祝华
 */
public class MeetingMember extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2557746697969589026L;

	private Long meetingId;// 会议ID

	private Long userId;// 用户编码
	
	private Integer memberLevel; // 等级
	
	private Integer sortCode;//排序
	
	private Integer mobileIsDisplay;//电话号码是否可见  0表示不可见 1表示可见
	private Integer roomNumberIsDisplay;//房间号
	private Integer jobIsDisplay;//是否显示职位
	private String department;//单位
	private String bookJob;//通讯录职位
	
	
	public String getBookJob() {
		return bookJob;
	}

	public void setBookJob(String bookJob) {
		this.bookJob = bookJob;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public Integer getJobIsDisplay() {
		return jobIsDisplay;
	}

	public void setJobIsDisplay(Integer jobIsDisplay) {
		this.jobIsDisplay = jobIsDisplay;
	}

	public Integer getRoomNumberIsDisplay() {
		return roomNumberIsDisplay;
	}

	public void setRoomNumberIsDisplay(Integer roomNumberIsDisplay) {
		this.roomNumberIsDisplay = roomNumberIsDisplay;
	}
	private String addInContacts;//是否加入通讯录  Y表示加入 N表示不加入
	
	private String roomNumber;//房间号
	
	//从User中移植过来
	private String mailbox;// 用户邮箱
	private String job;// 职位
	private String country;// 国家/地区'
	private String city;// 城市
	private String address;// 住址
	private String comments;// 备注
	private Date createTime;// 创建时间
	private Date modifyTime;// 修改时间
	
	public Integer getMemberLevel() {
		return memberLevel;
	}

	public void setMemberLevel(Integer memberLevel) {
		this.memberLevel = memberLevel;
	}

	public Long getMeetingId() {
		return meetingId;
	}

	public void setMeetingId(Long meetingId) {
		this.meetingId = meetingId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getSortCode() {
		return sortCode;
	}

	public void setSortCode(Integer sortCode) {
		this.sortCode = sortCode;
	}

	public Integer getMobileIsDisplay() {
		return mobileIsDisplay;
	}

	public void setMobileIsDisplay(Integer mobileIsDisplay) {
		this.mobileIsDisplay = mobileIsDisplay;
	}

	public String getAddInContacts() {
		return addInContacts;
	}

	public void setAddInContacts(String addInContacts) {
		this.addInContacts = addInContacts;
	}

	public String getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}

	public String getMailbox() {
		return mailbox;
	}

	public void setMailbox(String mailbox) {
		this.mailbox = mailbox;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
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

}

package com.wondertek.meeting.model;

import java.util.Date;

/**
 * 用户申请
 * 
 * @author 金祝华
 */
public class UserApply extends BaseObject {

	private static final long serialVersionUID = -8398714400276652119L;
	private Long id;
	private Long meetingId;// 会议ID
	private String mobile;// 手机号码即用户登入账号
	private String name;// 用户姓名
	private String mailbox;// 用户邮箱
	private Integer gender;// '性别；0：男，1：女
	private String job;// 职位
	private String city;// 城市
	private Integer state;// 用户状态 0：无效，1：有效
	private Date createTime;// 创建时间
	private Long auditor;//审批人
	private Date auditTime;// 修改时间
	private String auditOpinion;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMeetingId() {
		return meetingId;
	}

	public void setMeetingId(Long meetingId) {
		this.meetingId = meetingId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}

	public String getAuditOpinion() {
		return auditOpinion;
	}

	public void setAuditOpinion(String auditOpinion) {
		this.auditOpinion = auditOpinion;
	}

	public Long getAuditor() {
		return auditor;
	}

	public void setAuditor(Long auditor) {
		this.auditor = auditor;
	}

}

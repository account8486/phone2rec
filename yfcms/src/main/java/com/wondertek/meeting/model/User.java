package com.wondertek.meeting.model;

import java.util.Date;

/**
 * 用户信息
 * 
 * @author 金祝华
 */
public class User extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8103878837103734748L;

	private Long id;// 用户id
	private String mobile;// 手机号码即用户登入账号
	private String password;// '用户登入密码
	private String name;// 用户姓名
	private Integer gender;// '性别；0：男，1：女
	private String birthday;// 出生日期
	private String idtype;// 证件类型
	private String idnum;// 证件号码
	private String imei;// 手机imei码
	private Integer state;// 用户状态 0：无效，1：有效
	private Date createTime;// 创建时间
	private Date modifyTime;// 修改时间
	private String comments;// 备注
	//返回会议成员列表时用来记录成员的参会级别、排序码、号码是否可见、是否签到，和数据库实体不作对应
	private String isSigned; //是否签到
	private MeetingMember meetingMember;
	
	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}



	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getIdtype() {
		return idtype;
	}

	public void setIdtype(String idtype) {
		this.idtype = idtype;
	}

	public String getIdnum() {
		return idnum;
	}

	public void setIdnum(String idnum) {
		this.idnum = idnum;
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

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getIsSigned() {
		return isSigned;
	}

	public void setIsSigned(String isSigned) {
		this.isSigned = isSigned;
	}

	public MeetingMember getMeetingMember() {
		return meetingMember;
	}

	public void setMeetingMember(MeetingMember meetingMember) {
		this.meetingMember = meetingMember;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}


}

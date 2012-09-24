package com.wondertek.meeting.model;

import java.util.Date;

import com.wondertek.meeting.util.StringUtil;

/**
 * 用户信息
 */
public class AdminUser extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8103878837103734748L;

	private Long id;// 用户id
	private String mobile;// 手机号码即用户登入账号
	private String password;// '用户登入密码
	private String name;// 用户姓名
	private String mailbox;// 用户邮箱
	private Integer gender;// '性别；0：男，1：女
	private String birthday;// 出生日期
	private String job;// 职位
	private String idtype;// 证件类型
	private String idnum;// 证件号码
	private String country;// 国家/地区
	private String city;// 城市
	private String address;// 住址
	private String comments;// 备注

	private Integer state;// 用户状态 0：无效，1：有效
	private Date createTime;// 创建时间
	private Date modifyTime;// 修改时间

	private Organization org; // 机构
	private Integer roleId;

	private String imei;// 手机imei码
	
	// 导入使用 不作为数据库字段
	private Date startValidDate; // 开始有效日期
	private Date endValidDate; // 结束有效日期
	private String tel; // 电话

	
	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public Date getStartValidDate() {
		return startValidDate;
	}

	public void setStartValidDate(Date startValidDate) {
		this.startValidDate = startValidDate;
	}

	public Date getEndValidDate() {
		return endValidDate;
	}

	public void setEndValidDate(Date endValidDate) {
		this.endValidDate = endValidDate;
	}

	public Organization getOrg() {
		return org;
	}

	public void setOrg(Organization org) {
		this.org = org;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public void setGender(String gender) {
		int g = StringUtil.parseInteger(gender);
		this.gender = g;
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

}

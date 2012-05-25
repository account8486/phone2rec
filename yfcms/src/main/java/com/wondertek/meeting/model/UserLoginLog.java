package com.wondertek.meeting.model;

/**
 * 用户登录记录
 * 
 * @author 金祝华
 */
public class UserLoginLog extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9001504243292312600L;

	private Long id;

	private User user;

	private String loginTime; // 登录时间

	private String loginIp; // 登录ip

	private Integer portalType;// 登录方式 1：web 2:wap 3:client

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Integer getPortalType() {
		return portalType;
	}

	public void setPortalType(Integer portalType) {
		this.portalType = portalType;
	}

	public String getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(String loginTime) {
		this.loginTime = loginTime;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}
}

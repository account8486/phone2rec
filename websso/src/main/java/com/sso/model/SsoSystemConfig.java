package com.sso.model;

import java.util.Date;

public class SsoSystemConfig {
	
	private String id;
	private String ssoId;
	private String userNameCfg;
	private String passwordCfg;
	private String comments;
	private String logoUrl;
	private String accountColumnName;

	public String getAccountColumnName() {
		return accountColumnName;
	}
	public void setAccountColumnName(String accountColumnName) {
		this.accountColumnName = accountColumnName;
	}
	private Date createTime;
	private Date updateTime;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSsoId() {
		return ssoId;
	}
	public void setSsoId(String ssoId) {
		this.ssoId = ssoId;
	}
	public String getUserNameCfg() {
		return userNameCfg;
	}
	public void setUserNameCfg(String userNameCfg) {
		this.userNameCfg = userNameCfg;
	}
	public String getPasswordCfg() {
		return passwordCfg;
	}
	public void setPasswordCfg(String passwordCfg) {
		this.passwordCfg = passwordCfg;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getLogoUrl() {
		return logoUrl;
	}
	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
}

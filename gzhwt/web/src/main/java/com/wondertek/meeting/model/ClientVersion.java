package com.wondertek.meeting.model;

import java.util.Date;

/**
 * 客户端版本
 * 
 * @author 金祝华
 */
public class ClientVersion extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7705732765630882150L;

	private Integer id;

	// 安装文件名称
	private String name;

	// 安装文件版本
	private Integer version;

	// 修改时间
	private Date modifyTime;
	
	// 下载url
	private String url;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
}

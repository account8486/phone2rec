package com.wondertek.meeting.model;

import java.util.Date;

import com.wondertek.meeting.model.custom.MeetingType;

public class BaseMenu extends BaseObject {

	private static final long serialVersionUID = -6231556050382097417L;
	private Long id;
	private Integer type;
	// 菜单名称
	private String name;
	// 图标URL
	private String imgUrl;
	// 内容URL
	private String contentUrl;
	// 状态 0：无效，1：有效
	private Integer state;
	// 修改时间
	private Date modifyTime;
	// 终端类型
	public String terminalType;
	// 显示名称,显示在界面上
	public String description;
	private Integer defaultSortCode;// 默认排序码
	
	private String downloadUrl;
	private String packageName;
	//是否需要初始化
	private Boolean isInitial;
	
	private MeetingType meetingType; //会议类型
	
	public Boolean getIsInitial() {
		return isInitial;
	}
	public void setIsInitial(Boolean isInitial) {
		this.isInitial = isInitial;
	}
	public String getDownloadUrl() {
		return downloadUrl;
	}
	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public String getContentUrl() {
		return contentUrl;
	}
	public void setContentUrl(String contentUrl) {
		this.contentUrl = contentUrl;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	public String getTerminalType() {
		return terminalType;
	}
	public void setTerminalType(String terminalType) {
		this.terminalType = terminalType;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getDefaultSortCode() {
		return defaultSortCode;
	}
	public void setDefaultSortCode(Integer defaultSortCode) {
		this.defaultSortCode = defaultSortCode;
	}
	public MeetingType getMeetingType() {
		return meetingType;
	}
	public void setMeetingType(MeetingType meetingType) {
		this.meetingType = meetingType;
	}
	
	
	

}

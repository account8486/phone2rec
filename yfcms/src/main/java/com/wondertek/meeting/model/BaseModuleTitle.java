package com.wondertek.meeting.model;


public class BaseModuleTitle {

	private Long id;
	private Long baseMenuId;
	private Integer meetingTypeId;
	private String name;
	private String titleName;
	private String keyName;
	private String terminalType;
	
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
	public String getTitleName() {
		return titleName;
	}
	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}
	public String getKeyName() {
		return keyName;
	}
	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}
	public String getTerminalType() {
		return terminalType;
	}
	public void setTerminalType(String terminalType) {
		this.terminalType = terminalType;
	}
	public Long getBaseMenuId() {
		return baseMenuId;
	}
	public void setBaseMenuId(Long baseMenuId) {
		this.baseMenuId = baseMenuId;
	}
	public Integer getMeetingTypeId() {
		return meetingTypeId;
	}
	public void setMeetingTypeId(Integer meetingTypeId) {
		this.meetingTypeId = meetingTypeId;
	}
}

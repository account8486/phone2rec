package com.wondertek.meeting.model;


public class MeetingModuleTitle {

	private Long id;
	private Long meetingId;
	private Long baseModuleTitleId;
	private Long baseMenuId;
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
	public Long getMeetingId() {
		return meetingId;
	}
	public void setMeetingId(Long meetingId) {
		this.meetingId = meetingId;
	}
	public Long getBaseModuleTitleId() {
		return baseModuleTitleId;
	}
	public void setBaseModuleTitleId(Long baseModuleTitleId) {
		this.baseModuleTitleId = baseModuleTitleId;
	}
	public Long getBaseMenuId() {
		return baseMenuId;
	}
	public void setBaseMenuId(Long baseMenuId) {
		this.baseMenuId = baseMenuId;
	}
}

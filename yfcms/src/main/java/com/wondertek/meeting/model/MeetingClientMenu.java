package com.wondertek.meeting.model;

/**
 * 会议与客户端首页九宫格菜单关联
 * 
 * @author 金祝华
 */
public class MeetingClientMenu extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1027624267498303778L;

	private Long meetingId;// 会议ID

	private Long menuId; // 菜单ID

	private Integer sort; // 排序

	private Integer memberLevel;
	
	//终端类型
	public String terminalType; 

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

	public Long getMenuId() {
		return menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getTerminalType() {
		return terminalType;
	}

	public void setTerminalType(String terminalType) {
		this.terminalType = terminalType;
	}
}

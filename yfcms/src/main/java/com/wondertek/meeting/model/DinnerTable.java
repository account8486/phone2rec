package com.wondertek.meeting.model;


/**
 * 会议用餐座位信息
 * 
 * @author John Tang
 */
public class DinnerTable extends BaseObject {

	private static final long serialVersionUID = 4114649898L;
	private Long id;
	private Long dinnerId;
	private Long memberId;
	private Long meetingId;
	private String tableCode;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getDinnerId() {
		return dinnerId;
	}
	public void setDinnerId(Long dinnerId) {
		this.dinnerId = dinnerId;
	}
	public Long getMemberId() {
		return memberId;
	}
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	public Long getMeetingId() {
		return meetingId;
	}
	public void setMeetingId(Long meetingId) {
		this.meetingId = meetingId;
	}
	public String getTableCode() {
		return tableCode;
	}
	public void setTableCode(String tableCode) {
		this.tableCode = tableCode;
	}
	
}

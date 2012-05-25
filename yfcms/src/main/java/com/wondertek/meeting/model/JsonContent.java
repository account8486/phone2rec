package com.wondertek.meeting.model;


/**
 * 会议内容实体
 * 
 * @author GuoXu
 */
public class JsonContent extends BaseObject {

	private static final long serialVersionUID = 1L;
	private Long meetingId;
	private Long id;
	private String contentTitle;
	private Integer sortNo; //排序号
	public Long getMeetingId() {
		return meetingId;
	}
	public void setMeetingId(Long meetingId) {
		this.meetingId = meetingId;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getContentTitle() {
		return contentTitle;
	}
	public void setContentTitle(String contentTitle) {
		this.contentTitle = contentTitle;
	}
	public Integer getSortNo() {
		return sortNo;
	}
	public void setSortNo(Integer sortNo) {
		this.sortNo = sortNo;
	}
	
	
}

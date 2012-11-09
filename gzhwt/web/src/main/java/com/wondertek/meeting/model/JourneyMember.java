package com.wondertek.meeting.model;

import java.util.Date;

/**
 * 行程人员
 * 
 * @author John Tang
 * 
 */
public class JourneyMember extends BaseObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5064731123726714439L;
	private Long id;
	private Long meetingId;
    private Long journeyId;
    private Long userId;
    private Integer number;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getMeetingId() {
		return meetingId;
	}
	public void setMeetingId(Long meetingId) {
		this.meetingId = meetingId;
	}
	public Long getJourneyId() {
		return journeyId;
	}
	public void setJourneyId(Long journeyId) {
		this.journeyId = journeyId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
    
    
	
}

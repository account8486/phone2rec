package com.wondertek.meeting.model;

import java.util.Date;

/**
 * 行程模型
 * 
 * @author John Tang
 * 
 */
public class Journey extends BaseObject {
	private static final long serialVersionUID = 3244173610254743679L;
	private Long id;
	private Long meetingId;
	private String name;
	private String departure;
	private String destination;
	private Date startTime;
	private Date endTime;
	private String comments;
    private Long vehicleId;
    private Long driverId;
    private String journeyMembers;
	
	public String getJourneyMembers() {
		return journeyMembers;
	}

	public void setJourneyMembers(String journeyMembers) {
		this.journeyMembers = journeyMembers;
	}

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

	public String getDeparture() {
		return departure;
	}

	public void setDeparture(String departure) {
		this.departure = departure;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}


	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Long getMeetingId() {
		return meetingId;
	}

	public void setMeetingId(Long meetingId) {
		this.meetingId = meetingId;
	}

	public Long getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(Long vehicleId) {
		this.vehicleId = vehicleId;
	}

	public Long getDriverId() {
		return driverId;
	}

	public void setDriverId(Long driverId) {
		this.driverId = driverId;
	}
	
	
}

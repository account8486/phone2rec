package com.wondertek.meeting.vo;

import java.util.Date;

public class JourneyView {
	
	private Long journeyId;
	private String name;
	private String departure;
	private String destination;
	private Date startTime;
	private Date endTime;
    private String vehicleName;
	private String vehicleNumber;
    private String driverName;
    private String driverMobile;
    private String journeyMembers;
    private String vehicleType;
    //司机ID
    private String vehicleDriverId;
    
	public Long getJourneyId() {
		return journeyId;
	}
	public void setJourneyId(Long journeyId) {
		this.journeyId = journeyId;
	}
	
	
	public String getVehicleDriverId() {
		return vehicleDriverId;
	}
	public void setVehicleDriverId(String vehicleDriverId) {
		this.vehicleDriverId = vehicleDriverId;
	}
	public String getVehicleType() {
		return vehicleType;
	}
	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
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
	public String getVehicleName() {
		return vehicleName;
	}
	public void setVehicleName(String vehicleName) {
		this.vehicleName = vehicleName;
	}
	public String getVehicleNumber() {
		return vehicleNumber;
	}
	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}
	public String getDriverName() {
		return driverName;
	}
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	public String getDriverMobile() {
		return driverMobile;
	}
	public void setDriverMobile(String driverMobile) {
		this.driverMobile = driverMobile;
	}
	public String getJourneyMembers() {
		return journeyMembers;
	}
	public void setJourneyMembers(String journeyMembers) {
		this.journeyMembers = journeyMembers;
	}
    
    

}

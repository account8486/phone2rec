package com.wondertek.meeting.model;

public class DinnerInfo extends BaseObject {
	private static final long serialVersionUID = 6547104478387352037L;
	private Long dinnerId;
	private String dinnerDate;
	private String scetion;
	private String type;
	private String time;
	private String dinnerTable;// 不等于"自选座位"有分组
	private String address;
	private String comments;
	private Long groupPlanId;// 该用餐指定的分组ID
	private String groupPlanName;// 该用餐指定的分组

	public String getDinnerDate() {
		return dinnerDate;
	}

	public void setDinnerDate(String dinnerDate) {
		this.dinnerDate = dinnerDate;
	}

	public String getScetion() {
		return scetion;
	}

	public void setScetion(String scetion) {
		this.scetion = scetion;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDinnerTable() {
		return dinnerTable;
	}

	public void setDinnerTable(String dinnerTable) {
		this.dinnerTable = dinnerTable;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Long getDinnerId() {
		return dinnerId;
	}

	public void setDinnerId(Long dinnerId) {
		this.dinnerId = dinnerId;
	}

	public Long getGroupPlanId() {
		return groupPlanId;
	}

	public void setGroupPlanId(Long groupPlanId) {
		this.groupPlanId = groupPlanId;
	}

	public String getGroupPlanName() {
		return groupPlanName;
	}

	public void setGroupPlanName(String groupPlanName) {
		this.groupPlanName = groupPlanName;
	}

}

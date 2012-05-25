package com.wondertek.meeting.model;

import java.util.List;

public class UserTableInfoWithDate extends BaseObject {

	private static final long serialVersionUID = -5229788527876775466L;

	private String date;
	
	private List<DinnerInfo> info;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public List<DinnerInfo> getInfo() {
		return info;
	}

	public void setInfo(List<DinnerInfo> info) {
		this.info = info;
	}

}

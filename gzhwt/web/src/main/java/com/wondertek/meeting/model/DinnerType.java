package com.wondertek.meeting.model;

public class DinnerType extends BaseObject {
	private String id; //id
	private String name;//类型名称
	private Boolean choose;//是否分桌
	
	public DinnerType(String id, String name, Boolean choose) {
		super();
		this.id = id;
		this.name = name;
		this.choose = choose;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Boolean getChoose() {
		return choose;
	}
	public void setChoose(Boolean choose) {
		this.choose = choose;
	}
	
}

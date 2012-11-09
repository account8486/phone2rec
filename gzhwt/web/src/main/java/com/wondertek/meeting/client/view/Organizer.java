package com.wondertek.meeting.client.view;

public class Organizer {
	private Integer sort;
	private String organizer;
	
	public Organizer() {
	}
	
	public Organizer(Integer sort,String organizer){
		this.sort = sort;
		this.organizer = organizer;
	}
	
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public String getOrganizer() {
		return organizer;
	}
	public void setOrganizer(String organizer) {
		this.organizer = organizer;
	}
	
	
}

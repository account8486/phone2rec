/** 
*  
*/
package com.wondertek.meeting.model;

import java.io.Serializable;

/** 
 * @ClassName: Guest 
 * @Description: 嘉宾信息
 * @author zouxiaoming
 * @date Mar 6, 2012 1:11:28 PM 
 */
@SuppressWarnings("serial")
public class Guest extends BaseObject implements Serializable {
	private Long id;
	/**
	 * 姓名
	 */
	private String name;
	/**
	 * 头衔
	 */
	private String rank;
	/**
	 * 简介
	 */
	private String about;
	
	private String headUrl;//头像url
	
	
	/**
	 * 关联的会议
	 */
	private Meeting meeting;
	
	
	public String getHeadUrl() {
		return headUrl;
	}
	public void setHeadUrl(String headUrl) {
		this.headUrl = headUrl;
	}
	/**
	 * @Description
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @Description
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @Description
	 * @return the rank
	 */
	public String getRank() {
		return rank;
	}
	/**
	 * @param rank
	 */
	public void setRank(String rank) {
		this.rank = rank;
	}
	/**
	 * @Description
	 * @return the about
	 */
	public String getAbout() {
		return about;
	}
	/**
	 * @param about
	 */
	public void setAbout(String about) {
		this.about = about;
	}
	/**
	 * @param meeting
	 */
	public void setMeeting(Meeting meeting) {
		this.meeting = meeting;
	}
	/**
	 * @Description
	 * @return the meeting
	 */
	public Meeting getMeeting() {
		return meeting;
	}
	
	
	
}

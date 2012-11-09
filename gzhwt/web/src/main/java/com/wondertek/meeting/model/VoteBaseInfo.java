package com.wondertek.meeting.model;

import java.util.Date;
import java.util.Set;

/** 
 * @ClassName: VoteBaseInfo 
 * @Description: 投票基本信息
 * @author zouxiaoming
 * @date Jan 9, 2012 10:31:15 AM 
 *  
 */

public class VoteBaseInfo {
	private Long id;
	private String title;
	private Date createTime=new Date();
	private Integer type=1;
	private Integer state=2;
	private Integer count=0;
	private Meeting meeting;
	private Set<VoteItem> items;
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @Description
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @Description 投票标题
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @Description 投票创建时间
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * @Description 投票类型   1：单选  2：多选
	 * @return the type
	 */
	public Integer getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(Integer type) {
		this.type = type;
	}
	/**
	 * @param state the state to set
	 */
	public void setState(Integer state) {
		this.state = state;
	}
	/**
	 * @Description 投票状态  1:启用  2：取消
	 * @return the state
	 */
	public Integer getState() {
		return state;
	}
	/**
	 * @param count the count to set
	 */
	public void setCount(Integer count) {
		this.count = count;
	}
	/**
	 * @Description 投票参加人数
	 * @return the count
	 */
	public Integer getCount() {
		return count;
	}
	/**
	 * @param meeting
	 */
	public void setMeeting(Meeting meeting) {
		this.meeting = meeting;
	}
	/**
	 * @Description 投票关联的会议信息
	 * @return the meeting
	 */
	public Meeting getMeeting() {
		return meeting;
	}
	/**
	 * @param items
	 */
	public void setItems(Set<VoteItem> items) {
		this.items = items;
	}
	/**
	 * @Description 对应的所有投票项目
	 * @return the items
	 */
	public Set<VoteItem> getItems() {
		return items;
	}
}

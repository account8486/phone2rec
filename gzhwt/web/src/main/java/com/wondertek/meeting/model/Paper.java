package com.wondertek.meeting.model;

import java.util.Date;
import java.util.Set;

/** 
 * @ClassName: Paper 
 * @Description: 试卷表/会场建议
 * @author zouxiaoming
 * @date Feb 2, 2012 10:19:53 AM 
 *  
 */
public class Paper {
	private Long id; 
	private String title; // 标题
	private Date createTime=new Date(); // 创建时间
	private Integer state=2; // 试卷状态  1:启用 2:未启用
	private AdminUser creator;  // 创建人
	private Meeting meeting;  // 关联的会议
	private Set<Question> quetions;  // 试卷对应的所有的试题
	private Integer count=0; // 参与人数
	
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
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @Description
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * @param createTime
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * @Description
	 * @return the state
	 */
	public Integer getState() {
		return state;
	}
	/**
	 * @param state
	 */
	public void setState(Integer state) {
		this.state = state;
	}
	/**
	 * @param quetions
	 */
	public void setQuetions(Set<Question> quetions) {
		this.quetions = quetions;
	}
	/**
	 * @Description
	 * @return the quetions
	 */
	public Set<Question> getQuetions() {
		return quetions;
	}
	/**
	 * @param creator
	 */
	public void setCreator(AdminUser creator) {
		this.creator = creator;
	}
	/**
	 * @Description
	 * @return the creator
	 */
	public AdminUser getCreator() {
		return creator;
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
	/**
	 * @param count
	 */
	public void setCount(Integer count) {
		this.count = count;
	}
	/**
	 * @Description
	 * @return the count
	 */
	public Integer getCount() {
		return count;
	}
	
}

package com.wondertek.meeting.model;

import java.io.Serializable;
import java.util.Date;

/** 
 * @ClassName: QuestionItem 
 * @Description: 题目选项
 * @author zouxiaoming
 * @date Feb 2, 2012 10:21:37 AM 
 *  
 */
@SuppressWarnings("serial")
public class QuestionItem implements Serializable,Comparable<QuestionItem> {
	
	private Long id;
	private String content;
	private Date createTime=new Date();
	private Integer count=0;
	private Question question;
	
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
	 * @return the content
	 */
	public String getContent() {
		return content;
	}
	/**
	 * @param content
	 */
	public void setContent(String content) {
		this.content = content;
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
	 * @return the count
	 */
	public Integer getCount() {
		return count;
	}
	/**
	 * @param count
	 */
	public void setCount(Integer count) {
		this.count = count;
	}
	/**
	 * @Description
	 * @return the question
	 */
	public Question getQuestion() {
		return question;
	}
	/**
	 * @param question
	 */
	public void setQuestion(Question question) {
		this.question = question;
	}
	@Override
	public int compareTo(QuestionItem o) {
		return this.id.intValue()-o.getId().intValue();
	}
	
	
	
	
	
}

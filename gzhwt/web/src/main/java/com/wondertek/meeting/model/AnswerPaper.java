package com.wondertek.meeting.model;

import java.util.Date;

/** 
 * @ClassName: AnswerPaper 
 * @Description: 答卷表
 * @author zouxiaoming
 * @date Feb 2, 2012 10:20:56 AM 
 *  
 */
public class AnswerPaper {
	private Long id;
	private Paper paper;
	private User  user;
	private QuestionItem item;
	private Integer state=1;  // 是否匿名  1：匿名 2：署名
	private String content;   // 简答题的答案
	private Integer type=1; // 题型  1：单选题 2:多选题 3：简述题
	private Question question;
	
	private Date answerTime;//回答问题时间
	

	public Date getAnswerTime() {
		return answerTime;
	}
	public void setAnswerTime(Date answerTime) {
		this.answerTime = answerTime;
	}
	/**
	 * @Description
	 * @return the paper
	 */
	public Paper getPaper() {
		return paper;
	}
	/**
	 * @param paper
	 */
	public void setPaper(Paper paper) {
		this.paper = paper;
	}
	/**
	 * @Description
	 * @return the user
	 */
	public User getUser() {
		return user;
	}
	/**
	 * @param user
	 */
	public void setUser(User user) {
		this.user = user;
	}
	/**
	 * @Description
	 * @return the item
	 */
	public QuestionItem getItem() {
		return item;
	}
	/**
	 * @param item
	 */
	public void setItem(QuestionItem item) {
		this.item = item;
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
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @Description
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param content
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * @Description
	 * @return the content
	 */
	public String getContent() {
		return content;
	}
	/**
	 * @param question
	 */
	public void setQuestion(Question question) {
		this.question = question;
	}
	/**
	 * @Description
	 * @return the question
	 */
	public Question getQuestion() {
		return question;
	}
	/**
	 * @param type
	 */
	public void setType(Integer type) {
		this.type = type;
	}
	/**
	 * @Description
	 * @return the type
	 */
	public Integer getType() {
		return type;
	}
	
	
	
}

package com.wondertek.meeting.model;

import java.util.Date;
import java.util.Set;

/** 
 * @ClassName: Question 
 * @Description: 考题表
 * @author zouxiaoming
 * @date Feb 2, 2012 10:21:37 AM 
 *  
 */
public class Question {
	private Long id;
	private String question;  // 题目标题
	private String answer;  // 参考答案
	private Integer type;   // 题目类型   1：单选  2：多选  3:简答题
	private Integer state=1;   // 题目状态  是否启用  1：启用 2：未启用
	private Date createTime=new Date();  // 创建时间
	private AdminUser creator;   // 创建人
	private Paper paper;   // 属于的试卷
	private Set<QuestionItem> items;  // 对应的所有问题选项
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
	 * @return the question
	 */
	public String getQuestion() {
		return question;
	}
	/**
	 * @param question
	 */
	public void setQuestion(String question) {
		this.question = question;
	}
	
	/**
	 * @Description
	 * @return the answer
	 */
	public String getAnswer() {
		return answer;
	}
	/**
	 * @param answer
	 */
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	/**
	 * @Description
	 * @return the type
	 */
	public Integer getType() {
		return type;
	}
	/**
	 * @param type
	 */
	public void setType(Integer type) {
		this.type = type;
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
	 * @return the creator
	 */
	public AdminUser getCreator() {
		return creator;
	}
	/**
	 * @param creator
	 */
	public void setCreator(AdminUser creator) {
		this.creator = creator;
	}
	/**
	 * @param paper
	 */
	public void setPaper(Paper paper) {
		this.paper = paper;
	}
	/**
	 * @Description
	 * @return the paper
	 */
	public Paper getPaper() {
		return paper;
	}
	/**
	 * @param items
	 */
	public void setItems(Set<QuestionItem> items) {
		this.items = items;
	}
	/**
	 * @Description
	 * @return the items
	 */
	public Set<QuestionItem> getItems() {
		return items;
	}
	
	
	
}

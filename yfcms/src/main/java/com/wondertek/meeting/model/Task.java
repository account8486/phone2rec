package com.wondertek.meeting.model;

import java.util.Date;


/**
 * 任务信息
 * 
 * @author tangjun
 */
public class Task extends BaseObject {

	private static final long serialVersionUID = 81040L;
	
	/** 任务ID */
	private Long id;
	
	/** 会议ID */
	private Integer meetingId;

	/** 任务名称 */
	private String name;

	/** 任务类型 */
	private int type;

	/** 任务责任人ID */
	private User principle;

	/** 任务描述 */
	private String description;
	
	/** 任务评价 */
	private String evaluation;

	/** 任务状态 */
	private Integer state;
	
	/** 任务进度 */
	private Integer progress;

	
	private Date startTime;
	
	private Date endTime;
	
	private User creator;
	
	private User modifier;

	
	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	public User getModifier() {
		return modifier;
	}

	public void setModifier(User modifier) {
		this.modifier = modifier;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Integer getMeetingId() {
		return meetingId;
	}

	public void setMeetingId(Integer meetingId) {
		this.meetingId = meetingId;
	}


	public Integer getProgress() {
		return progress;
	}

	public void setProgress(Integer progress) {
		this.progress = progress;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public User getPrinciple() {
		return principle;
	}

	public void setPrinciple(User principle) {
		this.principle = principle;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getEvaluation() {
		return evaluation;
	}

	public void setEvaluation(String evaluation) {
		this.evaluation = evaluation;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}

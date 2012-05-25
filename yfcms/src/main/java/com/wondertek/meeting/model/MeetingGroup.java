package com.wondertek.meeting.model;


/**
 * 分组计划和议程用餐等的关联表
 * 
 * @author John Tang
 */
public class MeetingGroup extends BaseObject {

	private static final long serialVersionUID = 4114654453636L;
	private Long id;
	private Long planId;
	private Long relationId;
	private Integer type;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPlanId() {
		return planId;
	}

	public void setPlanId(Long planId) {
		this.planId = planId;
	}

	public Long getRelationId() {
		return relationId;
	}

	public void setRelationId(Long relationId) {
		this.relationId = relationId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

}

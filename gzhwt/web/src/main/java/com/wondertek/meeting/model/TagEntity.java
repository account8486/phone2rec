package com.wondertek.meeting.model;

public class TagEntity {
	private String entity;
	private String entityValue;
	private String entityValueType;
	private String entityName;
	private String entityNameType;
	private String condition;
	public String getEntity() {
		return entity;
	}
	public void setEntity(String entity) {
		this.entity = entity;
	}
	public String getEntityValue() {
		return entityValue;
	}
	public void setEntityValue(String entityValue) {
		this.entityValue = entityValue;
	}
	public String getEntityName() {
		return entityName;
	}
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	public String getEntityValueType() {
		return entityValueType;
	}
	public void setEntityValueType(String entityValueType) {
		this.entityValueType = entityValueType;
	}
	public String getEntityNameType() {
		return entityNameType;
	}
	public void setEntityNameType(String entityNameType) {
		this.entityNameType = entityNameType;
	}
}

package com.wondertek.meeting.model;

import java.util.Date;

/**
 * 会议内容实体
 * 
 * @author GuoXu
 */
public class MeetingContent extends BaseObject {

	private static final long serialVersionUID = 1L;
	private Long meetingId;
	private Long id;
	private String contentTitle;
	private Integer contentType;
	private String content;
	private Date createTime;
	private Date modifyTime;
	private AdminUser creator;
    private AdminUser modifier;
	private String comments;
	private Integer state;
	private Integer category;//0-7 显示在哪些菜单web,WAP,client
	private Integer sortNo; //排序号
	private String icon;
	
	private String isList = "0";
	
	private Long parentId;

	public Long getMeetingId() {
		return meetingId;
	}

	public void setMeetingId(Long meetingId) {
		this.meetingId = meetingId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContentTitle() {
		return contentTitle;
	}

	public void setContentTitle(String contentTitle) {
		this.contentTitle = contentTitle;
	}

	public Integer getContentType() {
		return contentType;
	}

	public void setContentType(Integer contentType) {
		this.contentType = contentType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public AdminUser getCreator() {
		return creator;
	}

	public void setCreator(AdminUser creator) {
		this.creator = creator;
	}

	public AdminUser getModifier() {
		return modifier;
	}

	public void setModifier(AdminUser modifier) {
		this.modifier = modifier;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getCategory() {
		return category;
	}

	public void setCategory(Integer category) {
		this.category = category;
	}

	public Integer getSortNo() {
		return sortNo;
	}

	public void setSortNo(Integer sortNo) {
		this.sortNo = sortNo;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	public String getIsList() {
		return isList;
	}

	public void setIsList(String isList) {
		this.isList = isList;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public boolean isInWeb(){
		if(category == null) return false;
		return (4 & category) == 4;
	}
	public boolean isInWap(){
		if(category == null) return false;
		return (2 & category) == 2;
	}
	public boolean isInClient(){
		if(category == null) return false;
		return (1 & category) == 1;
	}
	
	public boolean isInTouch(){
		if(category == null) return false;
		return (8 & category) == 8;
	}
	
}

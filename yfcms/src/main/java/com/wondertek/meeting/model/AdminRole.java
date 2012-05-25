package com.wondertek.meeting.model;

import java.util.Date;

/**
 * 角色
 * 
 * @author 金祝华
 */
public class AdminRole extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	// 角色名称
	private String roleName;

	// 状态 0：无效，1：有效
	private Integer state;

	// 备注
	private String comments;

	// 创建时间
	private Date createTime;

	// 修改时间
	private Date modifyTime;

	// 创建人
	private String createId;

	// 创建人姓名
	private String createName;

	// 修改人
	private String modifyId;

	// 修改人姓名
	private String modifyName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
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

	public String getCreateId() {
		return createId;
	}

	public void setCreateId(String createId) {
		this.createId = createId;
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public String getModifyId() {
		return modifyId;
	}

	public void setModifyId(String modifyId) {
		this.modifyId = modifyId;
	}

	public String getModifyName() {
		return modifyName;
	}

	public void setModifyName(String modifyName) {
		this.modifyName = modifyName;
	}
}

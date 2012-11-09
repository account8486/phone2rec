package com.wondertek.meeting.model.security;

import java.util.Date;
import com.wondertek.meeting.model.BaseObject;

public class SecurityRoleUnit extends BaseObject {
	
	private static final long serialVersionUID = 1673702930885017631L;
	private Long id;// 用户id
	private Long roleId;// 用户角色ID
	private Long unitId;// 用户菜单ID
	
	private Date createTime;// 创建时间
	private Long creator;
	private Date modifyTime;// 修改时间
	private Long modifier;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Long getUnitId() {
		return unitId;
	}

	public void setUnitId(Long unitId) {
		this.unitId = unitId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getCreator() {
		return creator;
	}

	public void setCreator(Long creator) {
		this.creator = creator;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Long getModifier() {
		return modifier;
	}

	public void setModifier(Long modifier) {
		this.modifier = modifier;
	}

}

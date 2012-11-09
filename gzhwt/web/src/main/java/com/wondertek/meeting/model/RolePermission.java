package com.wondertek.meeting.model;

import java.util.Date;

/**
 * 角色权限
 * 
 * @author 金祝华
 */
public class RolePermission extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1849310877677123386L;

	private Integer roleId;
	private String roleName;
	private Integer permissionId;
	private String permissionName;

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

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Integer getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(Integer permissionId) {
		this.permissionId = permissionId;
	}

	public String getPermissionName() {
		return permissionName;
	}

	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
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

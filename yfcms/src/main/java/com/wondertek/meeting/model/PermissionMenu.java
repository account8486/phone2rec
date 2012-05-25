package com.wondertek.meeting.model;

import java.util.Date;

/**
 * 权限菜单关联
 * 
 * @author 金祝华
 */
public class PermissionMenu extends BaseObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8990775523744483226L;

	private Integer permissionId;
	private String permissionName;
	private Integer menuId;
	private String menuName;

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

	public Integer getMenuId() {
		return menuId;
	}

	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
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

package com.wondertek.meeting.model;

/**
 * 用户角色
 * 
 * @author 金祝华
 */
public class UserRole extends BaseObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 380036058144508355L;

	private Long userId;

	private Long roleId;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
}

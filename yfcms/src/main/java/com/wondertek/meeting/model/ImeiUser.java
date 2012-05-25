package com.wondertek.meeting.model;

/**
 * imei-用户关联
 * 
 * @author 金祝华
 */
public class ImeiUser extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6337528642875920446L;

	private String imei;// imei码
	private Long userId;// 用户ID

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
}

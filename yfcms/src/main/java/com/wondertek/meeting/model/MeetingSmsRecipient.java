/**
 * 
 */
package com.wondertek.meeting.model;

/**
 * @author rain
 * 
 */
public class MeetingSmsRecipient extends BaseObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 379414870275871645L;
	private Long userId;
	private Long smsId;
	private Integer state;

	/**
	 * @return the userId
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * @return the smsId
	 */
	public Long getSmsId() {
		return smsId;
	}

	/**
	 * @param smsId
	 *            the smsId to set
	 */
	public void setSmsId(Long smsId) {
		this.smsId = smsId;
	}

	/**
	 * @return the state
	 */
	public Integer getState() {
		return state;
	}

	/**
	 * @param state
	 *            the state to set
	 */
	public void setState(Integer state) {
		this.state = state;
	}
}

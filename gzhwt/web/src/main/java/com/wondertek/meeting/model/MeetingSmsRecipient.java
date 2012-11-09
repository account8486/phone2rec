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
	private Long msgId;
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

	/**
	 * @return the msgId
	 */
	public Long getMsgId() {
		return msgId;
	}

	/**
	 * @param msgId the msgId to set
	 */
	public void setMsgId(Long msgId) {
		this.msgId = msgId;
	}
}

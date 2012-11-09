/**
 * 
 */
package com.wondertek.meeting.model;

import java.util.Date;

/**
 * @author rain
 *
 */
public class MessageRecipient {
	private Long id;
	private Date readTime;
	private User recipient;
	private Integer readFlag;
	private Long meetingId;
	private Long messageId;
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the readTime
	 */
	public Date getReadTime() {
		return readTime;
	}
	/**
	 * @param readTime the readTime to set
	 */
	public void setReadTime(Date readTime) {
		this.readTime = readTime;
	}
	/**
	 * @return the recipient
	 */
	public User getRecipient() {
		return recipient;
	}
	/**
	 * @param recipient the recipient to set
	 */
	public void setRecipient(User recipient) {
		this.recipient = recipient;
	}
	/**
	 * @return the readFlag
	 */
	public Integer getReadFlag() {
		return readFlag;
	}
	/**
	 * @param readFlag the readFlag to set
	 */
	public void setReadFlag(Integer readFlag) {
		this.readFlag = readFlag;
	}
	/**
	 * @return the meetingId
	 */
	public Long getMeetingId() {
		return meetingId;
	}
	/**
	 * @param meetingId the meetingId to set
	 */
	public void setMeetingId(Long meetingId) {
		this.meetingId = meetingId;
	}
	/**
	 * @return the messageId
	 */
	public Long getMessageId() {
		return messageId;
	}
	/**
	 * @param messageId the messageId to set
	 */
	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}
}
/**
 * 
 */
package com.wondertek.meeting.model;

import java.util.Date;
import java.util.Set;

/**
 * @author rain
 * 
 */
public class Message extends BaseObject {
	private static final long serialVersionUID = -1036939898434836366L;
	private Long id;
	private String subject;
	private String message;
	private User sender;
	private Date sendTime;
	private Integer sendFlag;
	private Long meetingId;
	private Set<MessageRecipient> recipientSet;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * @param subject
	 *            the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the sender
	 */
	public User getSender() {
		return sender;
	}

	/**
	 * @param sender
	 *            the sender to set
	 */
	public void setSender(User sender) {
		this.sender = sender;
	}

	/**
	 * @return the sendTime
	 */
	public Date getSendTime() {
		return sendTime;
	}

	/**
	 * @param sendTime
	 *            the sendTime to set
	 */
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	/**
	 * @return the sendFlag
	 */
	public Integer getSendFlag() {
		return sendFlag;
	}

	/**
	 * @param sendFlag
	 *            the sendFlag to set
	 */
	public void setSendFlag(Integer sendFlag) {
		this.sendFlag = sendFlag;
	}

	/**
	 * @return the meetingId
	 */
	public Long getMeetingId() {
		return meetingId;
	}

	/**
	 * @param meetingId
	 *            the meetingId to set
	 */
	public void setMeetingId(Long meetingId) {
		this.meetingId = meetingId;
	}

	/**
	 * @return the recipientSet
	 */
	public Set<MessageRecipient> getRecipientSet() {
		return recipientSet;
	}

	/**
	 * @param recipientSet the recipientSet to set
	 */
	public void setRecipientSet(Set<MessageRecipient> recipientSet) {
		this.recipientSet = recipientSet;
	}
}

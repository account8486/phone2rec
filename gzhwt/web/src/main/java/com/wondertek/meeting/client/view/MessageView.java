/**
 * 
 */
package com.wondertek.meeting.client.view;

import java.util.List;

import com.wondertek.meeting.model.JsonUser;

/**
 * @author rain
 * 
 */
public class MessageView {
	private Long id;
	private String message;
	private JsonUser sender;
	private String time;
	/**
	 * 0: 未读; 1:已读; 2:删除;
	 */
	private Integer status;
	private List<JsonUser> recipients; 

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
	 * @return the time
	 */
	public String getTime() {
		return time;
	}

	/**
	 * @param time
	 *            the time to set
	 */
	public void setTime(String time) {
		this.time = time;
	}

	/**
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * @return the recipients
	 */
	public List<JsonUser> getRecipients() {
		return recipients;
	}

	/**
	 * @param recipients the recipients to set
	 */
	public void setRecipients(List<JsonUser> recipients) {
		this.recipients = recipients;
	}

	/**
	 * @return the sender
	 */
	public JsonUser getSender() {
		return sender;
	}

	/**
	 * @param sender the sender to set
	 */
	public void setSender(JsonUser sender) {
		this.sender = sender;
	}
}

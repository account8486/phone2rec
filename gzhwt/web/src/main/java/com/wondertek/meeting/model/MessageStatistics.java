/**
 * 
 */
package com.wondertek.meeting.model;

/**
 * @author rain
 *
 */
public class MessageStatistics  extends BaseObject {
	private static final long serialVersionUID = -7771389418952324894L;
	private Long userId;
	private String userName;
	private String mobile;
	private Long messageCount;
	private Long postCount;
	private Long commentCount;
	private String meetingName;
	private Long meetingId;
	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}
	/**
	 * @param mobile the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	/**
	 * @return the userId
	 */
	public Long getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	/**
	 * @return the messageCount
	 */
	public Long getMessageCount() {
		return messageCount;
	}
	/**
	 * @param messageCount the messageCount to set
	 */
	public void setMessageCount(Long messageCount) {
		this.messageCount = messageCount;
	}
	/**
	 * @return the postCount
	 */
	public Long getPostCount() {
		return postCount;
	}
	/**
	 * @param postCount the postCount to set
	 */
	public void setPostCount(Long postCount) {
		this.postCount = postCount;
	}
	/**
	 * @return the commentCount
	 */
	public Long getCommentCount() {
		return commentCount;
	}
	/**
	 * @param commentCount the commentCount to set
	 */
	public void setCommentCount(Long commentCount) {
		this.commentCount = commentCount;
	}
	/**
	 * @return the meetingName
	 */
	public String getMeetingName() {
		return meetingName;
	}
	/**
	 * @param meetingName the meetingName to set
	 */
	public void setMeetingName(String meetingName) {
		this.meetingName = meetingName;
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
}

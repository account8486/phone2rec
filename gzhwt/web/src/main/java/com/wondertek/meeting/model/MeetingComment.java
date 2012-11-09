package com.wondertek.meeting.model;

import java.util.Date;

public class MeetingComment extends BaseObject {
	private static final long serialVersionUID = 13444068210076062L;
	private Long id;
	private Long postId;
	private Long creator;
	private Integer creatorType;
	private String creatorName;
	private String creatorJob;
	private Date time;
	private String content;
	private User user;

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the postId
	 */
	public Long getPostId() {
		return postId;
	}

	/**
	 * @param postId
	 *            the postId to set
	 */
	public void setPostId(Long postId) {
		this.postId = postId;
	}

	/**
	 * @return the time
	 */
	public Date getTime() {
		return time;
	}

	/**
	 * @param time
	 *            the time to set
	 */
	public void setTime(Date time) {
		this.time = time;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content
	 *            the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the creatorType
	 */
	public Integer getCreatorType() {
		return creatorType;
	}

	/**
	 * @param creatorType
	 *            the creatorType to set
	 */
	public void setCreatorType(Integer creatorType) {
		this.creatorType = creatorType;
	}

	/**
	 * @return the creatorName
	 */
	public String getCreatorName() {
		return creatorName;
	}

	/**
	 * @param creatorName
	 *            the creatorName to set
	 */
	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	/**
	 * @return the creatorJob
	 */
	public String getCreatorJob() {
		return creatorJob;
	}

	/**
	 * @param creatorJob
	 *            the creatorJob to set
	 */
	public void setCreatorJob(String creatorJob) {
		this.creatorJob = creatorJob;
	}

	/**
	 * @param creator
	 *            the creator to set
	 */
	public void setCreator(Long creator) {
		this.creator = creator;
	}

	/**
	 * @return the creator
	 */
	public Long getCreator() {
		return creator;
	}

}

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
public class MeetingPost extends BaseObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4580893828752976733L;

	// primary key
	private Long id;

	// fields
	private Long meetingId;
	private String content;
	private String contentImg;
	private Integer viewCount;
	private Integer commentCount;
	private Date createTime;
	private Date modifyTime;
	private Long posterId;
	private String posterName;
	private String posterJob;
	private Integer posterType;
	private User user;
	private Set<MeetingComment> comments;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
	 * @return the viewCount
	 */
	public Integer getViewCount() {
		return viewCount;
	}

	/**
	 * @param viewCount
	 *            the viewCount to set
	 */
	public void setViewCount(Integer viewCount) {
		this.viewCount = viewCount;
	}

	/**
	 * @return the commentCount
	 */
	public Integer getCommentCount() {
		return commentCount;
	}

	/**
	 * @param commentCount
	 *            the commentCount to set
	 */
	public void setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
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
	 * @return the contentImg
	 */
	public String getContentImg() {
		return contentImg;
	}

	/**
	 * @param contentImg
	 *            the contentImg to set
	 */
	public void setContentImg(String contentImg) {
		this.contentImg = contentImg;
	}

	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime
	 *            the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the modifyTime
	 */
	public Date getModifyTime() {
		return modifyTime;
	}

	/**
	 * @param modifyTime
	 *            the modifyTime to set
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	/**
	 * @return the posterId
	 */
	public Long getPosterId() {
		return posterId;
	}

	/**
	 * @param posterId
	 *            the posterId to set
	 */
	public void setPosterId(Long posterId) {
		this.posterId = posterId;
	}

	/**
	 * @return the posterName
	 */
	public String getPosterName() {
		return posterName;
	}

	/**
	 * @param posterName
	 *            the posterName to set
	 */
	public void setPosterName(String posterName) {
		this.posterName = posterName;
	}

	/**
	 * @return the posterJob
	 */
	public String getPosterJob() {
		return posterJob;
	}

	/**
	 * @param posterJob
	 *            the posterJob to set
	 */
	public void setPosterJob(String posterJob) {
		this.posterJob = posterJob;
	}

	/**
	 * @return the posterType
	 */
	public Integer getPosterType() {
		return posterType;
	}

	/**
	 * @param posterType
	 *            the posterType to set
	 */
	public void setPosterType(Integer posterType) {
		this.posterType = posterType;
	}

	/**
	 * @return the meetingUser
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param meetingUser the meetingUser to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return the comments
	 */
	public Set<MeetingComment> getComments() {
		return comments;
	}

	/**
	 * @param comments the comments to set
	 */
	public void setComments(Set<MeetingComment> comments) {
		this.comments = comments;
	}
}

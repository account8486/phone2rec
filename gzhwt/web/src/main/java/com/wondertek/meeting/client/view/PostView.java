/**
 * 
 */
package com.wondertek.meeting.client.view;

import java.util.List;


/**
 * @author rain
 * 
 */
public class PostView {
	private Long id;
	private Long meetingId;
	private String content;
	private String contentImg;
	private String videourl;
	private Integer viewCount;
	private Integer commentCount;
	private String time;
	private String name;
	private String job;
	private List<CommentView> comments;

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
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the job
	 */
	public String getJob() {
		return job;
	}

	/**
	 * @param job
	 *            the job to set
	 */
	public void setJob(String job) {
		this.job = job;
	}

	/**
	 * @return the comments
	 */
	public List<CommentView> getComments() {
		return comments;
	}

	/**
	 * @param comments the comments to set
	 */
	public void setComments(List<CommentView> comments) {
		this.comments = comments;
	}

	/**
	 * @return the videourl
	 */
	public String getVideourl() {
		return videourl;
	}

	/**
	 * @param videourl the videourl to set
	 */
	public void setVideourl(String videourl) {
		this.videourl = videourl;
	}

}

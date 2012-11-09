/**
 * 新闻类
 * Author: 张国敬
 * Copyrights: 版权归上海网达软件有限公司安徽分公司所有
 * Create at: 2012-3-6
 */
package com.wondertek.meeting.model;

import java.util.Date;

public class News extends BaseObject {
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String title; // 新闻标题
	private String content; // 新闻正文
	private String source; // 来源
	private Integer imageNews; // 是否图片新闻：1-是，0-否
	private String thumbnailImage; // 图片缩略图
	private Integer hitCount; // 点击数
	private Date createTime; // 创建时间
	private Date lastModifyTime; // 最后修改时间
	private Integer state; // 有关状态，1-有效，0-无效

//	private NewsKind newsKind; // 新闻类别 //暂时不需要新闻类别
	private Meeting meeting; //所属会议
	private AdminUser creator; // 新闻创建人

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Integer getImageNews() {
		return imageNews;
	}

	public void setImageNews(Integer imageNews) {
		this.imageNews = imageNews;
	}

	public String getThumbnailImage() {
		return thumbnailImage;
	}

	public void setThumbnailImage(String thumbnailImage) {
		this.thumbnailImage = thumbnailImage;
	}

	public Integer getHitCount() {
		return hitCount;
	}

	public void setHitCount(Integer hitCount) {
		this.hitCount = hitCount;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public AdminUser getCreator() {
		return creator;
	}

	public void setCreator(AdminUser creator) {
		this.creator = creator;
	}

	public Meeting getMeeting() {
		return meeting;
	}

	public void setMeeting(Meeting meeting) {
		this.meeting = meeting;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}

/**
 * 页面风格主题配置信息
 * Author: 张国敬
 * Copyrights: 版权归上海网达软件有限公司安徽分公司所有
 * Create at: 2011-12-29
 */
package com.wondertek.meeting.model.custom;

import com.wondertek.meeting.model.BaseObject;

public class PageTheme extends BaseObject {
	private Long id;
	private String name;	//主题名称
	private String title;	//主题显示标题
	private String description;	//主题描述信息
	private String thumbnailName;	//主题缩略图文件名
	private Integer state;	//状态，0-不可用，1-可用

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getThumbnailName() {
		return thumbnailName;
	}

	public void setThumbnailName(String thumbnailName) {
		this.thumbnailName = thumbnailName;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}
}

/**
 * 会议云类型
 * Author: 张国敬
 * Copyrights: 版权归上海网达软件有限公司安徽分公司所有
 * Create at: Jan 13, 2012
 */
package com.wondertek.meeting.model.custom;

import java.util.Date;

import com.wondertek.meeting.model.BaseObject;

public class MeetingType extends BaseObject {
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String name; //类型名称
	private String description; //类型描述
	private String logoImage; //类型Logo图片
	private Date createTime; //创建时间
	private Integer state; //有效状态 
	
	private PageTheme pageTheme; //使用页面主题

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLogoImage() {
		return logoImage;
	}

	public void setLogoImage(String logoImage) {
		this.logoImage = logoImage;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public PageTheme getPageTheme() {
		return pageTheme;
	}

	public void setPageTheme(PageTheme pageTheme) {
		this.pageTheme = pageTheme;
	}
}

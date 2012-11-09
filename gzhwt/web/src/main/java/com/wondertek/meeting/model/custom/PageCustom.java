/**
 * 页面信息定制内容
 * Author: 张国敬
 * Copyrights: 版权归上海网达软件有限公司安徽分公司所有
 * Create at: 2011-12-29
 */
package com.wondertek.meeting.model.custom;

import com.wondertek.meeting.model.BaseObject;
import com.wondertek.meeting.model.Meeting;

public class PageCustom extends BaseObject {
	private Long id;
	private String propertyName; // 页面信息属性名
	private String propertyValue; // 页面信息属性值
	private String description;	//描述信息
	
	private Meeting meeting;	//所属会议
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public String getPropertyValue() {
		return propertyValue;
	}

	public void setPropertyValue(String propertyValue) {
		this.propertyValue = propertyValue;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Meeting getMeeting() {
		return meeting;
	}

	public void setMeeting(Meeting meeting) {
		this.meeting = meeting;
	}


}

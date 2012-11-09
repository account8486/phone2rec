/**
 * 全局范围内本地土特产信息，此信息由会议特产MeetingSpecialty来组织
 * Author: 张国敬
 * Copyrights: 版权归上海网达软件有限公司安徽分公司所有
 * Create at: 2012-2-17
 */
package com.wondertek.meeting.model.reception;

import java.util.Set;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class LocalSpecialty {
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String name; //土特产名称
	private String memo; // 简要描述信息
	private String image; //对应的图片文件
	private String province; // 省代码
	private String city; // 市代码
	
	private Set<MeetingSpecialty> meetingSpecialtySet;
	
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	public Set<MeetingSpecialty> getMeetingSpecialtySet() {
		return meetingSpecialtySet;
	}

	public void setMeetingSpecialtySet(Set<MeetingSpecialty> meetingSpecialtySet) {
		this.meetingSpecialtySet = meetingSpecialtySet;
	}
}

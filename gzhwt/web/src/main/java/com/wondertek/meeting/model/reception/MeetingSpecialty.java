/**
 * 指定会议下的本地土特产信息
 * Author: 张国敬
 * Copyrights: 版权归上海网达软件有限公司安徽分公司所有
 * Create at: 2012-2-17
 */
package com.wondertek.meeting.model.reception;

import java.util.Date;
import java.util.Set;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.wondertek.meeting.model.BaseObject;
import com.wondertek.meeting.model.Meeting;

public class MeetingSpecialty {
	private static final long serialVersionUID = 1L;

	private Integer id;
	private Meeting meeting; // 会议
	private String memo; // 简要描述信息
	private Date createTime; // 创建时间
	private String province; // 省代码
	private String city; // 市代码
	private Integer state; // 有效状态：1-有效，0-无效
	
	private Set<LocalSpecialty> localSpecialtySet;  //只用来配置映射，不在程序直接使用

	public Meeting getMeeting() {
		return meeting;
	}

	public void setMeeting(Meeting meeting) {
		this.meeting = meeting;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public void setLocalSpecialtySet(Set<LocalSpecialty> localSpecialtySet) {
		this.localSpecialtySet = localSpecialtySet;
	}

	public Set<LocalSpecialty> getLocalSpecialtySet() {
		return localSpecialtySet;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
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
}

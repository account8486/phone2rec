/**
 * 酒店信息 Author: 张国敬 Copyrights: 版权归上海网达软件有限公司安徽分公司所有 Create at: 2012-2-17
 */
package com.wondertek.meeting.model.reception;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.wondertek.meeting.model.Meeting;

/**
 * 酒店
 * 
 * @author 金祝华
 */
public class Hotel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 696576305650529810L;

	private Long id;

	private String name; // 名称

	private String address; // 地址

	private String baseInfo; // 基本信息

	private String introduction; // 酒店简介

	private String peripheralInfo;// 周边信息

	private String additionalInfo;// 附加信息

	private String linking; // 联系方式

	private String service;// 服务项目

	private String facilities;// 客房设施

	private String province; // 省代码

	private String city; // 市代码

	private Integer state;// 状态 0：无效，1：有效
	private Date createTime;// 创建时间
	private Date modifyTime;// 修改时间

	private Set<Meeting> meetings; // 会议

	private List<HotelImage> images; // 酒店图片

	private Set<HotelRoomType> hotelRoomTypes; // 酒店房间类型

	public Set<HotelRoomType> getHotelRoomTypes() {
		return hotelRoomTypes;
	}

	public void setHotelRoomTypes(Set<HotelRoomType> hotelRoomTypes) {
		this.hotelRoomTypes = hotelRoomTypes;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getBaseInfo() {
		return baseInfo;
	}

	public void setBaseInfo(String baseInfo) {
		this.baseInfo = baseInfo;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getPeripheralInfo() {
		return peripheralInfo;
	}

	public void setPeripheralInfo(String peripheralInfo) {
		this.peripheralInfo = peripheralInfo;
	}

	public String getAdditionalInfo() {
		return additionalInfo;
	}

	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}

	public String getLinking() {
		return linking;
	}

	public void setLinking(String linking) {
		this.linking = linking;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getFacilities() {
		return facilities;
	}

	public void setFacilities(String facilities) {
		this.facilities = facilities;
	}

	public Set<Meeting> getMeetings() {
		return meetings;
	}

	public void setMeetings(Set<Meeting> meetings) {
		this.meetings = meetings;
	}

	public List<HotelImage> getImages() {
		return images;
	}

	public void setImages(List<HotelImage> images) {
		this.images = images;
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

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}

package com.wondertek.meeting.model.reception;

import java.util.Date;
import java.util.Set;

import com.wondertek.meeting.model.BaseObject;
import com.wondertek.meeting.model.Meeting;

/**
 * 客房房型
 * 
 * @author 金祝华
 */
public class HotelRoomType extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3848475967044097466L;

	private Long id;

	private String name;// 房型名称

	private Float area;// 面积

	private Integer capability;// 可住人数

	private Float bedWidth;// 床宽

	private String additionalInfo;// 附加信息

	private String facilities;// 客房设施

	private Integer state;// 状态 0：无效，1：有效
	private Date createTime;// 创建时间
	private Date modifyTime;// 修改时间

	private Hotel hotel;// 酒店

	private Set<Meeting> meetings; // 会议

	public Hotel getHotel() {
		return hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}

	public Set<Meeting> getMeetings() {
		return meetings;
	}

	public void setMeetings(Set<Meeting> meetings) {
		this.meetings = meetings;
	}

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

	public Float getArea() {
		return area;
	}

	public void setArea(Float area) {
		this.area = area;
	}

	public Integer getCapability() {
		return capability;
	}

	public void setCapability(Integer capability) {
		this.capability = capability;
	}

	public Float getBedWidth() {
		return bedWidth;
	}

	public void setBedWidth(Float bedWidth) {
		this.bedWidth = bedWidth;
	}

	public String getAdditionalInfo() {
		return additionalInfo;
	}

	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}

	public String getFacilities() {
		return facilities;
	}

	public void setFacilities(String facilities) {
		this.facilities = facilities;
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
}

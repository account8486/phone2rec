/**
 * 餐厅信息
 * Author: 张国敬
 * Copyrights: 版权归上海网达软件有限公司安徽分公司所有
 * Create at: 2012-2-17
 */
package com.wondertek.meeting.model.reception;

import java.util.Date;
import java.util.Set;

import com.wondertek.meeting.model.BaseObject;
import com.wondertek.meeting.model.Meeting;

/**
 * 餐厅
 * 
 * @author 金祝华
 */
public class DiningRoom extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7031456951437247570L;

	private Long id;

	private String name; // 餐厅名称

	private String businessHours;// 营业时间

	private Integer tableCnt;// 餐桌个数

	private Integer capability;// 最大就餐人数

	private String floor;// 楼层

	private String additionalInfo;// 附加信息

	private Integer state;// 状态 0：无效，1：有效
	private Date createTime;// 创建时间
	private Date modifyTime;// 修改时间

	private Hotel hotel;// 酒店

	private Set<Meeting> meetings; // 会议

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

	public String getBusinessHours() {
		return businessHours;
	}

	public void setBusinessHours(String businessHours) {
		this.businessHours = businessHours;
	}

	public Integer getTableCnt() {
		return tableCnt;
	}

	public void setTableCnt(Integer tableCnt) {
		this.tableCnt = tableCnt;
	}

	public Integer getCapability() {
		return capability;
	}

	public void setCapability(Integer capability) {
		this.capability = capability;
	}

	public String getAdditionalInfo() {
		return additionalInfo;
	}

	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}

	public String getFloor() {
		return floor;
	}

	public void setFloor(String floor) {
		this.floor = floor;
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
}

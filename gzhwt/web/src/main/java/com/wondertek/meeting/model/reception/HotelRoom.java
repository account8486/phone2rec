package com.wondertek.meeting.model.reception;

import java.util.Date;
import java.util.Set;

import com.wondertek.meeting.model.BaseObject;
import com.wondertek.meeting.model.Meeting;
import com.wondertek.meeting.model.User;

/**
 * 酒店客房
 * 
 * @author 金祝华
 */
public class HotelRoom extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4077480829888292839L;

	private Long id;

	private String roomNo;// 房间号

	private Integer floor;// 楼层

	private Integer state;// 状态 0：无效，1：有效
	private Date createTime;// 创建时间
	private Date modifyTime;// 修改时间

	private HotelRoomType hotelRoomType; // 类型

	private Meeting meeting; // 会议

	private Set<User> users; // 住客

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getFloor() {
		return floor;
	}

	public void setFloor(Integer floor) {
		this.floor = floor;
	}

	public String getRoomNo() {
		return roomNo;
	}

	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
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

	public HotelRoomType getHotelRoomType() {
		return hotelRoomType;
	}

	public void setHotelRoomType(HotelRoomType hotelRoomType) {
		this.hotelRoomType = hotelRoomType;
	}

	public Meeting getMeeting() {
		return meeting;
	}

	public void setMeeting(Meeting meeting) {
		this.meeting = meeting;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}
}

package com.wondertek.meeting.dao.reception;

import java.util.List;

import com.wondertek.meeting.dao.BaseDao;
import com.wondertek.meeting.model.reception.Hotel;

/**
 * 酒店管理
 * 
 * @author 金祝华
 */
public interface HotelDao extends BaseDao<Hotel, Long> {
	/**
	 * 查询酒店列表
	 * 
	 * @param example
	 * @return
	 */
	public List<Hotel> findHotelListByName(final Hotel example);

	/**
	 * 查询会议下酒店列表
	 * 
	 * @param meetingId
	 * @return
	 */
	public List<Hotel> findHotelListByMeeting(Long meetingId);

	public Hotel findById(Long id);
}

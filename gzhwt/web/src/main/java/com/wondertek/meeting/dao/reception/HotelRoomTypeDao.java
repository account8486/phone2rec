package com.wondertek.meeting.dao.reception;

import java.util.List;

import com.wondertek.meeting.dao.BaseDao;
import com.wondertek.meeting.model.reception.Hotel;
import com.wondertek.meeting.model.reception.HotelRoomType;

/**
 * 客房房型管理
 * 
 * @author 金祝华
 */
public interface HotelRoomTypeDao extends BaseDao<HotelRoomType, Long> {
	/**
	 * 查询列表
	 * 
	 * @param example
	 * @return
	 */
	public List<HotelRoomType> findListByName(final HotelRoomType example);

	/**
	 * findById
	 * 
	 * @param id
	 * @return
	 */
	public HotelRoomType findById(Long id);
	
	/**
	 * 查询会议下客房房型列表
	 * 
	 * @param meetingId
	 * @return
	 */
	public List<HotelRoomType> findListByMeetingId(Long meetingId);
}

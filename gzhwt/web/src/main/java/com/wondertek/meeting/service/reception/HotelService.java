package com.wondertek.meeting.service.reception;

import java.util.List;

import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.reception.Hotel;
import com.wondertek.meeting.service.BaseService;

/**
 * 酒店管理
 * 
 * @author 金祝华
 */
public interface HotelService extends BaseService<Hotel, Long> {

	/**
	 * 查询会议下酒店列表
	 * 
	 * @param meetingId
	 * @param currentPage
	 * @param pageSize
	 * @return
	 * @throws ServiceException
	 */
	public Pager<Hotel> findPager(Hotel hotel, Long meetingId, int currentPage, int pageSize) throws ServiceException;

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

	public Hotel findById(Long id) throws ServiceException;
}

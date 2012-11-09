package com.wondertek.meeting.service.reception;

import java.util.List;

import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.reception.Hotel;
import com.wondertek.meeting.model.reception.HotelRoomType;
import com.wondertek.meeting.service.BaseService;

/**
 * 客房房型管理
 * 
 * @author 金祝华
 */
public interface HotelRoomTypeService extends BaseService<HotelRoomType, Long> {

	/**
	 * 查询会议下客房房型列表
	 * 
	 * @param meetingId
	 * @param currentPage
	 * @param pageSize
	 * @return
	 * @throws ServiceException
	 */
	public Pager<HotelRoomType> findPager(HotelRoomType hotelRoomType, Long meetingId, int currentPage, int pageSize)
			throws ServiceException;

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

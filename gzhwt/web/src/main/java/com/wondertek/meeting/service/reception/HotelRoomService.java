package com.wondertek.meeting.service.reception;

import java.util.List;

import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.reception.HotelRoom;
import com.wondertek.meeting.service.BaseService;

/**
 * 酒店客房管理
 * 
 * @author 金祝华
 */
public interface HotelRoomService extends BaseService<HotelRoom, Long> {

	/**
	 * 查询会议下客房列表
	 * 
	 * @param meetingId
	 * @param currentPage
	 * @param pageSize
	 * @return
	 * @throws ServiceException
	 */
	public Pager<HotelRoom> findPager(HotelRoom hotelRoom, Long meetingId, int currentPage, int pageSize)
			throws ServiceException;

	/**
	 * 查询该会议下已经分配了客房的参会人员列表
	 * 
	 * @param meetingId
	 * @return
	 */
	public List<Long> queryAssignedUserList(Long meetingId);

	/**
	 * 查询会议下所有客房
	 * 
	 * @param meetingId
	 * @return
	 */
	public List<HotelRoom> list(Long meetingId);

	public HotelRoom findById(Long roomId) throws ServiceException;

	/**
	 * 删除客房
	 * 
	 * @param meetingId
	 * @param roomId
	 * @return 0:失败，1：成功
	 */
	public String delete(Long meetingId, Long roomId) throws ServiceException;

	/**
	 * 将参会人员加入房间
	 * 
	 * @param fromRoomId
	 * @param toRoomId
	 * @param userId
	 * @return
	 * @throws ServiceException
	 */
	public String addUserToRoom(Long fromRoomId, Long toRoomId, Long userId) throws ServiceException;

	/**
	 * 将住客移出房间
	 * 
	 * @param userId
	 * @param roomId
	 * @return
	 */
	public String removeUserFromRoom(Long userId, Long fromRoomId) throws ServiceException;

	/**
	 * 智能分配客房<br/>
	 * 
	 * 客房分配只支持单人间和双人间。<br/>
	 * 1、对于VIP，优先分配单人间，如果没有单人间房型，则分配一个双人间（单人住）。<br/>
	 * 2、对于非VIP，如果有双人间房型，按照相同性别两人一间双人间，最后落单的一人住一间双人间，否则都分配单人间。
	 * 
	 * @return
	 */
	public String assignRooms(Long meetingId) throws ServiceException;
}

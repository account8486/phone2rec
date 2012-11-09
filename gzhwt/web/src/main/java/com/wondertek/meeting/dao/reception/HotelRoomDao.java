package com.wondertek.meeting.dao.reception;

import java.util.List;

import com.wondertek.meeting.dao.BaseDao;
import com.wondertek.meeting.model.reception.HotelRoom;

/**
 * 酒店客房管理
 * 
 * @author 金祝华
 */
public interface HotelRoomDao extends BaseDao<HotelRoom, Long> {

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

	public HotelRoom findById(Long roomId);
}

package com.wondertek.meeting.service.reception;

import java.util.List;

import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.reception.DiningRoom;
import com.wondertek.meeting.service.BaseService;

/**
 * 餐厅管理
 * 
 * @author 金祝华
 */
public interface DiningRoomService extends BaseService<DiningRoom, Long> {

	/**
	 * 查询会议下餐厅列表
	 * 
	 * @param meetingId
	 * @param currentPage
	 * @param pageSize
	 * @return
	 * @throws ServiceException
	 */
	public Pager<DiningRoom> findPager(DiningRoom diningRoom, Long meetingId, int currentPage, int pageSize)
			throws ServiceException;

	/**
	 * 查询列表
	 * 
	 * @param example
	 * @return
	 */
	public List<DiningRoom> findListByName(final DiningRoom example);

	/**
	 * findById
	 * 
	 * @param id
	 * @return
	 */
	public DiningRoom findById(Long id);
}

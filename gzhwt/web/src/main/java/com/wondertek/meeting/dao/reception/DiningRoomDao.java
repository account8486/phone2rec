package com.wondertek.meeting.dao.reception;

import java.util.List;

import com.wondertek.meeting.dao.BaseDao;
import com.wondertek.meeting.model.reception.DiningRoom;

/**
 * 餐厅管理
 * 
 * @author 金祝华
 */
public interface DiningRoomDao extends BaseDao<DiningRoom, Long> {
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

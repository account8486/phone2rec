package com.wondertek.meeting.service.impl.reception;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.dao.reception.DiningRoomDao;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.reception.DiningRoom;
import com.wondertek.meeting.service.impl.BaseServiceImpl;
import com.wondertek.meeting.service.reception.DiningRoomService;
import com.wondertek.meeting.util.StringUtil;

/**
 * 餐厅管理
 * 
 * @author 金祝华
 */
public class DiningRoomServiceImpl extends BaseServiceImpl<DiningRoom, Long> implements DiningRoomService {

	private DiningRoomDao diningRoomDao;

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
			throws ServiceException {

		StringBuilder hql = new StringBuilder();
		hql.append("select h from DiningRoom h inner join h.meetings m where m.id = :meetingId");

		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("meetingId", meetingId);

		if (diningRoom != null && StringUtil.isNotEmpty(diningRoom.getName())) {
			hql.append(" and h.name like '%'||:name||'%'");
			properties.put("name", diningRoom.getName());
		}

		try {
			return diningRoomDao.findPager(hql.toString(), currentPage, pageSize, properties);
		} catch (Exception e) {
			final String errMsg = "Find pager error!";
			log.error(errMsg, e);
			throw new ServiceException(errMsg, e);
		}
	}

	/**
	 * 查询列表
	 * 
	 * @param example
	 * @return
	 */
	public List<DiningRoom> findListByName(final DiningRoom example) {
		return diningRoomDao.findListByName(example);
	}

	/**
	 * findById
	 * 
	 * @param id
	 * @return
	 */
	public DiningRoom findById(Long id) {
		return diningRoomDao.findById(id);
	}

	public DiningRoomDao getDiningRoomDao() {
		return diningRoomDao;
	}

	public void setDiningRoomDao(DiningRoomDao diningRoomDao) {
		super.setBaseDao(diningRoomDao);
		this.diningRoomDao = diningRoomDao;
	}
}

package com.wondertek.meeting.service.impl.reception;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.dao.reception.HotelRoomTypeDao;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.reception.Hotel;
import com.wondertek.meeting.model.reception.HotelRoomType;
import com.wondertek.meeting.service.impl.BaseServiceImpl;
import com.wondertek.meeting.service.reception.HotelRoomTypeService;
import com.wondertek.meeting.util.StringUtil;

/**
 * 客房房型管理
 * 
 * @author 金祝华
 */
public class HotelRoomTypeServiceImpl extends BaseServiceImpl<HotelRoomType, Long> implements HotelRoomTypeService {

	private HotelRoomTypeDao hotelRoomTypeDao;

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
			throws ServiceException {

		StringBuilder hql = new StringBuilder();
		hql.append("select h from HotelRoomType h inner join h.meetings m where m.id = :meetingId");

		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("meetingId", meetingId);

		if (hotelRoomType != null && StringUtil.isNotEmpty(hotelRoomType.getName())) {
			hql.append(" and h.name like '%'||:name||'%'");
			properties.put("name", hotelRoomType.getName());
		}

		try {
			return hotelRoomTypeDao.findPager(hql.toString(), currentPage, pageSize, properties);
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
	public List<HotelRoomType> findListByName(final HotelRoomType example) {
		return hotelRoomTypeDao.findListByName(example);
	}

	/**
	 * findById
	 * 
	 * @param id
	 * @return
	 */
	public HotelRoomType findById(Long id) {
		return hotelRoomTypeDao.findById(id);
	}

	/**
	 * 查询会议下客房房型列表
	 * 
	 * @param meetingId
	 * @return
	 */
	public List<HotelRoomType> findListByMeetingId(Long meetingId) {
		return hotelRoomTypeDao.findListByMeetingId(meetingId);
	}

	public HotelRoomTypeDao getHotelRoomTypeDao() {
		return hotelRoomTypeDao;
	}

	public void setHotelRoomTypeDao(HotelRoomTypeDao hotelRoomTypeDao) {
		super.setBaseDao(hotelRoomTypeDao);
		this.hotelRoomTypeDao = hotelRoomTypeDao;
	}
}

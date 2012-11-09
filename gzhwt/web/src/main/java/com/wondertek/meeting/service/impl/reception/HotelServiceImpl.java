package com.wondertek.meeting.service.impl.reception;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.dao.reception.HotelDao;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.reception.Hotel;
import com.wondertek.meeting.service.impl.BaseServiceImpl;
import com.wondertek.meeting.service.reception.HotelService;
import com.wondertek.meeting.util.StringUtil;

/**
 * 酒店管理
 * 
 * @author 金祝华
 */
public class HotelServiceImpl extends BaseServiceImpl<Hotel, Long> implements HotelService {

	private HotelDao hotelDao;

	/**
	 * 查询会议下酒店列表
	 * 
	 * @param meetingId
	 * @param currentPage
	 * @param pageSize
	 * @return
	 * @throws ServiceException
	 */
	public Pager<Hotel> findPager(Hotel hotel, Long meetingId, int currentPage, int pageSize) throws ServiceException {

		StringBuilder hql = new StringBuilder();
		hql.append("select h from Hotel h inner join h.meetings m where m.id = :meetingId");

		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("meetingId", meetingId);

		if (hotel != null && StringUtil.isNotEmpty(hotel.getName())) {
			hql.append(" and h.name like '%'||:name||'%'");
			properties.put("name", hotel.getName());
		}

		try {
			return hotelDao.findPager(hql.toString(), currentPage, pageSize, properties);
		} catch (Exception e) {
			final String errMsg = "Find pager error!";
			log.error(errMsg, e);
			throw new ServiceException(errMsg, e);
		}
	}

	/**
	 * 查询酒店列表
	 * 
	 * @param example
	 * @return
	 */
	public List<Hotel> findHotelListByName(final Hotel example) {
		return hotelDao.findHotelListByName(example);
	}

	/**
	 * 查询会议下酒店列表
	 * 
	 * @param meetingId
	 * @return
	 */
	public List<Hotel> findHotelListByMeeting(Long meetingId) {
		return hotelDao.findHotelListByMeeting(meetingId);
	}

	public Hotel findById(Long id) {
		return hotelDao.findById(id);
	}

	public HotelDao getHotelDao() {
		return hotelDao;
	}

	public void setHotelDao(HotelDao hotelDao) {
		super.setBaseDao(hotelDao);
		this.hotelDao = hotelDao;
	}
}

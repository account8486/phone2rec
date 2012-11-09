package com.wondertek.meeting.dao.impl.reception;

import java.util.List;

import com.wondertek.meeting.dao.impl.BaseDaoImpl;
import com.wondertek.meeting.dao.reception.HotelRoomTypeDao;
import com.wondertek.meeting.model.reception.Hotel;
import com.wondertek.meeting.model.reception.HotelRoomType;

/**
 * 客房房型管理
 * 
 * @author 金祝华
 */
public class HotelRoomTypeDaoImpl extends BaseDaoImpl<HotelRoomType, Long> implements HotelRoomTypeDao {

	/**
	 * 根据名称模糊查询列表
	 * 
	 * @param example
	 * @return
	 */
	public List<HotelRoomType> findListByName(final HotelRoomType example) {
		final String hql = "select id, name from HotelRoomType r where r.name like :name "
				+ "and r.hotel.id=:hotelId order by r.id desc";

		@SuppressWarnings("unchecked")
		List<HotelRoomType> list = this.getHibernateTemplate().findByNamedParam(hql,
				new String[] { "name", "hotelId" },
				new Object[] { "%" + example.getName() + "%", example.getHotel().getId() });

		return list;
	}

	/**
	 * findById
	 * 
	 * @param id
	 * @return
	 */
	public HotelRoomType findById(Long id) {
		String hql = "from HotelRoomType r left outer join fetch r.meetings where r.id = ?";

		@SuppressWarnings("unchecked")
		List<HotelRoomType> list = this.getHibernateTemplate().find(hql, id);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * 查询会议下客房房型列表
	 * 
	 * @param meetingId
	 * @return
	 */
	public List<HotelRoomType> findListByMeetingId(Long meetingId) {
		final String hql = "select h from HotelRoomType h inner join h.meetings m where m.id = :meetingId order by h.id desc";

		@SuppressWarnings("unchecked")
		List<HotelRoomType> list = this.getHibernateTemplate().findByNamedParam(hql, "meetingId", meetingId);

		return list;
	}
}

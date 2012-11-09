package com.wondertek.meeting.dao.impl.reception;

import java.util.List;

import com.wondertek.meeting.dao.impl.BaseDaoImpl;
import com.wondertek.meeting.dao.reception.HotelDao;
import com.wondertek.meeting.model.reception.Hotel;

/**
 * 酒店
 * 
 * @author 金祝华
 */
public class HotelDaoImpl extends BaseDaoImpl<Hotel, Long> implements HotelDao {

	/**
	 * 查询酒店列表
	 * 
	 * @param example
	 * @return
	 */
	public List<Hotel> findHotelListByName(final Hotel example) {
		final String hql = "select id, name from Hotel h where h.name like :name and h.province=:province "
				+ "and h.city=:city order by h.id desc";

		@SuppressWarnings("unchecked")
		List<Hotel> list = this.getHibernateTemplate().findByNamedParam(hql,
				new String[] { "name", "province", "city" },
				new Object[] { "%" + example.getName() + "%", example.getProvince(), example.getCity() });

		return list;
	}

	/**
	 * 查询会议下酒店列表
	 * 
	 * @param meetingId
	 * @return
	 */
	public List<Hotel> findHotelListByMeeting(Long meetingId) {
		final String hql = "select h from Hotel h inner join h.meetings m where m.id = :meetingId order by h.id desc";

		@SuppressWarnings("unchecked")
		List<Hotel> list = this.getHibernateTemplate().findByNamedParam(hql, "meetingId", meetingId);

		return list;
	}

	public Hotel findById(Long id) {
		String hql = "from Hotel h left outer join fetch h.meetings where h.id = ?";

		@SuppressWarnings("unchecked")
		List<Hotel> list = this.getHibernateTemplate().find(hql, id);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}

		return null;
	}
}

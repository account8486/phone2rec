package com.wondertek.meeting.dao.impl.reception;

import java.util.List;

import com.wondertek.meeting.dao.impl.BaseDaoImpl;
import com.wondertek.meeting.dao.reception.DiningRoomDao;
import com.wondertek.meeting.model.reception.DiningRoom;

/**
 * 餐厅管理
 * 
 * @author 金祝华
 */
public class DiningRoomDaoImpl extends BaseDaoImpl<DiningRoom, Long> implements DiningRoomDao {
	/**
	 * 查询列表
	 * 
	 * @param example
	 * @return
	 */
	public List<DiningRoom> findListByName(final DiningRoom example) {
		final String hql = "select id, name from DiningRoom r where r.name like :name "
				+ "and r.hotel.id=:hotelId order by r.id desc";

		@SuppressWarnings("unchecked")
		List<DiningRoom> list = this.getHibernateTemplate().findByNamedParam(hql, new String[] { "name", "hotelId" },
				new Object[] { "%" + example.getName() + "%", example.getHotel().getId() });

		return list;
	}

	/**
	 * findById
	 * 
	 * @param id
	 * @return
	 */
	public DiningRoom findById(Long id) {
		String hql = "from DiningRoom r left outer join fetch r.meetings where r.id = ?";

		@SuppressWarnings("unchecked")
		List<DiningRoom> list = this.getHibernateTemplate().find(hql, id);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}

		return null;
	}
}

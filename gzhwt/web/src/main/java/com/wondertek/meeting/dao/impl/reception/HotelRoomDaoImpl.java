package com.wondertek.meeting.dao.impl.reception;

import java.util.ArrayList;
import java.util.List;

import com.wondertek.meeting.dao.impl.BaseDaoImpl;
import com.wondertek.meeting.dao.reception.HotelRoomDao;
import com.wondertek.meeting.model.reception.HotelRoom;

/**
 * 酒店客房管理
 * 
 * @author 金祝华
 */
public class HotelRoomDaoImpl extends BaseDaoImpl<HotelRoom, Long> implements HotelRoomDao {

	/**
	 * 删除会议下所有客房及其分配的用户 TODO:jin暂时未用到，尚未完成。
	 * 
	 * @param meetingId
	 */
	public void batchDeleteByMeetingId(Long meetingId) {
		StringBuilder sb = new StringBuilder();
		sb.append("select id from hotel_room where meeting_id = ");
		sb.append(meetingId);

		List<Integer> hotelRoomIdList = this.getHibernateTemplate().getSessionFactory().getCurrentSession()
				.createSQLQuery(sb.toString()).list();

		// 删除客房
		sb.delete(0, sb.length());
		sb.append("update hotel_room set state = 0 where meeting_id = ");
		sb.append(meetingId);
		int cnt = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sb.toString())
				.executeUpdate();

		// 删除客房用户关联
		sb.delete(0, sb.length());
		sb.append("delete from hotel_room_user where hotel_room_id in ( ");
		sb.append(hotelRoomIdList.toArray());
		sb.append(")");

		cnt = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sb.toString())
				.executeUpdate();

		return;
	}

	/**
	 * 查询该会议下已经分配了客房的参会人员列表
	 * 
	 * @param meetingId
	 * @return
	 */
	public List<Long> queryAssignedUserList(Long meetingId) {
		StringBuilder sb = new StringBuilder();
		sb.append("select id from hotel_room where meeting_id = ");
		sb.append(meetingId);

		List<Integer> hotelRoomIdList = this.getHibernateTemplate().getSessionFactory().getCurrentSession()
				.createSQLQuery(sb.toString()).list();

		if (hotelRoomIdList == null || hotelRoomIdList.size() == 0) {
			return null;
		}

		// 删除客房用户关联
		sb.delete(0, sb.length());
		sb.append("select distinct user_id from hotel_room_user where hotel_room_id in ( ");
		String array = hotelRoomIdList.toString();
		array = array.substring(1, array.length() - 1);
		sb.append(array);
		sb.append(")");

		List<Integer> assignedUserList = this.getHibernateTemplate().getSessionFactory().getCurrentSession()
				.createSQLQuery(sb.toString()).list();

		if (assignedUserList != null && assignedUserList.size() > 0) {
			List<Long> retList = new ArrayList<Long>();
			for (Integer userId : assignedUserList) {
				retList.add(new Long(userId));
			}
			return retList;
		}

		return null;
	}

	/**
	 * 查询会议下所有客房
	 * 
	 * @param meetingId
	 * @return
	 */
	public List<HotelRoom> list(Long meetingId) {
		String hql = "select distinct h from HotelRoom h left outer join fetch h.users where h.meeting.id = :meetingId and h.state != 0";
		@SuppressWarnings("unchecked")
		List<HotelRoom> list = this.getHibernateTemplate().findByNamedParam(hql, "meetingId", meetingId);
		return list;
	}

	public HotelRoom findById(Long roomId) {
		String hql = "select distinct h from HotelRoom h left outer join fetch h.users where h.id = :roomId and h.state != 0";
		@SuppressWarnings("unchecked")
		List<HotelRoom> list = this.getHibernateTemplate().findByNamedParam(hql, "roomId", roomId);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
}

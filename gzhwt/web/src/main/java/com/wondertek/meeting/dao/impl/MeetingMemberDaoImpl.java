package com.wondertek.meeting.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wondertek.meeting.dao.MeetingMemberDao;
import com.wondertek.meeting.exception.HibernateDaoSupportException;
import com.wondertek.meeting.model.MeetingMember;
import com.wondertek.meeting.model.User;

/**
 * 会议成员
 * 
 * @author 金祝华
 */
public class MeetingMemberDaoImpl extends BaseDaoImpl<MeetingMember, Long> implements MeetingMemberDao {
	Logger log = LoggerFactory.getLogger(this.getClass());

	/**
	 * 根据会议id查询会议成员列表
	 * 
	 * @param meetingId
	 * @param userId
	 * @return
	 */
	public List<MeetingMember> queryMemberList(Long meetingId) {
		String sql = "from MeetingMember b where b.meetingId = ?";

		@SuppressWarnings("unchecked")
		List<MeetingMember> list = this.getHibernateTemplate().find(sql, meetingId);

		return list;
	}

	/**
	 * 查询会议成员
	 * 
	 * @param uuid
	 * @param meetingId
	 * @return
	 */
	public MeetingMember selectById(Long userId, Long meetingId) {
		String sql = "from MeetingMember b where b.userId = ? and b.meetingId = ?";

		try {
			@SuppressWarnings("unchecked")
			List<MeetingMember> list = this.getHibernateTemplate().find(sql, userId, meetingId);
			if (list != null && list.size() > 0) {
				return list.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 通过会议ID删除会议成员
	 * 
	 * @param meetingId
	 * @throws HibernateDaoSupportException
	 */
	public void deleteMeetingMemberByMeetingId(Long meetingId) throws HibernateDaoSupportException {
		// 删除某个会议下得MEETING_MEMBER
		String sql = "delete From MeetingMember where meetingId = " + meetingId;
		log.info("删除某一个会议下用户相关联的信息:" + sql);
		executeUpdate(sql, new HashMap());
	}

	/**
	 * 查询会议下参会人员
	 * 
	 * @param meetingId
	 * @return
	 */
	public List<MeetingMember> queryList(Long meetingId) {
		String sql = "SELECT m.user_id, m.vip, u.gender as gender, u.name as name,u.mobile as mobile FROM meeting_member m,user u where m.user_id = u.id and m.meeting_id = "
				+ meetingId;

		@SuppressWarnings("unchecked")
		List<Object[]> list = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql)
				.list();
		if (list != null && list.size() > 0) {
			List<MeetingMember> memberList = new ArrayList<MeetingMember>();
			for (Object[] array : list) {
				MeetingMember member = new MeetingMember();
				member.setMeetingId(meetingId);
				member.setUserId(new Long((Integer) array[0]));
				member.setVip((String) array[1]);
				User user = new User();
				member.setUser(user);
				user.setGender(shortToInteger((Short) array[2]));
				user.setName((String) array[3]);
				user.setMobile((String) array[4]);
				memberList.add(member);
			}

			return memberList;
		}

		return null;
	}

	private Integer shortToInteger(Short in) {
		if (in == null) {
			return null;
		}

		return new Integer(in);
	}
}

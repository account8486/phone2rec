package com.wondertek.meeting.dao.impl;

import java.util.List;

import com.wondertek.meeting.dao.SignInDao;
import com.wondertek.meeting.model.SignIn;

/**
 * 签到
 * 
 * @author 金祝华
 */
public class SignInDaoImpl extends BaseDaoImpl<SignIn, Long> implements SignInDao {

	/**
	 * 根据会议id查询签到记录
	 * 
	 * @param meetingId
	 * @param userId
	 * @return
	 */
	public List<SignIn> querySignInList(Long meetingId) {
		String sql = "from SignIn b where b.meetingId = :meetingId and b.userId in"
				+ "(select userId from MeetingMember m where m.meetingId= :meetingId)";

		@SuppressWarnings("unchecked")
		List<SignIn> list = this.getHibernateTemplate().findByNamedParam(sql, "meetingId", meetingId);

		return list;
	}

	/**
	 * 根据会议id、用户id查询签到记录
	 * 
	 * @param meetingId
	 * @param userId
	 * @return
	 */
	public SignIn querySignIn(Long meetingId, Long userId) {
		String sql = "from SignIn b where b.meetingId = ? and b.user.id =?";

		@SuppressWarnings("unchecked")
		List<SignIn> list = this.getHibernateTemplate().find(sql, meetingId, userId);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * 删除会议的签到码
	 * 
	 * @param meetingId
	 * @return
	 */
	public int delSignCodeByMeetingId(Long meetingId) {
		String sql = "delete from sign_in where meeting_id = :meetingId";

		int result = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql)
				.setLong("meetingId", meetingId).executeUpdate();

		return result;
	}

	/**
	 * 删除单个签到码
	 * 
	 * @param meetingId
	 * @return
	 */
	public int delSignCode(Long meetingId, Long userId) {
		String sql = "delete from sign_in where meeting_id = :meetingId and user_id =:userId";

		int result = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql)
				.setLong("meetingId", meetingId).setLong("userId", userId).executeUpdate();

		return result;
	}
}

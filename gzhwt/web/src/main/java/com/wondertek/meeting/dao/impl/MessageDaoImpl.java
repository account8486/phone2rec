package com.wondertek.meeting.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;

import com.wondertek.meeting.dao.MessageDao;
import com.wondertek.meeting.exception.HibernateDaoSupportException;
import com.wondertek.meeting.model.JsonUser;
import com.wondertek.meeting.model.Message;
import com.wondertek.meeting.model.MessageRecipient;

public class MessageDaoImpl extends BaseDaoImpl<Message, Long> implements MessageDao {

	@SuppressWarnings("unchecked")
	public List<Message> queryInbox(final Long meetingId, final Long userId) {
		final String sql = "select msg.* from message msg, message_recipient msg_r where msg.id = msg_r.message_id and msg.meeting_id = msg_r.meeting_id and msg_r.meeting_id=:meeting_id and msg_r.recipient=:user_id and msg_r.read_flag != 2 order by msg.send_time desc";
		Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		List<Message> result = session.createSQLQuery(sql).addEntity("msg", Message.class).setLong("user_id", userId).setLong("meeting_id", meetingId).list();
		return result;
	}

	@SuppressWarnings("unchecked")
	public List<Message> queryOutbox(final Long meetingId, final Long userId) {
		final String sql = "select msg.* from message msg where msg.meeting_id = :meeting_id and msg.sender = :user_id and msg.send_flag != 2 order by msg.send_time desc";
		Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		List<Message> result = session.createSQLQuery(sql).addEntity("msg", Message.class).setLong("user_id", userId).setLong("meeting_id", meetingId).list();
		return result;
	}

	public void setReadFlag(final Long msgId, final Long userId) {
		final String sql = "update message_recipient set read_flag = 1 WHERE message_id=:id and recipient=:user_id";
		this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql).setLong("id", msgId).setLong("user_id", userId).executeUpdate();
	}

	public void deleteById(final Long msgId, final Long userId, final boolean isInbox) {
		String sql = "";
		if (isInbox) {
			sql = "update message_recipient set read_flag = 2 WHERE message_id=:id and recipient=:user_id";
		} else {
			sql = "update message set send_flag = 2 WHERE id=:id";
		}
		final SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		if (isInbox) {
			query.setLong("user_id", userId);
		}
		query.setLong("id", msgId).executeUpdate();
	}

	public void deleteAllByUserId(final Long meetingId, final Long userId, final boolean isInbox) {
		String sql = "";
		if (isInbox) {
			sql = "update message_recipient set read_flag = 2 WHERE recipient=:id and meeting_id=:meeting_id";
		} else {
			sql = "update message set send_flag = 2 WHERE sender=:id and meeting_id=:meeting_id";
		}
		this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql).setLong("id", userId).setLong("meeting_id", meetingId).executeUpdate();
	}
	
	public void deleteAllByUserId(Long meetingId, final Long uid, final Long otherUid)
			throws HibernateDaoSupportException {
		String updateUnReadSql = "update message_recipient mr set mr.read_flag = 2 "
				+ "  where mr.meeting_id = :meetingId and mr.recipient = :uid  ";
		if (otherUid != null) {
			updateUnReadSql += "and mr.message_id in (select m.id from message m where m.sender =:otherUid) ";
		}

		String updateUnReadSql2 = "update message m set m.send_flag = 2 "
				+ "  where m.meeting_id = :meetingId and m.sender = :uid  ";
		if (otherUid != null) {
			updateUnReadSql2 += "and m.id in (select mr.message_id from message_recipient mr where mr.recipient =:otherUid) ";
		}

		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("meetingId", meetingId);
		params.put("uid", uid);
		if (otherUid != null) {
			params.put("otherUid", otherUid);
		}

		executeSql(updateUnReadSql, params);
		executeSql(updateUnReadSql2, params);
	}

	@SuppressWarnings("unchecked")
	public int queryUnReadMessageCount(final Long meetingId, final Long userId) {
		final String sql = "select msg.* from message_recipient msg where msg.meeting_id=:meeting_id and msg.recipient=:user_id and msg.read_flag = 0";
		Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		List<MessageRecipient> result = session.createSQLQuery(sql).addEntity("msg", MessageRecipient.class).setLong("user_id", userId).setLong("meeting_id", meetingId).list();
		return result.size();
	}
	
	public JsonUser getUserInfo(final Long userId, final Long meetingId, final boolean inContacts) {
		final StringBuffer sql = new StringBuffer();
		sql.append("select u.id,u.name,u.mobile,u.modify_time,u.state,u.gender,u.birthday,mm.job,mm.member_level,mm.sort_code,mm.mobile_is_display,mm.city,mm.mailbox,mm.address,mm.add_in_contacts,mm.room_Number,mm.room_Number_Is_Display,mm.job_Is_Display")
		.append(" from USER u,meeting_member mm where u.id=mm.user_id and u.state='1'");
		if (inContacts) {
			sql.append(" and mm.add_in_contacts = 'Y'");
		}
		sql.append(" and mm.meeting_id= :meetingId and u.id = :userId");
		final Object result = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql.toString()).setLong("meetingId", meetingId).setLong("userId", userId)
				.uniqueResult();
		if (result == null) {
			return null;
		}		
		final Object[] resultArray = (Object[]) result;
		final JsonUser user = new JsonUser();
        user.setId(Long.valueOf((Integer)resultArray[0]));
        user.setName((String)resultArray[1]);
        user.setMobile((String)resultArray[2]);
        user.setJob((String)resultArray[7]);
        final String showMobile = (resultArray[10]==null) ? "1" : (""+resultArray[10]);
        user.setIsShowMobile(showMobile);
        final String showJob = (resultArray[17]==null) ? "1" : (""+resultArray[17]); 
        user.setIsShowJob(showJob);
        return user;		
	}
}
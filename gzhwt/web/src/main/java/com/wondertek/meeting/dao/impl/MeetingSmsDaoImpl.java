/**
 * 
 */
package com.wondertek.meeting.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.hibernate.Session;

import com.wondertek.meeting.dao.MeetingSmsDao;
import com.wondertek.meeting.exception.HibernateDaoSupportException;
import com.wondertek.meeting.model.MeetingSms;

/**
 * @author rain
 * 
 */
public class MeetingSmsDaoImpl extends BaseDaoImpl<MeetingSms, Long> implements MeetingSmsDao {

	/**
	 * 通过HQL语句获取meetingSmsLst
	 * 
	 * @param meetingId
	 * @return
	 */
	public List<MeetingSms> getMeetingSms(String queryString) {
		@SuppressWarnings("unchecked")
		List<MeetingSms> meetingSmsLst = this.getHibernateTemplate().find(queryString);

		return meetingSmsLst;
	}

	@SuppressWarnings("unchecked")
	public List<MeetingSms> queryScheduledSmsList() {
		final String sql = "select ms.* from meeting_sms ms where ms.state = 0 and (ms.delay=0 or (ms.delay=1 and ms.sendtime<=NOW()))";
		Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		List<MeetingSms> result = session.createSQLQuery(sql).addEntity("ms", MeetingSms.class).list();
		return result;
	}

	public void updateRecipientState(final Long id, final Long userId, final Integer state) {
		final String sql = "UPDATE meeting_sms_recipient SET state = :state WHERE user_id=:user_id and meeting_sms_id=:sms_id";
		this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql).setLong("sms_id", id)
				.setLong("user_id", userId).setInteger("state", state).executeUpdate();
	}

	public void updateSmsState(final Long id, final Integer state) {
		final String sql = "UPDATE meeting_sms SET state = :state WHERE id=:id";
		this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql).setLong("id", id)
				.setInteger("state", state).executeUpdate();
	}

	/**
	 * 通过会议ID删除会议下短信
	 * 
	 * @param meetingId
	 * @throws HibernateDaoSupportException
	 */
	public void deleteMeetingSmsByMeetingId(Long meetingId) throws HibernateDaoSupportException {
		String hql = "delete from MeetingSms where meetingId=" + meetingId;
		log.info("通过会议ID删除会议下短信:"+hql);
		this.executeUpdate(hql, new HashMap<String, Object>());
	}

}
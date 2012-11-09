/**
 * 
 */
package com.wondertek.meeting.dao.impl;

import java.util.List;

import com.wondertek.meeting.dao.MeetingPostDao;
import com.wondertek.meeting.exception.HibernateDaoSupportException;
import com.wondertek.meeting.model.MeetingPost;

/**
 * @author rain
 * 
 */
public class MeetingPostDaoImpl extends BaseDaoImpl<MeetingPost, Long> implements MeetingPostDao {

	@SuppressWarnings("unchecked")
	public List<MeetingPost> queryListByMeetingId(final Long meetingId) {
		final String sql = "from MeetingPost where meetingId=? order by modify_time desc";
		return this.getHibernateTemplate().find(sql, meetingId);
	}

	public void addViewCount(final Long postId) {
		final String sql = "UPDATE meeting_post SET view_count = view_count + 1 WHERE id=:id";
		this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql).setLong("id", postId)
				.executeUpdate();
	}

	public void addCommentCount(final Long postId) {
		final String sql = "UPDATE meeting_post SET comment_count = comment_count + 1, modify_time=now() WHERE id=:id";
		this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql).setLong("id", postId)
				.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	public int checkNewPost(final Long meetingId) throws HibernateDaoSupportException {
		final String sql = "select * from meeting_post where modify_time >= (NOW() - INTERVAL 16 SECOND)";
		List<MeetingPost> posts = this.getHibernateTemplate().getSessionFactory().getCurrentSession()
				.createSQLQuery(sql).addEntity(MeetingPost.class).list();
		return posts.size();
	}

	public void decreaseCommentCount(final Long postId) {
		final String sql = "UPDATE meeting_post SET comment_count = comment_count - 1, modify_time=now() WHERE id=:id";
		this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql).setLong("id", postId)
				.executeUpdate();
	}

	public void deletePostByMeetingId(final Long meetingId) {
		String sql = "delete from meeting_comment where post_id in (select id from meeting_post where meeting_id = :meetingId)";
		getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql)
				.setLong("meetingId", meetingId).executeUpdate();
		getHibernateTemplate().getSessionFactory().getCurrentSession()
				.createSQLQuery("delete from meeting_post where meeting_id = :meetingId")
				.setLong("meetingId", meetingId).executeUpdate();
	}
	
	public Object getUserInfo(final Long meetingId, final Long userId) {
		final StringBuffer sql = new StringBuffer();
		sql.append("select u.id,u.name,u.mobile,u.modify_time,u.state,u.gender,u.birthday,mm.job,mm.member_level,mm.sort_code,mm.mobile_is_display,mm.city,mm.mailbox,mm.address,mm.add_in_contacts,mm.room_Number,mm.room_Number_Is_Display,mm.job_Is_Display")
		.append(" from USER u,meeting_member mm where u.id=mm.user_id and u.state='1' and  mm.add_in_contacts = 'Y' and mm.meeting_id= :meetingId and u.id = :userId");
		return getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql.toString()).setLong("meetingId", meetingId).setLong("userId", userId)
				.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MeetingPost> findPostUser(Long meetingId) {
		/*String hql="select  mc.creator,mp.posterId  from MeetingPost mp left join MeetingComment mc where "+
        " mp.meetingId="+meetingId+" and  mc.postId in (select m.posterId from MeetingPost m where m.meetingId="+meetingId+")"
        +" group by mp.posterId,mc.creator";*/
		String hql="from MeetingPost mp where mp.meetingId="+meetingId+" group by mp.posterId";
		return this.getHibernateTemplate().find(hql);
		
	}
}
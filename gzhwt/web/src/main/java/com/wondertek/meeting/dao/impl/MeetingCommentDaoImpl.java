/**
 * 
 */
package com.wondertek.meeting.dao.impl;

import java.util.List;

import com.wondertek.meeting.dao.MeetingCommentDao;
import com.wondertek.meeting.model.MeetingComment;

/**
 * @author rain
 * 
 */
public class MeetingCommentDaoImpl extends BaseDaoImpl<MeetingComment, Long>
		implements MeetingCommentDao {
	/*
	 * 发言评论列表
	 * 
	 * @see
	 * com.wondertek.meeting.dao.MeetingCommentDao#queryListByPostId(java.lang
	 * .Integer)
	 */
	@SuppressWarnings("unchecked")
	public List<MeetingComment> queryListByPostId(final Long postId) {
		final String sql = "from MeetingComment where postId=?";
		return this.getHibernateTemplate().find(sql, postId);
	}
}

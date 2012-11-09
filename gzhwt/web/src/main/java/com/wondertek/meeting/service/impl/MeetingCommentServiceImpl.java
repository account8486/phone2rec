/**
 * 
 */
package com.wondertek.meeting.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.dao.MeetingCommentDao;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.MeetingComment;
import com.wondertek.meeting.service.MeetingCommentService;

/**
 * @author rain
 * 
 */
public class MeetingCommentServiceImpl extends BaseServiceImpl<MeetingComment, Long> implements MeetingCommentService {
	private MeetingCommentDao meetingCommentDao;

	/**
	 * @param meetingCommentDao
	 *            the meetingCommentDao to set
	 */
	public void setMeetingCommentDao(final MeetingCommentDao meetingCommentDao) {
		super.setBaseDao(meetingCommentDao);
		this.meetingCommentDao = meetingCommentDao;
	}

	public List<MeetingComment> queryListByPostId(final Long postId) {
		return meetingCommentDao.queryListByPostId(postId);
	}

	public Pager<MeetingComment> queryPagerByPostId(final Long postId, final int currentPage, final int pageSize)
			throws ServiceException {
		Map<String, Object> properties = new HashMap<String, Object>();
		final String sql = "from MeetingComment where postId=" + String.valueOf(postId);
		return meetingCommentDao.findPager(sql, currentPage, pageSize, properties);

	}
}

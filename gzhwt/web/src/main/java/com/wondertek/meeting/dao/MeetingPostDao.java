/**
 * 
 */
package com.wondertek.meeting.dao;

import java.util.List;

import com.wondertek.meeting.exception.HibernateDaoSupportException;
import com.wondertek.meeting.model.MeetingPost;

/**
 * @author rain
 * 
 */
public interface MeetingPostDao extends BaseDao<MeetingPost, Long> {

	List<MeetingPost> queryListByMeetingId(final Long meetingId);

	void addViewCount(final Long postId);

	void addCommentCount(final Long postId);

	void decreaseCommentCount(final Long postId);

	int checkNewPost(final Long meetingId) throws HibernateDaoSupportException;

	void deletePostByMeetingId(final Long meetingId);
	
	Object getUserInfo(final Long meetingId, final Long userId);

	List<MeetingPost> findPostUser(Long meetingId);
}

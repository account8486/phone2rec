/**
 * 
 */
package com.wondertek.meeting.dao;

import java.util.List;

import com.wondertek.meeting.model.MeetingComment;

/**
 * @author rain
 * 
 */
public interface MeetingCommentDao extends BaseDao<MeetingComment, Long> {
	List<MeetingComment> queryListByPostId(final Long postId);

}

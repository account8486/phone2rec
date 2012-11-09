/**
 * 
 */
package com.wondertek.meeting.service;

import java.util.List;

import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.MeetingComment;

/**
 * @author rain
 * 
 */
public interface MeetingCommentService extends BaseService<MeetingComment, Long> {
	List<MeetingComment> queryListByPostId(final Long postId);

	Pager<MeetingComment> queryPagerByPostId(final Long postId, final int currentPage, final int pageSize)
			throws ServiceException;
}

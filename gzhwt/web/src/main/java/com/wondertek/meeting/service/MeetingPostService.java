/**
 * 
 */
package com.wondertek.meeting.service;

import java.util.List;

import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.MeetingPost;
import com.wondertek.meeting.model.User;

/**
 * @author rain
 * 
 */
public interface MeetingPostService extends BaseService<MeetingPost, Long> {
	List<MeetingPost> queryListByMeetingId(final Long meetingId);

	void addViewCount(final Long postId);

	void addCommentCount(final Long postId);

	void decreaseCommentCount(final Long postId);

	Pager<MeetingPost> queryPagerByMeetingId(final MeetingPost meetingPost, final int currentPage, final int pageSize)
			throws ServiceException;

	int checkNewPost(final Long meetingId) throws ServiceException;
	
	User getBussinessCardInfo(final Long meetingId, final Long userId);
	
	/**
	 * AJAX查询大于postId的最新消息
	 * @param postId
	 * @param meetingId  会议Id
	 * @return
	 * @author ZOUXIAOMING
	 */
	public List<MeetingPost> findByModifyTime(String postId,String meetingId);
	
	/**
	 * 查询发表过消息的所有人员
	 * @param meetingId
	 * @return
	 */
	public List<MeetingPost> findPostUser(Long meetingId);
	
	
	

}

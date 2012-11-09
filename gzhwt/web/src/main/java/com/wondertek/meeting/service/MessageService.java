/**
 * 
 */
package com.wondertek.meeting.service;

import java.util.List;

import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.JsonUser;
import com.wondertek.meeting.model.Message;
import com.wondertek.meeting.model.User;

/**
 * @author rain
 * 
 */
public interface MessageService extends BaseService<Message, Long> {
	List<Message> queryInbox(final Long meetingId, final Long userId);

	List<Message> queryOutbox(final Long meetingId, final Long userId);

	void setReadFlag(final Long msgId, final Long userId);

	void deleteById(final Long msgId, final Long userId, final boolean isInbox);

	void deleteAllByUserId(final Long meetingId, final Long userId, final boolean isInbox);

	int queryUnReadMessageCount(final Long meetingId, final Long userId);
	
	JsonUser getUserInfo(final Long userId, final Long meetingId);
	
	public List<User> listMessageUsers(Long meetingId, final Long userId) throws ServiceException;
	
	public List<Message> listMessages(Long meetingId, Long uid, Long otherUid) throws ServiceException;
	
	public void deleteAllByUserId(Long meetingId, final Long userId, final Long otherUid) throws ServiceException;
}

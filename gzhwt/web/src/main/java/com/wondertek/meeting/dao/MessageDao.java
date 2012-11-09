/**
 * 
 */
package com.wondertek.meeting.dao;

import java.util.List;

import com.wondertek.meeting.exception.HibernateDaoSupportException;
import com.wondertek.meeting.model.JsonUser;
import com.wondertek.meeting.model.Message;

/**
 * @author rain
 * 
 */
public interface MessageDao extends BaseDao<Message, Long> {

	List<Message> queryInbox(final Long meetingId, final Long userId);

	List<Message> queryOutbox(final Long meetingId, final Long userId);

	void setReadFlag(final Long msgId, final Long userId);

	void deleteById(final Long msgId, final Long userId, final boolean isInbox);

	void deleteAllByUserId(final Long meetingId, final Long userId, final boolean isInbox);

	int queryUnReadMessageCount(final Long meetingId, final Long userId);
	
	JsonUser getUserInfo(final Long userId, final Long meetingId, final boolean inContacts);
	
	public void deleteAllByUserId(Long meetingId, final Long uid, final Long otherUid) throws HibernateDaoSupportException;
}

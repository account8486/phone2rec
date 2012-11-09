/**
 * 
 */
package com.wondertek.meeting.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.dao.MessageDao;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.JsonUser;
import com.wondertek.meeting.model.Message;
import com.wondertek.meeting.model.User;
import com.wondertek.meeting.service.MessageService;
import com.wondertek.meeting.util.DateUtil;

/**
 * @author rain
 * 
 */
public class MessageServiceImpl extends BaseServiceImpl<Message, Long> implements MessageService {

	private MessageDao messageDao;

	/**
	 * @param messageDao
	 *            the messageDao to set
	 */
	public void setMessageDao(MessageDao messageDao) {
		super.setBaseDao(messageDao);
		this.messageDao = messageDao;
	}

	public List<Message> queryInbox(final Long meetingId, final Long userId) {
		return messageDao.queryInbox(meetingId, userId);
	}

	public List<Message> queryOutbox(final Long meetingId, final Long userId) {
		return messageDao.queryOutbox(meetingId, userId);
	}

	public void setReadFlag(final Long msgId, final Long userId) {
		messageDao.setReadFlag(msgId, userId);
	}

	public void deleteById(final Long msgId, final Long userId, final boolean isInbox) {
		messageDao.deleteById(msgId, userId, isInbox);
	}

	public void deleteAllByUserId(final Long meetingId, final Long userId, final boolean isInbox) {
		messageDao.deleteAllByUserId(meetingId, userId, isInbox);
	}

	public int queryUnReadMessageCount(final Long meetingId, final Long userId) {
		return messageDao.queryUnReadMessageCount(meetingId, userId);
	}
	
	public JsonUser getUserInfo(final Long userId, final Long meetingId) {
		return messageDao.getUserInfo(userId, meetingId, false);
	}

	public List<User> listMessageUsers(Long meetingId, final Long userId) throws ServiceException {
		final StringBuffer sql = new StringBuffer();
		sql.append("select u.id, u.name from user u, message m, message_recipient mr ").append(
				" where u.state=1 and m.meeting_id = :meetingId and mr.message_id = m.id ");
		sql.append(" AND ((u.id = m.sender  AND  mr.recipient = :userId AND mr.read_flag != 2 ) "
				+ "  OR  (u.id = mr.recipient  AND m.sender = :userId AND m.send_flag !=2) ) "
				+ "  GROUP BY u.id order by m.send_time desc");

		HashMap<String, Object> properties = new HashMap<String, Object>();
		properties.put("meetingId", meetingId);
		properties.put("userId", userId);
		final List resultList = messageDao.queryListSql(sql.toString(), 0, -1, properties);

		String latestMsgSql = "SELECT m.message, m.send_time FROM message m, message_recipient mr "
				+ "WHERE m.meeting_id = :meetingId and mr.message_id=m.id "
				+ "AND ((m.send_flag !=2  AND m.sender = :uid   AND    mr.recipient =:otherId )"
				+ " OR ( mr.read_flag != 2 AND m.sender =:otherId  AND    mr.recipient = :uid )) "
				+ " ORDER BY m.send_time DESC limit 1";

		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("meetingId", meetingId);
		params.put("uid", userId);

		List<User> userList = new ArrayList<User>();
		for (Object result : resultList) {
			final Object[] resultArray = (Object[]) result;
			final User user = new User();
			if (resultArray[0] == null) {
				continue;
			}
			user.setId(Long.valueOf("" + resultArray[0]));
			user.setName((String) resultArray[1]);

			params.put("otherId", user.getId());
			List msgList = messageDao.queryListSql(latestMsgSql, 0, -1, params);
			if (msgList.size() > 0) {
				// user.setMessage();
				Object[] array = (Object[]) msgList.get(0);
				Message msg = new Message();
				msg.setMessage((String) array[0]);
				msg.setSendTime((Timestamp) array[1]);
				user.setMessage(msg);
			}
			userList.add(user);
		}
		return userList;
	}
	
	public List<Message> listMessages(Long meetingId, Long uid, Long otherUid) throws ServiceException {

		// 查询会话信息列表
		String hql = "SELECT m from Message m, MessageRecipient mr WHERE m.meetingId=:meetingId and mr.messageId=m.id AND "
				+ "((m.sendFlag !=2 AND m.sender.id = :uid And mr.recipient.id = :otherUid ) OR "
				+ " (mr.readFlag != 2 AND m.sender.id = :otherUid  AND mr.recipient.id = :uid )) "
				+ "ORDER BY m.sendTime desc ";
		HashMap<String, Object> properties = new HashMap<String, Object>();
		properties.put("meetingId", meetingId);
		properties.put("uid", uid);
		properties.put("otherUid", otherUid);
		List<Message> list = messageDao.queryList(hql, 0, -1, null, properties);

		// 将他发给我的会话信息标记为已读
		String currentTime = DateUtil.getFormatDate(new Date(), "yyyy-MM-dd hh:mm:ss");
		String updateUnReadSql = "update message_recipient mr set mr.read_flag = 1 "
				+ " , mr.read_time = :currentTime where mr.read_flag=0 And mr.recipient = :uid  "
				+ "and mr.message_id in (select m.id from message m where m.sender =:otherUid) ";
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("uid", uid);
		params.put("otherUid", otherUid);
		params.put("currentTime", currentTime);
		int effected = messageDao.executeSql(updateUnReadSql, params);
		log.debug("update UnReadSql effected {}", effected);
		return list;
	}
	
	public void deleteAllByUserId(Long meetingId, final Long userId, final Long otherUid) throws ServiceException{
		messageDao.deleteAllByUserId(meetingId, userId, otherUid);
	}
}

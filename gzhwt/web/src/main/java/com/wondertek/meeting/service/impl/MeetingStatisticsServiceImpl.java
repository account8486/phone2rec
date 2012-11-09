/**
 * 
 */
package com.wondertek.meeting.service.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.dao.MeetingDao;
import com.wondertek.meeting.dao.MessageStatisticsDao;
import com.wondertek.meeting.exception.HibernateDaoSupportException;
import com.wondertek.meeting.model.MessageStatistics;
import com.wondertek.meeting.service.MeetingStatisticsService;
import com.wondertek.meeting.util.StringUtil;

/**
 * @author rain
 * 
 */
public class MeetingStatisticsServiceImpl extends BaseServiceImpl<MessageStatistics, Long> implements
		MeetingStatisticsService {

	private MessageStatisticsDao messageStatisticsDao;
	private MeetingDao meetingDao;
	
	/**
	 * @param messageStatisticsDao the messageStatisticsDao to set
	 */
	public void setMessageStatisticsDao(MessageStatisticsDao messageStatisticsDao) {
		super.setBaseDao(messageStatisticsDao);
		this.messageStatisticsDao = messageStatisticsDao;
	}

	/**
	 * @param meetingDao the meetingDao to set
	 */
	public void setMeetingDao(MeetingDao meetingDao) {
		this.meetingDao = meetingDao;
	}
	
	@SuppressWarnings("unchecked")
	public Pager<MessageStatistics> queryInteractionByMeetingId(final int currentPage, final int pageSize, final Long meetingId) {
		final StringBuffer sql = new StringBuffer();
		sql.append("select usr.id,usr.name,usr.mobile,m.msgCount,p.postCount,c.commentCount,mt.meeting_name,mt.id")
				.append(" from meeting mt, (select u.id, u.name, u.mobile from meeting_member mm, user u where mm.user_id = u.id and mm.meeting_id=:meeting_id) usr")
				.append(" left join ")
				.append("(select u.id,count(msg.id) as msgCount from message msg, user u where u.id = msg.sender and msg.meeting_id=:meeting_id group by u.id) m ")
				.append("on usr.id=m.id")
				.append(" left join ")
				.append("(select post.poster as id,count(post.id) as postCount from meeting_post post where post.poster_type=0 and post.meeting_id=:meeting_id group by post.poster) p ")
				.append("on usr.id=p.id")
				.append(" left join ")
				.append("(select comm.creator as id, count(comm.id) as commentCount	from meeting_comment comm, meeting_post post where post.meeting_id=:meeting_id and post.poster_type=0 and post.id=comm.post_id group by comm.creator) c ")
				.append("on usr.id=c.id ").append("where mt.id=:meeting_id");
		final Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("meeting_id", meetingId);

		try {
			final Pager<Object> pager = messageStatisticsDao.findPagerBySql(sql.toString(), currentPage, pageSize, properties);
			final List<Object> records = pager.getPageRecords();
			final List<MessageStatistics> datas = new ArrayList<MessageStatistics>();
			for (final Object record : records) {
				final Object[] recordArray = (Object[]) record;
				final MessageStatistics stat = new MessageStatistics();
				stat.setUserId(Long.valueOf((Integer) recordArray[0]));
				stat.setUserName((String) recordArray[1]);
				stat.setMobile((String) recordArray[2]);
				final BigInteger msgCount = (BigInteger) ((recordArray[3] == null) ? BigInteger.ZERO : recordArray[3]);
				final BigInteger postCount = (BigInteger) ((recordArray[4] == null) ? BigInteger.ZERO : recordArray[4]);
				final BigInteger commentCount = (BigInteger) ((recordArray[5] == null) ? BigInteger.ZERO : recordArray[5]);
				stat.setMessageCount(msgCount.longValue());
				stat.setPostCount(postCount.longValue());
				stat.setCommentCount(commentCount.longValue());
				stat.setMeetingName((String) recordArray[6]);
				stat.setMeetingId(Long.valueOf((Integer) recordArray[7]));
				datas.add(stat);
			}
			final Pager<MessageStatistics> result = new Pager<MessageStatistics>();
			result.setTotal(pager.getTotal());
			result.setPageSize(pager.getPageSize());
			result.setCurrentPage(pager.getCurrentPage());
			result.setPageRecords(datas);
			return result;
		} catch (HibernateDaoSupportException e) {
			log.error("交流统计查询失败： ", e);
		}
		return null;		
	}

	@SuppressWarnings("unchecked")
	public Pager<MessageStatistics> queryInteraction(final int currentPage, final int pageSize, final String treeId) {
		List<Long> mIdList = new ArrayList<Long>();
		if (StringUtil.isEmpty(treeId)) {
			log.info("treeId 为空，return null!");
			return null;
		} else if (treeId.startsWith("o")) { // treeId为组织id
			Long orgId = Long.parseLong(treeId.substring(1));
			mIdList = meetingDao.queryMeetingIdsByOrgId(orgId);
		} else if (treeId.startsWith("u")) { // treeId为用户id
			Long userId = Long.parseLong(treeId.substring(1));
			mIdList = meetingDao.queryMeetingIdsByUserId(userId);
		}
		if (mIdList.size() > 0) {
			final Map<String, Object> properties = new HashMap<String, Object>();
			properties.put("mlist", mIdList);
			final StringBuffer sql = new StringBuffer();
			sql.append("select mt.id,mt.meeting_name,mm.msgcount,p.postcount,c.commentcount from ")
					.append("(select m.id, m.meeting_name from meeting m where m.id in (:mlist)) mt ")
					.append("left join ")
					.append("(select msg.meeting_id as id,count(msg.id) as msgcount from message msg where msg.meeting_id in (:mlist) group by msg.meeting_id) mm on mt.id=mm.id ")
					.append("left join ")
					.append("(select post.meeting_id as id,count(post.id) as postcount from meeting_post post where post.poster_type=0 and post.meeting_id in (:mlist) group by post.meeting_id) p on mt.id=p.id ")
					.append("left join ")
					.append("(select post.meeting_id as id, count(comm.id) as commentcount from meeting_comment comm, meeting_post post where post.meeting_id in (:mlist) and post.poster_type=0 and post.id=comm.post_id group by post.meeting_id) c on mt.id=c.id");
			try {
				final Pager<Object> pager = messageStatisticsDao.findPagerBySql(sql.toString(), currentPage, pageSize, properties);
				final List<Object> records = pager.getPageRecords();
				final List<MessageStatistics> datas = new ArrayList<MessageStatistics>();
				for (final Object record : records) {
					final Object[] recordArray = (Object[]) record;
					final MessageStatistics stat = new MessageStatistics();
					stat.setMeetingId(Long.valueOf((Integer) recordArray[0]));
					stat.setMeetingName((String) recordArray[1]);
					final BigInteger msgCount = (BigInteger) ((recordArray[2] == null) ? BigInteger.ZERO : recordArray[2]);
					final BigInteger postCount = (BigInteger) ((recordArray[3] == null) ? BigInteger.ZERO : recordArray[3]);
					final BigInteger commentCount = (BigInteger) ((recordArray[4] == null) ? BigInteger.ZERO : recordArray[4]);
					stat.setMessageCount(msgCount.longValue());
					stat.setPostCount(postCount.longValue());
					stat.setCommentCount(commentCount.longValue());
					datas.add(stat);
				}
				final Pager<MessageStatistics> result = new Pager<MessageStatistics>();
				result.setTotal(pager.getTotal());
				result.setPageSize(pager.getPageSize());
				result.setCurrentPage(pager.getCurrentPage());
				result.setPageRecords(datas);
				return result;
			} catch (HibernateDaoSupportException e) {
				log.error("交流统计查询失败： ", e);
			}
		}
		return null;
	}
}
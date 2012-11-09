/**
 * 
 */
package com.wondertek.meeting.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.dao.MeetingDao;
import com.wondertek.meeting.dao.MeetingSmsDao;
import com.wondertek.meeting.exception.HibernateDaoSupportException;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.MeetingSms;
import com.wondertek.meeting.service.MeetingSmsService;
import com.wondertek.meeting.util.StringUtil;

/**
 * @author rain
 * 
 */
public class MeetingSmsServiceImpl extends BaseServiceImpl<MeetingSms, Long> implements MeetingSmsService {
	private MeetingSmsDao meetingSmsDao;
	private MeetingDao meetingDao;

	public List<MeetingSms> queryScheduledSmsList() {
		return meetingSmsDao.queryScheduledSmsList();
	}

	public void updateRecipientState(final Long id, final Long userId, final Integer state) {
		meetingSmsDao.updateRecipientState(id, userId, state);
	}
	
	public void updateSmsState(final Long id, final Integer state) {
		meetingSmsDao.updateSmsState(id, state);
	}

	/**
	 * 通过meetingId来获取列表
	 * 
	 * @param meetingId
	 * @return
	 */
	public List<MeetingSms> getMeetingSmsList(Long meetingId) {
		StringBuffer hql = new StringBuffer();
		hql.append("SELECT meetingSms FROM MeetingSms meetingSms where meetingSms.meetingId=" + meetingId);
		return meetingSmsDao.getMeetingSms(hql.toString());
	}

	/**
	 * 获取分页信息
	 * 
	 * @param currentPage
	 * @param pageSize
	 * @param meetingId
	 * @param messages
	 * @param mobile
	 * @param state
	 * @return
	 */
	public Pager<MeetingSms> getMeetingSmsPager(int currentPage, int pageSize, Long meetingId, String messages,
			String mobile, String state) {
		Pager<MeetingSms> pager = null;
		StringBuffer hql = new StringBuffer();

		hql.append("  from MeetingSms ms where 1=1 and state!=2   ");
		hql.append("  and  ms.meetingId=" + meetingId);

		// 通过内容模糊查询
		if (StringUtil.isNotEmpty(messages)) {
			hql.append("  and ms.messages like '%" + messages + "%'");
		}

		if (StringUtil.isNotEmpty(state)) {
			hql.append("   and ms.state=" + state);
		}

		// if(StringUtil.isNotEmpty(mobile)){
		// hql.append("  and ms.recipient.mobile like '%"+mobile+"%'");
		// }

		hql.append("  order by createTime desc ");

		try {
			pager = meetingSmsDao.findPager(hql.toString(), currentPage, pageSize, null);
		} catch (HibernateDaoSupportException e) {
			e.printStackTrace();
		}
		return pager;
	}

	public void deleteByMeetingId(final Long meetingId) throws ServiceException {
		final MeetingSms sms = new MeetingSms();
		sms.setMeetingId(meetingId);
		sms.setState(0);
		delete(sms);
	}

	@SuppressWarnings("unchecked")
	public Pager<Object> statMeetingSmsPagers(String treeId, int currentPage, int pageSize, String meetingName)
			throws HibernateDaoSupportException {
		StringBuffer sbSql = new StringBuffer();
		sbSql.append(" select st.meeting_id,st.meeting_name,st.free_num,st.sended_num  from  (SELECT meeting_id,(CASE WHEN m.meeting_name IS NULL THEN '已删除' ELSE m.meeting_name END) AS meeting_name ");
		sbSql.append(" ,(CASE WHEN  m.free_sms_num IS NULL THEN 0 ELSE   m.free_sms_num END) AS free_num,COUNT(1) sended_num ");

		sbSql.append("  FROM meeting_sms sms , meeting m ");
		sbSql.append("  WHERE 1=1  and m.id=sms.meeting_id  ");

		// 根据左侧树传递过来的组织或者管理员Id，查询会议id列表。
		Map<String, Object> properties = new HashMap<String, Object>();
		List<Long> mIdList = new ArrayList<Long>();
		if (StringUtil.isEmpty(treeId)) {
			log.info("treeId 为空，return null!");
			return null;
		} else if (treeId.startsWith("o"))// treeId为组织id
		{
			Long orgId = Long.parseLong(treeId.substring(1));
			mIdList = meetingDao.queryMeetingIdsByOrgId(orgId);
		} else if (treeId.startsWith("u"))// treeId为用户id
		{
			Long userId = Long.parseLong(treeId.substring(1));
			mIdList = meetingDao.queryMeetingIdsByUserId(userId);
		}

		if (mIdList.size() > 0) {
			sbSql.append(" and sms.meeting_id in(:mIdList)");
			properties.put("mIdList", mIdList);
		} else { // 会议id列表为空，返回空。
			return null;
		}

		if (StringUtil.isNotEmpty(meetingName)) {
			sbSql.append("  and m.meeting_name like '%" + meetingName + "%'");
		}

		sbSql.append("  GROUP BY meeting_id ORDER BY meeting_id DESC) st ");
		log.debug("短信统计SQL:" + sbSql.toString());
		Pager<Object> pager = meetingDao.findPagerBySql(sbSql.toString(), currentPage, pageSize, properties);

		return pager;
	}

	/**
	 * 通过会议ID删除会议下短信
	 * 
	 * @param meetingId
	 * @throws HibernateDaoSupportException
	 */
	public void deleteMeetingSmsByMeetingId(Long meetingId) throws HibernateDaoSupportException {
		meetingSmsDao.deleteMeetingSmsByMeetingId(meetingId);
	}

	/**
	 * @param meetingDao
	 *            the meetingDao to set
	 */
	public void setMeetingDao(MeetingDao meetingDao) {
		this.meetingDao = meetingDao;
	}

	/**
	 * @param meetingSmsDao
	 *            the meetingSmsDao to set
	 */
	public void setMeetingSmsDao(MeetingSmsDao meetingSmsDao) {
		super.setBaseDao(meetingSmsDao);
		this.meetingSmsDao = meetingSmsDao;
	}
}

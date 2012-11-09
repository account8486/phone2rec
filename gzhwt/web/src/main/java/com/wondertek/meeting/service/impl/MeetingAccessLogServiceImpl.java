package com.wondertek.meeting.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.dao.MeetingAccessLogDao;
import com.wondertek.meeting.dao.MeetingDao;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.MeetingAccessLog;
import com.wondertek.meeting.service.MeetingAccessLogService;
import com.wondertek.meeting.util.StringUtil;

/**
 * 会议访问记录
 * 
 * @author 金祝华
 */
public class MeetingAccessLogServiceImpl extends BaseServiceImpl<MeetingAccessLog, Long> implements
		MeetingAccessLogService {

	private MeetingAccessLogDao meetingAccessLogDao;
	private MeetingDao meetingDao;

	public MeetingAccessLogDao getMeetingAccessLogDao() {
		return meetingAccessLogDao;
	}

	public void setMeetingAccessLogDao(MeetingAccessLogDao meetingAccessLogDao) {
		this.basicDao = meetingAccessLogDao;
		this.meetingAccessLogDao = meetingAccessLogDao;
	}

	/**
	 * 查询会议访问统计，按会议进行汇总
	 * 
	 * @param user
	 * @param currentPage
	 * @param pageSize
	 * @return
	 * @throws ServiceException
	 */
	public Object[] findPager(MeetingAccessLog meetingAccessLog, Long selfOrgId, int currentPage, int pageSize)
			throws ServiceException {
		Map<String, Object> properties = new HashMap<String, Object>();

		String sql = "";
		if ("1".equals(meetingAccessLog.getIsDistinct())) {// 去掉重复用户
			sql = "select t.meeting_name, t.portal_type, count(t.user_id) cnt,t.meeting_id from (select l.meeting_id,m.meeting_name, l.portal_type, l.user_id";
		} else {
			sql = "select m.meeting_name, l.portal_type, count(l.id) cnt,l.meeting_id ";
		}

		sql += " from meeting_access_log l, meeting m where l.meeting_id = m.id";

		if (meetingAccessLog.getStartTime() != null) {
			sql += " and l.access_time > :startTime ";
			properties.put("startTime", meetingAccessLog.getStartTime());// 开始时间
		}

		if (meetingAccessLog.getEndTime() != null) {
			sql += " and l.access_time < :endTime ";
			properties.put("endTime", meetingAccessLog.getEndTime());// 结束时间
		}

		List<Long> mIdList = new ArrayList<Long>();
		// 如果查询页面输入了会议id，则以该会议id为准
		// 判断该会议id是否在管理员组织下，如果不在，返回null。
		if (meetingAccessLog.getMeeting() != null && meetingAccessLog.getMeeting().getId() != null) {
			List<Long> list = meetingDao.queryMeetingIdsByOrgId(selfOrgId);
			if (list.contains(meetingAccessLog.getMeeting().getId())) {
				mIdList.add(meetingAccessLog.getMeeting().getId());
			} else {
				return null;
			}
		} else {
			// 否则以treeId计算得出
			String treeId = meetingAccessLog.getTreeId();
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
		}

		Object[] result = new Object[2];
		if (mIdList.size() > 0) {
			sql += " and m.id in(:mIdList)";
			properties.put("mIdList", mIdList);
		} else { // 会议id列表为空，返回空。
			return null;
		}

		if ("1".equals(meetingAccessLog.getIsDistinct())) {// 去掉重复用户
			sql += " group by l.meeting_id, l.portal_type, l.user_id ) as t group by t.meeting_id, t.portal_type";
		} else {
			sql += " group by l.meeting_id, l.portal_type";
		}

		try {
			Pager<MeetingAccessLog> pager = meetingAccessLogDao.findPagerBySql(sql, currentPage, pageSize, properties);
			result[0] = pager;
		} catch (Exception e) {
			final String errMsg = "Find meeting pager error! ";
			log.error(errMsg, e);
			throw new ServiceException(errMsg, e);
		}

		// 查询汇总
		sql = "select tt.portal_type, sum(tt.cnt) from (" + sql + ") as tt group by portal_type";

		result[1] = meetingAccessLogDao.querySum(sql, properties);
		return result;
	}

	/**
	 * 查询会议访问统计明细
	 * 
	 * @param user
	 * @param currentPage
	 * @param pageSize
	 * @return
	 * @throws ServiceException
	 */
	public Pager<MeetingAccessLog> findDetailPager(MeetingAccessLog meetingAccessLog, Long selfOrgId, int currentPage,
			int pageSize) throws ServiceException {
		Map<String, Object> properties = new HashMap<String, Object>();

		String sql = "select m.meeting_name,u.mobile,u.name,l.portal_type,l.access_time,l.meeting_id from meeting_access_log l, meeting m, user u "
				+ "where l.meeting_id = m.id and l.user_id = u.id";

		String mobile = "";
		if (meetingAccessLog != null && meetingAccessLog.getUser() != null
				&& StringUtil.isNotEmpty(meetingAccessLog.getUser().getMobile())) {

			sql += " and u.mobile like concat('%',:mobile,'%') ";// 手机号码

			mobile = meetingAccessLog.getUser().getMobile();
			properties.put("mobile", mobile);
		}

		if (meetingAccessLog.getStartTime() != null) {
			sql += " and l.access_time > :startTime ";
			properties.put("startTime", meetingAccessLog.getStartTime());// 开始时间
		}

		if (meetingAccessLog.getEndTime() != null) {
			sql += " and l.access_time < :endTime ";
			properties.put("endTime", meetingAccessLog.getEndTime());// 结束时间
		}

		List<Long> mIdList = new ArrayList<Long>();
		// 如果查询页面输入了会议id，则以该会议id为准
		// 判断该会议id是否在管理员组织下，如果不在，返回null。
		if (meetingAccessLog.getMeeting() != null && meetingAccessLog.getMeeting().getId() != null) {
			List<Long> list = meetingDao.queryMeetingIdsByOrgId(selfOrgId);
			if (list.contains(meetingAccessLog.getMeeting().getId())) {
				mIdList.add(meetingAccessLog.getMeeting().getId());
			} else {
				return null;
			}
		} else {
			// 否则以treeId计算得出
			String treeId = meetingAccessLog.getTreeId();
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
		}

		if (mIdList.size() > 0) {
			sql += " and m.id in(:mIdList)";
			properties.put("mIdList", mIdList);
		} else { // 会议id列表为空，返回空。
			return null;
		}

		log.debug("sql--"+sql);
		
		try {
			return meetingAccessLogDao.findPagerBySql(sql, currentPage, pageSize, properties);
		} catch (Exception e) {
			final String errMsg = "Find meeting pager error! ";
			log.error(errMsg, e);
			throw new ServiceException(errMsg, e);
		}
	}

	public MeetingDao getMeetingDao() {
		return meetingDao;
	}

	public void setMeetingDao(MeetingDao meetingDao) {
		this.meetingDao = meetingDao;
	}
}

package com.wondertek.meeting.dao;

import java.util.List;
import java.util.Map;

import com.wondertek.meeting.model.MeetingAccessLog;

/**
 * 会议访问记录
 * 
 * @author 金祝华
 */
public interface MeetingAccessLogDao extends BaseDao<MeetingAccessLog, Long> {

	/**
	 * 查询会议访问总统计
	 * 
	 * @param adminUserId
	 * @return
	 */
	public List querySum(final String sql, final Map<String, Object> properties);
}

package com.wondertek.meeting.dao;

import java.util.List;
import java.util.Map;

import com.wondertek.meeting.model.Meeting;
import com.wondertek.meeting.model.User;

public interface MeetingDao extends BaseDao<Meeting, Long> {
	public List<User> queryUsers(Long meetingId,Long orgId);
	
	public List getMyAttendMeetingList(String userId,String hql,Map<String, Object> properties);
	
	public String getCityName(String meetingId);
	
	/**
	 * 查询某组织下用户创建的所有会议id列表
	 * 
	 * @param orgId
	 * @return
	 */
	public List<Long> queryMeetingIdsByOrgId(Long orgId);

	/**
	 * 查询某用户创建的所有会议id列表
	 * 
	 * @param orgId
	 * @return
	 */
	public List<Long> queryMeetingIdsByUserId(Long userId);
	

	public Meeting findById(Long id);
}

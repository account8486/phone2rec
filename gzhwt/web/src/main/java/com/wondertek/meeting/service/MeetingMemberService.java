package com.wondertek.meeting.service;

import java.util.List;

import com.wondertek.meeting.exception.HibernateDaoSupportException;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.MeetingMember;

/**
 * 会议成员
 * 
 * @author 金祝华
 */
public interface MeetingMemberService extends BaseService<MeetingMember, Long> {

	/**
	 * 根据会议id查询会议成员列表
	 * 
	 * @param meetingId
	 * @param userId
	 * @return
	 */
	public List<MeetingMember> queryMemberList(Long meetingId);
	
	/**
	 * 查询会议成员
	 * 
	 * @param uuid
	 * @param meetingId
	 * @return
	 */
	public MeetingMember selectById(Long userId, Long meetingId);
	
	/**
	 * 删除级联用户及相关信息
	 * 分桌 分组信息
	 * @throws ServiceException 
	 */
	public void deleteUserCascade(MeetingMember meetingMember) throws ServiceException;
	
	
	/**
	 * 通过会议ID删除会议成员
	 * @param meetingId
	 * @throws HibernateDaoSupportException
	 */
	public void deleteMeetingMemberByMeetingId(Long meetingId) throws HibernateDaoSupportException;
	
	/**
	 * 查询会议下参会人员
	 * 
	 * @param meetingId
	 * @return
	 */
	public List<MeetingMember> queryList(Long meetingId);
}

package com.wondertek.meeting.service.impl;

import java.util.List;

import com.wondertek.meeting.common.SignInHelper;
import com.wondertek.meeting.dao.MeetingMemberDao;
import com.wondertek.meeting.exception.HibernateDaoSupportException;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.MeetingMember;
import com.wondertek.meeting.service.MeetingMemberService;

/**
 * 会议成员
 * 
 * @author 金祝华
 */
public class MeetingMemberServiceImpl extends BaseServiceImpl<MeetingMember, Long> implements MeetingMemberService {

	private MeetingMemberDao meetingMemberDao;
	
	/**
	 * 根据会议id查询会议成员列表
	 * 
	 * @param meetingId
	 * @param userId
	 * @return
	 */
	public List<MeetingMember> queryMemberList(Long meetingId)
	{
		return meetingMemberDao.queryMemberList(meetingId);
	}
	
	/**
	 * 查询会议成员
	 * 
	 * @param uuid
	 * @param meetingId
	 * @return
	 */
	public MeetingMember selectById(Long userId, Long meetingId) {
		return meetingMemberDao.selectById(userId, meetingId);
	}

	public MeetingMemberDao getMeetingMemberDao() {
		return meetingMemberDao;
	}

	public void setMeetingMemberDao(MeetingMemberDao meetingMemberDao) {
		this.basicDao=meetingMemberDao;
		this.meetingMemberDao = meetingMemberDao;
	}

	/**
	 * 删除级联用户及相关信息
	 * 分桌 分组信息
	 * @throws ServiceException 
	 */
	public void deleteUserCascade(MeetingMember meetingMember) throws ServiceException{
		//首先删除
		this.delete(meetingMember);
		log.debug("删除后实体值meetingMember:meetingMember：MEETINGID"+meetingMember.getMeetingId()+",USERID:"+meetingMember.getUserId());
		
		//删除签到信息
		SignInHelper.getInstance().delSignCode(meetingMember.getMeetingId(),
				meetingMember.getUserId());
		
		//删除用户信息
		meetingMemberDao.deleteUserRelationInfo(meetingMember.getUserId(),meetingMember.getMeetingId());
		//删除此住宿信息
		meetingMemberDao.deleteHotemRoomUserByUserId(meetingMember.getMeetingId(), meetingMember.getUserId());
	}
	
	/**
	 * 通过会议ID删除会议成员
	 * @param meetingId
	 * @throws HibernateDaoSupportException
	 */
	public void deleteMeetingMemberByMeetingId(Long meetingId) throws HibernateDaoSupportException{
		meetingMemberDao.deleteMeetingMemberByMeetingId(meetingId);
	}
	
	/**
	 * 查询会议下参会人员
	 * 
	 * @param meetingId
	 * @return
	 */
	public List<MeetingMember> queryList(Long meetingId) {
		return meetingMemberDao.queryList(meetingId);
	}
}

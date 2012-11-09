package com.wondertek.meeting.dao;

import java.util.List;

import com.wondertek.meeting.model.SignIn;

/**
 * 签到
 * 
 * @author 金祝华
 */
public interface SignInDao extends BaseDao<SignIn, Long> {

	/**
	 * 根据会议id查询签到记录
	 * 
	 * @param meetingId
	 * @param userId
	 * @return
	 */
	public List<SignIn> querySignInList(Long meetingId);

	/**
	 * 根据会议id、用户id查询签到记录
	 * 
	 * @param meetingId
	 * @param userId
	 * @return
	 */
	public SignIn querySignIn(Long meetingId, Long userId);
	
	/**
	 * 删除会议的签到码
	 * 
	 * @param meetingId
	 * @return
	 */
	public int delSignCodeByMeetingId(Long meetingId);
	
	/**
	 * 删除单个签到码
	 * 
	 * @param meetingId
	 * @return
	 */
	public int delSignCode(Long meetingId, Long userId);
}

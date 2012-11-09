package com.wondertek.meeting.service;

import java.util.List;

import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.SignIn;

/**
 * 签到
 * 
 * @author 金祝华
 */
public interface SignInService extends BaseService<SignIn, Long> {

	/**
	 * 根据会议id查询签到记录
	 * 
	 * @param meetingId
	 * @param userId
	 * @return
	 */
	public List<SignIn> querySignInList(Long meetingId);

	/**
	 * 列表查询用户
	 * 
	 * @param user
	 * @param currentPage
	 * @param pageSize
	 * @return
	 * @throws ServiceException
	 */
	public Pager<SignIn> findPager(SignIn signIn, String mobile,String name, int currentPage, int pageSize) throws ServiceException;

	/**
	 * 根据会议id、用户id查询签到记录
	 * 
	 * @param meetingId
	 * @param userId
	 * @return
	 */
	public SignIn querySignIn(Long meetingId, Long userId);

	/**
	 * 签到（管理员用）
	 * 
	 * @param signIn
	 */
	public void signIn(SignIn signIn) throws ServiceException;
	
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

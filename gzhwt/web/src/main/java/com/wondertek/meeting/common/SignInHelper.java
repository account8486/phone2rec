package com.wondertek.meeting.common;

import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.SignIn;
import com.wondertek.meeting.model.User;
import com.wondertek.meeting.service.SignInService;

/**
 * 签到
 * 
 * @author 金祝华
 */
public class SignInHelper {

	private static volatile SignInHelper signInHelper = null;

	private SignInService signInService;

	private SignInHelper() {
	}

	public static SignInHelper getInstance() {
		if (signInHelper == null) {
			synchronized (SignInHelper.class) {
				if (signInHelper == null) {
					signInHelper = new SignInHelper();
					signInHelper.setSignInService((SignInService) ApplicationContextHelper.getBean("signInService"));
				}
			}
		}

		return signInHelper;
	}

	/**
	 * 生成签到码
	 * 
	 * @param meetingId
	 * @param userId
	 */
	public void genSignCode(Long meetingId, Long userId) {

		SignIn signIn = new SignIn();
		signIn.setMeetingId(meetingId); // 会议id
		User user = new User();
		user.setId(userId);
		signIn.setUser(user);// 用户id
		signIn.setSignCode(SysUtil.genSignCode());// 签到码

		SignIn temp = signInService.querySignIn(meetingId, userId);

		if (temp != null) {
			return; // 已经存在的不生成。
		}

		try {
			signInService.saveOrUpdate(signIn);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除会议的签到码
	 * 
	 * @param meetingId
	 * @param userId
	 */
	public void delSignCodeByMeetingId(Long meetingId) {
		signInService.delSignCodeByMeetingId(meetingId);
	}

	/**
	 * 删除单个签到码
	 * 
	 * @param meetingId
	 * @return
	 */
	public void delSignCode(Long meetingId, Long userId) {
		try {
			signInService.delSignCode(meetingId, userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public SignInService getSignInService() {
		return signInService;
	}

	public void setSignInService(SignInService signInService) {
		this.signInService = signInService;
	}
}

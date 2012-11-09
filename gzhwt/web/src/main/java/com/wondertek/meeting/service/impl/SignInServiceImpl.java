package com.wondertek.meeting.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wondertek.meeting.common.ExceptionCode;
import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.common.SysConstants;
import com.wondertek.meeting.dao.SignInDao;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.SignIn;
import com.wondertek.meeting.service.SignInService;
import com.wondertek.meeting.util.StringUtil;

/**
 * 签到
 * 
 * @author 金祝华
 */
public class SignInServiceImpl extends BaseServiceImpl<SignIn, Long> implements SignInService {

	private SignInDao signInDao;

	/**
	 * 根据会议id查询签到记录
	 * 
	 * @param meetingId
	 * @param userId
	 * @return
	 */
	public List<SignIn> querySignInList(Long meetingId) {
		return signInDao.querySignInList(meetingId);
	}

	/**
	 * 列表查询用户
	 * 
	 * @param user
	 * @param currentPage
	 * @param pageSize
	 * @return
	 * @throws ServiceException
	 */
	public Pager<SignIn> findPager(SignIn signIn, String mobile,String name, int currentPage, int pageSize) throws ServiceException {
		String hql = "from SignIn s left join fetch s.user u where s.meetingId=:meetingId and u.id in"
				+ "(select userId from MeetingMember m where m.meetingId= :meetingId )";
		Map<String, Object> properties = new HashMap<String, Object>();

		if (StringUtil.isNotEmpty(mobile)) {
			hql += " and u.mobile like '%'||:mobile||'%'";
			properties.put("mobile", mobile);
		}
		
		if (StringUtil.isNotEmpty(name)) {
			hql += " and u.name like '%'||:name||'%'";
			properties.put("name", name);
		}


		hql += " order by s.id desc";

		properties.put("meetingId", signIn.getMeetingId());
		try {
			return signInDao.findPager(hql, currentPage, pageSize, properties);
		} catch (Exception e) {
			final String errMsg = "Find signIn pager error ";
			log.error(errMsg, e);
			throw new ServiceException(errMsg, e);
		}
	}

	/**
	 * 根据会议id、用户id查询签到记录
	 * 
	 * @param meetingId
	 * @param userId
	 * @return
	 */
	public SignIn querySignIn(Long meetingId, Long userId) {
		return signInDao.querySignIn(meetingId, userId);
	}

	/**
	 * 签到（管理员用）
	 * 
	 * @param signIn
	 */
	public void signIn(SignIn signIn) throws ServiceException {

		SignIn sign = signInDao.querySignIn(signIn.getMeetingId(), signIn.getUser().getId());

		// 记录不存在
		if (sign == null) {
			throw new ServiceException(ExceptionCode.SIGN_IN_UNEXIST);
		}

		// 非客户端校验签到码
		if (!SysConstants.PORTAL_TYPE_CLIENT.equals(signIn.getPortalType())) {
			// 签到码有误
			if (!signIn.getSignCode().equals(sign.getSignCode())) {
				throw new ServiceException(ExceptionCode.SIGN_IN_ERROR_SIGN_CODE);
			}
		}

		if (sign.getSignTime() != null) {
			throw new ServiceException(ExceptionCode.SIGN_IN_ALREADY_SIGN);
		}

		sign.setSignTime(signIn.getSignTime());
		sign.setPortalType(signIn.getPortalType());

		this.signInDao.saveOrUpdateEntity(sign);
	}

	/**
	 * 删除会议的签到码
	 * 
	 * @param meetingId
	 * @return
	 */
	public int delSignCodeByMeetingId(Long meetingId) {
		return signInDao.delSignCodeByMeetingId(meetingId);
	}
	
	/**
	 * 删除单个签到码
	 * 
	 * @param meetingId
	 * @return
	 */
	public int delSignCode(Long meetingId, Long userId) {
		return signInDao.delSignCode(meetingId, userId);
	}

	public SignInDao getSignInDao() {
		return signInDao;
	}

	public void setSignInDao(SignInDao signInDao) {
		this.basicDao = signInDao;
		this.signInDao = signInDao;
	}
}

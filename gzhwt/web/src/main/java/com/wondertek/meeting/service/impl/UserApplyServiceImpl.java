package com.wondertek.meeting.service.impl;

import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.dao.UserApplyDao;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.UserApply;
import com.wondertek.meeting.service.UserApplyService;

public class UserApplyServiceImpl extends BaseServiceImpl<UserApply, Long>
		implements UserApplyService {

	UserApplyDao userApplyDao;

	public void setUserApplyDao(UserApplyDao userApplyDao) {
		this.basicDao=userApplyDao;
		this.userApplyDao = userApplyDao;
		
	}

	/**
	 * 通过MEETING ID来进行查询
	 * @param currentPage
	 * @param pageSize
	 * @param meetingId
	 * @return
	 */
	public Pager<UserApply> getUserApplyPager(int currentPage, int pageSize,
			Long meetingId) {
		StringBuffer hql = new StringBuffer(" from UserApply userApply where 1=1 and  meetingId="
				+ meetingId);
		Pager<UserApply> pager = null;
		try {
			pager = this.findPager(hql.toString(), currentPage, pageSize, null);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		
		return pager;
	}

}

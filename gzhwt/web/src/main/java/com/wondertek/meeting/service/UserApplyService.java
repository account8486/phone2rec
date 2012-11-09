package com.wondertek.meeting.service;

import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.model.UserApply;

public interface UserApplyService extends BaseService<UserApply, Long> {
	
	/**
	 * 通过MEETING ID来进行查询
	 * @param currentPage
	 * @param pageSize
	 * @param meetingId
	 * @return
	 */
	public Pager<UserApply> getUserApplyPager(int currentPage, int pageSize,
			Long meetingId);
	

}

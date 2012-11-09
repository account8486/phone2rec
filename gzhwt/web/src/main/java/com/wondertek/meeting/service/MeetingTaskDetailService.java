package com.wondertek.meeting.service;

import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.model.MeetingTaskDetail;

public interface MeetingTaskDetailService extends BaseService<MeetingTaskDetail, Long> {
	

	/**
	 * 获取列表
	 * 
	 * @param meetingId
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public Pager<MeetingTaskDetail> getListPager(String meetingId,
		 int currentPage, int pageSize,String parentId,String myTaskFlag,String adminId,String taskDetailName);

}

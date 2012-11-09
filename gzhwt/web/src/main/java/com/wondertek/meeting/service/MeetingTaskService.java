package com.wondertek.meeting.service;

import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.model.MeetingTask;

public interface MeetingTaskService extends BaseService<MeetingTask, Long> {
	
	public Pager<MeetingTask> getListPager(String meetingId, int currentPage,
			int pageSize);
	

}

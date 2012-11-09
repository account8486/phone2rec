package com.wondertek.meeting.service.impl;

import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.dao.MeetingTaskDao;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.MeetingTask;
import com.wondertek.meeting.service.MeetingTaskService;

public class MeetingTaskServiceImpl extends BaseServiceImpl<MeetingTask, Long>
		implements MeetingTaskService {
	
	MeetingTaskDao meetingTaskDao ;

	public MeetingTaskDao getMeetingTaskDao() {
		return meetingTaskDao;
	}

	public void setMeetingTaskDao(MeetingTaskDao meetingTaskDao) {
		this.basicDao=meetingTaskDao;
		this.meetingTaskDao = meetingTaskDao;
	}
	
	
	/**
	 * 获取列表
	 * @param meetingId
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public Pager<MeetingTask> getListPager(String meetingId, int currentPage,
			int pageSize) {
		Pager<MeetingTask> pager = null;
		
		try {
			
			StringBuffer hql = new StringBuffer();
			hql.append("  from MeetingTask task where 1=1 and  meetingId= "
					+ meetingId);

			pager=this.findPager(hql.toString(), currentPage, pageSize, null);

		} catch (ServiceException e) {
			e.printStackTrace();
		}
		
		

		return pager;

	}

	
	
}

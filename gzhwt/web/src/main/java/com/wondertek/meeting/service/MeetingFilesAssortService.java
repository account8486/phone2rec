package com.wondertek.meeting.service;

import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.model.MeetingFilesAssort;

public interface MeetingFilesAssortService extends BaseService<MeetingFilesAssort, Long> {
	
	public Pager<MeetingFilesAssort> getMeetingFilesAssortPages(int currentPage,
            int pageSize, Long meetingId,String assortName);
	

}

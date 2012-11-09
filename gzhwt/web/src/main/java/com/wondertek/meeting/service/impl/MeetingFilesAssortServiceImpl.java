package com.wondertek.meeting.service.impl;

import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.dao.MeetingFilesAssortDao;
import com.wondertek.meeting.exception.HibernateDaoSupportException;
import com.wondertek.meeting.model.MeetingFilesAssort;
import com.wondertek.meeting.service.MeetingFilesAssortService;
import com.wondertek.meeting.util.StringUtil;

public class MeetingFilesAssortServiceImpl extends
		BaseServiceImpl<MeetingFilesAssort, Long> implements
		MeetingFilesAssortService {
	
	MeetingFilesAssortDao meetingFilesAssortDao;

	public MeetingFilesAssortDao getMeetingFilesAssortDao() {
		return meetingFilesAssortDao;
	}

	public void setMeetingFilesAssortDao(MeetingFilesAssortDao meetingFilesAssortDao) {
		this.basicDao=meetingFilesAssortDao;
		this.meetingFilesAssortDao = meetingFilesAssortDao;
	}
	
	public Pager<MeetingFilesAssort> getMeetingFilesAssortPages(int currentPage,
            int pageSize, Long meetingId,String assortName){
		
	    Pager<MeetingFilesAssort> pager = null;
		  
		StringBuffer hql=new StringBuffer();
		hql.append("   from MeetingFilesAssort where 1=1  ");
		hql.append(" and   meetingId="+meetingId);
		
		if(StringUtil.isNotEmpty(assortName)){
			hql.append(" and   assortName like '%"+assortName+"%'");
		}
		
		try {
			pager=meetingFilesAssortDao.findPager(hql.toString(), currentPage, pageSize, null);
		} catch (HibernateDaoSupportException e) {
			e.printStackTrace();
		}
		
		return pager;
	} 

}

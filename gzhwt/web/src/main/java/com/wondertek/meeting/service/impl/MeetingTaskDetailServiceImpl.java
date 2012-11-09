package com.wondertek.meeting.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.dao.MeetingTaskDetailDao;
import com.wondertek.meeting.exception.HibernateDaoSupportException;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.MeetingTaskDetail;
import com.wondertek.meeting.service.MeetingTaskDetailService;
import com.wondertek.meeting.util.StringUtil;

public class MeetingTaskDetailServiceImpl extends
		BaseServiceImpl<MeetingTaskDetail, Long> implements
		MeetingTaskDetailService {

	MeetingTaskDetailDao meetingTaskDetailDao;

	public MeetingTaskDetailDao getMeetingTaskDetailDao() {
		return meetingTaskDetailDao;
	}

	public void setMeetingTaskDetailDao(
			MeetingTaskDetailDao meetingTaskDetailDao) {
		this.basicDao = meetingTaskDetailDao;
		this.meetingTaskDetailDao = meetingTaskDetailDao;
	}

	/**
	 * 获取列表
	 * 
	 * @param meetingId
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public Pager<MeetingTaskDetail> getListPager(String meetingId,
			int currentPage, int pageSize,String parentId,String myTaskFlag
			,String adminId,String taskDetailName) {
		Pager<MeetingTaskDetail> pager = null;
		
		try {
			StringBuffer hql = new StringBuffer();
			hql.append("  from MeetingTaskDetail detail where 1=1 and  meetingId= " + meetingId);
			
			if(StringUtil.isNotEmpty(parentId)){
				hql.append("  and parentId is not null and parentId="+parentId);
			}else{
				hql.append("  and parentId is not null and parentId=0");
			}
			
			if(StringUtil.isNotEmpty(myTaskFlag)){
				hql.append("  and charge like '%"+adminId+","+"%'");
			}
			
			if(StringUtil.isNotEmpty(taskDetailName)){
				hql.append("  and detailName like '%"+taskDetailName+"%'");
			}
			
			log.info("task detail list query:"+hql.toString());
			
			pager = this.findPager(hql.toString(), currentPage, pageSize, null);
			
			//通过父任务来查询子任务 让父子任务显示在同一级页面里面
			List<MeetingTaskDetail> fatherTaskDetailList=pager.getPageRecords();
			for(MeetingTaskDetail fatherTaskDetail:fatherTaskDetailList){
				fatherTaskDetail.setSonMeetingTaskDetailList(this.getSonMeetingTaskDetailList(fatherTaskDetail));
			}
			
		} catch (ServiceException e) {
			e.printStackTrace();
		}

		return pager;
	}
	
	
	/**
	 * 获取子任务列表
	 * @param fatherTaskDetail
	 * @return
	 */
	private List<MeetingTaskDetail>  getSonMeetingTaskDetailList(MeetingTaskDetail fatherTaskDetail){
		
	
		List<MeetingTaskDetail>  sonMeetingTaskDetailList=null;
		try {
			
			StringBuffer hql = new StringBuffer();
			hql.append("  from MeetingTaskDetail detail where 1=1 and  meetingId= " + fatherTaskDetail.getMeetingId());
			//父ID来查询
			hql.append("  and parentId is not null and parentId="+fatherTaskDetail.getId());
			
			sonMeetingTaskDetailList=this.meetingTaskDetailDao.getObjects(hql.toString());
		} catch (HibernateDaoSupportException e) {
			e.printStackTrace();
		}
		return sonMeetingTaskDetailList;
	}
	
	

}

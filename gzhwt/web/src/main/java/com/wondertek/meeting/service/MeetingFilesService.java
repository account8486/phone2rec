package com.wondertek.meeting.service;

import java.util.List;

import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.exception.HibernateDaoSupportException;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.MeetingFiles;

public interface MeetingFilesService extends BaseService<MeetingFiles, Integer>  {
	
	public List<MeetingFiles> getMeetingFilesList(MeetingFiles meetingFiles,Integer memberLevel) throws HibernateDaoSupportException;

    public List<MeetingFiles> getUnconvertMeetingFilesList() throws HibernateDaoSupportException;



	public void saveMeetingFiles(MeetingFiles meetingFiles) throws HibernateDaoSupportException;
	
	public Pager<MeetingFiles> getMeetingFilesPager(int currentPage,
			int pageSize,MeetingFiles meetingFiles,String memberLevel);
	
	  /**
	 * 删除会议下资料
	 * @param meetingId
	 * @throws ServiceException 
	 */
	public void deleteMeetingFilesByMeetingId(Long meetingId) throws ServiceException;
	
	/**
	 * 查询某个会议下有效的资料
	 * @author zouxiaoming
	 * @param meetingId
	 * @return
	 */
	public List<MeetingFiles> findMeetingFiles(Long meetingId) throws HibernateDaoSupportException;


	
}

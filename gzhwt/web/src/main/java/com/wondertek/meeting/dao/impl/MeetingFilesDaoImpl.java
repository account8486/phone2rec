package com.wondertek.meeting.dao.impl;

import java.util.HashMap;

import com.wondertek.meeting.dao.MeetingFilesDao;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.MeetingFiles;

public class MeetingFilesDaoImpl extends BaseDaoImpl<MeetingFiles, Integer> implements
		MeetingFilesDao {

	/**
	 * 删除会议下资料
	 * @param meetingId
	 * @throws ServiceException 
	 */
	public void deleteMeetingFilesByMeetingId(Long meetingId) throws ServiceException{
		String hql="delete from MeetingFiles where meetingId="+meetingId;
		log.info("删除会议下资料:"+hql);
		this.executeUpdate(hql, new HashMap());
	}
	
}

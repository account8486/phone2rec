package com.wondertek.meeting.dao;

import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.MeetingFiles;

public interface MeetingFilesDao extends BaseDao<MeetingFiles, Integer> {
	/**
	 * 删除会议下资料
	 * @param meetingId
	 * @throws ServiceException 
	 */
	public void deleteMeetingFilesByMeetingId(Long meetingId) throws ServiceException;

}

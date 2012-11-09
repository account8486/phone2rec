package com.wondertek.meeting.dao;

import java.util.List;

import com.wondertek.meeting.model.MeetingModuleTitle;

/**
 *JSP tag
 * 
 * @author chif
 */
public interface MeetingModuleTitleDao extends BaseDao<MeetingModuleTitle, Long>  {
	public List<MeetingModuleTitle> query(MeetingModuleTitle meetingModuleTitle);
}

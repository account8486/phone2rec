package com.wondertek.meeting.service;


import java.util.List;

import com.wondertek.meeting.model.MeetingModuleTitle;

/**
 * 
 * @author chif
 *
 */
public interface MeetingModuleTitleService extends BaseService<MeetingModuleTitle, Long> {
	public List<MeetingModuleTitle> query(MeetingModuleTitle meetingModuleTitle);
}

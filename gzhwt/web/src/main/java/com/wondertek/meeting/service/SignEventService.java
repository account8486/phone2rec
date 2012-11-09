package com.wondertek.meeting.service;


import java.util.List;

import com.wondertek.meeting.model.SignEvent;


/**
 * 签到事件管理
* @ClassName: SignEventService 
* @Description: TODO
* @author zouxiaoming
* @date Aug 9, 2012 1:39:40 PM 
*
 */
public interface SignEventService extends BaseService<SignEvent, Long> {
	
	public List<SignEvent> findByMeetingId(Long meetingId);
	
	public List<SignEvent> findByMeetingId(Long meetingId, String date);
	
	public boolean deleteSignEvent(Long id);
	
}

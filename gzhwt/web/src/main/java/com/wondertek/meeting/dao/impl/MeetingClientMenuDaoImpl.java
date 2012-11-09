package com.wondertek.meeting.dao.impl;

import java.util.HashMap;

import com.wondertek.meeting.dao.MeetingClientMenuDao;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.MeetingClientMenu;


public class MeetingClientMenuDaoImpl extends BaseDaoImpl<MeetingClientMenu, Long> implements MeetingClientMenuDao {
	
	/**
	 * 删除会议下的菜单配置
	 * @param meetingId
	 * @throws ServiceException 
	 */
	public void deleteMeetingClientMenuByMeetingId(Long meetingId) throws ServiceException{
		//删除某个会议下得MEETING_MEMBER
    	String sql = "delete From MeetingClientMenu where meetingId ="+ meetingId;
    	log.info("删除会议下的菜单配置:"+sql);
    	executeUpdate(sql, new HashMap());
	}
	
}

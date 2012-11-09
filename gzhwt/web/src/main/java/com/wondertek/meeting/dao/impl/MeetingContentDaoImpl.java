package com.wondertek.meeting.dao.impl;

import java.util.HashMap;
import java.util.Map;

import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.dao.MeetingContentDao;
import com.wondertek.meeting.exception.HibernateDaoSupportException;
import com.wondertek.meeting.model.MeetingContent;

public class MeetingContentDaoImpl extends BaseDaoImpl<MeetingContent, Long> implements MeetingContentDao{

	/**根据类型查询景点和酒店信息*/
	public Pager<MeetingContent> findMeetingContentPager(Long meetingId,int type,
			int currentPage, int pageSize, String title)
			throws HibernateDaoSupportException {
		String hql = "from MeetingContent where contentType = :type and meetingId = :meetingId ";
		Map<String,Object> properties = new HashMap<String,Object>();
		properties.put("type", type);
		properties.put("meetingId", meetingId);
		if(title != null && title.length()>0){
			hql += " and title like :title ";
			properties.put("title", "%"+title+"%");
		}
		return findPager(hql, currentPage, pageSize, properties);
	}

}

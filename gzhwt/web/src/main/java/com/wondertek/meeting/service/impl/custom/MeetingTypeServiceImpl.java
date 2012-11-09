/**
 * 会议云类型相关操作Service实现类
 * Author: 张国敬
 * Copyrights: 版权归上海网达软件有限公司安徽分公司所有
 * Create at: Jan 13, 2012
 */
package com.wondertek.meeting.service.impl.custom;

import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.dao.custom.MeetingTypeDao;
import com.wondertek.meeting.model.custom.MeetingType;
import com.wondertek.meeting.service.custom.MeetingTypeService;
import com.wondertek.meeting.service.impl.BaseServiceImpl;

public class MeetingTypeServiceImpl extends BaseServiceImpl<MeetingType, Integer> 
	implements MeetingTypeService {

	private MeetingTypeDao meetingTypeDao;

	
	/*
	 * 查看会议云类型列表
	 * meetingType: 查询条件
	 */
	public Pager<MeetingType> findAllMeetingTypePager(
			MeetingType meetingType, 
			int currentPage, 
			int pageSize) throws Exception {
		return this.meetingTypeDao.findAllMeetingTypePager(meetingType, currentPage, pageSize);
	}


	public void setMeetingTypeDao(MeetingTypeDao meetingTypeDao) {
		super.setBaseDao(meetingTypeDao);
		this.meetingTypeDao = meetingTypeDao;
	}
}

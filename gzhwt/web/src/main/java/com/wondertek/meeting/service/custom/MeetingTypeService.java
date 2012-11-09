/**
 * 会议云类型相关Service
 * Author: 张国敬
 * Copyrights: 版权归上海网达软件有限公司安徽分公司所有
 * Create at: Jan 13, 2012
 */
package com.wondertek.meeting.service.custom;

import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.model.custom.MeetingType;
import com.wondertek.meeting.service.BaseService;

public interface MeetingTypeService extends
		BaseService<MeetingType, Integer> {
	/*
	 * 查看会议云类型列表
	 * meetingType: 查询条件
	 */
	public Pager<MeetingType> findAllMeetingTypePager(
			MeetingType meetingType, 
			int currentPage, 
			int pageSize) throws Exception;
}

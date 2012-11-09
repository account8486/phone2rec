/**
 * 会议云类型操作DAO
 * Author: 张国敬
 * Copyrights: 版权归上海网达软件有限公司安徽分公司所有
 * Create at: Jan 13, 2012
 */
package com.wondertek.meeting.dao.custom;

import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.dao.BaseDao;
import com.wondertek.meeting.model.custom.MeetingType;

public interface MeetingTypeDao extends BaseDao<MeetingType, Integer> {
	/*
	 * 查看会议云类型列表
	 * meetingType: 查询条件
	 */
	public Pager<MeetingType> findAllMeetingTypePager(
			MeetingType meetingType, 
			int currentPage, 
			int pageSize) throws Exception ;

	public MeetingType findById(Integer id);
}

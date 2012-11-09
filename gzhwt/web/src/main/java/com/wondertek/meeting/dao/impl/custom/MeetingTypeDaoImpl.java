/**
 * 会议云类型相关DAO实现类
 * Author: 张国敬
 * Copyrights: 版权归上海网达软件有限公司安徽分公司所有
 * Create at: Jan 13, 2012
 */
package com.wondertek.meeting.dao.impl.custom;

import java.util.HashMap;
import java.util.Map;

import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.dao.custom.MeetingTypeDao;
import com.wondertek.meeting.dao.impl.BaseDaoImpl;
import com.wondertek.meeting.model.custom.MeetingType;

public class MeetingTypeDaoImpl extends
		BaseDaoImpl<MeetingType, Integer> implements MeetingTypeDao {
	/*
	 * 查看会议云类型列表
	 * meetingType: 查询条件
	 */
	public Pager<MeetingType> findAllMeetingTypePager(
			MeetingType meetingType, 
			int currentPage, 
			int pageSize) throws Exception {
		String hql = "from MeetingType mt where mt.name like :name " +
				"order by mt.id desc";
		Map<String, Object> param = new HashMap<String, Object>();
		String name = meetingType != null && meetingType.getName() != null ? meetingType.getName() : "";
		param.put("name", "%"+name +"%");
		return this.findPager(hql, currentPage, pageSize, param);
	}
	
	public MeetingType findById(Integer id) {
		return this.getHibernateTemplate().get(MeetingType.class, id);
	}
}

package com.wondertek.meeting.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wondertek.meeting.dao.GroupPlanDetailDao;
import com.wondertek.meeting.exception.HibernateDaoSupportException;
import com.wondertek.meeting.model.GroupPlanDetail;
import com.wondertek.meeting.service.GroupPlanDetailService;

/**
 * 
 * @author John Tang
 */
public class GroupPlanDetailServiceImpl extends BaseServiceImpl<GroupPlanDetail, Long> implements GroupPlanDetailService {
	
	private GroupPlanDetailDao groupPlanDetailDao;

	public GroupPlanDetailDao getGroupPlanDetailDao() {
		return groupPlanDetailDao;
	}

	public void setGroupPlanDetailDao(GroupPlanDetailDao groupPlanDetailDao) {
		this.basicDao = groupPlanDetailDao;
		this.groupPlanDetailDao = groupPlanDetailDao;
	}
	
	/**
	 * 通过用户id 及计划类型来区分
	 * @param meetingId
	 * @param userId
	 * @param planType
	 * @return
	 * @throws HibernateDaoSupportException
	 */
	public List<GroupPlanDetail> getDetailByMeetingUserId(Long meetingId,
			Long userId, Integer planType) throws HibernateDaoSupportException {
		StringBuffer hql = new StringBuffer();
		
		hql.append(" SELECT  gd FROM GroupPlanMember gm,GroupPlan gp,GroupPlanDetail gd  ");
		hql.append(" WHERE gm.planId=gp.id  and gm.groupId=gd.id  and gm.meetingId=:meetingId  ");
		hql.append(" and  gm.memberId=:userId  ");
		hql.append(" and gp.planType=:planType");
		
		//查询参数
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("meetingId",meetingId );
		properties.put("userId",userId );
		properties.put("planType",planType );
		
		List<GroupPlanDetail> detailList = groupPlanDetailDao.getObjects(
				hql.toString(), properties);

		return detailList;

	}
	
}

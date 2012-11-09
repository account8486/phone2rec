package com.wondertek.meeting.service;

import java.util.List;

import com.wondertek.meeting.exception.HibernateDaoSupportException;
import com.wondertek.meeting.model.GroupPlanDetail;

/**
 * @author John Tang
 */
public interface GroupPlanDetailService extends
		BaseService<GroupPlanDetail, Long> {

	/**
	 * 通过用户id 及计划类型来区分
	 * 
	 * @param meetingId
	 * @param userId
	 * @param planType
	 * @return
	 * @throws HibernateDaoSupportException
	 */
	public List<GroupPlanDetail> getDetailByMeetingUserId(Long meetingId,
			Long userId, Integer planType) throws HibernateDaoSupportException;

}

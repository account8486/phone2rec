package com.wondertek.meeting.service;

import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.MeetingGroup;

/**
 * @author John Tang
 */
public interface MeetingGroupService extends BaseService<MeetingGroup, Long> {
	
	/**更新meetingGroup信息*/
	public void updateByRelation(Long newPlanId,int type,Long relationId) throws ServiceException;
}

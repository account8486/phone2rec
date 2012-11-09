package com.wondertek.meeting.service;

import java.util.List;

import com.wondertek.meeting.model.JourneyMember;

public interface JourneyMemberService extends BaseService<JourneyMember, Long> {
	
	/**
	 * 通过行程ID查询出行程乘客信息
	 * @param journeyId
	 * @return
	 */
	public List<JourneyMember> getJourneyMemberByJourney(Long journeyId);
	
	
	/**
	 * 通过行程ID及用户ID来判断是否存在当前用户
	 * 
	 * @param journeyId
	 * @param userId
	 * @return
	 */
	public JourneyMember selectJourneyMember(Long journeyId, Long userId);
	
	/**
	 * 删除某一行程下的人员
	 * @param journeyId
	 */
	public void deleteJourneyMemberByJourneyId(Long journeyId);
	
	
}

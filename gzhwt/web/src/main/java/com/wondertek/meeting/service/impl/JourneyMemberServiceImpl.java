package com.wondertek.meeting.service.impl;

import java.util.List;

import com.wondertek.meeting.dao.JourneyMemberDao;
import com.wondertek.meeting.model.JourneyMember;
import com.wondertek.meeting.service.JourneyMemberService;

public class JourneyMemberServiceImpl extends
		BaseServiceImpl<JourneyMember, Long> implements JourneyMemberService {
	JourneyMemberDao journeyMemberDao;

	public JourneyMemberDao getJourneyMemberDao() {
		return journeyMemberDao;
	}

	public void setJourneyMemberDao(JourneyMemberDao journeyMemberDao) {
		this.basicDao = journeyMemberDao;
		this.journeyMemberDao = journeyMemberDao;
	}

	/**
	 * 通过行程ID查询出行程乘客信息
	 * @param journeyId
	 * @return
	 */
	public List<JourneyMember> getJourneyMemberByJourney(Long journeyId) {
		
		return journeyMemberDao.getJourneyMemberByJourney(journeyId);
	}
	
	

	/**
	 * 通过行程ID及用户ID来判断是否存在当前用户
	 * 
	 * @param journeyId
	 * @param userId
	 * @return
	 */
	public JourneyMember selectJourneyMember(Long journeyId, Long userId){
		return journeyMemberDao.selectJourneyMember(journeyId, userId);
	}
	
	/**
	 * 删除某一行程下的人员
	 * @param journeyId
	 */
	public void deleteJourneyMemberByJourneyId(Long journeyId){
	 journeyMemberDao.deleteJourneyMemberByJourneyId(journeyId);
	}

}

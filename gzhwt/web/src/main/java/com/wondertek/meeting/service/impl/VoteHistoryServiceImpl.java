package com.wondertek.meeting.service.impl;

import com.wondertek.meeting.dao.VoteHistoryDao;
import com.wondertek.meeting.model.VoteHistory;
import com.wondertek.meeting.service.VoteHistoryService;

/** 
 * @ClassName: VoteHistoryServiceImpl 
 * @Description: 投票历史
 * @author zouxiaoming
 * @date Jan 31, 2012 3:08:42 PM 
 *  
 */
public class VoteHistoryServiceImpl extends BaseServiceImpl<VoteHistory, Long> implements VoteHistoryService {
	private VoteHistoryDao voteHistoryDao;

	/**
	 * @param voteHistoryDao
	 */
	public void setVoteHistoryDao(VoteHistoryDao voteHistoryDao) {
		this.voteHistoryDao = voteHistoryDao;
		this.setBaseDao(voteHistoryDao);
	}

	/**
	 * @Description
	 * @return the voteHistoryDao
	 */
	public VoteHistoryDao getVoteHistoryDao() {
		return voteHistoryDao;
	}

	@Override
	public boolean validateVote(Long userId, Long voteId) {
		return this.voteHistoryDao.validateVote(userId, voteId);
	}
	
}

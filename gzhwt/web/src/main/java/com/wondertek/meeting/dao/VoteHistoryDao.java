package com.wondertek.meeting.dao;


import com.wondertek.meeting.model.VoteHistory;

/** 
 * @ClassName: VoteHistoryDao 
 * @Description: 投票历史
 * @author zouxiaoming
  * @date Jan 31, 2012 3:08:42 PM 
 *  
 */
public interface VoteHistoryDao extends BaseDao<VoteHistory, Long> {

	/**
	 * 查看用户是否投过指定的票
	 * @param userId
	 * @param voteId
	 * @return
	 */
	public boolean validateVote(Long userId,Long voteId);
	


	
}

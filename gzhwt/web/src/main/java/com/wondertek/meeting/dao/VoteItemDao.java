package com.wondertek.meeting.dao;


import java.util.List;

import com.wondertek.meeting.model.VoteItem;

/** 
 * @ClassName: VoteItemDao 
 * @Description: 投票选项
 * @author zouxiaoming
 * @date Jan 10, 2012 10:31:15 AM 
 *  
 */
public interface VoteItemDao extends BaseDao<VoteItem, Long> {

	public List<VoteItem> findItemByVoteId(Long voteId);

	
}

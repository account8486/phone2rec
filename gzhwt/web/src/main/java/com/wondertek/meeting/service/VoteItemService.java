package com.wondertek.meeting.service;

import java.util.List;

import com.wondertek.meeting.model.VoteItem;

/** 
 * @ClassName: VoteItemService 
 * @Description: 投票选项服务接口
 * @author zouxiaoming
 * @date Jan 10, 2012 10:31:15 AM 
 *  
 */
public interface VoteItemService extends BaseService<VoteItem, Long> {

	/**
	 * 通过投票Id查出下面所有的投票选项
	 * @param voteId
	 * @return
	 */
	public List<VoteItem> findItemByVoteId(Long voteId);
	


	
}

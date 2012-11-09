package com.wondertek.meeting.service.impl;
import java.util.List;

import com.wondertek.meeting.dao.VoteItemDao;
import com.wondertek.meeting.model.VoteItem;
import com.wondertek.meeting.service.VoteItemService;

/** 
 * @ClassName: VoteItemServiceImpl 
 * @Description: 投票选项服务接口实现类
 * @author zouxiaoming
 * @date Jan 10, 2012 10:31:15 AM 
 *  
 */
public class VoteItemServiceImpl extends BaseServiceImpl<VoteItem, Long> implements VoteItemService {
	private VoteItemDao voteItemDao;

	/**
	 * @param voteItemDao
	 */
	public void setVoteItemDao(VoteItemDao voteItemDao) {
		this.voteItemDao = voteItemDao;
		this.basicDao=voteItemDao;
	}

	/**
	 * @Description
	 * @return the voteItemDao
	 */
	public VoteItemDao getVoteItemDao() {
		return voteItemDao;
	}

	@Override
	public List<VoteItem> findItemByVoteId(Long voteId) {
		List<VoteItem> list=this.voteItemDao.findItemByVoteId(voteId);
		return list;
	}

	
}

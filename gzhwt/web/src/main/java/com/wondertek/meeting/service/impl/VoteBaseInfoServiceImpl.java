package com.wondertek.meeting.service.impl;
import java.util.List;

import com.wondertek.meeting.dao.VoteBaseInfoDao;
import com.wondertek.meeting.dao.VoteHistoryDao;
import com.wondertek.meeting.dao.VoteItemDao;
import com.wondertek.meeting.exception.HibernateDaoSupportException;
import com.wondertek.meeting.model.User;
import com.wondertek.meeting.model.VoteBaseInfo;
import com.wondertek.meeting.model.VoteHistory;
import com.wondertek.meeting.model.VoteItem;
import com.wondertek.meeting.service.VoteBaseInfoService;

/** 
 * @ClassName: VoteBaseInfoServiceImpl 
 * @Description: 投票基本信息服务接口实现类
 * @author zouxiaoming
 * @date Jan 9, 2012 10:31:15 AM 
 *  
 */
public class VoteBaseInfoServiceImpl extends BaseServiceImpl<VoteBaseInfo, Long> implements VoteBaseInfoService {
	private VoteBaseInfoDao voteDao;
	private VoteItemDao voteItemDao;
	private VoteHistoryDao voteHistoryDao;
	
	@Override
	public void addVote(VoteBaseInfo vote) {
		if(vote!=null){
			voteDao.addVote(vote);
		}
		
	}

	@Override
	public void deleteVote(VoteBaseInfo vote) {
		if(vote!=null){
			voteDao.deleteVote(vote);
		}
		
	}

	/**
	 * @param voteDao
	 */
	public void setVoteDao(VoteBaseInfoDao voteDao) {
		this.voteDao = voteDao;
		this.basicDao=voteDao;
	}

	/**
	 * @Description
	 * @return the voteDao
	 */
	public VoteBaseInfoDao getVoteDao() {
		return voteDao;
	}

	@Override
	public void updateVote(VoteBaseInfo vote) {
		this.voteDao.saveOrUpdateEntity(vote);
		
	}

	@Override
	public List<VoteBaseInfo> findVoteByMeetId(Long meetingId) {
		List<VoteBaseInfo> list=this.voteDao.findVoteByMeetId(meetingId);
		return list;
	}

	@Override
	public List<VoteBaseInfo> findVoteByMeetIdAndState(Long meetingId) {
		List<VoteBaseInfo> list=this.voteDao.findVoteByMeetIdAndState(meetingId);
		return list;
	}

	@Override
	public void processVote(Long voteId, String[] checks,User user) {
		try {
			VoteBaseInfo voteBase=this.voteDao.findById(voteId, VoteBaseInfo.class);
			VoteItem item=null;
			voteBase.setCount(voteBase.getCount()+1);
			this.voteDao.saveOrUpdateEntity(voteBase);
			for(String id:checks){
				Long itemId=Long.parseLong(id);
				item=this.voteItemDao.findById(itemId, VoteItem.class);
				item.setCount(item.getCount()+1);
				this.voteItemDao.saveOrUpdateEntity(item);
			}
			
			/**
			 * 保存投票历史
			 */
			VoteHistory history=new VoteHistory();
			history.setUser(user);
			history.setVoteBase(voteBase);
			this.voteHistoryDao.saveOrUpdateEntity(history);
			
		} catch (HibernateDaoSupportException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * @param voteItemDao
	 */
	public void setVoteItemDao(VoteItemDao voteItemDao) {
		this.voteItemDao = voteItemDao;
	}

	/**
	 * @Description
	 * @return the voteItemDao
	 */
	public VoteItemDao getVoteItemDao() {
		return voteItemDao;
	}

	/**
	 * @param voteHistoryDao
	 */
	public void setVoteHistoryDao(VoteHistoryDao voteHistoryDao) {
		this.voteHistoryDao = voteHistoryDao;
	}

	/**
	 * @Description
	 * @return the voteHistoryDao
	 */
	public VoteHistoryDao getVoteHistoryDao() {
		return voteHistoryDao;
	}


	
}

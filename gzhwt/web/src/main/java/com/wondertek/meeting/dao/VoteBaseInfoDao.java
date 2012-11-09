package com.wondertek.meeting.dao;


import java.util.List;

import com.wondertek.meeting.model.VoteBaseInfo;

/** 
 * @ClassName: VoteBaseInfoDao 
 * @Description: 投票基本信息Dao
 * @author zouxiaoming
 * @date Jan 9, 2012 10:31:15 AM 
 *  
 */
public interface VoteBaseInfoDao extends BaseDao<VoteBaseInfo, Long> {


	/**
	 * 保存投票基本信息
	 * @param vote
	 */
	public void addVote(VoteBaseInfo vote);
	
	
	/**
	 * 删除投票信息，会级联删除投票选项
	 * @param vote
	 */
	public void deleteVote(VoteBaseInfo vote);


	/**
	 * 查出某一个会议下的所有投票
	 * @param meetingId
	 * @return
	 */
	public List<VoteBaseInfo> findVoteByMeetId(Long meetingId);
	
	/**
	 * 查处某一个会议下所有有效的投票
	 * @param meetingId
	 * @return
	 */
	public List<VoteBaseInfo> findVoteByMeetIdAndState(Long meetingId);



	
}

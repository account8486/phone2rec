package com.wondertek.meeting.service;

import java.util.List;

import com.wondertek.meeting.model.User;
import com.wondertek.meeting.model.VoteBaseInfo;

/** 
 * @ClassName: VoteBaseInfoService 
 * @Description: 投票基本信息服务接口
 * @author zouxiaoming
 * @date Jan 9, 2012 10:31:15 AM 
 *  
 */
public interface VoteBaseInfoService extends BaseService<VoteBaseInfo, Long> {

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
	 * 修改投票信息
	 * @param vote
	 */
	public void updateVote(VoteBaseInfo vote);


	public List<VoteBaseInfo> findVoteByMeetId(Long meetingId);


	/**
	 * 查处某一个会议下所有有效的投票
	 * @param meetingId
	 */
	public List<VoteBaseInfo> findVoteByMeetIdAndState(Long meetingId);


	/**
	 * 处理投票操作
	 * @param voteId 投票Id
	 * @param checks 选择的投票选项
	 */
	public void processVote(Long voteId, String[] checks,User user);
	
	
	
}

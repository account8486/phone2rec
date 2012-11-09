package com.wondertek.meeting.dao;



import com.wondertek.meeting.model.LuckyUser;

/**
* @ClassName: LuckyUserDao 
* @Description: 可中奖人管理
* @author zouxiaoming
* @date Mar 20, 2012 4:28:42 PM 
*
 */
public interface LuckyUserDao extends BaseDao<LuckyUser, Long> {

	public void deleteLuckyUser(Long luckyId);
	
	public LuckyUser findLuckyUser(Long userId,Long meetingId);
	


	
}

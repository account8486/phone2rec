package com.wondertek.meeting.service;



import java.util.List;

import com.wondertek.meeting.model.LuckyUser;

/**
* @ClassName: LuckyUserService 
* @Description: 可中奖人员管理
* @author zouxiaoming
* @date Mar 20, 2012 5:05:38 PM 
*
 */
public interface LuckyUserService extends BaseService<LuckyUser, Long> {

	public List<LuckyUser> findLuckyUserByLuckyId(Long luckyId);
	
	public List<LuckyUser> findUserByMeetingId(Long meetingId);

}

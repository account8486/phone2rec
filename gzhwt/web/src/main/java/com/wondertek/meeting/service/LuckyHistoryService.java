package com.wondertek.meeting.service;



import java.util.List;

import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.LuckyHistory;

/**
* @ClassName: LuckyHistoryService 
* @Description:中奖历史信息
* @author zouxiaoming
* @date Mar 8, 2012 1:49:19 PM 
*
 */
public interface LuckyHistoryService extends BaseService<LuckyHistory, Long> {

	/**
	 * 查出某一个会议下的所有中奖历史
	 * @param meetingId
	 * @param currentPage
	 * @param pageSize
	 * @param mobile  模糊查询手机号
	 * @return
	 * @throws ServiceException
	 */
	public Pager<LuckyHistory> findAllLuckyHistory(Long meetingId, int currentPage, int pageSize,String mobile) throws ServiceException;
	
	/**
	 * 查询某一个会议下的所有中奖历史
	 * @param meetingId
	 * @return
	 */
	public List<LuckyHistory> findLuckyUserByMeetgingId(Long meetingId);
	
	/**
	 * 清除某一个会议下某个奖项的中奖历史
	 * @param meetingId
	 * @param luckyId
	 */
	public void clearLuckyHistory(Long meetingId,Long luckyId);

}

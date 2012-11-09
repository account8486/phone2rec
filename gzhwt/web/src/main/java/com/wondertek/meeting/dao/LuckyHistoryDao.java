package com.wondertek.meeting.dao;



import com.wondertek.meeting.model.LuckyDraw;
import com.wondertek.meeting.model.LuckyHistory;
/**
* @ClassName: LuckyHistoryDao 
* @Description:中奖历史信息
* @author zouxiaoming
* @date Mar 8, 2012 1:41:42 PM 
*
 */
public interface LuckyHistoryDao extends BaseDao<LuckyHistory, Long> {

	public void deleteLucky(LuckyDraw lucky);

	public void clearLuckyHistory(Long meetingId, Long luckyId);




	
}

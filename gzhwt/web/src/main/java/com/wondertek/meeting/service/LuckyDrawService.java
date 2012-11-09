package com.wondertek.meeting.service;



import java.util.List;

import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.LuckyDraw;
import com.wondertek.meeting.model.MeetingPost;

/**
* @ClassName: LuckyDrawService 
* @Description: 抽奖管理
* @author zouxiaoming
* @date Mar 8, 2012 10:09:39 AM 
*
 */
public interface LuckyDrawService extends BaseService<LuckyDraw, Long> {

	/**
	 * 查出某一个会议下的抽奖规则  分页实现
	 * @param meetingId  关联的会议Id
	 * @param currentPage  当前页
	 * @param pageSize  每页显示条数
	 * @return
	 * @throws ServiceException
	 */
	public Pager<LuckyDraw> findAllLucky(Long meetingId, int currentPage, int pageSize) throws ServiceException;
	
	/**
	 * 删除抽奖规则，会相应删除中奖历史
	 * @param lucky
	 * @return
	 */
	public boolean deleteLucky(LuckyDraw lucky);
	
	/**
	 * 保存奖项信息 并同时保存定制的中奖人信息
	 * @param lucky
	 * @param users
	 * @return
	 */
	public boolean saveLucky(LuckyDraw lucky,Long[] users,List<MeetingPost> list);

}

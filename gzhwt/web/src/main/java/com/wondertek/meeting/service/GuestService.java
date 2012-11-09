package com.wondertek.meeting.service;



import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.Guest;

/**
* @ClassName: GuestService 
* @Description: 嘉宾信息管理
* @author zouxiaoming
* @date Mar 6, 2012 1:21:53 PM 
*
 */
public interface GuestService extends BaseService<Guest, Long> {

	/**
	 * 查出某一个会议下的所有嘉宾信息  分页实现
	 * @param paperId
	 * @param currentPage  当前页
	 * @param pageSize  每页显示条数
	 * @param queryName 
	 * @return
	 * @throws ServiceException
	 */
	public Pager<Guest> findAllGuest(Long meetingId, int currentPage, int pageSize, String queryName) throws ServiceException;
}

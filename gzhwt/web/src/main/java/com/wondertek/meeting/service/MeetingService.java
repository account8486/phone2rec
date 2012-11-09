package com.wondertek.meeting.service;

import java.util.List;

import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.exception.HibernateDaoSupportException;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.AdminUser;
import com.wondertek.meeting.model.Meeting;
import com.wondertek.meeting.model.User;


/**
 * 
 * @author tangjun
 */
public interface MeetingService extends BaseService<Meeting,Long>{
	
	/**
	 * 查询指定用户创建的会议分页数据信息
	 * @param currentUser 
	 * 			会议创建者
	 * @param currentPage
	 * 			当前页
	 * @param pageSize
	 * 			每页记录数
	 * @return Pager<Meeting>
	 */
	public Pager<Meeting> findMeetingPager(Meeting meeting,AdminUser user,int currentPage,int pageSize) throws ServiceException;
	public Meeting getMeetingByPk(Long meetingId) throws HibernateDaoSupportException;
	public List<User> getMeetingUsers(Meeting meeting);
	public List getMyAttendMeetingList(String userId);
	public boolean cleanMeetingData(Meeting meeting) throws ServiceException;
	public Meeting findById(Long id) throws ServiceException;
}

package com.wondertek.meeting.service;

import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.MeetingAccessLog;

/**
 * 会议访问记录
 * 
 * @author 金祝华
 */
public interface MeetingAccessLogService extends BaseService<MeetingAccessLog, Long> {

	/**
	 * 查询会议访问统计，按会议进行汇总
	 * 
	 * @param user
	 * @param currentPage
	 * @param pageSize
	 * @return
	 * @throws ServiceException
	 */
	public Object[] findPager(MeetingAccessLog meetingAccessLog, Long selfOrgId, int currentPage, int pageSize)
			throws ServiceException;

	/**
	 * 查询会议访问统计明细
	 * 
	 * @param user
	 * @param currentPage
	 * @param pageSize
	 * @return
	 * @throws ServiceException
	 */
	public Pager<MeetingAccessLog> findDetailPager(MeetingAccessLog meetingAccessLog, Long selfOrgId, int currentPage,
			int pageSize) throws ServiceException;
}

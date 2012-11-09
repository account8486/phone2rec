package com.wondertek.meeting.service;

import java.util.List;

import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.AnalyticsLog;
import com.wondertek.meeting.model.MenuStatics;
import com.wondertek.meeting.model.User;

/**
 * 
 * @author John Tang
 *
 */
public interface AnalyticsLogService extends BaseService<AnalyticsLog, String> {
	public void addAnalyticsLog(String ip,User user,Long menuId,String url,Long meetingId,String params) throws ServiceException;

	/**统计该会议各菜单的访问次数 */
	public List<MenuStatics> staticsLog(Long meetingId) throws ServiceException;
}

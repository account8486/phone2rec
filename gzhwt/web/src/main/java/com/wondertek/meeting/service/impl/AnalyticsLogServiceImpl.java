package com.wondertek.meeting.service.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.wondertek.meeting.dao.AnalyticsLogDao;
import com.wondertek.meeting.dao.ClientMenuDao;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.AnalyticsLog;
import com.wondertek.meeting.model.MenuStatics;
import com.wondertek.meeting.model.User;
import com.wondertek.meeting.service.AnalyticsLogService;

/**
 * 
 * @author John Tang
 *
 */
public class AnalyticsLogServiceImpl extends BaseServiceImpl<AnalyticsLog, String> implements AnalyticsLogService {

	private AnalyticsLogDao analyticsLogDao;
	private ClientMenuDao clientMenuDao;

	public AnalyticsLogDao getAnalyticsLogDao() {
		return analyticsLogDao;
	}

	public void setAnalyticsLogDao(AnalyticsLogDao analyticsLogDao) {
		this.basicDao = analyticsLogDao;
		this.analyticsLogDao = analyticsLogDao;
	}
	
	public void addAnalyticsLog(String ip,User user,Long menuId,String url,Long meetingId,String params) throws ServiceException{
		
		if(menuId == null || meetingId == null || user == null){
			return ;
		}
		
		AnalyticsLog log = new AnalyticsLog();
		log.setAccessTime(new Date());
		log.setIp(ip);
		log.setUserId(user.getId());
		log.setMobile(user.getMobile());
		log.setUsername(user.getName());
		log.setMenuId(menuId);
		log.setUrl(url);
		log.setMeetingId(meetingId);
		
		if(params.length() > 5000){
			params= params.substring(0, 4999);
		}
		log.setParams(params);
		
		analyticsLogDao.add(log);
	}
	
	/**统计该会议各菜单的访问次数 */
	@SuppressWarnings("unchecked")
	public List<MenuStatics> staticsLog(Long meetingId) throws ServiceException{
		final String sql = "select c.id,c.name,c.terminal_type,count(a.id) from analytics_log a, client_menu c where c.meeting_id = :meeting_id and a.meeting_id=c.meeting_id and a.menu_id = c.id group by c.id, c.name, c.terminal_type";
		final HashMap<String,Object> properties = new HashMap<String,Object>();
		properties.put("meeting_id", meetingId);
		final List<Object> records = analyticsLogDao.queryListSql(sql, -1, -1, properties);
		final List<MenuStatics> resultList = new ArrayList<MenuStatics>();
		if (records!=null && records.size() > 0) {
			for(Object record:records){
				final Object[] recordArray = (Object[]) record;
				final MenuStatics statics = new MenuStatics();
				statics.setMenuId(Long.valueOf((Integer) recordArray[0]));
				statics.setMenuName((String) recordArray[1]);
				statics.setTerminalType((String) recordArray[2]);
				final BigInteger count = (BigInteger) ((recordArray[3] == null) ? BigInteger.ZERO : recordArray[3]);
				statics.setCount(count.longValue());
				resultList.add(statics);
			}
		}
		return resultList;
	}

	public ClientMenuDao getClientMenuDao() {
		return clientMenuDao;
	}

	public void setClientMenuDao(ClientMenuDao clientMenuDao) {
		this.clientMenuDao = clientMenuDao;
	}

}

package com.wondertek.meeting.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wondertek.meeting.dao.MeetingModuleTitleDao;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.MeetingModuleTitle;

/**
 * 会议数据操作类
 * 
 * @author chif
 */
public class MeetingModuleTitleDaoImpl extends BaseDaoImpl<MeetingModuleTitle, Long> implements MeetingModuleTitleDao {

	@Override
	public List<MeetingModuleTitle> query(MeetingModuleTitle meetingModuleTitle) {
		String hql = "from MeetingModuleTitle where  meetingId=:meetingId ";
		if(meetingModuleTitle.getMeetingId()==null){
			log.error("meetingTypeId 为 null，请检查程序是否正确传参！");
			return null;
		}
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("meetingId", meetingModuleTitle.getMeetingId());
		if(meetingModuleTitle.getBaseMenuId()!=null){
			hql += " and baseMenuId=:baseMenuId ";
			properties.put("baseMenuId",meetingModuleTitle.getBaseMenuId());
		}
		if(meetingModuleTitle.getTerminalType()!=null){
			hql += " and terminalType=:terminalType ";
			properties.put("terminalType",meetingModuleTitle.getTerminalType());
		}
		try {
			List<MeetingModuleTitle> list = this.getObjects(hql,properties);
			return list;
		} catch (ServiceException e) {
			e.printStackTrace();
			return null;
		}
	}
	


}

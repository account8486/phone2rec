package com.wondertek.meeting.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wondertek.meeting.dao.BaseModuleTitleDao;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.BaseModuleTitle;

/**
 * 会议数据操作类
 * 
 * @author chif
 */
public class BaseModuleTitleDaoImpl extends BaseDaoImpl<BaseModuleTitle, Long> implements BaseModuleTitleDao {
	
	public List<BaseModuleTitle> query(BaseModuleTitle baseModuleTitle){
		String hql = "from BaseModuleTitle where  meetingTypeId=:meetingTypeId ";
		if(baseModuleTitle.getMeetingTypeId()==null){
			log.error("meetingTypeId 为 null，请检查程序是否正确传参！");
			return null;
		}
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("meetingTypeId", baseModuleTitle.getMeetingTypeId());
		if(baseModuleTitle.getBaseMenuId()!=null){
			hql += " and baseMenuId=:baseMenuId ";
			properties.put("baseMenuId",baseModuleTitle.getBaseMenuId());
		}
		if(baseModuleTitle.getTerminalType()!=null){
			hql += " and terminalType=:terminalType ";
			properties.put("terminalType",baseModuleTitle.getTerminalType());
		}
		try {
			List<BaseModuleTitle> list = this.getObjects(hql,properties);
			return list;
		} catch (ServiceException e) {
			e.printStackTrace();
			return null;
		}
	}

}

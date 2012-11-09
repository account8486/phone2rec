package com.wondertek.meeting.service.impl;

import java.util.List;

import com.wondertek.meeting.dao.BaseModuleTitleDao;
import com.wondertek.meeting.model.BaseModuleTitle;
import com.wondertek.meeting.service.BaseModuleTitleService;

/**
 * 用户角色
 * 
 * @author 金祝华
 */
public class BaseModuleTitleServiceImpl extends BaseServiceImpl<BaseModuleTitle, Long> implements
		BaseModuleTitleService {

	private BaseModuleTitleDao baseModuleTitleDao;

	public BaseModuleTitleDao getBaseModuleTitleDao() {
		return baseModuleTitleDao;
	}

	public void setBaseModuleTitleDao(BaseModuleTitleDao baseModuleTitleDao) {
		this.basicDao = baseModuleTitleDao;
		this.baseModuleTitleDao = baseModuleTitleDao;
	}

	public List<BaseModuleTitle> query(BaseModuleTitle baseModuleTitle) {
		return baseModuleTitleDao.query(baseModuleTitle);
	}
}

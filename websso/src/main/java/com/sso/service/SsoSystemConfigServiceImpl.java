package com.sso.service;

import com.sso.dao.SsoSystemConfigDao;
import com.sso.model.SsoSystemConfig;
import com.wirelesscity.service.impl.BaseServiceImpl;

public class SsoSystemConfigServiceImpl extends
		BaseServiceImpl<SsoSystemConfig, String> implements  SsoSystemConfigService{
	
	
	SsoSystemConfigDao ssoSystemConfigDao;

	public SsoSystemConfigDao getSsoSystemConfigDao() {
		return ssoSystemConfigDao;
	}

	/**
	 * 
	 * @param ssoSystemConfigDao
	 */
	public void setSsoSystemConfigDao(SsoSystemConfigDao ssoSystemConfigDao) {
		this.ssoSystemConfigDao = ssoSystemConfigDao;
		this.basicDao = ssoSystemConfigDao;
	}
	
	

}

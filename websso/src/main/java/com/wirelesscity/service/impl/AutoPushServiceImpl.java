package com.wirelesscity.service.impl;

import com.wirelesscity.action.stats.GeneralStatAction;
import com.wirelesscity.service.AutoPushService;

/**
 * 专门定时来进行推送
 * 预计10分钟一次
 * @author Administrator
 */
public class AutoPushServiceImpl implements AutoPushService {
	
	GeneralStatAction generalStatActionBean;
	
	public GeneralStatAction getGeneralStatActionBean() {
		return generalStatActionBean;
	}

	public void setGeneralStatActionBean(GeneralStatAction generalStatActionBean) {
		this.generalStatActionBean = generalStatActionBean;
	}

	public void autoPushFiles(){
		
	
		try {
			generalStatActionBean.getDownloadStat();
			generalStatActionBean.getResourceStat();
			generalStatActionBean.getOrderStat();
			//generalStatActionBean.getPagerStat();
			//generalStatActionBean.getProductStat();
			generalStatActionBean.getBindStat();
			generalStatActionBean.getApplicationstat();
			//generalStatActionBean.get
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
	}
	
	
	
	

}

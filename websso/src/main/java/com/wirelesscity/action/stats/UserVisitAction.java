package com.wirelesscity.action.stats;

import java.util.List;

import com.wirelesscity.action.base.BaseAction;
import com.wirelesscity.service.SPService;
import com.wirelesscity.tools.ftp.FtpService;

public class UserVisitAction extends BaseAction {

	private static final long serialVersionUID = 4322104073545697858L;
	

	FtpService ftpService;
	
	SPService spService;
	
	public SPService getSpService() {
		return spService;
	}

	public void setSpService(SPService spService) {
		this.spService = spService;
	}

	public FtpService getFtpService() {
		return ftpService;
	}


	public void setFtpService(FtpService ftpService) {
		this.ftpService = ftpService;
	}

	@SuppressWarnings("rawtypes")
	public String getUserVisitStat(){
		//List lst=jdbcService.executeSP("");
	
		String procedure = "{call WCITY2_STATISTIC.sp_uservisit_stat(?)}";
		List lst=spService.executeSP(procedure);
		if(lst!=null){
			System.out.println(lst.size());
		}
		
		return SUCCESS;
	}
	
	

}

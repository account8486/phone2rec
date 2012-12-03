package com.sso;

import com.wirelesscity.action.base.BaseAction;
import com.wirelesscity.common.StringUtil;

public class CheckAction extends BaseAction {


	private static final long serialVersionUID = 9196787177054493022L;
	public static String NORMAL="normal";
	
	
	public String checkSystemStatus(){
		String userName=this.getRequest().getParameter("Ecom_User_ID");
		String password=this.getParameter("Ecom_Password");
		String jsoncallback=this.getParameter("jsoncallback");
		
		System.out.println(jsoncallback);
		log.debug("Ecom_User_ID:"+userName+"Ecom_Password"+password);
		String status=NORMAL;
		
		this.resultMap.put("status", status);
		
		if(StringUtil.isEmpty(userName)){
			//请输入用户名
			this.resultMap.put("status", "请输入用户名");
		}
		
		
		return SUCCESS;
	}

}

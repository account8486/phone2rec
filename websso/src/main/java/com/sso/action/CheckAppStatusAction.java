package com.sso.action;

import com.wirelesscity.action.base.BaseAction;
import com.wirelesscity.common.StringUtil;

public class CheckAppStatusAction extends BaseAction {

	private static final long serialVersionUID = -8380671286738568073L;

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	

	public static String NORMAL="normal";
	
	
	public String checkAppStatus(){
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

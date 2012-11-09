package com.sso.action;

import com.wirelesscity.action.base.BaseAction;

public class IntegrateLoginAction extends BaseAction {

	private static final long serialVersionUID = 2483103544033809632L;

	public String integrateLogin() {
		String userName = getRequest().getParameter("userName");
		String password = getRequest().getParameter("password");
		// TODO
		// 统一用户验证此用户是否存在
		
		

		return SUCCESS;
	}
	
	
	public void validateUser(){
		
		
	}

}

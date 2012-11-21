package com.sso.action;

import com.wirelesscity.action.base.BaseAction;
import com.wirelesscity.common.StringUtil;

public class IntegrateLoginAction extends BaseAction {

	private static final long serialVersionUID = 2483103544033809632L;

	/**
	 * 门户应急信息统一登陆
	 * @return
	 */
	public String integrateLogin() {
		String userName = getRequest().getParameter("userName");
		String password = getRequest().getParameter("password");
	
		// 统一用户验证此用户是否存在
		if(StringUtil.isEmpty(userName)||StringUtil.isEmpty(password)){
			return INPUT;
		}
		// TODO
		//此处进行数据库的查找与比对,查找数据库t_idm_user中的字段
		
		
		//如果账户存在并且密码正确的话，查询此用户对应的系统权限及对应的账号信息
		
		
		
		
		return SUCCESS;
	}
	
	
	public void validateUser(){
		
		
	}

}

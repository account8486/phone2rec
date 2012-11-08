package com.wirelesscity.action.stats;

import com.wirelesscity.action.base.BaseAction;

public class HttpResponseAction extends BaseAction {
	
	private static final long serialVersionUID = -8789466385359221471L;
	
	
	private static final String GET_USER_INFO_REQ="GetUserInfoReq";
	
	public String doExecute(){
		//消息名
		String msgname=this.getParameter("Msgname");
		String msgversion=this.getParameter("msgversion");
		String transactionid=this.getParameter("transactionid");
		String sendareacode=this.getParameter("sendareacode");
		String sendaddress=this.getParameter("sendaddress");
		String recvareacode=this.getParameter("recvareacode");
		String recvaddress=this.getParameter("recvaddress");
		
		//获取用户信息
		if(GET_USER_INFO_REQ.equals(msgname)){
			//8.1.1.13.3.	通信协议
			
			
		}
		
		
		
		
		
		
		
		
		
		
		
		
		return SUCCESS;
	}

}

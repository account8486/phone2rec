/**
 * 
 */
package com.wondertek.meeting.webservice.mas.delivery;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;


/**
 * @author rain
 *
 */
@WebService(name="DeliverService", targetNamespace="http://meeting.wondertek.com/webservice")
@SOAPBinding(style=Style.DOCUMENT)
public interface DeliverService {
	/**
	 * MAS平台将终端用户的上行消息提交给会务通
	 * 
	 * @param mobile 手机号码
	 * @param content 内容
	 * @param serviceCode 服务代码
	 * @return
	 */
	@WebMethod
	String smsDeliver(@WebParam(name="mobile") String mobile, @WebParam(name="content") String content, @WebParam(name="serviceCode") String serviceCode);
}

/**
 * 
 */
package com.wondertek.meeting.webservice.mas.delivery;

import javax.jws.WebService;

/**
 * @author rain
 *
 */
@WebService(name="DeliverService", targetNamespace="http://meeting.wondertek.com/webservice", endpointInterface="com.wondertek.meeting.webservice.mas.delivery.DeliverService")
public class DeliverServiceImpl implements DeliverService {

	public String smsDeliver(String mobile, String content, String serviceCode) {
		return mobile + "::" + content + "::" + serviceCode;
	}
}
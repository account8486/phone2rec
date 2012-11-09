/**
 * 
 */
package com.wondertek.meeting.webservice.mas.test;

import javax.xml.ws.Endpoint;

import com.wondertek.meeting.webservice.mas.delivery.DeliverServiceImpl;

/**
 * @author rain
 * 
 */
public class WebServiceTest {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Endpoint.publish("http://localhost:8080/DeliverService", new DeliverServiceImpl());
	}
}

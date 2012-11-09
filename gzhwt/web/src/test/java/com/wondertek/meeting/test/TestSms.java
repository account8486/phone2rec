/**
 * 
 */
package com.wondertek.meeting.test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import org.apache.commons.httpclient.util.DateUtil;

import com.huawei.insa2.comm.cmpp.message.CMPPMessage;
import com.huawei.insa2.comm.cmpp.message.CMPPSubmitMessage;
import com.huawei.insa2.util.Args;
import com.huawei.insa2.util.Cfg;
import com.huawei.smproxy.SMProxy;

/**
 * @author rain
 *
 */
public class TestSms {
	
	public static void main(String[] args) {
		try {
			Cfg cfg = new Cfg("file:///D:/workspace/cmpp/sms_config.xml");
			Args cmpp_args = cfg.getArgs("CMPPConnect");
			SMProxy smproxy = new SMProxy(cmpp_args);
			//Args cmpp_submit = cfg.getArgs("CMPPSubmitMessage");
			//CMPPSubmitMessage message = new CMPPSubmitMessage();
			//smproxy.send(message);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

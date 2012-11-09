/**
 * 
 */
package com.wondertek.meeting.manager;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wondertek.meeting.webservice.sms.ISmsInfoWs;
import com.wondertek.meeting.webservice.sms.ISmsInfoWsLocator;
import com.wondertek.meeting.webservice.sms.MeetingServerAction;
import com.wondertek.meeting.webservice.sms.MeetingServerActionLocator;

/**
 * @author rain
 * 
 */
public class SmsManager {
	private final static Logger log = LoggerFactory.getLogger(SmsManager.class);
	private final static String username = "hbzyy";
	private final static String password = "hbzyy94";
	private final static String LocaleNetworkServicePhone = "1065911401";
	private final static String[] DefaultServicePhone = {"05512681010", "05512681075", "05512681082", "05512681003", "18949814714"};
	private final static String[] Phone_Prefix = {"153","180","189","133"};

	private static ISmsInfoWs smsSendService = new ISmsInfoWsLocator();
	private static MeetingServerAction smsMeetingService = new MeetingServerActionLocator();

	/**
	 * 00 总机存在 -1 接口参数格式不合法。 -2 接口参数签名不正确。 -3 总机不存在. -99 未知异常
	 * 
	 * @param auth
	 * @return
	 */
	public static boolean authCustomerServicePhone(final ServicePhoneAuthParam auth) {
		try {
			log.debug("request: " + auth.toXml());
			final String responseXml = smsMeetingService.getMeetingServerActionHttpPort().sendTextSms(auth.toXml());
			log.debug("response: " + responseXml);
			return parseResponse(responseXml, "Response/param[@name='status']", "00");
		} catch (Exception e) {
			log.error("error happened when customer service phone number authrizing", e.getMessage());
		}
		return false;
	}
	
	private static String pickUpServicePhone() {
		final int random = (int) Math.floor(Math.random()*DefaultServicePhone.length);
		return DefaultServicePhone[random];
	}

	public static boolean smsSend(final String customerServicePhone, final String recipientPhone, final String content) {
		try {
			if (StringUtils.isNotEmpty(content)) {
				/*
				 * final String result =
				 * smsSendService.getISmsInfoWsHttpPort().sendSmsInfo(
				 * generateMessageRequestXml(customerServicePhone,
				 * recipientPhone, content));
				 */
				String servicePhone;
				if (StringUtils.startsWithAny(recipientPhone, Phone_Prefix)) {
					servicePhone = LocaleNetworkServicePhone;
				} else {
					servicePhone = pickUpServicePhone();
				}
				final String result = smsSendService.getISmsInfoWsHttpPort().sendSmsInfo(
						generateMessageRequestXml(servicePhone, recipientPhone, content));
				return parseResponse(result, "returnXML/result", "0");
			}
		} catch (Exception e) {
			log.error("Error happened when SMS sending", e.getMessage());
		}
		return false;
	}

	private static String generateMessageRequestXml(final String customerServicePhone, final String recipientPhone,
			final String content) {
		final StringBuffer xml = new StringBuffer();
		xml.append("<?xml version=\"1.0\" encoding=\"gb2312\"?><SMS><username>").append(username)
				.append("</username><password>").append(password).append("</password><callnum>")
				.append(validation(customerServicePhone)).append("</callnum><receivenum>")
				.append(validation(recipientPhone)).append("</receivenum><sms_content>").append(content)
				.append("</sms_content></SMS>");
		log.debug("request: " + xml.toString());
		return xml.toString();
	}

	private static boolean parseResponse(final String responseXml, final String nodeName, final String expectResponse) {
		log.debug("response: " + responseXml);
		try {
			final Document doc = DocumentHelper.parseText(responseXml);
			final Node resultNode = doc.selectSingleNode(nodeName);
			if (resultNode != null) {
				final String result = resultNode.getStringValue();
				if (StringUtils.equals(result, expectResponse)) {
					return true;
				}
			}
		} catch (DocumentException e) {
			log.error("Parse Error of Response XML", e.getMessage());
		}
		return false;
	}

	private static String validation(final String value) {
		return StringUtils.defaultIfEmpty(value, "");
	}

	public static void main(String[] args) {
		String xml = "<?xml version='1.0' encoding='UTF-8'?><returnXML><returnId>true</returnId><result>0</result><operTime>2011-11-29 21:19:13.437 CST</operTime></returnXML>";
		parseResponse(xml, "returnXML/result", "0");
	}
}
/**
 * 
 */
package com.wondertek.meeting.manager;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.Calendar;

import javax.xml.rpc.ServiceException;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wondertek.meeting.common.Configurations;
import com.wondertek.meeting.webservice.mas.CPMMS;
import com.wondertek.meeting.webservice.mas.MmsSubmit;
import com.wondertek.meeting.webservice.mas.Param;
import com.wondertek.meeting.webservice.mas.ReqHeader;
import com.wondertek.meeting.webservice.mas.RespHeader;
import com.wondertek.meeting.webservice.mas.SmsSubmit;
import com.wondertek.meeting.webservice.mas.SubmitService;
import com.wondertek.meeting.webservice.mas.SubmitServiceImplService;
import com.wondertek.meeting.webservice.mas.SubmitServiceImplServiceLocator;

/**
 * @author rain
 * 
 */
public class MasServiceManager {
	private final static Logger log = LoggerFactory.getLogger(MasServiceManager.class);
	/**
	 * MAS平台为第三方系统分配的接入账号
	 */
	private final static String SYSID = "mas.sysid";
	/**
	 * MAS平台为第三方系统分配的接入密码
	 */
	private final static String PASSWORD = "mas.password";
	/**
	 * 校验码
	 */
	private final static String CHECKCODE = "mas.checkcode";
	/**
	 * 源地址
	 */
	private final static String SOURCEADDR = "mas.sourceaddr";
	/**
	 * 短信、彩信服务地址
	 */
	private final static String SUBMIT_ENDPOINT = "mas.webservice.submit.endpoint";

	/**
	 * 短信发送
	 * 
	 * @param mobile
	 *            手机号码
	 * @param content
	 *            短信内容
	 * @return
	 */
	public static boolean smsSend(final String mobile, final String content) {
		log.debug("smsSend :: mobile :: " + mobile + "content::" + content);
		final String[] mobiles = {mobile};
		return smsSend(mobiles, content);
	}

	/**
	 * 短信发送
	 * 
	 * @param mobiles
	 *            手机号码
	 * @param content
	 *            短信内容
	 * @return boolean
	 */
	public static boolean smsSend(final String[] mobiles, final String content) {
		return smsSend(mobiles, content, null);
	}

	/**
	 * 短信发送
	 * 
	 * @param mobiles
	 *            手机号码
	 * @param content
	 *            短信内容
	 * @param params
	 *            特定接口调用所需的各项参数，与特定业务有关
	 * @return
	 */
	public static boolean smsSend(final String[] mobiles, final String content, final Param[] params) {
		final SmsSubmit smsSubmit = new SmsSubmit(content, mobiles, null, Configurations.getString(SOURCEADDR));
		try {
			return responseHandler(getService().smsSubmit(getReqHeader(), smsSubmit, params));
		} catch (RemoteException e) {
			log.error("Remote Exception::", e);
		} catch (MalformedURLException e) {
			log.error("MalformedURLException::", e);
		} catch (ServiceException e) {
			log.error("ServiceException::", e);
		}
		return false;
	}

	/**
	 * 
	 * @param cpMms
	 *            彩信内容
	 * @param mobiles
	 *            手机号码
	 * @return
	 */
	public static boolean mmsSend(final CPMMS cpMms, final String[] mobiles) {
		return mmsSend(cpMms, mobiles, null);
	}

	/**
	 * 
	 * 
	 * @param cpMms
	 *            彩信内容
	 * @param mobiles
	 *            手机号码
	 * @param params
	 *            消息参数，与特定业务有关
	 * @return
	 */
	public static boolean mmsSend(final CPMMS cpMms, final String[] mobiles, final Param[] params) {
		final MmsSubmit mmsSubmit = new MmsSubmit(cpMms, mobiles, null, Configurations.getString(SOURCEADDR));
		try {
			log.debug("向网关发送彩信  手机号:"+mobiles[0]+"等...  主题："+cpMms.getSubject());
			return responseHandler(getService().mmsSubmit(getReqHeader(), mmsSubmit, params));
		} catch (RemoteException e) {
			log.error("Remote Exception::", e);
		} catch (MalformedURLException e) {
			log.error("MalformedURLException::", e);
		} catch (Exception e) {
			log.error("Exception::", e);
		}
		return false;
	}

	private static ReqHeader getReqHeader() {
		final ReqHeader reqHeader = new ReqHeader();
		reqHeader.setAuthCode(getAutoCode());
		reqHeader.setSysid(Configurations.getString(SYSID));
		reqHeader.setReqno(getReqNo());
		return reqHeader;
	}

	private static String getAutoCode() {
		return DigestUtils.md5Hex(Configurations.getString(PASSWORD) + Configurations.getString(CHECKCODE));
	}

	public static String getReqNo() {
		final Calendar cal = Calendar.getInstance();
		return String.valueOf(cal.getTimeInMillis());
	}

	private static SubmitService getService() throws ServiceException, MalformedURLException {
		final SubmitServiceImplService serviceLocator = new SubmitServiceImplServiceLocator();
		final URL endpoint = new URL(Configurations.getString(SUBMIT_ENDPOINT));
		return serviceLocator.getSubmitServiceImplPort(endpoint);
	}

	private static boolean responseHandler(final RespHeader respHeader) {
		if (StringUtils.equalsIgnoreCase(respHeader.getResultCode(), "0")) {
			return true;
		} else {
			log.error("response::"+respHeader.getResultCode());
			return false;
		}
	}
}

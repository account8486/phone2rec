/**
 * 
 */
package com.wondertek.meeting.webservice.mas.test;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Calendar;

import javax.imageio.ImageIO;
import javax.xml.namespace.QName;
import javax.xml.rpc.ServiceException;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;

import com.wondertek.meeting.manager.SmsSender;
import com.wondertek.meeting.webservice.mas.CPFrag;
import com.wondertek.meeting.webservice.mas.CPMMS;
import com.wondertek.meeting.webservice.mas.MmsSubmit;
import com.wondertek.meeting.webservice.mas.ReqHeader;
import com.wondertek.meeting.webservice.mas.RespHeader;
import com.wondertek.meeting.webservice.mas.SmsSubmit;
import com.wondertek.meeting.webservice.mas.SubmitService;
import com.wondertek.meeting.webservice.mas.SubmitServiceImplService;
import com.wondertek.meeting.webservice.mas.SubmitServiceImplServiceLocator;
import com.wondertek.meeting.webservice.mas.delivery.client.DeliverService;
import com.wondertek.meeting.webservice.mas.delivery.client.DeliverServiceImplServiceLocator;
import sun.misc.BASE64Encoder; 

/**
 * @author rain
 * 
 */
public class SmsTest {
	
	/**
	 * 帐号：851000021139
	 
	密码：config
	校验码：gzyd89898
	短信接入代码：1065733301557
	彩信接入代码：1065733301557
	 */
	
	/**
	 * MAS平台为第三方系统分配的接入账号
	 */
	private final static String sysid = "851000021139";
	/**
	 * MAS平台为第三方系统分配的接入密码+校验码
	 */
	private final static String authCode = "configgzyd89898";
	/**
	 * 
	 */
	private final static String sourceAddr = "1065733301557";
	/**
	 * 目标手机号码
	 */
	private final static String[] mobiles = { "15186997171" };

	/**
	 * 彩信ID
	 */
	private final static String mmsId = "abc";

	/**
	 * 彩信标题
	 */
	private final static String mms_subject = "mms test";

	/**
	 * 彩信描述
	 */
	private final static String mms_description = "mms test";

	/**
	 * 彩信种类, 0:MULTIPART_MIXE混合型, 1:MULTIPART_RELATE关系型(必须包含SMIL)
	 */
	private final static int mms_type = 0;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			//testSmsSubmit();
			testMmsSubmit();
			//testSmsDeliver_axis();
			//testSmsDeliver();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void testSmsSubmit() throws ServiceException, RemoteException {
		final SmsSubmit smsSubmit = new SmsSubmit("sms submit test.", mobiles, null, sourceAddr);
		responseHandler(getService().smsSubmit(getReqHeader(), smsSubmit, null));
	}
	
	public static byte[] readImage(String imageFile) {
		try {
			final File file = new File(imageFile);
			final BufferedImage srcImage = ImageIO.read(file);
			final ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ImageIO.write(srcImage,"jpg", bos);
			final byte[] image = bos.toByteArray();
			return image;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}	

	private static void testMmsSubmit() throws ServiceException, RemoteException {
		final String sReqNo = getReqNo();                
		final CPFrag cpfrag1 = new CPFrag();
		final String content1 = "mms submit test.";
		cpfrag1.setId(sReqNo + "001");
		cpfrag1.setLength(content1.length());
		cpfrag1.setName(sReqNo + "001.txt");
		cpfrag1.setType(1);
		cpfrag1.setContent(content1.getBytes());

        final CPFrag cpfrag2 = new CPFrag();
        final String imageFile = "/wd/small.jpg";
        final byte[] content2 = readImage(imageFile);		
		cpfrag2.setId(sReqNo +"002");
		cpfrag2.setLength(content2.length);
		cpfrag2.setName(sReqNo +"002.jpg");
		cpfrag2.setType(9);
		cpfrag2.setContent(content2); 

        /*final CPFrag cpfrag3 = new CPFrag();
		final String content3 = "mms submit test.";
		cpfrag3.setId(sReqNo +"003");
		cpfrag3.setLength(content3.length());
		cpfrag3.setName(sReqNo + "002.smil");
		cpfrag3.setType(3);
		cpfrag3.setContent(content3.getBytes());*/

		final CPFrag[] cpfrags = { cpfrag1, cpfrag2 };

		final CPMMS cpMms = new CPMMS();
		cpMms.setId(getReqNo());
		cpMms.setSubject(mms_subject);
		cpMms.setDescription(mms_description);
		cpMms.setType(mms_type);
		cpMms.setCpfrags(cpfrags);

		final MmsSubmit mmsSubmit = new MmsSubmit();
		mmsSubmit.setCpmms(cpMms);
		mmsSubmit.setDest(mobiles);
		mmsSubmit.setSourceAddr(sourceAddr);

		responseHandler(getService().mmsSubmit(getReqHeader(), mmsSubmit, null));
	}

	private static SubmitService getService() throws ServiceException {
		final SubmitServiceImplService serviceLocator = new SubmitServiceImplServiceLocator();
		return serviceLocator.getSubmitServiceImplPort();
	}

	private static void responseHandler(final RespHeader respHeader) {
		if (StringUtils.equalsIgnoreCase(respHeader.getResultCode(), "0")) {
			System.out.println("success...");
		} else {
			System.out.println("failure..." + respHeader.getResultCode());
		}
	}

	private static ReqHeader getReqHeader() {
		final ReqHeader reqHeader = new ReqHeader();
		reqHeader.setAuthCode(getAutoCode());
		reqHeader.setSysid(sysid);
		reqHeader.setReqno(getReqNo());
		return reqHeader;
	}

	private static String getAutoCode() {
		// return StringUtils.upperCase(DigestUtils.md5Hex(authCode));
		// System.out.println(DigestUtils.md5Hex(authCode));
		return DigestUtils.md5Hex(authCode);
	}

	private static String getReqNo() {
		final Calendar cal = Calendar.getInstance();
		return String.valueOf(cal.getTimeInMillis());
	}
	
	private static void testSmsDeliver() throws ServiceException, RemoteException {
		final DeliverServiceImplServiceLocator serviceLocator = new DeliverServiceImplServiceLocator();
		final DeliverService service = serviceLocator.getDeliverServiceImplPort();
		final String result = service.smsDeliver("13956099163", "测试上行短信", "1111");
		System.out.println(result);
	}
	
	private static void testSmsDeliver_axis() {
		try {
			String wsdlUrl = "http://localhost:8080/DeliverService";
			//String wsdlUrl = "http://localhost:8088/gzhwt/DeliverService";
			String nameSpaceUri = "http://meeting.wondertek.com/webservice";

			// 创建调用对象
			Service service = new Service();
			Call call = null;
			call = (Call) service.createCall();

			call.setOperationName(new QName(nameSpaceUri, "smsDeliver"));
			call.setTargetEndpointAddress(new java.net.URL(wsdlUrl));
			
		    call.addParameter("mobile", org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);
		    call.addParameter("content", org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);
		    call.addParameter("serviceCode", org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);
	        call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);
	        
			String ret = (String) call.invoke(new Object[] { "13956099163", "测试上行短信", "1111" });
			System.out.println("return value is >>>" + ret);
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
}
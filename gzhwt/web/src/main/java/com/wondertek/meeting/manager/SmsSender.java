/**
 * 统一封装的短信发送方法
 * Author: 张国敬
 * Copyrights: 版权归上海网达软件有限公司安徽分公司所有
 * Create at: 2012-3-27
 */
package com.wondertek.meeting.manager;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.imageio.ImageIO;

import com.wondertek.meeting.common.Constants;
import com.wondertek.meeting.webservice.mas.CPFrag;
import com.wondertek.meeting.webservice.mas.CPMMS;
import com.wondertek.meeting.webservice.mas.MmsSubmit;

public class SmsSender {
	private static final String SMS_GATEWAY = Constants.SMS_SEND_GATEWAY; //短信发送网关
	
	/**
	 * 发送彩信接口
	 * 
	 * @param mobiles
	 * @param subject
	 * @param content
	 * @param image
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static boolean sendMms(final String[] mobiles, final String subject, final String content, final byte[] image) {
		final String sReqNo = MasServiceManager.getReqNo();  
		final CPFrag cpfrag1 = new CPFrag();
		cpfrag1.setId(sReqNo + "001");
		cpfrag1.setLength(content.length());
		cpfrag1.setName(sReqNo + "001.txt");
		cpfrag1.setType(1);
		try {
			cpfrag1.setContent(content.getBytes("GBK"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		final CPFrag cpfrag2 = new CPFrag();
		cpfrag2.setId(sReqNo +"002");
		cpfrag2.setLength(image.length);
		cpfrag2.setName(sReqNo +"002.jpg");
		cpfrag2.setType(9);
		cpfrag2.setContent(image); 
		
		final CPFrag[] cpfrags = {cpfrag2, cpfrag1 };//先图片，再文字
		final CPMMS cpMms = new CPMMS();
		cpMms.setId(MasServiceManager.getReqNo());
		cpMms.setSubject(subject);
		cpMms.setType(0);
		cpMms.setCpfrags(cpfrags);
		
		return MasServiceManager.mmsSend(cpMms, mobiles);
	}
	
	/**
	 * 发送彩信接口
	 * 
	 * @param mobiles
	 * @param subject
	 * @param content
	 * @param image
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static boolean sendMms(final String[] mobiles, final String subject, CPFrag[] cpfrags) {
		final CPMMS cpMms = new CPMMS();
		cpMms.setId(MasServiceManager.getReqNo());
		cpMms.setSubject(subject);
		cpMms.setType(0);
		cpMms.setCpfrags(cpfrags);
	
		return MasServiceManager.mmsSend(cpMms, mobiles);
	}
	
	/**
	 * 发送短信方法
	 * @param mobile 接收短信的手机号
	 * @param content 短信内容
	 * @return 
	 */
	public static boolean sendSms(String mobile, String content) {
		if(mobile == null || content == null) {
			return false;
		}
		
		boolean ret = true;
		
		if("MAS".equals(SMS_GATEWAY)) {
			ret = MasServiceManager.smsSend(mobile, content);
		} else if("CMPP".equals(SMS_GATEWAY)) {
			try {
				if (content.length() > 140) {
					CmppClientManager.getInstance().asyncSendLongSms(mobile, content);
				} else {
					CmppClientManager.getInstance().asyncSendSms(mobile, content.getBytes("GBK"));
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				ret = false;
			} catch (Exception e) {
				e.printStackTrace();
				ret = false;
			}
		} else if("TELSMS".equals(SMS_GATEWAY)) {
			ret = SmsManager.smsSend(null, mobile, content);
		}
		return ret;
	}
	
	public static void main(String[] args) {
		//SmsSender.sendSms("13908518578", "请把刚才收到的彩信转发到号码：15375296623，谢谢！");
		//SmsSender.sendSms("13608529590", "请把刚才收到的彩信转发到号码：15375296623，谢谢！");
		try {
			final File file = new File("/wd/pic01.jpg");
			final BufferedImage srcImage = ImageIO.read(file);
			final ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ImageIO.write(srcImage,"jpg", bos);
			final byte[] image = bos.toByteArray();
			final String[] mobiles = new String[] {"13908518578"};
			SmsSender.sendMms(mobiles, "中文测试", "中文测试", image);
			SmsSender.sendMms(mobiles, "", "中文测试", image);
			//SmsSender.sendMms(mobiles, "test", "this is a test", image);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
	}
}

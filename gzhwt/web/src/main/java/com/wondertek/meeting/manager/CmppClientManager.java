package com.wondertek.meeting.manager;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.huawei.insa2.comm.cmpp.message.CMPPDeliverMessage;
import com.huawei.insa2.comm.cmpp.message.CMPPSubmitMessage;
import com.huawei.insa2.comm.cmpp.message.CMPPSubmitRepMessage;
import com.huawei.insa2.util.Args;
import com.huawei.insa2.util.Cfg;
import com.huawei.opencmpp.hw.AyncSMProxy;
import com.huawei.opencmpp.hw.AyncSMProxyEventListener;

public class CmppClientManager {
	private static Logger logger = LoggerFactory.getLogger(CmppClientManager.class);
	static final List<CMPPDeliverMessage> receiveList = new ArrayList<CMPPDeliverMessage>();
	static final List<CMPPSubmitMessage> successList = new ArrayList<CMPPSubmitMessage>();
	static final List<CMPPSubmitMessage> failedList = new ArrayList<CMPPSubmitMessage>();
	static AyncSMProxyEventListener listener = new AyncSMProxyEventListener() {
		public void onReceive(CMPPDeliverMessage deliver) {
			logger.debug("######################## RECEIVE MSG : " + deliver);
			// receiveList.add(deliver);
		}

		public void onSubmitSuccess(CMPPSubmitMessage submit, CMPPSubmitRepMessage submit_resp) {
			logger.debug("######################## SEND SUCCESS : " + submit + "\n  RESP: " + submit_resp);
			// successList.add(submit);
		}

		public void onSubmitFailed(CMPPSubmitMessage submit, CMPPSubmitRepMessage submit_resp) {
			logger.debug("######################## SEND FAILED : " + submit.getSequenceId() + ", RESULT "
					+ submit_resp.getResult());
			// failedList.add(submit);
		}
	};

	public static final String SMPROXY_FILE = "/wd/smproxy.xml";
	public Args args = null;
	public AyncSMProxy myProxy = null;
	public String msg_Src;
	public String service_Id;
	public String src_Terminal_Id;

	/**
	 * 信息格式 0：ASCII串 3：短信写卡操作 4：二进制信息 8：UCS2编码 15：含GB汉字 ......
	 */
	public int msg_Fmt = 15;
	// volatile关键字确保当uniqueInstance变量被初始化成Singleton实例时,多个线程正确地处理uniqueInstance变量
	private volatile static CmppClientManager uniqueInstance;

	/**
	 * 信息内容来源(SP_ID) String msg_Src="912300"; 业务类别 String service_Id="wxcs";
	 * 源终端MSISDN号码, 即此短消息的主叫地址 String src_Terminal_Id="10658234";
	 * 
	 * @param AyncSMProxyEventListener
	 *            listener 消息接收处理
	 * @return CMPPClient
	 */
	public static CmppClientManager getInstance() {
		if (uniqueInstance == null) {// 检查实例,如是不存在就进行同步代码区
			synchronized (CmppClientManager.class) {// 对其进行锁,防止两个线程同时进入同步代码区
				if (uniqueInstance == null) {// 双重检查,非常重要,如果两个同时访问的线程,当第一线程访问完同步代码区后,生成一个实例;当第二个已进入getInstance方法等待的线程进入同步代码区时,也会产生一个新的实例
					uniqueInstance = new CmppClientManager(listener);
				}
			}
		}
		return uniqueInstance;
	}

	private CmppClientManager(AyncSMProxyEventListener listener) {
		try {
			args = new Cfg(SMPROXY_FILE, false).getArgs("CMPP3Connect");
			// this.listener = listener;
			myProxy = new AyncSMProxy(args, listener);

			this.msg_Src = args.get("sp-id", "");
			this.service_Id = args.get("service-id", "");
			this.src_Terminal_Id = args.get("sp-code", "");

			try {
				// 等待30秒，再发送短信
				System.out.println("等待30秒，再发送短信");
				Thread.sleep(30000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			logger.error("CmppClientManager Error: ", e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * 发送短信字段
	 * 
	 * 1 @pk_Total 相同msg_Id消息总条数 2 @pk_Number 相同msg_Id的消息序号 3 @registered_Delivery
	 * 是否要求返回状态报告 4 @msg_Level 信息级别 5 @service_Id 业务类型 6 @fee_UserType 计费用户类型字段
	 * 7 @fee_Terminal_Id 被计费用户的号码 8 @tp_Pid GSM协议类型 9 @tp_Udhi GSM协议类型 10 @msg_Fmt
	 * 消息格式 11 @msg_Src 消息内容来源 12 @fee_Type 资费类别 13 @fee_Code 资费代码(以分为单位) 14 @valid_Time
	 * 存活有效期 15 @at_Time 定时发送时间 16 @src_Terminal_Id 源号码 17 @dest_Terminal_Id
	 * 接收短信的MSISDN号码 18 @msg_Content 消息内容 19 @reserve 保留
	 */

	/* 请设置为1 */
	public int pk_Total = 1;

	/* 请设置为1 */
	public int pk_Number = 1;

	/* 1:需要状态报告,0:不需要状态报告 */
	public int registered_Delivery = 1;

	/* 信息级别 */
	public int msg_Level = 0;

	/*
	 * 计费用户类型字段， 0：对目的终端MSISDN计费； 1：对源终端MSISDN计费； 2：对SP计费;
	 * 3:表示本字段无效，对谁计费参见psFeeTerminalID参数。
	 */
	public int fee_UserType = 0;

	/*
	 * 被计费用户的号码（如本字节填空，则表示本字段无效，对谁计费参见byFeeUserType字段。本字段与Fee_UserType字段互斥）
	 */
	public String fee_Terminal_Id = "";

	/*
	 * GSM协议类型。详细是解释请参考GSM03.40中的9.2.3.9 此值请填0
	 */
	public int tp_Pid = 0;

	/*
	 * GSM协议类型。表示是否有头结构，详细是解释请参考GSM03.40中的9.2.3.23,仅使用1位，右对齐 请填写为0。
	 */
	public int tp_Udhi = 0;

	/* 资费类别 */
	public String fee_Type = "02";

	/* 资费代码（以分为单位） */
	public String fee_Code = "0";

	/* 有效期，一般填空 */
	public java.util.Date valid_Time = null;

	/* 定时发送时间，一般填空 */
	public java.util.Date at_Time = null;

	public String reserve = "";

	/**
	 * 
	 * @param mobile
	 *            接收手机号
	 * @param messageContent
	 *            = "你好那你你快乐".getBytes("GBK");
	 */
	public void asyncSendSms(String mobile, byte[] messageContent) {
		// 查询SMProxy与ISMG的TCP连接状态
		String state = myProxy.getConnState();
		if (state != null) {
			// 与短信网关的链接异常
			System.out.println("与短信网关的链接异常，重连....");
			myProxy.close();
			myProxy = null;
			myProxy = new AyncSMProxy(args, listener);
			try {
				// 等待30秒，再发送短信
				System.out.println("等待30秒，再发送短信...");
				Thread.sleep(30000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		logger.debug("asyncSendSms :: mobile :: " + mobile + "content::" + messageContent.toString());
		logger.debug("asyncSendSms :: service_Id :: " + service_Id);
		String[] dest_Terminal_Id = new String[] { mobile };
		CMPPSubmitMessage submitMsg = new CMPPSubmitMessage(pk_Total, pk_Number, registered_Delivery, msg_Level,
				service_Id, fee_UserType, fee_Terminal_Id, tp_Pid, tp_Udhi, msg_Fmt, msg_Src, fee_Type, fee_Code, null,
				null, src_Terminal_Id, dest_Terminal_Id, messageContent, reserve);

		try {
			myProxy.send_by_async(submitMsg);
		} catch (Exception e1) {
			logger.error("asyncSendSms :: " + e1.getMessage());
			e1.printStackTrace();
		}
	}

	public void asyncSendLongSms(String mobile, String message) {
		String[] dest_Terminal_Id = new String[] { mobile };
		List<byte[]> list = getLongByte(message);
		for (byte[] msg : list) {
			CMPPSubmitMessage submitMsg = new CMPPSubmitMessage(pk_Total, pk_Number,
					registered_Delivery, msg_Level, service_Id, fee_UserType,
					fee_Terminal_Id, tp_Pid, 1, 8, msg_Src, fee_Type, fee_Code, null, null, src_Terminal_Id, dest_Terminal_Id,
					msg, reserve);
			try {
				myProxy.send_by_async(submitMsg);
			} catch (Exception e1) {
				logger.error("asyncSendSms :: " + e1.getMessage());
				e1.printStackTrace();
			}
		}
	}

	private static List<byte[]> getLongByte(String message) {
		List<byte[]> list = new ArrayList<byte[]>();
		try {
			byte[] messageUCS2 = message.getBytes("UnicodeBigUnmarked");
			int messageUCS2Len = messageUCS2.length;// 长短信长度
			int maxMessageLen = 140;
			if (messageUCS2Len > maxMessageLen) {// 长短信发送
				// int tpUdhi = 1;
				// 长消息是1.短消息是0
				// int msgFmt = 0x08;//长消息不能用GBK
				int messageUCS2Count = messageUCS2Len / (maxMessageLen - 6) + 1;// 长短信分为多少条发送
				byte[] tp_udhiHead = new byte[6];
				tp_udhiHead[0] = 0x05;
				tp_udhiHead[1] = 0x00;
				tp_udhiHead[2] = 0x03;
				tp_udhiHead[3] = 0x0A;
				tp_udhiHead[4] = (byte) messageUCS2Count;
				tp_udhiHead[5] = 0x01;// 默认为第一条
				for (int i = 0; i < messageUCS2Count; i++) {
					tp_udhiHead[5] = (byte) (i + 1);
					byte[] msgContent;
					if (i != messageUCS2Count - 1) {// 不为最后一条
						msgContent = byteAdd(tp_udhiHead, messageUCS2, i * (maxMessageLen - 6), (i + 1)
								* (maxMessageLen - 6));
						list.add(msgContent);
					} else {
						msgContent = byteAdd(tp_udhiHead, messageUCS2, i * (maxMessageLen - 6), messageUCS2Len);
						list.add(msgContent);
					}
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	private static byte[] byteAdd(byte[] tpUdhiHead, byte[] messageUCS2, int i, int j) {
		byte[] msgb = new byte[j - i + 6];
		System.arraycopy(tpUdhiHead, 0, msgb, 0, 6);
		System.arraycopy(messageUCS2, i, msgb, 6, j - i);
		return msgb;
	}

	public static void main(String[] args) {
		try {
			Args cfgArgs = new Cfg(SMPROXY_FILE, false).getArgs("CMPP3Connect");
			AyncSMProxy proxy = new AyncSMProxy(cfgArgs, listener);
			System.out.println(cfgArgs.get("service-id", "..."));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

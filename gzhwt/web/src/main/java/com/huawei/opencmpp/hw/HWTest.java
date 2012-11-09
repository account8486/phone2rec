package com.huawei.opencmpp.hw;

import java.util.Date;

import com.huawei.insa2.comm.cmpp.message.CMPPSubmitMessage;
import com.huawei.insa2.util.TypeConvert;

public class HWTest {

	public static void main(String[] args) throws Exception {
		HWTest test = new HWTest();
		test.test_0();
	}

	public void test_0() throws Exception {
		System.out.print("Create 100 receiver Mobile No...");
		String rcvMobile[] = new String[100];
		int count = 0;
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				rcvMobile[count] = String.valueOf(String
						.valueOf((new StringBuffer("136000000")).append(i)
								.append(j)));
				count++;
			}

		}

		System.out.println("OK");
		System.out.println("new CMPPMessage...");
		CMPPSubmitMessage msg = new CMPPSubmitMessage(1, 1, 1, 1, "websms", 1,
				"", 0, 0, 0, "websms", "02", "10", new Date(System
						.currentTimeMillis()
						+ (long) 0xa4cb800), null, "888813512345678",
				rcvMobile, "testmessage".getBytes(), "");
		System.out.println("OK");
		int sendcount = 1;
		int sendinterval = 1;

		for (int i = 0; i < sendcount; i++) {
			System.out.print("Send Message...");
			if (WebSMSender.getInstance().send(msg))
				System.out.println("Success");
			else
				System.out.println("Fail");
			try {
				Thread.sleep(1000 * sendinterval);
			} catch (Exception exception) {
			}
		}

	}
	
	public void test_gen_msg_id() throws Exception {
		byte[] msg_id = new byte[8];
		byte[] buf = new byte[] {105, 8, 124, 64};
		System.out.println(TypeConvert.byte2int(buf));
	}
}

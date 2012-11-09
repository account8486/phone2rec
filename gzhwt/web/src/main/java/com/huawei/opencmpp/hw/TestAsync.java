package com.huawei.opencmpp.hw;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import com.huawei.insa2.comm.cmpp.message.CMPPDeliverMessage;
import com.huawei.insa2.comm.cmpp.message.CMPPSubmitMessage;
import com.huawei.insa2.comm.cmpp.message.CMPPSubmitRepMessage;

public class TestAsync {
	public void test_async_0() throws Exception {
		final List<CMPPDeliverMessage> receiveList = new ArrayList<CMPPDeliverMessage>();
		final List<CMPPSubmitMessage> successList = new ArrayList<CMPPSubmitMessage>();
		final List<CMPPSubmitMessage> failedList = new ArrayList<CMPPSubmitMessage>();
		AyncSMProxyEventListener listener = new AyncSMProxyEventListener() {
			public void onReceive(CMPPDeliverMessage deliver) {
				System.out.println("RECEIVE MSG : " + deliver.toString());
				receiveList.add(deliver);
			}

			public void onSubmitSuccess(CMPPSubmitMessage submit,
					CMPPSubmitRepMessage submit_resp) {
				System.out.println("SEND SUCCESS : " + submit.getSequenceId());
				successList.add(submit);
			}

			public void onSubmitFailed(CMPPSubmitMessage submit,
					CMPPSubmitRepMessage submit_resp) {
				System.out.println("SEND FAILED : " + submit.getSequenceId()
						+ ", RESULT " + submit_resp.getResult());
				failedList.add(submit);
			}

		};
		AyncSMProxy proxy = new AyncSMProxy(listener);

		String rcvMobile[] = new String[] { "13470565823" };

		for (int i = 0; i < 10; ++i) {
			long msgId = i;
			CMPPSubmitMessage submit = new CMPPSubmitMessage(msgId, 1, 1, 1, 1,
					"websms", 1, "", 0, 0, 0, "websms", "02", "10", new Date(
							System.currentTimeMillis() + (long) 0xa4cb800),
					null, "888813512345678", rcvMobile, ("testmessage_" + i)
							.getBytes(), "");

			proxy.send_by_async(submit);
		}

		proxy.close();

		System.out.println("exit");
	}

}

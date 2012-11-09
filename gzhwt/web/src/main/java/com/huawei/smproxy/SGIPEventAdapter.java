package com.huawei.smproxy;

import com.huawei.insa2.comm.*;
import com.huawei.insa2.comm.sgip.SGIPConnection;
import com.huawei.insa2.comm.sgip.SGIPTransaction;
import com.huawei.insa2.comm.sgip.message.*;

class SGIPEventAdapter extends PEventAdapter {
	private SGIPSMProxy smProxy;

	private SGIPConnection conn;

	public SGIPEventAdapter(SGIPSMProxy smProxy, SGIPConnection conn) {
		this.smProxy = null;
		this.conn = null;
		this.smProxy = smProxy;
		this.conn = conn;
	}

	public void childCreated(PLayer child) {
		SGIPTransaction t = (SGIPTransaction) child;
		SGIPMessage msg = t.getResponse();
		SGIPMessage resmsg = null;
		if (msg.getCommandId() == 2) {
			resmsg = new SGIPUnbindRepMessage();
			if (t.isChildOf(conn))
				smProxy.onTerminate();
		} else if (msg.getCommandId() == 1) {
			SGIPBindMessage tmpmes = (SGIPBindMessage) msg;
			int logintype = tmpmes.getLoginType();
			if (logintype != 2 && logintype != 11)
				resmsg = new SGIPBindRepMessage(4);
			resmsg = new SGIPBindRepMessage(0);
		} else if (msg.getCommandId() == 4) {
			SGIPDeliverMessage tmpmes = (SGIPDeliverMessage) msg;
			resmsg = smProxy.onDeliver(tmpmes);
		} else if (msg.getCommandId() == 5) {
			SGIPReportMessage tmpmes = (SGIPReportMessage) msg;
			resmsg = smProxy.onReport(tmpmes);
		} else if (msg.getCommandId() == 17) {
			SGIPUserReportMessage tmpmes = (SGIPUserReportMessage) msg;
			resmsg = smProxy.onUserReport(tmpmes);
		} else {
			t.close();
		}
		if (resmsg != null) {
			try {
				t.send(resmsg);
			} catch (PException e) {
				e.printStackTrace();
			}
			t.close();
		}
		if (msg.getCommandId() == 2) {
			SGIPConnection theconn = (SGIPConnection) t.getParent();
			theconn.close();
		}
	}

}

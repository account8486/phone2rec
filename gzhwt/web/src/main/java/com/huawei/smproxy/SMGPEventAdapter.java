package com.huawei.smproxy;

import com.huawei.insa2.comm.*;
import com.huawei.insa2.comm.smgp.SMGPConnection;
import com.huawei.insa2.comm.smgp.SMGPTransaction;
import com.huawei.insa2.comm.smgp.message.*;

class SMGPEventAdapter extends PEventAdapter {

	public SMGPEventAdapter(SMGPSMProxy smProxy) {
		this.smProxy = null;
		conn = null;
		this.smProxy = smProxy;
		conn = smProxy.getConn();
	}

	public void childCreated(PLayer child) {
		SMGPTransaction t = (SMGPTransaction) child;
		SMGPMessage msg = t.getResponse();
		SMGPMessage resmsg = null;
		if (msg.getRequestId() == 6) {
			resmsg = new SMGPExitRespMessage();
			smProxy.onTerminate();
		} else if (msg.getRequestId() == 4)
			resmsg = new SMGPActiveTestRespMessage();
		else if (msg.getRequestId() == 3) {
			SMGPDeliverMessage tmpmes = (SMGPDeliverMessage) msg;
			resmsg = smProxy.onDeliver(tmpmes);
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
		if (msg.getRequestId() == 6)
			conn.close();
	}

	private SMGPSMProxy smProxy;

	private SMGPConnection conn;
}

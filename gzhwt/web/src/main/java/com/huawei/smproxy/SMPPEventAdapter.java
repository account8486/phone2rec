package com.huawei.smproxy;

import com.huawei.insa2.comm.PEventAdapter;
import com.huawei.insa2.comm.PException;
import com.huawei.insa2.comm.PLayer;
import com.huawei.insa2.comm.smpp.SMPPConnection;
import com.huawei.insa2.comm.smpp.SMPPTransaction;
import com.huawei.insa2.comm.smpp.message.SMPPDeliverMessage;
import com.huawei.insa2.comm.smpp.message.SMPPMessage;
import com.huawei.insa2.comm.smpp.message.SMPPUnbindRespMessage;

class SMPPEventAdapter extends PEventAdapter {
	private SMPPSMProxy smProxy;

	private SMPPConnection conn;

	public SMPPEventAdapter(SMPPSMProxy smProxy) {
		this.smProxy = null;
		conn = null;
		this.smProxy = smProxy;
		conn = smProxy.getConn();
	}

	public SMPPConnection getConnection() {
		return conn;
	}

	public void childCreated(PLayer child) {
		SMPPTransaction t = (SMPPTransaction) child;
		SMPPMessage msg = t.getResponse();
		SMPPMessage resmsg = null;
		if (msg.getCommandId() == 6) {
			resmsg = new SMPPUnbindRespMessage();
			smProxy.onTerminate();
		} else if (msg.getCommandId() == 5) {
			SMPPDeliverMessage tmpmes = (SMPPDeliverMessage) msg;
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
	}

}

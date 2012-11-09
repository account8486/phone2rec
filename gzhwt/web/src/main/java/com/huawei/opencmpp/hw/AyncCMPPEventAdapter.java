package com.huawei.opencmpp.hw;

import com.huawei.insa2.comm.*;
import com.huawei.insa2.comm.cmpp.CMPPConnection;
import com.huawei.insa2.comm.cmpp.message.*;

public class AyncCMPPEventAdapter extends PEventAdapter {

	public AyncCMPPEventAdapter(AyncSMProxy smProxy) {
		this.smProxy = null;
		conn = null;
		this.smProxy = smProxy;
		conn = smProxy.getConn();
	}

	public void childCreated(PLayer child) {
		AyncCMPPTransaction txn = (AyncCMPPTransaction) child;
		CMPPMessage msg = txn.getResponse();
		CMPPMessage resmsg = null;
		if (msg.getCommandId() == 2) {
			resmsg = new CMPPTerminateRepMessage();
			smProxy.onTerminate();
		} else if (msg.getCommandId() == 8)
			resmsg = new CMPPActiveRepMessage(0);
		else if (msg.getCommandId() == 5) {
			CMPPDeliverMessage tmpmes = (CMPPDeliverMessage) msg;
			resmsg = smProxy.onDeliver(tmpmes);
		} else {
			txn.close();
		}
		if (resmsg != null) {
			try {
				smProxy.addTransactionInfo(txn, resmsg);
				txn.send(resmsg);
				smProxy.removeTransactionInfo(txn);
			} catch (PException e) {
				e.printStackTrace();
			}
			txn.close();
		}
		if (msg.getCommandId() == 2)
			conn.close();
	}

	private AyncSMProxy smProxy;

	private CMPPConnection conn;
}

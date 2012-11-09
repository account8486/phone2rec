package com.huawei.smproxy;

import com.huawei.insa2.comm.PEventAdapter;
import com.huawei.insa2.comm.PException;
import com.huawei.insa2.comm.PLayer;
import com.huawei.insa2.comm.cmpp.CMPPConstant;
import com.huawei.insa2.comm.cmpp.message.CMPPActiveRepMessage;
import com.huawei.insa2.comm.cmpp.message.CMPPMessage;
import com.huawei.insa2.comm.cmpp.message.CMPPTerminateRepMessage;
import com.huawei.insa2.comm.cmpp30.CMPP30Connection;
import com.huawei.insa2.comm.cmpp30.CMPP30Transaction;
import com.huawei.insa2.comm.cmpp30.message.CMPP30DeliverMessage;

public class CMPP30EventAdapter extends PEventAdapter {
	private SMProxy30 smProxy;

	private CMPP30Connection conn;

	public CMPP30EventAdapter(SMProxy30 smProxy) {
		this.smProxy = null;
		conn = null;
		this.smProxy = smProxy;
		conn = smProxy.getConn();
	}

	public void childCreated(PLayer child) {
		CMPP30Transaction t = (CMPP30Transaction) child;
		CMPPMessage msg = t.getResponse();
		CMPPMessage resmsg = null;
		if (msg.getCommandId() == CMPPConstant.Terminate_Command_Id) {
			resmsg = new CMPPTerminateRepMessage();
			smProxy.onTerminate();
		} else if (msg.getCommandId() == CMPPConstant.Active_Test_Command_Id)
			resmsg = new CMPPActiveRepMessage(0);
		else if (msg.getCommandId() == CMPPConstant.Deliver_Command_Id) {
			CMPP30DeliverMessage tmpmes = (CMPP30DeliverMessage) msg;
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
		if (msg.getCommandId() == 2)
			conn.close();
	}

}

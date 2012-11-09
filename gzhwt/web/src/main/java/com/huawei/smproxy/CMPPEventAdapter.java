package com.huawei.smproxy;

import com.huawei.insa2.comm.PEventAdapter;
import com.huawei.insa2.comm.PException;
import com.huawei.insa2.comm.PLayer;
import com.huawei.insa2.comm.cmpp.CMPPConnection;
import com.huawei.insa2.comm.cmpp.CMPPConstant;
import com.huawei.insa2.comm.cmpp.CMPPTransaction;
import com.huawei.insa2.comm.cmpp.message.CMPPActiveRepMessage;
import com.huawei.insa2.comm.cmpp.message.CMPPDeliverMessage;
import com.huawei.insa2.comm.cmpp.message.CMPPMessage;
import com.huawei.insa2.comm.cmpp.message.CMPPTerminateRepMessage;

public class CMPPEventAdapter extends PEventAdapter {

	private SMProxy smProxy;

	private CMPPConnection conn;

	public CMPPEventAdapter(SMProxy smProxy) {
		this.smProxy = null;
		conn = null;
		this.smProxy = smProxy;
		conn = smProxy.getConn();
	}

	public void childCreated(PLayer child) {
		CMPPTransaction t = (CMPPTransaction) child;
		CMPPMessage msg = t.getResponse();
		CMPPMessage resmsg = null;

		if (msg.getCommandId() == CMPPConstant.Terminate_Command_Id) {
			resmsg = new CMPPTerminateRepMessage();
			smProxy.onTerminate();
		} else if (msg.getCommandId() == CMPPConstant.Active_Test_Command_Id)
			resmsg = new CMPPActiveRepMessage(0);
		else if (msg.getCommandId() == CMPPConstant.Deliver_Command_Id) {
			CMPPDeliverMessage tmpmes = (CMPPDeliverMessage) msg;
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
		
		if (msg.getCommandId() == CMPPConstant.Terminate_Command_Id) {
			conn.close();
		}

	}

}

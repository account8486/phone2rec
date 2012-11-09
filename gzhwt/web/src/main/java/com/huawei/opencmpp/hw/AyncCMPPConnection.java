package com.huawei.opencmpp.hw;

import java.io.IOException;

import com.huawei.insa2.comm.PException;
import com.huawei.insa2.comm.PLayer;
import com.huawei.insa2.comm.PMessage;
import com.huawei.insa2.comm.cmpp.CMPPConnection;
import com.huawei.insa2.comm.cmpp.CMPPConstant;
import com.huawei.insa2.comm.cmpp.message.CMPPActiveMessage;
import com.huawei.insa2.comm.cmpp.message.CMPPActiveRepMessage;
import com.huawei.insa2.comm.cmpp.message.CMPPTerminateMessage;
import com.huawei.insa2.util.Args;

public class AyncCMPPConnection extends CMPPConnection {
	private int degree;

	private int hbnoResponseOut;

	private final AyncSMProxy smproxy;

	private boolean closed_flag = false;

	public AyncCMPPConnection(AyncSMProxy smproxy, Args args) {
		super(args);
		degree = 0;
		hbnoResponseOut = 3;
		hbnoResponseOut = args.get("heartbeat-noresponseout", 3);
		this.smproxy = smproxy;
	}

	public PLayer createChild() {
		return new AyncCMPPTransaction(this);
	}

	public void onReceive(PMessage msg) {
		super.onReceive(msg);
		smproxy.onReceive(msg);
	}

	protected void heartbeat() throws IOException {
		if (closed_flag) {
			return;
		}

		AyncCMPPTransaction txn = (AyncCMPPTransaction) createChild();
		CMPPActiveMessage hbmes = new CMPPActiveMessage();
		smproxy.addTransactionInfo(txn, hbmes);
		txn.send(hbmes);
		txn.waitResponse();
		CMPPActiveRepMessage rsp = (CMPPActiveRepMessage) txn.getResponse();
		if (rsp == null) {
			degree++;
			if (degree == hbnoResponseOut) {
				degree = 0;
				throw new IOException(CMPPConstant.HEARTBEAT_ABNORMITY);
			}
		} else {
			degree = 0;
		}
		txn.close();
	}

	public void close() {
		this.closed_flag = true;
		super.close();
	}

}

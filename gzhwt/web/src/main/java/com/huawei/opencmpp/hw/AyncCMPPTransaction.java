package com.huawei.opencmpp.hw;

import com.huawei.insa2.comm.PLayer;
import com.huawei.insa2.comm.PMessage;
import com.huawei.insa2.comm.cmpp.CMPPTransaction;

public class AyncCMPPTransaction extends CMPPTransaction {

	public AyncCMPPTransaction(PLayer connection) {
		super(connection);
	}

	public synchronized void onReceive(PMessage msg) {
		super.onReceive(msg);
	}

	public int getId() {
		return id;
	}
}
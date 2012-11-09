package com.huawei.smproxy;

import java.io.IOException;
import java.util.Map;

import com.huawei.insa2.comm.cmpp.CMPPConnection;
import com.huawei.insa2.comm.cmpp.CMPPTransaction;
import com.huawei.insa2.comm.cmpp.message.CMPPDeliverMessage;
import com.huawei.insa2.comm.cmpp.message.CMPPDeliverRepMessage;
import com.huawei.insa2.comm.cmpp.message.CMPPMessage;
import com.huawei.insa2.util.Args;

public class SMProxy {
	private CMPPConnection conn;

	public SMProxy(Map args) {
		this(new Args(args));
	}

	public SMProxy(Args args) {
		conn = new CMPPConnection(args);
		conn.addEventListener(new CMPPEventAdapter(this));
		conn.waitAvailable();
		if (!conn.available())
			throw new IllegalStateException(conn.getError());
		else
			return;
	}

	public CMPPMessage send(CMPPMessage message) throws IOException {
		if (message == null)
			return null;
		CMPPTransaction t = (CMPPTransaction) conn.createChild();
		try {
			t.send(message);
			t.waitResponse();
			CMPPMessage rsp = t.getResponse();
			CMPPMessage cmppmessage = rsp;
			return cmppmessage;
		} finally {
			t.close();
		}
	}

	public void onTerminate() {
	}

	public CMPPMessage onDeliver(CMPPDeliverMessage msg) {
		return new CMPPDeliverRepMessage(msg.getMsgId(), 0);
	}

	public void close() {
		conn.close();
	}

	public CMPPConnection getConn() {
		return conn;
	}

	public String getConnState() {
		return conn.getError();
	}

}

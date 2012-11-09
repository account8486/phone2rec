package com.huawei.smproxy;

import java.io.IOException;
import java.util.Map;

import com.huawei.insa2.comm.smpp.SMPPConnection;
import com.huawei.insa2.comm.smpp.SMPPTransaction;
import com.huawei.insa2.comm.smpp.message.SMPPDeliverMessage;
import com.huawei.insa2.comm.smpp.message.SMPPDeliverRespMessage;
import com.huawei.insa2.comm.smpp.message.SMPPMessage;
import com.huawei.insa2.util.Args;

public class SMPPSMProxy {

	public SMPPSMProxy(Map args) {
		this(new Args(args));
	}

	public SMPPSMProxy(Args args) {
		conn = new SMPPConnection(args);
		conn.addEventListener(new SMPPEventAdapter(this));
		conn.waitAvailable();
		if (!conn.available()) {
			throw new IllegalStateException(conn.getError());
		}
	}

	public SMPPMessage send(SMPPMessage message) throws IOException {
		if (message == null)
			return null;
		SMPPTransaction t = (SMPPTransaction) conn.createChild();
		try {
			t.send(message);
			t.waitResponse();
			SMPPMessage rsp = t.getResponse();
			SMPPMessage smppmessage = rsp;
			return smppmessage;
		} finally {
			t.close();
		}
	}

	public void onTerminate() {
	}

	public SMPPMessage onDeliver(SMPPDeliverMessage msg) {
		return new SMPPDeliverRespMessage(0);
	}

	public void close() {
		conn.close();
	}

	public SMPPConnection getConn() {
		return conn;
	}

	public String getConnState() {
		return conn.getError();
	}

	private SMPPConnection conn;
}

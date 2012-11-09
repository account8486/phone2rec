package com.huawei.insa2.comm.cmpp;

import com.huawei.insa2.comm.PMessage;
import com.huawei.insa2.comm.PWriter;
import com.huawei.insa2.comm.cmpp.message.CMPPMessage;
import java.io.IOException;
import java.io.OutputStream;

public class CMPPWriter extends PWriter {
	protected OutputStream out;

	public CMPPWriter(OutputStream out) {
		this.out = out;
	}

	public synchronized void write(PMessage message) throws IOException {
		CMPPMessage msg = (CMPPMessage) message;
		out.write(msg.getBytes());
	}

	public void writeHeartbeat() throws IOException {
	}

}
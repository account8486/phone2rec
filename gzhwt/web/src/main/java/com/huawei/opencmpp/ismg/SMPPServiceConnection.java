package com.huawei.opencmpp.ismg;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.huawei.insa2.comm.PMessage;
import com.huawei.insa2.comm.PReader;
import com.huawei.insa2.comm.PWriter;
import com.huawei.insa2.comm.smpp.SMPPConstant;
import com.huawei.insa2.comm.smpp.SMPPReader;
import com.huawei.insa2.comm.smpp.SMPPWriter;
import com.huawei.insa2.comm.smpp.message.SMPPLoginMessage;
import com.huawei.insa2.comm.smpp.message.SMPPMessage;
import com.huawei.opencmpp.server.AyncDispatchEventServiceConnection;

public class SMPPServiceConnection extends AyncDispatchEventServiceConnection {
	private static Log logger = LogFactory.getLog(SMPPServiceConnection.class);

	protected final CMPPISMGService server;

	public SMPPServiceConnection(ExecutorService executor,
			CMPPISMGService server, Socket socket) {
		super(executor, socket);

		if (server == null) {
			throw new IllegalArgumentException("server is null.");
		}

		this.server = server;
	}

	protected PReader getReader(InputStream in) {
		return new SMPPReader(in);
	}

	protected PWriter getWriter(OutputStream out) {
		return new SMPPWriter(out);
	}

	public void onReceive(PMessage pMessage) {
		if (pMessage == null) {
			return;
		}

		SMPPMessage msg = (SMPPMessage) pMessage;

		if (msg.getCommandId() == SMPPConstant.Bind_Receiver_Command_Id
				|| msg.getCommandId() == SMPPConstant.Bind_Transmitter_Command_Id) {
			onBind((SMPPLoginMessage) msg);
		}
	}

	public void onBind(SMPPLoginMessage bind_msg) {
		
	}
}

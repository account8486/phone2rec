package com.huawei.smproxy;

import java.io.IOException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.huawei.insa2.comm.sgip.SGIPConnection;
import com.huawei.insa2.comm.sgip.SGIPTransaction;
import com.huawei.insa2.comm.sgip.SSEventListener;
import com.huawei.insa2.comm.sgip.SSListener;
import com.huawei.insa2.comm.sgip.message.SGIPDeliverMessage;
import com.huawei.insa2.comm.sgip.message.SGIPDeliverRepMessage;
import com.huawei.insa2.comm.sgip.message.SGIPMessage;
import com.huawei.insa2.comm.sgip.message.SGIPReportMessage;
import com.huawei.insa2.comm.sgip.message.SGIPReportRepMessage;
import com.huawei.insa2.comm.sgip.message.SGIPUserReportMessage;
import com.huawei.insa2.comm.sgip.message.SGIPUserReportRepMessage;
import com.huawei.insa2.util.Args;

public class SGIPSMProxy implements SSEventListener {
	private SGIPConnection conn;

	private SSListener listener;

	private Args args;

	private HashMap serconns;

	private int src_nodeid;

	public SGIPSMProxy(Map args) {
		this(new Args(args));
	}

	public SGIPSMProxy(Args args) {
		this.args = args;
		src_nodeid = args.get("source-addr", 0);
	}

	public synchronized boolean connect(String loginName, String loginPass) {
		boolean result = true;
		if (loginName != null)
			args.set("login-name", loginName.trim());
		if (loginPass != null)
			args.set("login-pass", loginPass.trim());
		conn = new SGIPConnection(args, true, null);
		conn.addEventListener(new SGIPEventAdapter(this, conn));
		conn.waitAvailable();
		if (!conn.available()) {
			result = false;
			throw new IllegalStateException(conn.getError());
		} else {
			return result;
		}
	}

	public synchronized void startService(String localhost, int localport) {
		if (listener != null)
			return;
		try {
			listener = new SSListener(localhost, localport, this);
			listener.beginListen();
		} catch (Exception exception) {
		}
	}

	public synchronized void stopService() {
		if (listener == null)
			return;
		listener.stopListen();
		if (serconns != null) {
			SGIPConnection conn;
			for (Iterator iterator = serconns.keySet().iterator(); iterator
					.hasNext(); conn.close()) {
				String addr = (String) iterator.next();
				conn = (SGIPConnection) serconns.get(addr);
			}

			serconns.clear();
		}
	}

	public synchronized void onConnect(Socket socket) {
		String peerIP = socket.getInetAddress().getHostAddress();
		int port = socket.getPort();
		if (serconns == null)
			serconns = new HashMap();
		SGIPConnection conn = new SGIPConnection(args, false, serconns);
		conn.addEventListener(new SGIPEventAdapter(this, conn));
		conn.attach(args, socket);
		serconns.put(peerIP + port, conn);
	}

	public SGIPMessage send(SGIPMessage message) throws IOException {
		if (message == null)
			return null;
		SGIPTransaction t = (SGIPTransaction) conn.createChild();
		t.setSPNumber(src_nodeid);
		Date nowtime = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("MMddHHmmss");
		String tmpTime = dateFormat.format(nowtime);
		Integer timestamp = new Integer(tmpTime);
		t.setTimestamp(timestamp.intValue());
		try {
			t.send(message);
			t.waitResponse();
			SGIPMessage rsp = t.getResponse();
			SGIPMessage sgipmessage = rsp;
			return sgipmessage;
		} finally {
			t.close();
		}
	}

	public void onTerminate() {
	}

	public SGIPMessage onDeliver(SGIPDeliverMessage msg) {
		return new SGIPDeliverRepMessage(0);
	}

	public SGIPMessage onReport(SGIPReportMessage msg) {
		return new SGIPReportRepMessage(0);
	}

	public SGIPMessage onUserReport(SGIPUserReportMessage msg) {
		return new SGIPUserReportRepMessage(0);
	}

	public void close() {
		conn.close();
	}

	public SGIPConnection getConn() {
		return conn;
	}

	public String getConnState() {
		if (conn != null)
			return conn.getError();
		else
			return "\u5C1A\u672A\u5EFA\u7ACB\u8FDE\u63A5";
	}

}

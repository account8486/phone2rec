package com.huawei.opencmpp.ismg;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Calendar;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.huawei.insa2.comm.PException;
import com.huawei.insa2.comm.PMessage;
import com.huawei.insa2.comm.PReader;
import com.huawei.insa2.comm.PWriter;
import com.huawei.insa2.comm.cmpp.CMPPConstant;
import com.huawei.insa2.comm.cmpp.CMPPReader;
import com.huawei.insa2.comm.cmpp.CMPPWriter;
import com.huawei.insa2.comm.cmpp.message.CMPPActiveMessage;
import com.huawei.insa2.comm.cmpp.message.CMPPActiveRepMessage;
import com.huawei.insa2.comm.cmpp.message.CMPPCancelMessage;
import com.huawei.insa2.comm.cmpp.message.CMPPConnectMessage;
import com.huawei.insa2.comm.cmpp.message.CMPPConnectRepMessage;
import com.huawei.insa2.comm.cmpp.message.CMPPMessage;
import com.huawei.insa2.comm.cmpp.message.CMPPQueryMessage;
import com.huawei.insa2.comm.cmpp.message.CMPPSubmitMessage;
import com.huawei.insa2.comm.cmpp.message.CMPPSubmitRepMessage;
import com.huawei.insa2.comm.cmpp.message.CMPPTerminateMessage;
import com.huawei.insa2.comm.cmpp.message.CMPPTerminateRepMessage;
import com.huawei.insa2.util.SecurityTools;
import com.huawei.insa2.util.TypeConvert;
import com.huawei.opencmpp.server.AyncDispatchEventServiceConnection;
import com.huawei.opencmpp.util.BitSet;
import com.huawei.opencmpp.util.Bits;
import com.huawei.opencmpp.util.EasyTool;
import com.huawei.opencmpp.util.Open_CMPPConstant;

/**
 * CMPP的实现
 * 
 * @author 温少
 * 
 */
public class CMPPISMGServiceConnection extends
		AyncDispatchEventServiceConnection {
	private static Log logger = LogFactory
			.getLog(CMPPISMGServiceConnection.class);

	protected AtomicInteger submit_msg_id_counter = new AtomicInteger(0);

	protected final CMPPISMGService server;

	protected int version = -1;

	protected Listener listener;

	public final static int MAX_WINDOW_SIZE = 16;

	protected int window_size = 0;

	public CMPPISMGServiceConnection(ExecutorService executor,
			CMPPISMGService server, Socket socket) {
		super(executor, socket);

		if (server == null) {
			throw new IllegalArgumentException("server is null.");
		}

		this.server = server;
	}

	protected PWriter getWriter(OutputStream out) {
		return new CMPPWriter(out);
	}

	protected PReader getReader(InputStream in) {
		return new CMPPReader(in);
	}

	public void close() {
		super.close();
		this.server.connList.remove(this);
	}

	public Listener getListener() {
		return listener;
	}

	public void setListener(Listener listener) {
		this.listener = listener;
	}

	public int getVersion() {
		return version;
	}

	public void onReceive(PMessage pMessage) {
		if (pMessage == null) {
			return;
		}

		CMPPMessage msg = (CMPPMessage) pMessage;

		if (logger.isDebugEnabled()) {
			logger.debug("RECEIVE : "
					+ Open_CMPPConstant.getCommandId(msg.getCommandId()) + " "
					+ msg.getSequenceId());
		}

		if (msg.getCommandId() == CMPPConstant.Connect_Command_Id) {
			CMPPConnectMessage conn_msg = (CMPPConnectMessage) msg;
			onConnect(conn_msg);
			return;
		}

		if (msg.getCommandId() == CMPPConstant.Submit_Command_Id) {
			CMPPSubmitMessage submit_msg = (CMPPSubmitMessage) msg;
			onSubmit(submit_msg);
			return;
		}

		if (msg.getCommandId() == CMPPConstant.Active_Test_Command_Id) {
			CMPPActiveMessage act_msg = (CMPPActiveMessage) msg;
			onActive(act_msg);
			return;
		}

		if (msg.getCommandId() == CMPPConstant.Terminate_Command_Id) {
			CMPPTerminateMessage term_msg = (CMPPTerminateMessage) msg;
			onTerminate(term_msg);
			return;
		}

		if (msg.getCommandId() == CMPPConstant.Cancel_Command_Id) {
			CMPPCancelMessage cancel_msg = (CMPPCancelMessage) msg;
			onCancel(cancel_msg);
			return;
		}

		if (msg.getCommandId() == CMPPConstant.Query_Command_Id) {
			CMPPQueryMessage query_msg = (CMPPQueryMessage) msg;
			onQuery(query_msg);
			return;
		}
	}

	protected void onCancel(CMPPCancelMessage cancel_msg) {
		throw new RuntimeException("TODO");
	}

	protected void onQuery(CMPPQueryMessage query_msg) {
		throw new RuntimeException("TODO");
	}

	protected void onTerminate(CMPPTerminateMessage term_msg) {
		this.state = State.Closed;
		
		this.receiveThread.kill();

		for (int i = 0; i < 100; ++i) {
			if (this.getWindow() == 0) {
				break;
			}

			try {
				Thread.sleep(100);
			} catch (InterruptedException _) {
			}
		}

		try {
			CMPPTerminateRepMessage resp = new CMPPTerminateRepMessage();
			resp.setSequenceId(term_msg.getSequenceId());
			if (!socket.isClosed()) {
				send_sync(resp);
			}
		} catch (PException e) {
			if (logger.isErrorEnabled()) {
				logger.error(e.getMessage(), e);
			}
		}

		if (listener != null) {
			listener.onTerminate(term_msg);
		}
		super.close();

		this.server.connList.remove(this);
	}

	protected void onActive(CMPPActiveMessage act_msg) {
		CMPPActiveRepMessage resp_msg = new CMPPActiveRepMessage(act_msg
				.getSequenceId());
		this.send(resp_msg);

		if (listener != null) {
			listener.onActive(act_msg);
		}
	}

	protected void onSubmit(CMPPSubmitMessage submit_msg) {

		byte[] resp_msg_id_bytes = generate_submit_resp_id();

		byte result = 0;

		if (this.getWindow() > MAX_WINDOW_SIZE) {
			result = 8; // 流量控制错
		}

		if (submit_msg.getMsgFmt() == 0) {
			if (submit_msg.getMsgLength() >= 160) {
				result = 6; // 超过最大信息长
			}
		} else {
			if (submit_msg.getMsgLength() > 140) {
				result = 6; // 超过最大信息长
			}
		}

		CMPPSubmitRepMessage resp_msg = new CMPPSubmitRepMessage(submit_msg
				.getSequenceId(), resp_msg_id_bytes, result);

		this.send(resp_msg);

		if (listener != null) {
			listener.onSubmit(submit_msg);
		}
	}

	public void send_sync(PMessage message) throws PException {
		CMPPMessage cmpp_msg = (CMPPMessage) message;
		if (logger.isDebugEnabled()) {
			logger.debug("SEND : "
					+ Open_CMPPConstant.getCommandId(cmpp_msg.getCommandId())
					+ " " + cmpp_msg.getSequenceId());
		}

		super.send_sync(message);
	}

	/**
	 * 生成CMPP_Submit_Resp_Id
	 * 
	 * @return
	 */
	private byte[] generate_submit_resp_id() {
		byte[] resp_msg_id_bytes;

		long resp_msg_id = 0;

		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int minute = cal.get(Calendar.MINUTE);
		int second = cal.get(Calendar.SECOND);

		int ismg_Id = this.server.getConfig().getISMGId();

		// 截去前10位
		ismg_Id &= (int) Math.pow(2, 22) - 1;

		final short submit_msg_id_index = (short) (submit_msg_id_counter
				.getAndIncrement() % Short.MAX_VALUE);
		// bits.get(bitIndex)
		// 0000 00001 00001 000000 000000 0000100001000010000100
		// 0000100001000010
		// 月 日 时 分 秒 网关ID 序列号
		// 4位 5位 5位 6位 6位 22位 16位

		resp_msg_id |= ((long) month) << 60;
		resp_msg_id |= ((long) day) << 55;
		resp_msg_id |= ((long) hour) << 50;
		resp_msg_id |= ((long) minute) << 44;
		resp_msg_id |= ((long) second) << 38;
		resp_msg_id |= ((long) ismg_Id) << 16;
		resp_msg_id |= submit_msg_id_index;

		resp_msg_id_bytes = new byte[8];
		Bits.putLong(resp_msg_id_bytes, 0, resp_msg_id);

		return resp_msg_id_bytes;
	}

	protected void onConnect(CMPPConnectMessage conn_msg) {
		byte[] conn_bytes = conn_msg.getBytes();
		this.version = conn_bytes[34];

		byte status = (byte) (version <= 2 ? 0 : 4);

		// MD5（Status+AuthenticatorSource+shared secret）
		byte[] temp_bytes = new byte[26];
		temp_bytes[0] = status;
		System.arraycopy(conn_bytes, 18, temp_bytes, 1, 16);
		System.arraycopy(conn_bytes, 12, temp_bytes, 17, 6);

		byte[] buff = new byte[22];

		TypeConvert.int2byte(conn_msg.getSequenceId(), buff, 0);
		/*
		 * 0 正确, 1 消息结构错, 2 非法源地址, 3 认证错, 4 版本太高, 5 其他错误
		 */
		buff[4] = status;
		SecurityTools.md5(temp_bytes, 0, temp_bytes.length, buff, 5);
		buff[21] = 2; /* 最高支持版本号 */
		CMPPConnectRepMessage resp_msg = new CMPPConnectRepMessage(buff);

		this.send(resp_msg);

		if (listener != null) {
			listener.onConnect(conn_msg);
		}
	}

	public static interface Listener {
		public void onSubmit(CMPPSubmitMessage submit_msg);

		public void onConnect(CMPPConnectMessage conn_msg);

		public void onTerminate(CMPPTerminateMessage term_msg);

		public void onActive(CMPPActiveMessage act_msg);
	}
}

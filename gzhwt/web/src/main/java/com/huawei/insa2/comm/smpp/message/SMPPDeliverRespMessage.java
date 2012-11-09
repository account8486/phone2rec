package com.huawei.insa2.comm.smpp.message;

public class SMPPDeliverRespMessage extends SMPPMessage {

	public SMPPDeliverRespMessage(int status) throws IllegalArgumentException {
		int len = 17;
		super.buf = new byte[17];
		setMsgLength(len);
		setCommandId(0x80000005);
		setStatus(status);
		super.buf[16] = 0;
		strBuf = new StringBuffer(100);
		strBuf.append(",MsgId= ");
	}

	public String toString() {
		StringBuffer outStr = new StringBuffer(100);
		outStr.append("SMPPDeliverRespMessage:");
		strBuf.append("PacketLength=".concat(String.valueOf(String
				.valueOf(getMsgLength()))));
		strBuf.append(",CommandID=".concat(String.valueOf(String
				.valueOf(getCommandId()))));
		strBuf.append(",Status=".concat(String.valueOf(String
				.valueOf(getStatus()))));
		outStr.append(",SequenceId=".concat(String.valueOf(String
				.valueOf(getSequenceId()))));
		if (strBuf != null)
			outStr.append(strBuf.toString());
		return outStr.toString();
	}

	private StringBuffer strBuf;
}

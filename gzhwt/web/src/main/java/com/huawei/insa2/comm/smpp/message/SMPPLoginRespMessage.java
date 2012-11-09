package com.huawei.insa2.comm.smpp.message;

import com.huawei.insa2.comm.smpp.SMPPConstant;

public class SMPPLoginRespMessage extends SMPPMessage {

	public SMPPLoginRespMessage(byte buf[]) throws IllegalArgumentException {
		super.buf = new byte[buf.length];
		if (buf.length < 17 || buf.length > 32) {
			throw new IllegalArgumentException(SMPPConstant.SMC_MESSAGE_ERROR);
		}

		System.arraycopy(buf, 0, super.buf, 0, buf.length);
	}

	public String getSystemId() {
		return new String(buf, 16, buf.length - 17);
	}

	public String toString() {
		StringBuffer strBuf = new StringBuffer(300);
		strBuf.append("SMPPLoginRespMessage: ");
		strBuf.append("PacketLength=" + super.buf.length);
		strBuf.append(",CommandID=" + getCommandId());
		strBuf.append(",Status=" + getStatus());
		strBuf.append(",SequenceId=" + getSequenceId());
		strBuf.append(",SystemId=" + getSystemId());
		return strBuf.toString();
	}
}

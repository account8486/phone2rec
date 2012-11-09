package com.huawei.insa2.comm.smpp.message;

import com.huawei.insa2.comm.smpp.SMPPConstant;
import com.huawei.insa2.util.TypeConvert;

public class SMPPEnquireLinkMessage extends SMPPMessage {

	public SMPPEnquireLinkMessage() {
		int len = 16;
		super.buf = new byte[len];
		setMsgLength(len);
		setCommandId(21);
		setStatus(0);
	}

	public SMPPEnquireLinkMessage(byte buf[]) throws IllegalArgumentException {
		super.buf = new byte[16];
		if (buf.length != 16) {
			throw new IllegalArgumentException(SMPPConstant.SMC_MESSAGE_ERROR);
		} else {
			System.arraycopy(buf, 0, super.buf, 0, 12);
			super.sequence_Id = TypeConvert.byte2int(super.buf, 0);
			return;
		}
	}

	public String toString() {
		StringBuffer strBuf = new StringBuffer(100);
		strBuf.append("SMPPEnquireLinkMessage: ");
		strBuf.append("PacketLength=" + getMsgLength());
		strBuf.append(",CommandID=" + getCommandId());
		strBuf.append(",Status=" + getStatus());
		strBuf.append(",SequenceId=" + getSequenceId());
		return strBuf.toString();
	}
}

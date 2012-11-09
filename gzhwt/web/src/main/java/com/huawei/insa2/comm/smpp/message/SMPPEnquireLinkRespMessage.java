package com.huawei.insa2.comm.smpp.message;

import com.huawei.insa2.comm.smpp.SMPPConstant;
import com.huawei.insa2.util.TypeConvert;

public class SMPPEnquireLinkRespMessage extends SMPPMessage {

	public SMPPEnquireLinkRespMessage() {
		int len = 16;
		super.buf = new byte[len];
		setMsgLength(len);
		setCommandId(0x80000015);
		setStatus(0);
	}

	public SMPPEnquireLinkRespMessage(byte buf[])
			throws IllegalArgumentException {
		super.buf = new byte[16];
		if (buf.length != 16) {
			throw new IllegalArgumentException(SMPPConstant.SMC_MESSAGE_ERROR);
		} else {
			System.arraycopy(buf, 0, super.buf, 0, 16);
			super.sequence_Id = TypeConvert.byte2int(super.buf, 12);
			return;
		}
	}

	public String toString() {
		StringBuffer strBuf = new StringBuffer(100);
		strBuf.append("SMPPEnquireLinkRespMessage: ");
		strBuf.append("PacketLength=".concat(String.valueOf(String
				.valueOf(getMsgLength()))));
		strBuf.append(",CommandID=".concat(String.valueOf(String
				.valueOf(getCommandId()))));
		strBuf.append(",Status=".concat(String.valueOf(String
				.valueOf(getStatus()))));
		strBuf.append(",SequenceId=".concat(String.valueOf(String
				.valueOf(getSequenceId()))));
		return strBuf.toString();
	}

	public int getCommandId() {
		return 0x80000015;
	}
}

package com.huawei.insa2.comm.smpp.message;

import com.huawei.insa2.comm.smpp.SMPPConstant;
import com.huawei.insa2.util.TypeConvert;

public class SMPPUnbindRespMessage extends SMPPMessage {

	public SMPPUnbindRespMessage() {
		int len = 16;
		super.buf = new byte[len];
		setMsgLength(len);
		setCommandId(0x80000006);
		setStatus(0);
	}

	public SMPPUnbindRespMessage(byte buf[]) throws IllegalArgumentException {
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
		String tmpStr = "SMPP_Unbind_REP: ";
		tmpStr = String.valueOf(String.valueOf((new StringBuffer(String
				.valueOf(String.valueOf(tmpStr)))).append("Sequence_Id=")
				.append(getSequenceId())));
		return tmpStr;
	}
}

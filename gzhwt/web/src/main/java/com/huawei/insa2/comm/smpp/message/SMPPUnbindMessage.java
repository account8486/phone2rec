package com.huawei.insa2.comm.smpp.message;

import com.huawei.insa2.comm.smpp.SMPPConstant;
import com.huawei.insa2.util.TypeConvert;

public class SMPPUnbindMessage extends SMPPMessage {

	public SMPPUnbindMessage() {
		int len = 16;
		super.buf = new byte[len];
		setMsgLength(len);
		setCommandId(6);
		setStatus(0);
	}

	public SMPPUnbindMessage(byte buf[]) throws IllegalArgumentException {
		super.buf = new byte[4];
		if (buf.length != 4) {
			throw new IllegalArgumentException(SMPPConstant.SMC_MESSAGE_ERROR);
		} else {
			System.arraycopy(buf, 0, super.buf, 0, 4);
			super.sequence_Id = TypeConvert.byte2int(super.buf, 0);
			return;
		}
	}

	public String toString() {
		String tmpStr = "SMPP_Unbind: ";
		tmpStr = tmpStr + "Sequence_Id=" + getSequenceId();
		return tmpStr;
	}
}

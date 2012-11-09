package com.huawei.insa2.comm.cmpp.message;

import com.huawei.insa2.comm.cmpp.CMPPConstant;
import com.huawei.insa2.util.TypeConvert;

public class CMPPActiveMessage extends CMPPMessage {

	public CMPPActiveMessage() {
		int len = 12;
		super.buf = new byte[len];
		TypeConvert.int2byte(len, super.buf, 0);
		TypeConvert.int2byte(8, super.buf, 4);
	}

	public CMPPActiveMessage(byte buf[]) throws IllegalArgumentException {
		super.buf = new byte[4];
		if (buf.length != 4) {
			throw new IllegalArgumentException(CMPPConstant.SMC_MESSAGE_ERROR);
		} else {
			System.arraycopy(buf, 0, super.buf, 0, 4);
			super.sequence_Id = TypeConvert.byte2int(super.buf, 0);
			return;
		}
	}

	public String toString() {
		String tmpStr = "CMPP_Active_Test: ";
		tmpStr = tmpStr + "Sequence_Id=" + getSequenceId();
		return tmpStr;
	}

	public int getCommandId() {
		return CMPPConstant.Active_Test_Command_Id;
	}
}

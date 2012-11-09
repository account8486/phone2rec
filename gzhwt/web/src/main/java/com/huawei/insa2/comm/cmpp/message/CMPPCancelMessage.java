package com.huawei.insa2.comm.cmpp.message;

import com.huawei.insa2.comm.cmpp.CMPPConstant;
import com.huawei.insa2.util.TypeConvert;

public class CMPPCancelMessage extends CMPPMessage {

	public CMPPCancelMessage(byte msg_Id[]) throws IllegalArgumentException {
		if (msg_Id.length > 8) {
			throw new IllegalArgumentException(CMPPConstant.CONNECT_INPUT_ERROR
					+ ":msg_Id" + CMPPConstant.STRING_LENGTH_GREAT + "8");
		} else {
			int len = 20;
			super.buf = new byte[len];
			TypeConvert.int2byte(len, super.buf, 0);
			TypeConvert.int2byte(7, super.buf, 4);
			System.arraycopy(msg_Id, 0, super.buf, 12, msg_Id.length);
			return;
		}
	}

	public String toString() {
		String tmpStr = "CMPP_Cancel: ";
		tmpStr = tmpStr + "Sequence_Id=" + getSequenceId();
		return tmpStr;
	}

	public int getCommandId() {
		return CMPPConstant.Cancel_Command_Id;
	}

}

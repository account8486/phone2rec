package com.huawei.insa2.comm.cmpp.message;

import com.huawei.insa2.comm.cmpp.CMPPConstant;
import com.huawei.insa2.util.TypeConvert;

public class CMPPCancelRepMessage extends CMPPMessage {

	public CMPPCancelRepMessage(byte buf[]) throws IllegalArgumentException {
		super.buf = new byte[5];
		if (buf.length != 5) {
			throw new IllegalArgumentException(CMPPConstant.SMC_MESSAGE_ERROR);
		} else {
			System.arraycopy(buf, 0, super.buf, 0, 5);
			super.sequence_Id = TypeConvert.byte2int(super.buf, 0);
			return;
		}
	}

	public int getSuccessId() {
		return super.buf[4];
	}

	public String toString() {
		String tmpStr = "CMPP_Cancel_REP: ";
		tmpStr = tmpStr + "Sequence_Id=" + getSequenceId();
		tmpStr = tmpStr + ",SuccessId=" + this.getSuccessId();
		return tmpStr;
	}

	public int getCommandId() {
		return CMPPConstant.Cancel_Rep_Command_Id;
	}
}

package com.huawei.insa2.comm.cmpp.message;

import com.huawei.insa2.comm.cmpp.CMPPConstant;
import com.huawei.insa2.util.TypeConvert;

public class CMPPActiveRepMessage extends CMPPMessage {

	public CMPPActiveRepMessage(int success_Id) throws IllegalArgumentException {
		if (success_Id <= 0 || success_Id > 255) {
			throw new IllegalArgumentException(String.valueOf(String
					.valueOf((new StringBuffer(String.valueOf(String
							.valueOf(CMPPConstant.ACTIVE_REPINPUT_ERROR))))
							.append(":success_Id").append(
									CMPPConstant.INT_SCOPE_ERROR))));
		} else {
			int len = 13;
			super.buf = new byte[len];
			TypeConvert.int2byte(len, super.buf, 0);
			TypeConvert.int2byte(CMPPConstant.Active_Test_Rep_Command_Id,
					super.buf, 4);
			TypeConvert.int2byte(success_Id, super.buf, 8);
			super.buf[12] = (byte) success_Id;
			return;
		}
	}

	public CMPPActiveRepMessage(byte data_buff[])
			throws IllegalArgumentException {

		if (data_buff.length != 5) {
			throw new IllegalArgumentException(CMPPConstant.SMC_MESSAGE_ERROR);
		}

		int len = 13;
		super.buf = new byte[len];
		TypeConvert.int2byte(len, super.buf, 0);
		TypeConvert.int2byte(CMPPConstant.Active_Test_Rep_Command_Id,
				super.buf, 4);

		System.arraycopy(data_buff, 0, super.buf, 8, 5);
		super.sequence_Id = TypeConvert.byte2int(data_buff, 0);

	}

	public int getSuccessId() {
		return super.buf[12];
	}

	public String toString() {
		String tmpStr = "CMPP_Active_Test_REP: ";
		tmpStr = tmpStr + "Sequence_Id=" + getSequenceId();
		tmpStr = tmpStr + ",SuccessId=" + getSuccessId();
		return tmpStr;
	}

	public int getCommandId() {
		return CMPPConstant.Active_Test_Rep_Command_Id;
	}
}

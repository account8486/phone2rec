package com.huawei.insa2.comm.cmpp.message;

import com.huawei.insa2.comm.cmpp.CMPPConstant;
import com.huawei.insa2.util.TypeConvert;

public class CMPPConnectRepMessage extends CMPPMessage {

	public CMPPConnectRepMessage(byte data_buff[]) throws IllegalArgumentException {
		if (data_buff.length != 22) {
			throw new IllegalArgumentException(CMPPConstant.SMC_MESSAGE_ERROR);
		}
		
		int len = 30;
		super.buf = new byte[len];
		TypeConvert.int2byte(len, super.buf, 0);
		TypeConvert.int2byte(CMPPConstant.Connect_Rep_Command_Id, super.buf, 4);
		System.arraycopy(data_buff, 0, super.buf, 8, data_buff.length);
		super.sequence_Id = TypeConvert.byte2int(data_buff, 0);
	}

	public byte getStatus() {
		return super.buf[12];
	}

	public byte[] getAuthenticatorISMG() {
		byte tmpbuf[] = new byte[16];
		System.arraycopy(super.buf, 5, tmpbuf, 0, 16);
		return tmpbuf;
	}

	public byte getVersion() {
		return super.buf[21];
	}

	public String toString() {
		String tmpStr = "CMPP_Connect_REP: ";
		tmpStr = tmpStr + " Sequence_Id=" + getSequenceId();
		tmpStr = tmpStr + ", Status=" + getStatus();
		tmpStr = tmpStr + ", AuthenticatorISMG=" + getAuthenticatorISMG();
		tmpStr = tmpStr + ", Version=" + getVersion();
		return tmpStr;
	}

	public int getCommandId() {
		return CMPPConstant.Connect_Rep_Command_Id;
	}
}

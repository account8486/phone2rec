package com.huawei.opencmpp.message;

import java.util.Date;

import com.huawei.insa2.comm.cmpp.CMPPConstant;
import com.huawei.insa2.comm.cmpp.message.CMPPMessage;
import com.huawei.insa2.util.SecurityTools;
import com.huawei.insa2.util.TypeConvert;

public class CMPPConnectMessage extends CMPPMessage {

	public CMPPConnectMessage(String source_Addr, int version,
			String shared_Secret, Date timestamp)
			throws IllegalArgumentException {
		if (source_Addr == null) {
			throw new IllegalArgumentException(CMPPConstant.CONNECT_INPUT_ERROR
					+ ":source_Addr" + CMPPConstant.STRING_NULL);
		}

		if (source_Addr.length() > 6) {
			throw new IllegalArgumentException(CMPPConstant.CONNECT_INPUT_ERROR
					+ ":source_Addr" + CMPPConstant.STRING_LENGTH_GREAT + "6");
		}

		if (version < 0 || version > 255) {
			throw new IllegalArgumentException(CMPPConstant.CONNECT_INPUT_ERROR
					+ ":version" + CMPPConstant.INT_SCOPE_ERROR);
		}

		int len = 39;
		super.buf = new byte[len];

		TypeConvert.int2byte(len, super.buf, 0);
		TypeConvert.int2byte(1, super.buf, 4);

		System.arraycopy(source_Addr.getBytes(), 0, super.buf, 12, source_Addr
				.length());

		if (shared_Secret != null) {
			len = source_Addr.length() + 19 + shared_Secret.length();
		} else {
			len = source_Addr.length() + 19;
		}

		byte tmpbuf[] = new byte[len];
		int tmploc = 0;
		System.arraycopy(source_Addr.getBytes(), 0, tmpbuf, 0, source_Addr
				.length());
		tmploc = source_Addr.length() + 9;
		if (shared_Secret != null) {
			System.arraycopy(shared_Secret.getBytes(), 0, tmpbuf, tmploc,
					shared_Secret.length());
			tmploc += shared_Secret.length();
		}
		String tmptime = "0008080808";
		System.arraycopy(tmptime.getBytes(), 0, tmpbuf, tmploc, 10);
		SecurityTools.md5(tmpbuf, 0, len, super.buf, 18);
		super.buf[34] = (byte) version;
		TypeConvert.int2byte(0x7b4da8, super.buf, 35);
		outStr = ",source_Addr=" + source_Addr;
		outStr = outStr + ",version=" + version;
		outStr = outStr + ",shared_Secret=" + shared_Secret;
		outStr = outStr + ",timeStamp=" + tmptime;
	}

	public String toString() {
		String tmpStr = "CMPP_Connect: ";
		tmpStr = tmpStr + "Sequence_Id=" + getSequenceId();
		tmpStr = tmpStr + outStr;
		return tmpStr;
	}

	public int getCommandId() {
		return 1;
	}

	private String outStr;
}

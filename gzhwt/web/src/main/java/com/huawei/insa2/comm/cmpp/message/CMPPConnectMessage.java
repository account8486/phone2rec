package com.huawei.insa2.comm.cmpp.message;

import java.util.Date;

import com.huawei.insa2.comm.cmpp.CMPPConstant;
import com.huawei.insa2.comm.cmpp.message.CMPPMessage;
import com.huawei.insa2.util.SecurityTools;
import com.huawei.insa2.util.TypeConvert;

public class CMPPConnectMessage extends CMPPMessage {

	public CMPPConnectMessage(byte[] buf) {
		if (buf.length != 31) {
			throw new IllegalArgumentException(CMPPConstant.SMC_MESSAGE_ERROR);
		}

		super.sequence_Id = TypeConvert.byte2int(buf, 0);

		int len = 39;
		super.buf = new byte[len];
		TypeConvert.int2byte(len, super.buf, 0);
		TypeConvert.int2byte(CMPPConstant.Connect_Command_Id, super.buf, 4);

		System.arraycopy(buf, 0, super.buf, 8, 31);

		outStr = ",source_Addr=" + this.getSourceAddress();
		outStr = outStr + ",version=" + this.getVersion();
	}

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
		TypeConvert.int2byte(CMPPConstant.Connect_Command_Id, super.buf, 4);

		/* 6 位SourceAddress */
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

		TypeConvert.int2byte(8080808, super.buf, 35);

		outStr = ",source_Addr=" + source_Addr;
		outStr = outStr + ",version=" + version;
		outStr = outStr + ",shared_Secret=" + shared_Secret;
		outStr = outStr + ",timeStamp=" + tmptime;
	}

	/**
	 * 源地址，此处为SP_Id，即SP的企业代码。
	 * 
	 * @return
	 */
	public String getSourceAddress() {
		byte[] bytes = new byte[6];
		System.arraycopy(buf, 12, bytes, 0, 6);

		return new String(bytes);
	}

	public byte[] getAuthenticatorSource() {
		byte[] bytes = new byte[16];
		System.arraycopy(buf, 18, bytes, 0, 16);

		return bytes;
	}

	public String toString() {
		String tmpStr = "CMPP_Connect: ";
		tmpStr = tmpStr + "Sequence_Id=" + getSequenceId();
		tmpStr = tmpStr + outStr;
		return tmpStr;
	}

	/**
	 * 双方协商的版本号(高位4bit表示主版本号,低位4bit表示次版本号)
	 * 
	 * @return
	 */
	public int getVersion() {
		return buf[34];
	}

	/**
	 * 时间戳的明文,由客户端产生,格式为MMDDHHMMSS，即月日时分秒，10位数字的整型，右对齐 。
	 * 
	 * @return
	 */
	public int getTimestamp() {
		return TypeConvert.byte2int(buf, 35);
	}

	public int getCommandId() {
		return CMPPConstant.Connect_Command_Id;
	}

	private String outStr;
}

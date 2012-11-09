package com.huawei.insa2.comm.cmpp.message;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.huawei.insa2.comm.cmpp.CMPPConstant;
import com.huawei.insa2.util.TypeConvert;

public class CMPPQueryMessage extends CMPPMessage {

	public CMPPQueryMessage(Date time, int query_Type, String query_Code,
			String reserve) throws IllegalArgumentException {
		if (query_Type != 0 && query_Type != 1) {
			throw new IllegalArgumentException(CMPPConstant.QUERY_INPUT_ERROR
					+ ":query_Type" + CMPPConstant.VALUE_ERROR);
		}

		if (query_Code.length() > 10)
			throw new IllegalArgumentException(CMPPConstant.QUERY_INPUT_ERROR
					+ ":query_Code" + CMPPConstant.STRING_LENGTH_GREAT + "10");

		if (reserve.length() > 8) {
			throw new IllegalArgumentException(CMPPConstant.QUERY_INPUT_ERROR
					+ ":reserve" + CMPPConstant.STRING_LENGTH_GREAT + "8");
		}
		int len = 39;
		super.buf = new byte[len];
		TypeConvert.int2byte(len, super.buf, 0);
		TypeConvert.int2byte(CMPPConstant.Query_Command_Id, super.buf, 4);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		System.arraycopy(dateFormat.format(time).getBytes(), 0, super.buf, 12,
				dateFormat.format(time).length());
		super.buf[20] = (byte) query_Type;
		System.arraycopy(query_Code.getBytes(), 0, super.buf, 21, query_Code
				.length());
		System
				.arraycopy(reserve.getBytes(), 0, super.buf, 31, reserve
						.length());
		outStr = ",time=" + dateFormat.format(time);
		outStr = outStr + ",query_Type=" + query_Type;
		outStr = outStr + ",query_Code=" + query_Code;
		outStr = outStr + ",reserve=" + reserve;
	}

	public String toString() {
		String tmpStr = "CMPP_Query: ";
		tmpStr = String.valueOf(String.valueOf((new StringBuffer(String
				.valueOf(String.valueOf(tmpStr)))).append("Sequence_Id=")
				.append(getSequenceId())));
		tmpStr = String.valueOf(tmpStr) + String.valueOf(outStr);
		return tmpStr;
	}

	public int getCommandId() {
		return CMPPConstant.Query_Command_Id;
	}

	private String outStr;
}

package com.huawei.insa2.comm.smpp.message;

import com.huawei.insa2.comm.PMessage;
import com.huawei.insa2.util.TypeConvert;

public abstract class SMPPMessage extends PMessage implements Cloneable {
	protected byte buf[];

	protected int sequence_Id;

	public Object clone() {
		try {
			SMPPMessage m = (SMPPMessage) super.clone();
			m.buf = (byte[]) buf.clone();
			SMPPMessage smppmessage = m;
			return smppmessage;
		} catch (CloneNotSupportedException ex) {
			ex.printStackTrace();
		}
		Object obj = null;
		return obj;
	}

	public abstract String toString();

	public int getMsgLength() {
		int msgLength = TypeConvert.byte2int(buf, 0);
		return msgLength;
	}

	public void setMsgLength(int msgLength) {
		TypeConvert.int2byte(msgLength, buf, 0);
	}

	public int getCommandId() {
		int commandId = TypeConvert.byte2int(buf, 4);
		return commandId;
	}

	public void setCommandId(int commandId) {
		TypeConvert.int2byte(commandId, buf, 4);
	}

	/**
	 * This field will indicate the success or failure of a request. This field
	 * is only relevant in the response message, so in the request message it
	 * should contain NULL.
	 * 
	 * @return
	 */
	public int getStatus() {
		int status = TypeConvert.byte2int(buf, 8);
		return status;
	}

	public void setStatus(int status) {
		TypeConvert.int2byte(status, buf, 8);
	}

	public int getSequenceId() {
		sequence_Id = TypeConvert.byte2int(buf, 12);
		return sequence_Id;
	}

	public void setSequenceId(int sequence_Id) {
		this.sequence_Id = sequence_Id;
		TypeConvert.int2byte(sequence_Id, buf, 12);
	}

	public byte[] getBytes() {
		return buf;
	}

}

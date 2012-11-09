package com.huawei.insa2.comm.smpp.message;

import com.huawei.insa2.comm.smpp.SMPPConstant;

public class SMPPDeliverMessage extends SMPPMessage {
	private String serviceType;

	private int sourceAddrTon;

	private int sourceAddrNpi;

	private String sourceAddr;

	private int destAddrTon;

	private int destAddrNpi;

	private String destinationAddr;

	private int esmClass;

	private int protocolId;

	private int priorityFlag;

	private int registeredDelivery;

	private int dataCoding;

	private int smLength;

	private String shortMessage;

	public SMPPDeliverMessage(byte buf[]) throws IllegalArgumentException {
		int len = buf.length;
		if (buf.length > 332 || buf.length < 33) {
			throw new IllegalArgumentException(SMPPConstant.SMC_MESSAGE_ERROR);
		} else {
			super.buf = new byte[len];
			System.arraycopy(buf, 0, super.buf, 0, buf.length);
			int pos = 16;
			serviceType = getFirstStr(buf, pos);
			pos = pos + serviceType.length() + 1;
			sourceAddrTon = buf[pos];
			sourceAddrNpi = buf[pos + 1];
			pos += 2;
			sourceAddr = getFirstStr(buf, pos);
			pos = pos + sourceAddr.length() + 1;
			destAddrTon = buf[pos];
			destAddrNpi = buf[pos + 1];
			pos += 2;
			destinationAddr = getFirstStr(buf, pos);
			pos = pos + destinationAddr.length() + 1;
			esmClass = buf[pos];
			protocolId = buf[pos + 1];
			priorityFlag = buf[pos + 2];
			registeredDelivery = buf[pos + 5];
			dataCoding = buf[pos + 7];
			smLength = buf[pos + 9];
			byte tmpBuf[] = new byte[smLength];
			System.arraycopy(buf, pos + 10, tmpBuf, 0, smLength);
			shortMessage = new String(tmpBuf);
			return;
		}
	}

	private String getFirstStr(byte buf[], int sPos) {
		int deli = 0;
		byte tmpBuf[] = new byte[21];
		int pos;
		for (pos = sPos; buf[pos] != 0 && pos < buf.length; pos++)
			tmpBuf[pos - sPos] = buf[pos];

		if (pos == sPos) {
			return "";
		} else {
			String tmpStr = new String(tmpBuf);
			return tmpStr.substring(0, pos - sPos);
		}
	}

	public String getServiceType() {
		return serviceType;
	}

	public int getSourceAddrTon() {
		return sourceAddrTon;
	}

	public int getSourceAddrNpi() {
		return sourceAddrNpi;
	}

	public String getSourceAddr() {
		return sourceAddr;
	}

	public int getDestAddrTon() {
		return destAddrTon;
	}

	public int getDestAddrNpi() {
		return destAddrNpi;
	}

	public String getDestinationAddr() {
		return destinationAddr;
	}

	public int getEsmClass() {
		return esmClass;
	}

	public int getProtocolId() {
		return protocolId;
	}

	public int getPriorityFlag() {
		return priorityFlag;
	}

	public int getRegisteredDelivery() {
		return registeredDelivery;
	}

	public int getDataCoding() {
		return dataCoding;
	}

	public int getSmLength() {
		return smLength;
	}

	public String getShortMessage() {
		return shortMessage;
	}

	public String toString() {
		StringBuffer strBuf = new StringBuffer(600);
		strBuf.append("SMPPDeliverMessage: ");
		strBuf.append("PacketLength=" + getMsgLength());
		strBuf.append(",CommandID=" + getCommandId());
		strBuf.append(",Status=" + getStatus());
		strBuf.append(",Sequence_Id=" + getSequenceId());
		strBuf.append(",Service_Type=" + getServiceType());
		strBuf.append(",SourceAddrTon=" + getSourceAddrTon());
		strBuf.append(",SourceAddrNpi=" + getSourceAddrNpi());
		strBuf.append(",SourceAddr=" + getSourceAddr());
		strBuf.append(",DestAddrTon=" + getDestAddrTon());
		strBuf.append(",DestAddrNpi=" + getDestAddrNpi());
		strBuf.append(",DestinationAddr=" + getDestinationAddr());
		strBuf.append(",EsmClass=" + getEsmClass());
		strBuf.append(",ProtocolId=" + getProtocolId());
		strBuf.append(",PriorityFlag=" + getPriorityFlag());
		strBuf.append(",RegisteredDelivery=" + getRegisteredDelivery());
		strBuf.append(",DataCoding=" + getDataCoding());
		strBuf.append(",SmLength=" + getSmLength());
		strBuf.append(",ShortMessage=" + getShortMessage());
		return strBuf.toString();
	}

}

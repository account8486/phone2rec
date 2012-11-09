package com.huawei.insa2.comm.cmpp.message;

import java.util.Calendar;
import java.util.Date;

import com.huawei.insa2.comm.cmpp.CMPPConstant;
import com.huawei.insa2.util.TypeConvert;

public class CMPPDeliverMessage extends CMPPMessage {
	private int deliverType;

	public CMPPDeliverMessage(byte buf[]) throws IllegalArgumentException {
		deliverType = 0;
		deliverType = buf[67];
		int len = 77 + (buf[68] & 0xff);
		if (buf.length != len) {
			throw new IllegalArgumentException(CMPPConstant.SMC_MESSAGE_ERROR);
		} else {
			super.buf = new byte[len];
			System.arraycopy(buf, 0, super.buf, 0, buf.length);
			super.sequence_Id = TypeConvert.byte2int(super.buf, 0);
			return;
		}
	}

	public byte[] getMsgId() {
		byte tmpMsgId[] = new byte[8];
		System.arraycopy(super.buf, 4, tmpMsgId, 0, 8);
		return tmpMsgId;
	}

	public String getDestnationId() {
		byte tmpId[] = new byte[21];
		System.arraycopy(super.buf, 12, tmpId, 0, 21);
		return (new String(tmpId)).trim();
	}

	public String getServiceId() {
		byte tmpId[] = new byte[10];
		System.arraycopy(super.buf, 33, tmpId, 0, 10);
		return (new String(tmpId)).trim();
	}

	public int getTpPid() {
		int tmpId = super.buf[43];
		return tmpId;
	}

	public int getTpUdhi() {
		int tmpId = super.buf[44];
		return tmpId;
	}

	public int getMsgFmt() {
		int tmpFmt = super.buf[45];
		return tmpFmt;
	}

	public String getSrcterminalId() {
		byte tmpId[] = new byte[21];
		System.arraycopy(super.buf, 46, tmpId, 0, 21);
		return (new String(tmpId)).trim();
	}

	public int getRegisteredDeliver() {
		return super.buf[67];
	}

	public int getMsgLength() {
		return super.buf[68] & 0xff;
	}

	public byte[] getMsgContent() {
		if (deliverType == 0) {
			int len = super.buf[68] & 0xff;
			byte tmpContent[] = new byte[len];
			System.arraycopy(super.buf, 69, tmpContent, 0, len);
			return tmpContent;
		} else {
			return null;
		}
	}

	public String getReserve() {
		int loc = 69 + (super.buf[68] & 0xff);
		byte tmpReserve[] = new byte[8];
		System.arraycopy(super.buf, loc, tmpReserve, 0, 8);
		return (new String(tmpReserve)).trim();
	}

	public byte[] getStatusMsgId() {
		if (deliverType == 1) {
			byte tmpId[] = new byte[8];
			System.arraycopy(super.buf, 69, tmpId, 0, 8);
			return tmpId;
		} else {
			return null;
		}
	}

	public String getStat() {
		if (deliverType == 1) {
			byte tmpStat[] = new byte[7];
			System.arraycopy(super.buf, 77, tmpStat, 0, 7);
			return (new String(tmpStat)).trim();
		} else {
			return null;
		}
	}

	public Date getSubmitTime() {
		if (deliverType == 1) {
			byte tmpbyte[] = new byte[2];
			System.arraycopy(super.buf, 84, tmpbyte, 0, 2);
			String tmpstr = new String(tmpbyte);
			int tmpYear = 2000 + Integer.parseInt(tmpstr);
			System.arraycopy(super.buf, 86, tmpbyte, 0, 2);
			tmpstr = new String(tmpbyte);
			int tmpMonth = Integer.parseInt(tmpstr) - 1;
			System.arraycopy(super.buf, 88, tmpbyte, 0, 2);
			tmpstr = new String(tmpbyte);
			int tmpDay = Integer.parseInt(tmpstr);
			System.arraycopy(super.buf, 90, tmpbyte, 0, 2);
			tmpstr = new String(tmpbyte);
			int tmpHour = Integer.parseInt(tmpstr);
			System.arraycopy(super.buf, 92, tmpbyte, 0, 2);
			tmpstr = new String(tmpbyte);
			int tmpMinute = Integer.parseInt(tmpstr);
			Calendar calendar = Calendar.getInstance();
			calendar.set(tmpYear, tmpMonth, tmpDay, tmpHour, tmpMinute);
			return calendar.getTime();
		} else {
			return null;
		}
	}

	public Date getDoneTime() {
		if (deliverType == 1) {
			byte tmpbyte[] = new byte[2];
			System.arraycopy(super.buf, 94, tmpbyte, 0, 2);
			String tmpstr = new String(tmpbyte);
			int tmpYear = 2000 + Integer.parseInt(tmpstr);
			System.arraycopy(super.buf, 96, tmpbyte, 0, 2);
			tmpstr = new String(tmpbyte);
			int tmpMonth = Integer.parseInt(tmpstr) - 1;
			System.arraycopy(super.buf, 98, tmpbyte, 0, 2);
			tmpstr = new String(tmpbyte);
			int tmpDay = Integer.parseInt(tmpstr);
			System.arraycopy(super.buf, 100, tmpbyte, 0, 2);
			tmpstr = new String(tmpbyte);
			int tmpHour = Integer.parseInt(tmpstr);
			System.arraycopy(super.buf, 102, tmpbyte, 0, 2);
			tmpstr = new String(tmpbyte);
			int tmpMinute = Integer.parseInt(tmpstr);
			Calendar calendar = Calendar.getInstance();
			calendar.set(tmpYear, tmpMonth, tmpDay, tmpHour, tmpMinute);
			return calendar.getTime();
		} else {
			return null;
		}
	}

	public String getDestTerminalId() {
		if (deliverType == 1) {
			byte tmpId[] = new byte[21];
			System.arraycopy(super.buf, 104, tmpId, 0, 21);
			return new String(tmpId);
		} else {
			return null;
		}
	}

	public int getSMSCSequence() {
		if (deliverType == 1) {
			int tmpSequence = TypeConvert.byte2int(super.buf, 125);
			return tmpSequence;
		} else {
			return -1;
		}
	}

	public String toString() {
		String tmpStr = "CMPP_Deliver: ";
		tmpStr = tmpStr + "Sequence_Id=" + getSequenceId();
		tmpStr = tmpStr + "MsgId=" + getMsgId();
		tmpStr = tmpStr + "DestnationId=" + getDestnationId();
		tmpStr = tmpStr + "ServiceId=" + getServiceId();
		tmpStr = tmpStr + "TpPid=" + getTpPid();
		tmpStr = tmpStr + "TpUdhi=" + getTpUdhi();
		tmpStr = tmpStr + "MsgFmt=" + getMsgFmt();
		tmpStr = tmpStr + "SrcterminalId=" + getSrcterminalId();
		tmpStr = tmpStr + "RegisteredDeliver=" + getRegisteredDeliver();
		tmpStr = tmpStr + "MsgLength=" + getMsgLength();

		if (getRegisteredDeliver() == 1) {
			tmpStr = tmpStr + "Stat=" + getStat();
			tmpStr = tmpStr + "SubmitTime=" + getSubmitTime();
			tmpStr = tmpStr + "DoneTime=" + getDoneTime();
			tmpStr = tmpStr + "DestTerminalId=" + getDestTerminalId();
			tmpStr = tmpStr + "SMSCSequence=" + getSMSCSequence();
		} else {
			tmpStr = tmpStr + "MsgContent=" + getMsgContent();
		}

		tmpStr = tmpStr + "Reserve=" + getReserve();
		return tmpStr;
	}

	public int getCommandId() {
		return CMPPConstant.Deliver_Command_Id;
	}

}

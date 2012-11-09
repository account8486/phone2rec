package com.huawei.insa2.comm.cmpp.message;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.huawei.insa2.comm.cmpp.CMPPConstant;
import com.huawei.insa2.util.TypeConvert;

public class CMPPSubmitMessage extends CMPPMessage {
	private String outStr;

	public CMPPSubmitMessage(byte[] data_buff) {
		int len = data_buff.length + 8;
		super.buf = new byte[len];
		TypeConvert.int2byte(len, super.buf, 0);
		TypeConvert.int2byte(CMPPConstant.Submit_Command_Id, super.buf, 4);
		System.arraycopy(data_buff, 0, super.buf, 8, data_buff.length);
		super.sequence_Id = TypeConvert.byte2int(data_buff, 0);
	}

	/**
	 * CMPP_SUBMIT操作的目的是SP在与ISMG建立应用层连接后向ISMG提交短信。
	 * 
	 * @param pk_Total
	 *            相同Msg_Id的信息总条数，从1开始
	 * @param pk_Number
	 *            相同Msg_Id的信息序号，从1开始
	 * @param registered_Delivery
	 *            是否要求返回状态确认报告
	 *            <ul>
	 *            <li>0：不需要
	 *            <li>1：需要
	 *            <li>2：产生SMC话单（该类型短信仅供网关计费使用，不发送给目的终端)
	 *            </ul>
	 * @param msg_Level
	 *            信息级别
	 * @param service_Id
	 *            业务类型，是数字、字母和符号的组合。
	 * @param fee_UserType
	 *            计费用户类型字段
	 *            <ul>
	 *            <li>0：对目的终端MSISDN计费；
	 *            <li>1：对源终端MSISDN计费；
	 *            <li>2：对SP计费;
	 *            <li>3：表示本字段无效，对谁计费参见Fee_terminal_Id字段。
	 *            </ul>
	 * @param fee_Terminal_Id
	 *            被计费用户的号码（如本字节填空，则表示本字段无效，对谁计费参见Fee_UserType字段，本字段与Fee_UserType字段互斥）
	 * @param tp_Pid
	 *            GSM协议类型。
	 * @param tp_Udhi
	 *            GSM协议类型。详细是解释请参考GSM03.40中的9.2.3.23,仅使用1位，右对齐
	 * @param msg_Fmt
	 *            信息格式
	 *            <ul>
	 *            <li>0：ASCII串
	 *            <li>3：短信写卡操作
	 *            <li>4：二进制信息
	 *            <li>8：UCS2编码
	 *            <li>15：含GB汉字
	 *            </ul>
	 * @param msg_Src
	 *            信息内容来源(SP_Id)
	 * @param fee_Type
	 *            资费类别
	 *            <ul>
	 *            <li>01：对“计费用户号码”免费
	 *            <li>02：对“计费用户号码”按条计信息费
	 *            <li>03：对“计费用户号码”按包月收取信息费
	 *            <li>04：对“计费用户号码”的信息费封顶
	 *            <li>05：对“计费用户号码”的收费是由SP实现
	 *            </ul>
	 * @param fee_Code
	 *            资费代码（以分为单位）
	 * @param valid_Time
	 *            存活有效期，格式遵循SMPP3.3协议
	 * @param at_Time
	 *            定时发送时间，格式遵循SMPP3.3协议
	 * @param src_Terminal_Id
	 *            源号码。SP的服务代码或前缀为服务代码的长号码,
	 *            网关将该号码完整的填到SMPP协议Submit_SM消息相应的source_addr字段，该号码最终在用户手机上显示为短消息的主叫号码
	 * @param dest_Terminal_Id
	 *            接收短信的MSISDN号码
	 * @param msg_Content
	 *            信息内容
	 * @param reserve
	 *            保留
	 * @throws IllegalArgumentException
	 */
	public CMPPSubmitMessage(int pk_Total, int pk_Number,
			int registered_Delivery, int msg_Level, String service_Id,
			int fee_UserType, String fee_Terminal_Id, int tp_Pid, int tp_Udhi,
			int msg_Fmt, String msg_Src, String fee_Type, String fee_Code,
			Date valid_Time, Date at_Time, String src_Terminal_Id,
			String[] dest_Terminal_Id, byte[] msg_Content, String reserve)
			throws IllegalArgumentException {
		this(0, pk_Total, pk_Number, registered_Delivery, msg_Level,
				service_Id, fee_UserType, fee_Terminal_Id, tp_Pid, tp_Udhi,
				msg_Fmt, msg_Src, fee_Type, fee_Code, valid_Time, at_Time,
				src_Terminal_Id, dest_Terminal_Id, msg_Content, reserve);
	}

	/**
	 * CMPP_SUBMIT操作的目的是SP在与ISMG建立应用层连接后向ISMG提交短信。
	 * 
	 * @param msg_Id
	 *            信息标识，由SP侧短信网关本身产生，本处填空。
	 * @param pk_Total
	 *            相同Msg_Id的信息总条数，从1开始
	 * @param pk_Number
	 *            相同Msg_Id的信息序号，从1开始
	 * @param registered_Delivery
	 *            是否要求返回状态确认报告
	 *            <ul>
	 *            <li>0：不需要
	 *            <li>1：需要
	 *            <li>2：产生SMC话单（该类型短信仅供网关计费使用，不发送给目的终端)
	 *            </ul>
	 * @param msg_Level
	 *            信息级别
	 * @param service_Id
	 *            业务类型，是数字、字母和符号的组合。
	 * @param fee_UserType
	 *            计费用户类型字段
	 *            <ul>
	 *            <li>0：对目的终端MSISDN计费；
	 *            <li>1：对源终端MSISDN计费；
	 *            <li>2：对SP计费;
	 *            <li>3：表示本字段无效，对谁计费参见Fee_terminal_Id字段。
	 *            </ul>
	 * @param fee_Terminal_Id
	 *            被计费用户的号码（如本字节填空，则表示本字段无效，对谁计费参见Fee_UserType字段，本字段与Fee_UserType字段互斥）
	 * @param tp_Pid
	 *            GSM协议类型。
	 * @param tp_Udhi
	 *            GSM协议类型。详细是解释请参考GSM03.40中的9.2.3.23,仅使用1位，右对齐
	 * @param msg_Fmt
	 *            信息格式
	 *            <ul>
	 *            <li>0：ASCII串
	 *            <li>3：短信写卡操作
	 *            <li>4：二进制信息
	 *            <li>8：UCS2编码
	 *            <li>15：含GB汉字
	 *            </ul>
	 * @param msg_Src
	 *            信息内容来源(SP_Id)
	 * @param fee_Type
	 *            资费类别
	 *            <ul>
	 *            <li>01：对“计费用户号码”免费
	 *            <li>02：对“计费用户号码”按条计信息费
	 *            <li>03：对“计费用户号码”按包月收取信息费
	 *            <li>04：对“计费用户号码”的信息费封顶
	 *            <li>05：对“计费用户号码”的收费是由SP实现
	 *            </ul>
	 * @param fee_Code
	 *            资费代码（以分为单位）
	 * @param valid_Time
	 *            存活有效期，格式遵循SMPP3.3协议
	 * @param at_Time
	 *            定时发送时间，格式遵循SMPP3.3协议
	 * @param src_Terminal_Id
	 *            源号码。SP的服务代码或前缀为服务代码的长号码,
	 *            网关将该号码完整的填到SMPP协议Submit_SM消息相应的source_addr字段，该号码最终在用户手机上显示为短消息的主叫号码
	 * @param dest_Terminal_Id
	 *            接收短信的MSISDN号码
	 * @param msg_Content
	 *            信息内容
	 * @param reserve
	 *            保留
	 * @throws IllegalArgumentException
	 */
	public CMPPSubmitMessage(long msg_Id, int pk_Total, int pk_Number,
			int registered_Delivery, int msg_Level, String service_Id,
			int fee_UserType, String fee_Terminal_Id, int tp_Pid, int tp_Udhi,
			int msg_Fmt, String msg_Src, String fee_Type, String fee_Code,
			Date valid_Time, Date at_Time, String src_Terminal_Id,
			String[] dest_Terminal_Id, byte[] msg_Content, String reserve)
			throws IllegalArgumentException {

		validate(pk_Total, pk_Number, registered_Delivery, msg_Level,
				service_Id, fee_UserType, fee_Terminal_Id, tp_Pid, tp_Udhi,
				msg_Fmt, msg_Src, fee_Type, fee_Code, src_Terminal_Id,
				dest_Terminal_Id, msg_Content, reserve);

		int len = 138 + 21 * dest_Terminal_Id.length + msg_Content.length;
		super.buf = new byte[len];
		TypeConvert.int2byte(len, super.buf, 0);
		TypeConvert.int2byte(4, super.buf, 4);
		TypeConvert.long2byte(msg_Id, super.buf, 12);
		super.buf[20] = (byte) pk_Total;
		super.buf[21] = (byte) pk_Number;
		super.buf[22] = (byte) registered_Delivery;
		super.buf[23] = (byte) msg_Level;
		System.arraycopy(service_Id.getBytes(), 0, super.buf, 24, service_Id
				.length());
		super.buf[34] = (byte) fee_UserType;
		System.arraycopy(fee_Terminal_Id.getBytes(), 0, super.buf, 35,
				fee_Terminal_Id.length());
		super.buf[56] = (byte) tp_Pid;
		super.buf[57] = (byte) tp_Udhi;
		super.buf[58] = (byte) msg_Fmt;
		System
				.arraycopy(msg_Src.getBytes(), 0, super.buf, 59, msg_Src
						.length());
		System.arraycopy(fee_Type.getBytes(), 0, super.buf, 65, fee_Type
				.length());
		System.arraycopy(fee_Code.getBytes(), 0, super.buf, 67, fee_Code
				.length());
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMddHHmmss");
		if (valid_Time != null) {
			String tmpTime = String.valueOf(
					String.valueOf(dateFormat.format(valid_Time))).concat(
					"032+");
			System.arraycopy(tmpTime.getBytes(), 0, super.buf, 73, 16);
		}
		if (at_Time != null) {
			String tmpTime = String.valueOf(
					String.valueOf(dateFormat.format(at_Time))).concat("032+");
			System.arraycopy(tmpTime.getBytes(), 0, super.buf, 90, 16);
		}
		System.arraycopy(src_Terminal_Id.getBytes(), 0, super.buf, 107,
				src_Terminal_Id.length());
		super.buf[128] = (byte) dest_Terminal_Id.length;
		int i = 0;
		for (i = 0; i < dest_Terminal_Id.length; i++)
			System.arraycopy(dest_Terminal_Id[i].getBytes(), 0, super.buf,
					129 + i * 21, dest_Terminal_Id[i].length());

		int loc = 129 + i * 21;
		super.buf[loc] = (byte) msg_Content.length;
		System
				.arraycopy(msg_Content, 0, super.buf, loc + 1,
						msg_Content.length);
		loc = loc + 1 + msg_Content.length;
		System.arraycopy(reserve.getBytes(), 0, super.buf, loc, reserve
				.length());
		outStr = ",msg_id=" + this.getMessageId();
		outStr = outStr + ",pk_Total=" + pk_Total;
		outStr = outStr + ",pk_Number=" + pk_Number;
		outStr = outStr + ",registered_Delivery=" + registered_Delivery;
		outStr = outStr + ",msg_Level=" + msg_Level;
		outStr = outStr + ",service_Id=" + service_Id;
		outStr = outStr + ",fee_UserType=" + fee_UserType;
		outStr = outStr + ",fee_Terminal_Id=" + fee_Terminal_Id;
		outStr = outStr + ",tp_Pid=" + tp_Pid;
		outStr = outStr + ",tp_Udhi=" + tp_Udhi;
		outStr = outStr + ",msg_Fmt=" + msg_Fmt;
		outStr = outStr + ",msg_Src=" + msg_Src;
		outStr = outStr + ",fee_Type=" + fee_Type;
		outStr = outStr + ",fee_Code=" + fee_Code;

		if (valid_Time != null) {
			outStr = outStr + ",valid_Time=" + dateFormat.format(valid_Time);
		} else {
			outStr = outStr + ",valid_Time=null";
		}

		if (at_Time != null) {
			outStr = outStr + ",at_Time=" + dateFormat.format(at_Time);
		} else {
			outStr = outStr + ",at_Time=null";
		}

		outStr = outStr + ",src_Terminal_Id=" + src_Terminal_Id;
		outStr = outStr + ",destusr_Tl=" + dest_Terminal_Id.length;

		for (int t = 0; t < dest_Terminal_Id.length; t++) {
			outStr = outStr + ",dest_Terminal_Id[" + t + "]="
					+ dest_Terminal_Id[t];
		}

		outStr = outStr + ",msg_Length=" + msg_Content.length;
		if (msg_Fmt == 0) {
			try {
				outStr = outStr + ",msg_Content="
						+ new String(msg_Content, "ASCII");
			} catch (UnsupportedEncodingException _) {
			}
		} else if (msg_Fmt == 8) {
			try {
				outStr = outStr + ",msg_Content="
						+ new String(msg_Content, "ISO-10646-UCS-2");
			} catch (UnsupportedEncodingException _) {
			}
		} else if (msg_Fmt == 15) {
			try {
				outStr = outStr + ",msg_Content="
						+ new String(msg_Content, "GB2312");
			} catch (UnsupportedEncodingException _) {
			}
		} else {
			outStr = outStr + ",msg_Content=" + msg_Content;
		}

		outStr = outStr + ",reserve=" + reserve;
	}

	private void validate(int pk_Total, int pk_Number, int registered_Delivery,
			int msg_Level, String service_Id, int fee_UserType,
			String fee_Terminal_Id, int tp_Pid, int tp_Udhi, int msg_Fmt,
			String msg_Src, String fee_Type, String fee_Code,
			String src_Terminal_Id, String[] dest_Terminal_Id,
			byte[] msg_Content, String reserve) {
		if (pk_Total < 1 || pk_Total > 255) {
			throw new IllegalArgumentException(CMPPConstant.SUBMIT_INPUT_ERROR
					+ ":" + CMPPConstant.PK_TOTAL_ERROR);
		}

		if (pk_Number < 1 || pk_Number > 255) {
			throw new IllegalArgumentException(CMPPConstant.SUBMIT_INPUT_ERROR
					+ ":" + CMPPConstant.PK_NUMBER_ERROR);
		}

		if (registered_Delivery < 0 || registered_Delivery > 2) {
			throw new IllegalArgumentException(CMPPConstant.SUBMIT_INPUT_ERROR
					+ ":" + CMPPConstant.REGISTERED_DELIVERY_ERROR);
		}

		if (msg_Level < 0 || msg_Level > 255) {
			throw new IllegalArgumentException(CMPPConstant.SUBMIT_INPUT_ERROR
					+ ":msg_Level" + CMPPConstant.INT_SCOPE_ERROR);
		}

		if (service_Id.length() > 10) {
			throw new IllegalArgumentException(CMPPConstant.SUBMIT_INPUT_ERROR
					+ ":service_Id" + CMPPConstant.STRING_LENGTH_GREAT + "10");
		}

		if (fee_UserType < 0 || fee_UserType > 3) {
			throw new IllegalArgumentException(CMPPConstant.SUBMIT_INPUT_ERROR
					+ ":" + CMPPConstant.FEE_USERTYPE_ERROR);
		}

		if (fee_Terminal_Id.length() > 21) {
			throw new IllegalArgumentException(CMPPConstant.SUBMIT_INPUT_ERROR
					+ ":fee_Terminal_Id" + CMPPConstant.STRING_LENGTH_GREAT
					+ "21");
		}

		if (tp_Pid < 0 || tp_Pid > 255) {
			throw new IllegalArgumentException(CMPPConstant.SUBMIT_INPUT_ERROR
					+ ":tp_Pid" + CMPPConstant.INT_SCOPE_ERROR);
		}

		if (tp_Udhi < 0 || tp_Udhi > 255) {
			throw new IllegalArgumentException(CMPPConstant.SUBMIT_INPUT_ERROR
					+ ":tp_Udhi" + CMPPConstant.INT_SCOPE_ERROR);
		}

		if (msg_Fmt < 0 || msg_Fmt > 255) {
			throw new IllegalArgumentException(CMPPConstant.SUBMIT_INPUT_ERROR
					+ ":msg_Fmt" + CMPPConstant.INT_SCOPE_ERROR);
		}

		if (msg_Src.length() > 6) {
			throw new IllegalArgumentException(CMPPConstant.SUBMIT_INPUT_ERROR
					+ ":msg_Src" + CMPPConstant.STRING_LENGTH_GREAT + "6");
		}

		if (fee_Type.length() > 2) {
			throw new IllegalArgumentException(CMPPConstant.SUBMIT_INPUT_ERROR
					+ ":fee_Type" + CMPPConstant.STRING_LENGTH_GREAT + "2");
		}

		if (fee_Code.length() > 6) {
			throw new IllegalArgumentException(CMPPConstant.SUBMIT_INPUT_ERROR
					+ ":fee_Code" + CMPPConstant.STRING_LENGTH_GREAT + "6");
		}

		if (src_Terminal_Id.length() > 21) {
			throw new IllegalArgumentException(CMPPConstant.SUBMIT_INPUT_ERROR
					+ ":src_Terminal_Id" + CMPPConstant.STRING_LENGTH_GREAT
					+ "21");
		}

		if (dest_Terminal_Id.length > 100) {
			throw new IllegalArgumentException(CMPPConstant.SUBMIT_INPUT_ERROR
					+ ":dest_Terminal_Id" + CMPPConstant.STRING_LENGTH_GREAT
					+ "100");
		}

		for (int i = 0; i < dest_Terminal_Id.length; i++) {
			if (dest_Terminal_Id[i].length() > 21) {
				throw new IllegalArgumentException(
						CMPPConstant.SUBMIT_INPUT_ERROR + ":dest_Terminal_Id"
								+ CMPPConstant.STRING_LENGTH_GREAT + "21");
			}
		}

		if (msg_Fmt == 0) {
			if (msg_Content.length > 160) {
				throw new IllegalArgumentException(
						CMPPConstant.SUBMIT_INPUT_ERROR + ":msg_Content"
								+ CMPPConstant.STRING_LENGTH_GREAT + "160");
			}
		} else if (msg_Content.length > 140) {
			throw new IllegalArgumentException(CMPPConstant.SUBMIT_INPUT_ERROR
					+ ":msg_Content" + CMPPConstant.STRING_LENGTH_GREAT + "140");
		}

		if (reserve.length() > 8) {
			throw new IllegalArgumentException(CMPPConstant.SUBMIT_INPUT_ERROR
					+ ":reserve" + CMPPConstant.STRING_LENGTH_GREAT + "8");
		}
	}

	public void setMessageId(long msg_Id) {
		TypeConvert.long2byte(msg_Id, buf, 12);
	}

	public long getMessageId() {
		return TypeConvert.getLong(buf, 12);
	}

	public byte getPkTotal() {
		return buf[21];
	}

	public byte getPkNumber() {
		return buf[22];
	}

	public byte getLevel() {
		return buf[23];
	}

	public byte[] getServiceId() {
		byte[] bytes = new byte[10];
		System.arraycopy(buf, 24, bytes, 0, bytes.length);
		return bytes;
	}

	public byte getFeeUserType() {
		return buf[34];
	}

	public byte[] getFeeTerminalId() {
		byte[] bytes = new byte[21];
		System.arraycopy(buf, 35, bytes, 0, bytes.length);
		return bytes;
	}

	public byte getTPPId() {
		return super.buf[56];
	}

	public byte getTPUdhi() {
		return super.buf[57];
	}

	public byte getMsgFmt() {
		return super.buf[58];
	}

	public byte[] getMsgSrc() {
		byte[] bytes = new byte[6];
		System.arraycopy(buf, 59, bytes, 0, bytes.length);
		return bytes;
	}

	public byte[] getFeeType() {
		byte[] bytes = new byte[2];
		System.arraycopy(buf, 65, bytes, 0, bytes.length);
		return bytes;
	}

	public byte[] getFeeCode() {
		byte[] bytes = new byte[6];
		System.arraycopy(buf, 67, bytes, 0, bytes.length);
		return bytes;
	}

	public byte[] getValidTime() {
		byte[] bytes = new byte[17];
		System.arraycopy(buf, 73, bytes, 0, bytes.length);
		return bytes;
	}

	public byte[] getAtTime() {
		byte[] bytes = new byte[17];
		System.arraycopy(buf, 90, bytes, 0, bytes.length);
		return bytes;
	}

	public byte[] getSrcId() {
		byte[] bytes = new byte[21];
		System.arraycopy(buf, 107, bytes, 0, bytes.length);
		return bytes;
	}

	/**
	 * 接收信息的用户数量(小于100个用户)
	 * 
	 * @return
	 */
	public byte getDestUsrTl() {
		return buf[128];
	}

	/**
	 * 接收短信的MSISDN号码
	 * 
	 * @return
	 */
	public String[] getDestTerminalId() {
		byte len = getDestUsrTl();
		String[] terminals = new String[len];
		for (int i = 0; i < len; ++i) {
			byte[] bytes = new byte[21];
			System.arraycopy(buf, 129 + i * 21, bytes, 0, bytes.length);
			terminals[i] = new String(bytes);
		}

		return terminals;
	}

	public byte getMsgLength() {
		int loc = 129 + getDestUsrTl() * 21;
		return buf[loc];
	}

	public byte[] getMsgContent() {
		int loc = 129 + getDestUsrTl() * 21;
		byte len = buf[loc];

		byte[] bytes = new byte[len];
		System.arraycopy(buf, loc + 1, bytes, 0, bytes.length);
		return bytes;
	}

	public String toString() {
		String tmpStr = "CMPP_Submit: ";
		tmpStr = tmpStr + "Sequence_Id=" + getSequenceId();
		tmpStr = tmpStr + outStr;
		return tmpStr;
	}

	public int getCommandId() {
		return CMPPConstant.Submit_Command_Id;
	}

}

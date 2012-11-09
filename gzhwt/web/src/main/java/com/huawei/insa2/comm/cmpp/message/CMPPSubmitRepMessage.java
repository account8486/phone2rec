package com.huawei.insa2.comm.cmpp.message;

import com.huawei.insa2.comm.cmpp.CMPPConstant;
import com.huawei.insa2.util.TypeConvert;

public class CMPPSubmitRepMessage extends CMPPMessage {

	public CMPPSubmitRepMessage(int seq_id, byte[] msg_id, byte result) {
		if (seq_id < 0) {
			throw new IllegalArgumentException(CMPPConstant.SMC_MESSAGE_ERROR);
		}

		if (msg_id == null || msg_id.length != 8) {
			throw new IllegalArgumentException(CMPPConstant.SMC_MESSAGE_ERROR);
		}

		int len = 21;
		super.buf = new byte[len];
		TypeConvert.int2byte(len, super.buf, 0);
		TypeConvert.int2byte(CMPPConstant.Submit_Rep_Command_Id, super.buf, 4);
		TypeConvert.int2byte(seq_id, super.buf, 8);
		System.arraycopy(msg_id, 0, super.buf, 12, 8);
		super.buf[20] = result;
		this.sequence_Id = seq_id;
	}

	public CMPPSubmitRepMessage(byte buf[]) throws IllegalArgumentException {
		if (buf.length != 13) {
			throw new IllegalArgumentException(CMPPConstant.SMC_MESSAGE_ERROR);
		}

		int len = 21;
		super.buf = new byte[len];
		TypeConvert.int2byte(len, super.buf, 0);
		TypeConvert.int2byte(CMPPConstant.Submit_Rep_Command_Id, super.buf, 4);

		System.arraycopy(buf, 0, super.buf, 8, 13);
		super.sequence_Id = TypeConvert.byte2int(buf, 0);

	}

	/**
	 * 信息标识，生成算法如下：<br>
	 * 
	 * 采用64位（8字节）的整数：<br>
	 * （1）时间（格式为MMDDHHMMSS，即月日时分秒）：bit64~bit39，其中<BR>
	 * <ul>
	 * <li>bit64~bit61：月份的二进制表示；
	 * <li>bit60~bit56：日的二进制表示；
	 * <li>bit55~bit51：小时的二进制表示；
	 * <li>bit50~bit45：分的二进制表示；
	 * <li>bit44~bit39：秒的二进制表示；
	 * </ul>
	 * （2）短信网关代码：bit38~bit17，把短信网关的代码转换为整数填写到该字段中。<BR>
	 * （3）序列号：bit16~bit1，顺序增加，步长为1，循环使用。<BR>
	 * 各部分如不能填满，左补零，右对齐。<BR>
	 * （SP根据请求和应答消息的Sequence_Id一致性就可得到CMPP_Submit消息的Msg_Id）<BR>
	 * 
	 * @return
	 */
	public byte[] getMsgId() {
		byte tmpMsgId[] = new byte[8];
		System.arraycopy(super.buf, 12, tmpMsgId, 0, 8);
		return tmpMsgId;
	}

	/**
	 * 结果
	 * 
	 * 0：正确<br>
	 * 1：消息结构错<br>
	 * 2：命令字错<br>
	 * 3：消息序号重复<br>
	 * 4：消息长度错<br>
	 * 5：资费代码错<br>
	 * 6：超过最大信息长<br>
	 * 7：业务代码错<br>
	 * 8：流量控制错<br>
	 * 9~ ：其他错误<br>
	 */
	public int getResult() {
		return super.buf[20];
	}

	public String toString() {
		String tmpStr = "CMPP_Submit_REP: ";
		tmpStr = tmpStr + "Sequence_Id=" + getSequenceId();
		tmpStr = tmpStr + ",MsgId=" + TypeConvert.getLong(getMsgId(), 0);
		tmpStr = tmpStr + ",Result=" + getResult();
		return tmpStr;
	}

	public int getCommandId() {
		return CMPPConstant.Submit_Rep_Command_Id;
	}
}

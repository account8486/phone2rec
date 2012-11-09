package com.huawei.insa2.comm.smpp.message;

import com.huawei.insa2.comm.smpp.SMPPConstant;

/**
 * This command is issued by the ESME to submit a short message to the SMSC for
 * transmission to a specified subscriber. When a real source address is
 * provided in a registered submit_sm request, the source address can be used as
 * the destination address for a delivery receipt. It can also be used in
 * identifying the message source in a CDR. This source address must fall in the
 * range of addresses associated with the bind command. Where the originator of
 * messages from the ESME is the ESME itself, or where the ESME does not have a
 * real source address, the source address fields may be defaulted to NULL, and
 * the source address will be taken from the SMSC administration “callback
 * address” for the particular ESME instance. The submit_sm operation can also
 * be used to replace a short message which has previously been submitted. This
 * is achieved by setting the replace_if_present_flag to 0x01 in the Interface.
 * The first message found in the SMSC whose source and destination match those
 * given in the submit_sm will have it’s text replaced by the text in the
 * short_message field of the submit_sm.
 * 
 * 
 */
public class SMPPSubmitMessage extends SMPPMessage {
	private StringBuffer strBuf;

	protected final String serviceType;

	protected final byte sourceAddrTon;

	protected final byte sourceAddrNpi;

	protected final String sourceAddr;

	protected final byte destAddrTon;

	protected final byte destAddrNpi;

	protected final String destinationAddr;

	protected final byte esmClass;

	protected final byte protocolId;

	protected final byte priorityFlag;

	protected final String scheduleDeliveryTime;

	protected final String validityPeriod;

	protected final byte registeredDelivery;

	protected final byte replaceIfPresentFlag;

	protected final byte dataCoding;

	protected final byte smDefaultMsgId;

	protected final byte smLength;

	protected final String shortMessage;

	/**
	 * 
	 * @param serviceType
	 *            Var. Max 6. Indicates the type of service associated with the
	 *            message. Where not required this should be set to a single
	 *            NULL byte.
	 * @param sourceAddrTon
	 *            Type of number for source. Where not required this should be
	 *            NULL. (See GSM 03.40 [2] 9.1.2.5)
	 * @param sourceAddrNpi
	 *            Numbering Plan Indicator for source Where not required this
	 *            should be NULL. (See GSM 03.40 [2] 9.1.2.5)
	 * @param sourceAddr
	 *            Address of SME which originated this message. This is the
	 *            source address of the short message submitted. This variable
	 *            length field may have leading spaces. Where not required this
	 *            should be a single NULL byte.
	 * @param destAddrTon
	 *            Type of number for destination. Where not required this should
	 *            be NULL (See GSM 03.40 [2] 9.1.2.5)
	 * @param destAddrNpi
	 *            Numbering Plan Indicator for destination Where not required
	 *            this should be NULL. (See GSM 03.40 [2] 9.1.2.5)
	 * @param destinationAddr
	 *            Destination address of this short message. For mobile
	 *            terminated messages, this is the SME address of the target
	 *            subscriber. This variable length field may have leading
	 *            spaces. Where not required this should be a single NULL byte.
	 * @param esmClass
	 *            Indication of message type. For the submit_sm command this
	 *            field is unused, and should be set to NULL. For the deliver_sm
	 *            command however, this field may identify the message as a
	 *            delivery receipt.
	 * @param protocolId
	 *            GSM Protocol ID (See GSM 03.40 [2] 9.2.3.9)
	 * @param priorityFlag
	 *            Designates the message as priority. Setting priority on a
	 *            message moves it to the top of the SMSC message queue for that
	 *            subscriber. 0 = non-priority (default) 1 = priority
	 *            >1=Reserved
	 * @param scheduleDeliveryTime
	 *            The absolute date and time at which delivery of this message
	 *            must be attempted. The format is defined in section 7.5 Where
	 *            not required this should be a single NULL byte.
	 * @param validityPeriod
	 *            The expiration time of this message. This is specified as an
	 *            absolute date and time of expiry. The format is defined in
	 *            section 7.5 Where not required this should be a single NULL
	 *            byte.
	 * @param registeredDelivery
	 *            Flag indicating if the message is a registered short message
	 *            and thus if a Delivery Receipt is required upon the message
	 *            attaining a final state. 0=No receipt required (non-registered
	 *            delivery). 1=Receipt required (registered delivery)
	 *            >1=Reserved
	 * @param replaceIfPresentFlag
	 *            Flag indicating if submitted message should replace an
	 *            existing message between the specified source and destination.
	 *            0=Don’t Replace (default) 1=Replace >1=Reserved
	 * @param dataCoding
	 *            GSM Data-Coding-Scheme ( See GSM 03.40 [2] 9.2.3.10)
	 * @param smDefaultMsgId
	 *            Indicates the default short message to send, by providing an
	 *            index into the table of Predefined Messages set up by the SMSC
	 *            administrator. This should be set to NULL if a text message is
	 *            being sent. Range is 0x01 to 0x64. (See SMPP Applications
	 *            Guide [1] - Default Short Message).
	 * @param smLength
	 *            Length of the text of the message in bytes.
	 * @param shortMessage
	 *            Up to 160 bytes of data. This is the text that is transmitted
	 *            to the mobile station. Note that only ‘sm_length’ bytes will
	 *            be used.
	 * @throws IllegalArgumentException
	 */
	public SMPPSubmitMessage(String serviceType, byte sourceAddrTon,
			byte sourceAddrNpi, String sourceAddr, byte destAddrTon,
			byte destAddrNpi, String destinationAddr, byte esmClass,
			byte protocolId, byte priorityFlag, String scheduleDeliveryTime,
			String validityPeriod, byte registeredDelivery,
			byte replaceIfPresentFlag, byte dataCoding, byte smDefaultMsgId,
			byte smLength, String shortMessage) throws IllegalArgumentException {
		if (serviceType.length() > 5) {
			throw new IllegalArgumentException(SMPPConstant.SUBMIT_INPUT_ERROR
					+ ":serviceType " + SMPPConstant.STRING_LENGTH_GREAT + "5");
		}

		if (sourceAddr.length() > 20) {
			throw new IllegalArgumentException(SMPPConstant.SUBMIT_INPUT_ERROR
					+ ":sourceAddr " + SMPPConstant.STRING_LENGTH_GREAT + "20");
		}

		if (destinationAddr == null)
			throw new IllegalArgumentException(SMPPConstant.SUBMIT_INPUT_ERROR
					+ ":destinationAddr " + SMPPConstant.STRING_NULL);

		if (destinationAddr.length() > 20) {
			throw new IllegalArgumentException(SMPPConstant.SUBMIT_INPUT_ERROR
					+ ":destinationAddr " + SMPPConstant.STRING_LENGTH_GREAT
					+ "20");
		}

		if (scheduleDeliveryTime.length() != 0
				&& scheduleDeliveryTime.length() != 16) {
			throw new IllegalArgumentException(SMPPConstant.SUBMIT_INPUT_ERROR
					+ ":scheduleDeliveryTime "
					+ SMPPConstant.STRING_LENGTH_NOTEQUAL + "16");
		}

		if (validityPeriod.length() != 0 && validityPeriod.length() != 16) {
			throw new IllegalArgumentException(SMPPConstant.SUBMIT_INPUT_ERROR
					+ ":validityPeriod " + SMPPConstant.STRING_LENGTH_NOTEQUAL
					+ "16");
		}

		if (smLength > 254) {
			throw new IllegalArgumentException(SMPPConstant.SUBMIT_INPUT_ERROR
					+ ":smLength " + SMPPConstant.STRING_LENGTH_GREAT + "254");
		}

		if (shortMessage.length() > 254) {
			throw new IllegalArgumentException(SMPPConstant.SUBMIT_INPUT_ERROR
					+ ":shortMessage " + SMPPConstant.STRING_LENGTH_GREAT
					+ "254");
		}

		this.serviceType = serviceType;
		this.sourceAddrTon = sourceAddrTon;
		this.sourceAddrNpi = sourceAddrNpi;
		this.sourceAddr = destinationAddr;
		this.destAddrTon = destAddrTon;
		this.destAddrNpi = destAddrNpi;
		this.destinationAddr = destinationAddr;
		this.esmClass = esmClass;
		this.protocolId = protocolId;
		this.priorityFlag = priorityFlag;
		this.scheduleDeliveryTime = scheduleDeliveryTime;
		this.validityPeriod = validityPeriod;
		this.registeredDelivery = registeredDelivery;
		this.replaceIfPresentFlag = replaceIfPresentFlag;
		this.dataCoding = dataCoding;
		this.smDefaultMsgId = smDefaultMsgId;
		this.smLength = smLength;
		this.shortMessage = shortMessage;

		int len = 33 + serviceType.length() + sourceAddr.length()
				+ destinationAddr.length() + scheduleDeliveryTime.length()
				+ validityPeriod.length() + smLength;
		super.buf = new byte[len];
		setMsgLength(len);
		setCommandId(4);
		setStatus(0);
		int pos = 16;
		System.arraycopy(serviceType.getBytes(), 0, super.buf, pos, serviceType
				.length());
		pos = pos + serviceType.length() + 1;
		super.buf[pos] = sourceAddrTon;
		pos++;
		super.buf[pos] = sourceAddrNpi;
		pos++;
		System.arraycopy(sourceAddr.getBytes(), 0, super.buf, pos, sourceAddr
				.length());
		pos = pos + sourceAddr.length() + 1;
		super.buf[pos] = destAddrTon;
		pos++;
		super.buf[pos] = destAddrNpi;
		pos++;
		System.arraycopy(destinationAddr.getBytes(), 0, super.buf, pos,
				destinationAddr.length());
		pos = pos + destinationAddr.length() + 1;
		super.buf[pos++] = esmClass;
		super.buf[pos++] = protocolId;
		super.buf[pos++] = priorityFlag;
		System.arraycopy(scheduleDeliveryTime.getBytes(), 0, super.buf, pos,
				scheduleDeliveryTime.length());
		pos = pos + scheduleDeliveryTime.length() + 1;
		System.arraycopy(validityPeriod.getBytes(), 0, super.buf, pos,
				validityPeriod.length());
		pos += (validityPeriod.length() + 1);
		super.buf[pos++] = registeredDelivery;
		super.buf[pos++] = replaceIfPresentFlag;
		super.buf[pos++] = dataCoding;
		super.buf[pos++] = smDefaultMsgId;
		super.buf[pos++] = smLength;
		System.arraycopy(shortMessage.getBytes(), 0, super.buf, pos, smLength);

		strBuf = new StringBuffer(600);
		strBuf.append(",serviceType=" + serviceType);
		strBuf.append(",sourceAddrTon=" + sourceAddrTon);
		strBuf.append(",sourceAddrNpi=" + sourceAddrNpi);
		strBuf.append(",sourceAddr=" + sourceAddr);

		strBuf.append(",destAddrTon=" + destAddrTon);
		strBuf.append(",destAddrNpi=" + destAddrNpi);
		strBuf.append(",destinationAddr=" + destinationAddr);
		strBuf.append(",esmClass=" + esmClass);
		strBuf.append(",protocolId=" + protocolId);

		strBuf.append(",priorityFlag=" + priorityFlag);
		strBuf.append(",scheduleDeliveryTime=" + scheduleDeliveryTime);
		strBuf.append(",validityPeriod=" + validityPeriod);
		strBuf.append(",registeredDelivery=" + registeredDelivery);
		strBuf.append(",replaceIfPresentFlag=" + replaceIfPresentFlag);

		strBuf.append(",dataCoding=" + dataCoding);
		strBuf.append(",smDefaultMsgId=" + smDefaultMsgId);
		strBuf.append(",smLength=" + smLength);
		strBuf.append(",shortMessage=" + shortMessage);
	}

	public SMPPSubmitMessage(byte[] buf) throws IllegalArgumentException {
		super.buf = new byte[buf.length];
		System.arraycopy(buf, 0, super.buf, 0, buf.length);

		int pos = 16;
		for (; pos < this.buf.length; ++pos) {
			if (buf[pos] == 0) {
				break;
			}
		}

		int len = pos - 16;

		this.serviceType = new String(buf, 16, len);

		pos++;
		this.sourceAddrTon = buf[pos++];
		this.sourceAddrNpi = buf[pos++];

		int start = pos;

		for (; pos < this.buf.length; ++pos) {
			if (buf[pos] == 0) {
				break;
			}
		}

		this.sourceAddr = new String(buf, start, pos - start);
		pos++;
		this.destAddrTon = buf[pos++];
		this.destAddrNpi = buf[pos++];

		start = pos;

		for (; pos < this.buf.length; ++pos) {
			if (buf[pos] == 0) {
				break;
			}
		}

		this.destinationAddr = new String(buf, start, pos - start);
		pos++;

		this.esmClass = buf[pos++];
		this.protocolId = buf[pos++];
		this.priorityFlag = buf[pos++];

		this.scheduleDeliveryTime = new String(buf, pos, 16);
		pos += 17;

		this.validityPeriod = new String(buf, pos, 16);
		pos += 17;

		this.registeredDelivery = buf[pos++];
		this.replaceIfPresentFlag = buf[pos++];
		this.dataCoding = buf[pos++];
		this.smDefaultMsgId = buf[pos++];
		this.smLength = buf[pos++];
		this.shortMessage = new String(buf, pos, smLength);
	}

	public String getServiceType() {
		return serviceType;
	}

	public byte getSourceAddrTon() {
		return this.sourceAddrTon;
	}

	public byte getSourceAddrNpi() {
		return this.sourceAddrNpi;
	}

	public String getSourceAddress() {
		return sourceAddr;
	}

	public byte getDestAddrTon() {
		return this.destAddrTon;
	}

	public byte getDestAddrNpi() {
		return this.destAddrNpi;
	}

	public String getDestAddress() {
		return this.destinationAddr;
	}

	public byte getEsmClass() {
		return this.esmClass;
	}

	public byte getProtocolId() {
		return this.protocolId;
	}

	public String getScheduleDeliveryTime() {
		return this.scheduleDeliveryTime;
	}

	public String getValidityPeriod() {
		return this.validityPeriod;
	}

	public String getShortMessage() {
		return this.shortMessage;
	}

	public String toString() {
		StringBuffer outBuf = new StringBuffer(600);
		outBuf.append("SMPPSubmitMessage: ");
		outBuf.append("PacketLength=" + getMsgLength());
		outBuf.append(",CommandID=" + getCommandId());
		outBuf.append(",Status=" + getStatus());
		outBuf.append(",SequenceID=" + getSequenceId());
		if (strBuf != null) {
			outBuf.append(strBuf.toString());
		}
		return outBuf.toString();
	}

}

package com.huawei.insa2.comm.smpp.message;

import com.huawei.insa2.comm.smpp.SMPPConstant;

public class SMPPLoginMessage extends SMPPMessage {
	private StringBuffer strBuf;

	private int loginCommandId;

	/**
	 * 
	 * @param logintype
	 * @param systemId
	 *            Var. Max 16. Identifies the system requesting a bind to the
	 *            SMSC. This variable length field may have leading spaces.
	 * @param password
	 *            Var. Max 9. The password is used for security purposes. This
	 *            is a configurable attribute within the SMSC.
	 * @param systemType
	 *            Var. Max 13. Identifies the type of system requesting the
	 *            bind. This may enable SMSC responses which are particular to a
	 *            given type of ESME. This variable length field may have
	 *            leading spaces.
	 * @param interfaceVersion
	 *            Identifies the version number (major) of the interface to be
	 *            implemented.
	 * @param addrTon
	 *            Type of Number for use in routing Delivery Receipts. (See GSM
	 *            03.40 [2] 9.1.2.5) Where not required this should be NULL.
	 * @param addrNpi
	 *            Numbering Plan Identity for use in routing Delivery Receipts.
	 *            (See GSM 03.40 [2] 9.1.2.5) Where not required this should be
	 *            NULL.
	 * @param addressRange
	 *            Address range for use in routing short messages and Delivery
	 *            Receipts to an ESME. This variable length field may have
	 *            leading spaces. Where not required this should be a single
	 *            NULL byte.
	 * @throws IllegalArgumentException
	 */
	public SMPPLoginMessage(int logintype, String systemId, String password,
			String systemType, byte interfaceVersion, byte addrTon,
			byte addrNpi, String addressRange) throws IllegalArgumentException {
		if (logintype != 1 && logintype != 2) {
			throw new IllegalArgumentException(SMPPConstant.CONNECT_INPUT_ERROR
					+ ":loginCommandId " + SMPPConstant.OTHER_ERROR);
		}

		loginCommandId = logintype;
		if (systemId.length() > 15) {
			throw new IllegalArgumentException(SMPPConstant.CONNECT_INPUT_ERROR
					+ ":systemId " + SMPPConstant.STRING_LENGTH_GREAT + "15");
		}

		if (password.length() > 8) {
			throw new IllegalArgumentException(SMPPConstant.CONNECT_INPUT_ERROR
					+ ":password " + SMPPConstant.STRING_LENGTH_GREAT + "8");
		}

		if (systemType.length() > 12) {
			throw new IllegalArgumentException(SMPPConstant.CONNECT_INPUT_ERROR
					+ ":systemType " + SMPPConstant.STRING_LENGTH_GREAT + "12");
		}

		if (addressRange.length() > 40) {
			throw new IllegalArgumentException(SMPPConstant.CONNECT_INPUT_ERROR
					+ ":addressRange " + SMPPConstant.STRING_LENGTH_GREAT
					+ "40");
		}
		int len = 23 + systemId.length() + password.length()
				+ systemType.length() + addressRange.length();

		super.buf = new byte[len];
		setMsgLength(len);
		setCommandId(loginCommandId);
		setStatus(0);
		int pos = 16;
		System.arraycopy(systemId.getBytes(), 0, super.buf, pos, systemId
				.length());
		pos = pos + systemId.length() + 1;
		System.arraycopy(password.getBytes(), 0, super.buf, pos, password
				.length());
		pos = pos + password.length() + 1;
		System.arraycopy(systemType.getBytes(), 0, super.buf, pos, systemType
				.length());
		pos = pos + systemType.length() + 1;
		super.buf[pos++] = interfaceVersion;
		super.buf[pos++] = addrTon;
		super.buf[pos++] = addrNpi;

		System.arraycopy(addressRange.getBytes(), 0, super.buf, pos,
				addressRange.length());
		strBuf = new StringBuffer(200);
		strBuf.append(",systemID=" + systemId);
		strBuf.append(",password=" + password);
		strBuf.append(",systemType=" + systemType);
		strBuf.append(",interfaceVersion=" + interfaceVersion);
		strBuf.append(",addrTon=" + addrTon);
		strBuf.append(",addrNpi=" + addrNpi);
		strBuf.append(",addressRange=" + addressRange);
	}

	/**
	 * Identifies the system requesting a bind to the SMSC. This variable length
	 * field may have leading spaces.
	 * 
	 * @return
	 */
	public String getSystemId() {
		int pos = 16;
		for (; pos < this.buf.length; ++pos) {
			if (buf[pos] == 0) {
				break;
			}
		}

		int len = pos - 16;

		return new String(buf, 16, len);
	}

	/**
	 * The password is used for security purposes. This is a configurable
	 * attribute within the SMSC.
	 * 
	 * @return
	 */
	public String getPassword() {
		int pos = 16;

		int zero_counter = 0;

		int start = -1;

		for (; pos < this.buf.length; ++pos) {
			if (buf[pos] == 0) {
				zero_counter++;

				if (zero_counter == 1) {
					start = pos + 1;
					continue;
				}

				if (zero_counter == 2) {
					break;
				}
			}
		}

		int len = pos - start;

		return new String(buf, start, len);
	}

	/**
	 * Identifies the type of system requesting the bind. This may enable SMSC
	 * responses which are particular to a given type of ESME. This variable
	 * length field may have leading spaces.
	 * 
	 * @return
	 */
	public String getSystemType() {
		int pos = 16;

		int zero_counter = 0;

		int start = -1;

		for (; pos < this.buf.length; ++pos) {
			if (buf[pos] == 0) {
				zero_counter++;

				if (zero_counter == 2) {
					start = pos + 1;
					continue;
				}

				if (zero_counter == 3) {
					break;
				}
			}
		}

		int len = pos - start;

		return new String(buf, start, len);
	}

	/**
	 * Identifies the version number (major) of the interface to be implemented.
	 * 
	 * @return
	 */
	public byte getIntefaceVersion() {
		int pos = 16;

		int zero_counter = 0;

		for (; pos < this.buf.length; ++pos) {
			if (buf[pos] == 0) {
				zero_counter++;

				if (zero_counter == 3) {
					break;
				}
			}
		}

		return buf[pos + 1];
	}

	/**
	 * Type of Number for use in routing Delivery Receipts. (See GSM 03.40 [2]
	 * 9.1.2.5) Where not required this should be NULL.
	 * 
	 * @return
	 */
	public byte getAddrTon() {
		int pos = 16;

		int zero_counter = 0;

		for (; pos < this.buf.length; ++pos) {
			if (buf[pos] == 0) {
				zero_counter++;

				if (zero_counter == 3) {
					break;
				}
			}
		}

		return buf[pos + 2];
	}

	/**
	 * Numbering Plan Identity for use in routing Delivery Receipts. (See GSM
	 * 03.40 [2] 9.1.2.5) Where not required this should be NULL.
	 * 
	 * @return
	 */
	public byte getAddrNpi() {
		int pos = 16;

		int zero_counter = 0;

		for (; pos < this.buf.length; ++pos) {
			if (buf[pos] == 0) {
				zero_counter++;

				if (zero_counter == 3) {
					break;
				}
			}
		}

		return buf[pos + 3];
	}

	/**
	 * Address range for use in routing short messages and Delivery Receipts to
	 * an ESME. This variable length field may have leading spaces. Where not
	 * required this should be a single NULL byte.
	 * 
	 * @return
	 */
	public String getAddressRange() {
		int pos = 16;

		for (int zero_counter = 0; pos < this.buf.length; ++pos) {
			if (buf[pos] == 0) {
				zero_counter++;

				if (zero_counter == 3) {
					break;
				}
			}
		}

		pos = pos + 4;

		int start = pos;

		for (; pos < this.buf.length; ++pos) {
			if (buf[pos] == 0) {
				break;
			}
		}

		int len = pos - start;

		return new String(buf, start, len);
	}

	public int getLoginCommandId() {
		return this.loginCommandId;
	}

	public String toString() {
		StringBuffer outStr = new StringBuffer(300);
		outStr.append("SMPPLoginMessage:");
		outStr.append("PacketLength=" + super.buf.length);
		outStr.append(",CommandID=" + getCommandId());
		outStr.append(",SequenceId=" + getSequenceId());
		if (strBuf != null)
			outStr.append(strBuf.toString());
		return outStr.toString();
	}

}

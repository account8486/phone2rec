package com.huawei.insa2.comm;

import java.net.ProtocolException;

public class PException extends ProtocolException {

	private static final long serialVersionUID = 1L;

	public PException(String message) {
		super(message);
	}
}

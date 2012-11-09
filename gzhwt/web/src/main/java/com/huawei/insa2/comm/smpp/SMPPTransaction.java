package com.huawei.insa2.comm.smpp;

import com.huawei.insa2.comm.*;
import com.huawei.insa2.comm.smpp.message.SMPPMessage;
import com.huawei.insa2.util.Debug;

public class SMPPTransaction extends PLayer {

	public SMPPTransaction(PLayer connection) {
		super(connection);
		sequenceId = super.id;
	}

	public synchronized void onReceive(PMessage msg) {
		receive = (SMPPMessage) msg;
		sequenceId = receive.getSequenceId();
		if (SMPPConstant.debug)
			Debug.dump(receive.toString());
		notifyAll();
	}

	public void send(PMessage message) throws PException {
		SMPPMessage mes = (SMPPMessage) message;
		mes.setSequenceId(sequenceId);
		super.parent.send(message);
		if (SMPPConstant.debug)
			Debug.dump(mes.toString());
	}

	public SMPPMessage getResponse() {
		return receive;
	}

	public synchronized void waitResponse() {
		if (receive == null)
			try {
				wait(((SMPPConnection) super.parent).getTransactionTimeout());
			} catch (InterruptedException interruptedexception) {
			}
	}

	private SMPPMessage receive;

	private int sequenceId;
}

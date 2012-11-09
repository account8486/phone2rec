package com.huawei.insa2.comm;

public class PEventAdapter implements PEventListener {

	public PEventAdapter() {
	}

	public void handle(PEvent e) {
		switch (e.getType()) {
		case PEvent.CHILD_CREATED: // '\002'
			childCreated((PLayer) e.getData());
			break;

		case PEvent.CREATED: // '\001'
			created();
			break;

		case PEvent.DELETED: // '\004'
			deleted();
			break;

		case PEvent.MESSAGE_DISPATCH_FAIL: // '@'
			messageDispatchFail((PMessage) e.getData());
			break;

		case PEvent.MESSAGE_DISPATCH_SUCCESS: // ' '
			messageSendSuccess((PMessage) e.getData());
			break;

		case PEvent.MESSAGE_SEND_SUCCESS: // '\b'
			messageSendSuccess((PMessage) e.getData());
			break;

		case PEvent.MESSAGE_SEND_FAIL: // '\020'
			messageSendError((PMessage) e.getData());
			break;
		}
	}

	public void childCreated(PLayer player) {
	}

	public void messageSendError(PMessage pmessage) {
	}

	public void messageSendSuccess(PMessage pmessage) {
	}

	public void messageDispatchFail(PMessage pmessage) {
	}

	public void messageDispatchSuccess(PMessage pmessage) {
	}

	public void created() {
	}

	public void deleted() {
	}
}

package com.huawei.opencmpp.server;

public interface Service {
	public enum State {
		Not_Started, Running, Closed
	}

	public State getState();

	public void start();

	public void close();
}

package com.huawei.opencmpp.ismg;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.huawei.insa2.comm.PMessage;
import com.huawei.opencmpp.server.Service;

public class CMPPISMGService implements Service {
	private static Log logger = LogFactory.getLog(CMPPISMGService.class);

	protected CMPPISMGConfig config;

	protected ServerSocket cmpp_server;

	protected List<CMPPISMGServiceConnection> connList = new ArrayList<CMPPISMGServiceConnection>();

	protected ExecutorService executor;

	protected State state = State.Not_Started;

	protected Thread cmppServiceThread;

	protected ServerSocket smpp_server;

	public CMPPISMGService() {
		this(CMPPISMGConfig.DEFAULT);
	}

	// 5018

	public CMPPISMGService(CMPPISMGConfig config) {
		this.config = config;
	}

	public CMPPISMGConfig getConfig() {
		return this.config;
	}

	/**
	 * 启动，这是异步的
	 */
	public void start() {
		executor = Executors.newFixedThreadPool(10);

		try {
			cmpp_server = new ServerSocket(config.getPort());
		} catch (IOException e) {
			if (logger.isErrorEnabled()) {
				logger.error(e.getMessage(), e);
			}
			this.close();
			return;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("cmpp listen port: " + config.getPort());
		}

		try {
			smpp_server = new ServerSocket(5018);
		} catch (IOException e) {
			if (logger.isErrorEnabled()) {
				logger.error(e.getMessage(), e);
			}
			this.close();
			return;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("smpp listen port: " + smpp_server.getLocalPort());
		}

		state = State.Running;

		cmppServiceThread = new Thread(new Runnable() {
			public void run() {
				cmpp_serviceInternal();
			}
		});

		cmppServiceThread.setName("CMPP server accept:" + config.getPort());
		cmppServiceThread.start();
	}


	public void close() {
		state = State.Closed;

		List<CMPPISMGServiceConnection> waitForClosedList = new ArrayList<CMPPISMGServiceConnection>(
				this.connList);

		for (CMPPISMGServiceConnection conn : waitForClosedList) {
			conn.close();
		}

		if (this.cmppServiceThread != null) {
			this.cmppServiceThread.interrupt();
		}

		try {
			if (cmpp_server != null) {
				this.cmpp_server.close();
			}
		} catch (IOException e) {
			if (logger.isErrorEnabled()) {
				logger.error(e.getMessage(), e);
			}
		}

		try {
			if (smpp_server != null) {
				this.smpp_server.close();
			}
		} catch (IOException e) {
			if (logger.isErrorEnabled()) {
				logger.error(e.getMessage(), e);
			}
		}
	}

	public State getState() {
		return this.state;
	}

	protected void cmpp_serviceInternal() {
		for (;;) {
			try {
				Socket socket = cmpp_server.accept();

				if (state != State.Running) {
					break;
				}

				if (socket == null) {
					continue;
				}

				CMPPISMGServiceConnection conn = new CMPPISMGServiceConnection(
						executor, this, socket);

				connList.add(conn);
			} catch (IOException e) {
				if (logger.isErrorEnabled()) {
					logger.error(e.getMessage(), e);
				}
			}
		}
	}

}

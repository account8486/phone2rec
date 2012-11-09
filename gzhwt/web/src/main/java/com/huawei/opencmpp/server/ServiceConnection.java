package com.huawei.opencmpp.server;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.huawei.insa2.comm.PException;
import com.huawei.insa2.comm.PMessage;
import com.huawei.insa2.comm.PReader;
import com.huawei.insa2.comm.PWriter;
import com.huawei.insa2.util.WatchThread;

/**
 * �����l��
 * 
 * @author ����
 * 
 */
public abstract class ServiceConnection {

	public enum State {
		Not_Init, Running, Closed
	}

	private static Log logger = LogFactory.getLog(ServiceConnection.class);

	protected final Socket socket;

	protected String name;

	protected PReader in;

	protected PWriter out;

	protected State state = State.Not_Init;

	protected static DateFormat df = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss.SSS");

	protected WatchThread receiveThread;

	protected AtomicInteger window = new AtomicInteger(0);

	public ServiceConnection(Socket socket) {
		this.socket = socket;

		this.init();
	}

	public State getState() {
		return this.state;
	}

	protected void init() {
		if (name == null) {
			this.name = "ServiceConnection:" + this.socket.getPort();
		}

		try {
			this.in = this.getReader(this.socket.getInputStream());
			this.out = this.getWriter(this.socket.getOutputStream());
		} catch (IOException e) {
			if (logger.isErrorEnabled()) {
				logger.error(e.getMessage(), e);
			}
		}

		state = State.Running;

		class ReceiveThread extends WatchThread {
			public ReceiveThread() {
				super(name + "-receive");
			}

			public void task() {
				try {
					PMessage m = in.read();
					if (m != null) {
						receiveInternal(m);
					}
				} catch (EOFException ex) {
					close();
				} catch (SocketException ex) {

					if ("Connection reset".equals(ex.getMessage())) {
						if (logger.isDebugEnabled()) {
							logger.debug("socket:" + socket.getPort()
									+ " reset");
						}

						close();
						return;
					}

					if ("socket closed".equals(ex.getMessage())) {
						if (logger.isDebugEnabled()) {
							logger.debug("socket:" + socket.getPort()
									+ " closed");
						}
						close();
						return;
					}

					if (logger.isErrorEnabled()) {
						logger.error(ex.getMessage(), ex);
					}

					close();

				} catch (Exception ex) {
					if (logger.isErrorEnabled()) {
						logger.error(ex.getMessage(), ex);
					}

					close();
				}
			}
		}

		receiveThread = new ReceiveThread();
		receiveThread.start();
	}

	protected void receiveInternal(PMessage message) {
		if (state != State.Running) {
			return;
		}

		window.incrementAndGet();
		try {
			this.onReceive(message);
		} finally {
			window.decrementAndGet();
		}
	}

	public void onReceive(PMessage message) {

	}

	public void send(PMessage pmessage) throws PException {
		try {
			this.out.write(pmessage);
		} catch (IOException ex) {
			if (logger.isErrorEnabled()) {
				logger.error("write message error, " + ex.getMessage() + ". "
						+ pmessage.toString(), ex);
			}

			throw new PException(ex.getMessage());
		}
	}

	protected abstract PWriter getWriter(OutputStream outputstream);

	protected abstract PReader getReader(InputStream inputstream);

	public int getWindow() {
		return this.window.get();
	}

	public void close() {
		this.state = State.Closed;

		if (logger.isDebugEnabled()) {
			logger.debug("close");
		}

		try {
			if (this.socket != null) {
				this.socket.close();
			}

			this.receiveThread.kill();
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error(e.getMessage(), e);
			}
		}
	}

}

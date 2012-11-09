package com.huawei.opencmpp.server;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.huawei.insa2.comm.PException;
import com.huawei.insa2.comm.PMessage;
import com.huawei.insa2.util.WatchThread;

/**
 * 异步分发事件的服务端连接
 * 
 */
public abstract class AyncDispatchEventServiceConnection extends
		ServiceConnection {
	private static Log logger = LogFactory
			.getLog(AyncDispatchEventServiceConnection.class);

	protected ExecutorService executor;

	protected WatchThread writeThread;

	BlockingQueue<PMessage> waitForWriteMessageQ = new ArrayBlockingQueue<PMessage>(
			100);

	public AyncDispatchEventServiceConnection(ExecutorService executor,
			Socket socket) {
		super(socket);
		if (executor == null) {
			executor = Executors.newFixedThreadPool(10);
		}
		this.executor = executor;
	}

	public ExecutorService getExecutor() {
		return this.executor;
	}

	protected void init() {
		super.init();

		class WriteThread extends WatchThread {
			public WriteThread() {
				super(name + "-write");
			}

			public void task() {
				PMessage message = waitForWriteMessageQ.poll();
				if (message != null) {
					try {
						send_sync(message);
					} catch (IOException ex) {
						if (logger.isErrorEnabled()) {
							logger.error(ex.getMessage(), ex);
						}

						AyncDispatchEventServiceConnection.this.close();
					}
				}
			}
		}

		writeThread = new WriteThread();
		writeThread.start();
	}

	public void send(PMessage message) {
		this.waitForWriteMessageQ.add(message);
	}

	public void send_sync(PMessage message) throws PException {
		super.send(message);
		window.decrementAndGet();
	}

	/**
	 * 异步分发事件
	 */
	protected void receiveInternal(final PMessage message) {
		Runnable task = new Runnable() {
			public void run() {
				window.incrementAndGet();
				onReceive(message);
			}
		};
		this.executor.execute(task);
	}

	public void close() {
		if (logger.isDebugEnabled()) {
			logger.debug("connection:" + this.socket.getPort() + " closing...");
		}
		
		this.state = State.Closed;
		try {
			/*
			 * 等待10秒，等待把消息写完
			 */
			for (int i = 0; i < 100; ++i) {
				if (this.getWindow() == 0) {
					break;
				}

				Thread.sleep(100);
			}

			super.close();

			this.writeThread.kill();
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error(e.getMessage(), e);
			}
		}
	}
}

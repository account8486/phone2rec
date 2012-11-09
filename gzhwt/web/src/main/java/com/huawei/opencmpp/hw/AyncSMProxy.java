package com.huawei.opencmpp.hw;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.huawei.insa2.comm.PException;
import com.huawei.insa2.comm.PMessage;
import com.huawei.insa2.comm.cmpp.CMPPConnection;
import com.huawei.insa2.comm.cmpp.CMPPConstant;
import com.huawei.insa2.comm.cmpp.message.CMPPConnectRepMessage;
import com.huawei.insa2.comm.cmpp.message.CMPPDeliverMessage;
import com.huawei.insa2.comm.cmpp.message.CMPPDeliverRepMessage;
import com.huawei.insa2.comm.cmpp.message.CMPPMessage;
import com.huawei.insa2.comm.cmpp.message.CMPPSubmitMessage;
import com.huawei.insa2.comm.cmpp.message.CMPPSubmitRepMessage;
import com.huawei.insa2.comm.cmpp.message.CMPPTerminateRepMessage;
import com.huawei.insa2.util.Args;
import com.huawei.insa2.util.Cfg;
import com.huawei.opencmpp.util.Pair;

public class AyncSMProxy {
	private static Log logger = LogFactory.getLog(AyncSMProxy.class);

	static class TransactionInfo {

		public AyncCMPPTransaction transaction;

		public CMPPMessage message;

		public TransactionInfo(AyncCMPPTransaction txn, CMPPMessage msg) {
			transaction = txn;
			message = msg;
		}
	}

	public int windowSize = DEFAULT_MAX_WINDOW;

	public static final int DEFAULT_MAX_WINDOW = 16;

	private LinkedList<CMPPMessage> waitForSendMsgList = new LinkedList<CMPPMessage>();

	/**
	 * 窗口计数
	 */
	private Map<Integer, TransactionInfo> waitForResponseTxnMap = new HashMap<Integer, TransactionInfo>();

	private final Lock lock = new ReentrantLock();

	private final Condition notEmpty = lock.newCondition();

	private final Condition notFull = lock.newCondition();

	private final Condition connected = lock.newCondition();

	private CMPPConnection conn;

	private boolean connected_flag = false;

	private boolean closed_flag = false;

	private AyncSMProxyEventListener listener;

	public boolean loop_send_ctr_err_message = true;

	private int traffic_ctrl_err_count = 0;

	private BlockingQueue<Pair> receiveMessageQ = new ArrayBlockingQueue<Pair>(
			100);

	/**
	 * 发送线程
	 */
	private Thread senderThread;

	/**
	 * 事件分发线程
	 */
	private Thread eventDispatchThread;

	public AyncSMProxy() {
		this(getConfig().getArgs("CMPPConnect"));
	}

	public AyncSMProxy(Map args) {
		this(new Args(args));
	}

	public AyncSMProxy(Args args) {
		this(args, null);
	}

	public AyncSMProxy(AyncSMProxyEventListener listener) {
		this(getConfig().getArgs("CMPPConnect"), listener);
	}

	public AyncSMProxy(Args args, AyncSMProxyEventListener listener) {
		conn = new AyncCMPPConnection(this, args);
		conn.addEventListener(new AyncCMPPEventAdapter(this));
		this.listener = listener;
		conn.waitAvailable();
		if (!conn.available()) {
			throw new IllegalStateException(conn.getError());
		} else {
			senderThread = new Thread(new Runnable() {
				public void run() {
					loop_send();
				}
			});

			senderThread.setName("AyncSMProxy-Sender");
			senderThread.start();

			eventDispatchThread = new Thread(new Runnable() {

				public void run() {
					dipatchEvent();
				}

			});
			eventDispatchThread.setName("AyncSMProxy-EventDispatcher");
			eventDispatchThread.start();
			return;
		}
	}

	/**
	 * 分发事件
	 * 
	 */
	private void dipatchEvent() {
		for (;;) {
			try {
				Pair pair = (Pair) receiveMessageQ.take();
				CMPPMessage msg = (CMPPMessage) pair.getFirst();
				CMPPMessage res = (CMPPMessage) pair.getSecond();

				if (msg.getCommandId() == CMPPConstant.Submit_Command_Id) {
					CMPPSubmitMessage submit = (CMPPSubmitMessage) msg;
					CMPPSubmitRepMessage submit_res = (CMPPSubmitRepMessage) res;
					if (submit_res.getResult() == 0) {
						if (listener != null) {
							listener.onSubmitSuccess(submit, submit_res);
						}
					} else {
						if (listener != null) {
							listener.onSubmitFailed(submit, submit_res);
						}
					}
				}

				if (msg.getCommandId() == CMPPConstant.Deliver_Command_Id) {
					CMPPDeliverMessage deliver = (CMPPDeliverMessage) msg;
					if (this.listener != null) {
						this.listener.onReceive(deliver);
					}
				}

			} catch (InterruptedException _) {
				break;
			} catch (Exception ex) {
				if (AyncSMProxy.logger.isErrorEnabled())
					AyncSMProxy.logger.error(ex.getMessage(), ex);
			}
		}
	}

	/**
	 * 窗口大小
	 * 
	 * @return
	 */
	public int getWindowSize() {
		return windowSize;
	}

	/**
	 * 设置窗口大小
	 * 
	 * @param val
	 */
	public void setWindowSize(int val) {
		windowSize = val;
	}

	/**
	 * 把AyncCMPPTransaction添加到窗口计数
	 * 
	 * @param txn
	 * @param msg
	 */
	void addTransactionInfo(AyncCMPPTransaction txn, CMPPMessage msg) {
		lock.lock();
		try {
			if (waitForResponseTxnMap.size() >= windowSize)
				try {
					if (logger.isDebugEnabled())
						logger.debug("notFull.await()");
					notFull.await();
				} catch (InterruptedException _) {
				}
			waitForResponseTxnMap.put(Integer.valueOf(txn.getId()),
					new TransactionInfo(txn, msg));
		} finally {
			lock.unlock();
		}
	}

	/**
	 * 更新窗口计数
	 * 
	 * @param txn
	 * @return
	 */
	TransactionInfo removeTransactionInfo(AyncCMPPTransaction txn) {
		TransactionInfo txn_info;
		txn_info = null;
		lock.lock();
		try {
			if (waitForResponseTxnMap.size() >= windowSize) {
				txn_info = (TransactionInfo) waitForResponseTxnMap
						.remove(Integer.valueOf(txn.getId()));
				notFull.signalAll();
				if (logger.isDebugEnabled())
					logger.debug("notFull.signalAll()");
			} else {
				txn_info = (TransactionInfo) waitForResponseTxnMap
						.remove(Integer.valueOf(txn.getId()));
			}
		} finally {
			lock.unlock();
		}

		return txn_info;
	}

	/**
	 * 接受消息
	 * 
	 * @param msg
	 */
	public void onReceive(PMessage msg) {
		TransactionInfo txn_info = null;

		int childId = conn.getChildId(msg);
		Integer txnId = new Integer(childId);

		// 窗口大小控制
		lock.lock();
		try {
			if (waitForResponseTxnMap.size() >= windowSize) {
				txn_info = (TransactionInfo) waitForResponseTxnMap
						.remove(txnId);
				notFull.signalAll();
				if (logger.isDebugEnabled())
					logger.debug("notFull.signalAll()");
			} else {
				txn_info = (TransactionInfo) waitForResponseTxnMap
						.remove(txnId);
			}
		} finally {
			lock.unlock();
		}

		/**
		 * CMPP_SUBMIT_RESP消息处理
		 */
		if (msg instanceof CMPPSubmitRepMessage) {
			CMPPSubmitRepMessage res = (CMPPSubmitRepMessage) msg;
			CMPPSubmitMessage submit = (CMPPSubmitMessage) txn_info.message;
			if (res.getResult() == 0) {/* 正常处理 */
				if (logger.isDebugEnabled())
					logger.debug((new StringBuilder()).append(
							"RES_SUBMIT SUCCESS ").append(res.getSequenceId())
							.toString());
				try {
					receiveMessageQ.put(new Pair<CMPPMessage, CMPPMessage>(
							submit, res));
				} catch (InterruptedException _) {
				}
			} else if (res.getResult() == 8) { /* 流量控制错 */
				traffic_ctrl_err_count++;
				if (loop_send_ctr_err_message) {
					if (logger.isDebugEnabled()) {
						logger.debug((new StringBuilder()).append(
								"RES_SUBMIT 流量控制错 SEQ_ID ").append(
								res.getSequenceId()).append(", 共重发次数 ").append(
								traffic_ctrl_err_count).toString());
					}
					/* 重发处理 */
					send_by_async(txn_info.message);
				} else {
					try {
						receiveMessageQ.put(new Pair<CMPPMessage, CMPPMessage>(
								submit, res));
					} catch (InterruptedException _) {
					}
				}
			} else {
				if (logger.isDebugEnabled())
					logger.debug((new StringBuilder()).append(
							"RES_SUBMIT FAILED ").append(res.getSequenceId())
							.toString());
				try {
					receiveMessageQ.put(new Pair<CMPPMessage, CMPPMessage>(
							submit, res));
				} catch (InterruptedException _) {
				}
			}
			txn_info.transaction.close();
			return;
		}

		CMPPMessage resp_msg = (CMPPMessage) msg;

		if (resp_msg.getCommandId() == CMPPConstant.Connect_Rep_Command_Id) {
			CMPPConnectRepMessage res_msg = (CMPPConnectRepMessage) resp_msg;
			if (res_msg.getStatus() == 0) {

				lock.lock();
				try {
					connected_flag = true;
					connected.signalAll();
					if (logger.isInfoEnabled())
						logger.info("connected.");
				} finally {
					lock.unlock();
				}
			} else {
				if (logger.isWarnEnabled())
					logger.warn("KDSMProxy connect failed.");
			}

			return;
		}

		if (resp_msg.getCommandId() == CMPPConstant.Deliver_Command_Id) {
			CMPPDeliverMessage del_msg = (CMPPDeliverMessage) resp_msg;
			if (logger.isDebugEnabled()) {
				logger.debug("RECEIVED DELIVER_MSG : " + del_msg.toString());
			}

			try {
				receiveMessageQ.put(new Pair<CMPPMessage, CMPPMessage>(del_msg,
						null));
			} catch (InterruptedException _) {
			}
			return;
		}

		if (resp_msg.getCommandId() == CMPPConstant.Active_Test_Rep_Command_Id) {
			txn_info.transaction.close();
			return;
		}

		if (resp_msg.getCommandId() == CMPPConstant.Terminate_Rep_Command_Id) {
			CMPPTerminateRepMessage term_resp = (CMPPTerminateRepMessage) resp_msg;
			if (logger.isDebugEnabled()) {
				logger.debug("TERMINATE_RESP : " + term_resp.getSequenceId());
			}
			return;
		}

		if (txn_info == null) {
			if (logger.isWarnEnabled())
				logger.warn("not found transaction : "
						+ msg.getClass().getName() + ", "
						+ resp_msg.getSequenceId());
			return;
		} else {
			txn_info.transaction.close();
		}

	}

	/**
	 * 内部方法，循环发送
	 * 
	 */
	private void loop_send() {
		for (;;) {
			CMPPMessage message;
			AyncCMPPTransaction txn;

			message = null;
			txn = null;
			lock.lock();
			try {
				/*
				 * 非空等待
				 */
				if (waitForSendMsgList.size() == 0) {
					if (logger.isDebugEnabled()) {
						logger.debug("notEmpty.await()");
					}
					try {
						notEmpty.await();
					} catch (InterruptedException _) {

					}
				}

				/*
				 * 最大窗口控制
				 */
				if (waitForResponseTxnMap.size() >= windowSize) {
					try {
						if (logger.isDebugEnabled())
							logger.debug("notFull.await()");
						notFull.await();
					} catch (InterruptedException _) {
					}
				}

				/*
				 * 窗口计数
				 */
				message = (CMPPMessage) waitForSendMsgList.poll();
				if (message == null)
					throw new IllegalStateException();
				txn = (AyncCMPPTransaction) conn.createChild();
				waitForResponseTxnMap.put(Integer.valueOf(txn.getId()),
						new TransactionInfo(txn, message));

				if (logger.isDebugEnabled()) {
					logger.debug("BEGIN TRANS " + txn.getId());
				}

			} finally {
				lock.unlock();
			}

			/*
			 * 发送消息
			 */
			try {
				txn.send(message);
			} catch (PException e) {
				if (logger.isErrorEnabled()) {
					logger.error(e.getMessage(), e);
				}
			}
		}
	}

	/**
	 * 提交发送任务
	 * 
	 * @param message
	 */
	public void send_by_async(CMPPMessage message) {
		if (closed_flag) {
			return;
		}

		if (message == null)
			return;
		lock.lock();
		try {
			if (!connected_flag)
				try {
					if (logger.isDebugEnabled())
						logger.debug("connected.await()");
					connected.await();
				} catch (InterruptedException _) {
				}
			if (waitForSendMsgList.size() == 0) {
				waitForSendMsgList.add(message);
				notEmpty.signalAll();
				if (logger.isDebugEnabled())
					logger.debug("notEmpty.signalAll()");
			} else {
				waitForSendMsgList.add(message);
			}
		} finally {
			lock.unlock();
		}
	}

	/**
	 * 批量提交发送任务
	 * 
	 * @param messages
	 */
	public void send_by_async(CMPPMessage messages[]) {
		if (closed_flag) {
			return;
		}

		if (messages == null || messages.length == 0)
			return;
		lock.lock();
		try {
			if (!connected_flag)
				try {
					connected.await();
				} catch (InterruptedException _) {
				}
			if (waitForSendMsgList.size() == 0) {
				for (int i = 0; i < messages.length; i++)
					waitForSendMsgList.add(messages[i]);

				notEmpty.signalAll();
			} else {
				for (int i = 0; i < messages.length; i++)
					waitForSendMsgList.add(messages[i]);

			}
		} finally {
			lock.unlock();
		}
	}

	public void onTerminate() {
	}

	public CMPPMessage onDeliver(CMPPDeliverMessage msg) {
		if (this.closed_flag) {
			return null;
		}
		
		return new CMPPDeliverRepMessage(msg.getMsgId(), 0);
	}

	public LinkedList getWaitForSendMessageList() {
		return waitForSendMsgList;
	}

	public void close() {
		lock.lock();
		try {
			this.closed_flag = true;
		} finally {
			lock.unlock();
		}

		if (logger.isDebugEnabled()) {
			logger.debug("begin close, send waiting message ...");
		}
		
		/*
		 * 发送完剩余的消息 
		 */
		for (int i = 0; i < 100; ++i) {
			if (waitForSendMsgList.size() == 0) {
				break;
			}

			try {
				Thread.sleep(100);
			} catch (InterruptedException _) {
			}
		}
		
		/*
		 * 等待回应 
		 */
		for (int i = 0; i < 100; ++i) {
			if (this.waitForResponseTxnMap.size() == 0) {
				break;
			}

			try {
				Thread.sleep(100);
			} catch (InterruptedException _) {
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("closing...");
		}

		lock.lock();
		try {
			conn.close();
		} finally {
			lock.unlock();
		}

		if (logger.isDebugEnabled()) {
			logger.debug("closed");
		}
	}

	public CMPPConnection getConn() {
		return conn;
	}

	public String getConnState() {
		return conn.getError();
	}

	public static Cfg getConfig() {
		if (config == null)
			try {
				config = new Cfg("app.xml");
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		return config;
	}

	static Cfg config;

}

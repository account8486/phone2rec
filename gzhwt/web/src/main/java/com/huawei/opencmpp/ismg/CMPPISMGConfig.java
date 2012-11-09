package com.huawei.opencmpp.ismg;

import java.util.ArrayList;
import java.util.List;

public class CMPPISMGConfig {
	protected int port;

	private int ismg_Id;

	private int misc_Id;

	protected int timeout;

	protected final List<Item> itemList = new ArrayList<Item>();

	public CMPPISMGConfig(int port, int ismg_Id, int misc_Id, int timeout) {
		this.port = port;
		this.ismg_Id = ismg_Id;
		this.misc_Id = misc_Id;
		this.timeout = timeout;
	}

	public CMPPISMGConfig(int port, int ismg_Id, int misc_Id, int timeout,
			Item item) {
		this.port = port;
		this.ismg_Id = ismg_Id;
		this.misc_Id = misc_Id;
		this.timeout = timeout;
		this.itemList.add(item);
	}

	public final static CMPPISMGConfig DEFAULT = new CMPPISMGConfig(7890,
			100001, 1234, 10, new Item("99999", 1008, "pass", "127.0.0.1"));

	public int getISMGId() {
		return ismg_Id;
	}

	public List<Item> getItems() {
		return this.itemList;
	}

	/**
	 * 网关编号，Msg_Id中的网关编号部分，6位
	 * 
	 * @param ismg_Id
	 */
	public void setISMGId(int ismg_Id) {
		if (misc_Id >= 10000000 || misc_Id < 0) {
			throw new IllegalArgumentException();
		}

		this.ismg_Id = ismg_Id;
	}

	/**
	 * DSMP生成的用于组成LinkID，四位
	 * 
	 * @return
	 */
	public int getMISCId() {
		return misc_Id;
	}

	public void setMISCId(int misc_Id) {
		if (misc_Id >= 10000 || misc_Id < 0) {
			throw new IllegalArgumentException();
		}

		this.misc_Id = misc_Id;
	}

	/**
	 * 绑定端口号
	 * 
	 * @return
	 */
	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * Socket超时时间 （秒）
	 * 
	 * @return
	 */
	public long getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public static class Item {
		public Item() {

		}

		public Item(String icpid, int spid, String shared_scret,
				String ip_address) {
			this.icpid = icpid;
			this.spid = spid;
			this.shared_scret = shared_scret;
			this.ip_address = ip_address;
		}

		/**
		 * 企业代码
		 */
		public String icpid;

		/**
		 * 特服号
		 */
		public int spid;

		/**
		 * 登陆口令
		 */
		public String shared_scret;

		/**
		 * IP地址
		 */
		public String ip_address;

	}
}

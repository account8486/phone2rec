package dao;

import common.CommonUtil;
import common.SocketSender;

public class TestSMS {

	private String msg; //消息内容
	private String mid; //手机号码
	private String ext; //业务扩展号
	private int smg;  //运营商编号
	
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

	public int getSmg() {
		return smg;
	}

	public void setSmg(int smg) {
		this.smg = smg;
	}

	public void sendSMS() {
		// 用户ID
		String userID = CommonUtil.getProperty("SMS_USERID");
		// 用户密码
		String password = CommonUtil.getProperty("SMS_PASSWORD");
		// 二级平台IP
		String ipAddress = CommonUtil.getProperty("SMS_IPADDRESS");
		// 二级平台分配给用户的端口
		int port = Integer.valueOf(CommonUtil.getProperty("SMS_PORT"));
		// 登录超时时间
		int timeOut = Integer.valueOf(CommonUtil.getProperty("SMS_TIMEOUT"));
		
		SocketSender sSender = new SocketSender();
		if (sSender.login(userID, password, ipAddress, port, timeOut) == 0) {
			long seq = sSender.send(this.msg, this.mid, this.ext, this.smg);
			if (seq == 0) {
				System.out.println("发送成功！");
			} else if (seq == -1) {
				System.out.println("网络问题，发送失败！");
			} else if (seq == -2) {
				System.out.println("短信运营商指定有误，发送失败！");
			} else {
				System.out.println("其他错误，发送失败！");
			}
		}
		sSender.logout();
	}
}

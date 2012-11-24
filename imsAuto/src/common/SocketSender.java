package common;

import java.io.InputStream;
import java.net.URL;

public class SocketSender {

	private String userID;
	private String password;
	private String ipAddress;
	private int port;
	private int timeOut;

	public int login(String userID, String password, String ipAddress, int port, int timeOut) {

		this.userID = userID;
		this.password = password;
		this.ipAddress = ipAddress;
		this.port = port;
		this.timeOut = timeOut;

		return 0;
	}

	public long send(String msg, String mid, String ext, int smg) {

		if (mid == null || mid == "") {
			return 0; 
		}

		if (msg == null || msg == "") {
			return 0; 
		}
		
		msg = msg.replaceAll("&", "");
		try {
			msg = java.net.URLEncoder.encode(msg);
			
			//String deco = java.net.URLDecoder.decode(msg);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		
		StringBuffer sql = new StringBuffer();
		sql.append(ipAddress + "?");
		sql.append("revno=" + mid + "&");
		sql.append("smsinfo=" + msg + "&");
		sql.append("loginname=" + userID + "&");
		sql.append("password=" + password + "");

		String s = sql.toString();

		URL u;
		try {
			u = new URL(s);

			InputStream in = u.openStream();
			byte[] buff = new byte[1024];
			in.read(buff);
			//System.out.println(new String(buff));
		} catch (Exception e) {
			 System.out.println("send sms error!23");
			//e.printStackTrace();
		}

		return 0;

	}

	public void logout() {
		// TODO Auto-generated method stub

	}

	public static void main(String[] args) {

		String sss = "尊敬的 XXX 您好！客户号5100104350本期已产生，共计用电1001千瓦时，共计564元。";
		SocketSender sender = new SocketSender();
		String ipS = "10.138.4.10";
		String mNum = "13956085230";
		if (sender.login("imsgl", "imsgl", "http://10.138.1.48:81/adm/ext/sendsms.jsp", 3, 3) == 0) {
			long seq = sender.send(sss, mNum, "", 3);

		}
	}
	
}

package com.wondertek.meeting.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * 
 * @author 金祝华
 */
public class IPRequest {

	// 获取客户端IP地址，支持代理服务器
	public static String getIpAddress(HttpServletRequest request) {

		String ip = request.getHeader("x-forwarded-for");

		String localIP = "127.0.0.1";

		if ((ip == null) || (ip.length() == 0) || (ip.equalsIgnoreCase(localIP)) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}

		if ((ip == null) || (ip.length() == 0) || (ip.equalsIgnoreCase(localIP)) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}

		if ((ip == null) || (ip.length() == 0) || (ip.equalsIgnoreCase(localIP)) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}

		return ip;
	}

	/**
	 * 获取本地IP地址
	 * 
	 * @return
	 */
	public static String getLocalAdress() {
		InetAddress addr = null;
		try {
			addr = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return "";
		}

		if (addr != null) {
			byte[] ipAddr = addr.getAddress();
			String ipAddrStr = "";
			if (ipAddr != null) {
				for (int i = 0; i < ipAddr.length; i++) {
					if (i > 0) {
						ipAddrStr += ".";
					}
					ipAddrStr += ipAddr[i] & 0xFF;
				}

				return ipAddrStr;
			}
		}

		return "";
	}
}

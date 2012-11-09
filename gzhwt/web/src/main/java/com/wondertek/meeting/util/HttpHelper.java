/**
 * HTTP协议请求执行工具类
 * Author: 张国敬
 * Copyrights: 版权归上海网达软件有限公司安徽分公司所有
 * Create at: 2012-6-5
 */
package com.wondertek.meeting.util;

import java.io.InputStream;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.cxf.common.util.StringUtils;

public class HttpHelper {
	/**
	 * 执行一个Get方法，并返回服务器的响应信息
	 */
	public static String doGet(String url, String charset) {
		charset = StringUtils.isEmpty(charset) ? "utf-8" : charset;
		String response = "";
		HttpClient client = new HttpClient();
		GetMethod method = new GetMethod(url);
		try {
			client.executeMethod(method);
			if(method.getStatusCode() != HttpStatus.SC_OK) {
				return "";
			}
			
			StringBuilder sb = new StringBuilder();
			InputStream is = method.getResponseBodyAsStream();
			byte[] buf = new byte[1024];
			int len = 0;
			while((len = is.read(buf)) > 0) {
				sb.append(new String(buf, 0, len, charset));
			}
			is.close();
			response = sb.toString();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if(method != null) {
				method.releaseConnection();
			}
		}
		return response;
	}
}

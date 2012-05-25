package com.wondertek.meeting.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * 
 *
 * @author 金祝华
 */
public final class MD5Encrypt {

//	private static final String MD5_PREFIX = "o3L9*d!q57@e-w]fd8)da";

	private static final ThreadLocal<MD5Encrypt> local = new ThreadLocal<MD5Encrypt>();

	private MD5Encrypt() {
		super();
	}

	public static MD5Encrypt getEncrypt() {
		MD5Encrypt encrypt = local.get();
		if (encrypt == null) {
			encrypt = new MD5Encrypt();
			local.set(encrypt);
		}
		return encrypt;
	}

	public static String encode(String s) {
		if (s == null) {
			return null;
		}
//		return DigestUtils.md5Hex(MD5_PREFIX + s);
		return DigestUtils.md5Hex(s);
	}
	public static void main(String arg[]){
		
		System.out.println(MD5Encrypt.getEncrypt().encode("123456"));
	}
}

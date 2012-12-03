package com.util;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;


/**
 * 
 *
 * @author 金祝华
 */
public final class MD5Encrypt {

	// private static final String MD5_PREFIX = "o3L9*d!q57@e-w]fd8)da";

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

		Md5PasswordEncoder md5 = new Md5PasswordEncoder();

		return md5.encodePassword(s, null);

	}
	
	

	public static void main(String arg[]) {

		System.out.println(MD5Encrypt.getEncrypt().encode("abcd1234"));
	}
	
	
	
	
}



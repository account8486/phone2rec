package com.wondertek.meeting.util;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

/**
 * 根据密码的加密、解密工具类
 */
public class Encrypt {
	// 默认密码
	private static final String defaultPassword = "ah.meeting@wondertek.com.cn";
	// 默认的盐
	private static byte[] salt = new byte[] { 24, -121, -109, 109, 53, 58, 106, -96 };

	/** */
	/**
	 * 支持以下任意一种算法
	 * 
	 * <pre>
	 * 
	 * PBEWithMD5AndDES      
	 * PBEWithMD5AndTripleDES      
	 * PBEWithSHA1AndDESede     
	 * PBEWithSHA1AndRC2_40
	 * </pre>
	 */
	private static final String ALGORITHM = "PBEWITHMD5andDES";

	/**
	 * 将byte数组转换为表示16进制值的字符串， 如：byte[]{8,18}转换为：0813， 和public static byte[]
	 * hexStr2ByteArr(String strIn) 互为可逆的转换过程
	 * 
	 * @param arrB
	 *            需要转换的byte数组
	 * @return 转换后的字符串
	 * @throws Exception
	 *             本方法不处理任何异常，所有异常全部抛出
	 */
	public static String byteArr2HexStr(byte[] arrB) {
		int iLen = arrB.length;
		// 每个byte用两个字符才能表示，所以字符串的长度是数组长度的两倍
		StringBuffer sb = new StringBuffer(iLen * 2);
		for (int i = 0; i < iLen; i++) {
			int intTmp = arrB[i];
			// 把负数转换为正数
			while (intTmp < 0) {
				intTmp = intTmp + 256;
			}
			// 小于0F的数需要在前面补0
			if (intTmp < 16) {
				sb.append("0");
			}
			sb.append(Integer.toString(intTmp, 16));
		}
		return sb.toString();
	}

	/**
	 * 将表示16进制值的字符串转换为byte数组， 和public static String byteArr2HexStr(byte[] arrB)
	 * 互为可逆的转换过程
	 * 
	 * @param strIn
	 *            需要转换的字符串
	 * @return 转换后的byte数组
	 * @throws Exception
	 *             本方法不处理任何异常，所有异常全部抛出
	 * @author <a href="mailto:leo841001@163.com">LiGuoQing</a>
	 */
	public static byte[] hexStr2ByteArr(String strIn) {
		byte[] arrB = strIn.getBytes();
		int iLen = arrB.length;

		// 两个字符表示一个字节，所以字节数组长度是字符串长度除以2
		byte[] arrOut = new byte[iLen / 2];
		for (int i = 0; i < iLen; i = i + 2) {
			String strTmp = new String(arrB, i, 2);
			arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
		}
		return arrOut;
	}

	/** */
	/**
	 * 转换密钥<br>
	 * 
	 * @param password
	 * @return
	 * @throws Exception
	 */
	private static Key toKey(String password) throws Exception {
		password = password == null ? defaultPassword : password;

		PBEKeySpec keySpec = new PBEKeySpec(password.toCharArray());
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
		SecretKey secretKey = keyFactory.generateSecret(keySpec);
		return secretKey;
	}

	/** */
	/**
	 * 加密, 加密的数据和返回后的数据都是byte数组
	 * 
	 * @param data
	 *            数据
	 * @param password
	 *            密码
	 * @return
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] data, String password) {
		if (data == null) {
			return null;
		}
		try {
			Key key = toKey(password);
			PBEParameterSpec paramSpec = new PBEParameterSpec(salt, 100);
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
			return cipher.doFinal(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * 加密，要加密的数据及返回的数据都是String
	 * 
	 * @param data
	 * @param password
	 * @return
	 */
	public static String encrypt(String data, String password) {
		if (data == null) {
			return "";
		}
		byte[] tmp = encrypt(data.getBytes(), password);
		return byteArr2HexStr(tmp);
	}

	/** */
	/**
	 * 解密,要解密的数据和返回后的数据都是byte数组
	 * 
	 * @param data
	 *            数据
	 * @param password
	 *            密码
	 * @return
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] data, String password) {
		if (data == null) {
			return null;
		}
		try {
			Key key = toKey(password);

			PBEParameterSpec paramSpec = new PBEParameterSpec(salt, 100);
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, key, paramSpec);
			return cipher.doFinal(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 解密，要解密的数据及返回的数据都是String
	 * 
	 * @param data
	 * @param password
	 * @return
	 */
	public static String decrypt(String data, String password) {
		if (data == null) {
			return "";
		}
		byte[] tmp = decrypt(hexStr2ByteArr(data), password);
		return new String(tmp);
	}

	/**
	 * 测试代码
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		String str2 = Encrypt.encrypt("111111", null);
		System.out.println("加密后：" + str2);
		System.out.println("解密后：" + Encrypt.decrypt(str2, null));
	}
}
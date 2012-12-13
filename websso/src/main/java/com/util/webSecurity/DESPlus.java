package com.util.webSecurity;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class DESPlus
{
	private final static String strDefaultKey = "ZOqr5Udmy0MtYbhZCpA1xneu1xdn4UY12hGaxTyZRqo";
	private final static String DES = "DES";

	private static byte[] encrypt(byte[] src, byte[] key) throws Exception
	{
		SecureRandom sr = new SecureRandom();
		DESKeySpec dks = new DESKeySpec(key);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
		SecretKey securekey = keyFactory.generateSecret(dks);
		Cipher cipher = Cipher.getInstance(DES);
		cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
		return cipher.doFinal(src);
	}

	private static byte[] decrypt(byte[] src, byte[] key) throws Exception
	{
		SecureRandom sr = new SecureRandom();
		DESKeySpec dks = new DESKeySpec(key);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
		SecretKey securekey = keyFactory.generateSecret(dks);
		Cipher cipher = Cipher.getInstance(DES);
		cipher.init(Cipher.DECRYPT_MODE, securekey, sr);
		return cipher.doFinal(src);
	}

	public final static String decryptString(String data)
	{
		if (data != null)
			try
			{
				return new String(decrypt(hex2byte(data.getBytes()),
						strDefaultKey.getBytes()));
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		return null;
	}

	public final static String encryptString(String data)
	{
		if (data != null)
			try
			{
				return byte2hex(encrypt(data.getBytes(), strDefaultKey.getBytes()));
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		return null;
	}

	private static String byte2hex(byte[] b)
	{
		String hs = "";
		String stmp = "";
		for (int n = 0; b != null && n < b.length; n++)
		{
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
		}
		return hs.toUpperCase();
	}

	private static byte[] hex2byte(byte[] b)
	{
		if ((b.length % 2) != 0)
			throw new IllegalArgumentException("长度不是偶数");
		byte[] b2 = new byte[b.length / 2];
		for (int n = 0; n < b.length; n += 2)
		{
			String item = new String(b, n, 2);
			b2[n / 2] = (byte) Integer.parseInt(item, 16);
		}
		return b2;
	}
}

package com.util.webSecurity;

import java.io.UnsupportedEncodingException;
//import rtx.Comm;
public class UserSecurity
{
	/**
	 * 加密
	 * @param s
	 * @return
	 */
	public static String Encrypt(String s)
	{
		if(s == null)
			return null;
		
		s = DESPlus.encryptString(s);
		
		return s;
	}
	
	/**
	 * 解密
	 * @param s
	 * @return
	 */
	public static String Decrypt(String s)
	{
		if(s == null)
			return null;
		
		s = DESPlus.decryptString(s);
		
		return s;
	}

	
	public static void main(String[] agrs) throws UnsupportedEncodingException{
		//System.out.println(Encrypt("http://oa.ah.sgcc.com.cn/mail/huyq.nsf/($All)/83E274269A25E68048257599002E8555?Opendocument&box=($inbox)&83E274269A25E68048257599002E8555&FromPath=/index.nsf?OpenDatabase①ocx=ocx"));
		/*String  targetNew="http://portal.sgcc.com.cn/wsxw/277936.shtml";
		String  urlnew=targetNew.substring(0, 24);*/
		System.out.println("Urlnew:"+Encrypt("abcd1234"));
		//System.out.println("Urlnew:"+Decrypt("A835C3A3592B4E6BCE0D22288D66A53D"));
		//System.out.println(Comm.BASE64Encoder("ahRTXPortalSso"));
		//System.out.println(Decrypt("A1EF379DFC291547CE0D22288D66A53D"));
		//System.out.println(Decrypt(Encrypt("RTXPortalSso")));
		
		
	}
}

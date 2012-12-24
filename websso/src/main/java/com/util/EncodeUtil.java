package com.util;
/**
 * 加密专用包
 * @author 
 *
 */
public class EncodeUtil {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//System.out.println(md5Encode("111111"));
		//System.out.println(base64Encode("111111"));
		//System.out.println(md5Encode("abcd2234",LOWER_CASE_FORMATTER));
		
		System.out.println(base64Encode("abcd2234",null));
		
		
		
	}
	
	@SuppressWarnings("unused")
	public static String UPPER_CASE_FORMATTER="upper";
	public static String LOWER_CASE_FORMATTER="lower";
	
	/**
	 * MD5加密
	 */
	public static String md5Encode(String strSource, String formatter) {
		if ("upper".equals(formatter)) {
			return MD5Encrypt.encode(strSource).toUpperCase();
		} else if ("lower".equals(formatter)) {
			return MD5Encrypt.encode(strSource).toLowerCase();
		} else {
			return MD5Encrypt.encode(strSource);
		}

	}
	
	/**
	 * base64加密
	 * @param strSource
	 * @return
	 */
	public static String base64Encode(String strSource, String formatter) {
		if ("upper".equals(formatter)) {
			return BASE64.BASE64Encoder(strSource, "UTF-8").toUpperCase();
		} else if ("lower".equals(formatter)) {
			return BASE64.BASE64Encoder(strSource, "UTF-8").toLowerCase();
		} else {
			return BASE64.BASE64Encoder(strSource, "UTF-8");
		}

	}
	
	

}

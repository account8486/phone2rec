package com.guo.util;

public class StringUtil {
	
	public static boolean isEmpty(Object obj){
		if(obj!=null&&!"".equals(String.valueOf(obj))){
			//·Ç¿Õ
			return false;
		}else{
			return true;
		}	
	}
	
	public static boolean isNotEmpty(Object obj){
		return !isEmpty(obj);
	}

}

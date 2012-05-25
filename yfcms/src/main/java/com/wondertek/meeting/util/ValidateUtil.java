package com.wondertek.meeting.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * java来进行验证数据的有效性
 * @author zhuhua
 *
 */
public class ValidateUtil {

	public static void main(String[] args) {
		ValidateUtil validate=new ValidateUtil();
		System.out.println(validate.isMobileNO("18256926937"));
		
	}

	public Logger log = LoggerFactory.getLogger(this.getClass());

	/**
	 * 是手机号码
	 * @deprecated
	 * @param mobiles
	 * @return
	 */
	public static boolean isMobileNO(String mobiles) {
		Pattern p = Pattern
				.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}

	/**
	 * 非手机号码
	 * @deprecated
	 * @param mobiles
	 * @return
	 */
	public static boolean isNotMobileNO(String mobiles) {
		return !isMobileNO(mobiles);
	}
	
	public static boolean isEmail(String email) {
		String str = "^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(email);
		return m.matches();
	}
	
	/**
	 * 如果不是邮箱地址 RETURN TRUE
	 * @param email
	 * @return
	 */
	public static boolean isNotEmail(String email) {
		return !isEmail(email);
	}
	
	
    /**
     * 判断是否为IE
     * @return
     */
	public static boolean isIE() {
		return ServletActionContext.getRequest().getHeader("USER-AGENT")
				.toLowerCase().indexOf("msie") > 0 ? true : false;
	}  
	
}

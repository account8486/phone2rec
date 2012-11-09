package com.wondertek.meeting.util;

import java.io.InputStream;
import java.text.CharacterIterator;
import java.text.SimpleDateFormat;
import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.security.core.codec.Base64;
import org.springframework.util.StringUtils;

import com.wondertek.meeting.client.view.Organizer;

/**
 * 
 */
public class StringUtil {

	public static final String EMPTY = "";

	/**
	 * 判断是否为合法的日期时间字符串
	 * 
	 * @param str_input
	 * @return boolean;符合为true,不符合为false
	 */
	public static boolean isDate(String str_input, String rDateFormat) {
		if (!isNull(str_input)) {
			SimpleDateFormat formatter = new SimpleDateFormat(rDateFormat);
			formatter.setLenient(false);
			try {
				formatter.format(formatter.parse(str_input));
			} catch (Exception e) {
				System.out.println("isDate error formate "+str_input+" for rDateFormat "+ rDateFormat);
				e.printStackTrace();
				return false;
			}
			return true;
		}
		return false;
	}

	public static boolean isNull(String str) {
		if (str == null)
			return true;
		else
			return false;
	}

	public static boolean isEmpty(String str) {
		if (str == null || str.equals(""))
			return true;
		else
			return false;
	}

	public static boolean isNotEmpty(String str) {
		if (str != null && !"".equals(str) && !"null".equals(str)) {
			return true;
		} else {
			return false;
		}
	}

	// 将NULL转换成空字符串
	public static String null2Str(Object value) {
		return value == null || "null".equals(value.toString()) ? "" : value.toString();
	}

	public static String null2Str(String value) {
		return value == null || "null".equals(value) ? "" : value.trim();
	}

	public static String nullToString(String value) {
		return value == null || "null".equals(value) ? "" : value.trim();
	}

	public static String nullToString(Object value) {
		return value == null ? "" : value.toString().trim();
	}

	public static Long nullToLong(Object value) {
		return value == null || "null".equals(value.toString()) ? 0L : stringToLong(value.toString());
	}

	public static Long stringToLong(String value) {
		Long l;

		value = nullToString(value);
		if ("".equals(value)) {
			l = 0L;
		} else {
			try {
				l = Long.valueOf(value);

			} catch (Exception e) {
				l = 0L;
			}
		}

		return l;
	}

	/**
	 * 判断字符串是否是整数
	 */
	public static boolean isInteger(String value) {
		try {
			Integer.parseInt(value);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	/**
	 * 判断字符串是否是long类型整数
	 */
	public static boolean isLong(String value) {
		try {
			Long.parseLong(value);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public static int parseInteger(String value) {
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException e) {
			return 0;
		}
	}

	/**
	 * 判断字符串是否是浮点数
	 */
	public static boolean isDouble(String value) {
		try {
			Double.parseDouble(value);
			if (value.contains("."))
				return true;
			return false;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	/**
	 * 判断字符串是否是数字
	 */
	public static boolean isNumber(String value) {
		return isInteger(value) || isDouble(value);
	}
	
	/**
	 * 不是数字
	 * @param value
	 * @return
	 */
	public static boolean isNotNumber(String value) {
		return !isNumber(value);
	}
	
	/**
	 * 不是数字
	 * @param value
	 * @return
	 */
	public static boolean isNotNumber(Object value) {
		return !isNumber(String.valueOf(value));
	}
	
	/** 判断是否为时间 yyyy-MM-dd * */
	public static boolean isDate(String value) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			sdf.parse(value);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	/**
	 * 
	 * 中文转换--文章换行的转换
	 * 
	 * @param str
	 * 
	 * @return
	 */

	public static String getText(String str) {
		if (str == null)
			return ("");
		if (str.equals(""))
			return ("");
		// 建立一个StringBuffer来处理输入数据
		StringBuffer buf = new StringBuffer(str.length() + 6);
		char ch = '\n';
		for (int i = 0; i < str.length(); i++) {
			ch = str.charAt(i);
			if (ch == '\r') {
				buf.append(" ");
			} else if (ch == '\n') {
				buf.append(" ");
			} else if (ch == '\t') {
				buf.append("    ");
			} else if (ch == ' ') {
				buf.append(" ");
			} else if (ch == '\'') {
				buf.append("\\'");
			} else {
				buf.append(ch);
			}
		}
		return buf.toString();
	}

	// 清除特殊字符
	public static String getescapeText(String str) {
		if (str == null)
			return ("");
		if (str.equals(""))
			return ("");
		// 建立一个StringBuffer来处理输入数据
		StringBuffer buf = new StringBuffer(str.length() + 6);
		char ch = '\n';
		for (int i = 0; i < str.length(); i++) {
			ch = str.charAt(i);
			if (ch == '\r') {
				buf.append("");
			} else if (ch == '\n') {
				buf.append("");
			} else if (ch == '\t') {
				buf.append("");
			} else if (ch == ' ') {
				buf.append("");
			} else if (ch == '\'') {
				buf.append("");
			} else {
				buf.append(ch);
			}
		}
		return buf.toString();
	}

	/**
	 * 
	 * 根据转义列表对字符串进行转义(escape)。
	 * 
	 * @param source
	 *            待转义的字符串
	 * 
	 * @param escapeCharMap
	 *            转义列表
	 * 
	 * @return 转义后的字符串
	 */

	public static String escapeCharacter(

	String source,

	HashMap escapeCharMap) {

		if (source == null || source.length() == 0) {

			return source;

		}

		if (escapeCharMap.size() == 0) {

			return source;

		}

		StringBuffer sb = new StringBuffer(source.length() + 100);

		StringCharacterIterator sci = new StringCharacterIterator(source);

		for (char c = sci.first();

		c != CharacterIterator.DONE;

		c = sci.next()) {

			String character = String.valueOf(c);

			if (escapeCharMap.containsKey(character)) {

				character = (String) escapeCharMap.get(character);

			}

			sb.append(character);

		}

		return sb.toString();

	}

	/**
	 * 
	 * 中文转换--文章换行的转换
	 * 
	 * @param str
	 * 
	 * @return
	 */

	public static String changeEnter(String str) {
		if (str == null)
			return ("");
		if (str.equals(""))
			return ("");
		// 建立一个StringBuffer来处理输入数据
		StringBuffer buf = new StringBuffer(str.length() + 6);
		char ch = '\n';
		for (int i = 0; i < str.length(); i++) {
			ch = str.charAt(i);
			if (ch == '\r') {
				buf.append("|");
			} else if (ch == '\n') {
				buf.append("|");
			} else {
				buf.append(ch);
			}
		}
		return buf.toString();
	}

	// 截掉url左边的一级目录名,如/wap/news/index.xml -> /news/index.xml
	public static String trimLeftNode(String str) {
		if (str == null)
			return "";
		
		if (str.startsWith("/")) {
			int ind = str.indexOf("/", 1);
			if (ind > 0)
				return str.substring(ind);
		}
		return str;
	}
	
	public static String trim(String str) {
		if (str == null)
			return "";

		return str.trim();
	}

	public static String generatedUrl(int pageType, List<String> sourceList, String nodestr, int maxint) {
		List<String> nodeList = new ArrayList<String>();
		Random rmd = new Random();
		String rstr = "";
		Set<String> cpSet = new HashSet<String>();
		Set<Integer> distNum = new HashSet<Integer>();
		Set<String> distCp = new HashSet<String>();
		for (int i = 0; i < sourceList.size(); i++) {
			String tmpstr = sourceList.get(i);
			if (getSpstr(tmpstr, 1).equals(nodestr)) {
				nodeList.add(tmpstr);
				cpSet.add(getSpstr(tmpstr, 3));
			}
		}
		if (nodeList.size() > maxint) {
			for (int i = 0; i < maxint;) {
				int tmpint = rmd.nextInt(nodeList.size());
				String tmpstr = nodeList.get(tmpint);
				if ((distCp.add(getSpstr(tmpstr, 3)) || distCp.size() >= cpSet.size()) && distNum.add(tmpint)) {
					rstr += "<a href='" + getSpstr(tmpstr, 4) + "'>" + getSpstr(tmpstr, 2) + "</a><br/>";
					i++;
				}
			}
		} else {
			for (int i = 0; i < nodeList.size(); i++) {
				String tmpstr = nodeList.get(i);
				rstr += "<a href='" + getSpstr(tmpstr, 4) + "'>" + getSpstr(tmpstr, 2) + "</a><br/>";
			}
		}
		return rstr;
	}

	public static String getSpstr(String spstr, int level) {
		String rstr = "";
		for (int i = 0; i < level; i++) {
			if (spstr.indexOf("|*") == -1) {
				rstr = spstr;
				return rstr;
			} else {
				rstr = spstr.substring(0, spstr.indexOf("|*"));
			}
			spstr = spstr.substring(spstr.indexOf("|*") + 2, spstr.length());
		}
		return rstr;
	}

	/**
	 * 用于页面输出前，将尖括号转化成html语言表达式&lt;和&gt;
	 * 
	 * @param line
	 * @return
	 */
	public static String htmlencode(String line) {

		if (line != null) {
//			 char ch1 = 13; //回车
//			 char ch2 = 32; //空格
//			 char ch3 = 34; //双引号
//			 String str1 = String.valueOf(ch1);
//			 String str2 = String.valueOf(ch2);
//			 String str3 = String.valueOf(ch3);
			 return line.replaceAll("<", "&lt;").replaceAll(">",
			 "&gt;").replaceAll("&", "&amp;");
//			return line.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
		}
		return null;

	}

	public static String toString(Object obj) {
		try {
			return obj.toString();
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * put string array into map with a key, this method will return the detail
	 * information of array This method can be used in servlet for log many
	 * parameter array, and you can not worry about NULL POINT EXCEPTION see
	 * main method
	 * 
	 * @param map
	 * @return
	 */
	public static String ArrayToString(Map<String, String[]> map) {
		String res = "";
		for (String key : map.keySet()) {
			String[] values = map.get(key);
			if (values != null) {
				res = res + key + "=[";
				for (String value : values) {
					res = res + value + ",";
				}
				if (res.length() - res.lastIndexOf(",") == 1) {
					res = res.substring(0, res.lastIndexOf(","));
				}
				res = res + "],";
			} else {
				res = res + key + "=NULL,";
			}

		}
		if (res.length() - res.lastIndexOf(",") == 1) {
			res = res.substring(0, res.lastIndexOf(","));
		}
		return res;
	}

	/**
	 * 对关键词过滤SQL特殊字符
	 * 
	 * @param name
	 * @return
	 */
	public static String parese(String name) {
		if (name == null || "".equals(name))
			return "";
		name = StringUtils.replace(name, ">", "");
		name = StringUtils.replace(name, "<", "");
		name = StringUtils.replace(name, "\"", ""); // '双引号
		name = StringUtils.replace(name, "'", "");// '单引号
		name = StringUtils.replace(name, " ", "");// '空格
		name = StringUtils.replace(name, "	", "");// 'tab键值
		name = StringUtils.replace(name, "\r", "");// '换行
		name = StringUtils.replace(name, "\n", "");// '回车
		name = StringUtils.replace(name, "?", "");// '回车
		return name;
	}

	// 版本规范是0~9999.0~9999.0~9999
	public static boolean checkVersion(String version) {
		if (version == null || "".equals(version) || version.split("\\.").length != 3) {
			return false;
		}
		try {
			String[] numbers = version.split("\\.");
			for (int i = 0; i < numbers.length; i++) {
				Long num = Long.parseLong(numbers[i]);
				if (num > 9999L || num < 0L)
					return false;
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	// Left/Right/Mid
	// -----------------------------------------------------------------------
	/**
	 * <p>
	 * Gets the leftmost <code>len</code> characters of a String.
	 * </p>
	 * 
	 * <p>
	 * If <code>len</code> characters are not available, or the String is
	 * <code>null</code>, the String will be returned without an exception. An
	 * exception is thrown if len is negative.
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.left(null, *)    = null
	 * StringUtils.left(*, -ve)     = ""
	 * StringUtils.left("", *)      = ""
	 * StringUtils.left("abc", 0)   = ""
	 * StringUtils.left("abc", 2)   = "ab"
	 * StringUtils.left("abc", 4)   = "abc"
	 * </pre>
	 * 
	 * @param str
	 *            the String to get the leftmost characters from, may be null
	 * @param len
	 *            the length of the required String, must be zero or positive
	 * @return the leftmost characters, <code>null</code> if null String input
	 */
	public static String left(String str, int len) {
		if (str == null) {
			return null;
		}
		if (len < 0) {
			return EMPTY;
		}
		if (str.length() <= len) {
			return str;
		} else {
			return str.substring(0, len);
		}
	}

	/**
	 * <p>
	 * Gets the rightmost <code>len</code> characters of a String.
	 * </p>
	 * 
	 * <p>
	 * If <code>len</code> characters are not available, or the String is
	 * <code>null</code>, the String will be returned without an an exception.
	 * An exception is thrown if len is negative.
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.right(null, *)    = null
	 * StringUtils.right(*, -ve)     = ""
	 * StringUtils.right("", *)      = ""
	 * StringUtils.right("abc", 0)   = ""
	 * StringUtils.right("abc", 2)   = "bc"
	 * StringUtils.right("abc", 4)   = "abc"
	 * </pre>
	 * 
	 * @param str
	 *            the String to get the rightmost characters from, may be null
	 * @param len
	 *            the length of the required String, must be zero or positive
	 * @return the rightmost characters, <code>null</code> if null String input
	 */
	public static String right(String str, int len) {
		if (str == null) {
			return null;
		}
		if (len < 0) {
			return EMPTY;
		}
		if (str.length() <= len) {
			return str;
		} else {
			return str.substring(str.length() - len);
		}
	}

	/**
	 * <p>
	 * Gets <code>len</code> characters from the middle of a String.
	 * </p>
	 * 
	 * <p>
	 * If <code>len</code> characters are not available, the remainder of the
	 * String will be returned without an exception. If the String is
	 * <code>null</code>, <code>null</code> will be returned. An exception is
	 * thrown if len is negative.
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.mid(null, *, *)    = null
	 * StringUtils.mid(*, *, -ve)     = ""
	 * StringUtils.mid("", 0, *)      = ""
	 * StringUtils.mid("abc", 0, 2)   = "ab"
	 * StringUtils.mid("abc", 0, 4)   = "abc"
	 * StringUtils.mid("abc", 2, 4)   = "c"
	 * StringUtils.mid("abc", 4, 2)   = ""
	 * StringUtils.mid("abc", -2, 2)  = "ab"
	 * </pre>
	 * 
	 * @param str
	 *            the String to get the characters from, may be null
	 * @param pos
	 *            the position to start from, negative treated as zero
	 * @param len
	 *            the length of the required String, must be zero or positive
	 * @return the middle characters, <code>null</code> if null String input
	 */
	public static String mid(String str, int pos, int len) {
		if (str == null) {
			return null;
		}
		if (len < 0 || pos > str.length()) {
			return EMPTY;
		}
		if (pos < 0) {
			pos = 0;
		}
		if (str.length() <= (pos + len)) {
			return str.substring(pos);
		} else {
			return str.substring(pos, pos + len);
		}
	}
	
	 /**
     * Check whether String is MobileNumber.
     * 
     * @param s
     *            any String
     * @return boolean return is phone number or not
     */
    public static boolean isMobile(String s) {
        if (s.matches("^1[0-9]{10}$")) {
            return true;
        }
        return false;
    }
    
    
	 /**
     * Check whether String is MobileNumber.
     * 
     * @param s
     *            any String
     * @return boolean return is phone number or not
     */
    public static boolean isNotMobile(String s) {
        return !isMobile(s);
    }
    
    /**
     * 替换特殊字符
     * 空格 回车 换行符 制表符
     * @param original
     * @return
     */
	public static  String replaceBlank(String original){
		//如果为空值NULL,直接返回 杜绝空指针
		if(original==null){
			return "";
		}
		
		//正则表达式
		Pattern p = Pattern.compile("\\s*|\t|\r|\n");
		Matcher m= p.matcher(original);
		String after = m.replaceAll("");
		return  after;
	}
	
	public static String convertTextAreaNewLine(String text) {
		Pattern p = Pattern.compile("\\n");
		Matcher m= p.matcher(text);
		return m.replaceAll("<br/>");
	}
	
    public static boolean checkPortalPwd(String s) {
        if (s.matches("^\\d{4,20}$")) {
            return true;
        }
        
        return false;
    }
    
    /**
     * 把字符串限定指定的长度，如果超出则使用...代替
     */
    public static String limit(String value, int count) {
    	if(value == null) {
    		return "";
    	}
    	
    	String str = value;
    	if(value.length() > count) {
    		str = value.substring(0, count) + "...";
    	}
    	return str;
    }
    
    /**
     * Base64编码
     */
    public static String base64Encode(String str) {
    	if(str == null) {
    		return null;
    	}
    	
    	String value = null;
    	try {
    		byte[] buf = Base64.encode(str.getBytes("utf-8"));
    		value = new String(buf, "utf-8");
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    	return value;
    }
    
    /**
     * Base64编码
     */
    public static String base64Decode(String str) {
    	if(str == null) {
    		return null;
    	}
    	
    	String value = null;
    	try {
    		byte[] buf = str.getBytes("utf-8");
    		buf = Base64.decode(buf);
    		value = new String(buf, "utf-8");
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    	return value;
    }
    
    
    /**承办方str转换为list  1$$上海网达;2$$贵州移动 ;*/
    public static String orgListToStr(List<Organizer> orgList){
    	if(orgList == null || orgList.size() == 0 ){
    		return "";
    	}
    	Collections.sort(orgList, new Comparator<Organizer>() {
			@Override
			public int compare(Organizer o1, Organizer o2) {
				if(o1 != null && o2 != null){
					return o1.getSort() > o2.getSort() ? 1 : 0;
				}else{
					return 0;
				}
			}
		});
    	
    	StringBuffer sb = new StringBuffer();
    	int lastSort = 1;
    	for(Organizer org : orgList){
    		if(org!=null && isNotEmpty(org.getOrganizer())){
    			sb.append(lastSort++).append("$$").append(org.getOrganizer()).append(";;");
    		}
    	}
    	System.out.println(("orgListToStr:"+sb.toString()));
    	return sb.toString();
    }
    
    /**承办方list转换为Str 1$$上海网达;2$$贵州移动 ;*/
    public static List<Organizer> orgsToList(String orgs){
    	List<Organizer> list = new ArrayList<Organizer>();
    	if(isEmpty(orgs)){
    		Organizer o = new Organizer();
    		o.setSort(1);
    		list.add(o);
    		return list;
    	}
    	
    	int lastSort = 1;
    	String[] orgsArray = orgs.split(";;");
    	for(int i=0;i<orgsArray.length;i++){
    		if(orgsArray[i].indexOf("$$") < 0){
    			list.add(new Organizer(lastSort++,orgsArray[i]));
    		}else{
    			String[]  org = orgsArray[i].split("\\$\\$");
    			if(org.length == 2){
    				list.add(new Organizer(lastSort++,org[1]));
    			}
    		}
    	}
    	Collections.sort(list, new Comparator<Organizer>() {
			@Override
			public int compare(Organizer o1, Organizer o2) {
				if(o1 != null && o2 != null){
					return o1.getSort() > o2.getSort() ? 1 : 0;
				}else{
					return 0;
				}
			}
		});
    	
    	return list;
    }
    
    
    public  Properties reader(String fileName) {
   	 
        InputStream inputStream = this.getClass().getClassLoader()
                      .getResourceAsStream(fileName);
        Properties prop = new Properties();
        try {
               prop.load(inputStream);
               System.out.println("通用方法读取配置文件-----------OK");
        } catch (Exception e) {
               System.out.println("通用方法读取配置文件报错了");
               e.printStackTrace();
        }
        
        return prop;

 }
    
    
}
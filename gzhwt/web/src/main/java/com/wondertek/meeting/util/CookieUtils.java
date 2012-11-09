/**
 * 
 */
package com.wondertek.meeting.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Cookie 工具类
 * 
 * @author 金祝华
 */
public class CookieUtils {
	private final static String defEncode = "UTF-8";

	public static Cookie[] getCookie(HttpServletRequest request) {
		return request.getCookies();
	}

	public static String getCookieValue(HttpServletRequest request, String name) {
		return getCookieValue(request, name, defEncode);
	}

	public static String getMobile()
	{
		return "222222";
	}
	public static String getCookiePriValue(HttpServletRequest request, String name) {
		Cookie sCookie = getCookie(request, name);
		if (sCookie == null)
			return null;
		return sCookie.getValue();
	}

	public static Cookie getCookie(HttpServletRequest request, String name) {
		Cookie cookies[] = getCookie(request);
		if (cookies == null || cookies.length <= 0 || name == null)
			return null;
		else {
			for (int i = 0; i < cookies.length; i++) {
				Cookie sCookie = cookies[i];
				if (sCookie.getName().equals(name))
					return sCookie;
			}
		}
		return null;
	}

	public static String getCookieValue(HttpServletRequest request, String name, String encode) {
		if (encode == null || "".equals(encode.trim())) {
			encode = defEncode;
		}
		String val = getCookiePriValue(request, name);
		if (val == null)
			return null;
		try {
			return URLDecoder.decode(val, encode);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return val;
		}
	}

	public static void clearCookie(HttpServletResponse response, String name) {
		setCookie(response, name, "");
	}

	public static void removeCookie(HttpServletResponse response, String name) {
		Cookie cookie = new Cookie(name, "");
		cookie.setPath("/");
		cookie.setMaxAge(0);
		response.addCookie(cookie);
	}

	public static void removeCookie(HttpServletResponse response, String name, String domain, String path) {
		Cookie killMyCookie = new Cookie(name, "");
		killMyCookie.setMaxAge(0);
		killMyCookie.setDomain(domain);
		killMyCookie.setPath(path);
		response.addCookie(killMyCookie);
	}

	public static void removeCookie(HttpServletResponse response, String name, String path) {
		Cookie killMyCookie = new Cookie(name, "");
		killMyCookie.setMaxAge(0);
		killMyCookie.setPath(path);
		response.addCookie(killMyCookie);
	}

	public static void removeAllCookie(HttpServletRequest request, HttpServletResponse response) {
		Cookie cookies[] = getCookie(request);
		if (cookies == null || cookies.length <= 0)
			return;
		for (int i = 0; i < cookies.length; i++) {
			Cookie sCookie = cookies[i];
			sCookie.setValue(null);
			sCookie.setPath("/");
			sCookie.setMaxAge(0);
			response.addCookie(sCookie);
		}
	}

	public static Cookie cookie(String name, String value) {
		Cookie _cookie;
		try {
			_cookie = new Cookie(name, URLEncoder.encode(value, defEncode));
		} catch (UnsupportedEncodingException e) {
			_cookie = new Cookie(name, value);
			e.printStackTrace();
		}
		return _cookie;
	}

	public static void setCookie(HttpServletResponse response, String name, String value) {
		Cookie _cookie = cookie(name, value);
		_cookie.setMaxAge(-1);
		_cookie.setPath("/");
		response.addCookie(_cookie);
	}

	public static void setCookie(HttpServletResponse response, String name, String value, int expiry) {
		Cookie _cookie = cookie(name, value);
		_cookie.setMaxAge(expiry);// how many seconds
		_cookie.setPath("/");
		response.addCookie(_cookie);
	}

	public static void setCookie(HttpServletResponse response, String name, String value, int expiry, String domain,
			String path) {
		Cookie _cookie = cookie(name, value);
		_cookie.setMaxAge(expiry);// how many seconds
		_cookie.setDomain(domain);
		_cookie.setPath(path);
		response.addCookie(_cookie);
	}

	public static void setCookie(HttpServletResponse response, String name, String value, int expiry, String path) {
		Cookie _cookie = cookie(name, value);
		_cookie.setMaxAge(expiry);// how many seconds
		_cookie.setPath(path);
		response.addCookie(_cookie);
	}

	public static void setCookie(HttpServletResponse response, Cookie cookie) {
		response.addCookie(cookie);
	}

	/*
	 * 
	 */
	private void extendTime(HttpServletResponse response, Cookie cookie, int seconds, String path) {
		String domain = cookie.getDomain();
		String name = cookie.getName();
		String value = cookie.getValue();
		removeCookie(response, name, path);
		if (domain != null)
			setCookie(response, name, value, seconds, domain, path);
		else
			setCookie(response, name, value, seconds, path);
	}

	public void extendTime(HttpServletRequest request, HttpServletResponse response, String cookieName, int seconds,
			String path) {
		extendTime(response, getCookie(request, cookieName), seconds, path);
	}

	public static void main(String args[]) {
	}
}

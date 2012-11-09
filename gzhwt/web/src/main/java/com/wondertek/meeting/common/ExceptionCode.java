package com.wondertek.meeting.common;

public class ExceptionCode {
	public final static String LOGIN_USER_WRONG = "100001";// 用户名或密码错误

	public final static String LOGIN_PWD_WRONG = "100006";// 用户名或密码错误

	public final static String ADMIN_LOGIN_USER_PWD_WRONG = "100004";// 用户名或密码错误

	public final static String LOGIN_USER_FORBIDDEN = "100002";// 用户已被禁用

	public final static String ADD_USER_EXISTED = "100003";// 用户已经存在

	public final static String ADMIN_ADD_USER_EXISTED = "100005";// 用户已经存在

	public final static String SIGN_IN_UNEXIST = "100011";// 用户不在当前会议

	public final static String SIGN_IN_ERROR_SIGN_CODE = "100012";// 签到码错误。

	public final static String SIGN_IN_ALREADY_SIGN = "100013";// 已经签过到。
}

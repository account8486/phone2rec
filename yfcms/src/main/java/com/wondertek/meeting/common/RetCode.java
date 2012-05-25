package com.wondertek.meeting.common;

/**
 * 客户端接口返回码
 * 
 * @author 金祝华
 */
public class RetCode {

	/** 成功 */
	public static final String SUCCESS = "0";

	/** 请求参数不合法 */
	public static final String PARAMS_INVALID = "100";

	/** 验证失败 */
	public static final String FAIL = "101";

	/** 未知错误 */
	public static final String UNKOWN_WRONG = "1";
	
	/** 无效用户会话 */
	public static final String INVALID_SESSION = "2";
}

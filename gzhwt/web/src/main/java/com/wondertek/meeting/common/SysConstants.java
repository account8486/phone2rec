package com.wondertek.meeting.common;

/**
 * 系统常量类
 * 
 * @author 金祝华
 */
public class SysConstants {

	/** 用户角色：管理员 */
	public static final String ROLE_ADMIN = "sync_administrator";

	/** 数据库状态 0：无效 **/
	public static final int DB_STATUS_INVALID = 0;

	/** 数据库状态 1：有效 **/
	public static final int DB_STATUS_VALID = 1;

	/** 终端菜单类型 : 移动终端 */
	public static final String MENU_TERMINAL = "CLIENT";

	/** 终端菜单类型 : WEB页面 */
	public static final String MENU_WEB = "WEB";

	/** 终端菜单类型 : WAP页面 */
	public static final String MENU_WAP = "WAP";
	
	public static final String MENU_TOUCH = "TOUCH";

	/** 根组织id */
	public static final Long ROOT_ORG_ID = 0L;

	/** 门户类型 1：web */
	public static final Integer PORTAL_TYPE_WEB = 1;

	/** 门户类型 2：wap */
	public static final Integer PORTAL_TYPE_WAP = 2;

	/** 门户类型 3：客户端 */
	public static final Integer PORTAL_TYPE_CLIENT = 3;

	/** 客户端名称 */
	public static final String CLIENT_VERSION_NAME = "客户端android版";

	/** 用户角色 1：超级管理员 */
	public static final Long SUPER_ADMIN = 1L;

	/** 用户角色3：会议管理员 */
	public static final Long GROUP_ADMIN = 3L;

	/** 用户角色4：会务人员 */
	public static final Long MEETING_ADMIN = 4L;
}

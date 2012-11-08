package com.wirelesscity.common;

/**
 * @author liwei
 * 常量类
 */
public class Constants 
{
	//文件产生时间格式YYYYMMDDHHMMSS
	public static final String TIME_YYYYMMDDHHMMSS = "YYYYMMDDHHMMSS";
	
	//文件产生时间格式YYYYMMDD
	public static final String TIME_YYYYMMDD = "YYYYMMDD";
	
	//文件同步类常量
	class ftpFile
	{
		/**
		 * 
		 * WPID是无线城市平台的唯一标识，平台间互相通信的时候，用来标识消息的发送方与接收方
		 * WPID编码规则：平台类型+子系统+扩展位
		 * 前两位标识是集中平台or 省平台
		 * 中间2位标识子系统（00：服务子系统，01：运营管理子系统，02：统计分析子系统，03：客户端子系统，04展示子系统）
		 * 后4位留作扩展，标识子系统内部的模块
		 * 
		 */
		//基地集中平台服务子系统标识
		public static final String WPID = "99020000";
		//安徽平台服务子系统标识
		public static final String WPID_AH = "12020000";
		
		//头记录标记
		public static final String FILE_HEAD = "10";
		//尾记录标记
		public static final String FILE_FOOT = "90";
		//文件版本号
		public static final String FILE_VERSION = "01";
		//结果代码
		public static final String FILE_RESULT = "000";
		
		//统计子系统
		public static final String SYS_STATISTICS = "statistics";
		
	}
	
	
	public static final String INTERFACE_DONWLOAD = "download";
	public static final String INTERFACE_PAGER = "pager";
	public static final String INTERFACE_RESOURCE = "resource";
	public static final String INTERFACE_ORDER = "order";
	public static final String INTERFACE_APPLICATION = "application";
	public static final String INTERFACE_BIND = "bind";
	public static final String INTERFACE_PRODUCT = "product";
	
	
}

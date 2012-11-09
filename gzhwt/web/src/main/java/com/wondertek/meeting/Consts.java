/**
 * 定义全局常量
 * Author: 张国敬
 * Copyrights: 版权归上海网达软件有限公司安徽分公司所有
 * Create at: 2012-3-9
 */
package com.wondertek.meeting;

import java.util.HashMap;
import java.util.Map;

public class Consts {
	/**
	 * 定义文件类型及缩略图处
	 */
	public static final Map<String, String[]> FILE_TYPE_THUMBNAIL = new HashMap<String, String[]>();
	static {
		FILE_TYPE_THUMBNAIL.put("jpg", new String[]{"images/file/filetype/jpg.gif","JPG"});
		FILE_TYPE_THUMBNAIL.put("png", new String[]{"images/file/filetype/jpg.gif","PNG"});
		FILE_TYPE_THUMBNAIL.put("bmp", new String[]{"images/file/filetype/bmp.gif","BMP"});
		FILE_TYPE_THUMBNAIL.put("gif", new String[]{"images/file/filetype/gif.gif","GIF"});
		FILE_TYPE_THUMBNAIL.put("txt", new String[]{"images/file/filetype/txt.gif","TXT"});
		FILE_TYPE_THUMBNAIL.put("pdf", new String[]{"images/file/filetype/pdf.gif","PDF"});
		FILE_TYPE_THUMBNAIL.put("doc", new String[]{"images/file/filetype/word.gif","Word"});
		FILE_TYPE_THUMBNAIL.put("docx", new String[]{"images/file/filetype/word.gif","Word"});
		FILE_TYPE_THUMBNAIL.put("wps", new String[]{"images/file/filetype/wps.gif","WPS"});
		FILE_TYPE_THUMBNAIL.put("xls", new String[]{"images/file/filetype/excel.gif","Excel"});
		FILE_TYPE_THUMBNAIL.put("xlsx", new String[]{"images/file/filetype/excel.gif","Excel"});
		FILE_TYPE_THUMBNAIL.put("ppt", new String[]{"images/file/filetype/ppt.gif","PPT"});
		FILE_TYPE_THUMBNAIL.put("pptx", new String[]{"images/file/filetype/ppt.gif","PPT"});
		FILE_TYPE_THUMBNAIL.put("other", new String[]{"images/file/filetype/other.gif","其他"});
	}
	
	/**
	 * 资料管理显示方式属性名
	 */
	public static final String PAGE_CUSTOM_FILES_SHOW_TYPE = "files_show_type";
	public static final String FILES_SHOW_TYPE_BOOKSHELF = "bookshelf";
	public static final String FILES_SHOW_TYPE_LIST = "list";
	
	//会议portal登录页面图片名属性
	public static final String PAGE_CUSTOM_PORTAL_LOGIN_IMAGE = "portal_login_image";
	//会议portal顶部branding图片名属性
	public static final String PAGE_CUSTOM_PORTAL_TOP_IMAGE = "portal_top_image";
	//会议portal页面logo图片名属性
	public static final String PAGE_CUSTOM_PORTAL_LOGO_IMAGE = "portal_logo_image";
}

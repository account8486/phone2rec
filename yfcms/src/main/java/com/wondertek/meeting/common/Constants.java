package com.wondertek.meeting.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import com.wondertek.meeting.model.DinnerType;
import com.wondertek.meeting.model.Weather;

/**
 * 系统常量
 * 
 * @author 金祝华
 */
public class Constants {
	private static ResourceBundle resource = ResourceBundle.getBundle("sys-config"); //用于加载资源文件
	
	public interface MeetingContentType{
		int SCENIC = 1; //景点
		int HOTEL = 2; //酒店
	}
	
	//ckeditor图片上传的路径
	public static String UPLOAD_IMAGES_PATH = "upload/ck";
	//自定义菜单图片
	public static String UPLOAD_ICONS_PATH = "upload/menu_icon";
	//分组计划保存路径
	public static String UPLOAD_GROUP_PLAN_PATH = "upload/group_plan/";
	
	//菜单定义的类型
	public interface CLIENT_MENU_TYPE{
		String CUSTOME = "CUSTOME"; 
		String SYSTEM = "SYSTEM"; 
	}
	
	//菜单定义的类型
	public interface CLIENT_MENU_TERMINAL_TYPE{
		String WEB = "WEB";
		String WAP = "WAP";
		String CLIENT = "CLIENT"; 
	}
	
	/**用餐类型分类信息*/
	public static List<DinnerType> dinnerTypeList = new ArrayList<DinnerType>();
	/**用名字作为key*/
	public static Map<String,DinnerType> dinnerTypeNameMap = new HashMap<String,DinnerType>();
	/**用ID作为key*/
	public static Map<String,DinnerType> dinnerTypeIdMap = new HashMap<String,DinnerType>();
	static{
		dinnerTypeList = new ArrayList<DinnerType>();
		dinnerTypeList.add(new DinnerType("1","自助餐",false));
		dinnerTypeList.add(new DinnerType("2","桌餐",false));
		dinnerTypeList.add(new DinnerType("3","家宴",true));
		dinnerTypeList.add(new DinnerType("4","其他",false));
		
		dinnerTypeNameMap = new HashMap<String,DinnerType>();
		dinnerTypeIdMap = new HashMap<String,DinnerType>();
		
		for(DinnerType d:dinnerTypeList){
			dinnerTypeNameMap.put(d.getName(), d);
			dinnerTypeIdMap.put(d.getId(), d);
		}
	}
	
	/**分组模版类型*/
	public interface GROUP_PLAN_TYPE{
		Integer AGENDA = 1;
		Integer DINNER = 2;
	}
	/**分组模版是否公开*/
	public interface GROUP_PLAN_STATE{
		String OPEN = "1";
		String CLOSE = "0";
	}
	
	//页面定制信息上传的logo图片目录
	public static final String PAGE_CUSTOM_LOGO_UPLOAD_DIR = resource.getString("page.custom.logo.upload.dir");
	//页面定制的logo图片url
	public static final String PAGE_CUSTOM_PROPERTY_LOGO_URL = "logo_url";	
	//页面定制的版权信息
	public static final String PAGE_CUSTOM_PROPERTY_COPYRIGHTS = "copyrights";

	
	public static Map<String,Weather> weatherMap = new HashMap<String,Weather>();
	
	
	public static String emotions;
	static {
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		sb.append("{\"value\":\"[00]\",\"src\":\"images/emoji/emoji_00.gif\"}");
		for (int i=1; i<43; i++) {
			String numStr;
			if (i<10) {
				numStr = "0" + i;
			} else {
				numStr = String.valueOf(i);
			}
			sb.append(",{\"value\":\"[").append(numStr).append("]\",\"src\":\"images/emoji/emoji_").append(numStr).append(".gif\"}");
		}
		sb.append("]");
		emotions = sb.toString();
	}
}

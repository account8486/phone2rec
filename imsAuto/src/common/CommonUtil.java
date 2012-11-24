/**
 * 
 */
package common;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Properties;

/**
 * @author Administrator
 *
 */
public class CommonUtil {

	public static int[] deptId = {12,1200,1201,1202,1203,1204,1205,1206,1207,1208,1209,1210,1211,1212,1213,1214,1215,1216,1217};
	public static int[] deptId2 = {1200,1201,1202,1203,1204,1205,1206,1207,1208,1209,1210,1211,1212,1213,1214,1215,1216,1217};
	public static HashMap<Integer, String> Dept = new HashMap<Integer,String>() {
		private static final long serialVersionUID = 1683692464457009313L;
		{
			put(12, "安徽省电力公司");
			put(1200, "安徽省电力公司本部");
			put(1201, "合肥供电公司");
			put(1202, "安庆供电公司");
			put(1203, "蚌埠供电公司");
			put(1204, "亳州供电公司");
			put(1205, "巢湖供电公司");
			put(1206, "池州供电公司");
			put(1207, "滁州供电公司");
			put(1208, "阜阳供电公司");
			put(1209, "淮北供电公司");
			put(1210, "淮南供电公司");
			put(1211, "黄山供电公司");
			put(1212, "六安供电公司");
			put(1213, "马鞍山供电公司");
			put(1214, "宿州供电公司");
			put(1215, "铜陵供电公司");
			put(1216, "芜湖供电公司");
			put(1217, "宣城供电公司");
		}
	};
	
	
	/**
	 * 公司ID-公司名称，用于VRV查询映射
	 */
	public static HashMap<Integer, String> ID_Name = new HashMap<Integer,String>() {
		private static final long serialVersionUID = -7125457104948239178L;

		{
			put(1200, "安徽电力公司");
			put(1201, "合肥供电公司");
			put(1202, "安庆供电公司");
			put(1203, "蚌埠供电公司");
			put(1204, "亳州供电公司");
			put(1205, "巢湖供电公司");
			put(1206, "池州供电公司");
			put(1207, "滁州供电公司");
			put(1208, "阜阳供电公司");
			put(1209, "淮北供电公司");
			put(1210, "淮南供电公司");
			put(1211, "黄山供电公司");
			put(1212, "六安供电公司");
			put(1213, "马鞍山供电公司");
			put(1214, "宿州供电公司");
			put(1215, "铜陵供电公司");
			put(1216, "芜湖供电公司");
			put(1217, "宣城供电公司");
		}
	};
	
	/**
	 * 公司ID-公司名称，用于BETA查询映射
	 */
	public static HashMap<Integer, String> Link_Name = new HashMap<Integer,String>() {
		private static final long serialVersionUID = -7125457104948239178L;

		{
			put(1200, "省公司");
			put(1201, "合肥");
			put(1202, "安庆");
			put(1203, "蚌埠");
			put(1204, "亳州");
			put(1205, "巢湖");
			put(1206, "池州");
			put(1207, "滁州");
			put(1208, "阜阳");
			put(1209, "淮北");
			put(1210, "淮南");
			put(1211, "黄山");
			put(1212, "六安");
			put(1213, "马鞍山");
			put(1214, "宿州");
			put(1215, "铜陵");
			put(1216, "芜湖");
			put(1217, "宣城");
		}
	};	
	
	public static String[] resourceId = {"1200000000109641", "1200000000106981", "1200000000107008", "1200000000101561", "1200000000039527", 
										"1200000000109643", "1200000000039502", "1200000000109645", "1200000000109623", "1200000000109661", 
										"1200000000240761", "1200000000039521", "1200000000039525", "1200000000107006", 
										"1200000000039423", "1200000000240801", "1200000000109685", "1200000000109683", "1200000000039421", 
										"1200000000239741", "1200000000039422", "1200000000106996", "1200000000109681", "1200000000039501"};
	/**
	 * 业务系统号-系统名称
	 */
	public static HashMap<String, String> Syst = new HashMap<String, String>() {
		private static final long serialVersionUID = -7125457104948239178L;
		{
			put("1200000000109641", "财务管控");
			put("1200000000106981", "营销应用");
			put("1200000000107008", "营销辅助决策");
			put("1200000000101561", "生产管理");
			put("1200000000039527", "安全监督");
			put("1200000000109643", "应急管理");
			put("1200000000039502", "协同办公系统");
			put("1200000000109645", "远程培训");
			put("1200000000109623", "人资管控");
			put("1200000000109661", "基建管控");
			put("1200000000240761", "电网基建储备库");
			put("1200000000039521", "统计管理");
			put("1200000000039525", "农电管理");
			put("1200000000107006", "经济法律");
			put("1200000000039423", "电力市场交易");
			put("1200000000240801", "电网规划");
			put("1200000000109685", "电网前期");
			put("1200000000109683", "投资计划");
			put("1200000000039421", "企业门户");
			put("1200000000239741", "内网网站");
			put("1200000000039422", "综合查询");
			put("1200000000106996", "数据交换");
			put("1200000000109681", "ERP高级应用");
			put("1200000000039501", "ERP");
		}
	};
	
	/**
	 * 业务系统号-系统编号
	 */
	public static HashMap<String, String> Resource_No = new HashMap<String, String>() {
		private static final long serialVersionUID = -7125457104948239178L;
		{
			put("1200000000109641", "2");
			put("1200000000106981", "51");
			put("1200000000107008", "32");
			put("1200000000101561", "53");
			put("1200000000039527", "33");
			put("1200000000109643", "34");
			put("1200000000039502", "62");
			put("1200000000109645", "36");
			put("1200000000109623", "3");
			put("1200000000109661", "31");
			put("1200000000240761", "73");
			put("1200000000039521", "28");
			put("1200000000039525", "39");
			put("1200000000107006", "21");
			put("1200000000039423", "41");
			put("1200000000240801", "35");
			put("1200000000109685", "37");
			put("1200000000109683", "38");
			put("1200000000039421", "4");
			put("1200000000239741", "63");
			put("1200000000039422", "16");
			put("1200000000106996", "29");
			put("1200000000109681", "30");
			put("1200000000039501", "27");
		}
	};

	/**
	 * 应用编号-查询方法
	 */
	public static HashMap<String, String> AppNo_Method = new HashMap<String, String>() {
		private static final long serialVersionUID = -5177346743902947384L;
		{
			put("2", "getData_CWGK");
			put("51", "getData_YXYY");
			put("32", "getData_YXFZ");
			put("53", "getData_SCGL");
			put("33", "getData_AQJD");
			put("34", "getData_YJGL");
			put("62", "getData_XTBG");
			put("36", "getData_YCPX");
			put("3", "getData_RZGK");
			put("31", "getData_JJGK");
			put("73", "getData_DWCB");
			put("28", "");
			put("39", "getData_NDGL");
			put("21", "getData_JJFL");
			put("41", "getData_DLJY");
			put("35", "getData_DWGH");
			put("37", "getData_DWQQ");
			put("38", "getData_TZJH");
			put("4", "getData_QYMH");
			put("63", "getData_NWWZ");
			put("16", "getData_ZHCX");
			put("29", "getData_SJJH");
			put("30", "");
			put("27", "getData_ERP");
		}
	};

	public CommonUtil() {
	}
	
	/**
	 * 获取配置文本属性值
	 * @param name
	 * @return
	 */
	public static String getProperty(String name) {
		String value = null;
		//String str = System.getProperty( "user.dir "); //当前的用户工作目录
		try {
			Properties pro = new Properties();
			String path = null;
			try {
				path = CommonUtil.class.getClassLoader().getResource("properties.xml").toURI().getPath().substring(1);
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//InputStream in = CommonUtil.class.getClassLoader().getResourceAsStream("config.properties");
			InputStream in = new FileInputStream(path);
			pro.loadFromXML(in);
			in.close();
			value = pro.getProperty(name);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return value;
	}
	
	/**
	 * 修改配置文本属性
	 * @param name
	 * @param value
	 */
	public static void setProperty(String name, String value) {
		Properties pro = new Properties();
		try {
			String path = null;
			try {
				path = CommonUtil.class.getClassLoader().getResource("properties.xml").toURI().getPath().substring(1);
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			InputStream in = new FileInputStream(path);
			//InputStream in = CommonUtil.class.getClassLoader().getResourceAsStream("config.properties");
			pro.loadFromXML(in);
			in.close();

			pro.setProperty(name, value);
			FileOutputStream fOut = new FileOutputStream(path);
			pro.storeToXML(fOut, "配置信息");
			//pro.storeToXML(new FileOutputStream("properties.xml"), null);
			fOut.close();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			//pro.clear();
		}
	}
	
	/**
	 * 获取巡检提示消息
	 * @param name
	 * @return
	 */
	public static String getMessage(String name) {
		String value = null;
		//String str = System.getProperty( "user.dir "); //当前的用户工作目录
		try {
			Properties pro = new Properties();
			String path = null;
			try {
				path = CommonUtil.class.getClassLoader().getResource("message.xml").toURI().getPath().substring(1);
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}

			InputStream in = new FileInputStream(path);
			pro.loadFromXML(in);
			in.close();
			value = pro.getProperty(name);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return value;
	}
}

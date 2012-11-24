package common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Calendar;

public class DBUtil2 {

	private static String classForName = "com.microsoft.jdbc.sqlserver.SQLServerDriver";
	private static String url = "";
	private static String uid = "";
	private static String pwd = "";
	
	public static Connection getConnection(String dbName) {
		Connection conn = null;
		try {
			if (dbName.equals("vrv")) {
				Class.forName(classForName);
				url = CommonUtil.getProperty("URL_VRV");
				uid = CommonUtil.getProperty("UID_VRV");
				pwd = CommonUtil.getProperty("PWD_VRV");
			}
			else if (dbName.equals("beta")) {
				Class.forName(classForName);
				url = CommonUtil.getProperty("URL_BETA");
				uid = CommonUtil.getProperty("UID_BETA");
				pwd = CommonUtil.getProperty("PWD_BETA");
			}
			else if (dbName.equals("test")) {
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
				url = CommonUtil.getProperty("URL_CENT");
				uid = CommonUtil.getProperty("UID_CENT");
				pwd = CommonUtil.getProperty("PWD_CENT");
				System.out.println(url);
				System.out.println(uid);
				System.out.println(pwd);
			}
			else {
				System.out.println("ERROR:" + Calendar.getInstance().getTime().toString() + "未知数据源");
			}
			conn = DriverManager.getConnection(url, uid, pwd);
		} catch (ClassNotFoundException e) {
			System.out.println("ERROR1:" + dbName + Calendar.getInstance().getTime().toString() + e.getMessage());
		} catch (SQLException e) {
			System.out.println("ERROR2:" + dbName + Calendar.getInstance().getTime().toString() + e.getMessage());
		}
		return conn;
	}
}

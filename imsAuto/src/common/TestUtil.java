/**
 * 
 */
package common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Calendar;

/**
 * @author Administrator
 *
 */
public class TestUtil {

	private static String classForName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	private static String url = "jdbc:sqlserver://10.138.214.2:1433;DatabaseName=center";
	private static String uid = "sa";
	private static String pwd = "12345678";

	public static Connection getConnection(String dbName) {
		Connection conn = null;
		try {
			Class.forName(classForName);
			conn = DriverManager.getConnection(url, uid, pwd);
			System.out.println("Connection Successful!");
		} catch (ClassNotFoundException e) {
			System.out.println("ERROR1:" + dbName + Calendar.getInstance().getTime().toString() + e.getMessage());
			e.printStackTrace();
		} catch (SQLException e) {
			//System.out.println("ERROR2:" + dbName + Calendar.getInstance().getTime().toString() + e.getMessage());
			e.printStackTrace();
		}
		return conn;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Connection conn = getConnection("");

	}

}

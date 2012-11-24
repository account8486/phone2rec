package common;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DBUtil {
	private static String JNDI_NAME = "ims";

	private static DataSource dataSource;

	public static List<Map<String, Object>> executeQuery(String sql,
			Object... params) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();

		try {
			conn = getConnection(JNDI_NAME);
			ps = conn.prepareStatement(sql);
			for (int i = 0; i < params.length; i++) {
				ps.setObject(i + 1, params[i]);
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				ResultSetMetaData rsmd = rs.getMetaData();
				int numberOfColumns = rsmd.getColumnCount();
				Map<String, Object> result = new HashMap<String, Object>();
				for (int i = 1; i <= numberOfColumns; i++) {
					String columnName = rsmd.getColumnName(i);
					Object columnValue = rs.getObject(columnName);
					result.put(columnName.toLowerCase(), columnValue);
				}
				results.add(result);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			release(rs, ps, conn);
		}

		return results;
	}
	
	public static int executeUpdate(String sql, Object... params) {
		Connection conn = null;
		PreparedStatement ps = null;
		int updateRows = 0;

		try {
			conn = getConnection(JNDI_NAME);
			ps = conn.prepareStatement(sql);
			for (int i = 0; i < params.length; i++) {
				ps.setObject(i + 1, params[i]);
			}

			updateRows = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			release(ps, conn);
		}

		return updateRows;
	}


	private static DataSource getDataSource(String jndiName) {
		try {
			Context context = new InitialContext();
			context  = (Context)context.lookup("java:/comp/env");    
			if (jndiName != null) {
				JNDI_NAME = jndiName;
			}
			dataSource = (DataSource) context.lookup(JNDI_NAME);
		} catch (NamingException e) {
			System.out.println("�޷����DataSource!");
			e.printStackTrace();
		}
		return dataSource;
	}

	public static Connection getConnection(String jndiName) {
		DataSource dataSource = getDataSource(jndiName);
		Connection conn = null;
		if (dataSource != null) {
			try {
				conn = dataSource.getConnection();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				System.out.println("ERROR:" + e.getMessage());
			}
		} else {
			System.out.println("�޷����Connection!");
			return null;
		}
		return conn;
	}

	public static void commit(Connection conn) {
		if (conn != null) {
			try {
				conn.commit();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void rollback(Connection conn) {
		if (conn != null) {
			try {
				conn.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void release(ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void release(Statement stmt) {
		try {
			if (stmt != null) {
				stmt.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void release(Connection conn) {
		try {
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void release(Statement stmt, Connection conn) {
		release(stmt);
		release(conn);
	}

	public static void release(ResultSet rs, Statement stmt, Connection conn) {
		release(rs);
		release(stmt, conn);
	}
}

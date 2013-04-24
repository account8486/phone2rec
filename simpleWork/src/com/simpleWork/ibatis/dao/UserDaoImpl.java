package com.simpleWork.ibatis.dao;

import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

public class UserDaoImpl {

	private static SqlMapClient sqlMapClient = null;

	// 读取配置文件
	static {
		try {
			Reader reader = Resources.getResourceAsReader("/com/simpleWork/ibatis/config/sqlMap-Config.xml");
			sqlMapClient = SqlMapClientBuilder.buildSqlMapClient(reader);
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	public List getUserList(Map map) {
		List lst = new ArrayList();
		try {
			sqlMapClient.startTransaction();
			//lst = sqlMapClient.queryForList("getDb.getAllUsers");
			lst = sqlMapClient.queryForList("getDb.getAllUsers", map);
			sqlMapClient.commitTransaction();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return lst;
	}

}

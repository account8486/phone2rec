package com.wirelesscity.service.impl;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import oracle.jdbc.OracleTypes;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;

import com.wirelesscity.service.SPService;

public class SPServiceImpl extends JdbcServiceImpl implements SPService {


	
	/**
	 * 调用存储过程
	 * @param spName
	 */
	@SuppressWarnings("unchecked")
	public List<HashMap<String, Object>> executeSP(String procedure) {
		//procedure = "{call WCITY2_STATISTIC.sp_uservisit_stat(?)}";
		return (List<HashMap<String, Object>>) jdbcTemplate.execute(procedure,
				new CallableStatementCallback() {
					public Object doInCallableStatement(
							CallableStatement cs) throws SQLException,
							DataAccessException {

						List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();

						cs.registerOutParameter(1, OracleTypes.CURSOR);
						cs.execute();
						ResultSet rs = (ResultSet) cs.getObject(1);

						while (rs.next()) {
							HashMap<String, Object> dataMap = new HashMap<String, Object>();
							ResultSetMetaData rsMataData = rs.getMetaData();
							for (int i = 1; i <= rsMataData.getColumnCount(); i++) {
								dataMap.put(rsMataData.getColumnName(i), rs
										.getString(rsMataData.getColumnName(i)));
							}
							list.add(dataMap);
						}

						return list;
					}
				});

	}
	
}

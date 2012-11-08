package com.wirelesscity.service;

import java.util.HashMap;
import java.util.List;

public interface SPService extends JdbcService {
	/**
	 * 调用存储过程
	 * @param spName
	 */
	@SuppressWarnings("unchecked")
	public List<HashMap<String, Object>> executeSP(String procedure);

}

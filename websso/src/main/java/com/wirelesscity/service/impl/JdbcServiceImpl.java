package com.wirelesscity.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.wirelesscity.service.JdbcService;

public class JdbcServiceImpl implements JdbcService {
	public Logger log = LoggerFactory.getLogger(this.getClass());

	JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	
	public SqlRowSet getResultSet(String sql) {
		SqlRowSet rs =null;
		try {
		    rs = jdbcTemplate.queryForRowSet(sql);
		    
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return rs;

	}
	
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List getList(String sql) {
		List<Map> list = jdbcTemplate.queryForList(sql);
		log.debug(String.valueOf(list.size()));
		for (Object obj : list) {
			Map map4entity = (Map) obj;
			log.error(String.valueOf(map4entity.get("username")));

		}

		return list;
	}
	
	
	
	
	
	

}

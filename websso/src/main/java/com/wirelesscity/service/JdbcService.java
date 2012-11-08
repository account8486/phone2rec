package com.wirelesscity.service;

import java.util.List;

import org.springframework.jdbc.support.rowset.SqlRowSet;

public interface JdbcService{
	
	public SqlRowSet getResultSet(String sql);
	
	@SuppressWarnings({ "rawtypes" })
	public List getList(String sql) ;
	
}

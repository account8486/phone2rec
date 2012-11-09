package com.wondertek.meeting.dao;

import java.util.List;

import com.wondertek.meeting.model.ClientVersion;

/**
 * 客户端版本
 * 
 * @author 金祝华
 */
public interface ClientVersionDao extends BaseDao<ClientVersion, Integer> {

	public ClientVersion findById(Integer id);
	
	/**
	 * 查询全部
	 * 
	 * @param id
	 * @return
	 */
	public List<ClientVersion> findAll();
}

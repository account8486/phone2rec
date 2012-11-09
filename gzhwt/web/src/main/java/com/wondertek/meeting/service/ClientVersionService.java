package com.wondertek.meeting.service;

import java.io.File;
import java.util.List;

import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.ClientVersion;

/**
 * 客户端版本
 * 
 * @author 金祝华
 */
public interface ClientVersionService extends BaseService<ClientVersion, Integer> {

	public ClientVersion findById(Integer id);

	/**
	 * 查询全部
	 * 
	 * @param id
	 * @return
	 */
	public List<ClientVersion> findAll();

	/**
	 * 更新
	 * 
	 * @param version
	 * @return
	 */
	public void update(String url);

	/**
	 * 上传
	 * 
	 * @param url
	 * @param version
	 * @param backFile
	 * @param saveFile
	 * @param upload
	 * @throws Throwable
	 */
	public void upload(String url, ClientVersion version, File backFile, File saveFile, File upload) throws Throwable;
	
	public Pager<ClientVersion> findPager(ClientVersion version, int currentPage, int pageSize) throws ServiceException;
}

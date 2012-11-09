package com.wondertek.meeting.service.impl;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.dao.ClientVersionDao;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.ClientVersion;
import com.wondertek.meeting.service.ClientVersionService;
import com.wondertek.meeting.util.FileOperatorUtil;

/**
 * 客户端版本
 * 
 * @author 金祝华
 */
public class ClientVersionServiceImpl extends BaseServiceImpl<ClientVersion, Integer> implements ClientVersionService {

	private ClientVersionDao clientVersionDao;

	public ClientVersion findById(Integer id) {
		return clientVersionDao.findById(id);
	}

	/**
	 * 查询全部
	 * 
	 * @param id
	 * @return
	 */
	public List<ClientVersion> findAll() {
		return clientVersionDao.findAll();
	}

	/**
	 * 更新
	 * 
	 * @param version
	 * @return
	 */
	public void update(String url) {
		List<ClientVersion> list = clientVersionDao.findAll();

		if (list == null || list.size() == 0) {
			log.error("客户端版本信息不存在。");
			return;
		}

		ClientVersion oldVersion = list.get(0);
		oldVersion.setUrl(url);

		clientVersionDao.saveOrUpdateEntity(oldVersion);
	}

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
	public void upload(String url, ClientVersion version, File backFile, File saveFile, File upload) throws Throwable {

		try {
			// 更新当前版本的下载url
			this.update(url);

			// 新增版本
			this.add(version);

			// 如果文件存在，先备份当前文件
			if (saveFile.exists())
			{
				FileOperatorUtil.copy(saveFile, backFile);
			}
			
			// 更新文件
			FileOperatorUtil.copy(upload, saveFile);
		} catch (Exception e) {
			log.error("upload client version error!",e);
			throw new Throwable();
		}

	}

	public Pager<ClientVersion> findPager(ClientVersion version, int currentPage, int pageSize) throws ServiceException {

		String hql = "from ClientVersion b";
		Map<String, Object> properties = new HashMap<String, Object>();
		
		if (version != null && version.getVersion() != null) {
			hql += " where b.version = :version";
			properties.put("version", version.getVersion());
		}
		hql += " order by b.id desc";

		try {
			return clientVersionDao.findPager(hql, currentPage, pageSize, properties);
		} catch (Exception e) {
			final String errMsg = "Find pager error ";
			log.error(errMsg, e);
			throw new ServiceException(errMsg, e);
		}
	}

	public ClientVersionDao getClientVersionDao() {
		return clientVersionDao;
	}

	public void setClientVersionDao(ClientVersionDao clientVersionDao) {
		this.basicDao = clientVersionDao;
		this.clientVersionDao = clientVersionDao;
	}
}

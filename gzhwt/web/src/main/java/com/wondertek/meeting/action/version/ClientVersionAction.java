package com.wondertek.meeting.action.version;

import java.io.File;
import java.util.Date;
import java.util.List;

import com.wondertek.meeting.action.base.BaseAction;
import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.common.RetCode;
import com.wondertek.meeting.common.SysConstants;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.ClientVersion;
import com.wondertek.meeting.service.ClientVersionService;
import com.wondertek.meeting.util.DateUtil;
import com.wondertek.meeting.util.StringUtil;

/**
 * 客户端版本管理
 * 
 * @author 金祝华
 */
public class ClientVersionAction extends BaseAction {

	private static final long serialVersionUID = -3102151360541524276L;

	ClientVersionService clientVersionService;

	ClientVersion version;

	// 上传文件域对象
	private File upload;

	// 上传文件名
	private String uploadFileName;

	// 上传文件类型
	private String uploadContentType;

	private String saveFileName;

	/**
	 * 升级检测 判断客户端软件是否需要升级
	 * 
	 * @return
	 */
	public String checkUpdate() {
		String userAgent = this.getRequest().getHeader("user-agent");

		String clientVersion = this.getParameter("cVersion");

		log.info("checkUpdate start clientVersion=" + clientVersion + ",userAgent=" + userAgent);

		// 1.检查参数合法性
		if (StringUtil.isEmpty(clientVersion)) {
			resultMap.put("retcode", RetCode.PARAMS_INVALID);
			resultMap.put("retmsg", getText("version.empty.version"));
			log.debug("checkUpdate fail, " + getText("version.empty.version"));
			return SUCCESS;
		}

		int cVersion = 0;
		try {
			cVersion = Integer.parseInt(clientVersion);
		} catch (NumberFormatException ex) {
			resultMap.put("retcode", RetCode.PARAMS_INVALID);
			resultMap.put("retmsg", getText("version.invalid.version"));
			log.debug("checkUpdate fail, " + getText("version.invalid.version"));
			return SUCCESS;
		}

		List<ClientVersion> list = clientVersionService.findAll();
		if (list == null || list.size() == 0 || list.get(0).getVersion() == null) {
			log.error("客户端版本信息不存在。");
			resultMap.put("retcode", RetCode.UNKOWN_WRONG);
			resultMap.put("retmsg", getText("version.error.version"));
			log.debug("checkUpdate fail, " + getText("version.error.version"));
			return SUCCESS;
		}

		String result = "";
		if (list.get(0).getVersion() > cVersion) {
			resultMap.put("update", "1");
			resultMap.put("updateUrl", this.getServerUrl() + saveFileName);
			result = "需要更新，更新地址为：" + this.getServerUrl() + saveFileName;
		} else {
			resultMap.put("update", "0");
			result = "不需要更新。";
		}

		resultMap.put("retcode", RetCode.SUCCESS);
		resultMap.put("retmsg", getText("version.success"));

		log.info("checkUpdate success end. result = " + result);
		return SUCCESS;
	}

	/**
	 * 查询客户端软件
	 * 
	 * @return
	 */
	public String list() {

		Pager<ClientVersion> pager = null;
		try {
			pager = clientVersionService.findPager(version, currentPage, pageSize);
		} catch (ServiceException e) {
			log.error("客户端版本信息查询出错:" + e.toString());
		}

		this.getRequest().setAttribute("pager", pager);

		return SUCCESS;
	}

	/**
	 * 上传客户端软件
	 * 
	 * @return
	 */
	public String upload() {
		try {
			// 文件要保存到的服务器路径
			File saveDir = new File(this.getDocumentRoot());
			if (!saveDir.exists()) {
				saveDir.mkdirs();
			}

			// 要保存的目标文件
			File saveFile = new File(saveDir + File.separator + saveFileName);

			String nowStr = DateUtil.getFormatDate(new Date(), "yyyyMMddHHmmss");

			// 当前版本下载url
			String url = nowStr + saveFileName;

			// 备份文件
			File backFile = new File(saveDir + File.separator + nowStr + saveFileName);

			// 设置新版本属性
			version.setName(SysConstants.CLIENT_VERSION_NAME);// 名称
			version.setModifyTime(new Date());// 更新时间
			version.setUrl(saveFileName); // 下载url

			// 上传处理
			clientVersionService.upload(url, version, backFile, saveFile, upload);

			errMsg = getText("version.upload.success");
		} catch (Throwable e) {

			log.error("上传客户端失败", e);
			errMsg = getText("version.upload.fail");
		}

		return SUCCESS;
	}

	public ClientVersion getVersion() {
		return version;
	}

	public void setVersion(ClientVersion version) {
		this.version = version;
	}

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public String getUploadContentType() {
		return uploadContentType;
	}

	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}

	public String getSaveFileName() {
		return saveFileName;
	}

	public void setSaveFileName(String saveFileName) {
		this.saveFileName = saveFileName;
	}

	public ClientVersionService getClientVersionService() {
		return clientVersionService;
	}

	public void setClientVersionService(ClientVersionService clientVersionService) {
		this.clientVersionService = clientVersionService;
	}
}

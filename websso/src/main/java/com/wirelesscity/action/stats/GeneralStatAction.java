package com.wirelesscity.action.stats;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import com.wirelesscity.action.base.BaseAction;
import com.wirelesscity.common.Constants;
import com.wirelesscity.common.GenerateFileUtil;
import com.wirelesscity.common.StringUtil;
import com.wirelesscity.service.SPService;

/**
 * 通用查询
 * 
 * @author Administrator
 * 
 */
public class GeneralStatAction extends BaseAction {

	private static final long serialVersionUID = -6238058585952697881L;
	SPService spService;

	public SPService getSpService() {
		return spService;
	}

	public void setSpService(SPService spService) {
		this.spService = spService;
	}

	/**
	 * 统计分析子系统调用该接口查看下载信息，下载信息包括客户端编码、客户端类型、下载次数等。
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getDownloadStat() throws Exception {
		String procedure = "{call WCITY2_STATISTIC.sp_download_stat(?)}";
		List<HashMap<String, Object>> lstResult = spService
				.executeSP(procedure);
		// Constants.INTERFACE_DONWLOAD
		this.processGenAndFtpFiles(lstResult, Constants.INTERFACE_DONWLOAD);
		return SUCCESS;
	}

	/**
	 * 获取页面信息
	 * 
	 * @return
	 */
	public String getPagerStat() {
		String procedure = "{call WCITY2_STATISTIC.sp_pager_stats(?)}";
		List<HashMap<String, Object>> lstResult = spService
				.executeSP(procedure);
		this.processGenAndFtpFiles(lstResult, Constants.INTERFACE_PAGER);

		return SUCCESS;
	}

	/**
	 * 资源信息包括资源编码、资源类型、资源名称等。其中资源编码必须唯一且不能为空
	 * 
	 * @return
	 */
	public String getResourceStat() {
		String procedure = "{call WCITY2_STATISTIC.sp_resource_stat(?)}";
		List<HashMap<String, Object>> lstResult = spService.executeSP(procedure);
		this.processGenAndFtpFiles(lstResult, Constants.INTERFACE_RESOURCE);
		return SUCCESS;

	}

	/**
	 * 获取订单信息
	 * 
	 * @return
	 */
	public String getOrderStat() {
		String procedure = "{call WCITY2_STATISTIC.sp_order_stat(?)}";
		List<HashMap<String, Object>> lstResult = spService.executeSP(procedure);
		this.processGenAndFtpFiles(lstResult, Constants.INTERFACE_ORDER);
		return SUCCESS;
	}

	/**
	 * 查询应用信息
	 * 
	 * @return
	 */
	public String getApplicationstat() {

		String procedure = "{call WCITY2_STATISTIC.sp_application_stat(?)}";
		List<HashMap<String, Object>> lstResult = spService.executeSP(procedure);
		this.processGenAndFtpFiles(lstResult, Constants.INTERFACE_APPLICATION);
		return SUCCESS;
	}

	/**
	 * 服务订阅信息
	 * 
	 * @return
	 */
	public String getBindStat() {
		String procedure = "{call WCITY2_STATISTIC.sp_bind_stat(?)}";
		List<HashMap<String, Object>> lstResult = spService.executeSP(procedure);
		this.processGenAndFtpFiles(lstResult, Constants.INTERFACE_BIND);
		return SUCCESS;
	}

	/**
	 * 产品信息
	 * @return
	 */
	public String getProductStat() {
		// sp_product_stat
		String procedure = "{call WCITY2_STATISTIC.sp_product_stat(?)}";
		List<HashMap<String, Object>> lstResult = spService.executeSP(procedure);
		this.processGenAndFtpFiles(lstResult, Constants.INTERFACE_PRODUCT);
		return SUCCESS;
	}

	
	/**
	 * 进行生成文件与FTP文件的过程
	 * @param lstResult
	 * @param interFaceName
	 */
	private void processGenAndFtpFiles(List lstResult, String interFaceName) {
		String localPath = GenerateFileUtil.generate(lstResult,
				ftpService.getFtpLocalPathRoot(), interFaceName);

		try {
			ftpService.upFile(localPath, ftpService.getFtpRemotePathRoot()
					+ getLocalFileNameFromPath(localPath));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 获取文件名
	 * 
	 * @param localPath
	 * @return
	 */
	private String getLocalFileNameFromPath(String localPath) {
		if (StringUtil.isNotEmpty(localPath)) {
			return localPath.split("\\" + File.separator)[localPath.split("\\"
					+ File.separator).length - 1];
		}

		return "";
	}

}

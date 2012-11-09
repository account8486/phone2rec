/**
 * 定制组织机构对应的页面元素
 * Author: 张国敬
 * Copyrights: 版权归上海网达软件有限公司安徽分公司所有
 * Create at: 2011-12-30
 */
package com.wondertek.meeting.action.custom;

import java.io.File;
import java.io.OutputStream;
import java.util.Map;

import com.wondertek.meeting.action.base.BaseAction;
import com.wondertek.meeting.common.Constants;
import com.wondertek.meeting.model.custom.PageCustom;
import com.wondertek.meeting.service.OrganizationService;
import com.wondertek.meeting.service.custom.PageCustomService;
import com.wondertek.meeting.util.FileOperatorUtil;

public class PageCustomAction extends BaseAction {
	private static final long serialVersionUID = 1L;

	private PageCustomService pageCustomService;
	private OrganizationService organizationService;
	private Long organId;
	private String copyrights; //保存版权信息
	private File logoImage;	//保存上传的logo图片
	private String logoImageFileName; //上传的文件名
	
	/*
	 * 查看页面定制属性
	 */
	public String showPageCustomInfo() throws Exception {
		log.info("showPageCustomInfo: organId=" + organId);
		Map<String, String> pageCustomMap = this.pageCustomService.getPagePropertyMap(organId);
		this.setAttribute("pageCustomMap", pageCustomMap);
		return "configCustomInfo";
	}
	
	/*
	 * 保存页面定制属性 
	 */
	public String savePageCustomInfo() throws Exception {
		log.info("savePageCustomInfo: organId=" + organId);
		
		//保存logo图片
		if(logoImage == null) {
			this.setAttribute("message", "没有上传指定的logo图片");
			return this.showPageCustomInfo();
		}
		
		String logoImageSavePath = Constants.PAGE_CUSTOM_LOGO_UPLOAD_DIR + organId;
		log.info("savePageCustomInfo: logoImageSavePath=" + logoImageSavePath);
		File savePath = new File(logoImageSavePath);
		if(! savePath.exists()) {
			savePath.mkdirs();
		}
		
		//保存上传的logo图片到指定的目录下
		String savedLogoFileName = logoImageSavePath + File.separator + this.logoImageFileName;
		log.info("savePageCustomInfo: savedLogoFileName=" + savedLogoFileName);
		File savedLogoFile = new File(savedLogoFileName);
		
		FileOperatorUtil.copy(this.logoImage, savedLogoFile);
		
		PageCustom custom = this.pageCustomService.getPageCustom(organId, Constants.PAGE_CUSTOM_PROPERTY_LOGO_URL);
		if(custom == null) {
			custom = new PageCustom();
			custom.setPropertyName(Constants.PAGE_CUSTOM_PROPERTY_LOGO_URL);
//			custom.setOrgan(this.organizationService.findById(organId));
		}
		
		custom.setPropertyValue(this.logoImageFileName);
		this.pageCustomService.saveOrUpdate(custom);
		this.setAttribute("message", "页面属性数据保存成功");
		return this.showPageCustomInfo();
	}
	
	/*
	 * 显示页面定制logo图片
	 */
	public String showPageCustomLogo() throws Exception {
		log.info("showPageCustomLogo: organId=" + organId);
		PageCustom custom = this.pageCustomService.getPageCustom(organId, Constants.PAGE_CUSTOM_PROPERTY_LOGO_URL);
		if(custom == null) {
			return null;
		}
		
		//设置图片文件显示Content-Type
		this.getResponse().setContentType("image/*");
		
		//把图片文件数据写入response
		String logoImageName = Constants.PAGE_CUSTOM_LOGO_UPLOAD_DIR
			+ organId + File.separator + custom.getPropertyValue();
		log.info("showPageCustomLogo: logoImageSavePath=" + logoImageName);
		
		File logoImage = new File(logoImageName);
		OutputStream outputStream = this.getResponse().getOutputStream();
		FileOperatorUtil.copyFileAsStream(logoImage, outputStream);
		outputStream.flush();
		
		return null;
	}
	
	/*
	 * 删除页面定制logo图片
	 */
	public String deletePageCustomLogo() throws Exception {
		log.info("deletePageCustomLogo: organId=" + organId);
		
		PageCustom custom = this.pageCustomService.getPageCustom(organId, Constants.PAGE_CUSTOM_PROPERTY_LOGO_URL);
		if(custom == null) {
			this.resultMap.put("result", false);
			return "ok";
		}
		
		//删除图片文件
		String logoImageName = Constants.PAGE_CUSTOM_LOGO_UPLOAD_DIR
			+ organId + File.separator + custom.getPropertyValue();
		log.info("deletePageCustomLogo: logoImageSavePath=" + logoImageName);
		File logoImage = new File(logoImageName);
		logoImage.delete();
		
		//删除数据库记录
		this.pageCustomService.delete(custom);
		
		this.resultMap.put("result", true);
		return "ok";
	}
	
	public PageCustomService getPageCustomService() {
		return pageCustomService;
	}

	public void setPageCustomService(PageCustomService pageCustomService) {
		this.pageCustomService = pageCustomService;
	}

	public Long getOrganId() {
		return organId;
	}

	public void setOrganId(Long organId) {
		this.organId = organId;
	}

	public String getCopyrights() {
		return copyrights;
	}

	public void setCopyrights(String copyrights) {
		this.copyrights = copyrights;
	}

	public File getLogoImage() {
		return logoImage;
	}

	public void setLogoImage(File logoImage) {
		this.logoImage = logoImage;
	}

	public String getLogoImageFileName() {
		return logoImageFileName;
	}

	public void setLogoImageFileName(String logoImageFileName) {
		this.logoImageFileName = logoImageFileName;
	}

	public OrganizationService getOrganizationService() {
		return organizationService;
	}

	public void setOrganizationService(OrganizationService organizationService) {
		this.organizationService = organizationService;
	}
}

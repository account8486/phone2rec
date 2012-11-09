/**
 * 会议云类型Action
 * Author: 张国敬
 * Copyrights: 版权归上海网达软件有限公司安徽分公司所有
 * Create at: Jan 13, 2012
 */
package com.wondertek.meeting.action.custom;

import java.io.File;
import java.util.Date;
import java.util.List;

import com.wondertek.meeting.Consts;
import com.wondertek.meeting.action.base.BaseAction;
import com.wondertek.meeting.model.Meeting;
import com.wondertek.meeting.model.custom.PageCustom;
import com.wondertek.meeting.model.custom.PageTheme;
import com.wondertek.meeting.service.MeetingService;
import com.wondertek.meeting.service.custom.PageCustomService;
import com.wondertek.meeting.service.custom.PageThemeService;
import com.wondertek.meeting.util.FileOperatorUtil;
import com.wondertek.meeting.util.StringUtil;

@SuppressWarnings("serial")
public class MeetingViewConfigAction extends BaseAction {
	//private static final String UPLOAD_IMAGE_PATH = "upload\\custom\\"; //上传图片保存目录
	private static final String UPLOAD_IMAGE_PATH = new StringBuffer("upload")
			.append(File.separator).append("custom").append(File.separator)
			.toString(); // 上传图片保存目录
	private MeetingService meetingService;
	private PageThemeService pageThemeService;
	private PageCustomService pageCustomService;
	
	private Long meetingId;
	private Long pageThemeId;
	
	private File logoImage;	//保存上传的logo图片
	private String logoImageFileName; //上传的logo文件名
	private File topImage;	//保存上传的portal会议top branding图片
	private String topImageFileName; //上传的top brankding图片文件名
	private File loginImage;	//保存上传的login图片
	private String loginImageFileName; //上传的login图片文件名
	
	private String imageType; 
	
	public String goViewConfig() throws Exception {
		//查找可用的页面皮肤主题列表
		List<PageTheme> pageThemeList = this.pageThemeService.findAllUsablePageThemes();
		this.setAttribute("pageThemeList", pageThemeList);
		
		//查询登录页面图片配置
		PageCustom loginCustom = this.pageCustomService.getPageCustom(meetingId, Consts.PAGE_CUSTOM_PORTAL_LOGIN_IMAGE);
		if(loginCustom != null) {
			this.setAttribute("loginImage", loginCustom.getPropertyValue());
		}
		
		//查询logo图片配置
		PageCustom logoCustom = this.pageCustomService.getPageCustom(meetingId, Consts.PAGE_CUSTOM_PORTAL_LOGO_IMAGE);
		if(logoCustom != null) {
			this.setAttribute("logoImage", logoCustom.getPropertyValue());
		}
		
		//查询页面顶部图片配置
		PageCustom topCustom = this.pageCustomService.getPageCustom(meetingId, Consts.PAGE_CUSTOM_PORTAL_TOP_IMAGE);
		if(topCustom != null) {
			this.setAttribute("topImage", topCustom.getPropertyValue());
		}
		
		this.setAttribute("meeting", meetingService.findById(meetingId));
		return "viewConfig";
	}
	
	public String saveViewConfig() throws Exception {
		Meeting meeting = this.meetingService.findById(meetingId);
		
		//保存主题风格
		if(this.pageThemeId != null) {
			PageTheme theme = this.pageThemeService.findById(pageThemeId);
			meeting.setPageTheme(theme);
			this.meetingService.saveOrUpdate(meeting);
		}
		
		//保存logo图片
		if(logoImage != null) {
			String imageUrl = this.uploadImage(meetingId, logoImage, logoImageFileName);
			PageCustom custom = this.pageCustomService.getPageCustom(meetingId, Consts.PAGE_CUSTOM_PORTAL_LOGO_IMAGE);
			if(custom == null) {
				custom = new PageCustom();
				custom.setMeeting(meeting);
				custom.setPropertyName(Consts.PAGE_CUSTOM_PORTAL_LOGO_IMAGE);
				custom.setDescription("会议portal页面的logo图片");
			}
			custom.setPropertyValue(imageUrl);
			this.pageCustomService.saveOrUpdate(custom);
		}
		
		//保存login图片
		if(loginImage != null) {
			String imageUrl = this.uploadImage(meetingId, loginImage, loginImageFileName);
			PageCustom custom = this.pageCustomService.getPageCustom(meetingId, Consts.PAGE_CUSTOM_PORTAL_LOGIN_IMAGE);
			if(custom == null) {
				custom = new PageCustom();
				custom.setMeeting(meeting);
				custom.setPropertyName(Consts.PAGE_CUSTOM_PORTAL_LOGIN_IMAGE);
				custom.setDescription("会议portal页面的登录页面的图片");
			}
			custom.setPropertyValue(imageUrl);
			this.pageCustomService.saveOrUpdate(custom);
		}
		
		//保存top图片
		if(topImage != null) {
			String imageUrl = this.uploadImage(meetingId, topImage, topImageFileName);
			PageCustom custom = this.pageCustomService.getPageCustom(meetingId, Consts.PAGE_CUSTOM_PORTAL_TOP_IMAGE);
			if(custom == null) {
				custom = new PageCustom();
				custom.setMeeting(meeting);
				custom.setPropertyName(Consts.PAGE_CUSTOM_PORTAL_TOP_IMAGE);
				custom.setDescription("会议portal页面的顶部定制图片");
			}
			custom.setPropertyValue(imageUrl);
			this.pageCustomService.saveOrUpdate(custom);
		}
		
		this.errMsg = "会议主题界面定制成功！";
		return this.goViewConfig();
	}
	
	/**
	 * 上传图片
	 * meetingId: 
	 * imageFile: 上传的图片文件，可以是logo,top,login图片
	 * imageFileName: 上传的文件名
	 * 返回保存到数据库的文件名称
	 */
	private String uploadImage(Long meetingId, File imageFile, String imageFileName) throws Exception {
		// 图片要保存到的服务器路径
		String strSaveDir = this.getDocumentRoot() + UPLOAD_IMAGE_PATH + meetingId + File.separator;
		File saveDir = new File(strSaveDir);
		if (!saveDir.exists()) {
			saveDir.mkdirs();
		}

		//生成文件名
		String nowStr = String.valueOf(new Date().getTime()); 
		String extName = FileOperatorUtil.extractFileSuffixName(imageFileName);
		String filename = nowStr + extName;

		// 要保存的目标文件
		File saveFile = new File(strSaveDir + filename);
		// 存储文件
		FileOperatorUtil.copy(imageFile, saveFile);
		log.info("uploadImage: save file name " + strSaveDir + filename);
		
		// 图片url
		String imgUrl = UPLOAD_IMAGE_PATH + meetingId + File.separator + filename;
		imgUrl = imgUrl.replaceAll("\\\\", "/"); //转换成url格式
		log.info("uploadImage: file url " + imgUrl);
		return imgUrl;
	}
	
	/*
	 * 删除图片
	 */
	public String deleteImage() throws Exception {
		log.info("deleteImage: meetingId=" + meetingId + ", imageType=" + imageType);
		
		//根据文件类型确定配置属性名称
		String propertyName = "";
		if("logoImage".equals(imageType)) {
			propertyName = Consts.PAGE_CUSTOM_PORTAL_LOGO_IMAGE;
		} else if("loginImage".equals(imageType)) {
			propertyName = Consts.PAGE_CUSTOM_PORTAL_LOGIN_IMAGE;
		} else if("topImage".equals(imageType)) {
			propertyName = Consts.PAGE_CUSTOM_PORTAL_TOP_IMAGE;
		}
		
		PageCustom custom = this.pageCustomService.getPageCustom(meetingId, propertyName);
		if(custom == null) {
			this.resultMap.put("result", false);
			return "json";
		}
		 
		//删除图片文件
		String imageName = this.getDocumentRoot() + custom.getPropertyValue();
		log.info("deleteImage: imageSavePath=" + imageName);
		new File(imageName).delete();
		
		//删除数据库记录
		this.pageCustomService.delete(custom);
		
		this.resultMap.put("result", true);
		return "json";
	}
	
	public Long getMeetingId() {
		return meetingId;
	}

	public void setMeetingId(Long meetingId) {
		this.meetingId = meetingId;
	}

	public Long getPageThemeId() {
		return pageThemeId;
	}

	public void setPageThemeId(Long pageThemeId) {
		this.pageThemeId = pageThemeId;
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

	public File getTopImage() {
		return topImage;
	}

	public void setTopImage(File topImage) {
		this.topImage = topImage;
	}

	public String getTopImageFileName() {
		return topImageFileName;
	}

	public void setTopImageFileName(String topImageFileName) {
		this.topImageFileName = topImageFileName;
	}

	public File getLoginImage() {
		return loginImage;
	}

	public void setLoginImage(File loginImage) {
		this.loginImage = loginImage;
	}

	public String getLoginImageFileName() {
		return loginImageFileName;
	}

	public void setLoginImageFileName(String loginImageFileName) {
		this.loginImageFileName = loginImageFileName;
	}

	public void setMeetingService(MeetingService meetingService) {
		this.meetingService = meetingService;
	}

	public void setPageThemeService(PageThemeService pageThemeService) {
		this.pageThemeService = pageThemeService;
	}

	public void setPageCustomService(PageCustomService pageCustomService) {
		this.pageCustomService = pageCustomService;
	}

	public String getImageType() {
		return imageType;
	}

	public void setImageType(String imageType) {
		this.imageType = imageType;
	}
	

}

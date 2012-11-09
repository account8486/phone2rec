/**
 * 会议云类型Action
 * Author: 张国敬
 * Copyrights: 版权归上海网达软件有限公司安徽分公司所有
 * Create at: Jan 13, 2012
 */
package com.wondertek.meeting.action.custom;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.wondertek.meeting.action.base.BaseAction;
import com.wondertek.meeting.common.Constants;
import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.model.BaseMenu;
import com.wondertek.meeting.model.BaseModuleTitle;
import com.wondertek.meeting.model.custom.MeetingType;
import com.wondertek.meeting.model.custom.PageTheme;
import com.wondertek.meeting.service.BaseMenuService;
import com.wondertek.meeting.service.BaseModuleTitleService;
import com.wondertek.meeting.service.custom.MeetingTypeService;
import com.wondertek.meeting.service.custom.PageThemeService;
import com.wondertek.meeting.util.FileOperatorUtil;
import com.wondertek.meeting.util.StringUtil;

public class MeetingTypeAction extends BaseAction {
	private MeetingTypeService meetingTypeService;
	private PageThemeService pageThemeService;
	private BaseMenuService baseMenuService;
	private BaseModuleTitleService baseModuleTitleService;
	
	public void setBaseModuleTitleService(BaseModuleTitleService baseModuleTitleService) {
		this.baseModuleTitleService = baseModuleTitleService;
	}

	private Integer meetingTypeId;
	private MeetingType meetingType = new MeetingType();
	private File logoImage;	//保存上传的logo图片
	private String logoImageFileName; //上传的文件名
	private BaseMenu baseMenu = new BaseMenu(); //用于保存查询数据
	private Long baseMenuId;
	private File icon; 
	private String iconFileName;
	private String iconContentType;
	
	public String list() throws Exception {
		Pager<MeetingType> pager = this.meetingTypeService.findAllMeetingTypePager(meetingType, currentPage, pageSize);
		this.setAttribute("pager", pager);
		return "listMeetingType";
	}
	
	public String addReq() throws Exception {
		if(this.meetingTypeId != null && this.meetingTypeId > 0) {
			this.meetingType = this.meetingTypeService.findById(meetingTypeId);
		} else {
			this.meetingType.setPageTheme(new PageTheme());
		}
		//查找可用的页面皮肤主题列表
		List<PageTheme> pageThemeList = this.pageThemeService.findAllUsablePageThemes();
		this.setAttribute("pageThemeList", pageThemeList);
		
		return "addMeetingType";
	}

	/*
	 * 保存会议类型
	 */
	public String save() throws Exception {
		log.info("save: meetingType=" + meetingType);
		
		//保存logo图片
		String logoFileName = "";
		if(logoImage != null) {
			String logoImageSavePath = Constants.PAGE_CUSTOM_LOGO_UPLOAD_DIR;
			log.info("save: logoImageSavePath=" + logoImageSavePath);
			File savePath = new File(logoImageSavePath);
			if(! savePath.exists()) {
				savePath.mkdirs();
			}
			
			//保存上传的logo图片到指定的目录下
			//使用系统当前时间做为文件名
			String timeStr = String.valueOf(new Date().getTime()); 
			//取文件后缀名
			String extName = this.logoImageFileName.substring(this.logoImageFileName.lastIndexOf("."));
			String savedLogoFileName = logoImageSavePath + timeStr + extName;
			log.info("save: savedLogoFileName=" + savedLogoFileName);
			
			logoFileName = timeStr + extName; //保存到数据库中的名字
			File savedLogoFile = new File(savedLogoFileName);
			FileOperatorUtil.copy(this.logoImage, savedLogoFile);
		}
		
		//保存基本信息
		if(this.meetingTypeId == null || this.meetingTypeId == 0) { //add
			//把logo文件名保存到数据库中
			this.meetingType.setLogoImage(logoFileName);
			this.meetingType.setCreateTime(new Date());
			this.meetingType.setState(1);
			this.meetingTypeService.saveOrUpdate(meetingType);
			
			//初始化基础菜单
			this.baseMenuService.initMeetingTypeMenu(meetingType.getId());
		} else {
			MeetingType mType = this.meetingTypeService.findById(meetingTypeId);
			mType.setDescription(this.meetingType.getDescription());
			mType.setName(this.meetingType.getName());
			mType.setPageTheme(this.pageThemeService.findById(this.meetingType.getPageTheme().getId()));
			if(! logoFileName.equals("")) {
				mType.setLogoImage(logoFileName);
			}
			this.meetingTypeService.saveOrUpdate(mType);
		}
		
		this.meetingType = new MeetingType(); //重置查询条件为空
		return "listAction";
	}
	
	/*
	 * 显示logo图片
	 */
	public String showLogoImage() throws Exception {
		log.info("showLogoImage: meetingTypeId=" + meetingTypeId);
		
		this.meetingType = this.meetingTypeService.findById(meetingTypeId);
		if(StringUtil.isEmpty(this.meetingType.getLogoImage())) {
			return null;
		}
		
		String logoImageName = this.meetingType.getLogoImage();
		//取文件后缀名
		String logoName = meetingType.getLogoImage();
		String extName = logoName.substring(logoName.lastIndexOf("."));
		
		//设置图片文件显示Content-Type
		this.getResponse().setContentType("image/" + extName.toLowerCase());
		
		//把图片文件数据写入response
		logoImageName = Constants.PAGE_CUSTOM_LOGO_UPLOAD_DIR + logoImageName;
		log.info("showLogoImage: logoImageName=" + logoImageName);
		
		File logoImage = new File(logoImageName);
		OutputStream outputStream = this.getResponse().getOutputStream();
		FileOperatorUtil.copyFileAsStream(logoImage, outputStream);
		outputStream.flush();
		
		return null;
	}
	
	/*
	 * 删除logo图片
	 */
	public String deleteLogoImage() throws Exception {
		log.info("deleteLogoImage: meetingTypeId=" + meetingTypeId);
		this.meetingType = this.meetingTypeService.findById(meetingTypeId);
		if(StringUtil.isEmpty(this.meetingType.getLogoImage())) {
			this.resultMap.put("result", false);
			return "json";
		}
		
		//删除图片文件
		String logoImageName = Constants.PAGE_CUSTOM_LOGO_UPLOAD_DIR +
			this.meetingType.getLogoImage();
		log.info("deleteLogoImage: logoImageSavePath=" + logoImageName);
		File logoImage = new File(logoImageName);
		logoImage.delete();
		
		//删除数据库记录
		this.meetingType.setLogoImage(null);
		this.meetingTypeService.saveOrUpdate(meetingType);
		this.resultMap.put("result", true);
		return "json";
	}
	
	/*
	 * 删除
	 */
	public String delete() throws Exception {
		log.info("delete: meetingTypeId=" + meetingTypeId);
		
		//如果设定了logo图片，则先删除图片
		this.meetingType = this.meetingTypeService.findById(meetingTypeId);
		if(! StringUtil.isEmpty(this.meetingType.getLogoImage())) {
			//删除图片文件
			String logoImageName = Constants.PAGE_CUSTOM_LOGO_UPLOAD_DIR +
				this.meetingType.getLogoImage();
			log.info("deleteLogoImage: logoImageSavePath=" + logoImageName);
			File logoImage = new File(logoImageName);
			logoImage.delete();
		}
		
		//删除数据库记录
		this.meetingTypeService.delete(meetingType);
		this.meetingType = new MeetingType(); //重置查询条件为空
		return this.list();
	}
	
	/**
	 * 查看关联的基础菜单
	 */
	public String listMenu() throws Exception {
		this.meetingType = this.meetingTypeService.findById(meetingTypeId);
		Pager<BaseMenu> pager = this.baseMenuService.findAllValidBaseMenuPages(meetingTypeId, baseMenu, currentPage, pageSize);
		this.setAttribute("pager", pager);
		return "listBaseMenu";
	}
	
	/**
	 * 初始化关联的基础菜单
	 */
	public String initMenu() throws Exception {
		this.baseMenuService.initMeetingTypeMenu(meetingTypeId);
		this.baseMenu = new BaseMenu(); //重置查询条件
		return this.listMenu();
	}
	
	/**
	 * 请求编辑基础菜单
	 */
	public String editMenuReq() throws Exception {
		this.baseMenu = this.baseMenuService.findById(baseMenuId);
		
		//
		BaseModuleTitle baseModuleTitle = new BaseModuleTitle();
		baseModuleTitle.setBaseMenuId(baseMenuId);
		baseModuleTitle.setMeetingTypeId(baseMenu.getMeetingType().getId());
		baseModuleTitle.setTerminalType(baseMenu.getTerminalType());
		List<BaseModuleTitle> baseModuleTitleList = baseModuleTitleService.query(baseModuleTitle);
		
		this.setAttribute("baseModuleTitleList", baseModuleTitleList);
		return "editBaseMenu";
	}
	
	/**
	 * 编辑基础菜单
	 */
	public String editMenu() throws Exception {
		log.debug("editMenu: meetingTypeId=" + meetingTypeId + ", baseMenuId=" + baseMenuId);
		BaseMenu menu = baseMenuService.findById(baseMenuId);

		String name = this.baseMenu.getName();
		String description = this.baseMenu.getDescription();
		String contentUrl = this.baseMenu.getContentUrl();
		Integer defaultSortCode = this.baseMenu.getDefaultSortCode();

		if (StringUtil.isNotEmpty(name)) {
			menu.setName(name);
		}
		if (StringUtil.isNotEmpty(description)) {
			menu.setDescription(description);
		}

		if (StringUtil.isNotEmpty(contentUrl)) {
			menu.setContentUrl(contentUrl);
		}

		if (defaultSortCode != null) {
			menu.setDefaultSortCode(defaultSortCode);
		}

		menu.setModifyTime(new Date());
		
		//上传菜单图标
		try {
			String imgUrl = uploadImg();
			if(imgUrl!=null){
				menu.setImgUrl(imgUrl);
			}
			log.debug("图片上传url为：{}",menu.getImgUrl());
		} catch(Exception e) {
			log.error("editMenu: uploadImg error:" + e.getMessage());
			this.baseMenu = baseMenuService.findById(baseMenuId);
			return "editBaseMenu";
		}
		
		baseMenuService.saveOrUpdate(menu);
		
		//更新子页面标题内容 add by chengfeng 2012-2-3
		String[] baseModuleTitleIds =  this.getRequest().getParameterValues("baseModuleTitleId");
		String[] baseModuleTitles =  this.getRequest().getParameterValues("baseModuleTitle");
		for(int i=0;baseModuleTitleIds!=null&&i<baseModuleTitleIds.length;i++){
			BaseModuleTitle baseModuleTitle = baseModuleTitleService.findById(Long.valueOf(baseModuleTitleIds[i]));
			baseModuleTitle.setTitleName(baseModuleTitles[i]);
			baseModuleTitleService.modify(baseModuleTitle);
		}
		//更新子页面标题内容 结束 add by chengfeng 2012-2-3
		
		this.baseMenu = new BaseMenu(); //重置查询条件
		return this.listMenu();
	}
	
	private String uploadImg() throws Exception{
		if(icon != null){
			log.debug("开始保存菜单图片");
			// 获取图片后缀名
			String partRightType = iconFileName.substring(iconFileName.lastIndexOf("."));
			log.debug("iconContentType=[{}],partRightType=[{}]",iconContentType,partRightType);
			// 判断图片的格式
			if (!checkImageType(partRightType)) {
				String alt_msg = "图片格式只能是GIF, jpeg, PNG, JPG!";
				getRequest().setAttribute("retMsg", alt_msg);
				throw new Exception("图片格式只能是GIF, jpeg, PNG, JPG!");
			} else if(icon.length() > 30000){
				log.debug("icon size = {}",icon.length());
				String alt_msg = "图片大小必须小于30KB";
				getRequest().setAttribute("retMsg", alt_msg);
				throw new Exception("图片大小必须小于30KB!");
			}else{
					iconFileName =  iconFileName.substring(0,iconFileName.lastIndexOf("."))+"_"+System.currentTimeMillis() + partRightType;

//					String webRoot = getSession().getServletContext().getRealPath("");
					String webRoot = getDocumentRoot();
					String folderdir = webRoot +Constants.UPLOAD_ICONS_PATH;  
			        if (log.isDebugEnabled()) {  
			            log.debug("folderdir" + folderdir);  
			        }  
			        File floder = new File(folderdir);  
			        if (!floder.exists()) {  
			            if (!floder.mkdirs()) {  
			                log.error("创建文件夹出错！path=" + folderdir);  
			                getRequest().setAttribute("retMsg", "创建文件夹出错！path=" + folderdir);
							throw new Exception("创建文件夹出错！path=" + folderdir);
			            }  
			        }
			        FileOutputStream fos = new FileOutputStream(new File(floder , iconFileName));
					FileInputStream fis = new FileInputStream(getIcon());
					byte[] buffer = new byte[1024];
					int len = 0;
					while ((len = fis.read(buffer)) > 0) {
						fos.write(buffer, 0, len);
					}
					fos.close();
					fis.close();

				String path = getServerUrl()
						+ Constants.UPLOAD_ICONS_PATH+  "/" + iconFileName;
				log.debug("File.separator={};icon path {}",File.separator,path);
				//保存图片完毕
				return path;
			}
		}
		return null;
	}
	
	private boolean checkImageType(String type){
		log.debug("image file type {}",type);
		if(StringUtil.isEmpty(type)){
			return false;
		}
		type = type.toLowerCase();
		if(imgFileExtSet.contains(type)){
			return true;
		}
		return false;
	}
	
	 /**
     * 图片文件后缀
     */
    protected static Set<String> imgFileExtSet = new HashSet<String>();
    static{
    	imgFileExtSet.add(".png");
    	imgFileExtSet.add(".jpg");
    	imgFileExtSet.add(".jpeg");
    	imgFileExtSet.add(".gif");
    }
    
	public Integer getMeetingTypeId() {
		return meetingTypeId;
	}

	public void setMeetingTypeId(Integer meetingTypeId) {
		this.meetingTypeId = meetingTypeId;
	}

	public MeetingType getMeetingType() {
		return meetingType;
	}

	public void setMeetingType(MeetingType meetingType) {
		this.meetingType = meetingType;
	}

	public void setPageThemeService(PageThemeService pageThemeService) {
		this.pageThemeService = pageThemeService;
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

	public void setMeetingTypeService(MeetingTypeService meetingTypeService) {
		this.meetingTypeService = meetingTypeService;
	}

	public void setBaseMenuService(BaseMenuService baseMenuService) {
		this.baseMenuService = baseMenuService;
	}

	public BaseMenu getBaseMenu() {
		return baseMenu;
	}

	public void setBaseMenu(BaseMenu baseMenu) {
		this.baseMenu = baseMenu;
	}

	public Long getBaseMenuId() {
		return baseMenuId;
	}

	public void setBaseMenuId(Long baseMenuId) {
		this.baseMenuId = baseMenuId;
	}

	public File getIcon() {
		return icon;
	}

	public void setIcon(File icon) {
		this.icon = icon;
	}

	public String getIconFileName() {
		return iconFileName;
	}

	public void setIconFileName(String iconFileName) {
		this.iconFileName = iconFileName;
	}

	public String getIconContentType() {
		return iconContentType;
	}

	public void setIconContentType(String iconContentType) {
		this.iconContentType = iconContentType;
	}

}

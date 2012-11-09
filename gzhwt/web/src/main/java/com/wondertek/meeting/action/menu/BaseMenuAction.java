package com.wondertek.meeting.action.menu;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.wondertek.meeting.action.base.BaseAction;
import com.wondertek.meeting.common.Constants;
import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.exception.HibernateDaoSupportException;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.ClientMenu;
import com.wondertek.meeting.model.Meeting;
import com.wondertek.meeting.model.MeetingModuleTitle;
import com.wondertek.meeting.service.BaseMenuService;
import com.wondertek.meeting.service.ClientMenuService;
import com.wondertek.meeting.service.MeetingModuleTitleService;
import com.wondertek.meeting.service.MeetingService;
import com.wondertek.meeting.util.StringUtil;

public class BaseMenuAction extends BaseAction {
	private static final String FILESIZE_EXCEED_MSG = "图片大小必须小于30KB";
	private static final String FILETYPE_FILTER_MSG = "图片格式只能是GIF, jpeg, PNG, JPG!";
	private static final int FILESIZE_LIMIT = 30*1024;

	private static final long serialVersionUID = 6730855512805846535L;
	ClientMenuService clientMenuService;
	BaseMenuService baseMenuService;
	MeetingService meetingService;
	private MeetingModuleTitleService meetingModuleTitleService;
	public void setMeetingModuleTitleService(MeetingModuleTitleService meetingModuleTitleService) {
		this.meetingModuleTitleService = meetingModuleTitleService;
	}

	private File icon; 
	private String iconFileName;
	private String iconContentType;

	public BaseMenuService getBaseMenuService() {
		return baseMenuService;
	}

	public void setBaseMenuService(BaseMenuService baseMenuService) {
		this.baseMenuService = baseMenuService;
	}

	private Long meetingId;
	public Long getMeetingId() {
		return meetingId;
	}

	public void setMeetingId(Long meetingId) {
		this.meetingId = meetingId;
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
	
	private String menuId;
	/**
	 * @return the menuId
	 */
	public String getMenuId() {
		return menuId;
	}

	/**
	 * @param menuId the menuId to set
	 */
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	/**
	 * 获取列表
	 * 
	 * @return
	 * @throws HibernateDaoSupportException
	 */
	public String getBaseMenuPages() throws HibernateDaoSupportException {
		String forward = SUCCESS;
		String description = getRequest().getParameter("description");
		String cPage = getRequest().getParameter("currentPage");
		String terminalType = getRequest().getParameter("terminalType");
		String name = getRequest().getParameter("name");
		String meetingId=getRequest().getParameter("meetingId");

		if (cPage != null && !"".equals(cPage)) {
			currentPage = Integer.parseInt(cPage);
		}

		Pager<ClientMenu> pager = clientMenuService.getBaseMenuPages(
				currentPage, pageSize, description, terminalType, name,meetingId);

		getRequest().setAttribute("pager", pager);// 返回
		getRequest().setAttribute("description", description);
		getRequest().setAttribute("terminalType", terminalType);
		getRequest().setAttribute("name", name);
		getRequest().setAttribute("meetingId", meetingId);

		return forward;
	}

	/**
	 * 查询对应的BASE MENU
	 * 
	 * @return
	 * @throws ServiceException
	 * @throws NumberFormatException
	 */
	public String getBaseMenuById() throws NumberFormatException,
			ServiceException {
		String forward = SUCCESS;
		String id = getRequest().getParameter("id");
		ClientMenu clientMenu = clientMenuService.findById(Long.valueOf(id));

		getRequest().setAttribute("clientMenu", clientMenu);
		
		//获取子页面标题数据 add by chengfeng 2012-2-3
		MeetingModuleTitle meetingModuleTitle = new MeetingModuleTitle();
		meetingModuleTitle.setMeetingId(clientMenu.getMeetingId());
		meetingModuleTitle.setBaseMenuId(clientMenu.getBaseMenuId());
		meetingModuleTitle.setTerminalType(clientMenu.getTerminalType());
		List<MeetingModuleTitle> meetingModuleTitleList = meetingModuleTitleService.query(meetingModuleTitle);
		this.setAttribute("meetingModuleTitleList", meetingModuleTitleList);
		//获取子页面标题数据 结束 add by chengfeng 2012-2-3
		
		final String errorMsg = getRequest().getParameter("errorMsg");
		if (StringUtils.isNotEmpty(errorMsg)) {
			getRequest().setAttribute("errorMsg", errorMsg);
		}
		
		return forward;
	}

	/**
	 * 更新对应的BASE_MENU
	 * 
	 * @return
	 * @throws Exception 
	 */
	public String updateBaseMenuById() throws Exception {
		String id = getRequest().getParameter("id");
		ClientMenu clientMenu = clientMenuService.findById(Long.valueOf(id));

		String name = getRequest().getParameter("name");
		String description = getRequest().getParameter("description");
		//String imgUrl = getRequest().getParameter("imgUrl");
		String contentUrl = getRequest().getParameter("contentUrl");
		String defaultSortCode = getRequest().getParameter("defaultSortCode");

		if (StringUtil.isNotEmpty(name)) {
			clientMenu.setName(name);
		}
		if (StringUtil.isNotEmpty(description)) {
			clientMenu.setDescription(description);
		}

		if (StringUtil.isNotEmpty(contentUrl)) {
			clientMenu.setContentUrl(contentUrl);
		}

		if (StringUtil.isNotEmpty(defaultSortCode)
				&& StringUtil.isNumber(defaultSortCode)) {
			clientMenu.setDefaultSortCode(Integer.valueOf(defaultSortCode));
		}

		clientMenu.setModifyTime(new Date());
		
		//上传菜单图标
		try {
			String imgUrl = uploadImg();
			if(imgUrl!=null){
				clientMenu.setImgUrl(imgUrl);
			}
			log.debug("图片上传url为：{}",clientMenu.getImgUrl());
		} catch (Exception e) {
			setErrMsg(e.getMessage());
			setMenuId(id);
			return ERROR;
		}
		clientMenuService.saveOrUpdate(clientMenu);
		this.setMeetingId(clientMenu.getMeetingId());
		

		//更新子页面标题内容 add by chengfeng 2012-2-3
		String[] baseModuleTitleIds =  this.getRequest().getParameterValues("meetingModuleTitleId");
		String[] baseModuleTitles =  this.getRequest().getParameterValues("meetingModuleTitle");
		
		for(int i=0;baseModuleTitleIds!=null&&i<baseModuleTitleIds.length;i++){
			MeetingModuleTitle meetingModuleTitle = meetingModuleTitleService.findById(Long.valueOf(baseModuleTitleIds[i]));
			meetingModuleTitle.setTitleName(baseModuleTitles[i]);
			meetingModuleTitleService.modify(meetingModuleTitle);
		}
		//更新子页面标题内容 结束 add by chengfeng 2012-2-3
		

		// 跳转到列表页面
		return SUCCESS;
	}
	
	
	/**
	 * 一键还原会议菜单
	 * @return
	 */
	public String resetMenuByOneKey() throws Exception {
		String forward=SUCCESS;
		//首先看MEETING_MENU
	    //通过meetingId,baseMenuId来看是否存在。
		String meetingId=getRequest().getParameter("meetingId");
		//获取基础菜单列表
		Meeting meeting = this.meetingService.findById(Long.valueOf(meetingId));
		baseMenuService.resetMeetingMenu(meeting);

		this.setMeetingId(Long.valueOf(meetingId));
		return forward;
	}

	public ClientMenuService getClientMenuService() {
		return clientMenuService;
	}

	public void setClientMenuService(ClientMenuService clientMenuService) {
		this.clientMenuService = clientMenuService;
	}

	private String uploadImg() throws Exception{
		if(icon != null){
			log.debug("开始保存菜单图片");
			// 获取图片后缀名
			String partRightType = iconFileName.substring(iconFileName.lastIndexOf("."));
			log.debug("iconContentType=[{}],partRightType=[{}]",iconContentType,partRightType);
			// 判断图片的格式
			if (!checkImageType(partRightType)) {
				throw new Exception(FILETYPE_FILTER_MSG);
			} else if(icon.length() > FILESIZE_LIMIT){
				log.debug("icon size = {}",icon.length());
				throw new Exception(FILESIZE_EXCEED_MSG);
			}else{
					iconFileName =  iconFileName.substring(0,iconFileName.lastIndexOf("."))+"_"+System.currentTimeMillis() + partRightType;
					String webRoot = getDocumentRoot();
					String folderdir = webRoot +Constants.UPLOAD_ICONS_PATH;  
			        if (log.isDebugEnabled()) {  
			            log.debug("folderdir" + folderdir);  
			        }  
			        File floder = new File(folderdir);  
			        if (!floder.exists()) {  
			            if (!floder.mkdirs()) {  
			                log.error("创建文件夹出错！path=" + folderdir);  
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

//				String path = getServerUrl()
//						+ Constants.UPLOAD_ICONS_PATH+  "/" + iconFileName;
					
				String path =Constants.UPLOAD_ICONS_PATH+  "/" + iconFileName;
					
					
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
    	imgFileExtSet.add(".bmp");
    	imgFileExtSet.add(".gif");
    }
	public MeetingService getMeetingService() {
		return meetingService;
	}

	public void setMeetingService(MeetingService meetingService) {
		this.meetingService = meetingService;
	}
}

package com.wondertek.meeting.action.meeting;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.wondertek.meeting.action.base.BaseAction;
import com.wondertek.meeting.common.Constants;
import com.wondertek.meeting.common.RetCode;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.Folder;
import com.wondertek.meeting.model.JsonContent;
import com.wondertek.meeting.model.Meeting;
import com.wondertek.meeting.model.MeetingContent;
import com.wondertek.meeting.service.MeetingContentService;
import com.wondertek.meeting.util.StringUtil;

/**
 * @author GuoXu 用于进行的编辑及展示功能 涉及表：MEETING_CONTENT 
 */
public class MeetingContentAction extends BaseAction {

	private static final long serialVersionUID = 5238421527033506779L;

	private Integer contentType;//景点、酒店、静态菜单
	
	private Long meetingId;
	
	private MeetingContent content;
	private String category[]; //web,wap,client
	private String defaultImg; //默认显示的一个图片
	private String defaultIcon; //选择默认的菜单图片
	private File icon; //上传的菜单图片
	private String iconFileName;
	
	//CKEditor上传相关参数
	private File upload;//CKEditor 上传的图片
	private String uploadFileName;
	private String type;
	private String  CKEditor;
	private String CKEditorFuncNum;
	private String langCode;//CKeditor的语言

	private MeetingContentService meetingContentService;
	
	private int CONTENT_MAX_LENGTH = 1000000;
	
	private String itemIds;//条目ID
	private Long parentId;
	private Meeting meeting;
	public Meeting getMeeting() {
		return meeting;
	}

	public void setMeeting(Meeting meeting) {
		this.meeting = meeting;
	}

	/**
	 * 获取数据
	 * 
	 * @return
	 * @throws ServiceException
	 */
	public String meetingContent() throws ServiceException {
		try {
			// 获取会议的主键
			String meetingId = getRequest().getParameter("meetingId");
			String contentType = getRequest().getParameter("contentType");
			//如果没传 默认取SESSION中的
		
			StringBuffer hql = new StringBuffer();
			hql.append(" FROM MeetingContent meetingContent where meetingContent.meetingId="
					+ meetingId
					+ " and  meetingContent.contentType="
					+ contentType);

			List<MeetingContent> meetingList = meetingContentService
					.getObjects(hql.toString());
			// 获取
			MeetingContent meetingContent = new MeetingContent();
			if (meetingList != null && meetingList.size() > 0) {
				meetingContent = meetingList.get(0);
			}

			resultMap.put("retcode", 0);
			resultMap.put("retmsg", "查询完成");
			resultMap.put("meetingContent", meetingContent);

			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}

	/**
	 * 获取列表
	 * 
	 * @return
	 */
	public String meetingContentList() {
		try {
			// 获取会议的主键
			String meetingId = getRequest().getParameter("meetingId");
			String contentType = getRequest().getParameter("contentType");
			
			//如果没传 默认取SESSION中的
			MeetingContent meetingContent = new MeetingContent();
			meetingContent.setMeetingId(Long.valueOf(meetingId));
			meetingContent.setContentType(Integer.parseInt(contentType));
			//获取列表
			List<MeetingContent> meetingContentList = meetingContentService
					.getMeetingContentList(meetingContent);

			resultMap.put("retcode", 0);
			resultMap.put("retmsg", "查询完成");
			resultMap.put("meetingContentList", meetingContentList);

			return SUCCESS;
		} catch (Exception e) {
			resultMap.put("retcode", 1);
			resultMap.put("retmsg", "查询失败,系统忙，请稍后再试");
			e.printStackTrace();
			return ERROR;
		}
	}
	
	/** 按类型查询信息*/
	public String listMeetingContent(){
		try {
			
			log.debug("listMeetingContent meetingId = {} , contentType = {}",new String[]{String.valueOf(meetingId),String.valueOf(contentType)});
			log.debug("meetingId {}, contentType {}, currentPage {}, pageSize {}",new String[]{""+meetingId, ""+contentType, ""+currentPage, ""+pageSize});
			//验证meetingId 和 type
			getRequest().setAttribute("meetingId", meetingId);
			getRequest().setAttribute("contentType", contentType);
			getRequest().setAttribute("pager", meetingContentService.findMeetingContentPager(meetingId, contentType, currentPage, pageSize, ""));
		} catch (Exception e) {
			log.error("listMeetingContent error ",e);
		}
		return SUCCESS;
	}
	
	/**获取默认的菜单图片*/
	public List<String> getDefaultMenuIconList(){
		String basePath = getServerUrl();
		//菜单默认图片放置的目录
		final String defaultPath = "images/default_menu_icon/";
		//设置一个默认显示的图片
		defaultImg = basePath + defaultPath+"default.png";
//		final String rootDir = getSession().getServletContext().getRealPath(defaultPath);
		final String rootDir = getDocumentRoot()+defaultPath;
		List<String> icons = new ArrayList<String>();
		File root = new File(rootDir);
		if(root!=null && root.exists()){
			File[] filesOrDirs = root.listFiles();
			for (int i = 0; i < filesOrDirs.length; i++) {
				if (!filesOrDirs[i].isDirectory()
						&& filesOrDirs[i].getName().endsWith(".png")) {
					icons.add(basePath + defaultPath + filesOrDirs[i].getName());
				}
			}
		}
		
		return icons;
	}
	
	
	
	public String gotoAdd(){
		try {
			log.debug("gotoAdd contentType = {},meetingId = {}",new String[]{""+contentType,""+meetingId});
			getRequest().setAttribute("defaultIcons", getDefaultMenuIconList());
			content = new MeetingContent();
			content.setContentType(contentType);
			content.setMeetingId(meetingId);
		} catch (Exception e) {
			log.error("goto add content error",e);
		}
		return SUCCESS;
	}
	
	/**
	 * 添加信息
	 */
	public String add(){
		try {
			log.debug("current user {}",getAdminUserFromSession());
			log.debug("meetingContent: "+content);
			content.setCreateTime(new Date());
			content.setModifyTime(new Date());
			if(!"1".equals(content.getIsList())){
				log.debug("保存信息数据长度 {}",content.getContent().length());
				
				if(content.getContent().length() > CONTENT_MAX_LENGTH){
					log.warn("信息数据长度过长 {}",content.getContent().length());
					getRequest().setAttribute("retMsg", "内容太长，最多可以填写1M数据，大约50万个汉字，图片请使用工具栏中的图片上传工具。");
					return INPUT;
				}
			}
			content.setCreator(getAdminUserFromSession());
			Long mid = 0L;
			if(null != content.getContentType() && 3 == content.getContentType()){
				//对菜单数据进行处理
				String result = processCustomeMenu();
				if(!SUCCESS.equalsIgnoreCase(result)){
					return result;
				}
				mid = meetingContentService.saveMeetingContent(content,getBasePath(),itemIds);
			}else{
				mid = meetingContentService.add(content);
			}
			
			log.debug("content id====: " + mid);
			content.setId(mid);
			meetingId = content.getMeetingId();
			contentType = content.getContentType();
			getRequest().setAttribute("retMsg", "保存成功");
			return listMeetingContent();
		} catch (Exception e) {
			getRequest().setAttribute("retMsg", "保存失败");
			log.error("添加失败,",e);
			return INPUT;
		}
	}
	
	/**
	 * 添加信息条目
	 */
	public String addItem(){
		try {
			log.debug("current user {}",getAdminUserFromSession());
			log.debug("meetingContent: "+content);
			content.setCreateTime(new Date());
			content.setModifyTime(new Date());
			log.debug("保存信息数据长度 {}",content.getContent().length());
			
			if(content.getContent().length() > CONTENT_MAX_LENGTH){
				log.warn("信息数据长度过长 {}",content.getContent().length());
				getRequest().setAttribute("retMsg", "内容太长，最多可以填写1M数据，大约50万个汉字，图片请使用工具栏中的图片上传工具。");
				getRequest().setAttribute("retCode", RetCode.FAIL);
				return SUCCESS;
			}
			content.setCreator(getAdminUserFromSession());
			Long mid = meetingContentService.add(content);
			log.debug("content id====: " + mid);
			content.setId(mid);
			meetingId = content.getMeetingId();
			contentType = content.getContentType();
			getRequest().setAttribute("content", content);
			getRequest().setAttribute("retMsg", "保存成功");
			getRequest().setAttribute("retCode", RetCode.SUCCESS);
			return SUCCESS;
		} catch (Exception e) {
			getRequest().setAttribute("retMsg", "保存失败");
			getRequest().setAttribute("retCode", RetCode.FAIL);
			log.error("添加失败,",e);
			return SUCCESS;
		}
	}

	/**对自定义菜单的信息进行处理*/
	private String processCustomeMenu() throws FileNotFoundException, IOException {
		log.debug("验证自定义菜单配置参数");
		//选择菜单类型了吗
		log.debug("category[] = {} {} {}",category);
		getRequest().setAttribute("defaultIcons", getDefaultMenuIconList());
		//验证菜单类型
			if(category == null || category.length == 0){
				getRequest().setAttribute("retMsg", "请选择菜单类型");
				return INPUT;
			}
			
			//为菜单类型赋值
			Integer categoryByte = 0;
			if(category != null && category.length > 0){
				for(String c:category){
					if(c.equalsIgnoreCase("touch")){
						categoryByte |= 8; //设置选择了TOUCH菜单的标志位"1000"
					}
					if(c.equalsIgnoreCase("web")){
						categoryByte |= 4; //设置选择了web菜单的标志位"100"
					}
					if(c.equalsIgnoreCase("wap")){
						categoryByte |= 2;//设置选择了wap菜单的标志位"010"
					}
					if(c.equalsIgnoreCase("client")){
						categoryByte |= 1;//设置选择了clinet菜单的标志位"001"
					}
				}
				log.debug("category = {}",categoryByte);
				content.setCategory(categoryByte);
			}
		
		
		if(!StringUtil.isEmpty(content.getComments()) && content.getComments().length() > 100){
			getRequest().setAttribute("retMsg", "备注长度不能超过100个字");
			return INPUT;
		}
		
		//上传图片了吗？
		log.debug("defaultIcon {}",defaultIcon);
		log.debug("icon {}",icon);
		if(icon != null){
			log.debug("开始保存菜单图片");
			// 获取图片后缀名
			String partRightType = iconFileName.substring(iconFileName.lastIndexOf("."));
			// 判断图片的格式
			if (!checkImageType(partRightType)) {
				String alt_msg = "图片格式只能是GIF, jpeg, PNG, JPG!";
				getRequest().setAttribute("retMsg", alt_msg);
				return INPUT;
			} else if(icon.length() > 30000){
				log.debug("icon size = {}",icon.length());
				String alt_msg = "图片大小必须小于30KB";
				getRequest().setAttribute("retMsg", alt_msg);
				return INPUT;
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
			                return INPUT;  
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
//						+ Constants.UPLOAD_ICONS_PATH+ "/" + iconFileName;
				String path = Constants.UPLOAD_ICONS_PATH+ "/" + iconFileName;
				log.debug("icon path {}",path);
				//保存图片完毕
				content.setIcon(path);
			}
		}else if(!StringUtil.isEmpty(defaultIcon)){
			content.setIcon(defaultIcon.replace(getServerUrl(), ""));
		}
		//菜单排序序号
//		log.debug("sort number {}",content.getSortNo());
		return SUCCESS;
	}
	
	/**针对各终端查询content数据进行处理*/
	public String getMeetingContentById(){
		try {
			String returnType = this.getParameter("returnType");
			if(content!=null){
				log.debug("content id = {}",content.getId());
				log.debug("returnType = {}",returnType);
				content =  meetingContentService.findById(content.getId());
				//判断跳转页面，如果是在会议列表点击编辑，则跳转到admin的portal页面
				if(returnType!=null&&returnType.equalsIgnoreCase("modify"))
				{
					getRequest().setAttribute("defaultIcons", getDefaultMenuIconList());
					
					if(!StringUtil.isEmpty(content.getIcon())){
						content.setIcon(getServerUrl()+content.getIcon());
					}
					
					if("1".equals(content.getIsList())){
						//把属于该list的内容都查出来
						List<MeetingContent> list = meetingContentService.findContentItem(content.getId());
						StringBuffer sb = new StringBuffer();
						for(MeetingContent mc:list){
							sb.append(mc.getId()).append(",");
						}
						
						itemIds = sb.toString();
						if(itemIds.endsWith(",")){
							itemIds = itemIds.substring(0, itemIds.length()-1);
						}
						log.debug("itemIds = {}",itemIds);
						return "modifyList";//修改列表
					}
					
					if(4 == content.getContentType()){
						return "modifyItem";
					}
					
					return "modify";//后台修改
				}
				
				if("1".equals(content.getIsList())){
					//把属于该list的内容都查出来
					List<MeetingContent> list = meetingContentService.findContentItem(content.getId());
					content.setContent(convertContent(list));
				}
				meetingId = content.getMeetingId();
				this.setAttribute("meetingId", meetingId);
				meeting=new Meeting();
				this.meeting.setId(meetingId);
				
			}
		} catch (Exception e) {
			log.error("查询信息失败,",e);
			return INPUT;
		}
		return SUCCESS;
	}
	
	/**将list下的item的标题转换为链接形式的内容*/
	private String convertContent(List<MeetingContent> list){
		
		String returnType = this.getParameter("returnType");
		String uri = getRequest().getRequestURI();
		StringBuffer sb = new StringBuffer("<br/>");
		for(MeetingContent content : list){
			sb.append("&nbsp;&nbsp;&nbsp;&nbsp;<a target='_blank' href='"+uri+"?returnType="+returnType+"&content.id="+content.getId()+"&meeting.id="+content.getMeetingId()+"' title='"+content.getContentTitle()+"'>"+content.getContentTitle()+"</a><br/><br/>");
		}
		log.debug(sb.toString());
		return sb.toString();
	}
	
	public String listContentItem(){
		try {
			List<JsonContent> result = new ArrayList<JsonContent>();
			log.debug("itemIds = {}",itemIds);
			if(itemIds.length() > 0){
				List<MeetingContent> list = meetingContentService.findContentItem(itemIds);
				for(MeetingContent mc:list){
					JsonContent content = new JsonContent();
					content.setId(mc.getId());
					content.setContentTitle(mc.getContentTitle());
					content.setSortNo(mc.getSortNo());
					result.add(content);
				}
			}
			resultMap.put("result", result);
			resultMap.put("retmsg","查询成功");
			resultMap.put("retcode",RetCode.SUCCESS);
		} catch (Exception e) {
			log.error("查询信息失败,",e);
			resultMap.put("retmsg","查询失败");
			resultMap.put("retcode",RetCode.FAIL);
		}
		return json2Resp(resultMap);
	}
	
	public String modifyItem(){
		try {
			log.debug("current user {}",getAdminUserIdFromSession());
			if(!"1".equals(content.getIsList())){
				log.debug("更新信息 {}",content);
				log.debug("更新信息数据长度 {}",content.getContent().length());
				
				if(content.getContent().length() > CONTENT_MAX_LENGTH){
					log.warn("信息数据长度过长 {}",content.getContent().length());
					getRequest().setAttribute("retMsg", "内容太长，最多可以填写1M数据，大约50万个汉字，图片请使用工具栏中的图片上传工具。");
					getRequest().setAttribute("retCode", RetCode.FAIL);
					return SUCCESS;
				}
			}
			content.setModifier(getAdminUserFromSession());
			content.setModifyTime(new Date());
			
			MeetingContent old = meetingContentService.findById(content.getId());
			
			if(old == null ){
				log.warn("找不到meetingContent信息");
				getRequest().setAttribute("retMsg", "信息已不存在");
				getRequest().setAttribute("retCode", RetCode.FAIL);
				return SUCCESS;
			}
			
			old.setContentTitle(content.getContentTitle());
			old.setContent(content.getContent());
			old.setSortNo(content.getSortNo());
			meetingContentService.modify(old);
			
			meetingId = content.getMeetingId();
			contentType = content.getContentType();
			getRequest().setAttribute("from", "modify");
			getRequest().setAttribute("content", content);
			getRequest().setAttribute("retMsg", "保存成功");
			getRequest().setAttribute("retCode", RetCode.SUCCESS);
			return SUCCESS;
		} catch (Exception e) {
			log.error("更新信息失败,",e);
			getRequest().setAttribute("retMsg", "保存失败");
			getRequest().setAttribute("retCode", RetCode.FAIL);
			return INPUT;
		}
	}
	
	public String modify(){
		try {
			log.debug("current user {}",getAdminUserIdFromSession());
			if(!"1".equals(content.getIsList())){
				log.debug("更新信息 {}",content);
				log.debug("更新信息数据长度 {}",content.getContent().length());
				
				if(content.getContent().length() > CONTENT_MAX_LENGTH){
					log.warn("信息数据长度过长 {}",content.getContent().length());
					getRequest().setAttribute("retMsg", "内容太长，最多可以填写1M数据，大约50万个汉字，图片请使用工具栏中的图片上传工具。");
					return INPUT;
				}
			}
			content.setModifier(getAdminUserFromSession());
			content.setModifyTime(new Date());
			
			MeetingContent old = meetingContentService.findById(content.getId());
			
			if(old == null ){
				log.warn("找不到meetingContent信息");
				getRequest().setAttribute("retMsg", "信息已不存在");
				return INPUT;
			}
			
			//Long mid = 0L;
			if(null != content.getContentType() && 3 == content.getContentType()){
				if(!StringUtil.isEmpty(content.getComments()) && content.getComments().length() > 100){
					getRequest().setAttribute("retMsg", "备注长度不能超过100个字");
					return INPUT;
				}
				old.setComments(content.getComments());
				
				//对菜单类型和图标进行设置
				//验证图片和选择的菜单项
				String result = processCustomeMenu();
				if(!SUCCESS.equalsIgnoreCase(result)){
					return result;
				}
				
				old.setCategory(content.getCategory());
				if(!StringUtil.isEmpty(content.getIcon())){
					old.setIcon(content.getIcon());
				}
			}
			
			old.setContentTitle(content.getContentTitle());
			old.setContent(content.getContent());
			
			meetingContentService.modifyMeetingContent(old,getBasePath(),itemIds);
			
			meetingId = content.getMeetingId();
			contentType = content.getContentType();
			getRequest().setAttribute("retMsg", "保存成功");
			return listMeetingContent();
		} catch (Exception e) {
			log.error("更新信息失败,",e);
			getRequest().setAttribute("retMsg", "保存失败");
			return INPUT;
		}
	}
	
	public String delete(){
		try {
			log.debug("删除信息 id = {}",content.getId());
			meetingContentService.deleteContent(content);
			resultMap.put("retmsg", "成功删除信息");
			resultMap.put("retcode", RetCode.SUCCESS);
		} catch (Exception e) {
			log.error("删除信息失败,",e);
			resultMap.put("retmsg", "删除信息失败");
			resultMap.put("retcode", RetCode.FAIL);
		}
		return SUCCESS;
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
	
	// CkEditor 上传图片
	@Action(value = "/ckUploadImage")
	public String uploadImages() throws Exception {
		
		try {
			log.debug(
							"CKEditor uploadImages type {} CKEditor {} CKEditorFuncNum {} langCode {} type {}",
							new String[] { type, CKEditor, CKEditorFuncNum,
									langCode, type });
			
			HttpServletRequest request = ServletActionContext.getRequest();
			FileOutputStream fos;
//			String webRoot = request.getSession().getServletContext().getRealPath(
//					"");
			String webRoot = getDocumentRoot();
			// 获取图片后缀名
			String partRightType = uploadFileName.substring(uploadFileName
					.lastIndexOf("."));
			String CKEditorFuncNum = request.getParameter("CKEditorFuncNum");
			// 判断图片的格式
			if (!checkImageType(partRightType)) {
				String path = "";
				String alt_msg = "图片格式只能是 GIF, jpeg, PNG, JPG!";
				str2Resp("<script type='text/javascript'>window.parent.CKEDITOR.tools.callFunction("
						+ CKEditorFuncNum
						+ ", '"
						+ path
						+ "' , '"
						+ alt_msg
						+ "');</script>");
			} else {

					uploadFileName =  uploadFileName.substring(0,uploadFileName.lastIndexOf("."))+"_"+System.currentTimeMillis() + partRightType;

					File uploadFilePath = buildFolder(getRequest());
					
					if (uploadFilePath.exists() == false) {
						uploadFilePath.mkdirs();
						log.debug("路径不存在,但是已经成功创建了" + uploadFilePath.getPath());
					}
					fos = new FileOutputStream(new File(uploadFilePath.getPath() , uploadFileName));
					FileInputStream fis = new FileInputStream(getUpload());
					byte[] buffer = new byte[1024];
					int len = 0;
					while ((len = fis.read(buffer)) > 0) {
						fos.write(buffer, 0, len);
					}
					fos.close();
					fis.close();

				// String path = "http://" + request.getServerName() + ":"
				// + request.getServerPort() + request.getContextPath()
				// + Constants.UPLOAD_IMAGES_PATH + uploadFileName;

				log.debug("uploadFilePath.getPath() = "+uploadFilePath.getPath());	
				//不替换，控件不失败路径地址
				String uploadAbsolutePath = uploadFilePath.getPath().replace(webRoot, "").replace("\\", "/");
				log.debug("after replace uploadFilePath uploadAbsolutePath = "+uploadAbsolutePath);	
				
//				String path = getBasePath()
//				+ uploadAbsolutePath+ "/" + uploadFileName;
				String path = getServerUrl()
						+ uploadAbsolutePath+ "/" + uploadFileName;
				log.debug("return path {}",path);
				String alt_msg = "";
				str2Resp("<script type='text/javascript'>window.parent.CKEDITOR.tools.callFunction("
						+ CKEditorFuncNum
						+ ", '"
						+ path
						+ "' , '"
						+ alt_msg
						+ "');</script>");

			}
		} catch (Exception e) {
			log.error("图片上传失败",e);
			String path = "";
			String alt_msg = "图片上传失败";
			str2Resp("<script type='text/javascript'>window.parent.CKEDITOR.tools.callFunction("
					+ CKEditorFuncNum
					+ ", '"
					+ path
					+ "' , '"
					+ alt_msg
					+ "');</script>");
		}

		return null;
	}
	
	
    /** ~~~ 系统默认建立和使用的以时间字符串作为文件名称的时间格式*/  
    private static final String DEFAULT_SUB_FOLDER_FORMAT_AUTO = "yyyyMMdd";  
    /** ~~~ 这里扩充一下格式，防止手动建立的不统一*/  
    private static final String DEFAULT_SUB_FOLDER_FORMAT_NO_AUTO = "yyyy-MM-dd";  
  
  
    /** 
     * 创建目录 
     *  
     * @return 
     */  
    private File buildFolder(HttpServletRequest request) {  
//        String realPath = request.getSession().getServletContext()  
//                .getRealPath(""); 
    	
        String realPath = getDocumentRoot(); 
  
        String folderdir = realPath +Constants.UPLOAD_IMAGES_PATH;  
        if (log.isDebugEnabled()) {  
            log.debug("folderdir" + folderdir);  
        }  
  
        if (StringUtils.isBlank(folderdir)) {  
            log.error("路径错误:" + folderdir);  
            return null;  
        }  
  
        File floder = new File(folderdir);  
        if (!floder.exists()) {  
            if (!floder.mkdirs()) {  
                log.error("创建文件夹出错！path=" + folderdir);  
                return null;  
            }  
  
        }  
        // 再往下的文件夹都是以时间字符串来命名的，所以获取最新时间的文件夹即可  
        String[] files = floder.list();  
        if (null != files && 0 < files.length) {  
            // 含有子文件夹，则获取最新的一个  
            Date oldDate = null;  
            int index = -1;  
            for (int i = 0; i < files.length; i++) {  
                String fileName = files[i];  
  
                try {  
                    Date thisDate = DateUtils.parseDate(fileName, new String[] {  
                            DEFAULT_SUB_FOLDER_FORMAT_AUTO, DEFAULT_SUB_FOLDER_FORMAT_NO_AUTO });  
                    if (oldDate == null) {  
                        oldDate = thisDate;  
                        index = i;  
                    } else {  
                        if (thisDate.after(oldDate)) {  
                            // 保存最新的时间和数组中的下标  
                            oldDate = thisDate;  
                            index = i;  
                        }  
                    }  
                } catch (ParseException e) {  
                    // 这里异常吃掉，不用做什么，如果解析失败，会建立新的文件夹，防止人为的建立文件夹导致的异常。  
                }  
            }// for  
  
            // 判断当前最新的文件夹下是否已经存在了最大数目的图片  
            if (null != oldDate && -1 != index) {  
                File pointfloder = new File(folderdir + File.separator  
                        + files[index]);  
                if (!pointfloder.exists()) {  
                    if (!pointfloder.mkdirs()) {  
                        log.error("创建文件夹出错！path=" + folderdir);  
                        return null;  
                    }  
                }  
  
                // 如果文件夹下的文件超过了最大值，那么也需要新建一个文件夹  
                String[] pointfloderFiles = pointfloder.list();  
                if (null != pointfloderFiles  
                        && 50 < pointfloderFiles.length) {  
                    return buildNewFile(folderdir);  
                }  
  
                return pointfloder;  
            }  
              
            // 查找当前子文件夹失败，新建一个  
            return buildNewFile(folderdir);  
        } else {  
            // 不含有子文件夹，新建一个，通常系统首次上传会有这个情况  
            return buildNewFile(folderdir);  
        }  
  
    }  
      
    /** 
     * 创建一个新文件 
     * @param path 
     * @return 
     */  
    private File buildNewFile(String path){  
        // 不含有子文件夹，新建一个，通常系统首次上传会有这个情况  
        File newFile = buildFileBySysTime(path);  
        if (null == newFile) {  
            log.error("创建文件夹失败！newFile=" + newFile);  
        }  
  
        return newFile;  
    }  
  
    /** 
     * 根据当前的时间建立文件夹，时间格式yyyyMMdd 
     *  
     * @param path 
     * @return 
     */  
    private File buildFileBySysTime(String path) {  
        DateFormat df = new SimpleDateFormat(DEFAULT_SUB_FOLDER_FORMAT_AUTO);  
        String fileName = df.format(new Date());  
        File file = new File(path + File.separator + fileName);  
        if (!file.mkdirs()) {  
            return null;  
        }  
        return file;  
    }  
	
    /** ~~~ 上传文件的保存路径 */  
    private static final String FILE_UPLOAD_DIR = Constants.UPLOAD_IMAGES_PATH;  
  
    private String fo;
  
    // CkEditor 浏览图片
	@Action(value = "/ckbrowerServer",results = { @Result(name = "success", location = "/pages/admin/pri/content/browse.jsp")})
    public String processBrowerPost() {  
		try {
	        String typeStr = type;  
	        String floderName = fo;
	          
	        if (log.isDebugEnabled()) {  
	            log.debug("浏览文件，文件格式:{} 目录 {} getServerUrl {}",new String[]{ typeStr,fo,getServerUrl()});  
	        }  
	        // 定位到目标文件夹 ： 上传目录  
	        String realPath = "";  
	        if(StringUtils.isNotBlank(floderName)){  
	            floderName = URLDecoder.decode(floderName);  
	            // 如果请求中存在文件夹名称，则定位到文件夹中  
//	            realPath = getSession().getServletContext().getRealPath(floderName);  
	            realPath = getDocumentRoot()+floderName;  
	            if(log.isInfoEnabled()){  
	                log.info("sub floder:"+realPath);  
	            }  
	        }else if(StringUtils.equalsIgnoreCase(typeStr, "Image")){
	            // 如果请求中不存在文件夹名称，则使用默认的文件夹
//	        	realPath = getSession().getServletContext().getRealPath(FILE_UPLOAD_DIR);
	            realPath = getDocumentRoot();
	            realPath = realPath+FILE_UPLOAD_DIR;
	            if(log.isInfoEnabled()){  
	                log.info("default floder:"+realPath);  
	            }  
	            File folder = new File(realPath);  
	            if(!folder.exists()){
	            	log.warn("创建默认目录"+realPath);
	            	folder.mkdirs();
	            }  
	        }  
	          
	        File folder = new File(realPath);  
	        if(!folder.exists()){
	        	log.warn("目录不存在");
	            return null;  
	        }  
	          
	        // 存储子目录 ,路径需要从/freemarker开始  
	        List<String> subFolderSet = new ArrayList<String>();  
	        // 存储文件夹  
	        List<String> subFileerSet = new ArrayList<String>();  
	          
	        File[] subFiles = folder.listFiles();  
	        if(null != subFiles && 0 < subFiles.length){  
	            for(int i=0;i < subFiles.length; i++){  
	                File _file = subFiles[i];  
	                if(_file.isDirectory()){  
	                    subFolderSet.add(getDefaultFolder(_file));  
	                } else {  
	                    subFileerSet.add(_file.getName());  
	                }  
	            }  
	        }
	        
	        List<Folder> parentFolder = new ArrayList<Folder>(); 
	        List<Folder> childFolder = new ArrayList<Folder>(); 
	        List<String> imgList = new ArrayList<String>(); 
	        
            // 如果是子文件夹，显示上级目录链接  
            if(StringUtils.isNotBlank(floderName) && !checkIsRoot(folder)){  
                String parent = getDefaultFolder(folder.getParentFile());  
                if(log.isDebugEnabled()){  
                    log.debug("发现上级目录:"+ parent);  
                }
                
                Folder p = new Folder();
//                p.setName(parent.substring(1+parent.lastIndexOf("\\")));
                p.setName(parent);
                p.setPath(URLEncoder.encode(parent));
                parentFolder.add(p);
            }  
              
            // 如果是文件夹，则显示文件夹并且可以再次触发下级和上级目录  
            if(0 < subFolderSet.size()){  
                Iterator<String> subFolderSetIndex = subFolderSet.iterator();  
                while(subFolderSetIndex.hasNext()){  
                    String ftemp = subFolderSetIndex.next();  
                    
                    Folder p = new Folder();
                    p.setName(ftemp.substring(1+ftemp.lastIndexOf(File.separator)));
                    p.setPath(URLEncoder.encode(ftemp));
                    childFolder.add(p);
                    
                }  
            }  
              
            // 如果是文件，则点击就选择文件到控件中  
            if(0 < subFileerSet.size()){ 
                Iterator<String> subFileerSetIndex = subFileerSet.iterator();  
                while(subFileerSetIndex.hasNext()){  
                    String ftemp = subFileerSetIndex.next();  
                    String f = getDefaultFolder(folder);  
                    
                    String fileUrl = f + File.separator + ftemp;  

                    log.debug(fileUrl);
//                    fileUrl = getBasePath()+StringUtils.replace(fileUrl, "\\", "/");  
                    fileUrl = getServerUrl()+StringUtils.replace(fileUrl, "\\", "/");  
                      
                    if(log.isDebugEnabled()){  
                        log.debug("添加文件："+fileUrl);  
                    } 
                    imgList.add(fileUrl);
                }  
            }
            
            getRequest().setAttribute("parentFolder", parentFolder);
            getRequest().setAttribute("childFolder", childFolder);
            getRequest().setAttribute("imgList", imgList);
        } catch (Exception e) {  
        	log.error("浏览图片失败",e);
        	
        }
        return SUCCESS;
    }  
      
    /** 
     * 获取文件夹路径 
     * @return 
     */  
    private static String getDefaultFolder(File folder){
        String path = folder.getPath(); 
        
        System.out.println("getDefaultFolder path = "+path);
//        if(!path.endsWith(File.separator)){
//        	path+=File.separator;
//        }
//        System.out.println("getDefaultFolder path = "+path);
//        System.out.println("getDefaultFolder FILE_UPLOAD_DIR = "+FILE_UPLOAD_DIR.replace("/", File.separator));
        path = path.substring(path.indexOf(FILE_UPLOAD_DIR.replace("/", File.separator)));  
        return path;  
    }  
      
    /** 
     * 判断是否是根目录 
     * @param floderName 
     * @return 
     */  
    private static boolean checkIsRoot(File folder){  
        String name = folder.getName();
        System.out.println("checkIsRoot : "+name);
        System.out.println("checkIsRoot : "+FILE_UPLOAD_DIR);
        return FILE_UPLOAD_DIR.endsWith(name);  
    } 
	

	public Long getMeetingId() {
		return meetingId;
	}

	public void setMeetingId(Long meetingId) {
		this.meetingId = meetingId;
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
	
	public void setMeetingContentService(
			MeetingContentService meetingContentService) {
		this.meetingContentService = meetingContentService;
	}
	
	public MeetingContent getContent() {
		return content;
	}

	public void setContent(MeetingContent content) {
		this.content = content;
	}

	public Integer getContentType() {
		return contentType;
	}

	public void setContentType(Integer contentType) {
		this.contentType = contentType;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCKEditor() {
		return CKEditor;
	}

	public void setCKEditor(String editor) {
		CKEditor = editor;
	}

	public String getCKEditorFuncNum() {
		return CKEditorFuncNum;
	}

	public void setCKEditorFuncNum(String editorFuncNum) {
		CKEditorFuncNum = editorFuncNum;
	}

	public String getLangCode() {
		return langCode;
	}

	public void setLangCode(String langCode) {
		this.langCode = langCode;
	}

	public MeetingContentService getMeetingContentService() {
		return meetingContentService;
	}
	
	public String getFo() {
		return fo;
	}

	public void setFo(String fo) {
		this.fo = fo;
	}

	public String[] getCategory() {
		return category;
	}

	public void setCategory(String[] category) {
		this.category = category;
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

	public String getDefaultIcon() {
		return defaultIcon;
	}

	public void setDefaultIcon(String defaultIcon) {
		this.defaultIcon = defaultIcon;
	}

	public String getDefaultImg() {
		return defaultImg;
	}

	public void setDefaultImg(String defaultImg) {
		this.defaultImg = defaultImg;
	}

	public String getItemIds() {
		return itemIds;
	}

	public void setItemIds(String itemIds) {
		this.itemIds = itemIds;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

}

package com.wondertek.meeting.action.meeting;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;

import com.wondertek.meeting.Consts;
import com.wondertek.meeting.action.base.BaseAction;
import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.exception.HibernateDaoSupportException;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.Meeting;
import com.wondertek.meeting.model.MeetingFiles;
import com.wondertek.meeting.model.MeetingFilesAssort;
import com.wondertek.meeting.model.MeetingMember;
import com.wondertek.meeting.model.custom.PageCustom;
import com.wondertek.meeting.service.FileSystemService;
import com.wondertek.meeting.service.MeetingFilesAssortService;
import com.wondertek.meeting.service.MeetingFilesService;
import com.wondertek.meeting.service.MeetingMemberService;
import com.wondertek.meeting.service.MeetingService;
import com.wondertek.meeting.service.custom.PageCustomService;
import com.wondertek.meeting.util.FileOperatorUtil;
import com.wondertek.meeting.util.StringUtil;

/**
 * 会议附件管理
 * 
 * @author GuoXu
 */
public class MeetingFilesAction extends BaseAction {

	private static final long serialVersionUID = -2648590565375417813L;
	/**
	 * 获取文件列表
	 * 
	 * @return
	 */
	private MeetingFilesService meetingFilesService;
	private MeetingService meetingService;
	private FileSystemService fileSystemService;
	private MeetingFilesAssortService meetingFilesAssortService;
	MeetingMemberService meetingMemberService;
	private PageCustomService pageCustomService;
	private String viewDisplayType; //资料管理视图显示方式: bookshelf, list
	private Long pageCustomId;

	public void setMeetingMemberService(MeetingMemberService meetingMemberService) {
		this.meetingMemberService = meetingMemberService;
	}

	public void setMeetingFilesAssortService(
			MeetingFilesAssortService meetingFilesAssortService) {
		this.meetingFilesAssortService = meetingFilesAssortService;
	}

	// 文件标题
	private String title;
	// 上传文件域对象
	private File upload;
	// 上传文件名
	private String uploadFileName;
	// 上传文件类型
	private String uploadContentType;
	// 保存文件的目录路径(通过依赖注入)
	private String savePath;
	private String fileComment;

	private String meetingId;
	private final String FOLD_SUFIX="ZL";
	
	//资料封面
	private File fileCover;
	private String fileCoverFileName;
	
	
	/**
	 * 准备上传文件
	 * @return
	 */
	public String preAddMeetingFile(){
		String forward=SUCCESS;
		String meetingId=this.getParameter("meetingId");
		
		Pager<MeetingFilesAssort>  pager= meetingFilesAssortService.getMeetingFilesAssortPages(1, 1000, Long.valueOf(meetingId),null);
		this.getRequest().setAttribute("meetingId", meetingId);
		this.getRequest().setAttribute("meetingFilesAssortLst", pager.getPageRecords());
		
		return forward;
	}
	
	/**
	 * 客户端上传文件
	 * @return
	 */
	public String doFileUpload4Client() {
		try {
			String meetingId = getRequest().getParameter("meetingId");
			
			if(StringUtil.isEmpty(meetingId)){
				this.resultMap.put("retcode", "1");// 成功
				this.resultMap.put("retMsg", "上传失败:会议号未传给服务!");
				return SUCCESS;
			}
			// 创建文件夹
			String uploadFoldPathStr = fileSystemService.getDocumentRoot()
					+ this.getSavePath() + File.separator +FOLD_SUFIX+ meetingId;
			File uploadFoldPath = new File(uploadFoldPathStr);
			if (!uploadFoldPath.exists()) {
				uploadFoldPath.mkdirs();
			}

			FileOperatorUtil fileOperatorUtil = new FileOperatorUtil();
			final MultiPartRequestWrapper multiWrapper = (MultiPartRequestWrapper) getRequest();
			final String[] fileNameArr = multiWrapper.getFileNames("file");
			String fileName = fileNameArr[0];
			fileName=java.net.URLDecoder.decode(fileName, "UTF-8");
			log.info("fileName---:"+fileName);
			String filePostfix = fileName.split("[.]")[(fileName.split("[.]").length - 1)];
			final Enumeration<String> fileParameterNames = multiWrapper
					.getFileParameterNames();
			File file = null;
			while (fileParameterNames != null
					&& fileParameterNames.hasMoreElements()) {
				final String inputName = fileParameterNames.nextElement();
				if (StringUtils.endsWithIgnoreCase("file", inputName)) {
					final File[] files = multiWrapper.getFiles(inputName);
					file = files[0];
				}
			}
			if (file != null) {
				// 获取一个文件名
				Date dt = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
				String fileSaveName = sdf.format(dt);
				fileSaveName += "." + filePostfix;
				String dstPath = uploadFoldPathStr + File.separator
						+ fileSaveName;
				log.info("上传的文件的类型：" + this.getUploadContentType());
				File dstFile = new File(dstPath);
				fileOperatorUtil.copy(file, dstFile);
				
				//保存文件到数据库中
				MeetingFiles meetingFiles = new MeetingFiles();
				meetingFiles.setFileName(fileName);
				meetingFiles.setFileSaveName(fileSaveName);
				// 保存meetingId外键
				meetingFiles.setMeetingId(Integer.parseInt(meetingId));
				meetingFiles.setFilePath(this.getSavePath() + File.separator
						+ this.FOLD_SUFIX + meetingId);// 存储路径
				meetingFiles.setFilePostfix(filePostfix.toLowerCase());
				// meetingFiles.
				meetingFiles.setCreateTime(new Date());
				meetingFiles.setFileSize(Double.valueOf(Long.valueOf(dstFile.length())));
				meetingFiles.setState(0);
				meetingFiles.setDownloadFlg(1);// 下载
				meetingFiles.setPreviewFlg(0);// 预览
				meetingFiles.setComments("来源自客户端上传!");
				
				meetingFilesService.saveMeetingFiles(meetingFiles);
				
				this.resultMap.put("retcode", "0");// 成功
				this.resultMap.put("retMsg", "上传成功");
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.resultMap.put("retcode", "1");// 成功
			this.resultMap.put("retMsg", "上传失败:"+e.getMessage());
		}

		return SUCCESS;
	}

	/**
	 * 进行文件的上传操作
	 * 
	 * @return
	 */
	public String doFileUpload() {

		String meetingId = getRequest().getParameter("meetingId");
		String assortId=getRequest().getParameter("assortId");
		String sortCode=getRequest().getParameter("sortCode");
		String[] memberLevel=getRequest().getParameterValues("memberLevel");
		String downloadFlg = getRequest().getParameter("downloadFlg");
		String previewFlg = getRequest().getParameter("previewFlg");
		//访问权限
		String fileAccess ="";
		if(memberLevel!=null&&memberLevel.length>0){
			for(int i=0;i<memberLevel.length;i++){
				fileAccess=fileAccess+memberLevel[i]+",";
			}
		}
		log.debug("file_access："+fileAccess);
		
		MeetingFiles meetingFiles = new MeetingFiles();
		
		log.debug("上传文件meetingId = {}", meetingId);
		// 获取一个文件名
		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String fileSaveName = sdf.format(dt);
		log.debug("fileSaveName:" + fileSaveName);

		try {
			// 获取文件后缀
			String filePostfix = "";
			String[] fileNameInfo = this.getUploadFileName().split("[.]");
			if (fileNameInfo != null && fileNameInfo.length > 1) {
				filePostfix = fileNameInfo[fileNameInfo.length - 1];
				log.info("文件的后缀名为 :" + filePostfix);
			} else {
				log.debug("文件名切割后的数组length竟然小于等于1 不正常!");
			}

			fileSaveName += "." + filePostfix;
			
			//创建文件夹
			String uploadFoldPathStr=fileSystemService.getDocumentRoot() + this.getSavePath()+File.separator+this.FOLD_SUFIX+meetingId;
			File uploadFoldPath = new File(uploadFoldPathStr);
			if(!uploadFoldPath.exists()){
				uploadFoldPath.mkdirs();
			}

			// 根据服务器的文件保存地址和原文件名创建目录文件全路径
			String dstPath = uploadFoldPathStr + File.separator + fileSaveName;
			log.info("上传的文件的类型：" + this.getUploadContentType());
			FileOperatorUtil fileOperatorUtil = new FileOperatorUtil();

			File dstFile = new File(dstPath);
			fileOperatorUtil.copy(this.upload, dstFile);
			
			//上传资料封面
			if(fileCover != null) {
				String fileCoverName = sdf.format(dt);
				// 获取文件后缀
				String suffix = "";
				String[] fileName = this.fileCoverFileName.split("[.]");
				if (fileName != null && fileName.length > 1) {
					suffix = fileName[fileName.length - 1];
					log.info("文件封面的后缀名为 :" + suffix);
				} else {
					log.error("文件封面名切割后的数组length竟然小于等于1 不正常!");
				}
				fileCoverName += "." + suffix;
				
				//创建文件夹
				String fileCoverPathStr=fileSystemService.getDocumentRoot() + "fileCover" + File.separator + meetingId;
				File fileCoverPath = new File(fileCoverPathStr);
				if(!fileCoverPath.exists()){
					fileCoverPath.mkdirs();
				}

				// 根据服务器的文件保存地址和原文件名创建目录文件全路径
				String desCoverPath = fileCoverPathStr + File.separator + fileCoverName;
				log.info("封面文件的保存路径：" + desCoverPath);
				File desCoverFile = new File(desCoverPath);
				FileOperatorUtil.copy(fileCover, desCoverFile);
				
				//把路径写到数据库中
				meetingFiles.setFileCoverPath("fileCover/" + meetingId + "/" + fileCoverName);
				log.info("封面文件的在数据库中保存的路径：" + meetingFiles.getFileCoverPath());
			}

			
			meetingFiles.setFileName(this.getUploadFileName());
			meetingFiles.setFileSaveName(fileSaveName);
			// 保存meetingId外键
			meetingFiles.setMeetingId(Integer.parseInt(meetingId));
			meetingFiles.setFilePath(this.getSavePath()+File.separator+this.FOLD_SUFIX+meetingId);// 存储路径
			meetingFiles.setFilePostfix(filePostfix.toLowerCase());
			// meetingFiles.
			meetingFiles.setCreateTime(new Date());
			meetingFiles.setFileSize(Double.valueOf(Long.valueOf(dstFile.length())));
			meetingFiles.setState(0);
			meetingFiles.setCreator(String.valueOf(this.getAdminUserIdFromSession()));
			meetingFiles.setFileAccess(fileAccess);
			//添加分类信息
			if(StringUtil.isNotEmpty(assortId)){
				meetingFiles.setAssortId(Integer.valueOf(assortId));
			}
			//排序码
			if(StringUtil.isNotEmpty(sortCode)){
				meetingFiles.setSortCode(Integer.valueOf(sortCode));
			}else{
				meetingFiles.setSortCode(null);
			}
		
			meetingFiles.setDownloadFlg(StringUtil.parseInteger(downloadFlg));// 下载
			meetingFiles.setPreviewFlg(StringUtil.parseInteger(previewFlg));// 预览
			
			meetingFilesService.saveMeetingFiles(meetingFiles);

			getRequest().setAttribute("retcode", "0");// 成功
			getRequest().setAttribute("retMsg", "上传成功");

		} catch (Exception e) {
			getRequest().setAttribute("retcode", "1");
			getRequest().setAttribute("retMsg", "上传失败");
			log.error("file upload error ", e);
		}
		
		//判断是否是会议向导提交的请求
		String guideStep = getRequest().getParameter("guideStep");
		if("step4".equals(guideStep)) {
			return "guide_step4";
		}
		
		
		Pager<MeetingFilesAssort>  pager= meetingFilesAssortService.getMeetingFilesAssortPages(1, 1000, Long.valueOf(meetingId),null);
		this.getRequest().setAttribute("meetingFilesAssortLst", pager.getPageRecords());
		
		this.setAttribute("assortId", assortId);
		
		return SUCCESS;

	}
	
	/**
	 * 查询文件信息
	 * @return
	 * @throws ServiceException 
	 * @throws NumberFormatException 
	 */
	public String selectMeetingFileById() throws NumberFormatException, ServiceException {
		String forward=SUCCESS;
		
		String meetingId=getRequest().getParameter("meetingId");
		String filesId=getRequest().getParameter("filesId");
		
		
		//通过分类查询
		Pager<MeetingFilesAssort>  assortPager= meetingFilesAssortService.getMeetingFilesAssortPages(1, 1000, Long.valueOf(meetingId),null);
		this.getRequest().setAttribute("meetingFilesAssortLst", assortPager.getPageRecords());
		
		
		MeetingFiles meetingFiles= meetingFilesService.findById(Integer.valueOf(filesId));
		this.getRequest().setAttribute("meetingFiles", meetingFiles);
		this.getRequest().setAttribute("meetingId", meetingId);

		return forward;
	}
	
	
	/**
	 * 进行文件的上传操作
	 * 
	 * @return
	 * @throws ServiceException 
	 * @throws NumberFormatException 
	 */
	public String updateMeetingFile() throws Exception {
		String forward=SUCCESS;
		
		String filesId=getRequest().getParameter("filesId");
		String assortId=getRequest().getParameter("assortId");
		String fileName=getRequest().getParameter("fileName");
		String fileComment=getRequest().getParameter("fileComment");
	    String sortCode=getRequest().getParameter("sortCode");
	    String[] memberLevel=getRequest().getParameterValues("memberLevel");
		//访问权限
		String fileAccess ="";
		if(memberLevel!=null&&memberLevel.length>0){
			for(int i=0;i<memberLevel.length;i++){
				fileAccess=fileAccess+memberLevel[i]+",";
			}
		}
		log.debug("file_access："+fileAccess);
		
		 MeetingFiles meetingFiles= meetingFilesService.findById(Integer.valueOf(filesId));
		 if(StringUtil.isNotEmpty(assortId)){
			 meetingFiles.setAssortId(Integer.valueOf(assortId));
		 }else{
			 meetingFiles.setAssortId(null);
		 }
		 
		 if(StringUtil.isNotEmpty(fileName)){
			 meetingFiles.setFileName(fileName);
		 }
		 
		//排序码
			if(StringUtil.isNotEmpty(sortCode)){
				meetingFiles.setSortCode(Integer.valueOf(sortCode));
			}else{
				meetingFiles.setSortCode(null);
			}
		 
		 meetingFiles.setComments(fileComment);
		 meetingFiles.setModifier(String.valueOf(this.getAdminUserIdFromSession()));
		 meetingFiles.setModifyTime(new Date());
		 meetingFiles.setFileAccess(fileAccess);
		 
		 String downloadFlg = getRequest().getParameter("downloadFlg");
		 String previewFlg = getRequest().getParameter("previewFlg");
		 meetingFiles.setDownloadFlg(StringUtil.parseInteger(downloadFlg));// 下载
		 meetingFiles.setPreviewFlg(StringUtil.parseInteger(previewFlg));// 预览
		 
		//上传资料封面
			if(fileCover != null) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
				String fileCoverName = sdf.format(new Date());
				// 获取文件后缀
				String suffix = "";
				String[] fileSuffixName = this.fileCoverFileName.split("[.]");
				if (fileSuffixName != null && fileSuffixName.length > 1) {
					suffix = fileSuffixName[fileSuffixName.length - 1];
					log.info("文件封面的后缀名为 :" + suffix);
				} else {
					log.error("文件封面名切割后的数组length竟然小于等于1 不正常!");
				}
				fileCoverName += "." + suffix;
				
				//创建文件夹
				String fileCoverPathStr=fileSystemService.getDocumentRoot() + "fileCover" + File.separator + meetingFiles.getMeetingId();
				File fileCoverPath = new File(fileCoverPathStr);
				if(!fileCoverPath.exists()){
					fileCoverPath.mkdirs();
				}

				// 根据服务器的文件保存地址和原文件名创建目录文件全路径
				String desCoverPath = fileCoverPathStr + File.separator + fileCoverName;
				log.info("封面文件的保存路径：" + desCoverPath);
				File desCoverFile = new File(desCoverPath);
				FileOperatorUtil.copy(fileCover, desCoverFile);
				
				//把路径写到数据库中
				meetingFiles.setFileCoverPath("fileCover/" + meetingFiles.getMeetingId() + "/" + fileCoverName);
				log.info("封面文件的在数据库中保存的路径：" + meetingFiles.getFileCoverPath());
			}

		 
		 meetingFilesService.modify(meetingFiles) ;
		 
		 //传回到列表
		 this.setMeetingId(String.valueOf(meetingFiles.getMeetingId()));
		 
		 Pager<MeetingFilesAssort>  pager= meetingFilesAssortService.getMeetingFilesAssortPages(1, 1000, Long.valueOf(meetingId),null);
		 this.getRequest().setAttribute("meetingFilesAssortLst", pager.getPageRecords());
			
		 
		return forward;
	}

	@Override
        public void addActionError(String anErrorMessage) {

		// 这里要先判断一下，是我们要替换的错误，才处理

		if (anErrorMessage.startsWith("the request was rejected because its size")) {

			Matcher m = Pattern.compile("\\d+").matcher(anErrorMessage);

			String s1 = "";

			if (m.find())
				s1 = m.group();

			String s2 = "";

			if (m.find())
				s2 = m.group();

			Long l1 = Long.parseLong(s1);
			l1 = l1 / (1024 * 1024) + 1;
			Long l2 = Long.parseLong(s2) / (1024 * 1024);

			super.addFieldError("file", "你上传的文件大小为" + String.valueOf(l1) + "M，超过系统允许的大小（" + String.valueOf(l2) + "M）。");

		} else {// 否则按原来的方法处理
			
			super.addActionError(anErrorMessage);
		}

	}

	

	/**
	 * 获取文件列表信息 提供给后台管理使用
	 * 
	 * @return
	 * @throws ServiceException 
	 * @throws NumberFormatException 
	 */
	public String getMeetingFilesPager() throws NumberFormatException, ServiceException {

		String forward = SUCCESS;
		// String meeting
		String meetingId = getRequest().getParameter("meetingId");
		String from = getRequest().getParameter("from");
		String meetingFileName = getRequest().getParameter("meetingFileName");
		String assortId=getRequest().getParameter("assortId");
		String userId=String.valueOf(this.getUserIdFromSession());
		MeetingMember meetingMember=new MeetingMember();
		
		if(StringUtil.isNotEmpty(userId)){
			meetingMember=meetingMemberService.selectById(Long.valueOf(userId), Long.valueOf(meetingId));
		}
	    
	    
		
		log.info("获取文件分页列表:meetingId:" + meetingId + ",from:" + from
				+ ",meetingFileName:" + meetingFileName + ",assortId:"
				+ assortId + ",userId：" + userId);
		
		if (meetingId == null || "".equals(meetingId)) {
			meetingId = String.valueOf(getRequest().getAttribute("meetingId"));
		}

		String cPage = getRequest().getParameter("currentPage");
		if (cPage != null && !"".equals(cPage)) {
			currentPage = Integer.parseInt(cPage);
		}

		// 如果ACTION来源于PORTAL,则显示
		if(StringUtil.isNotEmpty(from)){
			if("portal".equals(from)){
				pageSize = 12;
				forward = "portal";
			}
			if(from.contains("guide")){
				pageSize = 100000;
				forward = "guide";
			}
		}
		
		// 页面展示形式
		String pageType = getRequest().getParameter("pageType");
		if (StringUtil.isEmpty(pageType)) {
			PageCustom pc = this.pageCustomService.getPageCustom(Long.parseLong(meetingId),
					Consts.PAGE_CUSTOM_FILES_SHOW_TYPE);
			if (pc != null) {
				pageType = pc.getPropertyValue();
			}
		}
		getRequest().setAttribute("pageType", pageType);
		
		
		//TODO
		//通过分类查询
		Pager<MeetingFilesAssort>  assortPager= meetingFilesAssortService.getMeetingFilesAssortPages(1, 1000, Long.valueOf(meetingId),null);
		this.getRequest().setAttribute("meetingFilesAssortLst", assortPager.getPageRecords());
		
		//查询文件列表
		MeetingFiles meetingFiles = new MeetingFiles();
		meetingFiles.setMeetingId(meetingId == null || "".equals(meetingId) ? null : Integer.parseInt(meetingId));
		meetingFiles.setFileName(meetingFileName);
		if(StringUtil.isNotEmpty(assortId)){
			meetingFiles.setAssortId(Integer.valueOf(assortId));
		}

		Pager<MeetingFiles> pager = null;
		String memberLevel = meetingMember != null ? String.valueOf(meetingMember.getMemberLevel()) : null;
		pager = meetingFilesService.getMeetingFilesPager(currentPage, pageSize, meetingFiles, memberLevel);
		
	    //循环一个分类名称
		List<MeetingFiles>  meetingFilespagerList=pager.getPageRecords();
		for(MeetingFiles meetingFile:meetingFilespagerList){
			//added by zgj at 2012/6/5: 如果是书架形式显示，则对文件名进行换行显示处理
			String filename = meetingFile.getFileName();
			if(!"list".equals(pageType) && filename != null) {
				StringBuffer sb = new StringBuffer();
				int count = 0;
				for(int i = 0; i < filename.length(); i++) {
					sb.append(filename.charAt(i));
					count++;
					
					if(count % 7 == 0) {  //7个字显示成一行
						sb.append("<br/>");
					}
				}
				meetingFile.setDisplayedFileName(sb.toString());
			}
			
			if(meetingFile.getAssortId()!=null){
				//TODO
				MeetingFilesAssort meetingFilesAssort=	meetingFilesAssortService.findById(Long.valueOf(String.valueOf(meetingFile.getAssortId())));
			    if(meetingFilesAssort!=null){
			    	meetingFile.setAssortName(meetingFilesAssort.getAssortName());
			    }else{
			    	meetingFile.setAssortName("未分类");
			    }
			}else{
				meetingFile.setAssortName("未分类");
			}
		}

		// 获取MEETING的信息
		Meeting meetting = new Meeting();
		try {
			// 获取会议信息
			meetting = meetingService.getMeetingByPk(Long.parseLong(meetingId));
			getRequest().setAttribute("meetting", meetting);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (HibernateDaoSupportException e) {
			e.printStackTrace();
		}

		getRequest().setAttribute("meetingId", meetingId);
		getRequest().setAttribute("pager", pager);
		getRequest().setAttribute("assortId", assortId);
		// 查询条件返回
		getRequest().setAttribute("meetingFileName", meetingFileName);
		// 获取当前用户
		getRequest().setAttribute("meetingMember", meetingMember);
		this.setAttribute("fileType", Consts.FILE_TYPE_THUMBNAIL);
		return forward;
	}

	/**
	 * 删除指定文件
	 * 
	 * @return
	 */
	public String deleteMeetingFile() {
		String meetingFileId = getRequest().getParameter("meetingFileId");
		String meetingId = "";
		try {
			MeetingFiles meetingFiles = this.meetingFilesService.findById(Integer.parseInt(meetingFileId));
			meetingId = String.valueOf(meetingFiles.getMeetingId());
			this.meetingFilesService.delete(meetingFiles);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (ServiceException e) {
			e.printStackTrace();
		}

		this.setMeetingId(meetingId);
		this.getRequest().setAttribute("meetingId", meetingId);
		this.getSession().setAttribute("meetingId", meetingId);

		return SUCCESS;
	}

	/**
	 * 下载指定文件
	 * 
	 * @return
	 */
	public String doDownloadFile() {
		String meetingFileId = getRequest().getParameter("meetingFileId");
		String from=getRequest().getParameter("from");

		MeetingFiles meetingFiles = null;
		try {
			meetingFiles = this.meetingFilesService.findById(Integer.parseInt(meetingFileId));

			// 判断文件是否存在
			String dstPath = fileSystemService.getDocumentRoot() + meetingFiles.getFilePath() + File.separator
					+ meetingFiles.getFileSaveName();
			// 如果文件不存在的话
			File tempFile = new File(dstPath);
			if (!tempFile.exists()) {
				// 返回到错误页面
				return this.ERROR;
			}
			
			String documentRoot=fileSystemService.getDocumentRoot();
			//DOCUMENT ROOT
			getRequest().setAttribute("documentRoot", documentRoot);
			getRequest().setAttribute("from", from);
			
			// String filelPath=
			// ServletActionContext.getServletContext().getRealPath(
			// meetingFiles.getFilePath())+meetingFiles.getFileSaveName();

		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (ServiceException e) {
			e.printStackTrace();
		}

		getRequest().setAttribute("meetingFiles", meetingFiles);

		return SUCCESS;

	}

	public String preview() {
		// String meeting
		String fileid = getRequest().getParameter("id");
		// 如果没传 默认取SESSION中的
		try {
			MeetingFiles meetingFiles = meetingFilesService.findById(Integer.parseInt(fileid));

			String path = "/upload/" + meetingFiles.getMeetingId() + "/a-" + meetingFiles.getId() + "-0.jpg";
			if (meetingFiles.getPrePage() == null || meetingFiles.getPrePage() == 0) {
				resultMap.put("retcode", 1);
				resultMap.put("retmsg", "没有预览文件");
				return SUCCESS;
			}

			String url = getRequest().getScheme() + "://" + getRequest().getServerName() + ":"
					+ getRequest().getServerPort() + path;

			// resultMap.put("url", java.net.URLEncoder.encode(url,"utf-8"));
			resultMap.put("url", url);
			resultMap.put("num", meetingFiles.getPrePage());
			resultMap.put("retcode", 0);
			resultMap.put("retmsg", "查询成功");
		} catch (Exception e) {
			resultMap.put("retmsg", "数据异常");
			resultMap.put("retcode", 1);
		}

		return SUCCESS;

	}
	
	
	
	/**
	 * 获取文件列表信息 提供给客户端使用
	 * 
	 * @return
	 */
	public String getMeetingFilesList() {
		// String meeting
		String meetingId = getRequest().getParameter("meetingId");
		String assortId=getRequest().getParameter("assortId");
		String userId=String.valueOf(this.getUserIdFromSession());
		// 如果没传 默认取SESSION中的
		log.debug("客户端会议资料列表服务:meetingId = "+meetingId+",assortId:"+assortId+",userId:"+userId);
		MeetingMember meetingMember=new MeetingMember();
		if(StringUtil.isNotEmpty(userId)){
			meetingMember=meetingMemberService.selectById(Long.valueOf(userId), Long.valueOf(meetingId));
		}
	    

		MeetingFiles meetingFiles = new MeetingFiles();
		meetingFiles.setMeetingId(meetingId == null || "".equals(meetingId) ? null : Integer.parseInt(meetingId));
		//种类
		if(StringUtil.isNotEmpty(assortId)){
			meetingFiles.setAssortId(Integer.valueOf(assortId ));
		}

		List<MeetingFiles> meetingFileList = new ArrayList<MeetingFiles>();
		String serverUrl=fileSystemService.getServerUrl();

		try {
			meetingFileList = meetingFilesService.getMeetingFilesList(meetingFiles,meetingMember.getMemberLevel());
		} catch (HibernateDaoSupportException e) {
			e.printStackTrace();
		}

		getRequest().setAttribute("meetingFileList", meetingFileList);
		resultMap.put("meetingFileList", meetingFileList);
		resultMap.put("retcode", 0);
		resultMap.put("serverUrl",serverUrl);
		resultMap.put("retmsg", "查询成功");

		return SUCCESS;

	}
	
	



	public MeetingFilesService getMeetingFilesService() {
		return meetingFilesService;
	}

	public void setMeetingFilesService(MeetingFilesService meetingFilesService) {
		this.meetingFilesService = meetingFilesService;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public String getSavePath() {
		return savePath;
	}

	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getFileComment() {
		return fileComment;
	}

	public void setFileComment(String fileComment) {
		this.fileComment = fileComment;
	}

	public String getMeetingId() {
		return meetingId;
	}

	public void setMeetingId(String meetingId) {
		this.meetingId = meetingId;
	}

	public void setMeetingService(MeetingService meetingService) {
		this.meetingService = meetingService;
	}

	/**
	 * @param fileSystemService
	 *            the fileSystemService to set
	 */
	public void setFileSystemService(FileSystemService fileSystemService) {
		this.fileSystemService = fileSystemService;
	}
	
	/**
	 * 有关资料浏览器界面配置的Action方法
	 * added by zhangguojing at 2012/3/19
	 */
	
	public String goViewTypeConfig() throws Exception {
		PageCustom pc = this.pageCustomService.getPageCustom(Long.parseLong(meetingId), Consts.PAGE_CUSTOM_FILES_SHOW_TYPE);
		if(pc != null) {
			this.pageCustomId = pc.getId();
			this.viewDisplayType = pc.getPropertyValue();
		}
		return SUCCESS;
	}
	
	public String saveViewTypeConfig() throws Exception {
		Meeting meeting = this.meetingService.findById(Long.parseLong(meetingId));
		PageCustom pageCustom = null;
		
		if(pageCustomId != null && pageCustomId > 0) { //edit
			pageCustom = this.pageCustomService.findById(pageCustomId);
		} else {
			pageCustom = new PageCustom();
			pageCustom.setPropertyName(Consts.PAGE_CUSTOM_FILES_SHOW_TYPE);
			pageCustom.setDescription("会议资料显示方式配置，bookshelf为书架，list为列表");
		}
		
		pageCustom.setMeeting(meeting);
		pageCustom.setPropertyValue(viewDisplayType);
		this.pageCustomService.saveOrUpdate(pageCustom);
		this.errMsg = "会议资料显示方式配置成功";
		return SUCCESS;
	}

	public PageCustomService getPageCustomService() {
		return pageCustomService;
	}

	public void setPageCustomService(PageCustomService pageCustomService) {
		this.pageCustomService = pageCustomService;
	}

	public String getViewDisplayType() {
		return viewDisplayType;
	}

	public void setViewDisplayType(String viewDisplayType) {
		this.viewDisplayType = viewDisplayType;
	}

	public Long getPageCustomId() {
		return pageCustomId;
	}

	public void setPageCustomId(Long pageCustomId) {
		this.pageCustomId = pageCustomId;
	}

	public File getFileCover() {
		return fileCover;
	}

	public void setFileCover(File fileCover) {
		this.fileCover = fileCover;
	}

	public String getFileCoverFileName() {
		return fileCoverFileName;
	}

	public void setFileCoverFileName(String fileCoverFileName) {
		this.fileCoverFileName = fileCoverFileName;
	}
	public String getDocumentRoot() {
		return fileSystemService.getDocumentRoot();
	}

	public String getServerUrl() {
		return fileSystemService.getServerUrl();
	}
}

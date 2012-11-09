package com.wondertek.meeting.action.meeting;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.wondertek.meeting.action.base.BaseAction;
import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.MeetingFilesAssort;
import com.wondertek.meeting.service.FileSystemService;
import com.wondertek.meeting.service.MeetingFilesAssortService;
import com.wondertek.meeting.util.FileOperatorUtil;
import com.wondertek.meeting.util.StringUtil;

/**
 * 用于编写文件分类
 * 功能模块
 * @author Guoxu
 *
 */
public class MeetingFilesAssortAction extends BaseAction {

	private static final long serialVersionUID = -2166315678596501137L;
	MeetingFilesAssortService meetingFilesAssortService;
	Long meetingId;
	FileSystemService fileSystemService;

	public FileSystemService getFileSystemService() {
		return fileSystemService;
	}

	public void setFileSystemService(FileSystemService fileSystemService) {
		this.fileSystemService = fileSystemService;
	}

	// 上传文件域对象
	private File pageImg;
	// 上传文件名
	private String pageImgFileName;
	// 上传文件类型
	private String pageImgContentType;
	
	public File getPageImg() {
		return pageImg;
	}

	public void setPageImg(File pageImg) {
		this.pageImg = pageImg;
	}

	public String getPageImgFileName() {
		return pageImgFileName;
	}


	public void setPageImgFileName(String pageImgFileName) {
		this.pageImgFileName = pageImgFileName;
	}


	public String getPageImgContentType() {
		return pageImgContentType;
	}


	public void setPageImgContentType(String pageImgContentType) {
		this.pageImgContentType = pageImgContentType;
	}


	public Long getMeetingId() {
		return meetingId;
	}


	public void setMeetingId(Long meetingId) {
		this.meetingId = meetingId;
	}


	public MeetingFilesAssortService getMeetingFilesAssortService() {
		return meetingFilesAssortService;
	}


	public void setMeetingFilesAssortService(
			MeetingFilesAssortService meetingFilesAssortService) {
		this.meetingFilesAssortService = meetingFilesAssortService;
	}


	/**
	 * 获取列表
	 * @return
	 */
	public String getFilesAssortPages(){
		String forward=SUCCESS;
		String meetingId=this.getParameter("meetingId");
		String assortName=this.getParameter("assortName");
		Pager<MeetingFilesAssort>  pager=  meetingFilesAssortService.getMeetingFilesAssortPages(currentPage, pageSize, Long.valueOf(meetingId),assortName);
		
		this.getRequest().setAttribute("assortName", assortName);
		this.getRequest().setAttribute("meetingId", meetingId);
		this.getRequest().setAttribute("pager", pager);
		
		return forward;
	}
	
	/**
	 * 跳转到新增类别
	 * @return
	 */
	public String preAddAssort(){
		String forward=SUCCESS;
		String meetingId=this.getParameter("meetingId");
		
		this.getRequest().setAttribute("meetingId", meetingId);
		return forward;
	}
	
	
	/**
	 * 新增类别
	 * @return
	 * @throws ServiceException 
	 */
	public String addAssort() throws ServiceException{
		String forward=SUCCESS;
		String assortName=this.getParameter("assortName");
		String description=this.getParameter("description");
		String meetingId=this.getParameter("meetingId");
		String applyDepartment=this.getParameter("applyDepartment");
		String isImgFold=this.getParameter("isImgFold");
		
		Boolean booleanisImgFold=false;
		if("1".equals(isImgFold)){
			booleanisImgFold=true;
		}

	
		
		
		log.info("assortName:"+assortName+",description:"+description+",meetingId:"+meetingId);
		
		MeetingFilesAssort meetingFilesAssort=new MeetingFilesAssort();
		meetingFilesAssort.setAssortName(assortName);
		meetingFilesAssort.setDescription(description);
		meetingFilesAssort.setMeetingId(Long.valueOf(meetingId));
		meetingFilesAssort.setCreator(this.getAdminUserIdFromSession());
		meetingFilesAssort.setApplyDepartment(applyDepartment);
		String fileAbsolutePath=this.uploadFile();
		if(StringUtil.isNotEmpty(fileAbsolutePath)){
			meetingFilesAssort.setPageImgUrl(fileAbsolutePath);
		}
		meetingFilesAssort.setIsImgFold(booleanisImgFold);
		
		
		
		meetingFilesAssortService.saveOrUpdate(meetingFilesAssort);
		
		this.setMeetingId(Long.valueOf(meetingId));
		
		return forward;
	}
	
	
	/**
	 * 更新类别
	 * @return
	 * @throws ServiceException 
	 * @throws NumberFormatException 
	 */
	public String updateAssort() throws NumberFormatException, ServiceException{
		String forward=SUCCESS;
		String id=this.getParameter("id");
		String assortName=this.getParameter("assortName");
		String description=this.getParameter("description");
		String meetingId=this.getParameter("meetingId");
		String applyDepartment=this.getParameter("applyDepartment");
		String isImgFold=this.getParameter("isImgFold");
		Boolean booleanisImgFold=false;
		
		if("1".equals(isImgFold)){
			booleanisImgFold=true;
		}
		
		//TODO查处这个实体
		MeetingFilesAssort meetingFilesAssort = meetingFilesAssortService
		.findById(Long.valueOf(id));
		
		meetingFilesAssort.setAssortName(assortName);
		meetingFilesAssort.setDescription(description);
		meetingFilesAssort.setModifier(this.getAdminUserIdFromSession());
		meetingFilesAssort.setModifyTime(new Date());
		
		meetingFilesAssort.setApplyDepartment(applyDepartment);
		String fileAbsolutePath=this.uploadFile();
		if(StringUtil.isNotEmpty(fileAbsolutePath)){
			meetingFilesAssort.setPageImgUrl(fileAbsolutePath);
		}
		
		meetingFilesAssort.setIsImgFold(booleanisImgFold);
		
		//TODO 更新并跳转到列表页面
		meetingFilesAssortService.saveOrUpdate(meetingFilesAssort);
		
		this.setMeetingId(Long.valueOf(meetingId));
		
		
		return forward;
	}
	
	
	public String uploadFile(){
		
		if(this.getPageImg()==null){
			return "";
		}
		
		//创建文件夹
		String absolutePath="pageImgFold" + File.separator + meetingId;
		String fileCoverPathStr=fileSystemService.getDocumentRoot() +absolutePath ;
		File fileCoverPath = new File(fileCoverPathStr);
		if(!fileCoverPath.exists()){
			fileCoverPath.mkdirs();
		}
		Date dt=new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String filePagerUrlName = sdf.format(dt);
		
		String filePostfix="";
		String[] fileNameInfo = this.getPageImgFileName().split("[.]");
		if (fileNameInfo != null && fileNameInfo.length > 1) {
			filePostfix = fileNameInfo[fileNameInfo.length - 1];
			log.info("文件的后缀名为 :" + filePostfix);
		} else {
			log.debug("文件名切割后的数组length竟然小于等于1 不正常!");
		}
		
		
		FileOperatorUtil fileOperatorUtil = new FileOperatorUtil();
		String fileName=File.separator+filePagerUrlName+"."+filePostfix;
		absolutePath+=fileName;
		File dstFile = new File(fileCoverPathStr+fileName);
		try {
			fileOperatorUtil.copy(this.pageImg, dstFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return absolutePath;
	}
	
	
	/**
	 * 查询当前ID
	 * @return
	 * @throws ServiceException 
	 * @throws NumberFormatException 
	 */
	public String selectAssortById() throws NumberFormatException,
			ServiceException {
		String forward = SUCCESS;
		// TODO 查询当前分类信息
		String id = this.getParameter("id");
		MeetingFilesAssort meetingFilesAssort = meetingFilesAssortService
				.findById(Long.valueOf(id));
		this.getRequest()
				.setAttribute("meetingFilesAssort", meetingFilesAssort);
		
		this.setAttribute("serverUrl", fileSystemService.getServerUrl());
		
		return forward;
	}
	
	

	/**
	 * 批量删除类别
	 * @return
	 * @throws ServiceException 
	 * @throws NumberFormatException 
	 */
	public String batchDelAssort() throws NumberFormatException, ServiceException{
		String forward=SUCCESS;
		
		String ids = getRequest().getParameter("ids");
		String meetingId=getParameter("meetingId");
		//String meetingId=getRequest().getParameter("meetingId");
		log.debug("ids:"+ids);
		
		if (ids != null && ids.length() > 0) {
			ids = ids.substring(0, ids.length() - 1);
		}
		String[] assortIdArray = ids.split(",");
		/**
		 * 删除操作。采用循环 省的SERVICE写HQL了
		 */
		for (int i = 0; i < assortIdArray.length; i++) {
			MeetingFilesAssort meetingFilesAssort=new MeetingFilesAssort();
			meetingFilesAssort=meetingFilesAssortService.findById(Long.valueOf(assortIdArray[i]));
			meetingFilesAssortService.delete(meetingFilesAssort);
		}
		
		this.setMeetingId(Long.valueOf(meetingId));
		
		return forward;
	}
	
	
	
	/**
	 * 提供给客户端使用的
	 */
	public String getFilesAssortListByClient(){
		String forward=SUCCESS;
		String meetingId=this.getParameter("meetingId");
		Pager<MeetingFilesAssort>  pager=  meetingFilesAssortService.getMeetingFilesAssortPages(1, 1000, Long.valueOf(meetingId),null);
		
		
		
		this.getRequest().setAttribute("meetingId", meetingId);
		this.getRequest().setAttribute("filesAssortLst", pager.getPageRecords());
		
		this.resultMap.put("filesAssortList",  pager.getPageRecords());
		resultMap.put("retcode", 0);
		resultMap.put("retmsg", "查询成功");
		resultMap.put("serverUrl", fileSystemService.getServerUrl());
		
		
		return forward;
	}
	
	
	
	

}

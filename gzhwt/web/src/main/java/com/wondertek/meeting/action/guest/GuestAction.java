
package com.wondertek.meeting.action.guest;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;
import com.wondertek.meeting.action.base.BaseAction;
import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.Guest;
import com.wondertek.meeting.model.Meeting;
import com.wondertek.meeting.service.FileSystemService;
import com.wondertek.meeting.service.GuestService;
import com.wondertek.meeting.service.MeetingService;
import com.wondertek.meeting.util.FileOperatorUtil;
import com.wondertek.meeting.util.ImageCut;
import com.wondertek.meeting.util.StringUtil;

/**
* @ClassName: GuestAction 
* @Description: 嘉宾信息管理
* @author zouxiaoming
* @date Mar 6, 2012 1:39:56 PM 
*
 */
@SuppressWarnings("serial")
public class GuestAction extends BaseAction implements ModelDriven<Guest> {
	private Log log = LogFactory.getLog(GuestAction.class);
	private Guest guest=new Guest();
	private GuestService guestService;
	private MeetingService meetingService;
	private Long meetingId;
	private String flag;
	private String queryName;
	File headImg;
	// 上传文件名
	private String headImgFileName;
	// 上传文件类型
	private String headImgContentType;
	FileSystemService fileSystemService;
	
	public String getHeadImgFileName() {
		return headImgFileName;
	}

	public void setHeadImgFileName(String headImgFileName) {
		this.headImgFileName = headImgFileName;
	}

	public String getHeadImgContentType() {
		return headImgContentType;
	}

	public void setHeadImgContentType(String headImgContentType) {
		this.headImgContentType = headImgContentType;
	}
	

	public FileSystemService getFileSystemService() {
		return fileSystemService;
	}
	
	public void setFileSystemService(FileSystemService fileSystemService) {
		this.fileSystemService = fileSystemService;
	}
	public File getHeadImg() {
		return headImg;
	}
	public void setHeadImg(File headImg) {
		this.headImg = headImg;
	}
	public String switchToAddGuest(){
		return "addGuest";
	}
	/**
	 * 添加/修改嘉宾信息
	 * @return
	 * @throws ServiceException 
	 */
	public String addGuest() throws ServiceException{
		log.info("guest:"+guest);
		Meeting meeting=this.meetingService.findById(meetingId);
		guest.setMeeting(meeting);
		this.guestService.saveOrUpdate(guest);
		return this.findAllGuest();
	}

	/**
	 * 查出所有的嘉宾信息  
	 * @return
	 * @throws ServiceException 
	 */
	public String findAllGuest() throws ServiceException{
		Pager<Guest> pager=null;
		pager=this.guestService.findAllGuest(meetingId, currentPage, pageSize,queryName);
		ActionContext.getContext().put("pager", pager);
		Meeting meeting=this.meetingService.findById(meetingId);
		this.setAttribute("meeting", meeting);
		return "guestList";
	}
	
	
	/**
	 * 查看某一个嘉宾的详细信息
	 * @return
	 * @throws ServiceException 
	 */
	public String findGuestById() throws ServiceException{
		guest=this.guestService.findById(guest.getId());
		if(!StringUtil.isEmpty(flag)&&flag.equals("edit")){  // 进入修改页面
			return this.switchToAddGuest();
		}
		return "findGuest";
	}
	
	/**
	 * 删除某一个嘉宾信息
	 * @return
	 * @throws ServiceException 
	 */
	public String deleteGuest() throws ServiceException{
		guest=this.guestService.findById(guest.getId());
		if(guest!=null){
			this.guestService.delete(guest);
		}
		return null;
	}
	
	/**
	 * 上传头像图片
	 * @return
	 * @throws Exception 
	 */
	public String uploadHeadImg() throws Exception{
		
		StringBuffer originalUrl=new StringBuffer();
		
		if (this.headImg != null) {
			log.debug("文件不为空！");
			// 获取一个文件名
			Date dt = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			String fileSaveName = sdf.format(dt);
			log.debug("fileSaveName:" + fileSaveName);
			// 创建文件夹
			String uploadFoldPathStr = fileSystemService.getDocumentRoot()
					+ "upload" + File.separator + "head";
			File uploadFoldPath = new File(uploadFoldPathStr);
			if (!uploadFoldPath.exists()) {
				uploadFoldPath.mkdirs();
			}
			String filePostfix = "";
			String[] fileNameInfo = this.getHeadImgFileName().split("[.]");
			if (fileNameInfo != null && fileNameInfo.length > 1) {
				filePostfix = fileNameInfo[fileNameInfo.length - 1];
				log.info("文件的后缀名为 :" + filePostfix);
			} else {
				log.debug("文件名切割后的数组length竟然小于等于1 不正常!");
			}
			// 根据服务器的文件保存地址和原文件名创建目录文件全路径
			fileSaveName += "." + filePostfix;
			String dstPath = uploadFoldPathStr + File.separator + fileSaveName;
			log.info("上传的文件的类型：" + this.getHeadImgContentType());
			FileOperatorUtil fileOperatorUtil = new FileOperatorUtil();
			File dstFile = new File(dstPath);
			fileOperatorUtil.copy(this.headImg, dstFile);
			
			this.resultMap.put("bigPicFileName", fileSaveName);
			
		  //检查图片大小  
          BufferedImage src = ImageIO.read(new File(dstPath)); // 读入文件                    
          int width=  src.getWidth();
          int height= src.getHeight();
          
          ImageCut.scale(dstPath, dstPath, width, height);
         // ImageCut.gray(dstPath, dstPath);
		  originalUrl.append(fileSystemService.getServerUrl() + "upload"
					+ File.separator + "head" + File.separator + fileSaveName);
		}else{
			log.debug("文件为空,不能保存！");
		}
		
		String ttt=this.getRequest().getParameter("ttt");
		
		this.resultMap.put("retMsg", "上传成功！");
		this.resultMap.put("originalUrl", originalUrl.toString());
		
		
		return "uploadHeadImg";
		
	}
	
	
	
	public String cutHeadImg() throws IOException {
		String x = this.getRequest().getParameter("x");
		String y = this.getRequest().getParameter("y");
		String w = this.getRequest().getParameter("w");
		String h = this.getRequest().getParameter("h");
		String bigPicFileName = this.getRequest()
				.getParameter("bigPicFileName");
		// TODO
		log.info("x:" + x + ",y:" + y + ",w:" + w + ",h:" + h
				+ ",bigPicFileName:" + bigPicFileName);
		String uploadFoldPathStr = fileSystemService.getDocumentRoot()
				+ "upload" + File.separator + "head";
		String srcPath = uploadFoldPathStr + File.separator + bigPicFileName;
		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String fileSaveName = sdf.format(dt);
		fileSaveName = fileSaveName + "_" + bigPicFileName;
		String dstPath = uploadFoldPathStr + File.separator + fileSaveName;
		// ImageCut.scale(srcPath, dstPath, Integer.parseInt(w),
		// Integer.parseInt(h), Integer.parseInt(x), Integer.parseInt(y));

		ImageCut.abscut(srcPath, dstPath, Integer.parseInt(x),
				Integer.parseInt(y), Integer.parseInt(w), Integer.parseInt(h));

		StringBuffer headUrl = new StringBuffer();
		headUrl.append(fileSystemService.getServerUrl() + "upload"
				+ File.separator + "head" + File.separator + fileSaveName);

		this.resultMap.put("headUrl", headUrl.toString());
		this.resultMap.put("retMsg", "选取成功！");

		return "cutHeadImg";
	}
	
	
	
	@Override
	public Guest getModel() {
		return guest;
	}

	/**
	 * @param guest
	 */
	public void setGuest(Guest guest) {
		this.guest = guest;
	}

	/**
	 * @Description
	 * @return the guest
	 */
	public Guest getGuest() {
		return guest;
	}

	/**
	 * @param guestService
	 */
	public void setGuestService(GuestService guestService) {
		this.guestService = guestService;
	}

	/**
	 * @Description
	 * @return the guestService
	 */
	public GuestService getGuestService() {
		return guestService;
	}

	/**
	 * @param meetingService
	 */
	public void setMeetingService(MeetingService meetingService) {
		this.meetingService = meetingService;
	}

	/**
	 * @Description
	 * @return the meetingService
	 */
	public MeetingService getMeetingService() {
		return meetingService;
	}

	/**
	 * @param meetingId
	 */
	public void setMeetingId(Long meetingId) {
		this.meetingId = meetingId;
	}

	/**
	 * @Description
	 * @return the meetingId
	 */
	public Long getMeetingId() {
		return meetingId;
	}

	/**
	 * @param flag
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}

	/**
	 * @Description
	 * @return the flag
	 */
	public String getFlag() {
		return flag;
	}
	/**
	 * @param queryName
	 */
	public void setQueryName(String queryName) {
		this.queryName = queryName;
	}
	/**
	 * @Description
	 * @return the queryName
	 */
	public String getQueryName() {
		return queryName;
	}
	
	
	

}

package com.wondertek.meeting.action.member;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import jxl.Workbook;
import jxl.write.Alignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import com.wondertek.meeting.action.base.BaseAction;
import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.MeetingFiles;
import com.wondertek.meeting.model.MeetingMember;
import com.wondertek.meeting.model.User;
import com.wondertek.meeting.service.FileSystemService;
import com.wondertek.meeting.service.MeetingMemberService;
import com.wondertek.meeting.service.MeetingService;
import com.wondertek.meeting.service.UserService;
import com.wondertek.meeting.util.StringUtil;

public class UserExportAction extends BaseAction {

	private static final long serialVersionUID = 6368314814840069036L;
	UserService userService;
	FileSystemService fileSystemService;
	MeetingMemberService meetingMemberService;
	MeetingService meetingService;
	

	public MeetingService getMeetingService() {
		return meetingService;
	}

	public void setMeetingService(MeetingService meetingService) {
		this.meetingService = meetingService;
	}

	public MeetingMemberService getMeetingMemberService() {
		return meetingMemberService;
	}

	public void setMeetingMemberService(
			MeetingMemberService meetingMemberService) {
		this.meetingMemberService = meetingMemberService;
	}

	private final String USER_EXPORT_FOLDER = "exportUser";

	public FileSystemService getFileSystemService() {
		return fileSystemService;
	}

	public void setFileSystemService(FileSystemService fileSystemService) {
		this.fileSystemService = fileSystemService;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	/**
	 * 会议用户导出
	 * 
	 * @return
	 * @throws NumberFormatException
	 * @throws ServiceException
	 * @throws IOException
	 * @throws RowsExceededException
	 * @throws WriteException
	 */
	public String exportMeetingUser() throws NumberFormatException,
			ServiceException, IOException, RowsExceededException,
			WriteException {
		String forward = SUCCESS;

		// TODO 进行导出操作
		// 获取查询条件
		String username = this.getParameter("username");
		String mobile = this.getParameter("mobile");
		String isAdmin = this.getParameter("isAdmin");
		String meetingId = this.getParameter("meetingId");
		User user = new User();
		user.setName(username);
		user.setMobile(mobile);

		// 把PAGE_SIZE设置很大

		pageSize = 1000;
		Pager<User> pager = userService.findMeetingUserPager(
				Long.valueOf(meetingId), user, currentPage, pageSize, isAdmin);
		log.debug("meeting id is： {}", meetingId);

		List<User> userList = pager.getPageRecords();

		String[] title = { "姓名", "手机号码", "职位(通讯录)", "单位", "房间号", "显示房间号", "性别",
				"电子邮箱", "城市", "加入通讯录", "显示电话号码", "排序", "职位简称", "显示职位简称","上传资料" };
		// TODO 进行导出的操作
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String exportFileName = sdf.format(new Date());
		// 创建文件夹
		File uploadFoldPath = new File(fileSystemService.getDocumentRoot()
				+ this.USER_EXPORT_FOLDER);
		if (!uploadFoldPath.exists()) {
			uploadFoldPath.mkdirs();
		}
		// 判断文件是否存在
		String fileSaveName = exportFileName + ".xls";
		String dstPath = fileSystemService.getDocumentRoot()
				+ this.USER_EXPORT_FOLDER + File.separator + fileSaveName;
		String from = "WEB";
		String documentRoot = fileSystemService.getDocumentRoot();
		// DOCUMENT ROOT
		getRequest().setAttribute("documentRoot", documentRoot);
		getRequest().setAttribute("from", from);

		OutputStream os = new FileOutputStream(dstPath);

		// 再建完这个文件的时候再建立工作文件
		jxl.write.WritableWorkbook wwb = Workbook.createWorkbook(os);
		int rowIndex = 0;
		
		Label label;
		WritableCellFormat wc = new WritableCellFormat();
		wc.setAlignment(Alignment.CENTRE);
		//wc.set
		
		WritableSheet sheet = wwb.createSheet("会议用户导出", 0);
		label = new Label(2, rowIndex, "此文件为会议云用户导出文件");
		sheet.addCell(label);
		
		rowIndex++;
		
		// 将列标题循环添加到Label中
		for (int i = 0; i < title.length; i++) {
			label = new Label(i, rowIndex, title[i]);
			sheet.addCell(label);
		}
		rowIndex++;
	
		
		// TODO下面是添加数据
		
		if (userList != null && userList.size() > 0) {
			for (User exportUser : userList) {

				MeetingMember member = meetingMemberService.selectById(
						exportUser.getId(), Long.valueOf(meetingId));
				exportUser.setMeetingMember(member);
				// "姓名", "手机号码", "职位(通讯录)", "单位", "房间号", "显示房间号", "性别",
				// "电子邮箱", "城市", "加入通讯录", "显示电话号码", "排序", "职位简称", "显示职位简称"
				// 用户名
				label = new Label(0, rowIndex, exportUser.getName());
				sheet.addCell(label);

				label = new Label(1, rowIndex, exportUser.getMobile());
				sheet.addCell(label);

				label = new Label(2, rowIndex, exportUser.getMeetingMember()
						.getBookJob());
				sheet.addCell(label);
				// 单位
				label = new Label(3, rowIndex, exportUser.getMeetingMember()
						.getDepartment());
				sheet.addCell(label);
				// 房间号
				label = new Label(4, rowIndex, exportUser.getMeetingMember()
						.getRoomNumber());
				sheet.addCell(label);
				// 显示房间号
				label = new Label(5, rowIndex,StringUtil.null2Str(exportUser
						.getMeetingMember().getRoomNumberIsDisplay()));
				sheet.addCell(label);
				// 性别,需要翻译
				String strGender="";
				if(0==exportUser
						.getGender()){
					strGender="男";
				}else if(1==exportUser.getGender()){
					strGender="女";
				}
				label = new Label(6, rowIndex,StringUtil.null2Str(strGender));
				sheet.addCell(label);
				// 电子邮箱
				label = new Label(7, rowIndex, StringUtil.null2Str(exportUser
						.getMeetingMember().getMailbox()));
				sheet.addCell(label);
				// 城市
				label = new Label(8, rowIndex, StringUtil.null2Str(exportUser
						.getMeetingMember().getCity()));
				sheet.addCell(label);
				// 加入通讯录
				label = new Label(9, rowIndex, StringUtil.null2Str(exportUser
						.getMeetingMember().getAddInContacts()));
				sheet.addCell(label);
				// 显示电话号码
				label = new Label(10, rowIndex, StringUtil.null2Str(exportUser
						.getMeetingMember().getMobileIsDisplay()));
				sheet.addCell(label);

				// 排序
				label = new Label(11, rowIndex, StringUtil.null2Str(exportUser
						.getMeetingMember().getSortCode()));
				sheet.addCell(label);
				// 职位简称
				label = new Label(12, rowIndex, StringUtil.null2Str(exportUser
						.getMeetingMember().getJob()));
				sheet.addCell(label);
				// 显示职位简称
				label = new Label(13, rowIndex, StringUtil.null2Str(exportUser
						.getMeetingMember().getJobIsDisplay()));
				sheet.addCell(label);
				// 上传资料
				label = new Label(14, rowIndex, StringUtil.null2Str(exportUser
						.getMeetingMember().getUploadAuthority()));
				
				sheet.addCell(label);

				rowIndex++;
			}
		}

		wwb.write();
		wwb.close();

		/**
		 * 参考范文 jxl.write.Number numb = new jxl.write.Number(3, 1, 20000);
		 * sheet.addCell(numb);
		 * 
		 * String newdate = sdf.format(new Date()); label = new Label(4, 1,
		 * newdate); sheet.addCell(label);
		 * 
		 * label = new Label(5, 1, "陕西西安"); sheet.addCell(label);
		 * 
		 * 
		 * //显示布尔值
		 * 
		 * jxl.write.Boolean bool = new jxl.write.Boolean(6, 1, true);
		 * sheet.addCell(bool);
		 * 
		 * WritableCellFormat wc = new WritableCellFormat(); // 设置居中
		 * wc.setAlignment(Alignment.CENTRE); // 设置边框线 wc.setBorder(Border.ALL,
		 * BorderLineStyle.THIN); // 设置单元格的背景颜色
		 * wc.setBackground(jxl.format.Colour.YELLOW); // 设置字体
		 * jxl.write.WritableFont wfont = new jxl.write.WritableFont(
		 * WritableFont.createFont("隶书"), 10); wc.setFont(wfont); label = new
		 * Label(0, 3, "2009年总体来说，项目都比较成功，希望2010年再接再厉！", wc);
		 * sheet.addCell(label);
		 * 
		 * 
		 * sheet.mergeCells(0, 3, 6, 3);
		 */

		// 现在开始进行下载操作
		MeetingFiles meetingFiles = new MeetingFiles();
		meetingFiles.setFilePath(this.USER_EXPORT_FOLDER);
		meetingFiles.setFileName(fileSaveName);
		meetingFiles.setFileSaveName(fileSaveName);

		this.getRequest().setAttribute("meetingFiles", meetingFiles);

		return forward;

		/********** 以文件输出流的形式把文件给输出到浏览器 START ******************/

		/**
		 * HttpServletResponse response = ServletActionContext.getResponse();
		 * InputStream in=null; try {
		 * response.setContentType("application/pdf"); //此处注释
		 * 表明是可以直接用浏览器打开。加上则表示打开一个下载文件的窗口
		 * //response.setHeader("Content-Disposition", "attachment; filename=" +
		 * fileName); in = null; //文件输入流 response.setCharacterEncoding("utf-8");
		 * 
		 * in =new ByteArrayInputStream(os.write(b)); int tp = 0; byte[]
		 * tmpByte=new byte[4096]; while (true) { tp = in.read(tmpByte); if(tp
		 * <= 0){ break; }
		 * 
		 * response.getOutputStream().write(tmpByte, 0, tp); } os.flush(); }
		 * catch (Throwable e) { throw new RuntimeException("文件下载失败！", e); }
		 * finally { try { in.close(); } catch (IOException e) { throw new
		 * RuntimeException("关闭流失败！", e); } }
		 **/

	}
	
	
	
	/**
	 * 会议通讯录导出 
	 * @return
	 * @throws NumberFormatException
	 * @throws ServiceException
	 * @throws IOException
	 * @throws RowsExceededException
	 * @throws WriteException
	 */
	public String exportUserBook() throws NumberFormatException,
			ServiceException, IOException, RowsExceededException,
			WriteException {
		String forward = SUCCESS;
		// TODO 进行导出操作
		// 获取查询条件
		String username = this.getParameter("username");
		String mobile = this.getParameter("mobile");
		String isAdmin = this.getParameter("isAdmin");
		String meetingId = this.getParameter("meetingId");
		User user = new User();
		user.setName(username);
		user.setMobile(mobile);

		// 把PAGE_SIZE设置很大
		pageSize = 1000;
		Pager<User> pager = userService.findMeetingUserPager(
				Long.valueOf(meetingId), user, currentPage, pageSize, isAdmin);
		log.debug("meeting id is： {}", meetingId);

		List<User> userList = pager.getPageRecords();

		String[] title = { "姓名", "手机号码 ", "职位", "单位", "房间号", "城市",
				"电子邮箱"};
		// TODO 进行导出的操作
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String exportFileName = sdf.format(new Date());
		// 创建文件夹
		File uploadFoldPath = new File(fileSystemService.getDocumentRoot()
				+ this.USER_EXPORT_FOLDER);
		if (!uploadFoldPath.exists()) {
			uploadFoldPath.mkdirs();
		}
		// 判断文件是否存在
		String fileSaveName = exportFileName + ".xls";
		String dstPath = fileSystemService.getDocumentRoot()
				+ this.USER_EXPORT_FOLDER + File.separator + fileSaveName;
		String from = "WEB";
		String documentRoot = fileSystemService.getDocumentRoot();
		// DOCUMENT ROOT
		getRequest().setAttribute("documentRoot", documentRoot);
		getRequest().setAttribute("from", from);

		OutputStream os = new FileOutputStream(dstPath);

		// 再建完这个文件的时候再建立工作文件
		jxl.write.WritableWorkbook wwb = Workbook.createWorkbook(os);
		int rowIndex = 0;

		Label label;
		WritableCellFormat wc = new WritableCellFormat();
		wc.setAlignment(Alignment.CENTRE);
		// wc.set

		WritableSheet sheet = wwb.createSheet("会议用户导出", 0);
		sheet.setColumnView(2, 20);
		label = new Label(2, rowIndex, "通讯录");
		sheet.addCell(label);

		rowIndex++;

		// 将列标题循环添加到Label中
		for (int i = 0; i < title.length; i++) {
			label = new Label(i, rowIndex, title[i]);
			sheet.addCell(label);
		}
		rowIndex++;
		

		if (userList != null && userList.size() > 0) {
			for (User exportUser : userList) {
				
				label = new Label(0, rowIndex, exportUser.getName());
			
				//label.setCellFormat(cf)
				sheet.addCell(label);
				
				if(exportUser.getMeetingMember().getMobileIsDisplay()==1){
					label = new Label(1, rowIndex, exportUser.getMobile());
				}else{
					label = new Label(1, rowIndex,"未公开");
				}
				sheet.addCell(label);
				
				label = new Label(2, rowIndex, exportUser.getMeetingMember()
						.getJob());
				sheet.addCell(label);
				// 单位
				label = new Label(3, rowIndex, exportUser.getMeetingMember()
						.getDepartment());
				sheet.addCell(label);
				// 房间号
				label = new Label(4, rowIndex, exportUser.getMeetingMember()
						.getRoomNumber());
				sheet.addCell(label);
				// 城市
				label = new Label(5, rowIndex, StringUtil.null2Str(exportUser
						.getMeetingMember().getCity()));
				sheet.addCell(label);
				// 电子邮箱
				label = new Label(6, rowIndex, StringUtil.null2Str(exportUser
						.getMeetingMember().getMailbox()));
				sheet.addCell(label);
				
				rowIndex++;
			}
		}
		wwb.write();
		wwb.close();
		// 现在开始进行下载操作
		MeetingFiles meetingFiles = new MeetingFiles();
		meetingFiles.setFilePath(this.USER_EXPORT_FOLDER);
		meetingFiles.setFileName(fileSaveName);
		meetingFiles.setFileSaveName(fileSaveName);

		this.getRequest().setAttribute("meetingFiles", meetingFiles);

		return forward;

	}
	

}

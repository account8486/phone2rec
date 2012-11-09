package com.wondertek.meeting.action.user;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;

import com.wondertek.meeting.action.base.BaseAction;
import com.wondertek.meeting.exception.HibernateDaoSupportException;
import com.wondertek.meeting.model.Meeting;
import com.wondertek.meeting.model.MeetingMember;
import com.wondertek.meeting.model.User;
import com.wondertek.meeting.service.MeetingService;
import com.wondertek.meeting.service.UserImportService;
import com.wondertek.meeting.util.StringUtil;
import com.wondertek.meeting.util.ValidateUtil;

/**
 * 进行用户的导入操作
 * 
 * @author guoxu
 * 
 */
public class UserImportAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private File upload;
	// 上传文件名
	private String uploadFileName;
	// 上传文件类型
	private String uploadContentType;

	private UserImportService userImportService;
	private MeetingService meetingService;
	
	private Long meetingId;

	/**
	 * 进行用户的导入
	 * 
	 * @return
	 */
	public String doImportUser() {

		long start = System.currentTimeMillis();
		String meetingId = getRequest().getParameter("meetingId");
		if (meetingId == null || "".equals(meetingId)) {
			getRequest().setAttribute("retMsg", " 会议号不能为空");
		}

		// 级别
		String memberLevel = getRequest().getParameter("memberLevel");

		// 默认已经上传

		// 进行文件的导入
		//现在不进行文件的copy直接就导入
		/**
		String fileFullPath = ServletActionContext.getServletContext()
				.getRealPath(File.separator + uploadFileName);
		// 创建文件
		File dstFile = new File(fileFullPath);
		try {
			// 把临时文件内容拷贝到目标文件中
			FileOperatorUtil.copy(this.upload, dstFile);
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
		 **/
		
		// 开始解析
		List<User> importUserList = new ArrayList<User>();
		try {
			WorkbookSettings setting = new WorkbookSettings();
			java.util.Locale locale = new java.util.Locale("zh ", "CN ");
			setting.setLocale(locale);
			Workbook book = Workbook.getWorkbook(this.upload);
			
			// 获得第一个工作表对象
			Sheet sheet = book.getSheet(0);
			// 得到第一列第一行的单元格
			int rowNum = sheet.getRows();
			
			log.info("SHEET的ROWS LENGTH---" + rowNum);
			// 循环读入每一行的每一列
			int rowStart = 2; // 定义开始的一行 一般是从第二行开始的！

			for (int i = rowStart; i < rowNum; i++) {
				User importUser = new User();
				importUser.setMeetingMember(new MeetingMember());
				// 一行记录 计入到userImport
				Cell[] cells = sheet.getRow(i);
				int cellsLength = cells.length;
				
				importUser.setName(StringUtil.replaceBlank(cells[0]
				                         						.getContents()));// 姓名
				importUser.setMobile(StringUtil.replaceBlank(cells[1]
						.getContents()));// 用户手机号
				
				// 职位(通讯录)2
				if (cellsLength > 2 && cells[2] != null
						&& !"".equals(cells[2].getContents())) {
					importUser.getMeetingMember().setBookJob(
							StringUtil.replaceBlank(cells[2].getContents()));
				}
				
				//3
				// 单位
				if (cellsLength > 3) {
					importUser.getMeetingMember().setDepartment(
							StringUtil.replaceBlank(cells[3].getContents()));
				}
				
				//4
				//是否是VIP
				if (cellsLength > 4 && cells[4] != null
						&& !"".equals(cells[4].getContents())) {
					importUser.getMeetingMember().setVip(
							cells[4].getContents().toUpperCase());
				}

				// 显示房间号
				if (cellsLength > 5 && cells[5] != null
						&& !"".equals(cells[5].getContents())
						&& StringUtil.isNumber(cells[5].getContents())) {
					importUser.getMeetingMember().setRoomNumberIsDisplay(
							Integer.valueOf(cells[5].getContents()));
				}
			
				
				// 添加性别字段
				if(cellsLength>6&&cells[6] != null  ){
					String sexuality=StringUtil.replaceBlank(cells[6]
					             							.getContents());
					if(sexuality.contains("男")){
						importUser.setGender(0);
					}else if(sexuality.contains("女")){
						importUser.setGender(1);
					}else if(sexuality.contains("保密")){
						importUser.setGender(2);
					}
					
				}
				
				// 7进行判断空指针
				if(cellsLength>7&&cells[7] != null  ){
					importUser.getMeetingMember().setMailbox(StringUtil.replaceBlank(cells[7]
							.getContents())); // 邮箱
				}
				
				if(cellsLength>8&&cells[8] != null  ){
					importUser.getMeetingMember().setCity(StringUtil.replaceBlank(cells[8]
							.getContents())); // 城市
				}
				
				// 是否加入通讯录
				if (cellsLength > 9) {
					importUser.getMeetingMember().setAddInContacts(
							cells[9].getContents());
				}
				
				// 显示电话号码
				if (cellsLength > 10&& cells[10] != null
						&& !"".equals(cells[10].getContents())
						&& StringUtil.isNumber(cells[10].getContents())) {
					importUser.getMeetingMember().setMobileIsDisplay(
							Integer.valueOf(cells[10].getContents()));

				}
				
				// 11排序码
				if (cellsLength > 11 && cells[11] != null
						&& !"".equals(cells[11].getContents())
						&& StringUtil.isNumber(cells[11].getContents())) {
					importUser.getMeetingMember().setSortCode(
							Integer.valueOf(cells[11].getContents()));

				}
				
				// 12职位简称
				if (cellsLength > 12) {
					importUser.getMeetingMember().setJob(
							StringUtil.replaceBlank(cells[12].getContents()));
				}
				
				// 是否显示职位简称
				if (cellsLength > 13 && cells[13] != null
						&& !"".equals(cells[13].getContents())
						&& StringUtil.isNumber(cells[13].getContents())) {
					importUser.getMeetingMember().setJobIsDisplay(
							Integer.valueOf(cells[13].getContents()));
				}
				
				// 是否拥有上传权限
				if (cellsLength > 14 && cells[14] != null
						&& !"".equals(cells[14].getContents())) {
					importUser.getMeetingMember().setUploadAuthority(
							cells[14].getContents());
				}
	
				
				
				importUserList.add(importUser);
			}

			book.close();
			// 首先对importUserList进行校验
			String tips = validateImportUserList(importUserList);
			log.debug("validateImportUserList tips："+tips);
			// 提示语不为空 则表明校验未通过
			if ("".equals(tips)) {
				// 导入成功
				HashMap tipsMap = userImportService.saveImportUser(
						importUserList, Long.valueOf(meetingId),
						Integer.valueOf(memberLevel));
				getRequest().setAttribute("importFlag", "S");// 成功导入标志
				String repeatedUserMobiles=String.valueOf(tipsMap.get("repeatedUserMobiles"));
				if(StringUtil.isNotEmpty(repeatedUserMobiles)){
					repeatedUserMobiles=",模板中存在如下重复的手机号码 :"+repeatedUserMobiles;
				}
				getRequest().setAttribute(
						"importMsg",
						"成功导入" +String.valueOf(tipsMap.get("importNum")) + "个用户.其中新增了"
								+ String.valueOf(tipsMap.get("addNum"))
								+ "个用户,更新了"
								+ String.valueOf(tipsMap.get("updateNum"))
								+ "个用户"+repeatedUserMobiles);
			} else {
				// 导入失败
				getRequest().setAttribute("importFlag", "F");// 成功导入标志
				// 返回提示信息
				getRequest().setAttribute("importMsg", tips);
			}
		}catch(jxl.read.biff.BiffException jxl ){
			jxl.printStackTrace();
			getRequest().setAttribute("importFlag", "F");// 导入失败标志
			getRequest().setAttribute("importMsg", "导入失败, 文件格式不正确。");
			
		}catch (Exception e) {
			e.printStackTrace();
			getRequest().setAttribute("importMsg", "导入失败, 请稍后重试或联系系统管理员。");
			getRequest().setAttribute("importFlag", "F");// 导入失败标志
			log.debug("导入失败,具体原因如下：" + e.getMessage());
		}finally{
			log.debug("导入用户共花费{}秒",(System.currentTimeMillis()-start)/1000);
		}

		getRequest().setAttribute("meetingId", meetingId);
		// 解析完毕 开始进行数据库的对比及导入

		//判断是否是会议向导提交的请求
		String guideStep = getRequest().getParameter("guideStep");
		if("step2".equals(guideStep)) {
			return "guide_step3";
		}
		
		return SUCCESS;

	}

	/**
	 * 校验ImportUserList
	 * 
	 * @return
	 */
	public String validateImportUserList(List<User> importUserList) {
		String tips = "";
		if (importUserList != null && importUserList.size() > 0) {
			// 校验
			for (User user : importUserList) {
				// 校验手机号或密码
				if ("".equals(user.getMobile()) || "".equals(user.getName())) {
					tips = "电话号码不能为空并且用户名不能为空,请检查模板中数据！";
					break;
				}
				
				//校验手机号是否正确,如果存在不合法的
				if(StringUtil.isNotMobile(user.getMobile())){
					tips = "手机号码必须为有效可用的手机号码,请检查模板手机号码"+user.getMobile();
					break;
				}
				
				// 排序码有值并且还不为数字型 则不允许输入
				if (user.getMeetingMember().getSortCode() != null && !"".equals(user.getMeetingMember().getSortCode())
						&& StringUtil.isNotNumber(user.getMeetingMember().getSortCode())) {
					log.debug(String.valueOf(user.getMeetingMember().getSortCode() == null
							|| "".equals(user.getMeetingMember().getSortCode())));
					tips = "显示优先级必须为数字类型,请检查模板中数据！";
					break;
				}

				if (StringUtil.isNotEmpty(user.getMeetingMember().getAddInContacts())
						&& !"Y".equals(user.getMeetingMember().getAddInContacts())
						&& !"N".equals(user.getMeetingMember().getAddInContacts())) {
					tips = "加入通讯录必须为Y或者N,请检查模板中数据！";
					break;
				}
				
				//增加邮箱的校验,如果邮箱不为空并且格式不正确的话
				if (StringUtil.isNotEmpty(user.getMeetingMember().getMailbox())
						&& ValidateUtil.isNotEmail(user.getMeetingMember()
								.getMailbox())) {
					tips = "电子邮箱:"+user.getMeetingMember().getMailbox()
							+ "不是合法有效的电子邮箱名,请检查！";
					break;
				}
				
				//判断房间号
				if (StringUtil.isNotEmpty(user.getMeetingMember()
						.getRoomNumber())
						&& user.getMeetingMember().getRoomNumber().length() > 20) {
					tips = "房间号:" + user.getMeetingMember().getRoomNumber()
							+ "长度大于20个字符,请精简！";
					break;
				}
				
				
			}

			
			/**
			String repeatMobile="";
			for (int i = 0; i < importUserList.size()-1; i++) {
				// 验证手机号是否重复
				String mobileNo=importUserList.get(i).getMobile();
				for(int k=i+1;k<importUserList.size();k++){
					String cmpMobileNo=importUserList.get(k).getMobile();
					//如果有两个电话号码重复
					if(mobileNo.equals(cmpMobileNo)){
						repeatMobile+=mobileNo+",";
					}
				}
			}
			
			//号码重复
			if(repeatMobile!=null&&!"".equals(repeatMobile)&&repeatMobile.length()>1){
				repeatMobile=repeatMobile.substring(0,repeatMobile.length()-1);
				tips=tips+"模板中手机号码重复,号码为:"+repeatMobile;
			}	*/
				
		}
	
			
		return tips;
	}

	/**
	 * 预备导入会议用户
	 * 
	 * @return
	 */
	public String preImportUser() {
		String forward = SUCCESS;
		String meetingId = getRequest().getParameter("meetingId");
		try {
			Meeting meeting = meetingService.getMeetingByPk(Long
					.valueOf(meetingId));
			if (meeting != null) {
				// 把信息设值
				getRequest().setAttribute("meeting", meeting);
				getRequest().setAttribute("retcode", 0);
				forward = SUCCESS;
			} else {
				getRequest().setAttribute("retcode", 0);
			}

		} catch (NumberFormatException e) {
			e.printStackTrace();
			log.debug(e.getMessage());
		} catch (HibernateDaoSupportException e) {
			e.printStackTrace();
			log.debug(e.getMessage());
		}

		return forward;
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

	public void setUserImportService(UserImportService userImportService) {
		this.userImportService = userImportService;
	}

	public void setMeetingService(MeetingService meetingService) {
		this.meetingService = meetingService;
	}

	public Long getMeetingId() {
		return meetingId;
	}

	public void setMeetingId(Long meetingId) {
		this.meetingId = meetingId;
	}

}

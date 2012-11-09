package com.wondertek.meeting.action.meeting;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;

import org.apache.commons.lang.StringUtils;

import com.wondertek.meeting.action.base.BaseAction;
import com.wondertek.meeting.common.Constants;
import com.wondertek.meeting.common.RetCode;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.AdminUser;
import com.wondertek.meeting.model.GroupPlan;
import com.wondertek.meeting.model.GroupPlanDetail;
import com.wondertek.meeting.model.JsonUser;
import com.wondertek.meeting.model.Meeting;
import com.wondertek.meeting.model.MeetingAgenda;
import com.wondertek.meeting.model.MeetingDinner;
import com.wondertek.meeting.model.User;
import com.wondertek.meeting.model.UserTableInfo;
import com.wondertek.meeting.model.UserTableInfoWithDate;
import com.wondertek.meeting.service.GroupPlanService;
import com.wondertek.meeting.service.MeetingDinnerService;
import com.wondertek.meeting.service.MeetingGroupService;
import com.wondertek.meeting.service.MeetingService;
import com.wondertek.meeting.util.StringUtil;

/**
 * 会议用餐信息
 * @author John Tang
 *
 */
public class MeetingDinnerAction extends BaseAction {

	private static final long serialVersionUID = 527033506779L;
	
	private MeetingDinnerService meetingDinnerService;
	private MeetingService meetingService;
	
//	private DinnerTableService dinnerTableService;
	private GroupPlanService groupPlanService;
	private MeetingGroupService meetingGroupService;
	
	private Long meetingId;
	private String address;
	private String dinnerDate;
	
	private MeetingDinner dinner;
	private List<MeetingDinner> dinnerList;
	private String tableCode; //分桌号
	private String memberstr; //分桌的用户ID数组
	
	private File upload; //导入的分桌数据
	// 上传文件名
	private String uploadFileName;
	// 上传文件类型
	private String uploadContentType;

	public String list(){
		String from=getParameter("from");

		
		log.debug("list dinner meetingId = {}",meetingId);
		log.debug("list dinner address = {}",address);
		log.debug("list dinner dinnerDate = {}",dinnerDate);
		try {
			//设置用餐类型信息
			setDinnerTypeInfo();
			
			getRequest().setAttribute("dinnerList",meetingDinnerService.findDinnerList(address,meetingId,dinnerDate));
		} catch (ServiceException e) {
			log.error("list meeting dinner error ",e);
		}
		
		if(StringUtil.isNotEmpty(from)&&from.contains("guide")){
		   return "guide";
		}
		
		return SUCCESS;
	}
	
	private void setDinnerTypeInfo(){
		//设置用餐类型信息
		getRequest().setAttribute("dinnerTypeList", Constants.dinnerTypeList);
		getRequest().setAttribute("dinnerTypeNameMap", Constants.dinnerTypeNameMap);
		getRequest().setAttribute("dinnerTypeIdMap", Constants.dinnerTypeIdMap);
	}
	
	public String gotoAdd(){
		//设置用餐类型信息
		setDinnerTypeInfo();
		return SUCCESS;
	}
	
	public String gotoBatchAdd(){
		//设置用餐类型信息
		setDinnerTypeInfo();
		return SUCCESS;
	}
	
	public String batchAdd(){
		try {
			setDinnerTypeInfo();
			if(dinnerList != null && dinnerList.size() > 0){
				log.debug("dinnerList.size() = "+dinnerList.size());
				for(MeetingDinner dinner : dinnerList){
					log.debug("dinner= {} ",dinner);
					if(dinner!=null){
						dinner.setCreator(getAdminUserFromSession());
						dinner.setCreateTime(new Date());
						dinner.setModifier(getAdminUserFromSession());
						dinner.setModifyTime(new Date());
						
						Long did =  meetingDinnerService.add(dinner);
						log.debug("dinner id====: " + did);
					}
				}
			}
			resultMap.put("retmsg", "保存成功");
		} catch (Exception e) {
			log.error("批量添加失败：",e);
			resultMap.put("retmsg", "保存失败");
		}
		return SUCCESS;
	}
	
	public String add(){
		String guideStep=getRequest().getParameter("guideStep");
		
		try {
			log.debug("current user {}",getAdminUserIdFromSession());
			//设置用餐类型信息
			setDinnerTypeInfo();
			log.debug("添加用餐信息 {}",dinner);
			dinner.setCreator(getAdminUserFromSession());
			dinner.setCreateTime(new Date());
			dinner.setModifier(getAdminUserFromSession());
			dinner.setModifyTime(new Date());
			
			Long did =  meetingDinnerService.add(dinner);
			log.debug("dinner id====: " + did);
			
			resultMap.put("retcode", RetCode.SUCCESS);
			resultMap.put("retmsg", "保存成功");
			resultMap.put("retdata", dinner);
			
			if("step6".equals(guideStep)){
				//return "step6";
			}
			
			return SUCCESS;
		} catch (Exception e) {
			log.error("添加用餐信息失败,",e);
			resultMap.put("result", RetCode.FAIL);
			resultMap.put("retmsg", e.getMessage());
			return SUCCESS;
		}
		
	
	}
	
	public String modify(){
		
		try {
			
			log.debug("current user {}",getAdminUserIdFromSession());
			//设置用餐类型信息
			setDinnerTypeInfo();
			log.debug("更新用餐信息 {}",dinner);
			dinner.setModifier(getAdminUserFromSession());
			dinner.setModifyTime(new Date());
			meetingDinnerService.modify(dinner);
			
			resultMap.put("retcode", RetCode.SUCCESS);
			resultMap.put("retmsg", "保存成功");
			resultMap.put("retdata", dinner);
			return SUCCESS;
		} catch (Exception e) {
			log.error("添加用餐信息失败,",e);
			
			resultMap.put("result", RetCode.FAIL);
			resultMap.put("retmsg", e.getMessage());
			return SUCCESS;
		}
	}
	
	public String delete(){
		try {
			log.debug("current user {}",getAdminUserIdFromSession());
			//设置用餐类型信息
			setDinnerTypeInfo();
			log.debug("删除用餐信息 dinnerId = {}",dinner.getId());
			meetingDinnerService.deleteDinnerById(dinner.getId());

			resultMap.put("retmsg", "成功删除用餐信息");
			resultMap.put("retcode", RetCode.SUCCESS);
		} catch (Exception e) {
			log.error("删除用餐信息失败,",e);
			resultMap.put("retmsg", "删除用餐信息失败");
			resultMap.put("retcode", RetCode.FAIL);
		}
		return SUCCESS;
	}
	
	public String getMeetingDinnerById(){
		try {
			String returnType = this.getParameter("returnType");
			//设置用餐类型信息
			setDinnerTypeInfo();
			if(dinner!=null){
				log.debug("dinner id = {}",dinner.getId());
				log.debug("returnType = {}",returnType);
				dinner =  meetingDinnerService.findById(dinner.getId());
				//判断跳转页面，如果是在会议列表点击编辑，则跳转到admin的portal页面
				if(returnType!=null&&returnType.equalsIgnoreCase("modify"))
				{
					return "modify";
				}else if(returnType!=null&&returnType.equalsIgnoreCase("import")){
					return "import";
				}
				else if(returnType!=null&&returnType.equalsIgnoreCase("choosetable")){
					getRequest().setAttribute("waitUserList", meetingDinnerService.getUserListNotInDinner(dinner.getMeetingId(), dinner.getId()));
					getRequest().setAttribute("choosedUserTableList", meetingDinnerService.getUserListInDinner(dinner.getMeetingId(), dinner.getId()));
					return "choosetable";
				}
			}
			return SUCCESS;
		} catch (ServiceException e) {
			log.error("查询用餐信息失败,",e);
			return INPUT;
		}
	}
	
	/**portal、客户端查询该用户该会议的用餐信息
	 * 192.168.1.88:8080/meeting/client/pri/dinner/getDinnerInfo.action?meetingId=3&userId=35
	 * */
	public String getDinnerInfo() {
		
		try {
			log.debug("查询该用户该会议的用餐信息 meetingId = {}",new String[]{""+meetingId,""+this.getUserIdFromSession()});
			if(meetingId == null){
				log.warn("meeting id is null");
				return SUCCESS;
			}
			List<UserTableInfoWithDate> list = meetingDinnerService.findMemberDinnerList(meetingId, this.getUserIdFromSession());
			resultMap.put("result", list);
			getRequest().setAttribute("dinnerInfoList", list);
		} catch (Exception e) {
			log.error("查询该用户该会议的用餐信息 ERROR ",e);
		}
		return SUCCESS;
	}
	
	public String wapView() {
		try {
			final String meetingId = getParameter("meetingId");
			final String index = getParameter("index");
			int idx = 0;
			if (StringUtils.isNotEmpty(index)) {
				idx = Integer.valueOf(index);
			}
			List<UserTableInfoWithDate> view = meetingDinnerService.findMemberDinnerList(Long.valueOf(meetingId), this.getUserIdFromSession());
			if(view.size() > 0){
				getRequest().setAttribute("view", view.get(idx));
				getRequest().setAttribute("preIdx", idx - 1);
				getRequest().setAttribute("nextIdx", idx + 1);
				getRequest().setAttribute("lastIdx", view.size());
				getRequest().setAttribute("meetingId", meetingId);
			}
			return SUCCESS;
		} catch (Exception e) {
			log.error("view dinner from wp: ", e);
			return ERROR;
		}
	}
	
	
	/**将用户加入该分桌信息表dinner_table*/
	public String addUserToDinnerTable() {
		
		try {
			log
			.debug(
					"addUserToDinnerTable memberstr = {}, tableCode = {}, dinner.getId()= {}, meetingId = {}",
					new String[] { memberstr, tableCode,
							"" + dinner.getId(), "" + meetingId });
			//更新分桌信息
			meetingDinnerService.addUserToDinnerTable(memberstr, tableCode, dinner.getId(), meetingId);
			
			//2011-12-12 用刷新页面替代用js刷新
			//setUserTableInfo();
			
			resultMap.put("retcode", RetCode.SUCCESS);
			resultMap.put("retmsg", "保存成功");
			log.debug("添加会议用餐分桌信息成功");
		} catch (Exception e) {
			log.error("addUserToTableInfo error,",e);
			resultMap.put("retmsg", "保存失败");
			resultMap.put("retcode", RetCode.FAIL);
		}
		return SUCCESS;
	}
	
	/**将选中用户移出该分桌信息表dinner_table进行重新分配*/
	public String removeUserFromDinnerTable() {
		
		try {
			log
					.debug(
							"addUserToDinnerTable memberstr = {}, tableCode = {}, dinner.getId()= {}, meetingId = {}",
							new String[] { memberstr, tableCode,
									"" + dinner.getId(), "" + meetingId });
			//更新分桌信息
			meetingDinnerService.removeUserFromDinnerTable(dinner.getId(), memberstr);
			
			//2011-12-12 用刷新页面替代用js刷新
			//setUserTableInfo();
			
			resultMap.put("retcode", RetCode.SUCCESS);
			resultMap.put("retmsg", "移除成功");
			log.debug("删除用户会议用餐分桌信息成功");
		} catch (Exception e) {
			log.error("removeUserFromDinnerTable error,",e);
			resultMap.put("retmsg", "移除失败");
			resultMap.put("retcode", RetCode.FAIL);
		}
		return SUCCESS;
	}

	/**设置页面所需分桌信息
	private void setUserTableInfo() throws ServiceException {
		dinner =  meetingDinnerService.findById(dinner.getId());
		//未分桌的用户
		log.debug("查询未分桌的用户");
		List<JsonUser> waitUserList = meetingDinnerService.getUserListNotInDinner(dinner.getMeetingId(), dinner.getId());
		resultMap.put("waitUserList", waitUserList);
		//已分桌的用户座位信息
		log.debug("查询已分桌的用户座位信息");
		List<UserTableInfo> choosedUserTableList = meetingDinnerService.getUserListInDinner(dinner.getMeetingId(), dinner.getId());
		resultMap.put("choosedUserTableList",choosedUserTableList );
	}*/
	
	/**
	 *导入用餐计划
	 */
	public String doImportPlan() {
		
		if(meetingId==null || 0 == meetingId){
			getRequest().setAttribute("retMsg", "请先选择会议信息");
			return INPUT;
		}
		Workbook book = null;
		// 进行文件的导入
		try {
			Meeting meeting = meetingService.findById(meetingId);
			if(meeting==null){
				getRequest().setAttribute("retMsg", "会议信息不存在");
				return INPUT;
			}
			
			WorkbookSettings setting = new WorkbookSettings();
			java.util.Locale locale = new java.util.Locale("zh ", "CN ");
			setting.setLocale(locale);
			book = Workbook.getWorkbook(this.upload);
			
			// 获得第一个工作表对象
			Sheet sheet = book.getSheet(0);
			// 得到第一列第一行的单元格
			int rowNum = sheet.getRows();
			// 循环读入每一行的每一列
			int rowStart = 2; // 定义开始的一行 一般是从第二行开始的！
			
			List<MeetingDinner> dinnerList = new ArrayList<MeetingDinner>();
			Date now = new Date();
			for (int i = rowStart; i < rowNum; i++) {
				Cell[] cells = sheet.getRow(i);
				
				String date = StringUtil.trim(cells[0].getContents()).replace("\"", "");//日期
				String address = StringUtil.trim(cells[1].getContents());//地点
				String section = StringUtil.trim(cells[2].getContents());//时段
				String startTime = StringUtil.trim(cells[3].getContents());//开始时间
				String endTime = StringUtil.trim(cells[4].getContents());//结束时间
				String type = StringUtil.trim(cells[5].getContents());//类型
				String comments = "";
				if(cells.length > 6){
					comments = StringUtil.trim(cells[6].getContents());//备注
					if(comments.length() > 50){
						getRequest().setAttribute("retMsg", "备注最多50个字");
						return INPUT;
					}
				}
				
				log.debug("date {},address {},section {},startTime {},endTime {},type {}",new String[]{date,address,section,startTime,endTime,type});
				//判断是否为空
				if(StringUtil.isEmpty(date) ||StringUtil.isEmpty(section)
						|| StringUtil.isEmpty(address) || address.length() > 50
						|| StringUtil.isEmpty(startTime)
						|| StringUtil.isEmpty(endTime)
						|| StringUtil.isEmpty(type)
				){
					//忽略该记录
					continue;
				}
				//判断日期格式
				if(!StringUtil.isDate(date,"yyyy年MM月dd日")) continue;
				//判断时段
				if(!sectionMap.containsKey(section)) continue;
				//判断开始时间
				if(!StringUtil.isDate(startTime,"HH:mm")) continue;
				//判断结束时间
				if(!StringUtil.isDate(endTime,"HH:mm")) continue;
				//判断类型
				if(!Constants.dinnerTypeNameMap.containsKey(type)) continue;
				
				MeetingDinner dinner = new MeetingDinner();
				dinner.setDinnerDate(date);
				dinner.setAddress(address);
				dinner.setMeetingId(meetingId);
				dinner.setStartTime(startTime);
				dinner.setEndTime(endTime);
				dinner.setSection(sectionMap.get(section));
				dinner.setType(Constants.dinnerTypeNameMap.get(type).getId());
				
				dinner.setCreateTime(now);
				dinner.setModifyTime(now);
				dinner.setCreator(getAdminUserFromSession());
				dinner.setModifier(getAdminUserFromSession());
				dinner.setComments(comments);
				dinnerList.add(dinner);
			}
			
			meetingDinnerService.saveImportDinnerPlan(dinnerList);
		} catch (jxl.read.biff.BiffException jxl) {
			log.error("导入用餐计划导入失败, 文件格式不正确。");
			getRequest().setAttribute("retMsg", "导入失败, 文件格式不正确。");
			return INPUT;
		} catch (Exception e) {
			log.error("import dinner plan error ",e);
			getRequest().setAttribute("retMsg", "导入失败, 请稍后重试或联系系统管理员。");
			return INPUT;
		}finally{
			if(book != null){
				book.close();
			}
		}
		
		getRequest().setAttribute("retMsg", "成功导入用餐计划");
		return SUCCESS;
		
	}
	
	static Map<String,String> sectionMap = new HashMap<String,String>();
	static {
		sectionMap.put("早餐","1");
		sectionMap.put("中餐","2");
		sectionMap.put("晚餐","3");
	}
	
	/**
	 *导入分桌信息 
	 */
	public String doImport() {

		if(dinner==null||dinner.getId() == null || 0 == dinner.getId()){
			getRequest().setAttribute("retMsg", "请先选择用餐信息");
			return INPUT;
		}
		
		// 进行文件的导入
		try {
			dinner = meetingDinnerService.findById(dinner.getId());
			if(dinner==null||dinner.getId() == null || 0 == dinner.getId()){
				getRequest().setAttribute("retMsg", "用餐信息不存在");
				return INPUT;
			}
			WorkbookSettings setting = new WorkbookSettings();
			java.util.Locale locale = new java.util.Locale("zh ", "CN ");
			setting.setLocale(locale);

			Workbook book = Workbook.getWorkbook(this.upload);
			// 获得第一个工作表对象
			Sheet sheet = book.getSheet(0);
			// 得到第一列第一行的单元格
			int rowNum = sheet.getRows();
			// 循环读入每一行的每一列
			int rowStart = 2; // 定义开始的一行 一般是从第二行开始的！

			List<UserTableInfo> importUserTableInfoList = new ArrayList<UserTableInfo>();
			for (int i = rowStart; i < rowNum; i++) {
				UserTableInfo tableInfo = new UserTableInfo();
				Cell[] cells = sheet.getRow(i);
				//这两个格式不正确
				if(StringUtil.isEmpty(cells[0].getContents()) ||StringUtil.isEmpty(cells[2].getContents())
						|| !StringUtil.isMobile(cells[0].getContents()) || cells[2].getContents().trim().length() > 50
				){
					//忽略该记录
					continue;
				}
				tableInfo.setMobile(StringUtil.trim(cells[0].getContents()));// 用户手机号
				tableInfo.setName(StringUtil.trim(cells[1].getContents()));// 用户名
				tableInfo.setTableCode(StringUtil.trim(cells[2].getContents())); // 桌位号
				tableInfo.setDinnerId(dinner.getId());//用餐信息ID
				importUserTableInfoList.add(tableInfo);
			}

			book.close();
			meetingDinnerService.saveImportDinnerInfo(importUserTableInfoList,dinner);
		} catch (Exception e) {
			log.error("悲剧,使用JXL导入分桌数据的时候报错了!",e);
			getRequest().setAttribute("retMsg", "导入数据失败，请稍后重试");
			return INPUT;
		}
		getRequest().setAttribute("retMsg", "成功导入分桌信息");
		return SUCCESS;

	}
	
	private GroupPlan oldGroupPlan;
	private Long groupPlanId;// 选择的分组模版ID
	/** 到选择分组模版的页面 */
	public String choose() {
		try {
			dinner = meetingDinnerService.findById(dinner.getId());
			// 根据用餐ID查找当前选择的分组模版ID
			oldGroupPlan = groupPlanService.findByRelationId(dinner.getId(), Constants.GROUP_PLAN_TYPE.DINNER);
			// 查找出所有的议程相关的分组模版
			List<GroupPlan> groupPlanList = groupPlanService.findPlanListByType(Constants.GROUP_PLAN_TYPE.DINNER,
					dinner.getMeetingId());
			getRequest().setAttribute("groupPlanList", groupPlanList);
		} catch (Exception e) {
			log.error("dinner choose groupplan ", e);
		}
		return SUCCESS;
	}

	/** 保存分组的值 */
	public String doChoose() {
		try {

			log.debug("dinner.getId() = {}", dinner.getId());
			log.debug("groupPlanId = ", groupPlanId);
			meetingGroupService.updateByRelation(groupPlanId, Constants.GROUP_PLAN_TYPE.DINNER, dinner.getId());
			resultMap.put("retmsg", "保存成功");
			resultMap.put("retcode", RetCode.SUCCESS);
		} catch (Exception e) {
			log.error("dinner choose groupplan ", e);
			resultMap.put("retmsg", "保存失败");
			resultMap.put("retcode", RetCode.FAIL);
		}
		return SUCCESS;
	}
	
	/***查询用餐分组组员信息
	 * /meeting/client/pri/meeting/getGroupInfoForDinner.action?userId=3540&dinner.id=
	 * */
	public String getGroupInfo(){
		try {
			GroupPlanDetail group = groupPlanService.getGroupDetailInfo(this.getUserIdFromSession(), Constants.GROUP_PLAN_TYPE.DINNER, dinner.getId());
			List<JsonUser> members = groupPlanService.getMemberByGroupId(group.getId());
			resultMap.put("retcode", RetCode.SUCCESS);
			resultMap.put("retmsg", "成功");
			resultMap.put("group", group);
			resultMap.put("members", members);
		} catch (Exception e) {
			resultMap.put("retcode", RetCode.FAIL);
			resultMap.put("retmsg", "失败");
			log.error("getGroupInfo for dinner error ",e);
		}
		return SUCCESS;
	}
	
	/***web,wap显示该组成员信息*/
	public String showGroupInfo(){
		try {
			GroupPlanDetail group = groupPlanService.getGroupDetailInfo(this.getUserIdFromSession(), Constants.GROUP_PLAN_TYPE.DINNER, dinner.getId());
			List<JsonUser> members = groupPlanService.getMemberByGroupId(group.getId());
			getRequest().setAttribute("group", group);
			getRequest().setAttribute("members", members);
		} catch (Exception e) {
			log.error("getGroupInfo for dinner error ",e);
		}
		return SUCCESS;
	}

	public MeetingDinnerService getMeetingDinnerService() {
		return meetingDinnerService;
	}

	public void setMeetingDinnerService(MeetingDinnerService meetingDinnerService) {
		this.meetingDinnerService = meetingDinnerService;
	}


	public Long getMeetingId() {
		return meetingId;
	}


	public void setMeetingId(Long meetingId) {
		this.meetingId = meetingId;
	}


	public MeetingDinner getDinner() {
		return dinner;
	}


	public void setDinner(MeetingDinner dinner) {
		this.dinner = dinner;
	}
	
	public String getMemberstr() {
		return memberstr;
	}

	public void setMemberstr(String memberstr) {
		this.memberstr = memberstr;
	}

	public String getTableCode() {
		return tableCode;
	}

	public void setTableCode(String tableCode) {
		this.tableCode = tableCode;
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

	public MeetingService getMeetingService() {
		return meetingService;
	}

	public void setMeetingService(MeetingService meetingService) {
		this.meetingService = meetingService;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDinnerDate() {
		return dinnerDate;
	}

	public void setDinnerDate(String dinnerDate) {
		this.dinnerDate = dinnerDate;
	}

	public GroupPlanService getGroupPlanService() {
		return groupPlanService;
	}

	public void setGroupPlanService(GroupPlanService groupPlanService) {
		this.groupPlanService = groupPlanService;
	}

	public MeetingGroupService getMeetingGroupService() {
		return meetingGroupService;
	}

	public void setMeetingGroupService(MeetingGroupService meetingGroupService) {
		this.meetingGroupService = meetingGroupService;
	}

	public GroupPlan getOldGroupPlan() {
		return oldGroupPlan;
	}

	public void setOldGroupPlan(GroupPlan oldGroupPlan) {
		this.oldGroupPlan = oldGroupPlan;
	}

	public Long getGroupPlanId() {
		return groupPlanId;
	}

	public void setGroupPlanId(Long groupPlanId) {
		this.groupPlanId = groupPlanId;
	}
	
	public Long getUserId() {
		return this.getUserIdFromSession();
	}

	public List<MeetingDinner> getDinnerList() {
		return dinnerList;
	}

	public void setDinnerList(List<MeetingDinner> dinnerList) {
		this.dinnerList = dinnerList;
	}
}

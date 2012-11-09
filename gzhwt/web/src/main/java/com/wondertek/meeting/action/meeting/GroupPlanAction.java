package com.wondertek.meeting.action.meeting;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;

import com.wondertek.meeting.action.base.BaseAction;
import com.wondertek.meeting.common.Constants;
import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.common.RetCode;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.GroupPlan;
import com.wondertek.meeting.model.GroupPlanDetail;
import com.wondertek.meeting.model.ImportGroupPlanData;
import com.wondertek.meeting.model.JsonUser;
import com.wondertek.meeting.service.GroupPlanDetailService;
import com.wondertek.meeting.service.GroupPlanService;
import com.wondertek.meeting.util.DateUtil;
import com.wondertek.meeting.util.FileOperatorUtil;
import com.wondertek.meeting.util.StringUtil;

/**
 * 分组计划
 * @author John Tang
 *
 */
public class GroupPlanAction extends BaseAction {
	private static final long serialVersionUID = -5806194905818632448L;
	private GroupPlanService groupPlanService;
	private GroupPlanDetailService groupPlanDetailService;

	public GroupPlanService getGroupPlanService() {
		return groupPlanService;
	}

	public void setGroupPlanService(GroupPlanService groupPlanService) {
		this.groupPlanService = groupPlanService;
	}
	
	private Long meetingId;
	private Long groupId;
	private File upload;
	private String uploadFileName;
	private GroupPlan groupPlan;
	private GroupPlanDetail groupPlanDetail;
	
	private String recieverIds;
	
	/**根据会议ID查询出所有的分组计划模版信息*/
	public String list(){
		try {
			getRequest().setAttribute("list", groupPlanService.listByMeetingId(meetingId));
		} catch (Exception e) {
			log.error("list group plan error",e);
		}
		return SUCCESS;
	}
	
	/**添加一个分组计划信息*/
	public String add(){
		//导入分组模版
		Workbook book = null;
		long start = System.currentTimeMillis();
		
		if(this.upload!=null){
		try {
			File saveRealDir = new File(getDocumentRoot()+Constants.UPLOAD_GROUP_PLAN_PATH);
			if(!saveRealDir.exists()){
				saveRealDir.mkdirs();
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

			Map<String,List<ImportGroupPlanData>> importDataMap = new HashMap<String,List<ImportGroupPlanData>>();
			int count = 0;
			Map<String,Set<String>> groupMemberMap = new HashMap<String,Set<String>>();
			for (int i = rowStart; i < rowNum; i++) {
				int memberSortCode = 0;
				Cell[] cells = sheet.getRow(i);
				
				if(cells.length < 3){
					log.warn("分组计划列数不够，忽略");
					continue;
				}
				
				//组名
				if(StringUtil.isEmpty(cells[0].getContents()) 
						|| cells[0].getContents().trim().length() > 50){
					getRequest().setAttribute("retMsg", "组名不能为空，并且不能超过50个字");
					return INPUT;
				}
				
				//详细内容
				if(!StringUtil.isEmpty(cells[1].getContents()) 
						&& cells[1].getContents().trim().length() > 500){
					getRequest().setAttribute("retMsg", cells[0].getContents()+"详细内容不能超过500个字");
					return INPUT;
				}
				
				//人员信息
				if(StringUtil.isEmpty(cells[2].getContents())){
					getRequest().setAttribute("retMsg", cells[0].getContents()+"人员不能为空");
					return INPUT;
				}
				
				//解析该组人员数据
				//替换掉所有的空格
				String members = StringUtil.replaceBlank(cells[2].getContents());
				//替换中文标点为英文标点
				members = members.replaceAll("，", ",").replaceAll("（", "(").replaceAll("）", ")");
				
				String[] memberArray = members.split(",");
				for(String member : memberArray){
					ImportGroupPlanData groupInfo = new ImportGroupPlanData();
					groupInfo.setGroupName(StringUtil.trim(cells[0].getContents()));//组名
					groupInfo.setDetail(StringUtil.trim(cells[1].getContents()).replace("\n", "<br />"));//详细内容
					groupInfo.setShowIndex(i-1);//显示序号
					
					String mobile = "";
					String username = member;
					if(member.indexOf("(") > 1 && member.indexOf(")") > 1){ //解析手机号
						mobile = member.substring(member.indexOf("(")+1, member.indexOf(")"));
						username = member.substring(0, member.indexOf("("));
					}
					
					if(StringUtil.isEmpty(username)){
						continue;
					}
					groupInfo.setUserName(username); // 用户名
					groupInfo.setMobile(mobile);// 用户手机号
					
//					log.debug("{}",groupInfo);
					if(!StringUtil.isEmpty(groupInfo.getMobile()) && !StringUtil.isMobile(groupInfo.getMobile())){
						//如果输入了手机号，并且该手机号格式不正确
						getRequest().setAttribute("retMsg", groupInfo.getUserName()+"的手机号码格式不正确");
						return INPUT;
					}
					
					//判断一个用户是否属于多个组
					String name = groupInfo.getUserName();
					if(groupMemberMap.containsKey(name)){
						
						if(StringUtil.isEmpty(groupInfo.getMobile()) || groupMemberMap.get(name).contains("")){
							getRequest().setAttribute("retMsg", "导入模版中"+name+"存在重名 ，请修改导入模版，在每位"+name+"之后补充手机号码，如"+name+"(1234567890)");
							return INPUT;
							
						}else if(groupMemberMap.get(name).contains(groupInfo.getMobile())){
							getRequest().setAttribute("retMsg", name+"("+mobile+")有重复记录");
							return INPUT;
						}
					}else{
						groupMemberMap.put(name, new HashSet<String>());
					}
					//将手机号保存，以便后续校验是否有重复的用户
					groupMemberMap.get(name).add(groupInfo.getMobile());
					
					if(importDataMap.get(groupInfo.getGroupName()) == null){
						importDataMap.put(groupInfo.getGroupName(), new ArrayList<ImportGroupPlanData>());
					}
					
					List<ImportGroupPlanData> groupList = importDataMap.get(groupInfo.getGroupName());
					groupInfo.setMemberSortCode(memberSortCode++);//组内成员排序
					groupList.add(groupInfo);
					count++;
					//分组人员解析结束
				}
			}

			if(count == 0){
				getRequest().setAttribute("retMsg", "没有合格的数据");
				return INPUT;
			}
			String saveDir = Constants.UPLOAD_GROUP_PLAN_PATH+DateUtil.getFormatDate(new Date())+File.separator;
			//创建目录
			File dstDir = new File(getDocumentRoot()+saveDir);
			if(!dstDir.exists()){
				dstDir.mkdirs();
			}
			
			String savePath = saveDir+DateUtil.formatLong2DateString(System.currentTimeMillis())+"_"+uploadFileName;
			String fileFullPath = getDocumentRoot()+savePath;
			// 创建文件
			File dstFile = new File(fileFullPath);
			// 把临时文件内容拷贝到目标文件中
			FileOperatorUtil.copy(this.upload, dstFile);
			String downFileUrl = getServerUrl()+savePath;
			log.debug("downFileUrl {}",downFileUrl);
			
			groupPlan.setImportFile(downFileUrl);
//			groupPlan.setIsOpen("");
			log.debug("groupPlan {}",groupPlan);
			
			String result = groupPlanService.saveImportData(importDataMap,groupPlan);
			if(StringUtil.isEmpty(result) ){
				getRequest().setAttribute("retMsg", "导入失败, 请稍后重试或联系系统管理员。");
				dstFile.delete();//删除上传的文件
				return INPUT;
			}
			getRequest().setAttribute("retMsg", result);
			meetingId = groupPlan.getMeetingId();
			return list();
		}catch (jxl.read.biff.BiffException jxl) {
			log.error("导入分组计划导入失败, 文件格式不正确。");
			getRequest().setAttribute("retMsg", "导入失败, 文件格式不正确。");
			return INPUT;
		}catch (Exception e) {
			if (e instanceof ServiceException) {
				ServiceException exp = (ServiceException) e;
				String msg = exp.getMessage();
				if(msg.endsWith("!")){//捕获service抛出的校验用户是否重复的异常
					log.error(msg);
					getRequest().setAttribute("retMsg", msg);
					return INPUT;
				}
			}
			log.error("导入分组计划数据报错 ",e);
			getRequest().setAttribute("retMsg", "导入失败, 请稍后重试或联系系统管理员。");
			return INPUT;
		}finally{
			if(null != book){
				book.close();
			}
			log.debug("导入分组模版共耗时{}秒",(System.currentTimeMillis()-start)/1000);
		}
		} else {
			try {
				groupPlanService.saveOrUpdate(groupPlan);
			} catch (ServiceException e) {
				log.debug(e.getMessage());
				getRequest().setAttribute("retMsg", "导入失败, 请稍后重试或联系系统管理员。");
				return INPUT;
			}
			
			return list();
		}
	}
	
	/**删除分组计划*/
	public String delete(){
		try {
			groupPlanService.deleteById(groupPlan);
			resultMap.put("retcode",RetCode.SUCCESS );
			resultMap.put("retmsg", "删除成功");
		} catch (Exception e) {
			log.error("删除分组计划报错 ",e);
			resultMap.put("retcode", RetCode.FAIL);
			resultMap.put("retmsg", "删除分组计划失败，请稍后重试");
		}
		return SUCCESS;
	}
	
	private Map<GroupPlanDetail,List<JsonUser>> groupDetailMap;
	/**查看分组详情*/
	public String show(){
		try{
			groupDetailMap = groupPlanService.getGroupMemberInfo(groupPlan.getId());
		}catch (Exception e) {
			log.error("查看分组计划报错 ",e);
			return INPUT;
		}
		return SUCCESS;
	}
	
	private Map<GroupPlanDetail,String> exportGroupMap;
	/**查看分组详情*/
	public String exportGroupPlan(){
		try{
			groupDetailMap = groupPlanService.getGroupMemberInfo(groupPlan.getId());
			exportGroupMap = new LinkedHashMap<GroupPlanDetail,String>();
			for(Map.Entry<GroupPlanDetail,List<JsonUser>> entry :groupDetailMap.entrySet()){
				GroupPlanDetail group = entry.getKey();
				List<JsonUser> userList = entry.getValue();
				group.setDetail(group.getDetail().replace("<br />", "\n"));
				
				StringBuffer sb = new StringBuffer();
				
				for(JsonUser user:userList){
					sb.append(user.getName()).append("(").append(user.getMobile()).append("),");
				}
				
				exportGroupMap.put(group, sb.toString());
			}
			
		}catch (Exception e) {
			log.error("导出分组计划报错 ",e);
			return INPUT;
		}
		return SUCCESS;
	}
	
	/**按组选择发送短信*/
	public String selectGroupSms(){
		try{
			List<GroupPlan> groupPlanlist = groupPlanService.listByMeetingId(meetingId);
			
			List<GroupPlanDetail> groupDetailList = new ArrayList<GroupPlanDetail>();
			List<JsonUser> userList = new ArrayList<JsonUser>();
			if(groupPlan != null && groupPlan.getId() != null){
				groupDetailList = groupPlanService.findPlanDetailList(groupPlan.getId());
			}else if(groupPlanlist.size() > 0){
				groupPlan = groupPlanlist.get(0);
				groupDetailList = groupPlanService.findPlanDetailList(groupPlan.getId());
			}
			
			if(groupPlanDetail != null && groupPlanDetail.getId() != null && -1 != groupPlanDetail.getId()){
				userList = groupPlanService.getMemberByGroupId(groupPlanDetail.getId());
			}else if(groupDetailList.size() > 0){
				groupPlanDetail = groupDetailList.get(0);
				userList = groupPlanService.getMemberByGroupId(groupDetailList.get(0).getId());
			}
			
			getRequest().setAttribute("groupPlanlist", groupPlanlist);
			getRequest().setAttribute("groupDetailList",groupDetailList);
			getRequest().setAttribute("userList",userList);
			
		}catch (Exception e) {
			log.error("按组选择发送短信报错",e);
			return INPUT;
		}
		return SUCCESS;
	}
	
	/**设置对哪些级别的用户成员公开*/
	public String updateState(){
		try {
			GroupPlan oldPlan = groupPlanService.findById(groupPlan.getId());
			oldPlan.setIsOpen(groupPlan.getIsOpen());
			groupPlanService.modify(oldPlan);
			resultMap.put("retcode", RetCode.SUCCESS);
			resultMap.put("retmsg", "保存成功");
		} catch (Exception e) {
			log.error("updateState error ",e);
			resultMap.put("retcode", RetCode.FAIL);
			resultMap.put("retmsg", "保存失败");
		}
		return SUCCESS;
	}
	
	/**客户端分页展示组信息*/
	public String showPlanDetail(){
		
		try {
			log.debug("groupPlan.getId() {} ",groupPlan.getId());
			log.debug("currentPage {} ",currentPage);
			log.debug("pageSize {} ",pageSize);
			List<GroupPlanDetail> groupList = groupPlanService.findPlanDetailList(groupPlan.getId());
			Pager<JsonUser> pager = new Pager<JsonUser>(1,pageSize);
			if(groupList.size() > 0){
				pager = groupPlanService.getMemberPagerByGroupId(groupList.get(0).getId(), currentPage, pageSize);
			}
			resultMap.put("groupList", groupList);
			resultMap.put("pager", pager);
			resultMap.put("retcode", RetCode.SUCCESS);
			resultMap.put("retmsg", "成功");
		} catch (Exception e) {
			resultMap.put("retcode", RetCode.FAIL);
			resultMap.put("retmsg", "失败");
			log.error("showPlanDetail error ",e);
		}
		
		return SUCCESS;
	}
	
	/**客户端分页展示组成员信息*/
	public String showGroupMemberDetail(){
		try {
			log.debug("groupId {} ",groupId);
			log.debug("currentPage {} ",currentPage);
			log.debug("pageSize {} ",pageSize);
			
			Pager<JsonUser> pager = new Pager<JsonUser>(1,pageSize);
			pager = groupPlanService.getMemberPagerByGroupId(groupId, currentPage, pageSize);
			resultMap.put("pager", pager);
			
			resultMap.put("retcode", RetCode.SUCCESS);
			resultMap.put("retmsg", "成功");
		} catch (Exception e) {
			resultMap.put("retcode", RetCode.FAIL);
			resultMap.put("retmsg", "失败");
			log.error("showGroupMemberDetail error ",e);
		}
		
		return SUCCESS;
	}
	
	/**跳转到更新分组模版的页面*/
	public String gotoModify(){
		try {
			groupPlan = groupPlanService.findById(groupPlan.getId());
		} catch (ServiceException e) {
			log.error("goto Modify groupplan error ",e);
			getRequest().setAttribute("retMsg", "查询分组模版失败，请稍后重试");
			return INPUT;
		}
		return SUCCESS;
	}
	
	/**更新分组模版*/
	public String modify(){
		try {
			log.debug("modify groupPlan {}",groupPlan);
			groupPlanService.modifyGroupPlan(groupPlan);
		} catch (ServiceException e) {
			log.error("modify groupplan error ",e);
			getRequest().setAttribute("retMsg", "更新分组模版失败，请稍后重试");
			return INPUT;
		}
		getRequest().setAttribute("retMsg", "保存成功");
		meetingId = groupPlan.getMeetingId();
		return list();
	}
	
	/**根据分组模版ID查询出所有的分组信息列表*/
	public String listGroup(){
		try {
			log.debug("listGroup groupPlan {}",groupPlan);
			groupPlan = groupPlanService.findById(groupPlan.getId());
			meetingId = groupPlan.getMeetingId();
			List<GroupPlanDetail> groupList = groupPlanService.findPlanDetailList(groupPlan.getId());
			getRequest().setAttribute("groupList",groupList);
		} catch (Exception e) {
			log.error("listGroup error ",e);
			getRequest().setAttribute("retMsg", "查询模版分组信息失败，请稍后重试");
			return INPUT;
		}
		return SUCCESS;
	}
	
	/**添加一个分组信息*/
	public String addGroup(){
		try {
			log.debug("addGroup groupPlanDetail {}",groupPlanDetail);
			if(!StringUtil.isEmpty(groupPlanDetail.getDetail())){
				groupPlanDetail.setDetail(groupPlanDetail.getDetail().replace("\n", "<br />"));
			}
			groupPlanDetailService.add(groupPlanDetail);
			groupPlan = groupPlanService.findById(groupPlanDetail.getPlanId());
			meetingId = groupPlan.getMeetingId();
		} catch (Exception e) {
			log.error("addGroup error ",e);
			getRequest().setAttribute("retMsg", "保存分组信息失败，请稍后重试");
			return INPUT;
		}
		getRequest().setAttribute("retMsg", "保存成功");
		return listGroup();
	}
	
	/**根据分组ID查询分组信息跳转到进行修改的页面*/
	public String gotoModifyGroup(){
		try {
			log.debug("gotoModifyGroup groupPlanDetail {}",groupPlanDetail);
			groupPlanDetail = groupPlanDetailService.findById(groupPlanDetail.getId());
			groupPlanDetail.setDetail(groupPlanDetail.getDetail().replace("<br />", "\n"));
			groupPlan = groupPlanService.findById(groupPlanDetail.getPlanId());
			meetingId = groupPlan.getMeetingId();
		} catch (Exception e) {
			log.error("gotoModifyGroup error ",e);
			getRequest().setAttribute("retMsg", "查询模版分组信息失败，请稍后重试");
			return INPUT;
		}
		return SUCCESS;
	}
	
	/**根据分组ID查询分组信息跳转到进行修改的页面*/
	public String modifyGroup(){
		try {
			log.debug("modifyGroup groupPlanDetail {}",groupPlanDetail);
			if(!StringUtil.isEmpty(groupPlanDetail.getDetail())){
				groupPlanDetail.setDetail(groupPlanDetail.getDetail().replace("\n", "<br />"));
			}
			groupPlanDetailService.modify(groupPlanDetail);
			groupPlan = groupPlanService.findById(groupPlanDetail.getPlanId());
			meetingId = groupPlan.getMeetingId();
		} catch (Exception e) {
			log.error("modifyGroup error ",e);
			getRequest().setAttribute("retMsg", "保存分组信息失败，请稍后重试");
			return INPUT;
		}
		getRequest().setAttribute("retMsg", "保存成功");
		return listGroup();
	}
	
	/**根据分组ID删除分组信息*/
	public String deleteGroup(){
		try {
			log.debug("deleteGroup groupPlanDetail {}",groupPlanDetail);
			groupPlanService.deleteGroupById(groupPlanDetail.getId());
			groupPlan = groupPlanService.findById(groupPlan.getId());
			meetingId = groupPlan.getMeetingId();
		} catch (Exception e) {
			log.error("deleteGroup error ",e);
			getRequest().setAttribute("retMsg", "删除分组信息失败，请稍后重试");
			return INPUT;
		}
		getRequest().setAttribute("retMsg", "删除成功");
		return listGroup();
	}
	
	/**根据分组ID查询出该组下的成员信息*/
	public String listGroupMember(){
		try{
			log.debug("listGroupMember groupPlanDetail {}",groupPlanDetail);
			groupPlanDetail = groupPlanDetailService.findById(groupPlanDetail.getId());
			groupPlanDetail.setDetail(groupPlanDetail.getDetail().replace("<br />", "\n"));
			List<JsonUser> userList = groupPlanService.getMemberByGroupId(groupPlanDetail.getId());
			getRequest().setAttribute("userList", userList);
			groupPlan = groupPlanService.findById(groupPlan.getId());
			List<GroupPlanDetail> groupList = groupPlanService.findPlanDetailList(groupPlan.getId());
			getRequest().setAttribute("groupList", groupList);
			meetingId = groupPlan.getMeetingId();
		}catch(Exception e) {
			log.error("listGroupMember error ",e);
			getRequest().setAttribute("retMsg", "查询分组成员信息失败，请稍后重试");
			return INPUT;
		}
		return SUCCESS;
	}
	
	private String name ;
	private String mobile ;
	
	/**列出该会议下未分组的成员信息*/
	public String listMemberNotInGroup(){
		try{
			String city=this.getParameter("city");
			String delegation=this.getParameter("delegation");
			
			
			log.debug("listMemberNotInGroup groupPlan {}",groupPlan);
			log.debug("query name {} mobile {}",new String[]{name,mobile});
			groupPlanDetail = groupPlanDetailService.findById(groupPlanDetail.getId());
			groupPlan = groupPlanService.findById(groupPlan.getId());
			meetingId = groupPlan.getMeetingId();
			log.debug("find groupPlan {}",groupPlan);
			List<JsonUser> userList = groupPlanService.findMemberListNotInGroup(groupPlan.getId(),meetingId,name,mobile,city,delegation);
			getRequest().setAttribute("userList", userList);
			
			getRequest().setAttribute("city",city );
			getRequest().setAttribute("delegation", delegation);
			
		}catch(Exception e) {
			log.error("listMemberNotInGroup error ",e);
			getRequest().setAttribute("retMsg", "查询未分组成员信息失败，请稍后重试");
			return INPUT;
		}
		
		return SUCCESS;
	}
	
	private String memberIds;
	
	/**将选择的成员加入该分组*/
	public String addMember2Group(){
		try{
			log.debug("addMember2Group groupPlanDetail {}",groupPlanDetail);
			log.debug("addMember2Group memberIds {}",memberIds);
			groupPlanDetail = groupPlanDetailService.findById(groupPlanDetail.getId());
			groupPlanService.addMember2Group(memberIds,groupPlanDetail);
//			groupPlan = groupPlanService.findById(groupPlan.getId());
//			log.debug("find groupPlan {}",groupPlan);
//			meetingId = groupPlan.getMeetingId();
		}catch(Exception e) {
			log.error("addMember2Group error ",e);
			resultMap.put("retcode", RetCode.FAIL);
			resultMap.put("retmsg", "添加失败");
			return INPUT;
		}
		resultMap.put("retcode", RetCode.SUCCESS);
		resultMap.put("retmsg", "添加成功");
		
		return SUCCESS;
	}
	
	private Long userId;
	
	/**将选择的成员移出该分组*/
	public String removeMemberFromGroup(){
		try{
			log.debug("addMember2Group groupPlanDetail {}",groupPlanDetail);
			log.debug("addMember2Group userId {}",userId);
			groupPlanService.removeMemberFromGroup(userId,groupPlanDetail.getId());
		}catch(Exception e) {
			log.error("addMember2Group error ",e);
			resultMap.put("retcode", RetCode.FAIL);
			resultMap.put("retmsg", "移出失败");
			return INPUT;
		}
		resultMap.put("retcode", RetCode.SUCCESS);
		resultMap.put("retmsg", "移出成功");
		
		return SUCCESS;
	}
	
	private Long otherGroupId;
	/**将选择的成员移到其他分组*/
	public String moveMemberFromGroup(){
		try{
			log.debug("addMember2Group groupPlanDetail {}",groupPlanDetail);
			log.debug("addMember2Group userId {}",userId);
			groupPlanService.moveMemberFromGroup(userId,groupPlanDetail.getId(),otherGroupId);
		}catch(Exception e) {
			log.error("addMember2Group error ",e);
			resultMap.put("retcode", RetCode.FAIL);
			resultMap.put("retmsg", "移组失败");
			return INPUT;
		}
		resultMap.put("retcode", RetCode.SUCCESS);
		resultMap.put("retmsg", "移组成功");
		
		return SUCCESS;
	}
	
	private String operator;
	
	/**将选择的成员排序*/
	public String sortMemberForGroup(){
		try{
			log.debug("addMember2Group groupPlanDetail {}",groupPlanDetail);
			log.debug("addMember2Group userId {}",userId);
			groupPlanService.sortMemberForGroup(userId,groupPlanDetail.getId(),operator);
		}catch(Exception e) {
			log.error("addMember2Group error ",e);
			resultMap.put("retcode", RetCode.FAIL);
			resultMap.put("retmsg", "排序失败");
			return INPUT;
		}
		resultMap.put("retcode", RetCode.SUCCESS);
		resultMap.put("retmsg", "排序成功");
		
		return SUCCESS;
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

	public GroupPlan getGroupPlan() {
		return groupPlan;
	}

	public void setGroupPlan(GroupPlan groupPlan) {
		this.groupPlan = groupPlan;
	}

	public String getRecieverIds() {
		return recieverIds;
	}

	public void setRecieverIds(String recieverIds) {
		this.recieverIds = recieverIds;
	}

	public GroupPlanDetail getGroupPlanDetail() {
		return groupPlanDetail;
	}

	public void setGroupPlanDetail(GroupPlanDetail groupPlanDetail) {
		this.groupPlanDetail = groupPlanDetail;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public Long getUserId() {
		return this.getUserIdFromSession();
	}

	public GroupPlanDetailService getGroupPlanDetailService() {
		return groupPlanDetailService;
	}

	public void setGroupPlanDetailService(
			GroupPlanDetailService groupPlanDetailService) {
		this.groupPlanDetailService = groupPlanDetailService;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getMemberIds() {
		return memberIds;
	}

	public void setMemberIds(String memberIds) {
		this.memberIds = memberIds;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getOtherGroupId() {
		return otherGroupId;
	}

	public void setOtherGroupId(Long otherGroupId) {
		this.otherGroupId = otherGroupId;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Map<GroupPlanDetail, List<JsonUser>> getGroupDetailMap() {
		return groupDetailMap;
	}

	public void setGroupDetailMap(
			Map<GroupPlanDetail, List<JsonUser>> groupDetailMap) {
		this.groupDetailMap = groupDetailMap;
	}

	public Map<GroupPlanDetail, String> getExportGroupMap() {
		return exportGroupMap;
	}

	public void setExportGroupMap(Map<GroupPlanDetail, String> exportGroupMap) {
		this.exportGroupMap = exportGroupMap;
	}
	
}

package com.wondertek.meeting.action.task;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.wondertek.meeting.action.base.BaseAction;
import com.wondertek.meeting.common.CommonActionInterface;
import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.AdminUser;
import com.wondertek.meeting.model.MeetingTaskDetail;
import com.wondertek.meeting.model.User;
import com.wondertek.meeting.service.AdminUserService;
import com.wondertek.meeting.service.MeetingTaskDetailService;
import com.wondertek.meeting.service.UserService;
import com.wondertek.meeting.util.StringUtil;

public class MeetingTaskDetailAction extends BaseAction implements
		CommonActionInterface {

	private static final long serialVersionUID = 1L;

	MeetingTaskDetailService meetingTaskDetailService;
	
	UserService userService;
	AdminUserService adminUserService;
	
	public AdminUserService getAdminUserService() {
		return adminUserService;
	}

	public void setAdminUserService(AdminUserService adminUserService) {
		this.adminUserService = adminUserService;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public MeetingTaskDetailService getMeetingTaskDetailService() {
		return meetingTaskDetailService;
	}

	public void setMeetingTaskDetailService(
			MeetingTaskDetailService meetingTaskDetailService) {
		this.meetingTaskDetailService = meetingTaskDetailService;
	}

	@Override
	public String getListPager() {
		String meetingId = getParameter("meetingId");
		String parentId = getParameter("parentId");
		String cPage = getRequest().getParameter("currentPage");
		String myTaskFlag= getRequest().getParameter("myTaskFlag");
		String taskDetailName=getRequest().getParameter("taskDetailName");
		
		String forward=SUCCESS;
		log.debug("meetingId:" + meetingId + ",cPage:" + cPage);
		if (cPage != null && !"".equals(cPage)) {
			this.currentPage = Integer.parseInt(cPage);
		}
		getRequest().setAttribute("meetingId", meetingId);
		// 把内容进行封装下
		String adminId = "";
		if (StringUtil.isNotEmpty(myTaskFlag)) {
			adminId = String.valueOf(this.getAdminUserIdFromSession());
			forward="mytask";
		}
		
		Pager<MeetingTaskDetail> pager = meetingTaskDetailService.getListPager(
				meetingId, currentPage, pageSize, parentId,myTaskFlag,adminId,taskDetailName);

		List<MeetingTaskDetail> list = pager.getPageRecords();
		for (MeetingTaskDetail detail : list) {
			try {
				detail.setChargeName(this.getAdminUserNamesByUserIds(detail
						.getCharge()));
				if (detail.getSonMeetingTaskDetailList() != null) {
					for (MeetingTaskDetail sonDetail : detail
							.getSonMeetingTaskDetailList()) {
						sonDetail.setChargeName(this
								.getAdminUserNamesByUserIds(sonDetail
										.getCharge()));
					}
				}
			} catch (ServiceException e) {
				e.printStackTrace();
			}
		}
		
		getRequest().setAttribute("pager", pager);
		//this.resultMap.put("pager", pager);
		this.setAttribute("taskDetailName",taskDetailName);

		return forward;

	}

	@Override
	public String toAdd() {
		String meetingId = getParameter("meetingId");
		String parentId=getParameter("parentId");
		getRequest().setAttribute("meetingId", meetingId);
		getRequest().setAttribute("parentId", parentId);
		return SUCCESS;
	}

	@Override
	public String add() {
		String meetingId = getParameter("meetingId");
		String detailName = getParameter("detailName");
		String detailDescription = getParameter("detailDescription");
		String charge=getParameter("adminIds");
		
		String executeStartTime = getParameter("executeStartTime");
		String executeEndTime = getParameter("executeEndTime");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		
		String parentId=this.getParameter("parentId");
		

		log.debug("meetingId:" + meetingId + ",detailName:" + detailName
				+ ",detailDescription:" + detailDescription  + ",executeStartTime:" + executeStartTime
				+ ",executeEndTime:" + executeEndTime);

		try {
			MeetingTaskDetail meetingTaskDetail = new MeetingTaskDetail();
			meetingTaskDetail.setStatus("0");
			meetingTaskDetail.setMeetingId(Long.valueOf(meetingId));
			meetingTaskDetail.setDetailName(detailName);
			meetingTaskDetail.setDetailDescription(detailDescription);
			meetingTaskDetail.setCharge(charge);
			meetingTaskDetail.setExecuteStartTime(StringUtil
					.isNotEmpty(executeStartTime) ? sdf.parse(executeStartTime)
					: null);
			meetingTaskDetail.setExecuteEndTime(StringUtil
					.isNotEmpty(executeEndTime) ? sdf.parse(executeEndTime)
					: null);
			meetingTaskDetail.setCreateTime(new Date());
			meetingTaskDetail.setCreator(this.getAdminUserIdFromSession());
			meetingTaskDetail
					.setParentId(StringUtil.isNotEmpty(parentId) ? Long
							.valueOf(parentId) : 0);
			
			if(StringUtil.isNotEmpty(parentId)){
				//通过父级目录
				MeetingTaskDetail meetingTaskDetailParent=meetingTaskDetailService
				.findById(Long.valueOf(parentId));
				meetingTaskDetail.setTaskFullPath(meetingTaskDetailParent.getTaskFullPath()+"-"+meetingTaskDetailParent.getId());
				
			}else{
				meetingTaskDetail.setTaskFullPath("0");
			}
			
			meetingTaskDetailService.saveOrUpdate(meetingTaskDetail);
			this.resultMap.put("retCode", 0);
			this.resultMap.put("retMsg", "保存成功！");
		} catch (Exception e) {
			this.resultMap.put("retCode", 1);
			this.resultMap.put("retMsg", "保存失败！" + e.getMessage());
			e.printStackTrace();
		}
		return SUCCESS;
	}

	@Override
	public String toUpdate() {
		String id = this.getParameter("id");
		try {
			MeetingTaskDetail meetingTaskDetail = meetingTaskDetailService
					.findById(Long.valueOf(id));
			this.getRequest().setAttribute("detail", meetingTaskDetail);
			this.getRequest().setAttribute(
					"chargeNames",
					this.getAdminUserNamesByUserIds(meetingTaskDetail
							.getCharge()));
		} catch (NumberFormatException e) {
			this.resultMap.put("retMsg", e.getMessage());
			e.printStackTrace();
		} catch (ServiceException e) {
			this.resultMap.put("retMsg", e.getMessage());
			e.printStackTrace();
		}

		return SUCCESS;
	}

	@Override
	public String update() {
		String id = this.getParameter("id");
		String detailName = getParameter("detailName");
		String detailDescription = getParameter("detailDescription");
		String executeStartTime = getParameter("executeStartTime");
		String executeEndTime = getParameter("executeEndTime");
		String charge = getParameter("adminIds");
		String status=this.getParameter("status"); 
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");

		log.debug(",detailName:" + detailName + ",detailDescription:"
				+ detailDescription + ",executeStartTime:" + executeStartTime
				+ ",executeEndTime:" + executeEndTime + ",charge:" + charge+",status:"+status);

		try {
			MeetingTaskDetail meetingTaskDetail = meetingTaskDetailService
					.findById(Long.valueOf(id));
			meetingTaskDetail.setDetailName(detailName);
			meetingTaskDetail.setDetailDescription(detailDescription);
			meetingTaskDetail.setCharge(charge);
			meetingTaskDetail.setModifyTime(new Date());
			meetingTaskDetail.setModifier(this.getAdminUserIdFromSession());
			meetingTaskDetail.setExecuteStartTime(StringUtil
					.isNotEmpty(executeStartTime) ? sdf.parse(executeStartTime)
					: null);
			meetingTaskDetail.setExecuteEndTime(StringUtil
					.isNotEmpty(executeEndTime) ? sdf.parse(executeEndTime)
					: null);
			meetingTaskDetail.setStatus(status);
			
			meetingTaskDetailService.saveOrUpdate(meetingTaskDetail);
			this.getRequest().setAttribute("detail", meetingTaskDetail);
			this.resultMap.put("retCode", 0);
			this.resultMap.put("retMsg", "保存成功！");

		} catch (NumberFormatException e) {
			this.resultMap.put("retMsg", e.getMessage());
			e.printStackTrace();
		} catch (ServiceException e) {
			this.resultMap.put("retMsg", e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {

		}

		return SUCCESS;
	}

	@Override
	public String delete() {
		String id = this.getParameter("id");
		try {
			MeetingTaskDetail meetingTaskDetail = meetingTaskDetailService
					.findById(Long.valueOf(id));
			meetingTaskDetailService.delete(meetingTaskDetail);
			this.resultMap.put("retCode", 0);
			this.resultMap.put("retMsg", "删除成功！");

		} catch (NumberFormatException e) {
			this.resultMap.put("retCode", 1);
			this.resultMap.put("retMsg", "保存失败！" + e.getMessage());
			e.printStackTrace();
		} catch (ServiceException e) {
			this.resultMap.put("retCode", 1);
			this.resultMap.put("retMsg", "保存失败！" + e.getMessage());
			e.printStackTrace();
		}

		return SUCCESS;
	}
	
	
	/**
	 * 通过用户的IDS来获取用户的名称传回前台
	 * 
	 * @param userIds
	 * @param meetingId
	 * @return
	 */
	private String getUserNamesByUserIds(String userIds, Long meetingId) {
		String userNames = "";
		// 转化参与人员
		// TODO
		if (StringUtil.isNotEmpty(userIds)) {
			List<User> userLst = userService.getMeetingMemberByIds(meetingId,
					userIds.substring(0, userIds.length() - 1));
			for (User user : userLst) {
				userNames += user.getName() + ",";
			}
			log.debug("attendeeNames:" + userNames);

		}

		return userNames;
	}
	
	
	/**
	 * 通过用户的IDS来获取用户的名称传回前台
	 * 
	 * @param userIds
	 * @param meetingId
	 * @return
	 * @throws ServiceException 
	 */
	private String getAdminUserNamesByUserIds(String userIds) throws ServiceException {
		String userNames = "";
		// 转化参与人员
		// TODO
		if (StringUtil.isNotEmpty(userIds)) {
			
			StringBuffer hql=new StringBuffer(" FROM  AdminUser where  id in("+ userIds.substring(0, userIds.length() - 1) +")");
			log.debug("hql:"+hql.toString());
			
			List<AdminUser> userLst = adminUserService.getObjects(hql.toString());

			for (AdminUser user : userLst) {
				userNames += user.getName() + ",";
			}
			
			log.debug("attendeeNames:" + userNames);

		}

		return userNames;
	}
	
	
	
	
	
	
}

package com.wondertek.meeting.action.task;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.wondertek.meeting.action.base.BaseAction;
import com.wondertek.meeting.common.CommonActionInterface;
import com.wondertek.meeting.common.SysConstants;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.AdminUser;
import com.wondertek.meeting.model.Meeting;
import com.wondertek.meeting.model.MeetingTask;
import com.wondertek.meeting.service.AdminUserService;
import com.wondertek.meeting.service.MeetingService;
import com.wondertek.meeting.service.MeetingTaskService;
/**
 * 用户会议下任务安排
 * @author Administrator
 */
public class MeetingTaskAction extends BaseAction implements
		CommonActionInterface {
	
	private static final long serialVersionUID = 1L;
	MeetingTaskService meetingTaskService ;
	MeetingService meetingService;
	AdminUserService adminUserService;
	
	public AdminUserService getAdminUserService() {
		return adminUserService;
	}

	public void setAdminUserService(AdminUserService adminUserService) {
		this.adminUserService = adminUserService;
	}

	public MeetingService getMeetingService() {
		return meetingService;
	}

	public void setMeetingService(MeetingService meetingService) {
		this.meetingService = meetingService;
	}

	public MeetingTaskService getMeetingTaskService() {
		return meetingTaskService;
	}

	public void setMeetingTaskService(MeetingTaskService meetingTaskService) {
		this.meetingTaskService = meetingTaskService;
	}

	public String getListPager() {
		String meetingId = this.getRequest().getParameter("meetingId");
		String cPage = getRequest().getParameter("currentPage");
		if (cPage != null && !"".equals(cPage)) {
			this.currentPage = Integer.parseInt(cPage);
		}

		log.info("meetingId:" + meetingId);
		this.getRequest().setAttribute("meetingId", meetingId);
		getRequest().setAttribute(
				"pager",
				meetingTaskService.getListPager(meetingId, currentPage,
						pageSize));

		return SUCCESS;
	}

	/**
	 * 跳转到添加任务页面
	 */
	public String toAdd() {
		String meetingId=this.getParameter("meetingId");
		
		this.getRequest().setAttribute("meetingId",meetingId);
		
		return SUCCESS;
	}

	public String add() {
		String meetingId=this.getParameter("meetingId");
		String taskName=this.getParameter("taskName");
		String taskDescription=this.getParameter("taskDescription");
		
		try {
			MeetingTask meetingTask = new MeetingTask();
			meetingTask.setCreateTime(new Date());
			meetingTask.setCreator(this.getAdminUserIdFromSession());
			meetingTask.setMeetingId(Long.valueOf(meetingId));
			meetingTask.setTaskName(taskName);
			meetingTask.setTaskDescription(taskDescription);
			meetingTaskService.saveOrUpdate(meetingTask);
			this.resultMap.put("retCode", 0);
			this.resultMap.put("retMsg", "保存成功！");
			
		} catch (ServiceException e) {
			e.printStackTrace();
		}

		return SUCCESS;
	}

	public String toUpdate() {
		String id=getRequest().getParameter("id");
		log.debug("id:"+id);
		try {
			MeetingTask meetingTask = meetingTaskService.findById(Long.valueOf(id));
			this.setAttribute("task", meetingTask);
			this.setAttribute("meetingId", meetingTask.getMeetingId());
			
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		
		
		
		return SUCCESS;
	}

	public String update() {

		String id = getRequest().getParameter("id");
		String meetingId = getRequest().getParameter("meetingId");
		String taskName = this.getParameter("taskName");
		String taskDescription = this.getParameter("taskDescription");
		log.debug("id:" + id + "" + ",meetingId:" + meetingId + ",taskName:"
				+ taskName + ",taskDescription:" + taskDescription);

		try {
			MeetingTask meetingTask = meetingTaskService.findById(Long
					.valueOf(id));
			meetingTask.setTaskName(taskName);
			meetingTask.setTaskDescription(taskDescription);
			meetingTask.setModifyTime(new Date());
			meetingTask.setModifier(this.getAdminUserIdFromSession());

			meetingTaskService.saveOrUpdate(meetingTask);

		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (ServiceException e) {
			e.printStackTrace();
		}

		this.resultMap.put("retCode", 0);
		this.resultMap.put("retMsg", "保存成功！");

		return SUCCESS;
	}

	/**
	 * 删除
	 */
	public String delete() {
		try{
			String id=getParameter("id");
			MeetingTask meetingTask = meetingTaskService.findById(Long.valueOf(id));
			
			meetingTaskService.delete(meetingTask);
			this.resultMap.put("retCode", 0);
			this.resultMap.put("retMsg", "删除成功！");
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return SUCCESS;
	}
	
	
	/**
	 * 获取Admin Users
	 * @return
	 */
	public String getAdminUsers() {
		try {
			List<AdminUser> selectAdminList = new ArrayList<AdminUser>();

			String meetingId = getParameter("meetingId");
			String adminIds = getParameter("adminIds");
			String orgId = getParameter("orgId");
			// 通过会议ID查询的会议的创建人的orgId
			Meeting meeting = meetingService.findById(Long.valueOf(meetingId));
			log.info("meeting.getCreator().getOrg().getId():"
					+ meeting.getCreator().getOrg().getId());

			// 通过orgId查询所有的管理员(统一级别下的)
			selectAdminList.addAll(adminUserService.getAdminUsersByOrg(meeting
					.getCreator().getOrg().getId(), SysConstants.SUPER_ADMIN));
			selectAdminList.addAll(adminUserService.getAdminUsersByOrg(meeting
					.getCreator().getOrg().getId(), SysConstants.GROUP_ADMIN));
			selectAdminList.addAll(meeting.getMeetingAdmins());

			// 把对应的信息返回到页面
			// TODO
			this.setAttribute("adminUserList", selectAdminList);
			this.setAttribute("meetingId", meetingId);
			this.setAttribute("adminIds", adminIds);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return SUCCESS;

	}

}

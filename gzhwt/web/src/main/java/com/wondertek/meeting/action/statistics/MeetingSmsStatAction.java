package com.wondertek.meeting.action.statistics;

import com.wondertek.meeting.action.base.BaseAction;
import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.exception.HibernateDaoSupportException;
import com.wondertek.meeting.model.AdminUser;
import com.wondertek.meeting.service.MeetingSmsService;
import com.wondertek.meeting.util.StringUtil;

/**
 * 短信统计
 * 
 * @author Administrator
 * 
 */
public class MeetingSmsStatAction extends BaseAction {

	private static final long serialVersionUID = 602147236652731562L;
	MeetingSmsService meetingSmsService;

	private String treeId;

	public void setMeetingSmsService(MeetingSmsService meetingSmsService) {
		this.meetingSmsService = meetingSmsService;
	}

	/**
	 * 通过用户下会议来查询
	 * 
	 * @throws HibernateDaoSupportException
	 */
	public String statSmsByMeeting() throws HibernateDaoSupportException {
		String forward = SUCCESS;

		String cPage = getRequest().getParameter("currentPage");
		String meetingName = StringUtil.replaceBlank(getRequest().getParameter("meetingName"));

		if (cPage != null && !"".equals(cPage)) {
			currentPage = Integer.parseInt(cPage);
		}

		// 如果treeId为空，则设置为管理员所在组织id
		if (StringUtil.isEmpty(treeId)) {
			AdminUser sessionUser = this.getAdminUserFromSession();
			Long orgId = sessionUser.getOrg().getId();
			treeId = "o" + orgId;
		}

		// 进行统计
		Pager<Object> pager = meetingSmsService.statMeetingSmsPagers(treeId, currentPage, pageSize, meetingName);

		this.getRequest().setAttribute("pager", pager);
		this.getRequest().setAttribute("meetingName", meetingName);

		return forward;
	}

	public String getTreeId() {
		return treeId;
	}

	public void setTreeId(String treeId) {
		this.treeId = treeId;
	}
}

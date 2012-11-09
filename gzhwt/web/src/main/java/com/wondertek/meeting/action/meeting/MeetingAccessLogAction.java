package com.wondertek.meeting.action.meeting;

import java.math.BigDecimal;
import java.util.List;

import com.wondertek.meeting.action.base.BaseAction;
import com.wondertek.meeting.client.view.TreeView;
import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.AdminUser;
import com.wondertek.meeting.model.MeetingAccessLog;
import com.wondertek.meeting.service.MeetingAccessLogService;
import com.wondertek.meeting.service.OrganizationService;
import com.wondertek.meeting.util.StringUtil;

/**
 * 会议访问记录
 * 
 * @author 金祝华
 */
public class MeetingAccessLogAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3217717041180325042L;

	private MeetingAccessLogService meetingAccessLogService;

	private OrganizationService organizationService;

	public List<TreeView> treeList;

	private MeetingAccessLog meetingAccessLog;

	/**
	 * 查询会议访问统计，按会议进行汇总
	 * 
	 * @return
	 */
	public String accessList() {

		if (meetingAccessLog == null) {
			meetingAccessLog = new MeetingAccessLog();
		}

		// 如果treeId为空，则设置为管理员所在组织id
		if (StringUtil.isEmpty(meetingAccessLog.getTreeId())) {
			AdminUser sessionUser = this.getAdminUserFromSession();
			Long orgId = sessionUser.getOrg().getId();
			meetingAccessLog.setTreeId("o" + orgId);
		}

		Pager<MeetingAccessLog> pager = null;
		try {
			Object[] result = meetingAccessLogService.findPager(meetingAccessLog, this.getAdminUserFromSession()
					.getOrg().getId(), currentPage, pageSize);
			if (result != null && result.length >= 2) {
				pager = (Pager<MeetingAccessLog>) result[0];
				List sumList = (List) result[1];

				if (sumList != null && sumList.size() > 0) {
					Integer totalSum = 0;
					for (Object o : sumList) {
						Object[] array = (Object[]) o;
						Short portalType = (Short) (array)[0];
						if (1 == portalType) {
							this.getRequest().setAttribute("webSum", array[1]);
							totalSum += ((BigDecimal) array[1]).intValue();
						} else if (2 == portalType) {
							this.getRequest().setAttribute("wapSum", array[1]);
							totalSum += ((BigDecimal) array[1]).intValue();
						} else if (3 == portalType) {
							this.getRequest().setAttribute("clientSum", array[1]);
							totalSum += ((BigDecimal) array[1]).intValue();
						}
					}
					this.getRequest().setAttribute("totalSum", totalSum);
				}
			}
		} catch (ServiceException e) {
			log.error("query meetingAccessLog list error: " + e.toString());
		} catch (Exception ex) {
			log.error("error!", ex);
		}

		this.getRequest().setAttribute("pager", pager);

		return SUCCESS;
	}

	/**
	 * 查询会议访问统计明细
	 * 
	 * @return
	 */
	public String accessDetail() {

		if (meetingAccessLog == null) {
			meetingAccessLog = new MeetingAccessLog();
		}

		// 如果treeId为空，则设置为管理员所在组织id
		if (StringUtil.isEmpty(meetingAccessLog.getTreeId())) {
			AdminUser sessionUser = this.getAdminUserFromSession();
			Long orgId = sessionUser.getOrg().getId();
			meetingAccessLog.setTreeId("o" + orgId);
		}

		Pager<MeetingAccessLog> pager = null;
		try {
			pager = meetingAccessLogService.findDetailPager(meetingAccessLog, this.getAdminUserFromSession().getOrg()
					.getId(), currentPage, pageSize);
		} catch (ServiceException e) {
			log.error("query meetingAccessLog list error: " + e.toString());
		}

		this.getRequest().setAttribute("pager", pager);

		return SUCCESS;
	}

	public MeetingAccessLogService getMeetingAccessLogService() {
		return meetingAccessLogService;
	}

	public void setMeetingAccessLogService(MeetingAccessLogService meetingAccessLogService) {
		this.meetingAccessLogService = meetingAccessLogService;
	}

	public MeetingAccessLog getMeetingAccessLog() {
		return meetingAccessLog;
	}

	public void setMeetingAccessLog(MeetingAccessLog meetingAccessLog) {
		this.meetingAccessLog = meetingAccessLog;
	}

	public OrganizationService getOrganizationService() {
		return organizationService;
	}

	public void setOrganizationService(OrganizationService organizationService) {
		this.organizationService = organizationService;
	}

	public List<TreeView> getTreeList() {
		return treeList;
	}

	public void setTreeList(List<TreeView> treeList) {
		this.treeList = treeList;
	}
}

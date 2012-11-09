/**
 * 
 */
package com.wondertek.meeting.action.statistics;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.wondertek.meeting.action.base.BaseAction;
import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.model.AdminUser;
import com.wondertek.meeting.model.MessageStatistics;
import com.wondertek.meeting.service.MeetingStatisticsService;

/**
 * @author rain
 *
 */
public class MeetingStatisticsAction  extends BaseAction {
	private static final long serialVersionUID = -1071994582706312892L;
	
	@Autowired
	private MeetingStatisticsService meetingStatisticsService;
	
	/**
	 * 交流统计：私信条数，交流次数，评论次数
	 * 
	 * @return
	 */
	public String interactionStatistics() {
		if (StringUtils.isNotEmpty(getParameter("meetingId"))) {
			final Long meetingId = Long.valueOf(getParameter("meetingId"));
			final Pager<MessageStatistics> pager = meetingStatisticsService.queryInteractionByMeetingId(currentPage, pageSize, meetingId);
			getRequest().setAttribute("pager", pager);
			getRequest().setAttribute("meetingId", meetingId);
			return "detail";
		} else {
			String treeId = getParameter("treeId");
			// 如果treeId为空，则设置为管理员所在组织id
			if (StringUtils.isEmpty(treeId)) {
				final AdminUser sessionUser = this.getAdminUserFromSession();
				final Long orgId = sessionUser.getOrg().getId();
				treeId = "o" + orgId;
			}
			final Pager<MessageStatistics> pager = meetingStatisticsService.queryInteraction(currentPage, pageSize, treeId);
			getRequest().setAttribute("pager", pager);
			getRequest().setAttribute("treeId", treeId);
			return SUCCESS;
		}
		
	}
}

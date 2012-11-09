package com.wondertek.meeting.action.meeting;

import com.wondertek.meeting.action.base.BaseAction;
import com.wondertek.meeting.service.AnalyticsLogService;

/**
 * 菜单访问统计
 * @author John Tang
 *
 */
public class AnalyticsLogAction extends BaseAction {
	
	private static final long serialVersionUID = -239998631366970937L;

	private Long meetingId;
	
	private AnalyticsLogService analyticsLogService;
	
	public AnalyticsLogService getAnalyticsLogService() {
		return analyticsLogService;
	}

	public void setAnalyticsLogService(AnalyticsLogService analyticsLogService) {
		this.analyticsLogService = analyticsLogService;
	}
	
	
	/**统计访问的菜单*/
	public String analyticsLog(){
		log.debug("analyticsLog menu_id = {}",getRequest().getAttribute("menu_id"));
		return SUCCESS;
	}
	
	public String statics(){
		try {
			getRequest().setAttribute("resultList", analyticsLogService.staticsLog(meetingId));
		} catch (Exception e) {
			log.error("statics error ",e);
		}
		return SUCCESS;
	}

	public Long getMeetingId() {
		return meetingId;
	}

	public void setMeetingId(Long meetingId) {
		this.meetingId = meetingId;
	}
	
	
}

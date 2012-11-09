package com.wondertek.meeting.interceptor;

import java.util.Date;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.wondertek.meeting.common.SessionKeeper;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.Meeting;
import com.wondertek.meeting.model.MeetingAccessLog;
import com.wondertek.meeting.model.User;
import com.wondertek.meeting.service.AnalyticsLogService;
import com.wondertek.meeting.service.MeetingAccessLogService;
import com.wondertek.meeting.util.IPRequest;

public class AnalyticsInterceptor extends AbstractInterceptor {
	private static final long serialVersionUID = 207704134209655270L;
	public Logger log = LoggerFactory.getLogger(this.getClass());

	private AnalyticsLogService analyticsLogService;
	private MeetingAccessLogService meetingAccessLogService;

	@SuppressWarnings("unchecked")
	public String intercept(ActionInvocation actionInvocation) throws Exception {
		try {
			ActionContext actionContext = actionInvocation.getInvocationContext();
			HttpServletRequest request = (HttpServletRequest) actionContext.get(ServletActionContext.HTTP_REQUEST);
			String uri = request.getRequestURI();
			if (uri.indexOf("/pri/") != -1 && uri.indexOf("/admin/") == -1) {
				String actionName = actionContext.getName();				
				Enumeration<String> enu = request.getParameterNames();
				StringBuffer params = new StringBuffer();
				if (enu != null) {
					while (enu.hasMoreElements()) {
						String paraName = (String) enu.nextElement();
						if (paraName.toLowerCase().contains("password")) {
							continue;
						}
						String paraValue = request.getParameter(paraName);
						if (paraValue != null && paraValue.length() > 5000) {
							continue;
						}
						params.append(paraName).append(" = ").append(paraValue).append(" , ");
					}
				}

				String meetingIdStr = request.getParameter("meetingId") == null ? request.getParameter("meeting.id")
						: request.getParameter("meetingId");
				meetingIdStr = meetingIdStr == null ? request.getParameter("meetingNo") : meetingIdStr;

				Long meetingId = null;
				if (meetingIdStr != null && meetingIdStr.length() > 0) {
					meetingId = Long.valueOf(meetingIdStr);
				}

				String menuIdStr = request.getParameter("menu_id");
				Long menuId = null;
				if (menuIdStr != null && menuIdStr.length() > 0) {
					menuId = Long.valueOf(menuIdStr);
				}

				String ip = IPRequest.getIpAddress(request);

				User user = null;
				if (request.getSession().getAttribute(SessionKeeper.SESSION_USER) != null) {
					user = (User) request.getSession().getAttribute(SessionKeeper.SESSION_USER);
					log.debug("user : {}", user.getMobile());
				}
				log.debug("actionName : {}", actionName);
				log.debug("uri : {}", uri);
				log.debug("IP : {}", ip);
				log.debug("meetingId : {}", meetingId);
				log.debug("menuId : {}", menuId);
				log.debug("params : {}", params);

				analyticsLogService.addAnalyticsLog(ip, user, menuId, uri, meetingId, params.toString());
				meetingAccessLog(meetingId, user, uri, request);
			}
		} catch (Exception e) {
			log.error("AnalyticsInterceptor error ", e);
		}

		return actionInvocation.invoke();
	}

	private void meetingAccessLog(final Long meetingId, final User user, final String uri, final HttpServletRequest request) throws ServiceException {
		Integer terminalType = 0;
		if (StringUtils.contains(uri, "portal/pri/meeting/getMeetingById.action")) {
			if (StringUtils.equalsIgnoreCase(request.getParameter("returnType"), "portal")) {
				terminalType = 1;
			}
		}
		if (StringUtils.contains(uri, "wap/pri/meeting/getMeetingById.action")) {
			if (StringUtils.equalsIgnoreCase(request.getParameter("returnType"), "wap_index")) {
				terminalType = 2;
			}
		}
		if (StringUtils.contains(uri, "client/pri/menu/getMenuList.action")) {
			terminalType = 3;
		}
		if (StringUtils.contains(uri, "touch/pri/meeting/getMeetingById.action")) {
			if (StringUtils.equalsIgnoreCase(request.getParameter("returnType"), "touch_viewmeeting")) {
				terminalType = 4;
			}
		}
		
		if (terminalType > 0) {
			final MeetingAccessLog accessLog = new MeetingAccessLog();
			final Meeting meeting = new Meeting();
			meeting.setId(meetingId);
			accessLog.setMeeting(meeting);
			accessLog.setUser(user);
			accessLog.setAccessTime(new Date());
			accessLog.setPortalType(terminalType);
			meetingAccessLogService.add(accessLog);
		}
	}

	public AnalyticsLogService getAnalyticsLogService() {
		return analyticsLogService;
	}

	public void setAnalyticsLogService(AnalyticsLogService analyticsLogService) {
		this.analyticsLogService = analyticsLogService;
	}

	/**
	 * @return the meetingAccessLogService
	 */
	public MeetingAccessLogService getMeetingAccessLogService() {
		return meetingAccessLogService;
	}

	/**
	 * @param meetingAccessLogService the meetingAccessLogService to set
	 */
	public void setMeetingAccessLogService(MeetingAccessLogService meetingAccessLogService) {
		this.meetingAccessLogService = meetingAccessLogService;
	}
}
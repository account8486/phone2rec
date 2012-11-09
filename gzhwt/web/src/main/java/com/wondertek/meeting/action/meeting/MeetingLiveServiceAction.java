/**
 * 会场服务Action
 * Author: 张国敬
 * Copyrights: 版权归上海网达软件有限公司安徽分公司所有
 * Create at: 2012-4-23
 */
package com.wondertek.meeting.action.meeting;

import java.util.Date;

import com.wondertek.meeting.action.base.BaseAction;
import com.wondertek.meeting.manager.liveservice.LiveServiceManager;
import com.wondertek.meeting.service.UserService;
import com.wondertek.meeting.util.DateUtil;
import com.wondertek.meeting.util.StringUtil;
import com.wondertek.meeting.vo.LiveService;
import com.wondertek.meeting.vo.LiveServiceInfo;

@SuppressWarnings("serial")
public class MeetingLiveServiceAction extends BaseAction {
	private UserService userService;
	private LiveServiceManager liveServiceManager = LiveServiceManager.getInstance();
	private Long meetingId;
	private String clientType; //请求的客户端类型
	private LiveServiceInfo liveService;

	public LiveServiceInfo getLiveService() {
		return liveService;
	}

	public void setLiveService(LiveServiceInfo liveService) {
		this.liveService = liveService;
	}

	public Long getMeetingId() {
		return meetingId;
	}

	public String getClientType() {
		return clientType;
	}

	public void setClientType(String clientType) {
		this.clientType = clientType;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setMeetingId(Long meetingId) {
		this.meetingId = meetingId;
	}

	/**
	 * 会务人员查看会场服务请求
	 */
	public String browse() throws Exception {
		return "browse";
	}
	
	/**
	 * 查询是否有服务请求
	 */
	public String seek() throws Exception {
		LiveService liveService = this.liveServiceManager.getliveService(meetingId);
		if(liveService == null || liveService.getliveServiceInfoCount() == 0) {
			this.resultMap.put("result", "false");
			return "json";
		}
		
		LiveServiceInfo serviceInfo = this.liveServiceManager.pop(meetingId);
		log.debug("seek: meetingId=" + meetingId + ", serviceInfo=" + serviceInfo);
		if(serviceInfo != null) {
			this.resultMap.put("result", "true");
			serviceInfo.setServiceName(StringUtil.convertTextAreaNewLine(serviceInfo.getServiceName()));
			this.resultMap.put("serviceInfo", serviceInfo);
		}
		return "json";
	}
	
	/**
	 * 进入会场服务请求
	 */
	public String requestService() throws Exception {
		log.debug("requestService: meetingId=" + meetingId);
		
		return "requestService";
	}
	
	/**
	 * 提交会场服务请求
	 */
	public String submitRequest() throws Exception {
		log.debug("serviceRequest: meetingId=" + meetingId + ", liveService=" + liveService);
		
		if(liveService != null) {
			liveService.setRequestTime(DateUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
			liveService.setRequester(userService.findById(liveService.getRequester().getId()));
			
			this.liveServiceManager.push(meetingId, liveService);
			this.resultMap.put("result", "true");
		} else {
			this.resultMap.put("result", "false");
		}
		
		return "json";
	}
	
}

/**
 * 
 */
package com.wondertek.meeting.action.sms;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.wondertek.meeting.action.base.BaseAction;
import com.wondertek.meeting.common.RetCode;
import com.wondertek.meeting.model.MeetingSms;
import com.wondertek.meeting.model.User;
import com.wondertek.meeting.service.MeetingSmsService;
import com.wondertek.meeting.service.UserService;

/**
 * 
 * @author rain
 * 
 */
public class MeetingSmsAction extends BaseAction {
	/**
	 * serial id
	 */
	private static final long serialVersionUID = -7516949297472658802L;

	private UserService userService;
	private MeetingSmsService meetingSmsService;
	
	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public MeetingSmsService getMeetingSmsService() {
		return meetingSmsService;
	}

	public void setMeetingSmsService(MeetingSmsService meetingSmsService) {
		this.meetingSmsService = meetingSmsService;
	}

	private String imei;
	private Long meetingId;
	private String userIds;
	private String content;
	
	
	/**转到客户端管理员页面*/
	public String gotoAdminPage(){
		log.debug("gotoAdminPage meetingId = {}",meetingId);
		return SUCCESS;
	}
	
	/**客户端发送短信*/
	public String sendSms(){
		try {
			log.info("userIds:" + userIds 
					+ ",meetingId:" + meetingId + ",messageContent:" + content);
			
			boolean sendAllFlag = false;
			// 是否全部发送
			if(userIds != null && userIds.length() > 1){
				if(userIds.contains("-1")){
					sendAllFlag = true;
				}
			}
			
			List<User> userLst = null;
			if (sendAllFlag) {
				userLst = userService.getMeetingMember(meetingId,null,null,null,null,null);
			} else if (userIds != null && userIds.length() > 1) {
				userLst = userService.getMeetingMemberByIds(meetingId, userIds);
			}

			// 收信人
			for (User user : userLst) {
				Set<User> recipient = new HashSet<User>();
				recipient.add(user);
				// 基本信息赋值
				MeetingSms meetingSms = new MeetingSms();
				// 会议ID
				meetingSms.setMeetingId(meetingId);
				// 是否延迟发送
				meetingSms.setDelay(false);
				meetingSms.setRecipient(recipient);
				meetingSms.setCreateTime(new Date());
				meetingSms.setState(0);
				
				meetingSms.setMessages(content);
				meetingSmsService.add(meetingSms);
				log.debug("Send Message Content success :" + meetingSms.getMessages());
			}
			
			resultMap.put("retcode", RetCode.SUCCESS);
			resultMap.put("retmsg", "发送成功");
		} catch (Exception e) {
			log.error("client send sms error",e);
			resultMap.put("retcode", RetCode.FAIL);
			resultMap.put("retmsg", "发送失败");
		}
		return SUCCESS;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public Long getMeetingId() {
		return meetingId;
	}

	public void setMeetingId(Long meetingId) {
		this.meetingId = meetingId;
	}

	public String getUserIds() {
		return userIds;
	}

	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}
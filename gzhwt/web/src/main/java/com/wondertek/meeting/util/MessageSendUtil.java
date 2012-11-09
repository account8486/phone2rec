package com.wondertek.meeting.util;

import java.util.List;

import com.wondertek.meeting.model.Journey;
import com.wondertek.meeting.model.JourneyMember;
import com.wondertek.meeting.model.Meeting;
import com.wondertek.meeting.model.User;
import com.wondertek.meeting.model.Vehicle;
import com.wondertek.meeting.model.VehicleDriver;

public class MessageSendUtil {
	
	/**
	 * 把模板转化为需要的信息
	 * 
	 * @param messageContent
	 * @param user
	 * @param meetingId
	 * @return
	 */
	public static String converTemplateToContent(String messageContent, User user, String meetingId) {
		return converTemplateToContent(messageContent, user).replace("({1})", meetingId);
	}
	/**
	 * 把模板转化为需要的信息
	 * 
	 * @param messageContent
	 * @param user
	 * @return
	 */
	public static String converTemplateToContent(String messageContent, User user) {
		StringBuffer message = new StringBuffer(messageContent);
		// 会议号 手机号随机密码
		String rlt = (message.toString()).replace("({0})", StringUtil.isNotEmpty(user.getName()) ? user.getName() : "")
				.replace("({2})", StringUtil.isNotEmpty(user.getMobile()) ? user.getMobile() : "")
				.replace("({3})", Encrypt.decrypt(user.getPassword(), null));

		if (user.getMeetingMember().getJobIsDisplay() == 1) {
			rlt = rlt.replace("({4})", StringUtil.isNotEmpty(user.getMeetingMember().getJob()) ? user
					.getMeetingMember().getJob() : "");// Name
		} else {
			rlt = rlt.replace("({4})", "");// Name
		}
		return rlt;
	}

	/**
	 * 把模板转化为需要的信息
	 * 
	 * @param messageContent
	 * @param meeting
	 * @param sign
	 * @return
	 */
	public static String converTemplateToContent(String messageContent, Meeting meeting, String sign) {
		if (meeting != null && meeting.getName() != null) {
			messageContent = messageContent.replace("({5})", meeting.getName()).replace("({1})",
					String.valueOf(meeting.getId()));
		} else {
			messageContent = messageContent.replace("({5})", "").replace("({1})", "");
		}
		return messageContent.replace("({6})", sign);
	}
	
	
	/**
	 * 把模板转化为需要的信息
	 * 
	 * @param messageContent
	 * @param meeting
	 * @param sign
	 * @return
	 */
	public static String converTemplateToContent(String messageContent,
			Journey journey, VehicleDriver driver, Vehicle vehicle,
			String memberNames) {
		if (driver != null) {
			messageContent = messageContent.replace("({name})",
					StringUtil.null2Str(driver.getName()));
		}

		if (journey != null) {
			if (journey.getStartTime() != null) {
				messageContent = messageContent.replace(
						"({startTime})",
						StringUtil.null2Str(DateUtil.getFormatDate(
								journey.getStartTime(), "yyyy-MM-dd HH:mm")));
			}

			if (journey.getEndTime() != null) {

			}

			messageContent = messageContent
					.replace("({fromAddress})",
							StringUtil.null2Str(journey.getDeparture()))
					.replace("({toAddress})",
							StringUtil.null2Str(journey.getDestination()))
					.replace("({passengers})", StringUtil.null2Str(memberNames));
			
			

		}

		return messageContent;

	}
	
	
}

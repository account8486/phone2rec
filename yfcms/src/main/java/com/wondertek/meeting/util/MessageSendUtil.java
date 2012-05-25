package com.wondertek.meeting.util;

import com.wondertek.meeting.model.User;

public class MessageSendUtil {
	/**
	 * 把模板转化为需要的信息
	 * 
	 * @param messageContent
	 * @param user
	 * @param meetingId
	 * @return
	 */
	public static String converTemplateToContent(String messageContent,
			User user, String meetingId) {
		StringBuffer message = new StringBuffer(messageContent);
		
		// 会议号 手机号随机密码
		String rlt = (message.toString())
				.replace(
						"({0})",
						StringUtil.isNotEmpty(user.getName()) ? user.getName()
								: "")
				.replace("({1})", meetingId)
				.replace(
						"({2})",
						StringUtil.isNotEmpty(user.getMobile()) ? user
								.getMobile() : "")
				.replace("({3})", Encrypt.decrypt(user.getPassword(), null));

		if (user.getMeetingMember().getJobIsDisplay() == 1) {
			rlt = rlt.replace("({4})", StringUtil.isNotEmpty(user
					.getMeetingMember().getJob()) ? user.getMeetingMember()
					.getJob() : "");// Name
		} else {
			rlt = rlt.replace("({4})", "");// Name
		}

		return rlt;
	}
	
}

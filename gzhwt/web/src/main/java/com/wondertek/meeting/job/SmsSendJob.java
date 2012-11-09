/**
 * 
 */
package com.wondertek.meeting.job;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.wondertek.meeting.manager.SmsSender;
import com.wondertek.meeting.model.MeetingSms;
import com.wondertek.meeting.model.User;
import com.wondertek.meeting.service.MeetingService;
import com.wondertek.meeting.service.MeetingSmsService;

/**
 * @author rain
 * 
 */
public class SmsSendJob {
	@Autowired
	private MeetingSmsService meetingSmsService;
	@Autowired
	private MeetingService meetingService;

	private String serverIP;

	private Logger log = LoggerFactory.getLogger(this.getClass());

	/**
	 * 短信发送任务处理器
	 */
	public void processor() {
		try {
			/*if (!StringUtils.equals(IPRequest.getLocalAdress(), serverIP)) {
				String osName = System.getProperty("os.name");
				if (!osName.toLowerCase().contains("windows")) {
					// 如果是WINDOWS系统 则不打印LOG
					log.info("osName:" + osName + ",定时发送短信主机IP:smsSendJob ip:"
							+ IPRequest.getLocalAdress());
				}
				return;
			}*/
			//防止在本机环境一直打印LOG,添加此段程序。如过需要发送短信  请注释掉。
			String osName = System.getProperty("os.name");
			if (osName.toLowerCase().contains("windows")) {
				return;
			}
			
			final List<MeetingSms> messages = meetingSmsService.queryScheduledSmsList();
			for (MeetingSms message : messages) {
				// final Meeting meeting =
				// meetingService.findById(message.getMeetingId());
				// String customerServicePhone;
				// if (meeting != null) {
				// customerServicePhone = meeting.getServiceNumber();
				// } else {
				// customerServicePhone = "";
				// }
				final String content = message.getMessages();
				final Set<User> recipients = message.getRecipient();
				
				//如果子表有记录
				if(recipients!=null&&recipients.size()>0){
					for (Iterator<User> it = recipients.iterator(); it.hasNext();) {
						User recipient = it.next();
						final boolean sendStatus = SmsSender.sendSms(recipient.getMobile(), content);
						// write state into table meeting_sms_recipient;
						// 1: 已发送; 9: 发送失败;
						if (sendStatus) {
							meetingSmsService.updateRecipientState(message.getId(), recipient.getId(), 1);
						} else {
							meetingSmsService.updateRecipientState(message.getId(), recipient.getId(), 9);
						}
					}
					
					meetingSmsService.updateSmsState(message.getId(), 1);
				} else {
					//添加单个发送的信息
					final boolean sendStatus = SmsSender.sendSms(
							message.getMobile(), content);
					if (sendStatus) {
						meetingSmsService.updateSmsState(message.getId(), 1);
					} else {
						meetingSmsService.updateSmsState(message.getId(), 1);
					}
				}
			}
		} catch (Exception e) {
			log.warn("Sms Send Job Processor Exception.", e.getMessage());
		}
	}

	/**
	 * @param serverIP
	 *            the serverIP to set
	 */
	public void setServerIP(String serverIP) {
		this.serverIP = serverIP;
	}
}

/**
 * RFID触发任务信息
 * Author: 张国敬
 * Copyrights: 版权归上海网达软件有限公司安徽分公司所有
 * Create at: 2012-2-14
 */
package com.wondertek.meeting.model.rfid;

import com.wondertek.meeting.model.BaseObject;
import com.wondertek.meeting.model.Meeting;

public class TriggerTask extends BaseObject {
	private static final long serialVersionUID = 1L;

	private Integer id;
	private Meeting meeting; // 会议
	private Integer triggerNotify = 0; // RFID设备触发时是否执行任务，0-不执行，1-执行
	private Integer delayTime = 0; // 触发后延迟发送时间，单位：秒
	private Integer sendSMS = 0; // 是否发送短信,0-不发送，1-发送
	private Integer displayWelcome = 0; // 是否在电子屏幕显示欢迎语,0-不显示，1-显示
	private Integer sendMeetingAgenda = 0; // 是否发送会议议程，0-不发送，1-发送
	private Integer sendOtherInfo = 0; // 是否发送其他信息，0-不发送，1-发送
	private Integer signIn = 0; // 是否记录签到信息，0-不记录，1-记录
	private String otherInfo; // 待发送的其他信息
	private String smsTemplate; // 待发送的短信模板

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Meeting getMeeting() {
		return meeting;
	}

	public void setMeeting(Meeting meeting) {
		this.meeting = meeting;
	}

	public Integer getTriggerNotify() {
		return triggerNotify;
	}

	public void setTriggerNotify(Integer triggerNotify) {
		this.triggerNotify = triggerNotify;
	}

	public Integer getDelayTime() {
		return delayTime;
	}

	public void setDelayTime(Integer delayTime) {
		this.delayTime = delayTime;
	}

	public Integer getSendSMS() {
		return sendSMS;
	}

	public void setSendSMS(Integer sendSMS) {
		this.sendSMS = sendSMS;
	}

	public Integer getDisplayWelcome() {
		return displayWelcome;
	}

	public void setDisplayWelcome(Integer displayWelcome) {
		this.displayWelcome = displayWelcome;
	}

	public Integer getSendMeetingAgenda() {
		return sendMeetingAgenda;
	}

	public void setSendMeetingAgenda(Integer sendMeetingAgenda) {
		this.sendMeetingAgenda = sendMeetingAgenda;
	}

	public Integer getSendOtherInfo() {
		return sendOtherInfo;
	}

	public void setSendOtherInfo(Integer sendOtherInfo) {
		this.sendOtherInfo = sendOtherInfo;
	}

	public String getOtherInfo() {
		return otherInfo;
	}

	public void setOtherInfo(String otherInfo) {
		this.otherInfo = otherInfo;
	}

	public String getSmsTemplate() {
		return smsTemplate;
	}

	public void setSmsTemplate(String smsTemplate) {
		this.smsTemplate = smsTemplate;
	}

	public Integer getSignIn() {
		return signIn;
	}

	public void setSignIn(Integer signIn) {
		this.signIn = signIn;
	}

}

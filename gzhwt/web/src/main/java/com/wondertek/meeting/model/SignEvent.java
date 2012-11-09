/** 
*  
*/
package com.wondertek.meeting.model;

import java.io.Serializable;
/**
 * RFID 签到事件管理
* @ClassName: SignEvent 
* @Description: TODO
* @author zouxiaoming
* @date Aug 9, 2012 11:01:26 AM 
*
 */
@SuppressWarnings("serial")
public class SignEvent extends BaseObject implements Serializable {

	private Long id;

	/**
	 * 签到事件名称 如上班签到，下班签到等
	 */
	private String eventName;
	
	/**
	 * 签到日期 格式为2012-12-12
	 */
	private String signDate;
	
	/**
	 * 签到开始时间   格式为 08:20
	 */
	private String  signInBeginTime;
	
	
	/**
	 * 签到结束时间    格式为 08:20
	 */
	private String  signInEndTime;
	
	
	/**
	 * 签退开始时间   格式为 08:20
	 */
	private String  signOutBeginTime;
	
	
	/**
	 * 签退结束时间    格式为 08:20
	 */
	private String  signOutEndTime;
	
	
	/**
	 * 关联的会议
	 */
	private Long meetId;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}



	public void setMeetId(Long meetId) {
		this.meetId = meetId;
	}

	public Long getMeetId() {
		return meetId;
	}

	public String getSignDate() {
		return signDate;
	}

	public void setSignDate(String signDate) {
		this.signDate = signDate;
	}

	public String getSignInBeginTime() {
		return signInBeginTime;
	}

	public void setSignInBeginTime(String signInBeginTime) {
		this.signInBeginTime = signInBeginTime;
	}

	public String getSignInEndTime() {
		return signInEndTime;
	}

	public void setSignInEndTime(String signInEndTime) {
		this.signInEndTime = signInEndTime;
	}

	public String getSignOutBeginTime() {
		return signOutBeginTime;
	}

	public void setSignOutBeginTime(String signOutBeginTime) {
		this.signOutBeginTime = signOutBeginTime;
	}

	public String getSignOutEndTime() {
		return signOutEndTime;
	}

	public void setSignOutEndTime(String signOutEndTime) {
		this.signOutEndTime = signOutEndTime;
	}

	
	
	
	
}

/**
 * 卡片信息
 * Author: 张国敬
 * Copyrights: 版权归上海网达软件有限公司安徽分公司所有
 * Create at: Feb 10, 2012
 */
package com.wondertek.meeting.model.rfid;

import java.util.Date;

import com.wondertek.meeting.model.BaseObject;
import com.wondertek.meeting.model.Meeting;

public class Card extends BaseObject {
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String uid; //RFID标签UID
	private Date registerTime; // 登录时间
	private Integer state; // 状态：1-有效，2-作废
	private Meeting meeting; // 会议

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public Date getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Meeting getMeeting() {
		return meeting;
	}

	public void setMeeting(Meeting meeting) {
		this.meeting = meeting;
	}

}

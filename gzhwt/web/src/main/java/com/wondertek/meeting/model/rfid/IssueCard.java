/**
 * 发放卡片
 * Author: 张国敬
 * Copyrights: 版权归上海网达软件有限公司安徽分公司所有
 * Create at: Feb 10, 2012
 */
package com.wondertek.meeting.model.rfid;

import java.util.Date;

import com.wondertek.meeting.model.BaseObject;
import com.wondertek.meeting.model.Meeting;
import com.wondertek.meeting.model.MeetingMember;
import com.wondertek.meeting.model.User;

public class IssueCard extends BaseObject {
	private static final long serialVersionUID = 1L;

	private Integer id;
	private Meeting meeting; // 会议
	private User owner; // 用卡人
	private Card card; // 卡片
	private Date issueTime; // 发放时间
	private Date lossTime; // 挂失时间
	private String lossReason; // 挂失原因
	private Integer state; // 状态： 1-正常，2-挂失

	public Meeting getMeeting() {
		return meeting;
	}

	public void setMeeting(Meeting meeting) {
		this.meeting = meeting;
	}


	public Card getCard() {
		return card;
	}

	public void setCard(Card card) {
		this.card = card;
	}

	public Date getIssueTime() {
		return issueTime;
	}

	public void setIssueTime(Date issueTime) {
		this.issueTime = issueTime;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public Date getLossTime() {
		return lossTime;
	}

	public void setLossTime(Date lossTime) {
		this.lossTime = lossTime;
	}

	public String getLossReason() {
		return lossReason;
	}

	public void setLossReason(String lossReason) {
		this.lossReason = lossReason;
	}
}

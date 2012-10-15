package com.guo.yf.model.mail;

import java.util.Date;

import com.wondertek.meeting.model.BaseObject;

public class MimeMail extends BaseObject {
	
	private static final long serialVersionUID = 167122038245514368L;
	
	private String id;
	private String mailFrom;
	private String mailTo;
	private String mailCc;
	private String mailSubject;
	private String mailText;

	private Date createTime;// 创建时间
	private Long creator;
	private Date modifyTime;// 修改时间
	private Long modifier;
	private Integer sendStatus;
	
	public Integer getSendStatus() {
		return sendStatus;
	}
	public void setSendStatus(Integer sendStatus) {
		this.sendStatus = sendStatus;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMailFrom() {
		return mailFrom;
	}
	public void setMailFrom(String mailFrom) {
		this.mailFrom = mailFrom;
	}
	public String getMailTo() {
		return mailTo;
	}
	public void setMailTo(String mailTo) {
		this.mailTo = mailTo;
	}
	public String getMailCc() {
		return mailCc;
	}
	public void setMailCc(String mailCc) {
		this.mailCc = mailCc;
	}
	public String getMailSubject() {
		return mailSubject;
	}
	public void setMailSubject(String mailSubject) {
		this.mailSubject = mailSubject;
	}
	public String getMailText() {
		return mailText;
	}
	public void setMailText(String mailText) {
		this.mailText = mailText;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Long getCreator() {
		return creator;
	}
	public void setCreator(Long creator) {
		this.creator = creator;
	}
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	public Long getModifier() {
		return modifier;
	}
	public void setModifier(Long modifier) {
		this.modifier = modifier;
	}
	
	

}

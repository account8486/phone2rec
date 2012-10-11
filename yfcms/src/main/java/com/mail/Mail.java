package com.mail;

import java.io.File;

import com.wondertek.meeting.model.BaseObject;
/**
 * 邮件发送的实体，最后无论是从内存中
 * 或者从数据库中读取的数据都要拼接成
 * Mail指定的格式
 * @author GUO
 *
 */
public class Mail extends BaseObject {

	private static final long serialVersionUID = -7222177439163342261L;

	private String from;
	private String to;
	private String subject;
	private String text;
	private String cc;
	private File[] attachmentFiles;
	// 默认不带有附件
	private boolean withAttachment = false;

	public boolean isWithAttachment() {
		return withAttachment;
	}

	public void setWithAttachment(boolean withAttachment) {
		this.withAttachment = withAttachment;
	}

	public String getFrom() {
		return from;
	}

	/**
	 * set mail send from
	 * 
	 * @param from
	 */
	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	/**
	 * set mail send to
	 * 
	 * @param to
	 */
	public void setTo(String to) {
		this.to = to;
	}

	public String getSubject() {
		return subject;
	}

	/**
	 * set mail subject or title
	 * 
	 * @param subject
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getText() {
		return text;
	}

	/**
	 * set mail text
	 * 
	 * @param text
	 */
	public void setText(String text) {
		this.text = text;
	}

	public String getCc() {
		return cc;
	}

	/**
	 * set mail copy to
	 * 
	 * @param cc
	 */
	public void setCc(String cc) {
		this.cc = cc;
	}

	public File[] getAttachmentFiles() {
		return attachmentFiles;
	}

	/**
	 * set mail attachment files
	 * 
	 * @param attachmentFiles
	 */
	public void setAttachmentFiles(File[] attachmentFiles) {
		this.attachmentFiles = attachmentFiles;
	}

}

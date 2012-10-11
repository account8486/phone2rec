package com.mail.service;

import javax.mail.MessagingException;

import com.mail.Mail;

/**
 * 发送邮件接口
 * @author GUO
 */
public interface SendMailServcie {
	public void doSend(Mail mail) throws MessagingException;

}

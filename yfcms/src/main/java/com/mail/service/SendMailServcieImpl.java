package com.mail.service;
import java.io.File;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import org.jfree.util.Log;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.mail.Mail;


/**
 * Send Mail ToolKit
 * 1.send simple mail
 * 2.send mail with attachment
 * @author GUO
 */
public class SendMailServcieImpl implements SendMailServcie {
	public ApplicationContext ctx = null;

	public SendMailServcieImpl() {
		// 获取上下文
		ctx = new ClassPathXmlApplicationContext(
				"spring/applicationContext-mail.xml");
	}

	/**
	 * 邮件公共发送类 唯一对外接口
	 * 
	 * @throws MessagingException
	 */
	public void doSend(Mail mail) throws MessagingException {
		// 判断是否有附件
		if (mail.isWithAttachment()) {
			// 发送带有附件的邮件
			this.sendAttachMentMail(mail);
		} else {
			// 发送简单的邮件
			this.sendSimpleMailMessage(mail);
		}
	}

	/**
	 * 发送普通邮件
	 */
	protected void sendSimpleMailMessage(Mail mail) {
		// 获取JavaMailSender bean
		JavaMailSender sender = (JavaMailSender) ctx.getBean("mailSender");
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		try {
			simpleMailMessage.setTo(mail.getTo());// 接受者
			simpleMailMessage.setCc(mail.getCc());
			// simpleMailMessage.setReplyTo("dddd");????
			simpleMailMessage.setFrom(mail.getFrom());// 发送者
			simpleMailMessage.setSubject(mail.getSubject());// 主题
			simpleMailMessage.setText(mail.getText());// 邮件内容

			sender.send(simpleMailMessage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @deprecated Send HTML mail
	 * @throws MessagingException
	 * 
	 */
	private void sendHtmlMail() throws MessagingException {

		JavaMailSender sender = (JavaMailSender) ctx.getBean("mailSender");
		MimeMessage mailMessage = sender.createMimeMessage();
		MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage,
				true, "utf-8");
		try {
			messageHelper.setTo("guoxu@wondertek.com.cn");// 接受者
			messageHelper.setFrom("guoxu775@163.com");// 发送者
			messageHelper.setSubject("测试邮件");// 主题
			// 邮件内容，注意加参数true,表示启用HTML格式
			// 可考虑整成模板 free maker
			messageHelper
					.setText(
							"<html><head></head><body><h1>hello!!chao.wang我是你旭哥</h1></body></html>",
							true);
			sender.send(mailMessage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Send HTML mail with attachment
	 * 
	 * @throws MessagingException
	 */
	private void sendAttachMentMail(Mail mail) throws MessagingException {
		// 获取JavaMailSender bean
		JavaMailSender sender = (JavaMailSender) ctx.getBean("mailSender");
		MimeMessage mailMessage = sender.createMimeMessage();
		// 设置utf-8或GBK编码，否则邮件会有乱码
		MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage,
				true, "utf-8");
		try {
			messageHelper.setTo(mail.getTo());// 接受者
			messageHelper.setFrom(mail.getFrom());// 发送者
			messageHelper.setSubject(mail.getSubject());// 主题
			// 邮件内容，注意加参数true,表示发送的为HTML格式
			messageHelper.setText(mail.getText(), true);
			// messageHelper.addAttachment(attachmentFilename, file);
			// 附件内容
			// messageHelper.addInline("a", new File("F:/pri/DXD_06.png"));
			// messageHelper.addInline("b", new File("F:/pri/DXD_06.png"));
			// 添加附件
			File[] files = mail.getAttachmentFiles();
			for (int i = 0; i < files.length; i++) {
				// 这里的方法调用和插入图片是不同的，使用MimeUtility.encodeWord()来解决附件名称的中文问题
				messageHelper.addAttachment(
						MimeUtility.encodeWord(files[i].getName()), files[i]);
			}

			// 发送邮件
			Log.debug("send mail start...");
			sender.send(mailMessage);
			Log.debug("send mail end");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}



}

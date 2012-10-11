package com.mail;

import java.io.File;

import com.mail.service.SendMailServcie;
import com.mail.service.SendMailServcieImpl;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		SendMailServcie sendMail = new SendMailServcieImpl();

		String from = "guoxu775@163.com";
		String to = "guoxu@wondertek.com.cn";
		String subject = "测试test mail send!";
		String text = "asdfasdfasdfasdf";

		Mail mail = new Mail();
		mail.setFrom(from);
		mail.setTo(to);
		mail.setSubject(subject);
		mail.setText(text);
		mail.setCc(to);

		File[] files = new File[2];
		for (int i = 0; i < files.length; i++) {
			File file = new File("F:/pri/dxd_PLAN.png");
			files[i] = file;
		}
		mail.setAttachmentFiles(files);

		try {
			sendMail.doSend(mail);
			mail.setWithAttachment(true);
			sendMail.doSend(mail);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	

	}

}

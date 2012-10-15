package com.guo.yf.service.impl;

import java.util.Date;
import java.util.List;
import javax.mail.MessagingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.guo.yf.dao.MimeMailDao;
import com.guo.yf.model.mail.MimeMail;
import com.guo.yf.service.MimeMailService;
import com.mail.Mail;
import com.mail.service.SendMailServcie;
import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.exception.HibernateDaoSupportException;
import com.wondertek.meeting.service.impl.BaseServiceImpl;
import com.wondertek.meeting.util.StringUtil;

public class MimeMailServiceImpl extends BaseServiceImpl<MimeMail, String> implements
		MimeMailService {
	
	MimeMailDao mimeMailDao;
	SendMailServcie sendMailServcie;
	public Logger log = LoggerFactory.getLogger(this.getClass());
	
	public SendMailServcie getSendMailServcie() {
		return sendMailServcie;
	}

	public void setSendMailServcie(SendMailServcie sendMailServcie) {
		this.sendMailServcie = sendMailServcie;
	}

	public MimeMailDao getMimeMailDao() {
		return mimeMailDao;
	}

	public void setMimeMailDao(MimeMailDao mimeMailDao) {
		this.mimeMailDao = mimeMailDao;
		this.basicDao=mimeMailDao;
	}
	
	/**
	 * 分页程序
	 */
	public Pager<MimeMail> getMailPager(int currentPage,int pageSize,String mailContent) throws HibernateDaoSupportException{
		StringBuffer hql=new StringBuffer();
		hql.append(" select mail from  MimeMail mail where 1=1 ");
		
		if(StringUtil.isNotEmpty(mailContent)){
			hql.append("  and ( mail.mailSubject like '%"+mailContent+"%' or mail.mailText like '%"+mailContent+"%' ) ");
		}
		
		Pager<MimeMail> pager=mimeMailDao.findPager(hql.toString(), currentPage, pageSize, null);
		
		return pager;
	}
	
	/**
	 * 定时发送邮件
	 */
	public void sendMailJob() {
		try {
		//	System.out.println("send mail:" + new Date());
			StringBuffer hql = new StringBuffer();
			hql.append(" select mail from  MimeMail mail where 1=1 and mail.sendStatus=0 ");
			List<MimeMail> mailList = mimeMailDao.getObjects(hql.toString(),
					null);
			
			for (MimeMail mail : mailList) {
				Mail mailVO = new Mail();
				mailVO.setFrom(mail.getMailFrom());
				mailVO.setTo(mail.getMailTo());
				mailVO.setText(mail.getMailText());
				mailVO.setSubject(mail.getMailSubject());
				mailVO.setWithAttachment(true);
				mailVO.setCc(mail.getMailCc());
				sendMailServcie.doSend(mailVO);
				//log.debug(String.valueOf(mailVO));
				//更新状态
				mail.setSendStatus(1);
				mail.setModifyTime(new Date());
				mimeMailDao.saveOrUpdateEntity(mail);
				
				log.debug("log mail!");
				
			}
		} catch (HibernateDaoSupportException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}

	}
	


}

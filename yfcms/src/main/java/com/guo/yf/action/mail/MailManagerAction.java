package com.guo.yf.action.mail;

import java.util.Date;

import javax.mail.MessagingException;

import com.guo.yf.model.mail.MimeMail;
import com.guo.yf.service.MimeMailService;
import com.mail.Mail;
import com.mail.service.SendMailServcie;
import com.wondertek.meeting.action.base.BaseAction;
import com.wondertek.meeting.exception.HibernateDaoSupportException;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.util.StringUtil;
import com.wondertek.meeting.util.ValidateUtil;

/**
 * 邮件管理
 * @author GUO
 *
 */
public class MailManagerAction extends BaseAction {


	private static final long serialVersionUID = 512173604791939357L;
	
	MimeMailService mimeMailService;
	SendMailServcie sendMailServcie;
	MimeMail mail;
	
	public MimeMailService getMimeMailService() {
		return mimeMailService;
	}

	public void setMimeMailService(MimeMailService mimeMailService) {
		this.mimeMailService = mimeMailService;
	}
	
	public MimeMail getMail() {
		return mail;
	}

	public void setMail(MimeMail mail) {
		this.mail = mail;
	}
	
	public SendMailServcie getSendMailServcie() {
		return sendMailServcie;
	}

	public void setSendMailServcie(SendMailServcie sendMailServcie) {
		this.sendMailServcie = sendMailServcie;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	/**
	 * 新增mail
	 * @return
	 */
	public String toAddMail(){
		
		return "toAddMail";
	}
	
	
	/**
	 * 新增mail
	 * @return
	 * @throws ServiceException 
	 * @throws MessagingException 
	 */
	public String addMail(){
		log.debug("mail："+String.valueOf(mail));
		try{
			if(StringUtil.isEmpty(mail.getMailTo())||ValidateUtil.isNotEmail(mail.getMailTo())){
				this.resultMap.put("retMsg", "收件人地址为空或者格式不 正确，请修改！");
				return "addMail";
			}
			
			mail.setMailFrom("mail_yfcms@163.com");
			mail.setCreateTime(new Date());
			mail.setCreator(this.getAdminUserIdFromSession());
			this.mimeMailService.saveOrUpdate(mail);
			
			Mail mailVO=new Mail();
			mailVO.setFrom(mail.getMailFrom());
			mailVO.setTo(mail.getMailTo());
			mailVO.setText(mail.getMailText());
			mailVO.setSubject(mail.getMailSubject());
			mailVO.setWithAttachment(true);
			mailVO.setCc(mail.getMailCc());
			sendMailServcie.doSend(mailVO);
			
			this.resultMap.put("retMsg", "已推送到系统");
		}catch(Exception e){
			e.printStackTrace();
			this.resultMap.put("retMsg",e.getMessage());
		}
		
		return "addMail";
	}
	
	
	/**
	 * 获取一个Mail list
	 * @return
	 */
	public String getMailList() {

		String mailContent=this.getParameter("mailContent");
		log.debug("mailContent:"+mailContent);
		try {
			this.setAttribute("pager",
					mimeMailService.getMailPager(currentPage, pageSize,mailContent));
		} catch (HibernateDaoSupportException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.setAttribute("mailContent", mailContent);
		
		return "mailList";
	}
	
	/**
	 * 
	 * @return
	 */
	public String batchDelMails(){
		
		String ids = this.getParameter("ids");
		String[] idsArr = ids.split(",");
		for (int i = 0; i < idsArr.length; i++) {
			try {
				mimeMailService.delete(mimeMailService.findById(String
						.valueOf(idsArr[i])));
				log.debug("delete mail succeed,id=:" + idsArr[i]);
			} catch (Exception e) {
				e.printStackTrace();
				log.info("batch delete mail fail because by id=:"
						+ idsArr[i]);
			}
		}
		
		return this.getMailList();
		
	}
	
	
	

}

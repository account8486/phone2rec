package com.guo.yf.service.impl;

import com.guo.yf.dao.MimeMailDao;
import com.guo.yf.model.mail.MimeMail;
import com.guo.yf.service.MimeMailService;
import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.exception.HibernateDaoSupportException;
import com.wondertek.meeting.service.impl.BaseServiceImpl;
import com.wondertek.meeting.util.StringUtil;

public class MimeMailServiceImpl extends BaseServiceImpl<MimeMail, String> implements
		MimeMailService {
	
	MimeMailDao mimeMailDao;

	public MimeMailDao getMimeMailDao() {
		return mimeMailDao;
	}

	public void setMimeMailDao(MimeMailDao mimeMailDao) {
		this.mimeMailDao = mimeMailDao;
		this.basicDao=mimeMailDao;
	}
	
	
	
	public Pager<MimeMail> getMailPager(int currentPage,int pageSize,String mailContent) throws HibernateDaoSupportException{
		StringBuffer hql=new StringBuffer();
		hql.append(" select mail from  MimeMail mail where 1=1 ");
		
		if(StringUtil.isNotEmpty(mailContent)){
			hql.append("  and ( mail.mailSubject like '%"+mailContent+"%' or mail.mailText like '%"+mailContent+"%' ) ");
		}
		
		Pager<MimeMail> pager=mimeMailDao.findPager(hql.toString(), currentPage, pageSize, null);
		
		return pager;
	}


}

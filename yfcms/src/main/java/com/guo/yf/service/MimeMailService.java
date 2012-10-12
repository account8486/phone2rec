package com.guo.yf.service;

import com.guo.yf.model.mail.MimeMail;
import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.exception.HibernateDaoSupportException;
import com.wondertek.meeting.service.BaseService;

public interface MimeMailService extends BaseService<MimeMail, String> {
	
	public Pager<MimeMail> getMailPager(int currentPage,int pageSize,String mailContent) throws HibernateDaoSupportException;

}

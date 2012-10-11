package com.guo.yf.service.impl;

import com.guo.yf.dao.MimeMailDao;
import com.guo.yf.model.mail.MimeMail;
import com.guo.yf.service.MimeMailService;
import com.wondertek.meeting.service.impl.BaseServiceImpl;

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


}

package com.wondertek.meeting.service.impl;

import com.wondertek.meeting.dao.QuestionItemDao;
import com.wondertek.meeting.model.QuestionItem;
import com.wondertek.meeting.service.QuestionItemService;

/** 
 * @ClassName: QuestionServiceImpl 
 * @Description: 试题类服务接口实现类
 * @author zouxiaoming
 * @date Feb 2, 2012 10:20:56 AM 
 *  
 */
public class QuestionItemServiceImpl extends BaseServiceImpl<QuestionItem, Long> implements QuestionItemService {
	private QuestionItemDao questionItemDao;

	/**
	 * @param questionItemDao
	 */
	public void setQuestionItemDao(QuestionItemDao questionItemDao) {
		this.basicDao=questionItemDao;
		this.questionItemDao = questionItemDao;
	}

	/**
	 * @Description
	 * @return the questionItemDao
	 */
	public QuestionItemDao getQuestionItemDao() {
		return questionItemDao;
	}

}

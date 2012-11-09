package com.wondertek.meeting.service.impl;

import java.util.HashMap;
import java.util.Map;

import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.dao.QuestionDao;
import com.wondertek.meeting.dao.QuestionItemDao;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.Question;
import com.wondertek.meeting.model.QuestionItem;
import com.wondertek.meeting.service.QuestionService;
import com.wondertek.meeting.util.StringUtil;

/** 
 * @ClassName: QuestionServiceImpl 
 * @Description: 试题类服务接口实现类
 * @author zouxiaoming
 * @date Feb 2, 2012 10:20:56 AM 
 *  
 */
public class QuestionServiceImpl extends BaseServiceImpl<Question, Long> implements QuestionService {
	private QuestionDao questionDao;
	private QuestionItemDao questionItemDao;

	/**
	 * @param questionDao
	 */
	public void setQuestionDao(QuestionDao questionDao) {
		this.setBaseDao(questionDao);
		this.questionDao = questionDao;
	}

	/**
	 * @Description
	 * @return the questionDao
	 */
	public QuestionDao getQuestionDao() {
		return questionDao;
	}

	@Override
	public void saveQuestion(Question q, String[] contents) {
		this.questionDao.saveOrUpdateEntity(q);
		if(q.getType()!=3){// 非简述题
			if(contents!=null&&contents.length>0){
				for(String str:contents){
					if(!StringUtil.isEmpty(str)){
						QuestionItem item=new QuestionItem();
						item.setContent(str);
						item.setQuestion(q);
						this.questionItemDao.saveOrUpdateEntity(item);
					}
				}
			}
		}
	}

	/**
	 * @param questionItemDao
	 */
	public void setQuestionItemDao(QuestionItemDao questionItemDao) {
		this.questionItemDao = questionItemDao;
	}

	/**
	 * @Description
	 * @return the questionItemDao
	 */
	public QuestionItemDao getQuestionItemDao() {
		return questionItemDao;
	}

	@Override
	public Pager<Question> enterExam(Long paperId, int currentPage, int pageSize) throws ServiceException {
		String hql="from Question q where q.state=1 and q.paper.id=:paperId";
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("paperId", paperId);
		return this.questionDao.findPager(hql, currentPage, pageSize, properties);
	}


}

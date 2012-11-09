package com.wondertek.meeting.service.impl;


import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.wondertek.meeting.dao.AnswerPaperDao;
import com.wondertek.meeting.dao.PaperDao;
import com.wondertek.meeting.dao.QuestionDao;
import com.wondertek.meeting.dao.QuestionItemDao;
import com.wondertek.meeting.exception.HibernateDaoSupportException;
import com.wondertek.meeting.model.AnswerPaper;
import com.wondertek.meeting.model.Paper;
import com.wondertek.meeting.model.Question;
import com.wondertek.meeting.model.QuestionItem;
import com.wondertek.meeting.model.User;
import com.wondertek.meeting.service.AnswerPaperService;

/**
 * 
* @ClassName: AnswerPaperService 
* @Description: 答卷服务接口
* @author zouxiaoming
* @date Feb 23, 2012 3:07:56 PM 
*
 */
public class AnswerPaperServiceImpl extends BaseServiceImpl<AnswerPaper, Long> implements AnswerPaperService {
	Log log = LogFactory.getLog(this.getClass());

	private AnswerPaperDao answerPaperDao;
	private PaperDao paperDao;
	private QuestionItemDao questionItemDao;
	private QuestionDao questionDao;
	
	
	@Override
	public void processPaper(List<Long> checks,Long[] quests,String[] contents, User user, Integer state,Paper paper) throws HibernateDaoSupportException {
		paper=this.paperDao.findById(paper.getId(), Paper.class);
		if(checks!=null&&checks.size()>0){
			for(Long c:checks){
				AnswerPaper aPaper=new AnswerPaper();
				aPaper.setUser(user);
				aPaper.setState(state);
				aPaper.setPaper(paper);
				QuestionItem item=this.questionItemDao.findById(c, QuestionItem.class);
				aPaper.setItem(item);
				aPaper.setQuestion(item.getQuestion());
				aPaper.setType(item.getQuestion().getType());
				aPaper.setContent(item.getContent());
				aPaper.setAnswerTime(new Date());
				this.answerPaperDao.saveOrUpdateEntity(aPaper);
				item.setCount(item.getCount()+1);
				this.questionItemDao.saveOrUpdateEntity(item);
			}
		}
		if(quests!=null&&quests.length>0){
			for(int i=0;i<quests.length;i++){
				AnswerPaper aPaper=new AnswerPaper();
				aPaper.setUser(user);
				aPaper.setState(state);
				aPaper.setPaper(paper);
				Question question=this.questionDao.findById(quests[i], Question.class);
				aPaper.setQuestion(question);
				aPaper.setType(question.getType());
				aPaper.setContent(contents[i]);
				aPaper.setAnswerTime(new Date());
				this.answerPaperDao.saveOrUpdateEntity(aPaper);
		}
		}
		paper.setCount(paper.getCount()+1);
		this.paperDao.saveOrUpdateEntity(paper);
	}
	
	
	@Override
	public List<AnswerPaper> findContent(Long pageId,Long questionId) {
		return this.answerPaperDao.findContent(pageId,questionId);
	}



	@Override
	public List<AnswerPaper> findMyAnswerPaper(Long userIdFromSession,
			Long paperId) {
		return  this.answerPaperDao.findMyAnswerPaper(userIdFromSession,paperId);
	}
	
	@Override
	public List<AnswerPaper> findAllByPaperId(Long paperId) {
		return  this.answerPaperDao.findAllByPaperId(paperId);
	}
	
	/**
	 * @Description
	 * @return the answerPaperDao
	 */
	public AnswerPaperDao getAnswerPaperDao() {
		return answerPaperDao;
	}
	/**
	 * @param answerPaperDao
	 */
	public void setAnswerPaperDao(AnswerPaperDao answerPaperDao) {
		this.answerPaperDao = answerPaperDao;
	}
	/**
	 * @Description
	 * @return the paperDao
	 */
	public PaperDao getPaperDao() {
		return paperDao;
	}
	/**
	 * @param paperDao
	 */
	public void setPaperDao(PaperDao paperDao) {
		this.paperDao = paperDao;
	}
	/**
	 * @Description
	 * @return the questionItemDao
	 */
	public QuestionItemDao getQuestionItemDao() {
		return questionItemDao;
	}
	/**
	 * @param questionItemDao
	 */
	public void setQuestionItemDao(QuestionItemDao questionItemDao) {
		this.questionItemDao = questionItemDao;
	}



	/**
	 * @param questionDao
	 */
	public void setQuestionDao(QuestionDao questionDao) {
		this.questionDao = questionDao;
	}



	/**
	 * @Description
	 * @return the questionDao
	 */
	public QuestionDao getQuestionDao() {
		return questionDao;
	}


	



	
	
	
	
	
	
	
}

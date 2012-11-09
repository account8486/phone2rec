package com.wondertek.meeting.service.impl;

import java.util.List;

import com.wondertek.meeting.dao.AnswerPaperDao;
import com.wondertek.meeting.dao.PaperDao;
import com.wondertek.meeting.dao.QuestionDao;
import com.wondertek.meeting.exception.HibernateDaoSupportException;
import com.wondertek.meeting.model.Paper;
import com.wondertek.meeting.model.Question;
import com.wondertek.meeting.service.PaperService;

/** 
 * @ClassName: PaperServiceImpl 
 * @Description: 试卷类服务接口实现类
 * @author zouxiaoming
 * @date Feb 2, 2012 10:20:56 AM 
 *  
 */
public class PaperServiceImpl extends BaseServiceImpl<Paper, Long> implements PaperService {
	
	private PaperDao paperDao;
	private QuestionDao questionDao;
	private AnswerPaperDao answerPaperDao;

	
	@Override
	public List<Paper> findPaperByMeetId(Long meetingId) {
		return this.paperDao.findPaperByMeetId(meetingId);
	}


	@Override
	public void deletePaper(Long paperId) {
		this.questionDao.deleteAllByPaperId(paperId);// 删除试卷下的所有试题
		this.answerPaperDao.deleteAllAnswerByPaperId(paperId);// 删除关于此问卷的答案
		Paper p=null;
		try {
			p = this.paperDao.findById(paperId,Paper.class);
			this.paperDao. delete(p);
		} catch (HibernateDaoSupportException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public List<Question> findAllQuestion(Long paperId, boolean flag) {
		
		return this.questionDao.findAllQuestion(paperId,flag);
	}

	
	/**
	 * @param paperDao
	 */
	public void setPaperDao(PaperDao paperDao) {
		this.paperDao = paperDao;
		this.basicDao=paperDao;
	}

	/**
	 * @Description
	 * @return the paperDao
	 */
	public PaperDao getPaperDao() {
		return paperDao;
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


	@Override
	public List<Paper> findEnablePaper(Long meetingId) {
		return this.paperDao.findEnablePaper(meetingId);
	}


	/**
	 * @param answerPaperDao
	 */
	public void setAnswerPaperDao(AnswerPaperDao answerPaperDao) {
		this.answerPaperDao = answerPaperDao;
	}


	/**
	 * @Description
	 * @return the answerPaperDao
	 */
	public AnswerPaperDao getAnswerPaperDao() {
		return answerPaperDao;
	}


	


	

	
}

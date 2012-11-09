package com.wondertek.meeting.service;



import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.Question;

/** 
 * @ClassName: QuestionService 
 * @Description: 试题service
 * @author zouxiaoming
 * @date Jan 9, 2012 10:31:15 AM 
 *  
 */
public interface QuestionService extends BaseService<Question, Long> {

	/**
	 * 保存试题对象
	 * @param q
	 * @param contents
	 */
	public void saveQuestion(Question q,String[] contents);
	
	/**
	 * 进入调查service
	 * @param paperId
	 * @return
	 */
	public Pager<Question> enterExam(Long paperId,int currentPage,int pageSize) throws ServiceException;
	
}

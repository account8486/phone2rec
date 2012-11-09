package com.wondertek.meeting.dao;



import java.util.List;

import com.wondertek.meeting.model.Question;

/** 
 * @ClassName: QuestionDao 
 * @Description: 试题Dao
 * @author zouxiaoming
 * @date Feb 2, 2012 10:20:56 AM 
 *  
 */
public interface QuestionDao extends BaseDao<Question, Long> {
	
	/**
	 * 删除一个问卷调查下的所有试题
	 */
	public void deleteAllByPaperId(Long paperId);

	public List<Question> findAllQuestion(Long paperId, boolean flag);
}

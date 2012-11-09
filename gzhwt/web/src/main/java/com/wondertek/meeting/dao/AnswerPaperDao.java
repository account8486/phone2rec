package com.wondertek.meeting.dao;




import java.util.List;

import com.wondertek.meeting.model.AnswerPaper;

/**
 * 
* @ClassName: AnswerPaperDao 
* @Description: 答卷DAO接口
* @author zouxiaoming
* @date Feb 23, 2012 3:00:30 PM 
*
 */
public interface AnswerPaperDao extends BaseDao<AnswerPaper, Long> {

	public List<AnswerPaper> findContent(Long pageId,Long questionId);
	
	public void deleteAllAnswerByPaperId(Long paperId);

	public List<AnswerPaper> findMyAnswerPaper(Long userIdFromSession,
			Long paperId);

	public List<AnswerPaper> findAllByPaperId(Long paperId);

	

	
}

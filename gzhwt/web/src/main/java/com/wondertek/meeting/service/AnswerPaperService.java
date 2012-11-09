package com.wondertek.meeting.service;



import java.util.List;

import com.wondertek.meeting.exception.HibernateDaoSupportException;
import com.wondertek.meeting.model.AnswerPaper;
import com.wondertek.meeting.model.Paper;
import com.wondertek.meeting.model.User;

/**
 * 
* @ClassName: AnswerPaperService 
* @Description: 答卷服务接口
* @author zouxiaoming
* @date Feb 23, 2012 3:07:56 PM 
*
 */
public interface AnswerPaperService extends BaseService<AnswerPaper, Long> {

	/**
	 * 提交问卷
	 * @param checks
	 * @param quests
	 * @param contents
	 * @param user
	 * @param state
	 * @param paper
	 * @throws HibernateDaoSupportException
	 */
	public void processPaper(List<Long> checks,Long[] quests,String[] contents,User user,Integer state,Paper paper) throws HibernateDaoSupportException;
	
	/**
	 * 查出此问卷下所有的简述题答案
	 * @param pageId
	 * @return
	 */
	public List<AnswerPaper> findContent(Long pageId,Long questionId);
	
	
	/**
	 * 查出某一个试卷的所有答案
	 * @param pageId
	 * @return
	 */
	public List<AnswerPaper> findAllByPaperId(Long paperId);

	/**
	 * 查出用户自己的答卷
	 * @param userIdFromSession
	 * @param paperId
	 * @return
	 */
	public List<AnswerPaper> findMyAnswerPaper(Long userIdFromSession,
			Long paperId);
	
	
}

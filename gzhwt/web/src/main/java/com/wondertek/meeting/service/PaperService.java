package com.wondertek.meeting.service;


import java.util.List;

import com.wondertek.meeting.model.Paper;
import com.wondertek.meeting.model.Question;

/** 
 * @ClassName: PaperService 
 * @Description: 试卷service
 * @author zouxiaoming
 * @date Jan 9, 2012 10:31:15 AM 
 *  
 */
public interface PaperService extends BaseService<Paper, Long> {

	/**
	 * 通过会议Id查出此会议下的所有调查
	 * @param meetingId
	 * @return
	 */
	public List<Paper> findPaperByMeetId(Long meetingId);
	
	/**
	 * 删除一个问卷调查  级联删除子项
	 * @param paperId
	 */
	public void deletePaper(Long paperId);
	
	/**
	 * 查出所有的试题
	 * @param paperId
	 * @param flag  flag=true 表示状态为可用的  flag=false 表示查出所有的
	 * @return
	 */
	public List<Question> findAllQuestion(Long paperId,boolean flag);

	/**
	 * 查出某一个会议下所有有效的调查
	 * @param meetingId
	 * @return
	 */
	public List<Paper> findEnablePaper(Long meetingId);

	
	
	
}

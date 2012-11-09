package com.wondertek.meeting.dao;



import java.util.List;

import com.wondertek.meeting.model.Paper;

/** 
 * @ClassName: PaperDao 
 * @Description: 试卷Dao
 * @author zouxiaoming
 * @date Feb 2, 2012 10:20:56 AM 
 *  
 */
public interface PaperDao extends BaseDao<Paper, Long> {

	public List<Paper> findPaperByMeetId(Long meetingId);

	public List<Paper> findEnablePaper(Long meetingId);
	
	

	
}

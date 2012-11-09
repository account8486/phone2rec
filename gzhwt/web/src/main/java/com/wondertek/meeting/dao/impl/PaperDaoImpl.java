package com.wondertek.meeting.dao.impl;



import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.wondertek.meeting.dao.PaperDao;
import com.wondertek.meeting.model.Paper;

/** 
 * @ClassName: PaperDaoImpl 
 * @Description: 试卷DaoImpl
 * @author zouxiaoming
 * @date Feb 2, 2012 10:20:56 AM 
 *  
 */
public class PaperDaoImpl extends BaseDaoImpl<Paper, Long> implements PaperDao {
	Log log = LogFactory.getLog(this.getClass());

	@Override
	public List<Paper> findPaperByMeetId(Long meetingId) {
		String hql="from Paper p where p.meeting.id="+meetingId;
		@SuppressWarnings("unchecked")
		List<Paper> list=this.getHibernateTemplate().find(hql);
		return list;
	}

	@Override
	public List<Paper> findEnablePaper(Long meetingId) {
		String hql="from Paper p where p.meeting.id="+meetingId+" and p.state=1";
		@SuppressWarnings("unchecked")
		List<Paper> list=this.getHibernateTemplate().find(hql);
		return list;
	}




	
	
}

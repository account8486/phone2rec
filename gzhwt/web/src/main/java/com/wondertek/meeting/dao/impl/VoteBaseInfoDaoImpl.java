package com.wondertek.meeting.dao.impl;


import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.wondertek.meeting.dao.VoteBaseInfoDao;
import com.wondertek.meeting.model.VoteBaseInfo;

/** 
 * @ClassName: VoteBaseInfoDaoImpl 
 * @Description: 投票基本信息DaoImpl
 * @author zouxiaoming
 * @date Jan 9, 2012 10:31:15 AM 
 *  
 */
public class VoteBaseInfoDaoImpl extends BaseDaoImpl<VoteBaseInfo, Long> implements VoteBaseInfoDao {
	Log log = LogFactory.getLog(this.getClass());

	@Override
	public void addVote(VoteBaseInfo vote) {
		this.getHibernateTemplate().save(vote);
	}

	@Override
	public void deleteVote(VoteBaseInfo vote) {
		this.getHibernateTemplate().delete(vote);
		
	}

	@Override
	public List<VoteBaseInfo> findVoteByMeetId(Long meetingId) {
		String hql="from VoteBaseInfo vb where vb.meeting.id="+meetingId;
		List<VoteBaseInfo> list=this.getHibernateTemplate().find(hql);
		return list;
	}

	@Override
	public List<VoteBaseInfo> findVoteByMeetIdAndState(Long meetingId) {
		String hql="from VoteBaseInfo vb where vb.meeting.id="+meetingId+" and vb.state=1 order by vb.id desc";
		List<VoteBaseInfo> list=this.getHibernateTemplate().find(hql);
		return list;
	}


	
	
}

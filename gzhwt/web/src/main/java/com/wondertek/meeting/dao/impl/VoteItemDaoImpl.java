package com.wondertek.meeting.dao.impl;


import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.wondertek.meeting.dao.VoteItemDao;
import com.wondertek.meeting.model.VoteItem;

/** 
 * @ClassName:   VoteItemDaoImpl 
 * @Description: 投票选项DaoImpl
 * @author zouxiaoming
 * @date Jan 10, 2012 10:31:15 AM 
 *  
 */
public class VoteItemDaoImpl extends BaseDaoImpl<VoteItem, Long> implements VoteItemDao {
	Log log = LogFactory.getLog(this.getClass());

	@SuppressWarnings("unchecked")
	@Override
	public List<VoteItem> findItemByVoteId(Long voteId) {
		String hql="from VoteItem v where v.vote.id="+voteId;
		List<VoteItem> list=this.getHibernateTemplate().find(hql);
		return list;
	}


	
	
}

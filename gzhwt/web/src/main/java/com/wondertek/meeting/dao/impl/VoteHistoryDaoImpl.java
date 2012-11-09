package com.wondertek.meeting.dao.impl;



import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.wondertek.meeting.dao.VoteHistoryDao;
import com.wondertek.meeting.model.VoteHistory;

/** 
 * @ClassName: VoteHistoryDaoImpl 
 * @Description: 投票历史
 * @author zouxiaoming
 * @date Jan 31, 2012 3:08:42 PM 
 *  
 */
public class VoteHistoryDaoImpl extends BaseDaoImpl<VoteHistory, Long> implements VoteHistoryDao {
	Log log = LogFactory.getLog(this.getClass());

	@SuppressWarnings("unchecked")
	@Override
	public boolean validateVote(Long userId, Long voteId) {
		String hql="from VoteHistory vh where vh.user.id="+userId+" and vh.voteBase.id="+voteId;
		List<VoteHistory> list=this.getHibernateTemplate().find(hql);
		if(list!=null&&!list.isEmpty()){
			return true;
		}
		return false;
	}

	
	
}

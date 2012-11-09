package com.wondertek.meeting.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.wondertek.meeting.dao.JourneyMemberDao;
import com.wondertek.meeting.model.JourneyMember;

public class JourneyMemberDaoImpl extends BaseDaoImpl<JourneyMember, Long> implements JourneyMemberDao {
	
	@SuppressWarnings("unchecked")
	public List<JourneyMember> getJourneyMemberByJourney(Long journeyId){
		
		List<JourneyMember> lstJourneyMember=null;

		StringBuffer hql=new StringBuffer();
		hql.append("  FROM  JourneyMember WHERE 1=1 and journeyId="+journeyId);
		
		Session session = this.getSessionFactory().getCurrentSession();
		Query query=session.createQuery(hql.toString());
		lstJourneyMember=query.list();
		
		
		return lstJourneyMember;
	}
	
	
	/**
	 * 通过行程ID及用户ID来判断是否存在当前用户
	 * 
	 * @param journeyId
	 * @param userId
	 * @return
	 */
	public JourneyMember selectJourneyMember(Long journeyId, Long userId) {
		List<JourneyMember> lstJourneyMember = null;
		StringBuffer hql = new StringBuffer();
		hql.append("  FROM  JourneyMember WHERE 1=1 and journeyId=" + journeyId
				+ "and userId=" + userId);
		Session session = this.getSessionFactory().getCurrentSession();
		Query query = session.createQuery(hql.toString());
		lstJourneyMember = query.list();
		if (lstJourneyMember != null && lstJourneyMember.size() > 0) {
			return lstJourneyMember.get(0);
		}else{
			return null;
		}
	}
	
	/**
	 * 删除某一行程下的人员
	 * @param journeyId
	 */
	public void deleteJourneyMemberByJourneyId(Long journeyId){
		String hql = "delete from JourneyMember  where journeyId=?";
		this.getHibernateTemplate().bulkUpdate(hql, journeyId);
	}
	
}

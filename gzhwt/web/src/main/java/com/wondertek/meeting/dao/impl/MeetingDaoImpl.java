package com.wondertek.meeting.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wondertek.meeting.dao.MeetingDao;
import com.wondertek.meeting.dao.OrganizationDao;
import com.wondertek.meeting.exception.HibernateDaoSupportException;
import com.wondertek.meeting.model.Meeting;
import com.wondertek.meeting.model.User;

/**
 * 会议数据操作类
 * 
 * @author tangjun
 */
public class MeetingDaoImpl extends BaseDaoImpl<Meeting, Long> implements MeetingDao {
	private OrganizationDao organizationDao;
	
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	public List<User> queryUsers(Long meetingId,Long orgId){ 
		String sql = " select u.* from user u,meeting_member mm,meeting m "
				+ " where u.id=mm.user_id and mm.meeting_id=m.id "
				+ " and m.id = :meetingid ";
	
		@SuppressWarnings("unchecked")
		List<User> userList = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(sql)
				.addEntity("u", User.class).setLong("meetingid", meetingId).list();
		
		return userList;
	}
	

	public List getMyAttendMeetingList(String userId,String hql,Map<String, Object> properties){
		List lst=new ArrayList();
		//lst=this.getHibernateTemplate().find(hql);
		try {
			lst = getObjects(hql, properties);
		} catch (HibernateDaoSupportException e) {
			log.error("获取我的会议列表失败："+e.getMessage());
			e.printStackTrace();
		}
		
		return lst;
		
	}


	@Override
	public String getCityName(String meetingId) {


		String sql = " select a.area_name from area a, meeting m where a.area_code=m.city "
				+ " and m.id=:meetingId ";
		log.debug("sql========{}"+sql);
		SQLQuery query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);

		log.debug("222222222"+sql);
		query.setLong("meetingId", Long.valueOf(meetingId));

		log.debug("333333333"+sql);
		// List<Object> result_list = query.list();
		// for (Object result : result_list) {
		// //将查询出来的数据转换成对象
		// Object[] resultArray = (Object[]) result;
		// String downloadUrl = (String)resultArray[0];
		// String packageName = (String)resultArray[1];
		// if (downloadUrl != null && downloadUrl.length() > 0)
		// {
		// log.error("-----------------------downloadUrl=" + downloadUrl);
		// }
		// if (packageName != null && packageName.length() > 0)
		// {
		// log.error("-----------------------packageName=" + packageName);
		// }
		// }

		@SuppressWarnings("unchecked")
		String cityName = (String) query.uniqueResult();
		

		log.debug("444444444444"+cityName);
		return cityName;
	}
	
	/**
	 * 查询某组织下用户创建的所有会议id列表
	 * 
	 * @param orgId
	 * @return
	 */
	public List<Long> queryMeetingIdsByOrgId(Long orgId) {
		// 获取该组织下所有组织ID，包括自身
		List<Long> childOrgIdList = organizationDao.getChildOrgIdListWithSelf(orgId);
		
		String sql = "select m.id from Meeting m where m.creator.org.id in (:childOrgIdList)";

		@SuppressWarnings("unchecked")
		List<Long> list = this.getHibernateTemplate().findByNamedParam(sql, "childOrgIdList", childOrgIdList);

		return list;
	}

	/**
	 * 查询某用户创建的所有会议id列表
	 * 
	 * @param orgId
	 * @return
	 */
	public List<Long> queryMeetingIdsByUserId(Long userId) {
		String sql = "select m.id from Meeting m where m.creator.id = :userId";

		@SuppressWarnings("unchecked")
		List<Long> list = this.getHibernateTemplate().findByNamedParam(sql, "userId", userId);

		return list;
	}
	
	public Meeting findById(Long id) {
		String hql = "from Meeting h left outer join fetch h.meetingAdmins where h.id = ?";

		@SuppressWarnings("unchecked")
		List<Meeting> list = this.getHibernateTemplate().find(hql, id);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}

		return null;
	}

	public OrganizationDao getOrganizationDao() {
		return organizationDao;
	}

	public void setOrganizationDao(OrganizationDao organizationDao) {
		this.organizationDao = organizationDao;
	}
}

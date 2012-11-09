/**
 * 
 * Author: 张国敬
 * Copyrights: 版权归上海网达软件有限公司安徽分公司所有
 * Create at: 2012-4-6
 */
package com.wondertek.meeting.dao.impl.rfid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.dao.impl.BaseDaoImpl;
import com.wondertek.meeting.dao.rfid.RfidSignInDao;
import com.wondertek.meeting.exception.HibernateDaoSupportException;
import com.wondertek.meeting.model.RfidSignIn;
import com.wondertek.meeting.util.StringUtil;

public class RfidSignInDaoImpl extends BaseDaoImpl<RfidSignIn, Long> implements RfidSignInDao {
	/**
	 * 根据会议ID和用户ID查询对应Rfid签到记录
	 */
	@SuppressWarnings("unchecked")
	public List<RfidSignIn> querySignIn(Long meetingId, Long userId) {
		String sql = "from RfidSignIn b where b.meetingId = ? and b.user.id =? order by b.id";

		return this.getHibernateTemplate().find(sql, meetingId, userId);
	}
	
	
	
	/**
	 * 分页查看RFID签到信息
	 * card: 查询条件
	 */
	public Pager<RfidSignIn> findAllRfidSignInPager(RfidSignIn rfidSignIn, int currentPage, int pageSize) throws Exception {
		String hql = "from RfidSignIn c where c.meetingId=:meetingId";
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("meetingId", rfidSignIn.getMeetingId());
		
		if(rfidSignIn.getSignEvent() != null && StringUtil.isNotEmpty(rfidSignIn.getSignEvent().getEventName()) ) {
			hql += " and c.signEvent.eventName like :signEventName";
			param.put("signEventName", "%" + rfidSignIn.getSignEvent().getEventName() + "%");
		}
		
		if(rfidSignIn.getSignEvent() != null && rfidSignIn.getSignEvent().getId() != null ) {
			hql += " and c.signEvent.id = :signEventId";
			param.put("signEventId", rfidSignIn.getSignEvent().getId());
		}
		
		if(rfidSignIn.getTagId() != null) {
			hql += " and c.tagId like :tagId";
			param.put("tagId", "%" + rfidSignIn.getTagId() + "%");
		}
		
		if(rfidSignIn.getUser() != null && rfidSignIn.getUser().getId() != null) {
			hql += " and c.user.id= :userId";
			param.put("userId", rfidSignIn.getUser().getId());
		}
		
		if(rfidSignIn.getUser() != null && rfidSignIn.getUser().getMobile() != null) {
			hql += " and c.user.mobile like :mobile";
			param.put("mobile", "%" + rfidSignIn.getUser().getMobile() + "%");
		}
		
		if(rfidSignIn.getUser() != null && rfidSignIn.getUser().getName() != null) {
			hql += " and c.user.name like :name";
			param.put("name", "%" + rfidSignIn.getUser().getName() + "%");
		}
		
		if(rfidSignIn.getSignState() != null) {
			hql += " and c.signState = :signState";
			param.put("signState", rfidSignIn.getSignState());
		}
		
		hql += " order by c.id ";
		return this.findPager(hql, currentPage, pageSize, param);
	}

	
	/**
	 * 查询对应的RFID签到统计信息
	 */
	public Pager findRfidSignInForStaticPager(RfidSignIn rfidSignIn, int currentPage, int pageSize) throws Exception  {
//		Map<String, Object> param = new HashMap<String, Object>();
//		param.put("meetingId", rfidSignIn.getMeetingId());
//		String where = "";
		
//		if(rfidSignIn.getSignEvent()!= null && rfidSignIn.getSignEvent().getId() != null) {
//			where += " and b.event_id = :eventId ";
//			param.put("eventId", rfidSignIn.getSignEvent().getId());
//		}
//		
//		if(rfidSignIn.getUser() != null && StringUtil.isNotEmpty(rfidSignIn.getUser().getName())) {
//			where += " and a.name like :name ";
//			param.put("name", "%" + rfidSignIn.getUser().getName() + "%");
//		}
		
//		String sql = "select a.meeting_id, a.user_id, a.sign_date, a.sign_time, a.sign_type, a.sign_state, b.event_name " +
//				" from rfid_sign_in a join sign_event b on a.event_id=b.id " +
//				" where a.meeting_id=200 and b.id=26";
		
		//Log.info("sql=" + sql);
		if (currentPage <= 0) {
			currentPage = 1;
		}
		
//		int totalRecords = 0;
//		String countSql = "select count(*) from (" +
//				" select a.id from meeting_member e join user a on e.user_id = a.id left join rfid_sign_in b on a.id=b.user_id " +
//				" left join sign_event c on b.event_id = c.id " +
//				" where e.meeting_id=:meetingId " + where +
//				" group by a.id, b.id " +
//				" ) as d";
//		totalRecords = ((BigInteger) this.getUniqueBeanResultSql(countSql, param)).intValue();
//		Pager page = new Pager();
//		List list = null;
//		page.setTotal(totalRecords);
//		page.setPageSize(pageSize);
//		page.setCurrentPage(currentPage);
//		list = this.queryListSql(sql, (currentPage - 1) * pageSize, pageSize, param);
//		page.setPageRecords(list);
		
		String sql = "select b.meeting_id, b.user_id, a.name, a.mobile, 0 " +
				" from user a join meeting_member b on a.id=b.user_id" +
				" where b.meeting_id=:meetingId";
				
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("meetingId", rfidSignIn.getMeetingId());
		String where = "";
		String orderby = " order by a.name";
		if(rfidSignIn.getUser() != null && StringUtil.isNotEmpty(rfidSignIn.getUser().getName())) {
			where += " and a.name like :name ";
			param.put("name", "%" + rfidSignIn.getUser().getName() + "%");
		}
		
		Pager page = this.findPagerBySql(sql + where + orderby, currentPage, pageSize, param);
		
		sql = "select a.user_id, a.sign_state " +
				" from rfid_sign_in a join sign_event b on a.event_id=b.id join user c on a.user_id = c.id" +
				" where a.meeting_id=:meetingId and b.id=:eventId and a.sign_state <> 0 ";
		param = new HashMap<String, Object>();
		param.put("meetingId", rfidSignIn.getMeetingId());
		param.put("eventId", rfidSignIn.getSignEvent().getId());
		
		where = "";
		if(rfidSignIn.getUser() != null && StringUtil.isNotEmpty(rfidSignIn.getUser().getName())) {
			where += " and c.name like :name ";
			param.put("name", "%" + rfidSignIn.getUser().getName() + "%");
		}
		
		List<Object[]> list = this.queryListSql(sql + where, (currentPage - 1) * pageSize, pageSize, param);
		
		List<Object[]> list2 = page.getPageRecords();
		for(Object[] data : list2) {
			Long userId = Long.valueOf(data[1].toString());
			data[4] = this.checkSignState(userId, list);
		}
		
		return page;
	}
	
	/*
	 * 检查签到状态
	 * 状态：0-未签到，1-已签到，2-退到，3-早退
	 */
	private int checkSignState(Long userId, List<Object[]> list) {
		int states[] = new int[] {0, 0, 0, 0, 0}; //未签到，正常签到，正常签退，迟到，早退
		for(Object[] ary : list) {
			if(userId.equals(Long.valueOf(ary[0].toString()))) {
				int state = new Integer(ary[1].toString());
				if(state == 1) {
					states[1] = 1;
				} else if(state == 2) {
					states[2] = 1;
				} else if(state == 3) {
					states[3] = 1;
				} else if(state == 4) {
					states[4] = 1;
				}
			}
		}
		
		int state = 0;
		if(states[1] == 1 && states[2] == 1) { //正常签到和正常签退
			state = 1;
		} else if(states[3] == 1 || (states[1] == 0 && states[2] == 1)) {//退到
			state = 2;
		} else if(states[4] == 1 || (states[1] == 1 && states[2] == 0)) { //早退
			state = 3;
		}
		
		return state;
	}


	@Override
	public Pager<RfidSignIn> findByEventId(Long id,int currentPage,int pageSize) throws HibernateDaoSupportException {
		String hql = "from RfidSignIn c where c.signEvent.id=:id";
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("id",id);
		hql += " order by c.id desc";
		return this.findPager(hql, currentPage, pageSize, param);
	}
	
	/**
	 * 通过签到状态获取用户信息
	 * ids
	 * @return
	 */
	public List getUserBySignStatus(String meetingId, String eventId,
			String signType, String signState) {
		StringBuilder sb = new StringBuilder();
		sb.append(
				" SELECT u.id FROM USER u,meeting_member mm LEFT JOIN rfid_sign_in rs ON mm.user_id=rs.user_id ")
				.append("  WHERE u.id=mm.user_id ")
				.append("  AND mm.meeting_id=" + meetingId)
				.append("  AND rs.event_id=" + eventId);
		
		//签到类型
	    if(StringUtil.isNotEmpty(signType)){
	    	sb.append("  AND rs.sign_type=" +signType );
	    } 
		//迁到(退)状态
		if(StringUtil.isNotEmpty(signState)){
			sb.append("  and rs.sign_state=" + signState);
	    }
				
				
		
		log.debug("getUserBySignStatus:"+sb.toString());
		SQLQuery query = this.getSession().createSQLQuery(sb.toString());
		List result = query.list();
		//this.queryListSql(sql, startRecord, pageSize, properties);
		return result;
	}
	
	
	
	
}

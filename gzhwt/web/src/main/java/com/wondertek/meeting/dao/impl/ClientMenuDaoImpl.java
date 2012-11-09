package com.wondertek.meeting.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.wondertek.meeting.dao.ClientMenuDao;
import com.wondertek.meeting.model.ClientMenu;
import com.wondertek.meeting.model.MeetingClientMenu;
import com.wondertek.meeting.util.StringUtil;

/**
 * 签到
 * 
 * @author 金祝华
 */
public class ClientMenuDaoImpl extends BaseDaoImpl<ClientMenu, Long> implements ClientMenuDao {

	/**
	 * 根据会议查询客户端首页九宫格菜单列表
	 * 
	 * @param meetingId
	 * @return
	 */
	public List<ClientMenu> queryMenuList(Long meetingId, Integer memberLevel, String menuType) throws Throwable {
		// String sql =
		// "select c.download_url, c.package_name, a.* from client_menu a, meeting_client_menu b, base_menu c "
		// +
		// " where a.base_menu_id = c.id and a.state = 1 and a.id = b.menu_id and b.meeting_id=:meetingId "
		// + " and b.member_level = :memberLevel and a.terminal_type=:menuType "
		// + " order by (case when b.sort is null then 20 else b.sort end) asc";

		String sql = "select c.download_url, c.package_name, r.* from ( "
				+ "select a.* from client_menu a, meeting_client_menu b "
				+ "where a.state = 1 and a.id = b.menu_id and b.meeting_id=:meetingId "
				+ "and b.member_level = :memberLevel and a.terminal_type=:menuType "
				+ "order by (case when b.sort is null then 20 else b.sort end) asc) r left join base_menu c on r.base_menu_id = c.id";
		
		SQLQuery query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);

		query.setLong("meetingId", meetingId).setInteger("memberLevel", memberLevel).setString("menuType", menuType);

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
		List<ClientMenu> list = (List<ClientMenu>) query.addEntity(ClientMenu.class).list();

		// // 如果没有查询到列表，查询默认列表
		// if (list == null) {
		// list =
		// this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql)
		// .setLong("meetingId", 0L).list();
		// }

		return list;
	}

	@SuppressWarnings("unchecked")
	/**
	 * 通过terminalType来进行查询
	 */
	public List<ClientMenu> getMenuByTerminalType(String terminalType) {

		List<ClientMenu> clientMenuLst = new ArrayList<ClientMenu>();
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ClientMenu.class);
		detachedCriteria.add(Restrictions.eq("terminalType", terminalType));
		detachedCriteria.add(Restrictions.eq("state", 1));// 查处有效状态的菜单
		detachedCriteria.addOrder(Order.asc("type"));
		clientMenuLst = this.getHibernateTemplate().findByCriteria(detachedCriteria);
		return clientMenuLst;

	}

	/**
	 * 进行保存MeetingClientMenu
	 * 
	 * @param meetingMenu
	 */
	public void saveMeetingClientMenuList(List<MeetingClientMenu> meetingMenu) {
		this.getHibernateTemplate().saveOrUpdateAll(meetingMenu);
	}

	/**
	 * 删除关系
	 * 
	 * @param meetingId
	 * @param memberLevel
	 */
	public void deleteMeetingMenu(Long meetingId, Integer memberLevel) {

		List<MeetingClientMenu> meetingClientMenuList = new ArrayList<MeetingClientMenu>();
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(MeetingClientMenu.class);
		detachedCriteria.add(Restrictions.eq("meetingId", meetingId));
		detachedCriteria.add(Restrictions.eq("memberLevel", memberLevel));
		// criteria.add(Restrictions.not(Restrictions.in("id", callbackIds)));
		// Object[] obj=new Object[3];
		// obj[0]
		// detachedCriteria.add(Restrictions.not(Restrictions.in("menuId","1,22,31")));
		meetingClientMenuList = this.getHibernateTemplate().findByCriteria(detachedCriteria);
		// 删除
		this.getHibernateTemplate().deleteAll(meetingClientMenuList);
	}

	/**
	 * 通过MEETING_ID, memberLevel来进行查询
	 * 
	 * @param meetingId
	 * @param memberLevel
	 * @param terminalType
	 * @return
	 */
	public List<MeetingClientMenu> getMeetingMenu(Long meetingId, Integer memberLevel, String terminalType) {
		List<MeetingClientMenu> meetingClientMenuList = new ArrayList<MeetingClientMenu>();
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(MeetingClientMenu.class);
		detachedCriteria.add(Restrictions.eq("meetingId", meetingId));
		detachedCriteria.add(Restrictions.eq("memberLevel", memberLevel));
		detachedCriteria.add(Restrictions.eq("terminalType", terminalType));
		meetingClientMenuList = this.getHibernateTemplate().findByCriteria(detachedCriteria);

		return meetingClientMenuList;
	}

	/**
	 * 
	 * @param meetingId
	 * @param memberLevel
	 * @param terminalType
	 * @param clientMenuId
	 * @return
	 */
	public MeetingClientMenu getMeetingMenu(Long meetingId, Integer memberLevel, String terminalType, Long clientMenuId) {
		List<MeetingClientMenu> meetingClientMenuList = new ArrayList<MeetingClientMenu>();
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(MeetingClientMenu.class);
		detachedCriteria.add(Restrictions.eq("meetingId", meetingId));
		detachedCriteria.add(Restrictions.eq("memberLevel", memberLevel));
		detachedCriteria.add(Restrictions.eq("terminalType", terminalType));
		detachedCriteria.add(Restrictions.eq("menuId", clientMenuId));// 菜单唯一ID

		// 或者把NOT

		meetingClientMenuList = this.getHibernateTemplate().findByCriteria(detachedCriteria);

		if (meetingClientMenuList != null && meetingClientMenuList.size() > 0) {
			return meetingClientMenuList.get(0);// 获取第一个
		}
		// 否则返回一个空对象 防止空指针
		return null;
	}

	/**
	 * 获取系统的菜单 terminalType:可为空
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ClientMenu> getConfigMenuByTerminalType(String terminalType, String meetingId) {

		List<ClientMenu> clientMenuLst = new ArrayList<ClientMenu>();
		// detachedCriteria.add(Restrictions.eq("state", 1));// 查处有效状态的菜单
		// if (StringUtil.isNotEmpty(terminalType)) {
		// detachedCriteria.add(Restrictions.eq("terminalType",
		// terminalType));// 查处有效状态的菜单
		// }
		// detachedCriteria.add(Restrictions.neProperty("menuType",
		// Constants.CLIENT_MENU_TYPE.CUSTOME));// 非CUSTOME
		// detachedCriteria.add(Restrictions.or(lhs, rhs))
		Session session = this.getSessionFactory().getCurrentSession();
		StringBuffer queryStr = new StringBuffer();
		queryStr.append(" FROM ClientMenu menu where menu.state=1  ").append(" AND terminalType IS NOT NULL ");

		if (StringUtil.isNotEmpty(terminalType)) {
			queryStr.append(" AND terminalType=:terminalType ");
		}

		queryStr.append(" AND (menuType IS NOT NULL and meetingId is not null and meetingId=:meetingId ) ").append(
				" ORDER BY (case when defaultSortCode is null then 20 else defaultSortCode end)  asc ");

		log.debug("queryStr:=" + queryStr.toString());

		Query query = session.createQuery(queryStr.toString());
		if (StringUtil.isNotEmpty(terminalType)) {
			query.setString("terminalType", terminalType);
		}
		query.setString("meetingId", meetingId);

		clientMenuLst = query.list();

		return clientMenuLst;

	}

	/**
	 * 获取系统的菜单 terminalType:可为空
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ClientMenu> getMeetingMenu(String terminalType, Long meetingId) {

		List<ClientMenu> clientMenuLst = new ArrayList<ClientMenu>();

		Session session = this.getSessionFactory().getCurrentSession();
		StringBuffer queryStr = new StringBuffer();
		queryStr.append(" FROM ClientMenu menu where menu.state=1  ").append(" AND terminalType IS NOT NULL ");

		if (StringUtil.isNotEmpty(terminalType)) {
			queryStr.append(" AND terminalType=:terminalType ");
		}

		queryStr.append(" AND menuType IS NOT NULL  AND menuType='SYSTEM' and meetingId= " + meetingId).append(
				" ORDER BY terminalType,(case when defaultSortCode is null then 20 else defaultSortCode end)  asc ");

		log.debug("queryStr:=" + queryStr.toString());

		Query query = session.createQuery(queryStr.toString());
		if (StringUtil.isNotEmpty(terminalType)) {
			query.setString("terminalType", terminalType);
		}

		clientMenuLst = query.list();

		return clientMenuLst;

	}
	
	/**
	 * 通过基础菜单的ID及会议ID
	 * @param baseMenuId
	 * @param meetingId
	 * @return
	 */
	public ClientMenu findMeetingMenuByBaseMenuId(Long baseMenuId,Long meetingId){
		
		List<ClientMenu> meetingMenuList = new ArrayList<ClientMenu>();
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ClientMenu.class);
		detachedCriteria.add(Restrictions.eq("meetingId", meetingId));
		detachedCriteria.add(Restrictions.eq("baseMenuId", baseMenuId));

		// 或者把NOT

		meetingMenuList = this.getHibernateTemplate().findByCriteria(detachedCriteria);

		if (meetingMenuList != null && meetingMenuList.size() > 0) {
			return meetingMenuList.get(0);// 获取第一个
		}
		// 否则返回一个空对象 防止空指针
		return null;
		
	}
	
	
}

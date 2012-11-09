package com.wondertek.meeting.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.dao.BaseMenuDao;
import com.wondertek.meeting.model.BaseMenu;
import com.wondertek.meeting.util.StringUtil;

public class BaseMenuDaoImpl extends BaseDaoImpl<BaseMenu, Long> implements BaseMenuDao {
	
	/**
	 * 获取系统配置中默认的会议
	 * @return
	 */
	public List<BaseMenu> getBaseMenuLst(){
		//获取基础菜单的数据
		StringBuffer hql=new StringBuffer();
		hql.append("  FROM  BaseMenu WHERE 1=1 and state=1 ");
		hql.append(" ORDER BY terminalType,defaultSortCode ");
		
		List<BaseMenu> baseMenuList=new ArrayList();
		Session session = this.getSessionFactory().getCurrentSession();
		
		Query query=session.createQuery(hql.toString());
		baseMenuList=query.list();
		
		return baseMenuList;
	}

	/**
	 * 查找所有有效的基础菜单信息
	 * 有效的基础菜单是指：状态为1且会议类型不为null，如果会议类型为null则表示是系统预置的基础菜单
	 */
	public Pager<BaseMenu> findAllValidBaseMenuPages(Integer meetingTypeId,
			BaseMenu example, int currentPage, int pageSize) {
		Pager<BaseMenu> pager = null;
		StringBuffer hql = new StringBuffer();
		hql.append(" from BaseMenu m where m.state=1 ");
		
		hql.append(" and m.meetingType.id=" + meetingTypeId)	;
		
		if(example != null) {
			if (StringUtil.isNotEmpty(example.getDescription())) {
				hql.append(" and m.description like'%" + example.getDescription() + "%'");
			}

			if (StringUtil.isNotEmpty(example.getTerminalType())) {
				hql.append(" and m.terminalType='" + example.getTerminalType() + "'");
			}
			
			if (StringUtil.isNotEmpty(example.getName())) {
				hql.append(" and m.name like '%" + example.getName() + "%'");
			}
		}
		
		hql.append(" order by m.terminalType,m.defaultSortCode asc   ");
		try {
			pager = this.findPager(hql.toString(), currentPage, pageSize, null);
		} catch(Exception e) {
			e.printStackTrace();
		}

		return pager;
	}
	
	/**
	 * 查找所有预置的基础菜单
	 * meeting_type为NULL的菜单表示是系统预置的菜单
	 */
	public List<BaseMenu> findAllPresetBaseMenu() {
		String hql = "from BaseMenu m where m.state=1 and m.meetingType is null order by m.terminalType, m.defaultSortCode ";
		return this.getHibernateTemplate().find(hql);
	}
	
	/*
	 * 根据会议类型ID查找对应的基础菜单
	 */
	public List<BaseMenu> findBaseMenuByMeetingTypeId(Integer meetingTypeId) {
		String hql = "from BaseMenu m where m.state=1 and m.meetingType.id=?";
		return this.getHibernateTemplate().find(hql, meetingTypeId);
	}
	
	/*
	 * 删除指定会议类型对应的基础菜单
	 */
	public void deleteAllBaseMenuByMeetingTypeId(Integer meetingTypeId) {
		String hql = "delete from BaseMenu m where m.meetingType.id=?";
		this.getHibernateTemplate().bulkUpdate(hql, meetingTypeId);
	}
}

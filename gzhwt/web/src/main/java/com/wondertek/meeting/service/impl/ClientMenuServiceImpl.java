package com.wondertek.meeting.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.dao.ClientMenuDao;
import com.wondertek.meeting.exception.HibernateDaoSupportException;
import com.wondertek.meeting.model.ClientMenu;
import com.wondertek.meeting.model.MeetingClientMenu;
import com.wondertek.meeting.service.ClientMenuService;
import com.wondertek.meeting.util.StringUtil;

/**
 * 客户端首页九宫格菜单
 * 
 * @author 金祝华
 */
public class ClientMenuServiceImpl extends BaseServiceImpl<ClientMenu, Long>
		implements ClientMenuService {

	private ClientMenuDao clientMenuDao;

	/**
	 * 根据会议查询客户端首页九宫格菜单列表
	 * 
	 * @param meetingId
	 * @return
	 */
	public List<ClientMenu> queryMenuList(Long meetingId, Integer memberLevel,
			String menuType) {
		try {
			return clientMenuDao
					.queryMenuList(meetingId, memberLevel, menuType);
		} catch (Throwable e) {
			e.printStackTrace();
		}

		return null;
	}

	public ClientMenuDao getClientMenuDao() {
		return clientMenuDao;
	}

	public void setClientMenuDao(ClientMenuDao clientMenuDao) {
		this.basicDao = clientMenuDao;
		this.clientMenuDao = clientMenuDao;
	}

	/**
	 * 通过terminalType来进行查询
	 * 
	 * @param terminalType
	 * @return
	 */
	public List<ClientMenu> getMenuByTerminalType(String terminalType,String meetingId) {
		List<ClientMenu> clientMenuLst = clientMenuDao
				.getConfigMenuByTerminalType(terminalType,meetingId);
		// 针对这次请求的level以及meetingId来机型下一次查询
		// 说明以下：同一个会下 同一个级别用户 是唯一的
		
		return clientMenuLst;
	}
	
	public List<ClientMenu> getMenuByTerminalType(String terminalType,
			String meetingId, String memberLevel) {
		List<ClientMenu> clientMenuLst = clientMenuDao
				.getConfigMenuByTerminalType(terminalType,meetingId);
		// 针对这次请求的level以及meetingId来机型下一次查询
		// 说明以下：同一个会下 同一个级别用户 是唯一的
		if (clientMenuLst != null && clientMenuLst.size() > 0) {
			for (ClientMenu menu : clientMenuLst) {
				// 循环
				MeetingClientMenu clientMenu = clientMenuDao.getMeetingMenu(
						Long.valueOf(meetingId), Integer.valueOf(memberLevel),
						terminalType, menu.getId());
				if (clientMenu != null) {
					menu.setOrderCode(String.valueOf(clientMenu.getSort()));
				}
			}

		}

		return clientMenuLst;
	}
	public void saveMeetingClientMenuList(
			List<MeetingClientMenu> meetingMenuList) {
		clientMenuDao.saveMeetingClientMenuList(meetingMenuList);
	}

	/**
	 * 删除关系
	 * 
	 * @param meetingId
	 * @param memberLevel
	 */
	public void deleteMeetingMenu(Long meetingId, Integer memberLevel) {
		clientMenuDao.deleteMeetingMenu(meetingId, memberLevel);
	}

	/**
	 * 通过MEETING_ID, memberLevel来进行查询
	 * 
	 * @param meetingId
	 * @param memberLevel
	 * @param terminalType
	 * @return
	 */
	public List<MeetingClientMenu> getMeetingMenu(Long meetingId,
			Integer memberLevel, String terminalType) {
		return clientMenuDao.getMeetingMenu(meetingId, memberLevel,
				terminalType);
	}

	/**
	 * 初始化菜单
	 * 
	 * @param meetingId
	 * @throws HibernateDaoSupportException
	 */
	public void InitializeMenu(Long meetingId)
			throws HibernateDaoSupportException {
		List<MeetingClientMenu> meetingMenuLst = new ArrayList<MeetingClientMenu>();

		// 把所有的menu查出来
		List<ClientMenu> menuLst = clientMenuDao.getMeetingMenu(null,meetingId);
		
		if (menuLst != null && menuLst.size() > 0) {
			// 五级
			for (int i = 1; i <= 5; i++) {
				// 每个菜单
				Integer initialSortCode=null;
				String terminalTypeFlag="";
				for (ClientMenu menu : menuLst) {
					//签到过滤 不初始化菜单
					/**
					if("签到".equals(menu.getName())){
						continue;
					}
					
					if("管理员".equals(menu.getName())){
						continue;
					}
					*/
					
					//如果不需要初始化
					if(!menu.getIsInitial()){
						continue;
					}
					
					//如果上个值跟下个值不一样的 那么把菜单排序码重新置为1
					if(!terminalTypeFlag.equals(menu.getTerminalType())){
						initialSortCode=0;
					}
					MeetingClientMenu meetingClientMenu = new MeetingClientMenu();
					meetingClientMenu.setMeetingId(meetingId);
					meetingClientMenu.setMenuId(menu.getId());
					meetingClientMenu.setTerminalType(menu.getTerminalType());
					meetingClientMenu.setMemberLevel(i);
					meetingClientMenu.setSort(initialSortCode);
					meetingMenuLst.add(meetingClientMenu);
					
					//赋个值
					terminalTypeFlag=menu.getTerminalType();
					initialSortCode++;			
				}
			}
		}

		// 统一保存
		clientMenuDao.saveMeetingClientMenuList(meetingMenuLst);

	}
	
	
	
	/**
	 * 管理基础菜单的功能
	 * @param currentPage
	 * @param pageSize
	 * @return
	 * @throws HibernateDaoSupportException
	 */
	public Pager<ClientMenu> getBaseMenuPages(int currentPage, int pageSize,
			String description, String terminalType, String name,String meetingId)
			throws HibernateDaoSupportException {
		Pager<ClientMenu> pager = null;
		StringBuffer hql = new StringBuffer();
		hql.append(" from ClientMenu where 1=1 and  menuType is not null and menuType='SYSTEM'  ");
		
		//通过meetingId过滤
		hql.append(" AND meetingId="+meetingId)	;
		
		if (StringUtil.isNotEmpty(description)) {
			hql.append(" and description like'%" + description + "%'");
		}

		if (StringUtil.isNotEmpty(terminalType)) {
			hql.append(" and terminalType='" + terminalType + "'");
		}
		if (StringUtil.isNotEmpty(name)) {
			hql.append(" and name like '%" + name + "%'");
		}
		
		hql.append(" order by terminalType,defaultSortCode asc   ");
		pager = clientMenuDao.findPager(hql.toString(), currentPage, pageSize,
				null);

		return pager;

	}
	
	

}

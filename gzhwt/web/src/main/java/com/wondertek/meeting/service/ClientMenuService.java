package com.wondertek.meeting.service;

import java.util.List;

import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.exception.HibernateDaoSupportException;
import com.wondertek.meeting.model.ClientMenu;
import com.wondertek.meeting.model.MeetingClientMenu;

/**
 * 客户端首页九宫格菜单
 * 
 * @author 金祝华
 */
public interface ClientMenuService extends BaseService<ClientMenu, Long> {
	
	/**
	 * 根据会议查询客户端首页九宫格菜单列表
	 * 
	 * @param meetingId
	 * @return
	 */
	public List<ClientMenu> queryMenuList(Long meetingId, Integer memberLevel,String menuType);
	
	public List<ClientMenu> getMenuByTerminalType(String terminalType,String meetingId,String memberLevel);
	
	public List<ClientMenu> getMenuByTerminalType(String terminalType,String meetingId);
	
	public void saveMeetingClientMenuList(List<MeetingClientMenu> meetingMenuList);
	
	public void deleteMeetingMenu(Long meetingId,Integer memberLevel);
	
	/**
	 * 通过MEETING_ID, memberLevel来进行查询
	 * @param meetingId
	 * @param memberLevel
	 * @param terminalType
	 * @return
	 */
	public List<MeetingClientMenu> getMeetingMenu(Long meetingId,Integer memberLevel,String terminalType);
	
	/**
	 * 初始化菜单
	 * 
	 * @param meetingId
	 * @throws HibernateDaoSupportException
	 */
	public void InitializeMenu(Long meetingId)
			throws HibernateDaoSupportException ;
	
	/**
	 * 管理基础菜单的功能
	 * @param currentPage
	 * @param pageSize
	 * @return
	 * @throws HibernateDaoSupportException
	 */
	public Pager<ClientMenu> getBaseMenuPages(int currentPage,int pageSize,String description,String terminalType,String name,String meetingId) throws HibernateDaoSupportException;
	
	
}

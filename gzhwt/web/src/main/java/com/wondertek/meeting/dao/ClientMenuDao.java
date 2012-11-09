package com.wondertek.meeting.dao;

import java.util.List;

import com.wondertek.meeting.model.ClientMenu;
import com.wondertek.meeting.model.MeetingClientMenu;

/**
 * 客户端首页九宫格菜单
 * 
 * @author 金祝华
 */
public interface ClientMenuDao extends BaseDao<ClientMenu, Long> {

	/**
	 * 根据会议查询客户端首页九宫格菜单列表
	 * 
	 * @param meetingId
	 * @return
	 */
	public List<ClientMenu> queryMenuList(Long meetingId, Integer memberLevel,String menuType)throws Throwable;
	
	public List<ClientMenu> getMenuByTerminalType(String terminalType);
	
	public void saveMeetingClientMenuList(List<MeetingClientMenu> meetingMenu);
	
	/**
	 * 删除关系
	 * @param meetingId
	 * @param memberLevel
	 */
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
	 * 
	 * @param meetingId
	 * @param memberLevel
	 * @param terminalType
	 * @param clientMenuId
	 * @return
	 */
	public MeetingClientMenu getMeetingMenu(Long meetingId,Integer memberLevel,String terminalType,Long clientMenuId);
	
	/**
	 * 
	 * 获取需要配置的菜单项
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ClientMenu> getConfigMenuByTerminalType(String terminalType,
			String meetingId);
	
	public List<ClientMenu> getMeetingMenu(String terminalType,Long meetingId);
	
	/**
	 * 通过基础菜单的ID及会议ID
	 * @param baseMenuId
	 * @param meetingId
	 * @return
	 */
	public ClientMenu findMeetingMenuByBaseMenuId(Long baseMenuId,Long meetingId);
	
	
}

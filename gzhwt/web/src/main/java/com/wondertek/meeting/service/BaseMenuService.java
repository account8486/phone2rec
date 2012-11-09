package com.wondertek.meeting.service;

import java.util.List;

import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.model.BaseMenu;
import com.wondertek.meeting.model.Meeting;
public interface BaseMenuService extends BaseService<BaseMenu, Long> {
	
	/**
	 * 初始化meeting_menu字段
	 */
	public void initialMeetingMenu(Meeting meeting);
	
	/**
	 * 重置MEETING_MENU
	 * @param meetingId
	 */
	public void resetMeetingMenu(Meeting meeting);

	/**
	 * 查找所有有效的基础菜单信息
	 */
	public Pager<BaseMenu> findAllValidBaseMenuPages(Integer meetingTypeId, 
			BaseMenu example, int currentPage, int pageSize);
	
	/*
	 * 为指定的会议类型初始化基础菜单，如果该会议类型下面有基础菜单则跳过
	 * 初始化算法：把base_menu表中的所有字段meeting_type为null的记录复制一份到当前会议类型下面
	 */
	public void initMeetingTypeMenu(Integer meetingTypeId);
	
	/**
	 * 查找所有预置的基础菜单
	 * meeting_type为NULL的菜单表示是系统预置的菜单
	 */
	public List<BaseMenu> findAllPresetBaseMenu();
}

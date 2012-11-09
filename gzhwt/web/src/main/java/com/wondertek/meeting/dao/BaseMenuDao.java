package com.wondertek.meeting.dao;

import java.util.List;

import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.model.BaseMenu;

public interface BaseMenuDao extends BaseDao<BaseMenu, Long> {
	/**
	 * 获取系统配置中默认的会议
	 * @return
	 */
	public List<BaseMenu> getBaseMenuLst();
	
	/*
	 * 查找所有有效的基础菜单信息
	 * 有效的基础菜单是指：状态为1且会议类型不为null，如果会议类型为null则表示是系统预置的基础菜单
	 */
	public Pager<BaseMenu> findAllValidBaseMenuPages(Integer meetingTypeId,
			BaseMenu example, int currentPage, int pageSize);
	
	/*
	 * 查找所有预置的基础菜单
	 * meeting_type为NULL的菜单表示是系统预置的菜单
	 */
	public List<BaseMenu> findAllPresetBaseMenu();
	
	
	/*
	 * 根据会议类型ID查找对应的基础菜单
	 */
	public List<BaseMenu> findBaseMenuByMeetingTypeId(Integer meetingTypeId);
	
	/*
	 * 删除指定会议类型对应的基础菜单
	 */
	public void deleteAllBaseMenuByMeetingTypeId(Integer meetingTypeId);
}

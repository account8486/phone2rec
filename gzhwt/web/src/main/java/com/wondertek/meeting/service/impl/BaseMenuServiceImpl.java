package com.wondertek.meeting.service.impl;

import java.util.List;

import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.dao.BaseMenuDao;
import com.wondertek.meeting.dao.BaseModuleTitleDao;
import com.wondertek.meeting.dao.ClientMenuDao;
import com.wondertek.meeting.dao.MeetingModuleTitleDao;
import com.wondertek.meeting.dao.custom.MeetingTypeDao;
import com.wondertek.meeting.model.BaseMenu;
import com.wondertek.meeting.model.BaseModuleTitle;
import com.wondertek.meeting.model.ClientMenu;
import com.wondertek.meeting.model.Meeting;
import com.wondertek.meeting.model.MeetingModuleTitle;
import com.wondertek.meeting.model.custom.MeetingType;
import com.wondertek.meeting.service.BaseMenuService;

public class BaseMenuServiceImpl extends BaseServiceImpl<BaseMenu, Long> implements
		BaseMenuService {
	
	private BaseMenuDao baseMenuDao;
	private MeetingTypeDao meetingTypeDao;
	private ClientMenuDao clientMenuDao;
	private BaseModuleTitleDao baseModuleTitleDao;
	private MeetingModuleTitleDao meetingModuleTitleDao;
	
	public ClientMenuDao getClientMenuDao() {
		return clientMenuDao;
	}

	public void setClientMenuDao(ClientMenuDao clientMenuDao) {
		this.clientMenuDao = clientMenuDao;
	}

	public BaseMenuDao getBaseMenuDao() {
		return baseMenuDao;
	}

	public void setBaseMenuDao(BaseMenuDao baseMenuDao) {
		super.setBaseDao(baseMenuDao);
		this.baseMenuDao = baseMenuDao;
	}
	

	
	
	/**
	 * 初始化meeting_menu字段
	 */
	public void initialMeetingMenu(Meeting meeting){
		//modified by 张国敬 at 2012/2/1: 根据当前会议所属类型来初始化该类型对应的会议菜单
//		List<BaseMenu> baseMenuLst=	baseMenuDao.getBaseMenuLst();
		MeetingType meetingType = meeting.getMeetingType();
		if(meetingType == null) {
			return;
		}
		
		List<BaseMenu> baseMenuLst = this.baseMenuDao.findBaseMenuByMeetingTypeId(meetingType.getId());
		//循环来插入
		for(BaseMenu baseMenu:baseMenuLst){
			ClientMenu meetingMenu=new ClientMenu();
			meetingMenu.setMeetingId(meeting.getId());
			meetingMenu.setType(baseMenu.getType());
			meetingMenu.setName(baseMenu.getName());
			meetingMenu.setImgUrl(baseMenu.getImgUrl());
			meetingMenu.setContentUrl(baseMenu.getContentUrl());
			meetingMenu.setState(baseMenu.getState());
			meetingMenu.setTerminalType(baseMenu.getTerminalType());
			meetingMenu.setDescription(baseMenu.getDescription());
			meetingMenu.setMenuType("SYSTEM");
			meetingMenu.setDefaultSortCode(baseMenu.getDefaultSortCode());
			meetingMenu.setBaseMenuId(baseMenu.getId());//关联字段，保存
			meetingMenu.setIsInitial(baseMenu.getIsInitial());//是否需要初始化 如果为假 就不需要初始化
			
			clientMenuDao.saveOrUpdateEntity(meetingMenu);

			BaseModuleTitle bmt = new BaseModuleTitle();
			bmt.setMeetingTypeId(meetingType.getId());    //默认的云类型，用来作为新建云类型时初始化的蓝本，值固定为-1；
			bmt.setTerminalType(baseMenu.getTerminalType());
			bmt.setBaseMenuId(baseMenu.getId());
			List<BaseModuleTitle> baseModuleTitleList = baseModuleTitleDao.query(bmt);
			for(BaseModuleTitle result:baseModuleTitleList){
				MeetingModuleTitle mmt = new MeetingModuleTitle();
				mmt.setMeetingId(meeting.getId());
				mmt.setBaseModuleTitleId(result.getId());
				mmt.setBaseMenuId(result.getBaseMenuId());
				mmt.setName(result.getName());
				mmt.setTitleName(result.getTitleName());
				mmt.setKeyName(result.getKeyName());
				mmt.setTerminalType(baseMenu.getTerminalType());
				meetingModuleTitleDao.saveOrUpdateEntity(mmt);
			}
		}
		
	}
	
	
	/**
	 * 重置MEETING_MENU
	 * @param meetingId
	 */
	public void resetMeetingMenu(Meeting meeting){
		//modified by 张国敬 at 2012/2/1: 根据当前会议所属类型来初始化该类型对应的会议菜单
//		List<BaseMenu> baseMenuLst=	baseMenuDao.getBaseMenuLst();
		MeetingType meetingType = meeting.getMeetingType();
		if(meetingType == null) {
			return;
		}
		
		List<BaseMenu> baseMenuLst = this.baseMenuDao.findBaseMenuByMeetingTypeId(meetingType.getId());
		//循环来插入
		for(BaseMenu baseMenu:baseMenuLst){
			//先进行查询
			ClientMenu meetingMenu=clientMenuDao.findMeetingMenuByBaseMenuId(baseMenu.getId(), meeting.getId());
			//如果
			if(meetingMenu!=null){
				continue;
			}
			
			//如果不存在的话
		    meetingMenu=new ClientMenu();
			meetingMenu.setMeetingId(meeting.getId());
			meetingMenu.setType(baseMenu.getType());
			meetingMenu.setName(baseMenu.getName());
			meetingMenu.setImgUrl(baseMenu.getImgUrl());
			meetingMenu.setContentUrl(baseMenu.getContentUrl());
			meetingMenu.setState(baseMenu.getState());
			meetingMenu.setTerminalType(baseMenu.getTerminalType());
			meetingMenu.setDescription(baseMenu.getDescription());
			meetingMenu.setMenuType("SYSTEM");
			meetingMenu.setDefaultSortCode(baseMenu.getDefaultSortCode());
			meetingMenu.setBaseMenuId(baseMenu.getId());//关联字段，保存
			meetingMenu.setIsInitial(baseMenu.getIsInitial());//是否需要初始化 如果为假 就不需要初始化
			//在插入之前先判断这个会议下面是否有了这个菜单。
			clientMenuDao.saveOrUpdateEntity(meetingMenu);
		}
		
		
		
	}

	/**
	 * 查找所有有效的基础菜单信息
	 * 有效的基础菜单是指：状态为1且会议类型不为null，如果会议类型为null则表示是系统预置的基础菜单
	 */
	public Pager<BaseMenu> findAllValidBaseMenuPages(Integer meetingTypeId,
			BaseMenu example, int currentPage, int pageSize) {
		return this.baseMenuDao.findAllValidBaseMenuPages(meetingTypeId, example, currentPage, pageSize);
	}
	
	/**
	 * 查找所有预置的基础菜单
	 * meeting_type为NULL的菜单表示是系统预置的菜单
	 */
	public List<BaseMenu> findAllPresetBaseMenu() {
		return this.baseMenuDao.findAllPresetBaseMenu();
	}

	/*
	 * 为指定的会议类型初始化基础菜单
	 * 初始化算法：如果菜单不存在，则通过模板创建新菜单，如果存在菜单，则返回
	 */
	public void initMeetingTypeMenu(Integer meetingTypeId) {
		//先查找当前会议类型下的菜单
		List<BaseMenu> currMenuList = this.baseMenuDao.findBaseMenuByMeetingTypeId(meetingTypeId);
		if(currMenuList.size() > 0) {
			log.warn("当前会议类型下面已经存在基础菜单，不能再重新生成了。");
			return;
		}
		
		MeetingType meetingType = this.meetingTypeDao.findById(meetingTypeId);
		//循环复制
		List<BaseMenu> baseMenuList= baseMenuDao.findAllPresetBaseMenu();
		for(BaseMenu baseMenu : baseMenuList){
			BaseMenu menu = new BaseMenu();
			menu.setType(baseMenu.getType());
			menu.setName(baseMenu.getName());
			menu.setImgUrl(baseMenu.getImgUrl());
			menu.setContentUrl(baseMenu.getContentUrl());
			menu.setState(baseMenu.getState());
			menu.setTerminalType(baseMenu.getTerminalType());
			menu.setDescription(baseMenu.getDescription());
			menu.setDefaultSortCode(baseMenu.getDefaultSortCode());
			menu.setDownloadUrl(baseMenu.getDownloadUrl());
			menu.setPackageName(baseMenu.getPackageName());
			menu.setIsInitial(baseMenu.getIsInitial());
			menu.setMeetingType(meetingType);
			
			this.baseMenuDao.saveOrUpdateEntity(menu); //保存
			log.debug("菜单id为=================：{}",menu.getId());
			
			//根据生成的菜单id，初始化子页面标题定制的数据 add by chengfeng 2012-2-3
			BaseModuleTitle bmt = new BaseModuleTitle();
			bmt.setMeetingTypeId(-1);    //默认的云类型，用来作为新建云类型时初始化的蓝本，值固定为-1；
			bmt.setTerminalType(baseMenu.getTerminalType());
			bmt.setBaseMenuId(baseMenu.getId());
			List<BaseModuleTitle> baseModuleTitleList = baseModuleTitleDao.query(bmt);
			for(BaseModuleTitle result:baseModuleTitleList){
				BaseModuleTitle baseModuleTitle = new BaseModuleTitle();
				baseModuleTitle.setBaseMenuId(menu.getId());
				baseModuleTitle.setMeetingTypeId(meetingTypeId);
				baseModuleTitle.setName(result.getName());
				baseModuleTitle.setTitleName(result.getTitleName());
				baseModuleTitle.setKeyName(result.getKeyName());
				baseModuleTitle.setTerminalType(baseMenu.getTerminalType());
				baseModuleTitleDao.saveOrUpdateEntity(baseModuleTitle);
			}
		}
		
	}

	public MeetingTypeDao getMeetingTypeDao() {
		return meetingTypeDao;
	}

	public void setMeetingTypeDao(MeetingTypeDao meetingTypeDao) {
		this.meetingTypeDao = meetingTypeDao;
	}

	public BaseModuleTitleDao getBaseModuleTitleDao() {
		return baseModuleTitleDao;
	}

	public void setBaseModuleTitleDao(BaseModuleTitleDao baseModuleTitleDao) {
		this.baseModuleTitleDao = baseModuleTitleDao;
	}

	public MeetingModuleTitleDao getMeetingModuleTitleDao() {
		return meetingModuleTitleDao;
	}

	public void setMeetingModuleTitleDao(MeetingModuleTitleDao meetingModuleTitleDao) {
		this.meetingModuleTitleDao = meetingModuleTitleDao;
	}
}

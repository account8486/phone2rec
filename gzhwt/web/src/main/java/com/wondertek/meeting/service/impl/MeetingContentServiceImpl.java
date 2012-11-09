package com.wondertek.meeting.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.wondertek.meeting.action.menu.MenuConstants;
import com.wondertek.meeting.common.Constants;
import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.dao.ClientMenuDao;
import com.wondertek.meeting.dao.MeetingContentDao;
import com.wondertek.meeting.exception.HibernateDaoSupportException;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.ClientMenu;
import com.wondertek.meeting.model.MeetingContent;
import com.wondertek.meeting.service.MeetingContentService;

public class MeetingContentServiceImpl extends
		BaseServiceImpl<MeetingContent, Long> implements
		MeetingContentService {

	MeetingContentDao meetingContentDao;
	
	private ClientMenuDao clientMenuDao;

	public MeetingContentDao getMeetingContentDao() {
		return meetingContentDao;
	}

	public void setMeetingContentDao(MeetingContentDao meetingContentDao) {
		super.basicDao = meetingContentDao;
		this.meetingContentDao = meetingContentDao;
	}

	/**
	 * 通过HQL获取列表
	 */
	public List<MeetingContent> getObjects(String hql) {
		try {
			return meetingContentDao.getObjects(hql);
		} catch (HibernateDaoSupportException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 通过MeetingContent来进行查询
	 * @param meetingContent
	 * @return
	 */
	public List<MeetingContent> getMeetingContentList(
			MeetingContent meetingContent) {
		try {
			List<MeetingContent> meetingList = new ArrayList<MeetingContent>();
			StringBuffer hql = new StringBuffer();
			
			hql.append(" FROM MeetingContent meetingContent where meetingContent.meetingId="
					+ meetingContent.getMeetingId()
					+ " and  meetingContent.contentType="
					+ meetingContent.getContentType());
			meetingList = meetingContentDao.getObjects(hql.toString());
			
			return meetingList;
		} catch (HibernateDaoSupportException e) {
			e.printStackTrace();
			return null;
		}

	}

	public Pager<MeetingContent> findMeetingContentPager(Long meetingId,int type,
			int currentPage, int pageSize,String title) throws ServiceException {
		return meetingContentDao.findMeetingContentPager(meetingId,type,
				currentPage, pageSize,title);
	}

	/**处理自定义菜单的的数据*/
	public Long saveMeetingContent(MeetingContent content,String basePath,String itemIds)
			throws ServiceException {
		log.debug("saveMeetingContent {}",content);
		
		//保存content信息
		long mid = meetingContentDao.add(content);
		
		//更新条目菜单属于该list
		updateChildContent(content, itemIds);
		
		//是自定义菜单
		if(content.getContentType() != null && content.getContentType() == 3){
			//保存菜单信息
			if( content.isInWeb()){ //WEB的
				ClientMenu  menu = new ClientMenu();
				menu.setName(content.getContentTitle());
				menu.setDescription(content.getContentTitle());
				menu.setMeetingId(content.getMeetingId());
				menu.setMenuType(Constants.CLIENT_MENU_TYPE.CUSTOME);
				menu.setContentUrl("portal/pri/meeting/getCustomeMenu.action?returnType=portal&content.id="+mid+"&meetingId=");
				menu.setType(0);//自定义菜单
				menu.setImgUrl(content.getIcon());
				menu.setState(1);//默认启用
				menu.setContentId(mid);
				menu.setTerminalType(Constants.CLIENT_MENU_TERMINAL_TYPE.WEB);
				clientMenuDao.add(menu);
			}
			if( content.isInWap()){//WAP的
				ClientMenu  menu = new ClientMenu();
				menu.setName(content.getContentTitle());
				menu.setDescription(content.getContentTitle());
				menu.setMeetingId(content.getMeetingId());
				menu.setMenuType(Constants.CLIENT_MENU_TYPE.CUSTOME);
				menu.setContentUrl("wap/pri/meeting/getCustomeMenu.action?returnType=wap&content.id="+mid+"&meetingId=");
				menu.setType(0);//自定义菜单
				menu.setImgUrl(content.getIcon());
				menu.setState(1);//默认启用
				menu.setContentId(mid);
				menu.setTerminalType(Constants.CLIENT_MENU_TERMINAL_TYPE.WAP);
				clientMenuDao.add(menu);
			}
			if( content.isInClient()){//client
				ClientMenu  menu = new ClientMenu();
				menu.setName(content.getContentTitle());
				menu.setDescription(content.getContentTitle());
				menu.setMeetingId(content.getMeetingId());
				menu.setMenuType(Constants.CLIENT_MENU_TYPE.CUSTOME);
				menu.setContentUrl("client/pri/meeting/getCustomeMenu.action?returnType=client&content.id="+mid);
				menu.setType(0);//自定义菜单
				menu.setImgUrl(content.getIcon());
				menu.setState(1);//默认启用
				menu.setContentId(mid);
				menu.setTerminalType(Constants.CLIENT_MENU_TERMINAL_TYPE.CLIENT);
				clientMenuDao.add(menu);
			}
			
			if( content.isInTouch()){//client
				ClientMenu  menu = new ClientMenu();
				menu.setName(content.getContentTitle());
				menu.setDescription(content.getContentTitle());
				menu.setMeetingId(content.getMeetingId());
				menu.setMenuType(Constants.CLIENT_MENU_TYPE.CUSTOME);//非系统菜单
				menu.setContentUrl("touch/pri/meeting/getCustomeMenu.action?returnType=touch&content.id="+mid+"&meetingId=");
				menu.setType(0);//自定义菜单
				menu.setImgUrl(content.getIcon());
				menu.setState(1);//默认启用
				menu.setContentId(mid);
				menu.setTerminalType(MenuConstants.TERMINAL_MENU_TOUCH);
				clientMenuDao.add(menu);
			}
		}
		return mid;
	}
	
	/**更新自定义菜单的信息*/
	public Long modifyMeetingContent(MeetingContent content, String basePath,String itemIds)
			throws ServiceException {
		log.debug("modifyMeetingContent {}",content);
		
		//更新content
		meetingContentDao.modify(content);
		
		//保存content信息
		//更新条目菜单属于该list
		updateChildContent(content, itemIds);
		
		//是自定义菜单
		if(content.getContentType() != null && content.getContentType()== 3){
			if(content.isInWeb()){ //WEB型的菜单
				String hql = "from ClientMenu where terminalType = '"+Constants.CLIENT_MENU_TERMINAL_TYPE.WEB+"' and contentId = "+content.getId();
				List<ClientMenu> menuList = clientMenuDao.getObjects(hql);
				if(menuList.size() > 0){
					//update
					//更新已有的
					ClientMenu m = menuList.get(0);
					m.setImgUrl(content.getIcon());
					m.setName(content.getContentTitle());
					m.setDescription(content.getContentTitle());
					clientMenuDao.modify(m);
				}else{
					//add
					//加上没有的
					ClientMenu  menu = new ClientMenu();
					menu.setName(content.getContentTitle());
					menu.setDescription(content.getContentTitle());
					menu.setMeetingId(content.getMeetingId());
					menu.setMenuType(Constants.CLIENT_MENU_TYPE.CUSTOME);
					menu.setContentUrl("portal/pri/meeting/getCustomeMenu.action?returnType=portal&content.id="+content.getId()+"&meetingId=");
					menu.setType(0);//自定义菜单
					menu.setImgUrl(content.getIcon());
					menu.setState(1);//默认启用
					menu.setContentId(content.getId());
					menu.setTerminalType(Constants.CLIENT_MENU_TERMINAL_TYPE.WEB);
					clientMenuDao.add(menu);
				}
			}else{
				//不再属于web delete 
				//删除去掉的
				deleteClientMenuByContentIdAndMenuType(content.getId(), content.getMeetingId(),Constants.CLIENT_MENU_TERMINAL_TYPE.WEB);	
			}
			
			if(content.isInWap()){//WAP型的菜单
				String hql = "from ClientMenu where terminalType = '"+Constants.CLIENT_MENU_TERMINAL_TYPE.WAP+"' and contentId = "+content.getId();
				List<ClientMenu> menuList = clientMenuDao.getObjects(hql);
				if(menuList.size() > 0){
					//update
					//更新已有的
					ClientMenu m = menuList.get(0);
					m.setImgUrl(content.getIcon());
					m.setName(content.getContentTitle());
					m.setDescription(content.getContentTitle());
					clientMenuDao.modify(m);
				}else{
					//add
					//加上没有的
					ClientMenu  menu = new ClientMenu();
					menu.setName(content.getContentTitle());
					menu.setDescription(content.getContentTitle());
					menu.setMeetingId(content.getMeetingId());
					menu.setMenuType(Constants.CLIENT_MENU_TYPE.CUSTOME);
					menu.setContentUrl("wap/pri/meeting/getCustomeMenu.action?returnType=wap&content.id="+content.getId()+"&meetingId=");
					menu.setType(0);//自定义菜单
					menu.setImgUrl(content.getIcon());
					menu.setState(1);//默认启用
					menu.setContentId(content.getId());
					menu.setTerminalType(Constants.CLIENT_MENU_TERMINAL_TYPE.WAP);
					clientMenuDao.add(menu);
				}
			}else{
				//不再属于wap delete 
				//删除去掉的
				deleteClientMenuByContentIdAndMenuType(content.getId(), content.getMeetingId(),Constants.CLIENT_MENU_TERMINAL_TYPE.WAP);	
			}
			if(content.isInClient()){//CLIENT型的菜单
				String hql = "from ClientMenu where terminalType = '"+Constants.CLIENT_MENU_TERMINAL_TYPE.CLIENT+"' and contentId = "+content.getId();
				List<ClientMenu> menuList = clientMenuDao.getObjects(hql);
				if(menuList.size() > 0){
					//update
					//更新已有的
					ClientMenu m = menuList.get(0);
					m.setImgUrl(content.getIcon());
					m.setName(content.getContentTitle());
					m.setDescription(content.getContentTitle());
					clientMenuDao.modify(m);
				}else{
					//add
					//加上没有的
					ClientMenu  menu = new ClientMenu();
					menu.setName(content.getContentTitle());
					menu.setDescription(content.getContentTitle());
					menu.setMeetingId(content.getMeetingId());
					menu.setMenuType(Constants.CLIENT_MENU_TYPE.CUSTOME);
//					menu.setContentUrl(basePath+"/client/pri/meeting/getCustomeMenu.action?returnType=client&content.id="+content.getId());
					menu.setContentUrl("client/pri/meeting/getCustomeMenu.action?returnType=client&content.id="+content.getId());
					menu.setType(0);//自定义菜单
					menu.setImgUrl(content.getIcon());
					menu.setState(1);//默认启用
					menu.setContentId(content.getId());
					menu.setTerminalType(Constants.CLIENT_MENU_TERMINAL_TYPE.CLIENT);
					clientMenuDao.add(menu);
				}
			}else{
				//不再属于client delete 
				//删除去掉的
				deleteClientMenuByContentIdAndMenuType(content.getId(), content.getMeetingId(),Constants.CLIENT_MENU_TERMINAL_TYPE.CLIENT);	
			}
			
			
			
			if(content.isInTouch()){//CLIENT型的菜单
				String hql = "from ClientMenu where terminalType = '"+MenuConstants.TERMINAL_MENU_TOUCH+"' and contentId = "+content.getId();
				List<ClientMenu> menuList = clientMenuDao.getObjects(hql);
				if(menuList.size() > 0){
					//update
					//更新已有的
					ClientMenu m = menuList.get(0);
					m.setImgUrl(content.getIcon());
					m.setName(content.getContentTitle());
					m.setDescription(content.getContentTitle());
					clientMenuDao.modify(m);
				}else{
					//add
					//加上没有的
					ClientMenu  menu = new ClientMenu();
					menu.setName(content.getContentTitle());
					menu.setDescription(content.getContentTitle());
					menu.setMeetingId(content.getMeetingId());
					menu.setMenuType(Constants.CLIENT_MENU_TYPE.CUSTOME);
					menu.setContentUrl("touch/pri/meeting/getCustomeMenu.action?returnType=touch&content.id="+content.getId()+"&meetingId=");
					menu.setType(0);//自定义菜单
					menu.setImgUrl(content.getIcon());
					menu.setState(1);//默认启用
					menu.setContentId(content.getId());
					menu.setTerminalType(MenuConstants.TERMINAL_MENU_TOUCH);
					clientMenuDao.add(menu);
				}
			}else{
				//不再属于touch delete 
				//删除去掉的
				deleteClientMenuByContentIdAndMenuType(content.getId(), content.getMeetingId(),MenuConstants.TERMINAL_MENU_TOUCH);	
			}
			
			
		}
		
		return content.getId();
	}

	private void updateChildContent(MeetingContent content, String itemIds)
			throws HibernateDaoSupportException {
		if("1".equals(content.getIsList())){
			if(null != itemIds && itemIds.length() > 0){
				String hql = "update MeetingContent set parentId = :parentId where id in ("+itemIds+")";
				HashMap<String,Object> params= new HashMap<String,Object>();
				params.put("parentId", content.getId());
				meetingContentDao.executeUpdate(hql, params);
			}
		}
	}
	
	
	/**根据content信息和菜单类型删除clientMenu 和 meeting_client_menu*/
	private void deleteClientMenuByContentIdAndMenuType(Long contentId,Long meetingId,String menuType) throws HibernateDaoSupportException {
		//删除菜单相关连的信息
		String sql = "delete from meeting_client_menu where meeting_id = "
			+ meetingId
			+ " and menu_id in (select id from client_menu where content_id = "
			+ contentId + " and terminal_type = '"+menuType+"')";
		log.info("deleteContent 删除meeting_client_menu:\n"+sql);
		clientMenuDao.executeSql(sql, new HashMap());	
		sql = "delete from client_menu where meeting_id = "
			+ meetingId
			+ " and  content_id = "
			+ contentId + "  and terminal_type = '"+menuType+"'";
		log.info("deleteContent 删除client_menu:\n"+sql);
		clientMenuDao.executeSql(sql, new HashMap());
	}
	
	
	
	/**删除自定义菜单相关的信息*/
	public void deleteContent(MeetingContent content) throws ServiceException {
		
		MeetingContent old = meetingContentDao.findById(content.getId(), MeetingContent.class);
		if(old == null){
			log.warn("deleteContent not find record.");
			return ;
		}
		//是自定义菜单
		if(old.getContentType() != null && old.getContentType()== 3){
			deleteClientMenuByContentId(content.getId(), old.getMeetingId());
			deleteChildContent(old);
		}
		meetingContentDao.delete(old);
		log.info("deleteContent id = {}",content.getId());
	}

	/**根据content信息删除clientMenu 和 meeting_client_menu*/
	private void deleteClientMenuByContentId(Long contentId,Long meetingId
	) throws HibernateDaoSupportException {
		//删除菜单相关连的信息
		String sql = "delete from meeting_client_menu where meeting_id = "
			+ meetingId
			+ " and menu_id in (select id from client_menu where content_id = "
			+ contentId + " )";
		log.info("deleteContent 删除meeting_client_menu:\n"+sql);
		clientMenuDao.executeSql(sql, new HashMap());	
		sql = "delete from client_menu where meeting_id = "
			+ meetingId
			+ " and  content_id = "
			+ contentId + " ";
		log.info("deleteContent 删除client_menu:\n"+sql);
		clientMenuDao.executeSql(sql, new HashMap());
	}
	
	/**根据content信息删除条目内容*/
	private void deleteChildContent(MeetingContent content) throws HibernateDaoSupportException {
		//删除菜单相关连的信息
		String sql = "delete from MeetingContent where parentId = "+content.getId();
		log.info("deleteContent 删除childContent:\n"+sql);
		clientMenuDao.executeUpdate(sql, new HashMap());	
	}
	
	
	public List<MeetingContent> findContentItem(Long parentId) throws ServiceException{
		String hql = "from MeetingContent where parentId = :parentId";
		HashMap<String,Object> params = new HashMap<String,Object>();
		params.put("parentId", parentId);
		return meetingContentDao.getObjects(hql, params);
	}
	
	public List<MeetingContent> findContentItem(String itemIds) throws ServiceException{
		String hql = "from MeetingContent where id in ("+itemIds+") order by sortNo";
		return meetingContentDao.getObjects(hql, new HashMap());
	}
	

	public ClientMenuDao getClientMenuDao() {
		return clientMenuDao;
	}

	public void setClientMenuDao(ClientMenuDao clientMenuDao) {
		this.clientMenuDao = clientMenuDao;
	}

}

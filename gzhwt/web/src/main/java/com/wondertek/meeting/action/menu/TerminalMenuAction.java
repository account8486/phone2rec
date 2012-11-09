package com.wondertek.meeting.action.menu;

import java.util.ArrayList;
import java.util.List;

import com.wondertek.meeting.action.base.BaseAction;
import com.wondertek.meeting.exception.HibernateDaoSupportException;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.AdminUser;
import com.wondertek.meeting.model.ClientMenu;
import com.wondertek.meeting.model.MeetingClientMenu;
import com.wondertek.meeting.service.AdminUserService;
import com.wondertek.meeting.service.ClientMenuService;
import com.wondertek.meeting.service.SecurityUnitService;
import com.wondertek.meeting.vo.SecurityUnitView;

/**
 * 用于WEB WAP CLIENT三个终端的动态菜单功能
 * 
 * @author GuoXu
 * 
 */
public class TerminalMenuAction extends BaseAction {

	private static final long serialVersionUID = -3708141585563350908L;
	ClientMenuService clientMenuService;
	private String meetingId;// meetingId
	private String memberLevel;
	// 说明是来自哪里
	private String msgFromSave;
	SecurityUnitService securityUnitService;
	AdminUserService adminUserService;

	public AdminUserService getAdminUserService() {
		return adminUserService;
	}

	public void setAdminUserService(AdminUserService adminUserService) {
		this.adminUserService = adminUserService;
	}

	public SecurityUnitService getSecurityUnitService() {
		return securityUnitService;
	}

	public void setSecurityUnitService(SecurityUnitService securityUnitService) {
		this.securityUnitService = securityUnitService;
	}

	/**
	 * 获取基础表的数据,展示到前台页面 进行选择
	 * 
	 * @return
	 */
	public String getBaseMenu() {
		String meetingId = getRequest().getParameter("meetingId");// 获取MEETINGid
																	// 带入到下一页面
		String memberLevel = getRequest().getParameter("memberLevel");

		String msgFromSave = getRequest().getParameter("msgFromSave");

		// 获取当前已经选中的菜单 传回到前台页面 供展示

		// 通过查询CLIENT_MENU表来进行数据的查询
		List<ClientMenu> clientMenuList = clientMenuService
				.getMenuByTerminalType(MenuConstants.TERMINAL_MENU_CLIENT,
						meetingId, memberLevel);

		List<ClientMenu> webMenuList = clientMenuService.getMenuByTerminalType(
				MenuConstants.TERMINAL_MENU_WEB, meetingId, memberLevel);

		List<ClientMenu> wapMenuList = clientMenuService.getMenuByTerminalType(
				MenuConstants.TERMINAL_MENU_WAP, meetingId, memberLevel);
		
		List<ClientMenu> touchMenuList = clientMenuService.getMenuByTerminalType(
				MenuConstants.TERMINAL_MENU_TOUCH, meetingId, memberLevel);
		

		// 把已经选中的单独列出来
		String choosedClientMenuStr = this.converList2Str(meetingId,
				memberLevel, MenuConstants.TERMINAL_MENU_CLIENT);
		String choosedWebMenuStr = this.converList2Str(meetingId, memberLevel,
				MenuConstants.TERMINAL_MENU_WEB);
		String choosedWapMenuStr = this.converList2Str(meetingId, memberLevel,
				MenuConstants.TERMINAL_MENU_WAP);
		//添加touch方法
		String choosedTouchMenuStr=this.converList2Str(meetingId, memberLevel,
				MenuConstants.TERMINAL_MENU_TOUCH);

		getRequest().setAttribute("choosedClientMenuStr", choosedClientMenuStr);
		getRequest().setAttribute("choosedWebMenuStr", choosedWebMenuStr);
		getRequest().setAttribute("choosedWapMenuStr", choosedWapMenuStr);
		getRequest().setAttribute("choosedTouchMenuStr", choosedTouchMenuStr);

		getRequest().setAttribute("meetingId", meetingId);
		// 客户端菜单
		getRequest().setAttribute("clientMenuList", clientMenuList);
		// WEB
		getRequest().setAttribute("webMenuList", webMenuList);
		// WAP
		getRequest().setAttribute("wapMenuList", wapMenuList);
		getRequest().setAttribute("touchMenuList", touchMenuList);

		// 把当前页面参数带回去
		getRequest().setAttribute("meetingId", meetingId);
		getRequest().setAttribute("memberLevel", memberLevel);

		// 如果是来源于保存后查询 则显示提示语句
		if (msgFromSave != null && !"".equals(msgFromSave)) {
			getRequest().setAttribute("msgFromSave", msgFromSave);
		}

		return SUCCESS;

	}

	/**
	 * 为制定会议保存菜单
	 * 
	 * @return
	 */
	public String saveMenuForMeeting() {

		String meetingId = getRequest().getParameter("meetingId");
		String memberLevel = getRequest().getParameter("memberLevel");

		// 原始数据
		String[] clientRequestObjArrValue = getRequest().getParameterValues(
				"clientRequest");
		String[] webRequestObjArrValue = getRequest().getParameterValues(
				"webRequest");
		String[] wapRequestObjArrValue = getRequest().getParameterValues(
				"wapRequest");
		String[] touchRequestObjArrValue = getRequest().getParameterValues(
		"touchRequest");

		// 已选择的用户的ID
		String[] clientMenuCkArr = new String[getArrayCanUsedSize(clientRequestObjArrValue)];
		String[] webMenuCkArr = new String[getArrayCanUsedSize(webRequestObjArrValue)];
		String[] wapMenuCkArr = new String[getArrayCanUsedSize(wapRequestObjArrValue)];
		String[] touchMenuCkArr = new String[getArrayCanUsedSize(touchRequestObjArrValue)];
		
		// 获取对应的排序码
		String[] clientSorCodeArr = new String[getArrayCanUsedSize(clientRequestObjArrValue)];
		String[] webSorCodeArr = new String[getArrayCanUsedSize(webRequestObjArrValue)];
		String[] wapSorCodeArr = new String[getArrayCanUsedSize(wapRequestObjArrValue)];
		String[] touchSorCodeArr = new String[getArrayCanUsedSize(touchRequestObjArrValue)];
		// 打印大小
		log.debug("clientMenuCkArr:" + clientMenuCkArr.length);
		log.debug("webMenuCkArr:" + webMenuCkArr.length);
		log.debug("wapMenuCkArr:" + wapMenuCkArr.length);
		log.debug("touchMenuCkArr:" + touchMenuCkArr.length);

		
		//进行转化
		this.changeArrays(clientRequestObjArrValue, clientMenuCkArr,
				clientSorCodeArr);
		this.changeArrays(webRequestObjArrValue, webMenuCkArr,
				webSorCodeArr);
		this.changeArrays(wapRequestObjArrValue, wapMenuCkArr, wapSorCodeArr);
		this.changeArrays(touchRequestObjArrValue, touchMenuCkArr, touchSorCodeArr);
		
		// 先都删除掉 省的哥哥一个一个去比对状态了
		clientMenuService.deleteMeetingMenu(Long.valueOf(meetingId),
				Integer.valueOf(memberLevel));

		// 获取列表
		if (clientMenuCkArr != null && clientMenuCkArr.length > 0) {
			List<MeetingClientMenu> clientMeetingMenuLst = this
					.getMeetingClentMenu(clientMenuCkArr, clientSorCodeArr,
							meetingId, memberLevel,
							MenuConstants.TERMINAL_MENU_CLIENT);

			clientMenuService.saveMeetingClientMenuList(clientMeetingMenuLst);
		}

		if (webMenuCkArr != null && webMenuCkArr.length > 0) {
			List<MeetingClientMenu> webMeetingMenuLst = this
					.getMeetingClentMenu(webMenuCkArr, webSorCodeArr,
							meetingId, memberLevel,
							MenuConstants.TERMINAL_MENU_WEB);
			clientMenuService.saveMeetingClientMenuList(webMeetingMenuLst);
		}

		if (wapMenuCkArr != null && wapMenuCkArr.length > 0) {
			List<MeetingClientMenu> wapMeetingMenuLst = this
					.getMeetingClentMenu(wapMenuCkArr, wapSorCodeArr,
							meetingId, memberLevel,
							MenuConstants.TERMINAL_MENU_WAP);
			clientMenuService.saveMeetingClientMenuList(wapMeetingMenuLst);
		}
		
		if (touchMenuCkArr != null && touchMenuCkArr.length > 0) {
			List<MeetingClientMenu> touchMeetingMenuLst = this
					.getMeetingClentMenu(touchMenuCkArr, touchSorCodeArr,
							meetingId, memberLevel,
							MenuConstants.TERMINAL_MENU_TOUCH);
			clientMenuService.saveMeetingClientMenuList(touchMeetingMenuLst);
		}

		this.setMeetingId(meetingId);
		this.setMemberLevel(memberLevel);
		this.setMsgFromSave("菜单保存成功!");

		getRequest().setAttribute("meetingId", meetingId);
		getRequest().setAttribute("memberLevel", memberLevel);

		// 进行批量保存
		return SUCCESS;
	}

	/**
	 * 把原始数据数组转化为我们需要的数组参数
	 * 
	 * @param orignal
	 * @param menus
	 * @param sortCodes
	 */
	public void changeArrays(String[] orignal, String[] menus,
			String[] sortCodes) {
		// 进行参数处理
		if (orignal != null && orignal.length > 0) {
			// 循环下
			int actSize=0;
			for (int i = 0; i < orignal.length&&actSize<menus.length; i++) {
				// 获取有效个数
				String requestObj = orignal[i];
				String[] temp = requestObj.split(",");
				//选中如果没有排序码
				if(temp.length==1){
					menus[actSize] = temp[0];
					sortCodes[actSize]="";
					actSize++;
					continue;
				}
				//第二位不能为null
				if (temp.length > 1&&!"unchecked".equals(temp[1])) {
					// clientMenuCkArrValue[clientInvalidNum]=temp;
					menus[actSize] = temp[0];
					sortCodes[actSize] = temp[1];
					actSize++;
				}
			}
		}
	}

	/**
	 * 获取我需要的数组的大小
	 * 
	 * @param requestObjArrValue
	 * @return
	 */
	public int getArrayCanUsedSize(String[] requestObjArrValue) {
		int validNum = 0;
		// 处理客户端
		if (requestObjArrValue != null && requestObjArrValue.length > 0) {
			int k = 0;
			for (int i = 0; i < requestObjArrValue.length; i++) {
				String str = requestObjArrValue[i];
				String[] tmp = str.split(",");
				//此处的数组为2并且都不能第二个值为null字符串
				//如果当前数组选中 无排序码tmp.length=1
				//选中并且有排序码并且
				if (tmp.length==1||(tmp.length > 1&&!"unchecked".equals(tmp[1]))) {
					validNum++;
				}
				
				k++;
			}
		}

		return validNum;
	}

	/**
	 * 把数组转化为实体LIST
	 * 
	 * @param menuCk
	 * @param meetingId
	 * @param memberLevel
	 * @return
	 */
	private List<MeetingClientMenu> getMeetingClentMenu(String[] menuCkArr,
			String[] sorCodeArr, String meetingId, String memberLevel,
			String terminalType) {
		// 客户端
		List<MeetingClientMenu> meetingMenuLst = new ArrayList<MeetingClientMenu>();
		if (menuCkArr != null && menuCkArr.length > 0) {
			for (int i = 0; i < menuCkArr.length; i++) {
				MeetingClientMenu meetingClientMenu = new MeetingClientMenu();
				meetingClientMenu.setMeetingId(Long.valueOf(meetingId));
				meetingClientMenu.setMemberLevel(Integer.valueOf(memberLevel));
				meetingClientMenu.setMenuId(Long.valueOf(menuCkArr[i]));
				// 设置排序码
				meetingClientMenu.setSort(sorCodeArr[i] == null
						|| "".equals(sorCodeArr[i]) ? null : Integer
						.valueOf(sorCodeArr[i]));

				meetingClientMenu.setTerminalType(terminalType);// 冗余出来 方便后续查询
				meetingMenuLst.add(meetingClientMenu);
			}
		}

		return meetingMenuLst;
	}
	
	/**
	 * 直接跳转到页面
	 * @return
	 * @throws ServiceException 
	 */
	public String toManagerMeetingMenu() throws ServiceException {
		String meetingId = this.getParameter("meetingId");
		this.getRequest().setAttribute("meetingId", meetingId);
		String ctx=this.getRequest().getContextPath();
		// TODO
		AdminUser adminUser =adminUserService.findById(this.getAdminUserIdFromSession());
		List<SecurityUnitView> lstSecurityUnitView = securityUnitService
				.getMenuUnitsByRoleId(adminUser.getSecurityRoleId(), meetingId,ctx);
		getRequest().setAttribute("unitViewList", lstSecurityUnitView);
		return SUCCESS;
	}
	
	
	/**
	 * 转化
	 * 
	 * @param meetingId
	 * @param memberLevel
	 * @param terminalType
	 * @return
	 */
	public String converList2Str(String meetingId, String memberLevel,
			String terminalType) {
		String rlt = "";
		List<MeetingClientMenu> choosedMenu = clientMenuService.getMeetingMenu(
				Long.valueOf(meetingId), Integer.valueOf(memberLevel),
				terminalType);

		if (choosedMenu != null && choosedMenu.size() > 0) {
			for (MeetingClientMenu menu : choosedMenu) {
				rlt += menu.getMenuId() + ",";
			}
		}
		return rlt;
	}

	public ClientMenuService getClientMenuService() {
		return clientMenuService;
	}

	public void setClientMenuService(ClientMenuService clientMenuService) {
		this.clientMenuService = clientMenuService;
	}

	public String getMeetingId() {
		return meetingId;
	}

	public void setMeetingId(String meetingId) {
		this.meetingId = meetingId;
	}

	public String getMemberLevel() {
		return memberLevel;
	}

	public void setMemberLevel(String memberLevel) {
		this.memberLevel = memberLevel;
	}

	public String getMsgFromSave() {
		return msgFromSave;
	}

	public void setMsgFromSave(String msgFromSave) {
		this.msgFromSave = msgFromSave;
	}

}

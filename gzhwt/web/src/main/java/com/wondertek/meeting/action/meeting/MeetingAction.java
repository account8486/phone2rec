package com.wondertek.meeting.action.meeting;

import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;

import com.wondertek.meeting.Consts;
import com.wondertek.meeting.action.base.BaseAction;
import com.wondertek.meeting.client.view.AgendaDateView;
import com.wondertek.meeting.client.view.AgendaView;
import com.wondertek.meeting.client.view.MeetingUserView;
import com.wondertek.meeting.client.view.MeetingView;
import com.wondertek.meeting.client.view.Organizer;
import com.wondertek.meeting.common.Constants;
import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.common.RetCode;
import com.wondertek.meeting.common.SessionKeeper;
import com.wondertek.meeting.common.SysConstants;
import com.wondertek.meeting.exception.HibernateDaoSupportException;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.manager.ServicePhoneAuthParam;
import com.wondertek.meeting.manager.SmsManager;
import com.wondertek.meeting.model.AdminUser;
import com.wondertek.meeting.model.ClientMenu;
import com.wondertek.meeting.model.GroupPlan;
import com.wondertek.meeting.model.GroupPlanDetail;
import com.wondertek.meeting.model.Meeting;
import com.wondertek.meeting.model.MeetingAgenda;
import com.wondertek.meeting.model.MeetingComment;
import com.wondertek.meeting.model.MeetingDinner;
import com.wondertek.meeting.model.MeetingFiles;
import com.wondertek.meeting.model.MeetingFilesAssort;
import com.wondertek.meeting.model.MeetingMember;
import com.wondertek.meeting.model.MeetingPost;
import com.wondertek.meeting.model.News;
import com.wondertek.meeting.model.User;
import com.wondertek.meeting.model.UserApply;
import com.wondertek.meeting.model.UserTableInfoWithDate;
import com.wondertek.meeting.model.custom.MeetingType;
import com.wondertek.meeting.model.custom.PageCustom;
import com.wondertek.meeting.model.custom.PageTheme;
import com.wondertek.meeting.service.AdminUserService;
import com.wondertek.meeting.service.BaseMenuService;
import com.wondertek.meeting.service.ClientMenuService;
import com.wondertek.meeting.service.GroupPlanDetailService;
import com.wondertek.meeting.service.GroupPlanService;
import com.wondertek.meeting.service.MeetingAgendaService;
import com.wondertek.meeting.service.MeetingDinnerService;
import com.wondertek.meeting.service.MeetingFilesAssortService;
import com.wondertek.meeting.service.MeetingFilesService;
import com.wondertek.meeting.service.MeetingMemberService;
import com.wondertek.meeting.service.MeetingPostService;
import com.wondertek.meeting.service.MeetingService;
import com.wondertek.meeting.service.NewsService;
import com.wondertek.meeting.service.OrganizationService;
import com.wondertek.meeting.service.UserApplyService;
import com.wondertek.meeting.service.UserImportService;
import com.wondertek.meeting.service.UserService;
import com.wondertek.meeting.service.custom.MeetingTypeService;
import com.wondertek.meeting.service.custom.PageCustomService;
import com.wondertek.meeting.service.custom.PageThemeService;
import com.wondertek.meeting.util.DateUtil;
import com.wondertek.meeting.util.Encrypt;
import com.wondertek.meeting.util.HttpHelper;
import com.wondertek.meeting.util.StringUtil;


/**
 * 会务管理相关的页面逻辑处理
 * 
 * @author John Tang
 * 
 */
public class MeetingAction extends BaseAction {
	private String _meeting_id_;
	private static final long serialVersionUID = 8907637156408356721L;
	private MeetingService meetingService;
	private OrganizationService organizationService;
	private AdminUserService adminUserService;
	private ClientMenuService clientMenuService;
	private MeetingMemberService meetingMemberService;
	private UserImportService userImportService;
	private UserService userService;
	private static String WIN7 = "Windows 7";
	private BaseMenuService baseMenuService;
	private PageThemeService pageThemeService;
	private PageCustomService pageCustomService;
	private MeetingTypeService meetingTypeService;
	private NewsService newsService;
	private MeetingFilesAssortService meetingFilesAssortService;
	private MeetingFilesService meetingFilesService;
	private MeetingAgendaService meetingAgendaService;
	private GroupPlanService groupPlanService;
	private GroupPlanDetailService groupPlanDetailService;
	private MeetingDinnerService meetingDinnerService;
	
	private UserApplyService userApplyService;
	private User attendee;  //接收会议报名人的信息
	private Long[] meetingAdmins;
	private Long meetingId;
	MeetingPostService meetingPostService;

	private List<Organizer> orgList;//承办方列表
	
	
	public GroupPlanDetailService getGroupPlanDetailService() {
		return groupPlanDetailService;
	}

	public void setGroupPlanDetailService(
			GroupPlanDetailService groupPlanDetailService) {
		this.groupPlanDetailService = groupPlanDetailService;
	}

	public List<Organizer> getOrgList() {
		return orgList;
	}

	public void setOrgList(List<Organizer> orgList) {
		this.orgList = orgList;
	}

	public MeetingPostService getMeetingPostService() {
		return meetingPostService;
	}

	public void setMeetingPostService(MeetingPostService meetingPostService) {
		this.meetingPostService = meetingPostService;
	}

	private List<User> memberList;
	public List<User> getMemberList() {
		return memberList;
	}

	public void setMemberList(List<User> memberList) {
		this.memberList = memberList;
	}

	public BaseMenuService getBaseMenuService() {
		return baseMenuService;
	}

	public void setBaseMenuService(BaseMenuService baseMenuService) {
		this.baseMenuService = baseMenuService;
	}

	public OrganizationService getOrganizationService() {
		return organizationService;
	}

	public void setOrganizationService(OrganizationService organizationService) {
		this.organizationService = organizationService;
	}

	public MeetingService getMeetingService() {
		return meetingService;
	}

	public void setMeetingService(MeetingService meetingService) {
		this.meetingService = meetingService;
	}

	public ClientMenuService getClientMenuService() {
		return clientMenuService;
	}

	public void setClientMenuService(ClientMenuService clientMenuService) {
		this.clientMenuService = clientMenuService;
	}

	public MeetingMemberService getMeetingMemberService() {
		return meetingMemberService;
	}

	public void setMeetingMemberService(MeetingMemberService meetingMemberService) {
		this.meetingMemberService = meetingMemberService;
	}

	public UserImportService getUserImportService() {
		return userImportService;
	}

	public void setUserImportService(UserImportService userImportService) {
		this.userImportService = userImportService;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	private Meeting meeting;

	public Meeting getMeeting() {
		return meeting;
	}

	public void setMeeting(Meeting meeting) {
		this.meeting = meeting;
	}

	/**
	 * 查询出当前用户所创建的会议信息
	 */
	public String list() {
		try {
			log.debug("meeting: " + meeting);
			AdminUser currentUser = getAdminUserFromSession();
			log.debug("current user {}", currentUser);
			Pager<Meeting> meetingPager = meetingService.findMeetingPager(meeting, currentUser, currentPage, pageSize);
			getRequest().setAttribute("pager", meetingPager);
			return SUCCESS;
		} catch (Exception e) {
			log.error("List meeting pager error,", e);
			return ERROR;
		}
	}

	/**
	 * 添加会议信息
	 */
	public String add() {
		try {
			AdminUser currentUser = getAdminUserFromSession();
			log.debug("current user {}", currentUser);
			log.debug("meeting: " + meeting);
			meeting.setCreator(currentUser);
			meeting.setCreateTime(DateUtil.getCurrentDate());

			/**
			meeting.setStartTime(DateUtil.parseDate(meeting.getStartTime(), DateUtil.FIRST_SECOND));
			meeting.setEndTime(DateUtil.parseDate(meeting.getEndTime(), DateUtil.LAST_SECOND));
			**/

			Date startDate = DateUtil.convertStringToDate("yyyy-MM-dd HH:mm:ss", getParameter("meeting.startTime")+":00");
			Date endDate = DateUtil.convertStringToDate("yyyy-MM-dd HH:mm:ss", getParameter("meeting.endTime")+":59");
			meeting.setStartTime(startDate);
			meeting.setEndTime(endDate);
			
			if(meeting.getAccessStartTime()!=null){
				meeting.setAccessStartTime(DateUtil.parseDate(meeting.getAccessStartTime(), DateUtil.FIRST_SECOND));
			}
			if(meeting.getAccessEndTime()!=null){
				meeting.setAccessEndTime(DateUtil.parseDate(meeting.getAccessEndTime(), DateUtil.LAST_SECOND));
			}
			
			// 处理会务人员
			if (meetingAdmins != null && meetingAdmins.length > 0) {
				Set<AdminUser> users = new HashSet<AdminUser>();
				meeting.setMeetingAdmins(users);
				for (Long meetingAdminId : meetingAdmins) {
					AdminUser meetingAdmin = new AdminUser();
					meetingAdmin.setId(meetingAdminId);
					users.add(meetingAdmin);
				}
			}
			//处理承办方信息
			meeting.setOrganizer(StringUtil.orgListToStr(orgList));
			
			Long mid = meetingService.add(meeting);
			log.debug("meeting id====: " + mid);
			// Pager<Meeting> meetingPager =
			// meetingService.findMeetingPager(currentUser, currentPage,
			// pageSize);
			// getRequest().setAttribute("pager",meetingPager);

			meeting.setId(mid);

			// 进行菜单的初始化 记得抓异常 不要影响主流程
			try {
				baseMenuService.initialMeetingMenu(meeting);
				clientMenuService.InitializeMenu(mid);
			} catch (Exception e) {
				log.debug("系统异常:" + e.getMessage());
				e.printStackTrace();
			}
			
			//保存会议信息到Session中
			String meetingName = StringEscapeUtils.escapeXml(meeting.getName());
			getSession().setAttribute(SessionKeeper.CURRENT_MEETING, meetingName);
			getSession().setAttribute(SessionKeeper.CURRENT_MEETING_ID, meeting.getId());
			
			resultMap.put("retcode", RetCode.SUCCESS);
			resultMap.put("retmsg", getText("meeting.add.success"));
			resultMap.put("retdata", meeting);
			
			//added from meeting guide
			String guideStep = this.getParameter("guideStep");
			resultMap.put("guideStep", guideStep);
			
			return SUCCESS;
		} catch (Exception e) {
			log.error("添加会议失败,", e);
			resultMap.put("result", RetCode.FAIL);
			resultMap.put("retmsg", e.getMessage());
			return INPUT;
		}
	}

	/**
	 * 更新会议信息
	 */
	public String modifyXY() {
		try {
			AdminUser currentUser = getAdminUserFromSession();
			meeting = meetingService.findById(Long.parseLong(getParameter("id")));
			log.debug("current user {}", currentUser);
			meeting.setModifier(currentUser);
			meeting.setLocationXY(getParameter("xy"));
			meetingService.modify(meeting);
			resultMap.put("retcode", RetCode.SUCCESS);
			resultMap.put("retmsg", getText("meeting.modify.success"));

			return SUCCESS;
		} catch (Exception e) {
			log.error("修改会议失败,", e);
			
			resultMap.put("result", RetCode.FAIL);
			resultMap.put("retmsg", e.getMessage());
			return INPUT;
		}
	}

	/**
	 * 更新会议信息
	 */
	public String modify() {
		try {
			AdminUser currentUser = getAdminUserFromSession();
			log.debug("current user {}", currentUser);
			String flag = getParameter("flag");
			// 如果是编辑会议用户，则需要先保留会议基本信息
			log.debug("meeting=================: " + meeting);
			log.debug("meeting startDate=================: " + getParameter("meeting.startTime"));
			log.debug("meeting endDate=================: " + getParameter("meeting.endTime"));
			Meeting m = meetingService.getMeetingByPk(Long.parseLong(getParameter("meeting.id")));

			meeting.getStartTime();
			// 如果是编辑会议用户而不修改其他内容
			if (flag != null && flag.equals("editMember")) {
				String memberstr = getParameter("memberstr");
				String[] members = memberstr.split(",");
				Set<User> memberSet = new HashSet<User>();

				for (int i = 0; i < members.length; i++) {
					User u = new User();
					u.setId(Long.parseLong(members[i]));
					memberSet.add(u);
				}
				m.setMemberSet(memberSet);
			} else {
				// 更新除了"id","creator","createtime","memberSet","locationXY"之外的字段
				/*
				meeting.setStartTime(DateUtil.parseDate(meeting.getStartTime(), DateUtil.FIRST_SECOND));
				meeting.setEndTime(DateUtil.parseDate(meeting.getEndTime(), DateUtil.LAST_SECOND));				*/
				Date startDate = DateUtil.convertStringToDate("yyyy-MM-dd HH:mm:ss", getParameter("meeting.startTime")+":00");
				Date endDate = DateUtil.convertStringToDate("yyyy-MM-dd HH:mm:ss", getParameter("meeting.endTime")+":59");
				meeting.setStartTime(startDate);
				meeting.setEndTime(endDate);
				if(meeting.getAccessStartTime()!=null){
					meeting.setAccessStartTime(DateUtil.parseDate(meeting.getAccessStartTime(), DateUtil.FIRST_SECOND));
				}
				if(meeting.getAccessEndTime()!=null){
					meeting.setAccessEndTime(DateUtil.parseDate(meeting.getAccessEndTime(), DateUtil.LAST_SECOND));
				}
				meeting.setModifyTime(DateUtil.getCurrentDate());
				BeanUtils.copyProperties(meeting, m, new String[] { "id", "creator", "createTime", "memberSet",
						"locationXY", "meetingAdmins" });
				// 处理会务人员
				if (meetingAdmins != null && meetingAdmins.length > 0) {
					Set<AdminUser> users = new HashSet<AdminUser>();
					m.setMeetingAdmins(users);
					for (Long meetingAdminId : meetingAdmins) {
						AdminUser meetingAdmin = new AdminUser();
						meetingAdmin.setId(meetingAdminId);
						users.add(meetingAdmin);
					}
				} else {
					m.setMeetingAdmins(null);
				}
			}
			m.setModifier(currentUser);
			
			//处理承办方信息
			m.setOrganizer(StringUtil.orgListToStr(orgList));
			log.debug("meeting-----------------------: " + m);
			
			meetingService.modify(m);

			resultMap.put("retcode", RetCode.SUCCESS);
			resultMap.put("retmsg", getText("meeting.modify.success"));
			resultMap.put("retdata", meeting);
			
			//added from meeting guide
			String guideStep = this.getParameter("guideStep");
			String fromStep = getParameter("fromStep");
			resultMap.put("guideStep", guideStep);
			resultMap.put("fromStep", fromStep);
			
			return SUCCESS;
		} catch (Exception e) {
			log.error("修改会议失败,", e);

			resultMap.put("result", RetCode.FAIL);
			resultMap.put("retmsg", e.getMessage());
			return INPUT;
		}
	}

	/**
	 * 根据会议id删除会议
	 * 
	 * @return
	 */
	public String delete() {
		//修改为逻辑删除
		try {
			AdminUser currentUser = getAdminUserFromSession();
			meeting = meetingService.getMeetingByPk(meeting.getId());
			log.debug("current user {}", currentUser);
			meeting.setModifier(currentUser);
			meeting.setModifyTime(new Date());
			meeting.setState(9);
			meetingService.modify(meeting);
			resultMap.put("retcode", RetCode.SUCCESS);
			//物理删除并且删除相应的信息
//			meetingService.cleanMeetingData(meeting);
			resultMap.put("retmsg", getText("meeting.delete.success"));
			return SUCCESS;
		} catch (Exception e) {
			log.error("删除会议失败,", e);
			resultMap.put("result", RetCode.FAIL);
			resultMap.put("retmsg", e.getMessage());
			return INPUT;
		}
		/*
		try {
			User currentUser = (User) getFromSession(SessionKeeper.SESSION_USER);
			log.debug("current user {}", currentUser);
			log.debug("meeting: " + meeting);
			meetingService.delete(meeting);
			// Pager<Meeting> meetingPager =
			// meetingService.findMeetingPager(currentUser, currentPage,
			// pageSize);
			// getRequest().setAttribute("pager",meetingPager);

			resultMap.put("retcode", RetCode.SUCCESS);
			resultMap.put("retmsg", getText("meeting.delete.success"));

			return SUCCESS;
		} catch (Exception e) {
			log.error("修改会议失败,", e);

			resultMap.put("result", RetCode.FAIL);
			resultMap.put("retmsg", e.getMessage());
			return INPUT;
		}
		*/
	}

	/**
	 * 清除会议历史数据，针对已经做过逻辑删除的会议
	 * @return
	 */
	public String cleanMeetingData() {		
		try {
			User currentUser = (User) getFromSession(SessionKeeper.SESSION_USER);
			log.debug("current user {}", currentUser);
			log.debug("meeting: " + meeting);
			meetingService.cleanMeetingData(meeting);

			resultMap.put("retcode", RetCode.SUCCESS);
			resultMap.put("retmsg", getText("meeting.clean.success"));

			return SUCCESS;
		} catch (Exception e) {
			log.error("修改会议失败,", e);

			resultMap.put("result", RetCode.FAIL);
			resultMap.put("retmsg", e.getMessage());
			return INPUT;
		}

	}
	
	/**
	 * 根据会议的云类型来处理与此会议相关的主题信息，把主题信息保存到session中
	 * 如果该会议没有指定主题信息，则使用默认主题
	 * 同样，对于页面定制信息，当参会人员进入一个会议时，写该会议相关的logo等信息也会显示出来
	 * added by 张国敬 at 2011/12/30
	 */
	private void handlePageTheme(Meeting meeting) {
		if(meeting == null) {
			return;
		}
		
		this.getSession().removeAttribute(SessionKeeper.SESSION_KEY_MEETING_LOGO_IMAGE); //先删除session中的会议logo文件url
		this.getSession().removeAttribute(SessionKeeper.SESSION_KEY_MEETING_TOP_IMAGE); //先删除session中的会议顶部图片文件url
		this.getSession().removeAttribute(SessionKeeper.SESSION_KEY_PAGE_THEME_NAME); //先删除session中的页面主题
		
		//处理会议logo
		PageCustom custom = this.pageCustomService.getPageCustom(meeting.getId(), Consts.PAGE_CUSTOM_PORTAL_LOGO_IMAGE);
		if(custom != null) {
			String imageUrl = this.getServerUrl() + custom.getPropertyValue();
			this.getSession().setAttribute(SessionKeeper.SESSION_KEY_MEETING_LOGO_IMAGE, imageUrl);
		}
		
		//处理顶部图片
		custom = this.pageCustomService.getPageCustom(meeting.getId(), Consts.PAGE_CUSTOM_PORTAL_TOP_IMAGE);
		if(custom != null) {
			String imageUrl = this.getServerUrl() + custom.getPropertyValue();
			this.getSession().setAttribute(SessionKeeper.SESSION_KEY_MEETING_TOP_IMAGE, imageUrl);
		}
		
		//与会议相关的主题信息配置在数据库的page_theme表中
		PageTheme theme = meeting.getPageTheme();
		if(theme == null) { 
			try {
				theme = this.pageThemeService.findById(1L); //1为系统默认配置的主题
			} catch (ServiceException e) {
				e.printStackTrace();
			}
			
			//如果没有设定默认会议类型，则返回
			if(theme == null) {
				return;
			}
		}
		
		if(theme != null && theme.getName().trim().length() > 0) {
			String themeName = "/" + theme.getName();
			this.getSession().setAttribute(SessionKeeper.SESSION_KEY_PAGE_THEME_NAME, themeName);
			
			log.info("handlePageTheme: put page theme info into session by key pageThemeName, theme=" + theme);
		}
	}
	
	/**
	 * 根据会议id获取会议基本信息
	 * 
	 * @return
	 */
	public String getMeetingById() {
		try {
			String returnType = this.getParameter("returnType");
			log.debug("returnType=========={}", returnType);
			log.debug("meeting=========={}", meeting);
			// 注明来源
			String from = getRequest().getParameter("from");
			getRequest().setAttribute("from", from);
			if (meeting != null) {
				log.debug("meeting id is： {}", meeting.getId());
				meeting = meetingService.findById(meeting.getId());
				if(meeting == null) {
					return "meeting_list";  //会议ID不正确，则进入会议列表视图
				}
				
				//处理会议主题信息，added by 张国敬 at 2011/12/30
				this.handlePageTheme(meeting);
				
				//查找可用的页面皮肤主题列表
				List<PageTheme> pageThemeList = this.pageThemeService.findAllUsablePageThemes();
				this.setAttribute("pageThemeList", pageThemeList);
				
				// 判断跳转页面，
				if (returnType != null && returnType.equals("portal")) {
					User currentUser = (User) getFromSession(SessionKeeper.SESSION_USER);
					MeetingMember mm = meetingMemberService.selectById(currentUser.getId(), meeting.getId());
					if(mm != null) {
						List<ClientMenu> menulist = clientMenuService.queryMenuList(meeting.getId(), mm.getMemberLevel(),
							SysConstants.MENU_WEB);
						getSession().setAttribute("_portal_menulist_", menulist);
					}
					
					//移除上一次选择的会议session信息
					getSession().removeAttribute("_portal_meeting_");
					getSession().setAttribute("_portal_meeting_", meeting);
					
					return "portal";
				} else if (returnType != null && returnType.equals("wap_index")) {
					User currentUser = (User) getFromSession(SessionKeeper.SESSION_USER);
					MeetingMember mm = meetingMemberService.selectById(currentUser.getId(), meeting.getId());
					List<ClientMenu> menulist = clientMenuService.queryMenuList(meeting.getId(), mm.getMemberLevel(),
							SysConstants.MENU_WAP);
					
					//移除上一次选择的会议session信息
					getSession().removeAttribute("_portal_menulist_");
					getSession().removeAttribute("_portal_meeting_");
					
					getSession().setAttribute("_portal_menulist_", menulist);
					getSession().setAttribute("_portal_meeting_", meeting);
					return "wap_index";
				} else if (returnType != null && returnType.equals("wap")) {					
					return "wap";
				} else if (returnType != null && returnType.equals("preview")) {
					User previewUser = (User) getFromSession(SessionKeeper.SESSION_PREVIEW_USER);
					log.debug("previewUser is :{}", previewUser);
					List<ClientMenu> menulist = clientMenuService.getMenuByTerminalType(SysConstants.MENU_WEB,
							String.valueOf(meeting.getId()));
					getSession().setAttribute("_portal_menulist_", menulist);
					getSession().setAttribute("_portal_meeting_", meeting);
					return "portal";
				} else if (returnType != null && returnType.equals("portal_addmeeting")) {
					// 如果是门户添加会议
					return "portal_addmeeting";
				} else if (returnType != null && returnType.equals("portal_viewmeeting")) {
					// 如果是门户查看会议
					//added by 张国敬 at 2012/3/9: 处理会议portal首页数据
					this.loadDataForMeetingHomePage(meeting.getId());
					
					return "portal_viewmeeting";
				} else if (returnType != null && returnType.equals("touch_viewmeeting")) {
					// 如果是门户查看会议
					this.loadDataForMeetingHomePage(meeting.getId());
					//TODO
					if("touch_list".equals(from)){
						
					}
					
					return "touch_viewmeeting";
				} else if (returnType != null && returnType.equals("meeting_topic")) {
					
					return "meeting_topic";
				}else if (returnType != null && returnType.equals("portal_map")) {
					// 地图
					//setMapInfo();
					return "portal_map";
				} else if (returnType != null && returnType.equals("edit_map")) {
					// 地图
					setMapInfo();
					return "edit_map";
				} else if (returnType != null && returnType.equals("portal_users")) {
					// 如果是门户查看参会人员
					return "portal_users";
				}
				// 如果是后台管理列表中点击编辑会议，则保存当亲编辑会议名称到session中，方便页面中提示当前正在编辑的是哪一个会议
				if ("manage".equals(returnType)) {
					String meetingName = StringEscapeUtils.escapeXml(meeting.getName());
					// meetingName = meetingName.length() >= 50 ?
					// meetingName.substring(0, 50) + "..." : meetingName;
					/*
					 * getSession().setAttribute(SessionKeeper.CURRENT_MEETING,
					 * "您当前正在编辑的会议是：" + meetingName + "(会议编号：" + meeting.getId()
					 * + ")");
					 */
					getSession().setAttribute(SessionKeeper.CURRENT_MEETING, meetingName);
					getSession().setAttribute(SessionKeeper.CURRENT_MEETING_ID, meeting.getId());
					
					//后台修改会议信息，则初始化基本类型列表
					List<MeetingType> meetingTypeList = this.meetingTypeService.findAll();
					this.setAttribute("meetingTypeList", meetingTypeList);
					
					String fromStep = getParameter("fromStep");
					setAttribute("fromStep", fromStep);
				}
			}

			// 查询出creator所属机构下的会务人员列表
			Long orgId = null;
			Set<AdminUser> selectedAdmins = null;
			if (meeting != null) {
				orgId = meeting.getCreator().getOrg().getId();
				selectedAdmins = meeting.getMeetingAdmins();
			} else {
				AdminUser sessionUser = this.getAdminUserFromSession();
				orgId = sessionUser.getOrg().getId();
			}
			
			// 标记选择的会务人员
			List<AdminUser> meetingAdmins = adminUserService.getAdminUsersByOrg(orgId, SysConstants.MEETING_ADMIN);
			if (selectedAdmins != null && selectedAdmins.size() > 0) {
				for (AdminUser meetingAdmin : meetingAdmins) {
					for (AdminUser selectedAdmin : selectedAdmins) {
						if (meetingAdmin.getId().equals(selectedAdmin.getId())) {
							meetingAdmin.setSelected(true);
							break;
						}
					}
				}
			}
			this.getRequest().setAttribute("meetingAdmins", meetingAdmins);
			
			
			if (returnType != null && returnType.equals("manage_baseinfo")) {
				//后台添加会议信息，则初始化基本类型列表
				List<MeetingType> meetingTypeList = this.meetingTypeService.findAll();
				this.setAttribute("meetingTypeList", meetingTypeList);
				
				// 如果是后台管理会议基本信息
				return "manage_baseinfo";
			} else if (returnType != null && returnType.equals("manage_members")) {
				// 如果是后台管理会议基本信息
				return "manage_members";
			} else if (returnType != null && returnType.equals("batch_add_members")) {
				// 如果是后台批量添加参会人员
				return "batch_add_members";
			}
			return SUCCESS;
		} catch (ServiceException e) {
			log.error("添加会议失败,", e);
			return INPUT;
		}
	}
	
	/**
	 * 加载会议首页显示数据
	 */
	private void loadDataForMeetingHomePage(Long meetingId) {
		try {
			//设置会议ID
			this.setAttribute("meetingId", meetingId);
			//取时间信息
			Date now = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("M-d", Locale.SIMPLIFIED_CHINESE);
			String dateStr = sdf.format(now);
			this.setAttribute("dateStr", dateStr);
			sdf = new SimpleDateFormat("E", Locale.SIMPLIFIED_CHINESE);
			String weekStr = sdf.format(now);
			this.setAttribute("weekStr", weekStr);
			
			//新闻信息,7条
			News news = new News();
			news.setState(1); //有效新闻
			news.setImageNews(1); //图片新闻
			Pager<News> newsPager = this.newsService.findAllNewsPager(meetingId, news, 1, 7);
			if(newsPager.getTotal() > 0) {
				this.setAttribute("newsList", newsPager.getPageRecords());
			}
			
			//用餐信息
			List<UserTableInfoWithDate> list = meetingDinnerService.findMemberDinnerList(meetingId, this.getUserIdFromSession());
			if(list.size() > 0) {
				//如果当天有用餐信息，则返回当前的用餐信息，否则则返回最小那一天的用餐信息
				UserTableInfoWithDate dinnerDateView = null;
				Date date = new Date();
				String todayStr = DateUtil.formatDate(date, "yyyy年MM月dd日");
				todayStr = DateUtil.convertDateFormat(todayStr,"yyyy年MM月dd日");
				for(UserTableInfoWithDate info: list) {
					if(info.getDate().equals(todayStr)) {
						dinnerDateView = info;
						break;
					}
				}
				if(dinnerDateView == null) {
					dinnerDateView = list.get(0);
				}
				dinnerDateView.setDate(DateUtil.formatDate(DateUtil.parseDate(dinnerDateView.getDate(), "yyyy年MM月dd日"), "M月d日"));
				this.setAttribute("dinnerDateView", dinnerDateView);
			}
			
			//会议资料信息
			String userId=String.valueOf(this.getUserIdFromSession());
			MeetingMember meetingMember=new MeetingMember();
			if(StringUtil.isNotEmpty(userId)){
				meetingMember=meetingMemberService.selectById(Long.valueOf(userId), Long.valueOf(meetingId));
			}
			//查询文件列表
			MeetingFiles meetingFiles = new MeetingFiles();
			meetingFiles.setMeetingId(meetingId.intValue());
			Pager<MeetingFiles> filePager = null;
			filePager = meetingFilesService.getMeetingFilesPager(currentPage, pageSize,
					meetingFiles, String.valueOf(meetingMember.getMemberLevel()));
		    //循环一个分类名称
			List<MeetingFiles>  meetingFilespagerList=filePager.getPageRecords();
			for(MeetingFiles meetingFile:meetingFilespagerList){
				if(meetingFile.getAssortId()!=null){
					MeetingFilesAssort meetingFilesAssort=	meetingFilesAssortService.findById(meetingFile.getAssortId().longValue());
				    if(meetingFilesAssort!=null){
				    	meetingFile.setAssortName(meetingFilesAssort.getAssortName());
				    }else{
				    	meetingFile.setAssortName("未分类");
				    }
				}else{
					meetingFile.setAssortName("未分类");
				}
			}
			if(meetingFilespagerList.size() > 0) {
				this.setAttribute("totalFileCount", filePager.getTotal());
				this.setAttribute("fileList", meetingFilespagerList);
				this.setAttribute("fileType", Consts.FILE_TYPE_THUMBNAIL);
			}
			
			//会议日程信息
			User user = getUserFromSession();
			List<MeetingAgenda> agendaList = meetingAgendaService.queryListByMeetingId(meetingId, user.getId());
			if(agendaList.size() > 0) {
				AgendaDateView agendaDateView = convertAgendaToView(agendaList);
				this.setAttribute("agendaDateView", agendaDateView);
			}
			
			//议程列表
			final List<MeetingAgenda> agenda = meetingAgendaService.queryListByMeetingId(meetingId, user.getId());
			//取当前日期的后3天议程
			this.setAttribute("agendaListContent", convertAgentListToView(agenda));
			
			//会务人员信息
		 List<GroupPlanDetail> detailList=	groupPlanDetailService.getDetailByMeetingUserId(meetingId,this.getUserIdFromSession(), 1);
		 if(detailList!=null&&detailList.size()>0){
			 GroupPlanDetail detail=detailList.get(0);
			 log.info("detail getBusinessPersonnel value:"+ detail.getBusinessPersonnel());
			 this.setAttribute("businessPersonnel", detail.getBusinessPersonnel());
		 }	
		 
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 查询天气预报信息
	 * added by zhangguojing: 由于265天气预报查询禁用iframe查询方式，帮改由此方式
	 */
	public String queryWeather() throws Exception {
		if(meeting == null || StringUtils.isEmpty(meeting.getCity())) {
			return null;
		}
		
		//查询天气预报信息
		String cityCode = meeting.getCity();
		String weatherUrl = "http://www.265.com/weather/" + cityCode + ".htm";
		String weatherInfo = HttpHelper.doGet(weatherUrl, "gbk");
		String contextPath = this.getRequest().getContextPath();
		weatherInfo = weatherInfo.replace("/js/weather_265_2.js", contextPath + "/js/weather_265_2.js");
		this.str2Resp(weatherInfo);
		return null;
	}
	
	/**
	 * 把查询出来的当天的后3天会议议程列表封装
	 * @param agendas
	 * @return
	 */
	private List<AgendaDateView> convertAgentListToView(final List<MeetingAgenda> agendas) {
		final Map<String, List<AgendaView>> viewMap = new LinkedHashMap<String, List<AgendaView>>();
		for (int i = 0; i < agendas.size(); i++) {
			MeetingAgenda agenda = agendas.get(i);
			final AgendaView view = new AgendaView();
			view.setId(agenda.getId());
			final GroupPlanDetail group = getGroupPlanDetail(agenda.getId());
			if (group == null) {
				view.setHasGroup(false);
				final GroupPlan groupPlan = agenda.getGroupPlan();
				if (groupPlan != null) {
					view.setGroupPlanId(groupPlan.getId());	
				}
			} else {
				view.setHasGroup(true);
				view.setGroupId(group.getId());
				view.setGroupName(group.getGroupName());
				view.setGroupDetail(group.getDetail());
				view.setGroupPlanId(group.getPlanId());
				view.setGroupPlanIsOpen(group.getIsOpen());
			}
			view.setMeetingId(agenda.getMeetingId());
			view.setDate(agenda.getDate());
			view.setHostName(agenda.getHost());
			view.setStartTime(agenda.getStartTime());
			view.setEndTime(agenda.getEndTime());
			view.setDescription(StringUtil.convertTextAreaNewLine(StringUtils.defaultIfEmpty(agenda.getDescription(), "")));
			view.setTopic(agenda.getTopic());
			view.setLocation(agenda.getLocation());
			//转化参与人员
			//TODO
			if (StringUtil.isNotEmpty(agenda.getAttendee())) {
				List<User> userLst = userService.getMeetingMemberByIds(
						agenda.getMeetingId(),
						agenda.getAttendee().substring(0,
								agenda.getAttendee().length() - 1));
				String attendeeNames = "";
				for (User user : userLst) {
					attendeeNames += user.getName() + ",";
				}
				
				//getRequest().setAttribute("attendeeNames", attendeeNames);
				log.debug("attendeeNames:"+attendeeNames);
				view.setAttendeeNames(attendeeNames);
			}
			
			
			
			if (viewMap.containsKey(agenda.getDate())) {
				viewMap.get(agenda.getDate()).add(view);
			} else {
				final List<AgendaView> views = new ArrayList<AgendaView>();
				views.add(view);
				viewMap.put(agenda.getDate(), views);
			}
		}

		int count = 0;
		Date cd = new Date();
		
		//限制只显示当前3天内的议程
		List<AgendaDateView> dateViews;
		try {
			dateViews = new ArrayList<AgendaDateView>();
			for (Map.Entry<String, List<AgendaView>> entry : viewMap.entrySet()) {
				final AgendaDateView dateView = new AgendaDateView();
				dateView.setDate(DateUtil.convertDateFormat(entry.getKey(), "yyyy-MM-dd"));
				Date tmpDate = DateUtil.convertStringToDate("yyyy年MM月dd日", dateView.getDate().substring(0,dateView.getDate().indexOf(" ")));
				cd = DateUtil.convertStringToDate("yyyy-MM-dd",DateUtil.formatDate(cd, "yyyy-MM-dd"));
				if(cd.equals(tmpDate) || cd.before(tmpDate)){
					if(count < 3){
						dateView.setAgendaViews(entry.getValue());
						dateViews.add(dateView);
						count++;
					}else{
						break;
					}
				}
				
			}
			if(count == 0){//会议议程已过很久，显示最后一天的议程
				int i=1;
				for (Map.Entry<String, List<AgendaView>> entry : viewMap.entrySet()) {
					if(i++ > viewMap.size()-3){
						final AgendaDateView dateView = new AgendaDateView();
						dateView.setDate(DateUtil.convertDateFormat(entry.getKey(), "yyyy-MM-dd"));
						dateView.setAgendaViews(entry.getValue());
						dateViews.add(dateView);
					}
				}
			}
			return dateViews;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	
	/**
	 * 如果当天有议程，则返回当前的议程，否则则返回最小那一天的议程
	 */
	private AgendaDateView convertAgendaToView(final List<MeetingAgenda> agendas) {
		final TreeMap<String, List<AgendaView>> viewMap = new TreeMap<String, List<AgendaView>>();
		for (int i = 0; i < agendas.size(); i++) {
			MeetingAgenda agenda = agendas.get(i);
			final AgendaView view = new AgendaView();
			view.setId(agenda.getId());
			final GroupPlanDetail group = getGroupPlanDetail(agenda.getId());
			if (group == null) {
				view.setHasGroup(false);
				final GroupPlan groupPlan = agenda.getGroupPlan();
				if (groupPlan != null) {
					view.setGroupPlanId(groupPlan.getId());	
				}
			} else {
				view.setHasGroup(true);
				view.setGroupId(group.getId());
				view.setGroupName(group.getGroupName());
				view.setGroupDetail(group.getDetail());
				view.setGroupPlanId(group.getPlanId());
				view.setGroupPlanIsOpen(group.getIsOpen());
			}
			view.setMeetingId(agenda.getMeetingId());
			view.setDate(agenda.getDate());
			view.setHostName(agenda.getHost());
			view.setStartTime(agenda.getStartTime());
			view.setEndTime(agenda.getEndTime());
			view.setDescription(StringUtil.convertTextAreaNewLine(StringUtils.defaultIfEmpty(agenda.getDescription(), "")));
			view.setTopic(agenda.getTopic());
			view.setLocation(agenda.getLocation());
			if (viewMap.containsKey(agenda.getDate())) {
				viewMap.get(agenda.getDate()).add(view);
			} else {
				final List<AgendaView> views = new ArrayList<AgendaView>();
				views.add(view);
				viewMap.put(agenda.getDate(), views);
			}
		}

		//如果当天有议程，则返回当前的议程，否则则返回最小那一天的议程
		String todayStr = DateUtil.formatDate(new Date(), "yyyy-MM-dd");
		List<AgendaView> avList = viewMap.get(todayStr);
		if(avList == null) {
			avList = viewMap.firstEntry().getValue();
			todayStr = viewMap.firstEntry().getKey();
		}
		
		AgendaDateView dateView = new AgendaDateView();
		dateView.setDate(DateUtil.formatDate(DateUtil.parseDate(todayStr, "yyyy-MM-dd"), "M月d日"));
		dateView.setAgendaViews(avList);
		return dateView;
	}
	
	private GroupPlanDetail getGroupPlanDetail(final Long agendaId) {
		final User user = getUserFromSession();
		GroupPlanDetail group = null;
		try {
			group = groupPlanService.getGroupDetailInfo(user.getId(), Constants.GROUP_PLAN_TYPE.AGENDA, agendaId);
		} catch (ServiceException e) {
			log.debug("do not have group plan", e.getMessage());
		}
		return group;
	}
	
	private void setMapInfo() {
		final String serverName = getRequest().getServerName();
		final String mapkeyName = serverName + "_mapabc_key";
		String mapkey = "";
		try {
			mapkey = ResourceBundle.getBundle("sys-config").getString(mapkeyName);
		
			setAttribute("city", ResourceBundle.getBundle("mapabc").getString(meeting.getCity()));
		} catch(Exception e) {
			setAttribute("city", "");
			e.printStackTrace();
		}
		setAttribute("mapkey", mapkey);		
	}

	/**
	 * 根据meetingid、userid获取参会人员基本信息
	 * 
	 * @return
	 * @throws ServiceException
	 */
	public String getMeetingUserById() throws ServiceException {

		if (meeting != null) {
			String userId = this.getParameter("userId");
			log.debug("meeting id is： {}", meeting.getId());
			log.debug("userId  is： {}", userId);
			MeetingMember mm = meetingMemberService.selectById(Long.valueOf(userId), meeting.getId());
			User member = userService.findById(Long.valueOf(userId));
			
			member.setPassword(Encrypt.decrypt(member.getPassword(), null));
			//会议用户关联信息表
			member.setMeetingMember(mm);
			
			getRequest().setAttribute("member", member);
			getRequest().setAttribute("member_level", mm.getMemberLevel());
			this.setAttribute("show", this.getParameter("show"));
		}
		return SUCCESS;
	}

	/**
	 * 获取会议参会人员
	 * 
	 * @return
	 * @throws ServiceException
	 */
	public String getMeetingUsers() throws ServiceException {
//		AdminUser currentUser = this.getAdminUserFromSession();
		log.debug("meeting is ================={}", meeting);
		String from=getRequest().getParameter("from");
		if (meeting != null) {
			String username = this.getParameter("username");
			String mobile = this.getParameter("mobile");
			String isAdmin = this.getParameter("isAdmin");
			String memberLevel=this.getParameter("memberLevel");
			User user = new User();
			user.setName(username);
			user.setMobile(mobile);
			MeetingMember meetingMember=new MeetingMember();
			meetingMember.setMemberLevel(StringUtil.isNotEmpty(memberLevel)?Integer.valueOf(memberLevel):null);
			user.setMeetingMember(meetingMember);
			
			//表明是通讯录的
			if(!"1".equals(isAdmin)){
				pageSize=40;
			}
			if(StringUtil.isNotEmpty(from)&&from.contains("guide")){
				pageSize=10000000;
			}
			
			Pager<User> userList = userService.findMeetingUserPager(meeting.getId(), user, currentPage, pageSize,isAdmin);
			log.debug("meeting id is： {}", meeting.getId());
			// 查询参会级别
			/*
			 * for(User u:userList.getPageRecords()){ MeetingMember mm =
			 * meetingMemberService.selectById(u.getId(), meeting.getId());
			 * u.setTempLevel(mm.getMemberLevel());
			 * u.setSortCode(mm.getSortCode()); }
			 * 
			 * 
			 * // 把当前的用户LIST进行排序 UserComparator userComparator=new
			 * UserComparator(); Collections.sort(userList.getPageRecords(),
			 * userComparator);
			 */
			// List<User> userList = meetingService.getMeetingUsers(meeting);
			getRequest().setAttribute("pager", userList);
			// 返回查询条件
			getRequest().setAttribute("username", username);
			getRequest().setAttribute("mobile", mobile);
			// log.debug("in manage_baseinfo=========={}",userList.size());
			/*
			 * boolean isAdmin= false; //系统管理员可以查看所有会议组织 AdminRole role =
			 * currentUser.getRole();
			 * log.debug("roles is ==================={}",role);
			 * 
			 * if(role!=null&&(role.getId().longValue()==1||role.getId()==2)){
			 * isAdmin = true; }
			 * 
			 * List<Organization> orgList = null; if(isAdmin){ orgList =
			 * organizationService.findAll(); }else{ orgList = new
			 * ArrayList<Organization>(); orgList.add(currentUser.getOrg()); }
			 * getRequest().setAttribute("orgList",orgList);
			 */
			if(StringUtil.isNotEmpty(from)&&from.contains("guide")){
				return "guide";
			}
			
			return "list";
		}
		return INPUT;
	}

	/**
	 * 获取通讯录返回给手机客户端
	 * @return
	 * @throws ServiceException
	 */
	public String getMeetingUsersForClient() throws ServiceException {
		String sMeetingNo = this.getParameter("meetingNo");
		String phone = this.getParameter("phone");
		String name = this.getParameter("name");
		String sPageSize = this.getParameter("pageSize")==null?"-1":this.getParameter("pageSize");
		String sCurrentPage = this.getParameter("currentPage")==null?"1":this.getParameter("currentPage");
		log.debug("phone====[{}],name=[{}]",phone,name);
		log.debug("pagesize====[{}],currentpage=[{}]",this.getParameter("pageSize"),this.getParameter("currentPage"));
		//转换分页参数为整型
		try{
			pageSize = Integer.parseInt(sPageSize);
			currentPage = Integer.parseInt(sCurrentPage);
		}catch (Exception e) {
			log.error("pagesize====[{}],currentpage=[{}]",sPageSize,sCurrentPage);
			resultMap.put("retcode", RetCode.PARAMS_INVALID);
			resultMap.put("retmsg", getText("client.meeting.user.paraminvalid"));
			return SUCCESS;
		}
		//转换会议编号参数为长整型
		Long meetingNo = null;
		try {
			meetingNo = Long.valueOf(sMeetingNo);
			log.debug("meeting is ================={}", meetingNo);
		} catch (Exception e) {
			resultMap.put("retcode", RetCode.PARAMS_INVALID);
			log.error("meeting no =[{}]",meetingNo);
			resultMap.put("retmsg", getText("client.meeting.user.paraminvalid"));
			return SUCCESS;
		}
		try {
			User user = new User();
			user.setMobile(phone);
			user.setName(name);
			Pager<User> userList = userService.findMeetingUserPager(meetingNo, user, currentPage, pageSize);
			// meeting = meetingService.findById(Long.valueOf(meetingNo));
			// Set<User> userSet = meeting.getMemberSet();
			resultMap.put("retcode", RetCode.SUCCESS);
			resultMap.put("retmsg", getText("client.meeting.user.success"));
			resultMap.put("totalPage", userList.getPageCount());
			resultMap.put("pageSize", userList.getPageSize());
			resultMap.put("currentPage", userList.getCurrentPage());
			resultMap.put("userList", convertToView(userList.getPageRecords()));
			return SUCCESS;
		} catch (Exception e) {
			resultMap.put("retcode", RetCode.FAIL);
			resultMap.put("retmsg", getText("client.meeting.user.error"));
			log.error("getMeetingUsersForClient  error :{}", e);
			return SUCCESS;
		}
	}

	/**
	 * 获取会议列表返回给手机客户端
	 * @return
	 */
	public String getMeetingListForClient() {
		String mobile = this.getParameter("mobile");// 手机号码

		// 1.检查参数合法性
		if (StringUtil.isEmpty(mobile)) {
			resultMap.put("retcode", RetCode.PARAMS_INVALID);
			resultMap.put("retmsg", getText("user.login.params.invalid.mobile"));
			return SUCCESS;
		}

		User user = new User();
		user.setMobile(mobile);

		// 2.登录
		User loginUser = null;
		loginUser = userService.selectByMobile(mobile);
		//loginUser = userService.clientLogin(user, this.getLoginIP());


		// 查询会议列表
		List<Meeting> meetingList = meetingService.getMyAttendMeetingList(String.valueOf(loginUser.getId()));

		// 登录成功
		Map<String, Object> retdata = new HashMap<String, Object>();
		retdata.put("meetingList", convertToMeetingView(meetingList));

		resultMap.put("retcode", RetCode.SUCCESS);
		resultMap.put("retmsg", getText("user.login.success"));
		resultMap.put("retdata", retdata);

		return SUCCESS;
	}
	
	/**
	 * 删除单个参会人员信息
	 * 
	 * @return
	 * @throws ServiceException
	 */
	public String deleteMeetingUser() throws ServiceException {
		log.debug("meeting is ================={}", meeting);
		if (meeting != null) {
			AdminUser currentUser = this.getAdminUserFromSession();
			String userId = this.getParameter("userid");
			MeetingMember mm = new MeetingMember();
			mm.setMeetingId(meeting.getId());
			mm.setUserId(Long.valueOf(userId));
			meetingMemberService.deleteUserCascade(mm);
		}
		return "list";
	}

	/**
	 * 批量保存参会人员信息
	 * 
	 * @return
	 */
	public String batchAddMeetingUser() {
		try {
			if(memberList != null && memberList.size() > 0){
				List<User> userlist = new ArrayList<User>();
				log.debug("dinnerList.size() = "+memberList.size());
				for(User user : memberList){
					log.debug("user= {} ",user);
					if(user!=null){
						userlist.add(user);
					}
				}
				userImportService.saveImportUser(userlist, meetingId,1);
			}
			resultMap.put("saveMsgTips", this.getText("meetingmember.saveMsgTips"));
		} catch (Exception e) {
			log.error("批量添加失败：",e);
			resultMap.put("retmsg", "保存失败");
		}
		return SUCCESS;
		
	}
	
	/**
	 * 保存参会人员信息
	 * 
	 * @return
	 */
	public String saveMeetingUser() {
		AdminUser currentUser = this.getAdminUserFromSession();
		String userName = this.getParameter("userName");// 用户名
		String mobile = this.getParameter("mobile");// 手机号码
		String job = this.getParameter("job");
		String gender = this.getParameter("gender");
		String mailbox = this.getParameter("mailbox");
		String address = this.getParameter("address");
		String birthday = this.getParameter("birthday");
		String city = this.getParameter("city");
		String memberLevel = this.getParameter("memberLevel");
		String sortCode = this.getParameter("sortCode");
		String password = this.getParameter("password");
		String mobileIsDisplay = this.getParameter("mobileIsDisplay");
		String addInContacts = this.getParameter("addInContacts");
		String roomNumber = StringUtil.replaceBlank(this.getParameter("roomNumber"));
		String roomNumberIsDisplay=this.getParameter("roomNumberIsDisplay");
		String jobIsDisplay=this.getParameter("jobIsDisplay");
		String department=this.getParameter("department");
		String bookJob=this.getParameter("bookJob");
		String uploadAuthority=this.getParameter("uploadAuthority");
		String vip=this.getParameter("vip");
		
		
		String delegation=this.getParameter("delegation");
		String inPrivateMessage=this.getParameter("inPrivateMessage");
		
		
		
		log.info("userName:" + userName + ",mobile:" + mobile + ",job:" + job
				+ ",gender:" + gender + ",mailbox:" + mailbox + ",address:"
				+ address + ",birthday:" + birthday + ",city:" + city
				+ ",memberLevel:" + memberLevel + ",sortCode:" + sortCode
				+ ",password:" + password + ",mobileIsDisplay:"
				+ mobileIsDisplay + ",addInContacts:" + addInContacts
				+ ",roomNumberIsDispaly:" + roomNumberIsDisplay
				+ ",jobIsDisplay:" + jobIsDisplay + ",bookJob:" + bookJob+",uploadAuthority:"+uploadAuthority+",vip:"+vip);
		
		User user = new User();
		//会议下成员
		user.setMeetingMember(new MeetingMember());
		user.setName(StringUtil.replaceBlank(userName));
		user.setPassword(StringUtil.replaceBlank(password));
		user.setMobile(StringUtil.replaceBlank(mobile));
		//对GENDER赋值
		if(StringUtil.isNotEmpty(gender)){
			user.setGender(Integer.valueOf(gender));
		}else{
			user.setGender(null);
		}
		user.getMeetingMember().setMailbox(StringUtil.replaceBlank(mailbox));
		user.getMeetingMember().setAddress(StringUtil.replaceBlank(address));
		user.setBirthday(birthday);
		user.getMeetingMember().setCity(StringUtil.replaceBlank(city));
		user.getMeetingMember().setJob(StringUtil.replaceBlank(job));
		user.getMeetingMember().setBookJob(StringUtil.replaceBlank(bookJob));
		user.getMeetingMember().setMobileIsDisplay(
				Integer.valueOf(mobileIsDisplay));
		user.getMeetingMember().setAddInContacts(addInContacts);
		user.getMeetingMember().setRoomNumber(roomNumber);
		user.getMeetingMember().setRoomNumberIsDisplay(
				Integer.valueOf(roomNumberIsDisplay));
		user.getMeetingMember().setJobIsDisplay(Integer.valueOf(jobIsDisplay));
		if (sortCode != null && !"".equals(sortCode)
				&& StringUtil.isNumber(sortCode)) {
			user.getMeetingMember().setSortCode(Integer.valueOf(sortCode));
		}
		user.getMeetingMember().setDepartment(department);
		user.getMeetingMember().setUploadAuthority(uploadAuthority);
		user.getMeetingMember().setVip(vip);
		user.getMeetingMember().setDelegation(delegation);
		user.getMeetingMember().setInPrivateMessage(Boolean.valueOf(inPrivateMessage));
		
		List<User> userlist = new ArrayList<User>();
		userlist.add(user);
		//对memberLevel进行一个赋值,防止空指针
		if(StringUtil.isEmpty(memberLevel)){
			memberLevel="1";
		}
		userImportService.saveImportUser(userlist, meeting.getId(),
				Integer.valueOf(memberLevel));
		//提示消息
		this.setAttribute("saveMsgTips", this.getText("meetingmember.saveMsgTips"));
		// getRequest().setAttribute("_meeting_id_", meeting.getId());
		return SUCCESS;
	}

	/**
	 * 检查会议总机是否存在
	 * 
	 * @return
	 */
	public String checkServiceNumber() {
		SmsManager sm = new SmsManager();
		log.debug("service number ======" + meeting.getServiceNumber());
		ServicePhoneAuthParam spap = new ServicePhoneAuthParam();
		// 会议id，默认为99999999
		spap.setMeetingId("99999999");
		// 会议开始、结束时间
		log.debug("service getStartTime() ======" + meeting.getStartTime());
		log.debug("service getEndTime() ======" + meeting.getEndTime());
		log.debug("service getFreeSmsNum() ======" + meeting.getFreeSmsNum());
		log.debug("service getServiceNumber() ======" + meeting.getServiceNumber());
		/*
		meeting.setStartTime(DateUtil.parseDate(meeting.getStartTime(), DateUtil.FIRST_SECOND));
		meeting.setEndTime(DateUtil.parseDate(meeting.getEndTime(), DateUtil.LAST_SECOND));
		*/

		try {
			Date startDate = DateUtil.convertStringToDate("yyyy-MM-dd HH:mm:ss", getParameter("meeting.startTime")+":00");
			Date endDate = DateUtil.convertStringToDate("yyyy-MM-dd HH:mm:ss", getParameter("meeting.endTime")+":59");
			meeting.setStartTime(startDate);
			meeting.setEndTime(endDate);
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			inputStream = new StringBufferInputStream("false");
			return SUCCESS;
		}
		
		String startTime = DateUtil.getFormatDate(meeting.getStartTime(), "yyyyMMddHHmmss");
		String endTime = DateUtil.getFormatDate(meeting.getEndTime(), "yyyyMMddHHmmss");
		spap.setStartTime(startTime);
		spap.setEndTime(endTime);
		spap.setServicePhone(meeting.getServiceNumber());
		// 免费短信条数
		spap.setFreeSmsNum(String.valueOf(meeting.getFreeSmsNum()));

		if (sm.authCustomerServicePhone(spap)) {
			inputStream = new StringBufferInputStream("true");
		} else {
			inputStream = new StringBufferInputStream("false");
			// TODO 如果是WIN7系统 则不验证是否有效 直接返回TRUE
			if (WIN7.equals(System.getProperty("os.name"))) {
				inputStream = new StringBufferInputStream("true");
			}

		}
		return SUCCESS;
	}

	private InputStream inputStream;

	public InputStream getInputStream() {
		return inputStream;
	}

	public String get_meeting_id_() {
		return _meeting_id_;
	}

	public void set_meeting_id_(String _meeting_id_) {
		this._meeting_id_ = _meeting_id_;
	}

	private List<MeetingUserView> convertToView(List<User> userList) {
		final List<MeetingUserView> list = new ArrayList<MeetingUserView>();
		for (User u : userList) {
			MeetingUserView muv = new MeetingUserView();
			muv.setId(u.getId());
			muv.setName(u.getName());
			String mobile = u.getMeetingMember().getMobileIsDisplay()
					.intValue() == 1 ? u.getMobile() : "未公开";
			muv.setMobile(mobile);
			muv.setJob(u.getMeetingMember().getJob());
			muv.setGender(u.getGender());
			muv.setMailbox(u.getMeetingMember().getMailbox());
			muv.setCity(u.getMeetingMember().getCity());
			muv.setMobileIsDisplay(u.getMeetingMember().getMobileIsDisplay());
			String roomNo = u.getMeetingMember().getRoomNumberIsDisplay()
					.intValue()==1?u.getMeetingMember().getRoomNumber():"未公开";
			muv.setRoomNo(roomNo);
			muv.setRoomNumberIsDisplay(u.getMeetingMember().getRoomNumberIsDisplay());
			muv.setJobIsDisplay(u.getMeetingMember().getJobIsDisplay());
			muv.setDepartment(u.getMeetingMember().getDepartment());
			//通讯录职位
//			String bookJob = u.getMeetingMember().getJobIsDisplay()
//					.intValue() == 1 ? u.getMeetingMember().getBookJob(): "未公开";
			muv.setBookJob(u.getMeetingMember().getBookJob());
			
			list.add(muv);
		}
		return list;
	}
	
	/**
	 * 批量删除
	 * @return
	 * @throws NumberFormatException
	 * @throws ServiceException
	 */
	public String batchDelMeetingUser() throws NumberFormatException, ServiceException {
		String forword = SUCCESS;
		String ids = getRequest().getParameter("ids");
		//String meetingId=getRequest().getParameter("meetingId");
		log.debug("ids:"+ids);
		
		if (ids != null && ids.length() > 0) {
			ids = ids.substring(0, ids.length() - 1);
		}
		String[] userIdArray = ids.split(",");
		for (int i = 0; i < userIdArray.length; i++) {
			MeetingMember mm = new MeetingMember();
			mm.setMeetingId(meeting.getId());
			mm.setUserId(Long.valueOf(userIdArray[i]));
			meetingMemberService.deleteUserCascade(mm);
		}
		
		
		return forword;
	}

	public String previewMeeting() {
		User u = new User();
		u.setId(99999999999L);
		u.setMobile("99999999999");
		getSession().setAttribute(SessionKeeper.SESSION_PREVIEW_USER, u);
		return "preview";
	}
	
	private List<MeetingView> convertToMeetingView(List<Meeting> meetingList) {
		List<MeetingView> views = new ArrayList<MeetingView>();

		for (Meeting m : meetingList) {

			MeetingView view = new MeetingView();
			view.setId(m.getId());
			view.setName(m.getName());

			views.add(view);
		}

		return views;
	}

	public void setPageThemeService(PageThemeService pageThemeService) {
		this.pageThemeService = pageThemeService;
	}

	/*
	 * 以访客的身份查看会议信息，无需登录到系统中
	 */
	public String viewMeeting() throws Exception {
		if(meeting == null || meeting.getId() == null) {
			return "message";
		}
		
		log.info("viewMeeting: meetingId=" + meeting.getId());
		meeting = this.meetingService.findById(meeting.getId());
		if(meeting == null) {
			return "message";
		}
		
		//处理会议主题信息，added by 张国敬 at 2011/12/30
		this.handlePageTheme(meeting);
		return SUCCESS;
	}
	
	/*
	 * 快速加入会议
	 */
	public String quickEnterMeeting() {
		if (attendee == null) {
			errMsg = "请输入手机号码和参会密码";
			return INPUT;
		}

		// 2.登录
		User loginUser = null;
		try {
			loginUser = userService.login(attendee, this.getLoginIP(), SysConstants.PORTAL_TYPE_WEB);
		} catch (ServiceException e) {
			errMsg = getText(e.getMessage());
			log.error("Portal用户登录失败：mobile:{},passwd:{},errCode:{}",
					new String[] { attendee.getMobile(), attendee.getPassword(), e.getMessage() });
			return INPUT;
		}

		//判断是不是会议成员
		MeetingMember mm = meetingMemberService.selectById(loginUser.getId(), meeting.getId());
		if(mm == null) {
			errMsg = this.getText("meeting.quickenter.user.not.meetingmember");
			return INPUT;
		}		
		// 将用户信息存入session
		this.putToSession(SessionKeeper.SESSION_USER, loginUser);
		
		return "enterMeeting";
	}
	
	/*
	 * 申请加会请求，跳转到加会界面
	 */
	public String applyMeetingReq() throws Exception {
		log.info("applyMeetingReq: meetingId=" + meeting.getId());
		meeting = this.meetingService.findById(meeting.getId());
		if(meeting == null) {
			return "message";
		}
		return SUCCESS;
	}
	
	/*
	 * 访客提出会议报名申请，如果该用户已经存在，则加入到会议中
	 * 否则既加入到会议中，同时也加入到用户表中
	 */
	public String applyMeeting() throws Exception {
		log.info("applyMeeting: meetingId" + this._meeting_id_ 
				+ ",gender:" + attendee.getGender()
				+ ",mobile:" + attendee.getMobile() + ",name:" + attendee.getName() 
				+ ",city:" + attendee.getMeetingMember().getCity() 
				+ ",job:" + attendee.getMeetingMember().getJob() 
				+ ",mailbox:" + attendee.getMeetingMember().getMailbox());

		if (StringUtil.isEmpty(this._meeting_id_)) {
			this.resultMap.put("result", 0);
			this.resultMap.put("message", "会议报名失败,会议号不能为空");
			return this.SUCCESS;
		}

		if (StringUtil.isEmpty(attendee.getMobile())) {
			this.resultMap.put("result", 0);
			this.resultMap.put("message", "会议报名失败, 手机号码不能为空");
			return this.SUCCESS;
		}

		if (StringUtil.isEmpty(attendee.getName())) {
			this.resultMap.put("result", 0);
			this.resultMap.put("message", "会议报名失败, 姓名不能为空");
			return this.SUCCESS;
		}
		
		// 进行会议是否存在的判断
		Meeting meeting = meetingService.findById(Long.valueOf(this._meeting_id_));
		if (meeting == null) {
			this.resultMap.put("result", 0);
			this.resultMap.put("message", "会议报名失败, 您所报名的会议不存在");
			return this.SUCCESS;
		}
		
		// 判断这个人是否已经在会议里了
		User user = userService.selectByMobile(this.attendee.getMobile());
		if (user != null) {
			MeetingMember member = meetingMemberService.selectById(
					user.getId(), Long.valueOf(this._meeting_id_));
			if (member != null) {
				this.resultMap.put("result", 0);
				this.resultMap.put("message", "无需报名本次会议，已经是会议成员了");
				return this.SUCCESS;
			}
		}
		
		//用户不存在，则把该用户存入到会议申请表中
		UserApply userApply = new UserApply();
		userApply.setMobile(attendee.getMobile());
		userApply.setName(attendee.getName());
		userApply.setMeetingId(Long.valueOf(this._meeting_id_));
		userApply.setCity(attendee.getMeetingMember().getCity());
		userApply.setJob(attendee.getMeetingMember().getJob());
		userApply.setMailbox(attendee.getMeetingMember().getMailbox());
		userApply.setGender(attendee.getGender());
		userApply.setCreateTime(new Date());
		
		userApplyService.add(userApply);
		
		this.resultMap.put("result", 1);
		this.resultMap.put("message", "会议报名成功,等待会务人员审批.");
		log.debug("会议报名成功,等待会务人员审批.");
		return SUCCESS;
	}

	public User getAttendee() {
		return attendee;
	}

	public void setAttendee(User attendee) {
		this.attendee = attendee;
	}

	public UserApplyService getUserApplyService() {
		return userApplyService;
	}

	public void setUserApplyService(UserApplyService userApplyService) {
		this.userApplyService = userApplyService;
	}

	public void setMeetingTypeService(MeetingTypeService meetingTypeService) {
		this.meetingTypeService = meetingTypeService;
	}

	public void setNewsService(NewsService newsService) {
		this.newsService = newsService;
	}

	public void setMeetingFilesAssortService(
			MeetingFilesAssortService meetingFilesAssortService) {
		this.meetingFilesAssortService = meetingFilesAssortService;
	}

	public void setMeetingFilesService(MeetingFilesService meetingFilesService) {
		this.meetingFilesService = meetingFilesService;
	}

	public void setMeetingAgendaService(MeetingAgendaService meetingAgendaService) {
		this.meetingAgendaService = meetingAgendaService;
	}

	public void setGroupPlanService(GroupPlanService groupPlanService) {
		this.groupPlanService = groupPlanService;
	}

	public void setMeetingDinnerService(MeetingDinnerService meetingDinnerService) {
		this.meetingDinnerService = meetingDinnerService;
	}

	public void setPageCustomService(PageCustomService pageCustomService) {
		this.pageCustomService = pageCustomService;
	}

	public AdminUserService getAdminUserService() {
		return adminUserService;
	}

	public void setAdminUserService(AdminUserService adminUserService) {
		this.adminUserService = adminUserService;
	}

	public Long[] getMeetingAdmins() {
		return meetingAdmins;
	}

	public void setMeetingAdmins(Long[] meetingAdmins) {
		this.meetingAdmins = meetingAdmins;
	}
	
	/**
	 * 以下是实现会议向导功能
	 * added by zhangguojing at 2012/3/30
	 */
	
	/*
	 * 会议向导开始界面
	 */
	public String begin() throws Exception {
		return "begin";
	}

	/*
	 * 会议向导第1步
	 */
	public String step1() throws Exception {
		//后台添加会议信息，则初始化基本类型列表
		List<MeetingType> meetingTypeList = this.meetingTypeService.findAll();
		this.setAttribute("meetingTypeList", meetingTypeList);
		
		// 查询出creator所属机构下的会务人员列表
		Long orgId = null;
		Set<AdminUser> selectedAdmins = null;
		if (meeting != null) {
			if(meeting.getCreator()==null){
				meeting=this.meetingService.findById(meeting.getId());
			}
			orgId = meeting.getCreator().getOrg().getId();
			selectedAdmins = meeting.getMeetingAdmins();
		} else {
			AdminUser sessionUser = this.getAdminUserFromSession();
			orgId = sessionUser.getOrg().getId();
		}
		
		// 标记选择的会务人员
		List<AdminUser> meetingAdmins = adminUserService.getAdminUsersByOrg(orgId, SysConstants.MEETING_ADMIN);
		if (selectedAdmins != null && selectedAdmins.size() > 0) {
			for (AdminUser meetingAdmin : meetingAdmins) {
				for (AdminUser selectedAdmin : selectedAdmins) {
					if (meetingAdmin.getId().equals(selectedAdmin.getId())) {
						meetingAdmin.setSelected(true);
						break;
					}
				}
			}
		}
		this.setAttribute("meetingAdmins", meetingAdmins);
		
		String fromStep = getRequest().getParameter("fromStep");
		if("step2".equals(fromStep)) {
			Meeting meeting = meetingService.getMeetingByPk(this.meeting.getId());
			this.setAttribute("meeting", meeting);
			this.setAttribute("fromStep", fromStep);
		}
		return "step1";
	}
	
	/*
	 * 会议向导第2步
	 */
	public String step2() throws Exception {
		Meeting meeting = meetingService.getMeetingByPk(Long.valueOf(this.meeting.getId()));
		this.setAttribute("meeting", meeting);
		this.setAttribute("meetingId", meeting.getId());
		this.setAttribute("retcode", 0);
		return "step2";
	}

	/*
	 * 会议向导第3步
	 */
	public String step3() throws Exception {
		Meeting meeting = meetingService.getMeetingByPk(Long.valueOf(this.meeting.getId()));
		this.setAttribute("meeting", meeting);
		this.setAttribute("meetingId", meeting.getId());
		return "step3";
	}
	
	/*
	 * 会议向导第4步
	 */
	public String step4() throws Exception {
		Pager<MeetingFilesAssort>  pager= meetingFilesAssortService.getMeetingFilesAssortPages(1, 1000, this.meeting.getId(),null);
		Meeting meeting = meetingService.getMeetingByPk(Long.valueOf(this.meeting.getId()));
		
		this.setAttribute("meeting", meeting);
		this.setAttribute("meetingId", meeting.getId());
		this.setAttribute("meetingFilesAssortLst", pager.getPageRecords());
		
		MeetingFiles mfile = new MeetingFiles();
		mfile.setMeetingId(meeting.getId().intValue());
		List<MeetingFiles> meetingFilesList = this.meetingFilesService.getMeetingFilesList(mfile, null);
		this.setAttribute("meetingFilesList", meetingFilesList);
		return "step4";
	}
	
	
	/*
	 * 会议向导第5步
	 */
	public String step5() throws Exception {
		Meeting meeting = meetingService.getMeetingByPk(Long.valueOf(this.meeting.getId()));
		this.setAttribute("meeting", meeting);
		this.setAttribute("meetingId", meeting.getId());
		return "step5";
	}
	
	/*
	 * 会议向导第6步
	 */
	public String step6() throws Exception {
		//设置用餐类型信息
		getRequest().setAttribute("dinnerTypeList", Constants.dinnerTypeList);
		getRequest().setAttribute("dinnerTypeNameMap", Constants.dinnerTypeNameMap);
		getRequest().setAttribute("dinnerTypeIdMap", Constants.dinnerTypeIdMap);
		
		Meeting meeting = meetingService.getMeetingByPk(Long.valueOf(this.meeting.getId()));
		this.setAttribute("meeting", meeting);
		this.setAttribute("meetingId", meeting.getId());
		return "step6";
	}
	
	
	/*
	 * 会议向导第6步
	 */
	public String step7() throws Exception {
		
		MeetingPostAction postAction=new MeetingPostAction();
		final String meetingNo = getParameter("meetingId");
		getRequest().setAttribute("meetingId", meetingNo);
		try {
			final MeetingPost meetingPost = new MeetingPost();
			meetingPost.setMeetingId(Long.valueOf(meetingNo));
			final Pager<MeetingPost> pager = meetingPostService.queryPagerByMeetingId(meetingPost, currentPage,
					pageSize);
			List<MeetingPost> posts = pager.getPageRecords();
			for (MeetingPost post : posts) {
				post.setContent(StringUtil.convertTextAreaNewLine(postAction.convertEmotions(post.getContent())));
				if (post.getPosterType() == 0) {
					final User user = meetingPostService.getBussinessCardInfo(post.getMeetingId(), post.getPosterId());
					post.setUser(user);
					if (user != null) {
						post.setPosterName(user.getName());
						post.setPosterJob(postAction.getJob(user));
					}
				}
				if (post.getCommentCount() > 0) {
					for (Iterator<MeetingComment> it = post.getComments().iterator(); it.hasNext();) {
						MeetingComment comment = it.next();
						comment.setContent(StringUtil.convertTextAreaNewLine(postAction.convertEmotions(comment.getContent())));
						if (comment.getCreatorType() == 0) {
							final User user = meetingPostService.getBussinessCardInfo(post.getMeetingId(), comment.getCreator());
							comment.setUser(user);
							if (user != null) {
								comment.setCreatorName(user.getName());
								comment.setCreatorJob(postAction.getJob(user));
							}
						}
					}
				}
			}
			pager.setPageRecords(posts);
			getRequest().setAttribute("pager", pager);
			getRequest().setAttribute("emotions", Constants.emotions);
			
	
			return "step7";
		} catch (ServiceException e) {
			e.printStackTrace();
			return ERROR;
		}
	
		
		
		
		
	}
	
	/*
	 * 会议向导第4步
	 */
	public String finish() throws Exception {
		this.setAttribute("fromStep", "finish");
		return "finish";
	}
	

	
	/**
	 * 统计资料完成度
	 * @author zouxiaoming
	 * @return
	 * @throws HibernateDaoSupportException 
	 */
	public String staticsProcess(){
		float count=1.00f;
		List list=null;
		log.info(meetingId.toString());
		try {
			//统计参会人员是否完成
			list=this.meetingMemberService.queryMemberList(meetingId);
			if(list!=null&&list.size()>0){
				resultMap.put("info1", "true");
				count++;
			}
			
			//统计会议议程是否完成
			list=this.meetingAgendaService.getNewestAgenda(meetingId);
			if(list!=null&&list.size()>0){
				resultMap.put("info2", "true");
				count++;
			}
			
			//统计会议资料是否完成
			list=this.meetingFilesService.findMeetingFiles(meetingId);
			if(list!=null&&list.size()>0){
				resultMap.put("info3", "true");
				count++;
			}
			
			//信息发布
			Pager<News> newsPager = this.newsService.findAllNewsPager(meetingId, null, 1, 7);
			if(newsPager.getTotal() > 0) {
				resultMap.put("info_news", "true");
				count++;
			}
			
			
			//用餐信息
			list = meetingDinnerService.findDinnerList(null, meetingId, null);
			if(list.size() > 0) {
				resultMap.put("info_dinner", "true");
				count++;
			}
			
			//互动交流
			list=meetingPostService.queryListByMeetingId(meetingId);
			if(list.size() > 0) {
				resultMap.put("info_post", "true");
				count++;
			}
			//分组
			list=groupPlanService.listByMeetingId(meetingId);
			if(list.size() > 0) {
				resultMap.put("info_group", "true");
				//count++;
			}
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		double result=count/7.00;
		
		
		BigDecimal bd=new BigDecimal(result);
		
		BigDecimal bdGetValue= bd.multiply(new BigDecimal("100")).setScale(2, BigDecimal.ROUND_HALF_UP);
		
		resultMap.put("result",String.valueOf(bdGetValue).concat("%"));
		return "process";
	}
	
	
	/**
	 * 会议预览
	 * @author zouxiaoming
	 * @Time   2012/08/17
	 * @return
	 * @throws ServiceException 
	 */
	public String preView() throws ServiceException{
		//查询会议基本信息
		meeting=this.meetingService.getMeetingByPk(meetingId);
		super.getContext().put("meeting",meeting);
		
		
		//查询参会人员信息
		Pager<User> userList = userService.findMeetingUserPager(meetingId,new User(), 1, 10000,"1");
		super.getContext().put("userList", userList);
		
		//会议议程
		List<MeetingAgenda> agendaList=this.meetingAgendaService.getNewestAgenda(meetingId);
		super.getContext().put("agendaList",agendaList);
		
		//会议资料
		List<MeetingFiles> fileList=this.meetingFilesService.findMeetingFiles(meetingId);
		super.getContext().put("fileList", fileList);
		
		//用餐信息
		List<MeetingDinner> dinnerList = meetingDinnerService.findDinnerList(null, meetingId, null);
		super.getContext().put("dinnerTypeIdMap", Constants.dinnerTypeIdMap);
		super.getContext().put("dinnerList", dinnerList);
		
		return "preView";
	}

	public void setMeetingId(Long meetingId) {
		this.meetingId = meetingId;
	}

	public Long getMeetingId() {
		return meetingId;
	}
}




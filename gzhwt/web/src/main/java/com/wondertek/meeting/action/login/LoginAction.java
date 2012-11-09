package com.wondertek.meeting.action.login;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.wondertek.meeting.Consts;
import com.wondertek.meeting.action.base.BaseAction;
import com.wondertek.meeting.client.view.MeetingModuleTitleView;
import com.wondertek.meeting.client.view.MeetingView;
import com.wondertek.meeting.client.view.MenuView;
import com.wondertek.meeting.common.Constants;
import com.wondertek.meeting.common.RetCode;
import com.wondertek.meeting.common.SessionKeeper;
import com.wondertek.meeting.common.SysConstants;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.manager.SmsSender;
import com.wondertek.meeting.model.ClientMenu;
import com.wondertek.meeting.model.DataDictConfig;
import com.wondertek.meeting.model.Meeting;
import com.wondertek.meeting.model.MeetingMember;
import com.wondertek.meeting.model.MeetingModuleTitle;
import com.wondertek.meeting.model.User;
import com.wondertek.meeting.model.custom.PageCustom;
import com.wondertek.meeting.service.AdminUserService;
import com.wondertek.meeting.service.ClientMenuService;
import com.wondertek.meeting.service.MeetingMemberService;
import com.wondertek.meeting.service.MeetingModuleTitleService;
import com.wondertek.meeting.service.MeetingService;
import com.wondertek.meeting.service.TagService;
import com.wondertek.meeting.service.UserService;
import com.wondertek.meeting.service.custom.PageCustomService;
import com.wondertek.meeting.util.Encrypt;
import com.wondertek.meeting.util.RandomUtil;
import com.wondertek.meeting.util.StringUtil;

/**
 * 登录
 * 
 * @author 金祝华
 */
public class LoginAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3102151360541524276L;

	private UserService userService;
	private AdminUserService adminUserService;

	// 会议成员
	private MeetingMemberService meetingMemberService;

	private ClientMenuService clientMenuService;

	private MeetingService meetingService;
	private PageCustomService pageCustomService;

	private User u;
	
	private Meeting meeting;

	TagService tagService;

	public TagService getTagService() {
		return tagService;
	}

	public void setTagService(TagService tagService) {
		this.tagService = tagService;
	}

	private MeetingModuleTitleService meetingModuleTitleService;

	public void setMeetingModuleTitleService(MeetingModuleTitleService meetingModuleTitleService) {
		this.meetingModuleTitleService = meetingModuleTitleService;
	}

	/**
	 * 加入会议
	 * 
	 * @return
	 */
	public String enterMeeting() {
		String meetingId = this.getParameter("meetingId");// 会议编码

		if (StringUtil.isEmpty(meetingId)) {
			resultMap.put("retcode", RetCode.PARAMS_INVALID);
			resultMap.put("retmsg", getText("user.enter.meeting.invalid.meetingId"));
			return SUCCESS;
		}

		Long mId = 0L;
		try {
			mId = Long.parseLong(meetingId);
		} catch (NumberFormatException ex) {
			resultMap.put("retcode", RetCode.PARAMS_INVALID);
			resultMap.put("retmsg", getText("user.enter.meeting.invalid.meetingId"));
			return SUCCESS;
		}

		Meeting meeting = null;
		try {
			meeting = meetingService.findById(mId);
		} catch (ServiceException e) {
			resultMap.put("retcode", RetCode.UNKOWN_WRONG);
			resultMap.put("retmsg", "未知异常。");

			return SUCCESS;
		}

		if (meeting == null) {
			resultMap.put("retcode", "101");
			resultMap.put("retmsg", "对不起，您输入的会议不存在。");

			return SUCCESS;
		}

		// 进入会议成功
		resultMap.put("retcode", RetCode.SUCCESS);
		resultMap.put("retmsg", "进入会议成功。");

		return SUCCESS;
	}

	/**
	 * 获取密码
	 * 
	 * @return
	 */
	public String getPwd() {
		// 过滤特殊字符串
		String mobile = StringUtil.replaceBlank(this.getParameter("username"));// 手机号码
		// 1.检查参数合法性
		if (StringUtil.isEmpty(mobile)) {
			resultMap.put("retcode", RetCode.PARAMS_INVALID);
			resultMap.put("retmsg", "您输入的手机号码为空。");
			return SUCCESS;
		}

		// 判断当前手机用户是否存在
		User transientUser = userService.selectByMobile(mobile);
		if (transientUser == null) {
			resultMap.put("retcode", "101");
			resultMap.put("retmsg", "对不起，您输入的用户不存在。");
			return SUCCESS;
		}

		// 默认为S找回原来的密码
		String passwordIsDramic = "S";
		// 查询数据库配置,是否动态
		DataDictConfig queryDc = new DataDictConfig();
		queryDc.setDataType("find_back_password");
		queryDc.setActType("common");
		List<DataDictConfig> dataDictConfigList = tagService.query(queryDc);

		// 如果数据库中有配置,则采用数据库的配置
		if (dataDictConfigList != null && dataDictConfigList.size() > 0) {
			passwordIsDramic = dataDictConfigList.get(0).getDataValue();
			log.info("dataDictConfigList act size is:" + dataDictConfigList.size() + ",passwordIsDramic:"
					+ passwordIsDramic);
		}

		String pwd = "";
		if ("D".equals(passwordIsDramic)) {
			// 重新生成密码
			String randomPasswordDes = RandomUtil.getRandomCode();
			if (transientUser != null) {
				transientUser.setPassword(Encrypt.encrypt(randomPasswordDes, null));
				transientUser.setModifyTime(new Date());
				try {
					userService.saveOrUpdate(transientUser);
				} catch (ServiceException e) {
					log.info("保存user实体报错,在更新密码的时候。");
					e.printStackTrace();
				}
				// 把最新密码发出
				pwd = randomPasswordDes;
			}
		} else {
			pwd = userService.findPwdByMobile(mobile);
			// 如果配置为S
			pwd = Encrypt.decrypt(pwd, null);
		}

		if (StringUtil.isEmpty(pwd)) {
			resultMap.put("retcode", "101");
			resultMap.put("retmsg", "对不起，你的密码在系统中为空,请确认!");
			return SUCCESS;
		}

		log.info("找回密码功能:MobileNo:" + mobile + ",password:" + pwd);

		// 下发短信
		String[] args = { pwd };
		try{
			SmsSender.sendSms(mobile, getText("user.getPwd.message", args));
		}catch(Exception ex){
			log.error(ex.getMessage());
		}
		
		// 进入会议成功
		resultMap.put("retcode", RetCode.SUCCESS);
		resultMap.put("retmsg", "您的动态密码已发送到您的手机，请查收。");
		resultMap.put("password",pwd );

		return SUCCESS;
	}

	/**
	 * 申请加入会议
	 * 
	 * @return
	 */
	public String applyJoinMeeting() {
		return SUCCESS;
	}

	/**
	 * 获取客户端首页九宫格菜单列表
	 * 
	 * @return
	 */
	public String getMenuList() {
		String meetingId = this.getParameter("meetingId");// 会议编码
		String menuType = this.getParameter("menuType");// 菜单类型，1、www ；
														// 2、wap；空：从手机客户端反馈
		menuType = (menuType == null || menuType.equals("")) ? SysConstants.MENU_TERMINAL : menuType;

		// 1.检查参数合法性
		if (StringUtil.isEmpty(meetingId)) {
			resultMap.put("retcode", RetCode.PARAMS_INVALID);
			resultMap.put("retmsg", getText("user.enter.meeting.invalid.meetingId"));
			return SUCCESS;
		}

		Long mId = 0L;
		try {
			mId = Long.parseLong(meetingId);
		} catch (NumberFormatException ex) {
			resultMap.put("retcode", RetCode.PARAMS_INVALID);
			resultMap.put("retmsg", getText("user.enter.meeting.invalid.meetingId"));
			return SUCCESS;
		}

		getSession().setAttribute(SessionKeeper.CURRENT_MEETING_ID, mId);

		Long userId = this.getUserIdFromSession();

		// 查询用户等级
		MeetingMember meetingMember = meetingMemberService.selectById(userId, mId);
		if (meetingMember == null) {
			resultMap.put("retcode", RetCode.FAIL);
			resultMap.put("retmsg", "获取菜单列表失败，用户不在所参加的会议。");
			return SUCCESS;
		}
		
		// 查询客户端首页要展示的菜单列表
		List<ClientMenu> menuList = clientMenuService.queryMenuList(mId, meetingMember.getMemberLevel(), menuType);
		//客户端PICTURE访问地址
		for(ClientMenu menu:menuList){
			if(StringUtil.isNotEmpty(menu.getImgUrl())){
				menu.setImgUrl(this.getServerUrl()+menu.getImgUrl());
			}
		}
		
		if (menuList == null) {
			resultMap.put("retcode", RetCode.FAIL);
			resultMap.put("retmsg", "获取菜单列表失败。");
			return SUCCESS;
		}

		String notice = null;
		try {
			Meeting meeting = meetingService.findById(mId);
			notice = meeting.getNotice();
		} catch (ServiceException e) {
			log.error("", e);
		}

		//
		MeetingModuleTitle meetingModuleTitle = new MeetingModuleTitle();
		meetingModuleTitle.setMeetingId(mId);
		meetingModuleTitle.setTerminalType(SysConstants.MENU_TERMINAL);
		List<MeetingModuleTitle> meetingModuleTitleList = meetingModuleTitleService.query(meetingModuleTitle);
		// 登录成功
		Map<String, Object> retdata = new HashMap<String, Object>();
		retdata.put("menuList", convertToView(menuList));
		retdata.put("titleList", convertMeetingModuleTitleToView(meetingModuleTitleList));
		
		resultMap.put("retcode", RetCode.SUCCESS);
		resultMap.put("retmsg", "获取菜单列表成功。");
		if(StringUtil.isNotEmpty(notice)){
			resultMap.put("notice", notice);
		}else{
			resultMap.put("notice", "欢迎参加会议");
		}
		

		resultMap.put("job", meetingMember.getJob());
		resultMap.put("jobIsDisplay", meetingMember.getJobIsDisplay());
		resultMap.put("retdata", retdata);
		//文件上传权限控制
		resultMap.put("uploadAuthority", meetingMember.getUploadAuthority());
		

		return SUCCESS;
	}

	/**
	 * 获取触屏版菜单列表
	 * 
	 * @return
	 */
	public String getTouchMenuList() {

		Long userId = this.getUserIdFromSession();

		// 查询用户等级
		MeetingMember meetingMember = meetingMemberService.selectById(userId, meeting.getId());
		if (meetingMember == null) {
			return SUCCESS;
		}

		// 查询客户端首页要展示的菜单列表
		List<ClientMenu> menuList = clientMenuService.queryMenuList(meeting.getId(), meetingMember.getMemberLevel(),
				SysConstants.MENU_TOUCH);

		this.getRequest().setAttribute("menuList", menuList);
		return SUCCESS;
	}
	
	/**
	 * 客户端登录
	 * 
	 * @return
	 */
	public String clientLogin() {
		String mobile = this.getParameter("username");// 手机号码
		String passwd = this.getParameter("password");// 密码
		String imei = this.getParameter("imei");// 手机imei码

		log.info("客户端用户登录：mobile:{},passwd:{},imei:{}", new String[] { mobile, passwd, imei });

		// 1.检查参数合法性
		if (StringUtil.isEmpty(mobile)) {
			resultMap.put("retcode", RetCode.PARAMS_INVALID);
			resultMap.put("retmsg", getText("user.login.params.invalid.mobile"));
			return SUCCESS;
		}

		if (!StringUtil.isMobile(mobile)) {
			errMsg = "请输入11位正确的手机号码。";
			resultMap.put("retcode", RetCode.PARAMS_INVALID);
			resultMap.put("retmsg", errMsg);
			return SUCCESS;
		}

		if (StringUtil.isEmpty(passwd)) {
			resultMap.put("retcode", RetCode.PARAMS_INVALID);
			resultMap.put("retmsg", getText("user.login.params.invalid.passwd"));
			return SUCCESS;
		}

		if (StringUtil.isEmpty(imei)) {
			log.error("客户端登陆imei为空!");
		}

		User user = new User();
		user.setMobile(mobile);
		user.setPassword(passwd);
		user.setImei(imei);

		// 2.登录
		User loginUser = null;
		try {
			loginUser = userService.clientLogin(user, this.getLoginIP());
		} catch (ServiceException e) {
			String errCode = e.getMessage();
			log.error("客户端用户登录失败：mobile:{},passwd:{},imei:{},errCode:{}",
					new String[] { mobile, passwd, imei, errCode });
			resultMap.put("retcode", RetCode.FAIL);
			resultMap.put("retmsg", getText(errCode));
			return SUCCESS;
		} catch (Throwable t) {
			String errCode = t.getMessage();
			log.error("客户端用户登录失败：mobile:{},passwd:{},imei:{},errCode:{}",
					new String[] { mobile, passwd, imei, errCode });
			resultMap.put("retcode", RetCode.FAIL);
			resultMap.put("retmsg", "对不起，系统忙，请稍后再试。");
			return SUCCESS;
		}

		// 将用户信息存入session
		this.putToSession(SessionKeeper.SESSION_USER, loginUser);

		// 查询会议列表
		List<Meeting> meetingList = meetingService.getMyAttendMeetingList(String.valueOf(loginUser.getId()));

		// 登录成功
		Map<String, Object> retdata = new HashMap<String, Object>();
		retdata.put("sessionid", getSession().getId());
		retdata.put("meetingList", convertToMeetingView(meetingList));
		
		//重置密码
		this.resetUserPassword(mobile);

		resultMap.put("retcode", RetCode.SUCCESS);
		resultMap.put("retmsg", getText("user.login.success"));
		resultMap.put("name", loginUser.getName());
		resultMap.put("retdata", retdata);

		return SUCCESS;
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

	private List<MenuView> convertToView(List<ClientMenu> clientMenuList) {
		List<MenuView> views = new ArrayList<MenuView>();

		for (ClientMenu menu : clientMenuList) {

			MenuView view = new MenuView();
			view.setId(menu.getId());
			view.setType(menu.getType());
			view.setName(menu.getName());
			view.setImgUrl(menu.getImgUrl());
			/*
			if (Constants.CLIENT_MENU_TYPE.CUSTOME.equalsIgnoreCase(menu.getMenuType())) {
				// 自定义菜单需要将图片访问的相对路径转换为绝对路径
				view.setImgUrl(getServerUrl() + menu.getImgUrl());
			} else {
				view.setImgUrl(menu.getImgUrl());
			}*/
			view.setContentUrl(menu.getContentUrl());
			view.setDownloadUrl(menu.getDownloadUrl());
			view.setPackageName(menu.getPackageName());

			views.add(view);
		}

		return views;
	}

	private List<MeetingModuleTitleView> convertMeetingModuleTitleToView(List<MeetingModuleTitle> meetingModuleTitleList) {
		List<MeetingModuleTitleView> views = new ArrayList<MeetingModuleTitleView>();

		for (MeetingModuleTitle mmt : meetingModuleTitleList) {

			MeetingModuleTitleView view = new MeetingModuleTitleView();
			view.setKeyName(mmt.getKeyName());
			view.setTitleName(mmt.getTitleName());
			views.add(view);
		}

		return views;
	}

	/**
	 * web门户登录
	 * 
	 * @return
	 * @deprecated
	 */
	@Deprecated
	public String webLogin() {

		String mobile = this.getParameter("mobile");// 手机号码
		String passwd = this.getParameter("password");// 密码
		String meetingId = this.getParameter("meetingId");// 会议编码

		Long mId = 0L;
		try {
			mId = Long.parseLong(meetingId);
		} catch (NumberFormatException ex) {
			resultMap.put("result", false);
			resultMap.put("message", getText("user.enter.meeting.invalid.meetingId"));
			return SUCCESS;
		}

		User user = new User();
		user.setMobile(mobile);
		user.setPassword(passwd);

		// 2.登录
		User loginUser = null;
		try {
			loginUser = userService.login(user, this.getLoginIP(), SysConstants.PORTAL_TYPE_WEB);
		} catch (ServiceException e) {
			String errCode = e.getMessage();
			resultMap.put("result", false);
			resultMap.put("message", getText(errCode));
			return SUCCESS;
		}

		// 3.判断是否是会议成员
		MeetingMember meetingMember = meetingMemberService.selectById(loginUser.getId(), mId);
		if (meetingMember == null) {
			resultMap.put("result", false);
			resultMap.put("message", getText("user.login.fail.no.member"));
			return SUCCESS;
		}

		// 将用户信息存入session
		this.putToSession(SessionKeeper.SESSION_USER, loginUser);

		// 修改验证码
		this.putToSession(SessionKeeper.CheckCode, new Random().nextInt(9999));

		// 登录成功
		resultMap.put("result", true);
		resultMap.put("message", getText("user.login.success"));
		return SUCCESS;
	}
	
	/**
	 * web门户登录跳转请求，用来准备登录页面中所需要的资料
	 * added by zhangguojing at 2012/3/26
	 */
	public String goPortalLogin() throws Exception {
		if(meeting != null && meeting.getId() != null) {
			//处理会议logo
			PageCustom custom = this.pageCustomService.getPageCustom(meeting.getId(), Consts.PAGE_CUSTOM_PORTAL_LOGO_IMAGE);
			if(custom != null) {
				String imageUrl = custom.getPropertyValue();
				this.setAttribute("logoImageUrl", imageUrl);
			}
			
			//处理登录背景图片
			custom = this.pageCustomService.getPageCustom(meeting.getId(), Consts.PAGE_CUSTOM_PORTAL_LOGIN_IMAGE);
			if(custom != null) {
				String imageUrl = custom.getPropertyValue();
				this.setAttribute("loginImageUrl", imageUrl);
			}
		}
		
		return "portal_login";
	}

	/**
	 * web门户登录
	 * 
	 * @return
	 */
	public String login() {

		if (u == null) {
			errMsg = "请输入手机号码和密码";
			return INPUT;
		}

		String flag=getRequest().getParameter("flag");
		this.getRequest().setAttribute("flag", flag);
		// 2.登录
		User loginUser = null;
		try {
			loginUser = userService.login(u, this.getLoginIP(), SysConstants.PORTAL_TYPE_WEB);
		} catch (ServiceException e) {
			errMsg = getText(e.getMessage());
			log.error("Portal用户登录失败：mobile:{},passwd:{},errCode:{}",
					new String[] { u.getMobile(), u.getPassword(), e.getMessage() });
			return INPUT;
		}

		// 将用户信息存入session
		this.putToSession(SessionKeeper.SESSION_USER, loginUser);
		//重置密码
		this.resetUserPassword(u.getMobile());
		
		//判断是否在登录时提供会议编号，如果有有效的会议编号，则直接进入会议中
		if(meeting != null && meeting.getId() != null) {
			Meeting mt = null;
			try {
				mt = this.meetingService.findById(meeting.getId());
			} catch (ServiceException e) {
				e.printStackTrace();
			}
			if(mt != null) {
				return "meeting";
			}
		}
		return "portal";
	}

	/**
	 * 取菜单信息
	 * 
	 * @return
	 */
	public String listMenu() {

		Object menuList = this.getFromSession("menuList");

		resultMap.put("result", true);
		resultMap.put("menuList", menuList);
		return SUCCESS;
	}

	/**
	 * 注销
	 * 
	 * @return
	 */
	public String logout() {
		// User user = (User)
		// this.getSession().getAttribute(SessionKeeper.SESSION_USER);
		//
		// if (user == null) {
		// return "portal";
		// }

		getSession().removeAttribute(SessionKeeper.SESSION_USER);

		return "portal";
	}

	/**
	 * wap门户登录
	 * 
	 * @return
	 */
	public String wapLogin() {

		if (u == null) {
			errMsg = "请输入手机号码和密码。";
			return INPUT;
		}

		if (StringUtil.isEmpty(u.getMobile())) {
			errMsg = "请输入手机号码。";
			return INPUT;
		}

		Matcher m = Pattern.compile("^1[0-9]{10}$").matcher(u.getMobile());

		if (!m.find()) {
			errMsg = "请输入11位正确的手机号码。";
			return INPUT;
		}

		if (StringUtil.isEmpty(u.getPassword())) {
			errMsg = "请输入密码。";
			return INPUT;
		}

		// 2.登录
		User loginUser = null;
		try {
			loginUser = userService.login(u, this.getLoginIP(), SysConstants.PORTAL_TYPE_WAP);
		} catch (ServiceException e) {
			errMsg = getText(e.getMessage());
			return INPUT;
		}

		// 将用户信息存入session
		this.putToSession(SessionKeeper.SESSION_USER, loginUser);
		//重置密码
		this.resetUserPassword(u.getMobile());
		
		//判断是否在登录时提供会议编号，如果有有效的会议编号，则直接进入会议中
		if(meeting != null && meeting.getId() != null) {
			Meeting mt = null;
			try {
				mt = this.meetingService.findById(meeting.getId());
			} catch (ServiceException e) {
				e.printStackTrace();
			}
			if(mt != null) {
				return "meeting";
			}
		}
				
		return SUCCESS;
	}
	
	/**
	 * 触摸屏门户登录
	 * 
	 * @return
	 */
	public String touchLogin() {

		if (u == null) {
			errMsg = "请输入手机号码和密码。";
			return INPUT;
		}

		if (StringUtil.isEmpty(u.getMobile())) {
			errMsg = "请输入手机号码。";
			return INPUT;
		}

		Matcher m = Pattern.compile("^1[0-9]{10}$").matcher(u.getMobile());

		if (!m.find()) {
			errMsg = "请输入11位正确的手机号码。";
			return INPUT;
		}

		if (StringUtil.isEmpty(u.getPassword())) {
			errMsg = "请输入密码。";
			return INPUT;
		}

		// 2.登录
		User loginUser = null;
		try {
			loginUser = userService.login(u, this.getLoginIP(), SysConstants.PORTAL_TYPE_WAP);
		} catch (ServiceException e) {
			errMsg = getText(e.getMessage());
			return INPUT;
		}

		// 将用户信息存入session
		this.putToSession(SessionKeeper.SESSION_USER, loginUser);
		//重置密码
		this.resetUserPassword(u.getMobile());
		
		//判断是否在登录时提供会议编号，如果有有效的会议编号，则直接进入会议中
		if(meeting != null && meeting.getId() != null) {
			Meeting mt = null;
			try {
				mt = this.meetingService.findById(meeting.getId());
			} catch (ServiceException e) {
				e.printStackTrace();
			}
			if(mt != null) {
				return "meeting";
			}
		}
				
		return SUCCESS;
	}


	/**
	 * 注销
	 * 
	 * @return
	 */
	public String wapLogout() {

		getSession().removeAttribute(SessionKeeper.SESSION_USER);

		return SUCCESS;
	}

	/**
	 * 获取密码
	 * 
	 * @return
	 */
	public String wapGetPwd() {
		if (u == null) {
			errMsg = "请输入手机号码和密码";
			return INPUT;
		}

		String mobile = u.getMobile();// 手机号码

		// 1.检查参数合法性
		if (StringUtil.isEmpty(mobile)) {
			errMsg = "您输入的手机号码为空。";
			return INPUT;
		}

		String pwd = userService.findPwdByMobile(mobile);

		if (StringUtil.isEmpty(pwd)) {
			errMsg = "对不起，您输入的用户不存在。";

			return INPUT;
		}

		
		User transientUser = userService.selectByMobile(mobile);
		// 重新生成密码
		String randomPasswordDes = RandomUtil.getRandomCode();
		if (transientUser != null) {
			transientUser.setPassword(Encrypt.encrypt(randomPasswordDes, null));
			transientUser.setModifyTime(new Date());
			try {
				userService.saveOrUpdate(transientUser);
			} catch (ServiceException e) {
				log.info("保存user实体报错,在更新密码的时候。");
				e.printStackTrace();
			}
			// 把最新密码发出
			pwd = randomPasswordDes;
		}
	

		// 下发短信
		String[] args = { pwd };
		try{
			SmsSender.sendSms(mobile, getText("user.getPwd.message", args));
		}catch(Exception ex){
			log.error(ex.getMessage());
		}
		
		// 进入会议成功
		errMsg = "您的密码已发送到您的手机，请查收。";
		
		this.setAttribute("u", transientUser);
		log.info("wap获取的最新动态密码为:"+randomPasswordDes);

		return SUCCESS;
	}
	
	/**
	 * 重置密码,当每次登录成功后
	 * @param mobile
	 */
	private void resetUserPassword(String mobile){

		User transientUser = userService.selectByMobile(mobile);
		// 重新生成密码
		String randomPasswordDes = RandomUtil.getRandomCode();
		if (transientUser != null) {
			transientUser.setPassword(Encrypt.encrypt(randomPasswordDes, null));
			transientUser.setModifyTime(new Date());
			try {
				userService.saveOrUpdate(transientUser);
			} catch (ServiceException e) {
				log.info("保存user实体报错,在更新密码的时候。");
				e.printStackTrace();
			}
		}	 
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public MeetingMemberService getMeetingMemberService() {
		return meetingMemberService;
	}

	public void setMeetingMemberService(MeetingMemberService meetingMemberService) {
		this.meetingMemberService = meetingMemberService;
	}

	public ClientMenuService getClientMenuService() {
		return clientMenuService;
	}

	public void setClientMenuService(ClientMenuService clientMenuService) {
		this.clientMenuService = clientMenuService;
	}

	public MeetingService getMeetingService() {
		return meetingService;
	}

	public void setMeetingService(MeetingService meetingService) {
		this.meetingService = meetingService;
	}

	public AdminUserService getAdminUserService() {
		return adminUserService;
	}

	public void setAdminUserService(AdminUserService adminUserService) {
		this.adminUserService = adminUserService;
	}

	public User getU() {
		return u;
	}

	public void setU(User u) {
		this.u = u;
	}

	public Meeting getMeeting() {
		return meeting;
	}

	public void setMeeting(Meeting meeting) {
		this.meeting = meeting;
	}

	public void setPageCustomService(PageCustomService pageCustomService) {
		this.pageCustomService = pageCustomService;
	}
}

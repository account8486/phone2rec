package com.wondertek.meeting.action.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import com.wondertek.meeting.action.base.BaseAction;
import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.common.RetCode;
import com.wondertek.meeting.common.SessionKeeper;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.filter.OnlineUserListener;
import com.wondertek.meeting.model.Organization;
import com.wondertek.meeting.model.Role;
import com.wondertek.meeting.model.User;
import com.wondertek.meeting.service.OrganizationService;
import com.wondertek.meeting.service.RoleService;
import com.wondertek.meeting.service.UserService;
import com.wondertek.meeting.util.Encrypt;
import com.wondertek.meeting.util.StringUtil;

/**
 * 后台用户管理
 * 
 * @author 金祝华
 */
public class UserAction extends BaseAction {

	private static final long serialVersionUID = -3102151360541524276L;

	private UserService userService; // 用户
	private OrganizationService organizationService; // 机构
	private RoleService roleService; // 角色

	private User user; // 修改用户时接受参数

	private Set<Long> roles; // 设置角色时接受页面参数

	String oldPass;
	String pass;
	String passAg;
	
	private Long meetingId;

	/**
	 * 查询用户列表
	 * 
	 * @return
	 */
	public String list() {

		if (user == null) {
			user = new User();
		}

		Pager<User> userPager = null;
		try {
			userPager = userService.findUserPager(user, currentPage, pageSize);
		} catch (ServiceException e) {
			log.error("query user list error: " + e.toString());
		}

		this.getRequest().setAttribute("pager", userPager);

		return SUCCESS;
	}

	/**
	 * 跳转到新增用户
	 * 
	 * @return
	 */
	public String goAdd() {

		// 查询机构列表供添加用户使用
		List<Organization> orgList = null;
		String from = this.getParameter("from");
		try {
			// 从portal跳转过来的，需要接受org id字段
			if ("portal".equals(from)) {
				String org_id = this.getParameter("org_id");
				log.debug("org_id============" + org_id);
				Organization org = organizationService.findById(Long.parseLong(org_id));
				log.debug("org_ is============" + org);
				this.getRequest().setAttribute("_organization_", org);
				String meeting_id = getParameter("meeting_id");
				log.debug("meeting_id is============" + meeting_id);
				this.getRequest().setAttribute("meeting_id", meeting_id);
				return "portal";
			} else {
				orgList = organizationService.findAll();
				this.getRequest().setAttribute("orgList", orgList);
			}
		} catch (ServiceException e) {
			log.error("query organization list error: " + e.toString());
		}

		return SUCCESS;
	}

	/**
	 * 新增用户
	 * 
	 * @return
	 */
	public String add() {

		if (user == null) {
			this.addFieldError("errMsg", getText("user.add.fail"));
			return INPUT;
		}

		// TODO: jin临时设置默认密码
		user.setPassword("111111");
		if (StringUtil.isEmpty(user.getMeetingMember().getJob())) {
			user.getMeetingMember().setJob("会议成员");
		}
		user.setModifyTime(new Date());
		user.setState(1);
		String from = this.getParameter("from");
		if ("portal".equals(from)) {
			// 如果是会务人员添加用户到组织结构，则默认添加角色为会议成员
			Role role = new Role();
			role.setId(new Long(5));
			Set<Role> roles = new HashSet<Role>();
			roles.add(role);
			//user.setRoles(roles);
		}
		// 新增
		try {
			userService.add(user);
		} catch (ServiceException e) {
			log.error("user add failed! ", e);
			resultMap.put("retcode", RetCode.FAIL);
			resultMap.put("retmsg", getText(e.getMessage()));
			if ("portal".equals(from)) {
				return "json";
			}
			this.addFieldError("errMsg", getText("user.add.fail"));
			return INPUT;
		}

		if ("portal".equals(from)) {
			resultMap.put("retcode", RetCode.SUCCESS);
			resultMap.put("retmsg", getText("user.add.success"));
			return "json";
		}
		return SUCCESS;
	}

	/**
	 * 跳转到修改用户
	 * 
	 * @return
	 */
	public String goUpdate() {

		String id = this.getParameter("id");

		Long idL = StringUtil.stringToLong(id);

		try {
			user = userService.findById(idL);
		} catch (ServiceException e) {
			String errCode = e.getMessage();
			log.error("errCode:" + errCode + "detail:" + e.toString());
		}

		this.getRequest().setAttribute("user", user);

		// 获取机构列表
		List<Organization> orgList = null;
		// try {
		orgList = organizationService.findAll();
		// } catch (ServiceException e) {
		// log.error(e.toString());
		// }

		this.getRequest().setAttribute("orgList", orgList);

		return SUCCESS;
	}

	/**
	 * 修改用户
	 * 
	 * @return
	 */
	public String update() {
		if (user == null) {
			this.addFieldError("errMsg", getText("user.update.fail"));
			return INPUT;
		}

		User oldUser = null;
		try {
			oldUser = userService.findById(user.getId());
		} catch (ServiceException e) {
			e.printStackTrace();
		}

		if (oldUser == null) {
			this.addFieldError("errMsg", getText("org.update.fail"));
			return INPUT;
		}

		oldUser.setName(user.getName());
		//oldUser.setOrg(user.getOrg());
		oldUser.getMeetingMember().setJob(user.getMeetingMember().getJob());
		oldUser.getMeetingMember().setMailbox(
				user.getMeetingMember().getMailbox());
		oldUser.getMeetingMember().setAddress(
				user.getMeetingMember().getAddress());
		oldUser.getMeetingMember().setComments(
				user.getMeetingMember().getComments());
		
		oldUser.setState(user.getState());
		oldUser.setGender(user.getGender());
		oldUser.setModifyTime(new Date());

		// 修改
		try {
			userService.modify(oldUser);
		} catch (ServiceException e) {
			this.addFieldError("errMsg", getText("user.update.fail"));
			return INPUT;
		}

		return SUCCESS;
	}

	/**
	 * 删除用户
	 * 
	 * @return
	 */
	public String del() {

		String id = this.getParameter("id");

		Long idL = StringUtil.stringToLong(id);

		User oldUser = null;
		try {
			oldUser = userService.findById(idL);
		} catch (ServiceException e) {
			e.printStackTrace();
		}

		if (oldUser == null) {
			return SUCCESS;
		}

		oldUser.setState(0);

		// 删除
		try {
			userService.modify(oldUser);
		} catch (ServiceException e) {
			log.error("删除用户失败", e);
		}
		return SUCCESS;
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
		String meetingId=getRequest().getParameter("meetingId");
		if (ids != null && ids.length() > 0) {
			ids = ids.substring(0, ids.length() - 1);
		}
		String[] userIdArray = ids.split(ids);
		for (int i = 0; i < userIdArray.length; i++) {
			User user = userService.findById(Long.valueOf(userIdArray[i]));
			if (user != null) {
				userService.delete(user);
			}
		}
		
		getRequest().setAttribute("meeting.id", meetingId);

		return forword;
	}

	/**
	 * 跳转到设置角色
	 * 
	 * @return
	 */
	public String goSetRoles() {

		String id = this.getParameter("id");

		Long idL = StringUtil.stringToLong(id);

		try {
			user = userService.findById(idL);
		} catch (ServiceException e) {
			String errCode = e.getMessage();
			log.error("errCode:" + errCode + "detail:" + e.toString());
		}

		this.getRequest().setAttribute("user", user);

		// 查询出所有的角色供用户选择
		List<Role> roleList = null;
		try {
			roleList = roleService.findAll();
		} catch (ServiceException e) {
			e.printStackTrace();
		}

		this.getRequest().setAttribute("roleList", roleList);
		return SUCCESS;
	}

	/**
	 * 设置用户角色
	 * 
	 * @return
	 */
	public String setRoles() {
		if (user == null) {
			this.addFieldError("errMsg", getText("user.setRoles.fail"));
			return INPUT;
		}

		User oldUser = null;
		try {
			oldUser = userService.findById(user.getId());
		} catch (ServiceException e) {
			e.printStackTrace();
		}

		if (oldUser == null) {
			this.addFieldError("errMsg", getText("user.setRoles.fail"));
			return INPUT;
		}

		Set<Role> newRoles = new HashSet<Role>();

		if (roles != null && roles.size() > 0) {
			for (Long roleId : roles) {
				Role role = new Role();
				role.setId(roleId);
				newRoles.add(role);
			}
		}
		oldUser.setModifyTime(new Date());
		//oldUser.setRoles(newRoles);

		try {
			userService.modify(oldUser);
		} catch (ServiceException e) {
			this.addFieldError("errMsg", getText("user.setRoles.fail"));
			return INPUT;
		}

		return SUCCESS;
	}

	/**
	 * 查询在线用户列表
	 * 
	 * @return
	 */
	public String listOnline() {
		ServletContext application = this.getSession().getServletContext();

		// 删除用户session
		@SuppressWarnings("unchecked")
		List<HttpSession> list = (List<HttpSession>) application.getAttribute(OnlineUserListener.ONLINE_USER_LIST);

		List<User> onlineList = new ArrayList<User>();
		if (list != null && list.size() > 0) {
			for (HttpSession session : list) {
				onlineList.add((User) session.getAttribute(SessionKeeper.SESSION_USER));
			}
		}

		this.getRequest().setAttribute("onlineList", onlineList);

		return SUCCESS;
	}

	/**
	 * 后台强制注销用户会话
	 * 
	 * @return
	 */
	public String forceOut() {
		String userId = this.getParameter("id");

		Long uId = 0L;
		try {
			uId = Long.parseLong(userId);
		} catch (Exception e) {
			return ERROR;
		}

		ServletContext application = this.getSession().getServletContext();

		// 删除用户session
		@SuppressWarnings("unchecked")
		List<HttpSession> list = (List<HttpSession>) application.getAttribute(OnlineUserListener.ONLINE_USER_LIST);

		if (list != null && list.size() > 0) {
			for (HttpSession session : list) {
				User u = (User) session.getAttribute(SessionKeeper.SESSION_USER);
				if (uId.equals(u.getId())) {
					session.removeAttribute(SessionKeeper.SESSION_USER);
					break;
				}
			}
		}

		return SUCCESS;
	}

	/**
	 * 根据组织结构获取该组织下用户
	 * 
	 * @return
	 */
	public String getUserByOrg() {

		String org_id = this.getParameter("org_id");
		log.debug("org_id==={}" + org_id);
		if (org_id == null || org_id.equals("")) {
			resultMap.put("retcode", RetCode.PARAMS_INVALID);
			resultMap.put("retmsg", getText("user.setRoles.fail"));

		}
		try {
			Organization org = new Organization();
			org.setId(Long.parseLong(org_id));
			List<User> userList = userService.getUsersByOrg(org);

			resultMap.put("retcode", 0);
			resultMap.put("retmsg", "获取归属机构用户成功。");
			resultMap.put("retdata", userList);
			return SUCCESS;

		} catch (Exception e) {
			log.error("添加会议失败,", e);
			return INPUT;
		}

	}

	/**
	 * wap修改个人密码
	 * 
	 * @return
	 */
	public String wapModifyPwd() {

		if (StringUtil.isEmpty(oldPass)) {
			errMsg = "请输入当前密码。";
			return INPUT;
		}

		if (StringUtil.isEmpty(pass)) {
			errMsg = "请输入新密码。";
			return INPUT;
		}

		if (StringUtil.isEmpty(passAg)) {
			errMsg = "请输入确认新密码。";
			return INPUT;
		}

		User oldUser = null;
		try {
			oldUser = userService.findById(this.getUserIdFromSession());
		} catch (ServiceException e) {
			e.printStackTrace();
		}

		if (oldUser == null) {
			errMsg = "修改密码失败，请稍后再试。";
			return INPUT;
		}

		String encryptOld = "";
		try {
			encryptOld = Encrypt.encrypt(oldPass, null);
		} catch (Exception e) {
			log.error("password:" + oldPass + "encrypt error!" + e.getMessage());
		}

		if (!oldUser.getPassword().equals(encryptOld)) {
			errMsg = "当前密码输入不正确。";
			return INPUT;
		}

		if (!StringUtil.checkPortalPwd(pass)) {
			errMsg = "新密码长度或格式不正确。";
			return INPUT;
		}

		if (!pass.equals(passAg)) {
			errMsg = "两次输入的新密码不一致。";
			return INPUT;
		}
		
		if (pass.equals(oldPass))
		{
			errMsg = "新密码不能跟当前密码一样。";
			return INPUT;
		}

		String encryptPass = "";
		try {
			encryptPass = Encrypt.encrypt(pass, null);
		} catch (Exception e) {
			log.error("password:" + pass + "encrypt error!" + e.getMessage());
		}

		oldUser.setPassword(encryptPass);

		// 修改
		try {
			userService.modify(oldUser);
		} catch (ServiceException e) {
			errMsg = "修改密码失败，请稍后再试。";
			return INPUT;
		}

		errMsg = "修改密码成功。";
		return INPUT;
	}

	/**
	 * 修改个人密码请求
	 */
	public String modifyPwdReq() {
		return "input";
	}
	
	/**
	 * 修改个人密码
	 * 
	 * @return
	 */
	public String modifyPwd() {

		User oldUser = null;
		try {
			oldUser = userService.findById(this.getUserIdFromSession());
		} catch (ServiceException e) {
			e.printStackTrace();
		}

		if (oldUser == null) {
			this.addFieldError("errMsg", "修改密码失败，请稍后再试。");
			return INPUT;
		}

		String encryptOld = "";
		try {
			encryptOld = Encrypt.encrypt(oldPass, null);
		} catch (Exception e) {
			log.error("password:" + oldPass + "encrypt error!" + e.getMessage());
		}

		if (!oldUser.getPassword().equals(encryptOld)) {
			this.addFieldError("errMsg", "当前密码输入不正确。");
			return INPUT;
		}

		String encryptPass = "";
		try {
			encryptPass = Encrypt.encrypt(pass, null);
		} catch (Exception e) {
			log.error("password:" + pass + "encrypt error!" + e.getMessage());
		}

		oldUser.setPassword(encryptPass);

		// 修改
		try {
			userService.modify(oldUser);
		} catch (ServiceException e) {
			this.addFieldError("errMsg", "修改密码失败，请稍后再试。");
			return INPUT;
		}

		this.addFieldError("errMsg", "修改密码成功。");
		return INPUT;
	}

	/**
	 * 修改客户端密码
	 * @return
	 */
	public String clientModifyPwd() {
		String mobile = this.getParameter("username");
		if (StringUtil.isEmpty(mobile)) {
			resultMap.put("retcode", RetCode.PARAMS_INVALID);
			resultMap.put("retmsg", getText("client.user.modifypwd.invalidparam.mobile"));
			return SUCCESS;
		}
		
		if (StringUtil.isEmpty(oldPass)) {
			resultMap.put("retcode", RetCode.PARAMS_INVALID);
			resultMap.put("retmsg", getText("client.user.modifypwd.invalidparam.oldPass"));
			return SUCCESS;
		}

		if (StringUtil.isEmpty(pass)) {
			resultMap.put("retcode", RetCode.PARAMS_INVALID);
			resultMap.put("retmsg", getText("client.user.modifypwd.invalidparam.pass"));
			return SUCCESS;
		}

		//判断新密码长度是否合法
		try{
			String decrypedPass = Encrypt.decrypt(pass, null);
	
			if (!StringUtil.checkPortalPwd(decrypedPass)) {
				resultMap.put("retcode", RetCode.PARAMS_INVALID);
				resultMap.put("retmsg", getText("client.user.modifypwd.invalidparam.pattern.length"));
				return SUCCESS;
			}
		}catch(Exception e){
			resultMap.put("retcode", RetCode.UNKOWN_WRONG);
			resultMap.put("retmsg", getText("client.user.modifypwd.invalidparam.pass.decrypt"));
			return SUCCESS;			
		}

		if (pass.equals(oldPass))
		{
			resultMap.put("retcode", RetCode.PARAMS_INVALID);
			resultMap.put("retmsg", getText("client.user.modifypwd.invalidparam.pattern.same"));
			return SUCCESS;
		}
		
		User oldUser = null;
		try{
			oldUser = userService.selectByMobile(mobile);
		}catch(Exception e){
			resultMap.put("retcode", RetCode.FAIL);
			resultMap.put("retmsg", getText("lient.user.modifypwd.invaliduser"));
			return SUCCESS;	
		}

		if (oldUser == null) {
			resultMap.put("retcode", RetCode.FAIL);
			resultMap.put("retmsg", getText("lient.user.modifypwd.invaliduser"));
			return SUCCESS;
		}
	
		if (!oldUser.getPassword().equals(oldPass)) {
			resultMap.put("retcode", "102");
			resultMap.put("retmsg", getText("client.user.modifypwd.invalid.oldPass"));
			return SUCCESS;
		}

		oldUser.setPassword(pass);

		// 修改
		try {
			userService.modify(oldUser);
		} catch (ServiceException e) {
			resultMap.put("retcode", RetCode.UNKOWN_WRONG);
			resultMap.put("retmsg", getText("client.user.modifypwd.fail"));
			return SUCCESS;
		}
		resultMap.put("retcode", RetCode.SUCCESS);
		resultMap.put("retmsg", getText("client.user.modifypwd.success"));
		return SUCCESS;
	}
	
	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public OrganizationService getOrganizationService() {
		return organizationService;
	}

	public void setOrganizationService(OrganizationService organizationService) {
		this.organizationService = organizationService;
	}

	public RoleService getRoleService() {
		return roleService;
	}

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	public String getOldPass() {
		return oldPass;
	}

	public void setOldPass(String oldPass) {
		this.oldPass = oldPass;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getPassAg() {
		return passAg;
	}

	public void setPassAg(String passAg) {
		this.passAg = passAg;
	}

	public Set<Long> getRoles() {
		return roles;
	}

	public void setRoles(Set<Long> roles) {
		this.roles = roles;
	}

	public Long getMeetingId() {
		return meetingId;
	}

	public void setMeetingId(Long meetingId) {
		this.meetingId = meetingId;
	}
}

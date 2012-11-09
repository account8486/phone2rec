package com.wondertek.meeting.action.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import com.wondertek.meeting.action.base.BaseAction;
import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.common.RetCode;
import com.wondertek.meeting.common.SessionKeeper;
import com.wondertek.meeting.common.SysConstants;
import com.wondertek.meeting.common.SysUtil;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.filter.OnlineUserListener;
import com.wondertek.meeting.model.AdminRole;
import com.wondertek.meeting.model.AdminUser;
import com.wondertek.meeting.model.Organization;
import com.wondertek.meeting.model.security.SecurityRole;
import com.wondertek.meeting.service.AdminRoleService;
import com.wondertek.meeting.service.AdminUserService;
import com.wondertek.meeting.service.OrganizationService;
import com.wondertek.meeting.service.SecurityRoleService;
import com.wondertek.meeting.util.Encrypt;
import com.wondertek.meeting.util.StringUtil;

/**
 * 后台用户管理
 * 
 * @author 金祝华
 */
public class AdminUserAction extends BaseAction {

	private static final long serialVersionUID = -3102151360541524276L;

	private AdminUserService adminUserService; // 用户
	private OrganizationService organizationService; // 机构
	private AdminRoleService adminRoleService; // 角色
	SecurityRoleService securityRoleService;

	public SecurityRoleService getSecurityRoleService() {
		return securityRoleService;
	}

	public void setSecurityRoleService(SecurityRoleService securityRoleService) {
		this.securityRoleService = securityRoleService;
	}

	private AdminUser user; // 修改用户时接受参数
	private Long organId;

	private String errMsg;

	/**
	 * 查询用户列表
	 * 
	 * @return
	 */
	public String list() {

		if (user == null) {
			user = new AdminUser();
		}

		// 一般管理员能查看自己本级组织下低角色用户及下级组织的所有用户列表
		AdminUser sessionUser = this.getAdminUserFromSession();
		Long orgId = sessionUser.getOrg().getId();

		// 如果前台传入的组织id不为空，则以前台id为基准。（前台传入的组织id必定不高于管理员所属组织）
		if (organId != null && !organId.equals(0L)) {
			orgId = organId;
		} else {
			organId = orgId;// 回设给前台使用，基本上没用，因为前台过来总会带上org.id
		}

		// 1、查询管理员所属下级组织的列表
		List<Organization> orgList = null;

		orgList = new ArrayList<Organization>();

		organizationService.findChildren(orgId, orgList);

		// 2、组织列表id
		List<Long> childOrgIdList = null;
		if (orgList != null && orgList.size() > 0) {
			childOrgIdList = new ArrayList<Long>();
			for (Organization org : orgList) {
				childOrgIdList.add(org.getId());
			}
		}

		// 2.角色（系统管理员）
		Long roleId = sessionUser.getRole().getId();
		List<Long> roleList = new ArrayList<Long>();

		if (roleId.equals(3L)) // 会议管理员查看本级的会务人员及下级所有管理员
		{
			roleList.add(4L);

			// 非登录管理员所在组织时，允许查看会议管理员
			if (!orgId.equals(sessionUser.getOrg().getId())) {
				roleList.add(3L);
			}
		} else if (roleId.equals(1L)) // 系统管理员查看所有
		{
			roleList.add(1L);
			roleList.add(3L);
			roleList.add(4L);
		}

		Pager<AdminUser> adminUserPager = null;
		try {
			adminUserPager = adminUserService.findAdminUserPager(user, childOrgIdList, orgId, sessionUser.getId(),
					roleList, currentPage, pageSize);
		} catch (ServiceException e) {
			log.error("query adminUser list error: " + e.toString());
		}

		this.getRequest().setAttribute("pager", adminUserPager);

		return SUCCESS;
	}

	/**
	 * 跳转到新增用户
	 * 
	 * @return
	 */
	public String goAdd() {

		// 1. 只能添加管理员所属组织及下级组织的用户
		Organization organ = null;
		try {
			organ = organizationService.findById(organId);
		} catch (ServiceException e) {
			log.error("新增用户时，获取组织失败。");
			e.printStackTrace();
		}

		this.getRequest().setAttribute("organ", organ);

		// 2.角色（系统管理员）
		AdminUser sessionUser = this.getAdminUserFromSession();
		Long roleId = sessionUser.getRole().getId();
		List<AdminRole> roleList = new ArrayList<AdminRole>();

		Integer selfOrgLevel = sessionUser.getOrg().getLevel();

		if (SysUtil.isSuperAdmin(roleId)) // 系统管理员可以增加所有管理员
		{
			// 只有根组织才能增加系统管理员
			if (SysConstants.ROOT_ORG_ID.equals(organId)) {
				AdminRole adminRole = new AdminRole();
				adminRole.setId(1L);
				adminRole.setRoleName("系统管理员");
				roleList.add(adminRole);
			}
			// 低于四级的组织才能增加会议管理员 
//			if (organ.getLevel() > selfOrgLevel && organ.getLevel() < 4) {
			if (organ.getLevel() < 4) {
				AdminRole clientRole = new AdminRole();
				clientRole.setId(3L);
				clientRole.setRoleName("会议管理员");
				roleList.add(clientRole);
			}

		} else if (SysUtil.isGroupAdmin(roleId)) // 会议管理员可以增加会议管理员
		{
			// 低于四级的组织才能增加会议管理员 ，会议管理员不能建立自己所属组织的会议管理员
			if (organ.getLevel() > selfOrgLevel && organ.getLevel() < 4) {
				AdminRole clientRole = new AdminRole();
				clientRole.setId(3L);
				clientRole.setRoleName("会议管理员");
				roleList.add(clientRole);
			}
		}

		AdminRole meetingRole = new AdminRole();
		meetingRole.setId(4L);
		meetingRole.setRoleName("会务人员");
		roleList.add(meetingRole);

		this.getRequest().setAttribute("roleList", roleList);
		
		try {
			 List<SecurityRole> securityRolesList= securityRoleService.find(SecurityRole.class);
			 this.getRequest().setAttribute("securityRolesList", securityRolesList);
			 
		} catch (ServiceException e) {
			e.printStackTrace();
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
			errMsg = "新增用户失败。";
			return INPUT;
		}

		if (StringUtil.isEmpty(user.getJob())) {
			user.setJob("会务人员");
		}
		user.setModifyTime(new Date());
		user.setState(1);

		// 新增
		try {
			adminUserService.add(user);
		} catch (ServiceException e) {
			log.error("adminUser add failed! ", e);
			errMsg = getText(e.getMessage());
			return INPUT;
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
			user = adminUserService.findById(idL);
		} catch (ServiceException e) {
			String errCode = e.getMessage();
			log.error("errCode:" + errCode + "detail:" + e.toString());
		}

		if (user != null) {
			user.setPassword(Encrypt.decrypt(user.getPassword(), null));
		}
		this.getRequest().setAttribute("user", user);

		// 角色（系统管理员）
		AdminUser sessionUser = this.getAdminUserFromSession();
		Long roleId = sessionUser.getRole().getId();
		List<AdminRole> roleList = new ArrayList<AdminRole>();

		Integer selfOrgLevel = sessionUser.getOrg().getLevel();
		
		if (SysUtil.isSuperAdmin(roleId)) // 系统管理员可以增加所有管理员
		{
			// 只有根组织才能增加系统管理员
			if (SysConstants.ROOT_ORG_ID.equals(organId)) {
				AdminRole adminRole = new AdminRole();
				adminRole.setId(1L);
				adminRole.setRoleName("系统管理员");
				roleList.add(adminRole);
			}
			// 低于四级的组织才能增加会议管理员
			if (user.getOrg() != null && user.getOrg().getLevel() < 4) {
				AdminRole clientRole = new AdminRole();
				clientRole.setId(3L);
				clientRole.setRoleName("会议管理员");
				roleList.add(clientRole);
			}

		} else if (SysUtil.isGroupAdmin(roleId)) // 会议管理员可以增加会议管理员
		{
			// 低于四级的组织才能增加会议管理员，会议管理员不能建立自己所属组织的会议管理员
			if (user.getOrg() != null && user.getOrg().getLevel() > selfOrgLevel && user.getOrg().getLevel() < 4) {
				AdminRole clientRole = new AdminRole();
				clientRole.setId(3L);
				clientRole.setRoleName("会议管理员");
				roleList.add(clientRole);
			}
		}

		AdminRole meetingRole = new AdminRole();
		meetingRole.setId(4L);
		meetingRole.setRoleName("会务人员");
		roleList.add(meetingRole);
		
		try {
			 List<SecurityRole> securityRolesList= securityRoleService.find(SecurityRole.class);
			 this.getRequest().setAttribute("securityRolesList", securityRolesList);
			 
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		

		this.getRequest().setAttribute("roleList", roleList);

		return SUCCESS;
	}

	/**
	 * 修改用户
	 * 
	 * @return
	 */
	public String update() {
		if (user == null) {
			this.addFieldError("errMsg", getText("adminUser.update.fail"));
			return INPUT;
		}

		AdminUser oldAdminUser = null;
		try {
			oldAdminUser = adminUserService.findById(user.getId());
		} catch (ServiceException e) {
			e.printStackTrace();
		}

		if (oldAdminUser == null) {
			this.addFieldError("errMsg", getText("org.update.fail"));
			return INPUT;
		}

		String pwd = user.getPassword();
		try {
			pwd = Encrypt.encrypt(pwd, null);
		} catch (Exception e) {
			log.error("password:" + pwd + "encrypt error!" + e.getMessage());
		}
		oldAdminUser.setPassword(pwd);

		oldAdminUser.setName(user.getName());
		oldAdminUser.setOrg(user.getOrg());
		oldAdminUser.setRole(user.getRole());
		oldAdminUser.setCanOrg(user.getCanOrg());
		oldAdminUser.setJob(user.getJob());
		oldAdminUser.setGender(user.getGender());
		oldAdminUser.setState(user.getState());
		oldAdminUser.setTel(user.getTel());
		oldAdminUser.setMailbox(user.getMailbox());
		oldAdminUser.setAddress(user.getAddress());
		oldAdminUser.setComments(user.getComments());
		oldAdminUser.setModifyTime(new Date());
		oldAdminUser.setSecurityRoleId(user.getSecurityRoleId());
		// 修改
		try {
			adminUserService.modify(oldAdminUser);
		} catch (ServiceException e) {
			this.addFieldError("errMsg", getText("adminUser.update.fail"));
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

		AdminUser oldAdminUser = null;
		try {
			oldAdminUser = adminUserService.findById(idL);
		} catch (ServiceException e) {
			e.printStackTrace();
		}

		if (oldAdminUser == null) {
			return SUCCESS;
		}

		oldAdminUser.setState(-1);
		oldAdminUser.setModifyTime(new Date());

		// 删除
		try {
			adminUserService.modify(oldAdminUser);
		} catch (ServiceException e) {
			log.error("删除用户失败", e);
			errMsg = "删除失败。";
		}

		errMsg = "删除成功。";
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

		List<AdminUser> onlineList = new ArrayList<AdminUser>();
		if (list != null && list.size() > 0) {
			for (HttpSession session : list) {
				onlineList.add((AdminUser) session.getAttribute(SessionKeeper.SESSION_USER));
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
		String adminUserId = this.getParameter("id");

		Long uId = 0L;
		try {
			uId = Long.parseLong(adminUserId);
		} catch (Exception e) {
			return ERROR;
		}

		ServletContext application = this.getSession().getServletContext();

		// 删除用户session
		@SuppressWarnings("unchecked")
		List<HttpSession> list = (List<HttpSession>) application.getAttribute(OnlineUserListener.ONLINE_USER_LIST);

		if (list != null && list.size() > 0) {
			for (HttpSession session : list) {
				AdminUser u = (AdminUser) session.getAttribute(SessionKeeper.SESSION_USER);
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
	public String getAdminUserByOrg() {

		String org_id = this.getParameter("org_id");
		log.debug("org_id==={}" + org_id);
		if (org_id == null || org_id.equals("")) {
			resultMap.put("retcode", RetCode.PARAMS_INVALID);
			resultMap.put("retmsg", getText("adminUser.setRoles.fail"));

		}
		try {
			Organization org = new Organization();
			org.setId(Long.parseLong(org_id));
			List<AdminUser> adminUserList = adminUserService.getAdminUsersByOrg(org);

			resultMap.put("retcode", 0);
			resultMap.put("retmsg", "获取归属机构用户成功！");
			resultMap.put("retdata", adminUserList);
			return SUCCESS;

		} catch (Exception e) {
			log.error("添加会议失败,", e);
			return INPUT;
		}

	}

	/**
	 * 修改个人密码
	 * 
	 * @return
	 */
	public String modifyPwd() {
		String oldPass = this.getParameter("oldPass");
		String pass = this.getParameter("pass");

		AdminUser oldAdminUser = null;
		try {
			oldAdminUser = adminUserService.findById(this.getAdminUserIdFromSession());
		} catch (ServiceException e) {
			e.printStackTrace();
		}

		if (oldAdminUser == null) {
			resultMap.put("result", false);
			resultMap.put("message", "修改密码失败，请稍后再试！");
			return SUCCESS;
		}

		try {
			oldPass = Encrypt.encrypt(oldPass, null);
		} catch (Exception e) {
			log.error("password:" + oldPass + "encrypt error!" + e.getMessage());
		}

		if (!oldAdminUser.getPassword().equals(oldPass)) {
			resultMap.put("result", false);
			resultMap.put("message", "当前密码输入不正确！");
			return SUCCESS;
		}

		try {
			pass = Encrypt.encrypt(pass, null);
		} catch (Exception e) {
			log.error("password:" + pass + "encrypt error!" + e.getMessage());
		}

		oldAdminUser.setPassword(pass);

		// 修改
		try {
			adminUserService.modify(oldAdminUser);
		} catch (ServiceException e) {
			resultMap.put("result", false);
			resultMap.put("message", "修改密码失败，请稍后再试！");
			return SUCCESS;
		}

		resultMap.put("result", true);
		resultMap.put("message", "修改密码成功。");
		return SUCCESS;
	}

	public AdminUserService getAdminUserService() {
		return adminUserService;
	}

	public void setAdminUserService(AdminUserService adminUserService) {
		this.adminUserService = adminUserService;
	}

	public AdminUser getUser() {
		return user;
	}

	public void setUser(AdminUser user) {
		this.user = user;
	}

	public OrganizationService getOrganizationService() {
		return organizationService;
	}

	public void setOrganizationService(OrganizationService organizationService) {
		this.organizationService = organizationService;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public AdminRoleService getAdminRoleService() {
		return adminRoleService;
	}

	public void setAdminRoleService(AdminRoleService adminRoleService) {
		this.adminRoleService = adminRoleService;
	}

	public Long getOrganId() {
		return organId;
	}

	public void setOrganId(Long organId) {
		this.organId = organId;
	}
}

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
import com.wondertek.meeting.service.AdminRoleService;
import com.wondertek.meeting.service.AdminUserService;
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
	
	private AdminRoleService adminRoleService; // 角色

	private AdminUser user; // 修改用户时接受参数
	private Long organId;

	private String errMsg;

	/**
	 * 查询管理员列表
	 * 
	 * @return
	 */
	public String list() {

		if (user == null) {
			user = new AdminUser();
		}

		// 一般管理员能查看自己本级组织下低角色用户及下级组织的所有用户列表
		AdminUser sessionUser = this.getAdminUserFromSession();






	

		Pager<AdminUser> adminUserPager = null;
		try {
			adminUserPager = adminUserService.findAdminUserPager(user, null, null, sessionUser.getId(),
					null, currentPage, pageSize);
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
			user.setJob("会议管理员");
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


		


		AdminRole meetingRole = new AdminRole();
		meetingRole.setId(4L);
		meetingRole.setRoleName("");




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
		oldAdminUser.setJob(user.getJob());
		oldAdminUser.setGender(user.getGender());
		oldAdminUser.setState(user.getState());
		oldAdminUser.setTel(user.getTel());
		oldAdminUser.setMailbox(user.getMailbox());
		oldAdminUser.setAddress(user.getAddress());
		oldAdminUser.setComments(user.getComments());
		oldAdminUser.setModifyTime(new Date());
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

		oldAdminUser.setState(0);
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

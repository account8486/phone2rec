package com.wondertek.meeting.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wondertek.meeting.common.ExceptionCode;
import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.common.SysConstants;
import com.wondertek.meeting.dao.AdminUserDao;
import com.wondertek.meeting.dao.UserLoginLogDao;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.AdminUser;
import com.wondertek.meeting.model.Menu;
import com.wondertek.meeting.model.Organization;
import com.wondertek.meeting.service.AdminUserService;
import com.wondertek.meeting.util.Encrypt;
import com.wondertek.meeting.util.StringUtil;

/**
 * 用户
 * 
 */
public class AdminUserServiceImpl extends BaseServiceImpl<AdminUser, Long> implements AdminUserService {

	AdminUserDao adminUserDao;
	UserLoginLogDao userLoginLogDao;

	public AdminUser findById(Long id) throws ServiceException {
		return adminUserDao.findById(id);
	}

	/**
	 * 根据用户名获取密码
	 * 
	 * @param id
	 * @return
	 */
	public String findPwdByMobile(String mobile) {
		return adminUserDao.findPwdByMobile(mobile);
	}

	/**
	 * 新增用户
	 */
	public Long add(AdminUser adminUser) throws ServiceException {

		AdminUser old = null;
		try {
			old = adminUserDao.selectByMobile(adminUser.getMobile());
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (old != null) {
			throw new ServiceException(ExceptionCode.ADMIN_ADD_USER_EXISTED);
		}

		Long adminUserId = null;
		try {
			adminUserId = adminUserDao.add(adminUser);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return adminUserId;
	}

	/**
	 * 管理后台登录
	 * 
	 * @param adminUserInfo
	 * @param loginIp
	 * @return
	 * @throws ServiceException
	 */
	public AdminUser adminLogin(AdminUser adminUserInfo, String loginIp) throws ServiceException {

		// 加密密码
		String pwd = adminUserInfo.getPassword();
		try {
			pwd = Encrypt.encrypt(pwd, null);
		} catch (Exception e) {
			log.error("password:" + pwd + "encrypt error!" + e.getMessage());
		}

		// 1.登录
		AdminUser adminUser = null;
		try {
			adminUser = adminUserDao.selectByAdminUsernameAndPwd(adminUserInfo.getMobile(), pwd);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (null == adminUser) {
			throw new ServiceException(ExceptionCode.ADMIN_LOGIN_USER_PWD_WRONG);
		}

		// 判断用户状态是否"禁用"
		if (SysConstants.DB_STATUS_INVALID == adminUser.getState().intValue()) {
			throw new ServiceException(ExceptionCode.LOGIN_USER_FORBIDDEN);
		}

		// 2.记录用户登录日志
//		UserLoginLog adminUserLog = new UserLoginLog();
//		adminUserLog.setMobile(adminUserInfo.getMobile());
//		adminUserLog.setLoginTime(DateUtil.formatDate(new Date()));
//		adminUserLog.setLoginIp(loginIp);
//		userLoginLogDao.add(adminUserLog);

		return adminUser;
	}

	/**
	 * 根据用户名查询用户信息
	 * 
	 * @param adminUsername
	 * @return
	 */
	public AdminUser selectByMobile(String mobile) {
		return adminUserDao.selectByMobile(mobile);
	}

	/**
	 * 修改用户密码
	 * 
	 * @param adminUsername
	 * @param newPwd
	 *            新密码
	 * @return
	 */
	public String changePwd(String adminUsername, String newPwd) {
		return adminUserDao.changePwd(adminUsername, newPwd);
	}

	/**
	 * 查询用户的权限列表
	 * 
	 * @param adminUserId
	 * @return
	 */
	public List<String> queryPermissionsByAdminUserId(String adminUserId) {
		return adminUserDao.queryPermissionsByAdminUserId(adminUserId);
	}

	/**
	 * 查询用户的菜单列表
	 * 
	 * @param adminUserId
	 * @return
	 */
	public List<Menu> queryAdminUserMenuList(String adminUserId) {
		return adminUserDao.queryAdminUserMenuList(adminUserId);
	}

	/**
	 * 列表查询用户
	 * 
	 * @param adminUser 搜索条件
	 * @param childOrgIdList 下级组织列表
	 * @param roleList 本级机构能查看的角色列表
	 * @param currentPage 当前页
	 * @param pageSize 每页大小
	 * 
	 * @return
	 * @throws ServiceException
	 */
	public Pager<AdminUser> findAdminUserPager(AdminUser adminUser, List<Long> childOrgIdList, Long selfOrgId,
			Long selfUserId, List<Long> roleList, int currentPage, int pageSize,Integer status) throws ServiceException {

		// 能查看本级低角色用户及下级组织所有用户
		String hql = "from AdminUser u where 1=1 and u.state=:state";
		
		//通过过滤来查询有效或者无效的用户
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("state", status);

		try {
			return adminUserDao.findPager(hql, currentPage, pageSize, properties);
		} catch (Exception e) {
			final String errMsg = "Find meeting pager error ";
			log.error(errMsg, e);
			throw new ServiceException(errMsg, e);
		}
	}

	public AdminUserDao getAdminUserDao() {
		return adminUserDao;
	}

	public void setAdminUserDao(AdminUserDao adminUserDao) {
		this.adminUserDao = adminUserDao;
		this.basicDao = adminUserDao;
	}

	public UserLoginLogDao getUserLoginLogDao() {
		return userLoginLogDao;
	}

	public void setUserLoginLogDao(UserLoginLogDao userLoginLogDao) {
		this.userLoginLogDao = userLoginLogDao;
	}

	public List<AdminUser> getAdminUsersByOrg(Organization org) {
		return adminUserDao.getAdminUsersByOrg(org);
	}
}

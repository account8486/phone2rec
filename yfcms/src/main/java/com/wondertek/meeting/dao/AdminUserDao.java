package com.wondertek.meeting.dao;

import java.util.List;

import com.wondertek.meeting.model.AdminUser;
import com.wondertek.meeting.model.Menu;
import com.wondertek.meeting.model.Organization;

public interface AdminUserDao extends BaseDao<AdminUser, Long> {

	AdminUser findById(Long id);

	/**
	 * 根据用户名获取密码
	 * 
	 * @param id
	 * @return
	 */
	public String findPwdByMobile(String mobile);

	public Long add(AdminUser userInfo);

	/**
	 * 根据用户名查询用户信息
	 * 
	 * @param username
	 * @return
	 */
	public AdminUser selectByMobile(String mobile);

	/**
	 * 根据用户名密码查询用户信息
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	public AdminUser selectByAdminUsernameAndPwd(String username, String password) throws Exception;

	/**
	 * 修改用户密码
	 * 
	 * @param username
	 * @param newPwd
	 *            新密码
	 * @return
	 */
	public String changePwd(String username, String newPwd);

	/**
	 * 查询用户的权限列表
	 * 
	 * @param userId
	 * @return
	 */
	public List<String> queryPermissionsByAdminUserId(String userId);

	/**
	 * 查询用户的菜单列表
	 * 
	 * @param userId
	 * @return
	 */
	public List<Menu> queryAdminUserMenuList(String userId);

	/**
	 * 查询用户角色
	 * 
	 * @param uuid
	 * @return
	 */
	public List<Long> queryAdminUserRoles(Long userId);

	/**
	 * 根据用户归属组织机构获取该机构下所有用户
	 * 
	 * @param org
	 * @return
	 */
	public List<AdminUser> getAdminUsersByOrg(Organization org);

	public void saveOrUpdateAdminUser(AdminUser user);
}

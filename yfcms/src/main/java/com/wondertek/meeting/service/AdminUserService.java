package com.wondertek.meeting.service;

import java.util.List;

import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.AdminUser;
import com.wondertek.meeting.model.Menu;
import com.wondertek.meeting.model.Organization;

/**
 * 用户
 * 
 * @author 金祝华
 */
public interface AdminUserService extends BaseService<AdminUser, Long> {

	AdminUser findById(Long id) throws ServiceException;

	/**
	 * 根据用户名获取密码
	 * 
	 * @param id
	 * @return
	 */
	public String findPwdByMobile(String mobile);

	public Long add(AdminUser userInfo) throws ServiceException;

	/**
	 * 管理后台登录
	 * 
	 * @param userInfo
	 * @param loginIp
	 * @return
	 * @throws ServiceException
	 */
	public AdminUser adminLogin(AdminUser userInfo, String loginIp) throws ServiceException;

	/**
	 * 根据用户名查询用户信息
	 * 
	 * @param username
	 * @return
	 */
	public AdminUser selectByMobile(String mobile);

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
	 * 列表查询用户
	 * @param adminUser
	 * @param childOrgIdList
	 * @param selfOrgId
	 * @param selfUserId
	 * @param roleList
	 * @param currentPage
	 * @param pageSize
	 * @return
	 * @throws ServiceException
	 */
	public Pager<AdminUser> findAdminUserPager(AdminUser adminUser, List<Long> childOrgIdList, Long selfOrgId,
			Long selfUserId, List<Long> roleList, int currentPage, int pageSize,Integer status) throws ServiceException;

	/**
	 * 根据用户归属组织机构获取该机构下所有用户
	 * 
	 * @param org
	 * @return
	 */
	public List<AdminUser> getAdminUsersByOrg(Organization org);
}

package com.wondertek.meeting.dao;

import java.util.List;

import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.exception.HibernateDaoSupportException;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.ImeiUser;
import com.wondertek.meeting.model.Organization;
import com.wondertek.meeting.model.User;
import com.wondertek.meeting.model.UserTableInfo;

public interface UserDao extends BaseDao<User, Long> {

	User findById(Long id);
	
	/**
	 * 根据用户名获取密码
	 * 
	 * @param id
	 * @return
	 */
	public String findPwdByMobile(String mobile);

	/**
	 * 更新Imei、用户映射
	 * 
	 * @param imeiUser
	 * @throws Exception
	 */
	public void saveOrUpdate(ImeiUser imeiUser) throws Exception;

	public Long add(User userInfo);

	/**
	 * 根据用户名查询用户信息
	 * 
	 * @param username
	 * @return
	 */
	public User selectByMobile(String mobile);

	/**
	 * 根据用户名imei查询用户信息
	 * 
	 * @param username
	 * @return
	 */
	public User selectByImei(String imei) throws Exception;

	/**
	 * 根据用户名密码查询用户信息
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	public User selectByUsernameAndPwd(String username, String password) throws Exception;

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
//	public List<String> queryPermissionsByUserId(String userId);

	/**
	 * 查询用户的菜单列表
	 * 
	 * @param userId
	 * @return
	 */
//	public List<Menu> queryUserMenuList(String userId);

	/**
	 * 查询用户角色
	 * 
	 * @param uuid
	 * @return
	 */
//	public List<Long> queryUserRoles(Long userId);

	/**
	 * 根据用户归属组织机构获取该机构下所有用户
	 * 
	 * @param org
	 * @return
	 */
	public List<User> getUsersByOrg(Organization org);
	
	public void saveOrUpdateUser(User user);
	
	public List<User> getMeetingMember(String hql);
	
	
	/**查询已分桌的用户座位信息
	 * @throws HibernateDaoSupportException */
	@SuppressWarnings("unchecked")
	public List<UserTableInfo> getUserTableInfo(Long meetingId,Long dinnerId) throws HibernateDaoSupportException;
	
	/**
	 * 根据姓名或手机号查询指定会议的会议成员
	 * 条件：会议ID+（username/mobile,username和mobile至少有一个不为空)
	 */
	public Pager<User> queryMeetingMember(Long meetingId, String username, String mobile, int currentPage, int pageSize) throws ServiceException;
}

package com.wondertek.meeting.service;

import java.util.List;

import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.Organization;
import com.wondertek.meeting.model.User;

/**
 * 用户
 * 
 * @author 金祝华
 */
public interface UserService extends BaseService<User, Long> {

	User findById(Long id) throws ServiceException;

	/**
	 * 根据用户名获取密码
	 * 
	 * @param id
	 * @return
	 */
	public String findPwdByMobile(String mobile);

	public Long add(User userInfo) throws ServiceException;

	/**
	 * 管理后台登录
	 * 
	 * @param userInfo
	 * @param loginIp
	 * @return
	 * @throws ServiceException
	 */
//	public User adminLogin(User userInfo, String loginIp) throws ServiceException;

	/**
	 * web门户登录
	 * 
	 * @param userInfo
	 * @param loginIp
	 * @return
	 * @throws ServiceException
	 */
	public User login(User userInfo, String loginIp, Integer portalType) throws ServiceException;

	/**
	 * 客户端登录
	 * 
	 * @param userInfo
	 * @param loginIp
	 * @return
	 * @throws ServiceException
	 */
	public User clientLogin(User userInfo, String loginIp) throws ServiceException;

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
	public User selectByImei(String imei);

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
	 * 列表查询用户
	 * 
	 * @param user
	 * @param currentPage
	 * @param pageSize
	 * @return
	 * @throws ServiceException
	 */
	public Pager<User> findUserPager(User user, int currentPage, int pageSize) throws ServiceException;

	/**
	 * 根据用户归属组织机构获取该机构下所有用户
	 * 
	 * @param org
	 * @return
	 */
	public List<User> getUsersByOrg(Organization org);
	
	/**
	 * 获取某此会议下的所有用户
	 * @param meetingId
	 * @return
	 */
	public List<User> getMeetingMember(Long meetingId, String mobile,
			String userName,String memberLevel,String job,String userIds);
	
	public List<User> getMeetingMemberByIds(Long meetingId,String userIds);
	
	public Pager<User> findMeetingUserPager(Long meetingId, User user, int currentPage, int pageSize) throws ServiceException;
	public Pager<User> findMeetingUserPager(Long meetingId, User user, int currentPage, int pageSize,String isAdmin) throws ServiceException;
	
	
	/**
	 * 根据姓名或手机号查询指定会议的会议成员
	 * 条件：会议ID+（username/mobile,username和mobile至少有一个不为空)
	 */
	public Pager<User> queryMeetingMember(Long meetingId, String username, String mobile, int currentPage, int pageSize) throws ServiceException;
	
	public Pager<User> findMeetingUserPagerNotIncludeMe(Long meetingId, User user, int currentPage, int pageSize, String isAdmin,String currentUserId)
	throws ServiceException;
	
}

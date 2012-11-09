package com.wondertek.meeting.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wondertek.meeting.common.ExceptionCode;
import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.common.SysConstants;
import com.wondertek.meeting.dao.ImeiUserDao;
import com.wondertek.meeting.dao.UserDao;
import com.wondertek.meeting.dao.UserLoginLogDao;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.ImeiUser;
import com.wondertek.meeting.model.MeetingMember;
import com.wondertek.meeting.model.Organization;
import com.wondertek.meeting.model.User;
import com.wondertek.meeting.model.UserLoginLog;
import com.wondertek.meeting.service.UserService;
import com.wondertek.meeting.util.DateUtil;
import com.wondertek.meeting.util.Encrypt;
import com.wondertek.meeting.util.StringUtil;

/**
 * 用户
 * 
 * @author 金祝华
 */
public class UserServiceImpl extends BaseServiceImpl<User, Long> implements UserService {

	UserDao userDao;
	UserLoginLogDao userLoginLogDao;

	ImeiUserDao imeiUserDao;

	public User findById(Long id) throws ServiceException {
		return userDao.findById(id);
	}

	/**
	 * 根据用户名获取密码
	 * 
	 * @param id
	 * @return
	 */
	public String findPwdByMobile(String mobile) {
		return userDao.findPwdByMobile(mobile);
	}

	/**
	 * 新增用户
	 */
	public Long add(User user) throws ServiceException {

		User old = null;
		try {
			old = userDao.selectByMobile(user.getMobile());
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (old != null) {
			throw new ServiceException(ExceptionCode.ADD_USER_EXISTED);
		}

		Long userId = null;
		try {
			userId = userDao.add(user);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return userId;
	}

	/**
	 * 管理后台登录
	 * 
	 * @param userInfo
	 * @param loginIp
	 * @return
	 * @throws ServiceException
	 */
	/*
	 * public User adminLogin(User userInfo, String loginIp) throws
	 * ServiceException {
	 * 
	 * // 加密密码 String pwd = userInfo.getPassword(); try { pwd =
	 * Encrypt.encrypt(pwd, null); } catch (Exception e) { log.error("password:"
	 * + pwd + "encrypt error!" + e.getMessage()); }
	 * 
	 * // 1.登录 User user = null; try { user =
	 * userDao.selectByUsernameAndPwd(userInfo.getMobile(), pwd); } catch
	 * (Exception e) { e.printStackTrace(); }
	 * 
	 * if (null == user) { throw new
	 * ServiceException(ExceptionCode.LOGIN_USER_PWD_WRONG); }
	 * 
	 * // 判断用户状态是否"禁用" if (SysConstants.DB_STATUS_INVALID ==
	 * user.getState().intValue()) { throw new
	 * ServiceException(ExceptionCode.LOGIN_USER_FORBIDDEN); }
	 * 
	 * // 2.判断用户角色（后台只有超级管理员、系统管理员、会务人员、会议云客服可以登录） List<Long> userRoles =
	 * userDao.queryUserRoles(user.getId()); boolean result = false; if
	 * (userRoles != null && userRoles.size() > 0) { for (Long role : userRoles)
	 * { if (role.intValue() == 1 || role.intValue() == 2 || role.intValue() ==
	 * 3 || role.intValue() == 4) { result = true; break; } } }
	 * 
	 * if (!result) { throw new
	 * ServiceException(ExceptionCode.LOGIN_USER_PWD_WRONG); }
	 * 
	 * // 3.记录用户登录日志 // UserLoginLog userLog = new UserLoginLog(); //
	 * userLog.setMobile(userInfo.getMobile()); //
	 * userLog.setLoginTime(DateUtil.formatDate(new Date())); //
	 * userLog.setLoginIp(loginIp); // userLoginLogDao.add(userLog);
	 * 
	 * return user; }
	 */

	/**
	 * web门户登录
	 * 
	 * @param userInfo
	 * @param loginIp
	 * @return
	 * @throws ServiceException
	 */
	public User login(User userInfo, String loginIp, Integer portalType) throws ServiceException {

		// 加密密码
		String pwd = userInfo.getPassword();
		try {
			pwd = Encrypt.encrypt(pwd, null);
		} catch (Exception e) {
			log.error("password:" + pwd + "encrypt error!" + e.getMessage());
		}

		// 1.登录
		User user = null;
		try {
			user = userDao.selectByMobile(userInfo.getMobile());
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (null == user) {
			throw new ServiceException(ExceptionCode.LOGIN_USER_WRONG);
		}

		if (!pwd.equals(user.getPassword())) {
			throw new ServiceException(ExceptionCode.LOGIN_PWD_WRONG);
		}

		// 判断用户状态是否"禁用"
		if (SysConstants.DB_STATUS_INVALID == user.getState().intValue()) {
			throw new ServiceException(ExceptionCode.LOGIN_USER_FORBIDDEN);
		}

		// 3.记录用户登录日志
		UserLoginLog userLog = new UserLoginLog();
		userLog.setUser(user);
		userLog.setLoginTime(DateUtil.formatDate(new Date()));
		userLog.setLoginIp(loginIp);
		userLog.setPortalType(portalType);
		userLoginLogDao.add(userLog);

		return user;
	}

	/**
	 * 门户登录
	 * 
	 * @param userInfo
	 * @param loginIp
	 * @return
	 * @throws ServiceException
	 */
	public User clientLogin(User userInfo, String loginIp) throws ServiceException {

		User user = null;
		try {
			user = userDao.selectByMobile(userInfo.getMobile());
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (null == user) {
			throw new ServiceException(ExceptionCode.LOGIN_USER_WRONG);
		}

		if (!userInfo.getPassword().equals(user.getPassword())) {
			throw new ServiceException(ExceptionCode.LOGIN_PWD_WRONG);
		}

		// 将imei码更新到user表
		ImeiUser imeiUser = new ImeiUser();
		imeiUser.setImei(userInfo.getImei());
		imeiUser.setUserId(user.getId());
		log.info("client login! userId:{},Imei:{}",
				new String[] { imeiUser.getUserId().toString(), imeiUser.getImei() });
		try {
			userDao.saveOrUpdate(imeiUser);
			// imeiUserDao.saveOrUpdateEntity(imeiUser);
		} catch (Throwable e) {
			log.error("update imei error!", e);
		}

		// 判断用户状态是否"禁用"
		if (SysConstants.DB_STATUS_INVALID == user.getState().intValue()) {
			throw new ServiceException(ExceptionCode.LOGIN_USER_FORBIDDEN);
		}

		// 记录用户登录日志
		UserLoginLog userLog = new UserLoginLog();
		userLog.setUser(user);
		userLog.setLoginTime(DateUtil.formatDate(new Date()));
		userLog.setLoginIp(loginIp);
		userLog.setPortalType(SysConstants.PORTAL_TYPE_CLIENT);
		userLoginLogDao.add(userLog);

		return user;
	}

	/**
	 * 根据用户名查询用户信息
	 * 
	 * @param username
	 * @return
	 */
	public User selectByMobile(String mobile) {
		return userDao.selectByMobile(mobile);
	}

	/**
	 * 根据用户名imei查询用户信息
	 * 
	 * @param username
	 * @return
	 */
	public User selectByImei(String imei) {
		User user = null;

		try {
			user = userDao.selectByImei(imei);
		} catch (Exception e) {
			log.error("select user by imei error!", e);
		}

		return user;
	}

	/**
	 * 修改用户密码
	 * 
	 * @param username
	 * @param newPwd
	 *            新密码
	 * @return
	 */
	public String changePwd(String username, String newPwd) {
		return userDao.changePwd(username, newPwd);
	}

	/**
	 * 查询用户的权限列表
	 * 
	 * @param userId
	 * @return
	 */
	/*
	 * public List<String> queryPermissionsByUserId(String userId) { return
	 * userDao.queryPermissionsByUserId(userId); }
	 */

	/**
	 * 查询用户的菜单列表
	 * 
	 * @param userId
	 * @return
	 */
	/*
	 * public List<Menu> queryUserMenuList(String userId) { return
	 * userDao.queryUserMenuList(userId); }
	 */

	/**
	 * 列表查询用户
	 * 
	 * @param user
	 * @param currentPage
	 * @param pageSize
	 * @return
	 * @throws ServiceException
	 */
	public Pager<User> findUserPager(User user, int currentPage, int pageSize) throws ServiceException {
		String hql = "from User u where u.mobile like '%'||:mobile||'%' and u.name like '%'||:name||'%' order by modifyTime desc";
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("mobile", StringUtil.null2Str(user.getMobile()));
		properties.put("name", StringUtil.null2Str(user.getName()));
		try {
			return userDao.findPager(hql, currentPage, pageSize, properties);
		} catch (Exception e) {
			final String errMsg = "Find meeting pager error ";
			log.error(errMsg, e);
			throw new ServiceException(errMsg, e);
		}
	}

	/**
	 * 获取某此会议下的所有用户
	 * 
	 * @param meetingId
	 * @return
	 */
	public List<User> getMeetingMember(Long meetingId, String mobile, String userName, String memberLevel, String job,String userIds) {
		StringBuffer hql = new StringBuffer();
		hql.append(
				"  SELECT meetingUser FROM  User meetingUser,MeetingMember meetingMember where meetingUser.id=meetingMember.userId  ")
				.append("  AND  meetingMember.meetingId=" + meetingId);
		// 通过手机号来进行查询
		if (StringUtil.isNotEmpty(mobile)) {
			hql.append(" AND meetingUser.mobile like '%" + mobile + "%'");
		}
		// 通过userName来进行查询
		if (StringUtil.isNotEmpty(userName)) {
			hql.append(" AND meetingUser.name like '%" + userName + "%'");
		}

		// 用户级别
		if (StringUtil.isNotEmpty(memberLevel)) {
			hql.append(" AND  meetingMember.memberLevel=" + memberLevel);
		}

		// 通过职务来查询
		if (StringUtil.isNotEmpty(job)) {
			hql.append(" AND meetingUser.job like '%" + job + "%'");
		}
		
		if(StringUtil.isNotEmpty(userIds)){
			hql.append(" AND meetingUser.id in ("+userIds+") ");
		}

		hql.append("  order by (case when meetingMember.sortCode is not null then meetingMember.sortCode else 2000 end )  ");
		
		log.debug("getMeetingMember:"+hql.toString());

		return this.userDao.getMeetingMember(hql.toString());
	}

	/**
	 * 增加通过IDS来过滤
	 */
	public List<User> getMeetingMemberByIds(Long meetingId, String userIds) {
		StringBuffer hql = new StringBuffer();
		hql.append("  SELECT meetingUser FROM  User meetingUser,MeetingMember meetingMember where meetingUser.id=meetingMember.userId  ");
		// 通过meetingid来过滤
		hql.append("  AND  meetingMember.meetingId=" + meetingId);
		// 通过用户ID来过滤
		hql.append("  AND  meetingMember.userId in(" + userIds + ")");

		return this.userDao.getMeetingMember(hql.toString());
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
		this.basicDao = userDao;
	}

	public UserLoginLogDao getUserLoginLogDao() {
		return userLoginLogDao;
	}

	public void setUserLoginLogDao(UserLoginLogDao userLoginLogDao) {
		this.userLoginLogDao = userLoginLogDao;
	}

	public List<User> getUsersByOrg(Organization org) {
		return userDao.getUsersByOrg(org);
	}

	public Pager<User> findMeetingUserPager(Long meetingId, User user, int currentPage, int pageSize, String isAdmin)
			throws ServiceException {

		Map<String, Object> properties = new HashMap<String, Object>();
		StringBuffer sql = new StringBuffer();
		sql.append(" select a.*,case when b.sign_time is null then '0' else '1' end is_signed ");
		sql.append(" from  (");
		sql.append(" select c.*,r.room_no ");
		sql.append(" from (");
		sql.append(" select u.id,u.name,u.mobile,mm.job,u.modify_time,u.state,mm.member_level,");
		sql.append(" mm.sort_code,mm.mobile_is_display,mm.meeting_id,mm.city,mm.mailbox,u.gender,");
		sql.append(" u.birthday,mm.address,mm.add_in_contacts,mm.hotel_room_id,mm.room_Number_Is_Display,mm.job_Is_Display ");
		// 单位
		sql.append("  ,mm.department,mm.book_Job  ");
		sql.append(" from  user u,meeting_member mm where u.id=mm.user_id  AND u.state='1' ");
		if (!"1".equals(isAdmin)) {
			sql.append("  AND  mm.add_in_contacts = 'Y' ");
		}

		if (user!=null&&user.getMeetingMember() != null) {
			if (StringUtil.isNotEmpty(String.valueOf(user.getMeetingMember().getMemberLevel()))) {
				sql.append("  and mm.member_level=" + user.getMeetingMember().getMemberLevel());
			}
		}

		// 通过meetingid来过滤
		sql.append("  AND  mm.meeting_id= :meetingId ");
		// 通过用户name来过滤
		sql.append("  AND  u.name like :username ");
		sql.append("  AND  u.mobile like :mobile ");
		sql.append("   ) c  LEFT JOIN  hotel_room r on c.hotel_room_id=r.id ");
		sql.append("   ) a  LEFT JOIN  sign_in b ");
		sql.append("  on a.meeting_id=b.meeting_id AND a.id=b.user_id ");
		// 如果排序码为空 则排在最后面,采用5000这个数字 一般排序码肯定不会排到5000的了
		sql.append("  order by (case when a.sort_code is null then 5000 else a.sort_code end) ");

		log.debug("sql================{}", sql);
		properties.put("meetingId", meetingId);
		properties.put("username", "%" + StringUtil.null2Str(user.getName()) + "%");
		properties.put("mobile", "%" + StringUtil.null2Str(user.getMobile()) + "%");
		try {
			// List results = userDao.queryListSql(sql.toString(), -1, -1,
			// properties);
			Pager<Object> result_pager = userDao.findPagerBySql(sql.toString(), currentPage, pageSize, properties);
			List<Object> result_list = result_pager.getPageRecords();
			List<User> userList = new ArrayList<User>();
			for (Object result : result_list) {
				// 将查询出来的数据转换成对象
				Object[] resultArray = (Object[]) result;
				short mobile_is_display = (Short) (resultArray[8] == null ? (short) 1 : resultArray[8]);
				// String mobile =
				// mobile_is_display==(short)1?(String)resultArray[2]:"未公开";
				User result_user = new User();
				result_user.setMeetingMember(new MeetingMember());
				result_user.setId(Long.valueOf((Integer) resultArray[0]));
				result_user.setName((String) resultArray[1]);
				result_user.setMobile((String) resultArray[2]);
				result_user.getMeetingMember().setJob((String) resultArray[3]);
				result_user.setModifyTime((Date) resultArray[4]);
				result_user.setState(Integer.valueOf((Short) resultArray[5]));

				result_user.getMeetingMember().setMemberLevel(Integer.valueOf((Short) resultArray[6]));
				// 排序码可为空
				result_user.getMeetingMember().setSortCode(
						resultArray[7] == null ? null : Integer.valueOf(String.valueOf(resultArray[7])));
				result_user.getMeetingMember().setMobileIsDisplay(Integer.valueOf(mobile_is_display));
				result_user.getMeetingMember().setCity((String) resultArray[10]);
				result_user.getMeetingMember().setMailbox((String) resultArray[11]);
				result_user.setGender(Integer.valueOf((Short) (resultArray[12] == null ? (short) 2 : resultArray[12])));
				result_user.setBirthday((String) resultArray[13]);
				result_user.getMeetingMember().setAddress((String) resultArray[14]);

				result_user.getMeetingMember().setAddInContacts(String.valueOf(resultArray[15]));

				result_user.getMeetingMember().setRoomNumberIsDisplay(
						Integer.valueOf((Short) (resultArray[17] == null ? (short) 1 : resultArray[17])));
				result_user.getMeetingMember().setJobIsDisplay(
						Integer.valueOf((Short) (resultArray[18] == null ? (short) 1 : resultArray[18])));
				// 单位
				result_user.getMeetingMember().setDepartment(
						StringUtil.isNotEmpty(String.valueOf(resultArray[19])) ? String.valueOf(resultArray[19]) : "");
				// 职位
				result_user.getMeetingMember().setBookJob(StringUtil.null2Str(resultArray[20]));

				result_user.getMeetingMember().setRoomNumber(
						StringUtil.isNotEmpty(String.valueOf(resultArray[21])) ? String.valueOf(resultArray[21]) : "");

				// 加新字段的时候 记得吧IsSigned的数组下标自加1
				result_user.setIsSigned((String) resultArray[22]);
				userList.add(result_user);
			}
			Pager<User> pager = new Pager<User>();
			pager.setTotal(result_pager.getTotal());
			pager.setPageSize(result_pager.getPageSize());
			pager.setCurrentPage(result_pager.getCurrentPage());
			pager.setPageRecords(userList);
			return pager;
		} catch (Exception e) {
			final String errMsg = "Find meeting pager error ";
			log.error(errMsg, e);
			throw new ServiceException(errMsg, e);
		}
	}
	
	
/**
 * 选择不包含自己的人
 * @param meetingId
 * @param user
 * @param currentPage
 * @param pageSize
 * @param isAdmin
 * @return
 * @throws ServiceException
 */
public Pager<User> findMeetingUserPagerNotIncludeMe(Long meetingId, User user, int currentPage, int pageSize, String isAdmin,String currentUserId)
 throws ServiceException {

		Map<String, Object> properties = new HashMap<String, Object>();
		StringBuffer sql = new StringBuffer();
		sql.append(" select a.*,case when b.sign_time is null then '0' else '1' end is_signed ");
		sql.append(" from  (");
		sql.append(" select c.*,r.room_no ");
		sql.append(" from (");
		sql.append(" select u.id,u.name,u.mobile,mm.job,u.modify_time,u.state,mm.member_level,");
		sql.append(" mm.sort_code,mm.mobile_is_display,mm.meeting_id,mm.city,mm.mailbox,u.gender,");
		sql.append(" u.birthday,mm.address,mm.add_in_contacts,mm.hotel_room_id,mm.room_Number_Is_Display,mm.job_Is_Display ");
		// 单位
		sql.append("  ,mm.department,mm.book_Job  ");
		sql.append(" from  user u,meeting_member mm where u.id=mm.user_id  AND u.state='1' ");
		//过滤私信人员
		
		if (!"1".equals(isAdmin)) {
			sql.append("  AND  mm.add_in_contacts = 'Y' ");
		}
		
		sql.append("  AND  mm.in_private_message <>0 ");

		// 过滤当前用户
		if (StringUtil.isNotEmpty(currentUserId)) {
			sql.append("  AND  u.id <> " + currentUserId);
		}

		if (user.getMeetingMember() != null) {
			if (StringUtil.isNotEmpty(String.valueOf(user.getMeetingMember()
					.getMemberLevel()))) {
				sql.append("  and mm.member_level="
						+ user.getMeetingMember().getMemberLevel());
			}
		}

		// 通过meetingid来过滤
		sql.append("  AND  mm.meeting_id= :meetingId ");
		// 通过用户name来过滤
		sql.append("  AND  u.name like :username ");
		sql.append("  AND  u.mobile like :mobile ");
		sql.append("   ) c  LEFT JOIN  hotel_room r on c.hotel_room_id=r.id ");
		sql.append("   ) a  LEFT JOIN  sign_in b ");
		sql.append("  on a.meeting_id=b.meeting_id AND a.id=b.user_id ");
		// 如果排序码为空 则排在最后面,采用5000这个数字 一般排序码肯定不会排到5000的了
		sql.append("  order by (case when a.sort_code is null then 5000 else a.sort_code end) ");

		log.debug("sql================{}", sql);
		properties.put("meetingId", meetingId);
		properties.put("username", "%" + StringUtil.null2Str(user.getName())
				+ "%");
		properties.put("mobile", "%" + StringUtil.null2Str(user.getMobile())
				+ "%");
		try {
			// List results = userDao.queryListSql(sql.toString(), -1, -1,
			// properties);
			Pager<Object> result_pager = userDao.findPagerBySql(sql.toString(),
					currentPage, pageSize, properties);
			List<Object> result_list = result_pager.getPageRecords();
			List<User> userList = new ArrayList<User>();
			for (Object result : result_list) {
				// 将查询出来的数据转换成对象
				Object[] resultArray = (Object[]) result;
				short mobile_is_display = (Short) (resultArray[8] == null ? (short) 1
						: resultArray[8]);
				// String mobile =
				// mobile_is_display==(short)1?(String)resultArray[2]:"未公开";
				User result_user = new User();
				result_user.setMeetingMember(new MeetingMember());
				result_user.setId(Long.valueOf((Integer) resultArray[0]));
				result_user.setName((String) resultArray[1]);
				result_user.setMobile((String) resultArray[2]);
				result_user.getMeetingMember().setJob((String) resultArray[3]);
				result_user.setModifyTime((Date) resultArray[4]);
				result_user.setState(Integer.valueOf((Short) resultArray[5]));

				result_user.getMeetingMember().setMemberLevel(
						Integer.valueOf((Short) resultArray[6]));
				// 排序码可为空
				result_user.getMeetingMember().setSortCode(
						resultArray[7] == null ? null : Integer.valueOf(String
								.valueOf(resultArray[7])));
				result_user.getMeetingMember().setMobileIsDisplay(
						Integer.valueOf(mobile_is_display));
				result_user.getMeetingMember()
						.setCity((String) resultArray[10]);
				result_user.getMeetingMember().setMailbox(
						(String) resultArray[11]);
				result_user.setGender(Integer
						.valueOf((Short) (resultArray[12] == null ? (short) 2
								: resultArray[12])));
				result_user.setBirthday((String) resultArray[13]);
				result_user.getMeetingMember().setAddress(
						(String) resultArray[14]);

				result_user.getMeetingMember().setAddInContacts(
						String.valueOf(resultArray[15]));

				result_user
						.getMeetingMember()
						.setRoomNumberIsDisplay(
								Integer.valueOf((Short) (resultArray[17] == null ? (short) 1
										: resultArray[17])));
				result_user
						.getMeetingMember()
						.setJobIsDisplay(
								Integer.valueOf((Short) (resultArray[18] == null ? (short) 1
										: resultArray[18])));
				// 单位
				result_user.getMeetingMember()
						.setDepartment(
								StringUtil.isNotEmpty(String
										.valueOf(resultArray[19])) ? String
										.valueOf(resultArray[19]) : "");
				// 职位
				result_user.getMeetingMember().setBookJob(
						StringUtil.null2Str(resultArray[20]));

				result_user.getMeetingMember()
						.setRoomNumber(
								StringUtil.isNotEmpty(String
										.valueOf(resultArray[21])) ? String
										.valueOf(resultArray[21]) : "");

				// 加新字段的时候 记得吧IsSigned的数组下标自加1
				result_user.setIsSigned((String) resultArray[22]);
				userList.add(result_user);
			}
			Pager<User> pager = new Pager<User>();
			pager.setTotal(result_pager.getTotal());
			pager.setPageSize(result_pager.getPageSize());
			pager.setCurrentPage(result_pager.getCurrentPage());
			pager.setPageRecords(userList);
			return pager;
		} catch (Exception e) {
			final String errMsg = "Find meeting pager error ";
			log.error(errMsg, e);
			throw new ServiceException(errMsg, e);
		}
}
	
	public Pager<User> findMeetingUserPager(Long meetingId, User user, int currentPage, int pageSize)
			throws ServiceException {
		return findMeetingUserPager(meetingId, user, currentPage, pageSize, "0");
	}
	

	public ImeiUserDao getImeiUserDao() {
		return imeiUserDao;
	}

	public void setImeiUserDao(ImeiUserDao imeiUserDao) {
		this.imeiUserDao = imeiUserDao;
	}

	/**
	 * 根据姓名或手机号查询指定会议的会议成员 条件：会议ID+（username/mobile,username和mobile至少有一个不为空)
	 */
	public Pager<User> queryMeetingMember(Long meetingId, String username, String mobile, int currentPage, int pageSize) throws ServiceException {
		return this.userDao.queryMeetingMember(meetingId, username, mobile, currentPage, pageSize);
	}
}

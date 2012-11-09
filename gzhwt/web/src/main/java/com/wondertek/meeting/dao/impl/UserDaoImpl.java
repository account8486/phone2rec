package com.wondertek.meeting.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;

import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.dao.UserDao;
import com.wondertek.meeting.exception.HibernateDaoSupportException;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.ImeiUser;
import com.wondertek.meeting.model.Organization;
import com.wondertek.meeting.model.User;
import com.wondertek.meeting.model.UserTableInfo;
import com.wondertek.meeting.util.Encrypt;
import com.wondertek.meeting.util.StringUtil;

/**
 * 用户Dao
 * 
 * @author 金祝华
 */
public class UserDaoImpl extends BaseDaoImpl<User, Long> implements UserDao {
	Logger log = LoggerFactory.getLogger(this.getClass());

	@SuppressWarnings("unchecked")
	public User findById(Long id) {
		String hql = "from User b where b.id = ?";

		User user = null;

		List<User> UserInfoList = this.getHibernateTemplate().find(hql, id);
		if (UserInfoList != null && UserInfoList.size() > 0) {
			return UserInfoList.get(0);
		}

		return user;
	}

	/**
	 * 根据用户名获取密码
	 * 
	 * @param id
	 * @return
	 */
	public String findPwdByMobile(String mobile) {
		String hql = "select b.password from User b where b.mobile = ?";

		@SuppressWarnings("unchecked")
		List<String> list = this.getHibernateTemplate().find(hql, mobile);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * 更新Imei、用户映射
	 * 
	 * @param imeiUser
	 * @throws Exception
	 */
	public void saveOrUpdate(ImeiUser imeiUser) throws Exception {
		this.getHibernateTemplate().saveOrUpdate(imeiUser);
//		Transaction tx = this.getSession().beginTransaction();
//		tx.begin();
//		this.getSession().saveOrUpdate(imeiUser);
//		this.getSession().flush();
//		tx.commit();
	}

	@Override
	public Long add(User userInfo) {
		String pwd = userInfo.getPassword();
		try {
			pwd = Encrypt.encrypt(pwd, null);
		} catch (Exception e) {
			log.error("password:" + pwd + "encrypt error!" + e.getMessage());
		}
		userInfo.setPassword(pwd);

		Long userId = null;
		try {
			userId = (Long) this.getHibernateTemplate().save(userInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return userId;
	}

	/**
	 * 根据用户名查询用户信息
	 * 
	 * @return
	 */
	public User selectByMobile(String mobile) {
		String sql = "from User b where b.mobile = ?";

		@SuppressWarnings("unchecked")
		List<User> UserInfoList = this.getHibernateTemplate().find(sql, mobile);
		if (UserInfoList != null && UserInfoList.size() > 0) {
			return UserInfoList.get(0);
		}

		return null;
	}

	/**
	 * 根据用户名imei查询用户信息
	 * 
	 * @param imei
	 * @return
	 */
	public User selectByImei(String imei) throws Exception {
		ImeiUser imeiUser = this.getHibernateTemplate().load(ImeiUser.class, imei);

		if (imeiUser != null && imeiUser.getUserId() != null) {
			return this.findById(imeiUser.getUserId());
		}

		return null;
	}

	public User selectByUsernameAndPwd(String username, String pwd) throws Exception {
		// String sql = "from User b where b.mobile = ? and b.password = ?";

		String sql = "from User b where b.mobile = ? and b.password = ?";

		@SuppressWarnings("unchecked")
		List<User> UserInfoList = this.getHibernateTemplate().find(sql, username, pwd);

		if (UserInfoList != null && UserInfoList.size() > 0) {
			User user = UserInfoList.get(0);

			// @SuppressWarnings("unchecked")
			// List<Long> list = this.getHibernateTemplate().find(
			// "select roleId from UserRole b where b.userId = ?",
			// user.getId());
			//
			// user.setRoles(list);
			return user;
		}

		return null;
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
		String sql = "from UserInfo b where b.username = ?";

		@SuppressWarnings("unchecked")
		List<User> UserInfoList = this.getHibernateTemplate().find(sql, username);
		if (UserInfoList != null && UserInfoList.size() > 0) {
			User user = UserInfoList.get(0);
			user.setPassword(newPwd);// 修改密码
			this.getHibernateTemplate().save(user);
		} else {
			return "0";
		}

		return "1";
	}

	/**
	 * 查询用户的权限列表
	 * 
	 * @param userId
	 * @return
	 */
	@Deprecated
	/*public List<String> queryPermissionsByUserId(String userId) {

		String sql = "select distinct permission_code from permission p, role_permission rp "
				+ "where p.permission_id = rp.permission_id and rp.role_id in (select role_id from user_role ur where ur.user_id = :userId)";

		@SuppressWarnings("unchecked")
		List<String> permissionCodeList = this.getHibernateTemplate().getSessionFactory().getCurrentSession()
				.createSQLQuery(sql).setString("userId", userId).list();

		return permissionCodeList;
	}*/

	/**
	 * 查询用户的菜单列表
	 * 
	 * @param userId
	 * @return
	 */
	/*public List<Menu> queryUserMenuList(String userId) {
		String sql = "select distinct m.* from menu m join permission_menu pm "
				+ "where m.menu_id = pm.menu_id and m.menu_rank in (0,1) and pm.permission_id in"
				+ "(select p.permission_id from permission p, role_permission rp where p.permission_id = rp.permission_id and rp.role_id in "
				+ "(select role_id from user_role ur where ur.user_id = :userId))";

		@SuppressWarnings("unchecked")
		List<Menu> menuList = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql)
				.addEntity("m", Menu.class).setString("userId", userId).list();

		return menuList;
	}*/

	/**
	 * 查询用户角色
	 * 
	 * @param userId
	 * @return
	 */
	/*public List<Long> queryUserRoles(Long userId) {
		String sql = "select roleId from UserRole b where b.userId = ?";

		@SuppressWarnings("unchecked")
		List<Long> userRoles = this.getHibernateTemplate().find(sql, userId);

		return userRoles;
	}*/

	@SuppressWarnings("unchecked")
	public List<User> getUsersByOrg(Organization org) {
		String hql = "from User b where b.state=1 AND b.org = ?";
		return this.getHibernateTemplate().find(hql, org);
	}
	
	/**
	 * 更新USER
	 * @param user
	 */
	public void saveOrUpdateUser(User user){
		this.getHibernateTemplate().saveOrUpdate(user);
	}
	
	/**
	 * 获取某此会议下的所有用户
	 * @param meetingId
	 */
	public List<User> getMeetingMember(String hql){
		@SuppressWarnings("unchecked")
		List<User> meetingUserList= this.getHibernateTemplate().find(hql.toString());
		return meetingUserList;
		
	}
	
	/**查询已分桌的用户座位信息
	 * @throws HibernateDaoSupportException */
	@SuppressWarnings("unchecked")
	 /*SELECT u.id,u.mobile,u.name,dd.table_code
	 FROM USER u JOIN dinner_table dd ON dd.member_id = u.id AND dd.dinner_id
	 = 2 WHERE u.id IN (
	 SELECT mm.user_id FROM meeting_member mm WHERE mm.meeting_id = 3
	 AND mm.user_id IN
	 (SELECT dt.member_id FROM dinner_table dt WHERE dt.dinner_id = 2))*/
	public List<UserTableInfo> getUserTableInfo(Long meetingId,Long dinnerId) throws HibernateDaoSupportException {
		try {
			final String sql = "select u.id ,u.mobile ,u.name , "
					+ " dd.table_code "
					+ " from user u "
					+ " join dinner_table dd on dd.member_id = u.id and dd.dinner_id = "
					+ dinnerId
					+ " WHERE u.id IN (SELECT mm.user_id FROM meeting_member mm WHERE mm.meeting_id = "
					+ meetingId
					+ "  AND mm.user_id IN (SELECT dt.member_id FROM dinner_table dt WHERE dt.dinner_id = "
					+ dinnerId + "))" + "  order by dd.table_code ";
			
			List results = queryListSql(sql, -1, -1, new HashMap());
            List<UserTableInfo> resultList = new ArrayList<UserTableInfo>();
            for (Object result : results) {
                Object[] resultArray = (Object[]) result;
                UserTableInfo uti = new UserTableInfo();
                uti.setId(Long.valueOf(""+ resultArray[0]));
                uti.setMobile(""+ resultArray[1]);
                uti.setName(""+ resultArray[2]);
                uti.setTableCode(""+ resultArray[3]);
                uti.setDinnerId(dinnerId);
                resultList.add(uti);
            }
            
            return resultList;
			
        } catch (DataAccessException e) {
            log.error("Error getUserTableInfo:{}", e);
            throw new HibernateDaoSupportException("Error getUserTableInfo " + e);
        }
	}
	
	/**
	 * 根据姓名或手机号查询指定会议的会议成员
	 * 条件：会议ID+（username/mobile,username和mobile至少有一个不为空)
	 */
	public Pager<User> queryMeetingMember(Long meetingId, String username, String mobile, int currentPage, int pageSize) throws ServiceException {
		StringBuffer hql = new StringBuffer();
		hql.append(
				"  select meetingUser from  User meetingUser,MeetingMember meetingMember where meetingUser.id=meetingMember.userId  ")
				.append("  and  meetingMember.meetingId=" + meetingId);
		// 通过手机号来进行查询
		if (StringUtil.isNotEmpty(mobile)) {
			hql.append(" and meetingUser.mobile like '%" + mobile + "%'");
		}
		// 通过userName来进行查询
		if (StringUtil.isNotEmpty(username)) {
			hql.append(" and meetingUser.name like '%" + username + "%'");
		}
		
		hql.append("  order by (case when meetingMember.sortCode is not null then meetingMember.sortCode else 2000 end )  ");

		return this.findPager(hql.toString(), currentPage, pageSize, null);
	}
}

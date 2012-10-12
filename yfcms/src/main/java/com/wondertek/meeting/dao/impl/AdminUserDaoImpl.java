package com.wondertek.meeting.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wondertek.meeting.dao.AdminUserDao;
import com.wondertek.meeting.model.AdminUser;
import com.wondertek.meeting.model.Menu;
import com.wondertek.meeting.model.Organization;
import com.wondertek.meeting.util.Encrypt;

/**
 * 用户Dao
 * 
 */
public class AdminUserDaoImpl extends BaseDaoImpl<AdminUser, Long> implements AdminUserDao {
	Logger log = LoggerFactory.getLogger(this.getClass());

	@SuppressWarnings("unchecked")
	public AdminUser findById(Long id) {
		String hql = "from AdminUser b where b.id = ?";

		AdminUser adminUser = null;

		List<AdminUser> AdminUserInfoList = this.getHibernateTemplate().find(hql, id);
		if (AdminUserInfoList != null && AdminUserInfoList.size() > 0) {
			return AdminUserInfoList.get(0);
		}

		return adminUser;
	}

	/**
	 * 根据用户名获取密码
	 * 
	 * @param id
	 * @return
	 */
	public String findPwdByMobile(String mobile) {
		String hql = "select b.password from AdminUser b where b.mobile = ?";

		@SuppressWarnings("unchecked")
		List<String> list = this.getHibernateTemplate().find(hql, mobile);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}

		return null;
	}

	@Override
	public Long add(AdminUser adminUserInfo) {
		String pwd = adminUserInfo.getPassword();
		try {
			pwd = Encrypt.encrypt(pwd, null);
		} catch (Exception e) {
			log.error("password:" + pwd + "encrypt error!" + e.getMessage());
		}
		adminUserInfo.setPassword(pwd);

		Long adminUserId = null;
		try {
			adminUserId = (Long) this.getHibernateTemplate().save(adminUserInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return adminUserId;
	}

	/**
	 * 根据用户名查询用户信息
	 * 
	 * @return
	 */
	public AdminUser selectByMobile(String mobile) {
		String sql = "from AdminUser b where b.mobile = ?";

		@SuppressWarnings("unchecked")
		List<AdminUser> AdminUserInfoList = this.getHibernateTemplate().find(sql, mobile);
		if (AdminUserInfoList != null && AdminUserInfoList.size() > 0) {
			return AdminUserInfoList.get(0);
		}

		return null;
	}

	public AdminUser selectByAdminUsernameAndPwd(String adminUsername, String pwd) throws Exception {
		String sql = "from AdminUser b where b.mobile = ? and b.password = ?";

		@SuppressWarnings("unchecked")
		List<AdminUser> AdminUserInfoList = this.getHibernateTemplate().find(sql, adminUsername, pwd);

		if (AdminUserInfoList != null && AdminUserInfoList.size() > 0) {
			AdminUser adminUser = AdminUserInfoList.get(0);
			return adminUser;
		}

		return null;
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
		String sql = "from AdminUserInfo b where b.adminUsername = ?";

		@SuppressWarnings("unchecked")
		List<AdminUser> AdminUserInfoList = this.getHibernateTemplate().find(sql, adminUsername);
		if (AdminUserInfoList != null && AdminUserInfoList.size() > 0) {
			AdminUser adminUser = AdminUserInfoList.get(0);
			adminUser.setPassword(newPwd);// 修改密码
			this.getHibernateTemplate().save(adminUser);
		} else {
			return "0";
		}

		return "1";
	}

	/**
	 * 查询用户的权限列表
	 * 
	 * @param adminUserId
	 * @return
	 */
	public List<String> queryPermissionsByAdminUserId(String adminUserId) {

		String sql = "select distinct permission_code from permission p, role_permission rp "
				+ "where p.permission_id = rp.permission_id and rp.role_id in (select role_id from adminUser_role ur where ur.adminUser_id = :adminUserId)";

		@SuppressWarnings("unchecked")
		List<String> permissionCodeList = this.getHibernateTemplate().getSessionFactory().getCurrentSession()
				.createSQLQuery(sql).setString("adminUserId", adminUserId).list();

		return permissionCodeList;
	}

	/**
	 * 查询用户的菜单列表
	 * 
	 * @param adminUserId
	 * @return
	 */
	public List<Menu> queryAdminUserMenuList(String adminUserId) {
		String sql = "select distinct m.* from menu m join permission_menu pm "
				+ "where m.menu_id = pm.menu_id and m.menu_rank in (0,1) and pm.permission_id in"
				+ "(select p.permission_id from permission p, role_permission rp where p.permission_id = rp.permission_id and rp.role_id in "
				+ "(select role_id from adminUser_role ur where ur.adminUser_id = :adminUserId))";

		@SuppressWarnings("unchecked")
		List<Menu> menuList = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql)
				.addEntity("m", Menu.class).setString("adminUserId", adminUserId).list();

		return menuList;
	}

	/**
	 * 查询用户角色
	 * 
	 * @param adminUserId
	 * @return
	 */
	public List<Long> queryAdminUserRoles(Long adminUserId) {
		String sql = "select roleId from AdminUserRole b where b.adminUserId = ?";

		@SuppressWarnings("unchecked")
		List<Long> adminUserRoles = this.getHibernateTemplate().find(sql, adminUserId);

		return adminUserRoles;
	}

	@SuppressWarnings("unchecked")
	public List<AdminUser> getAdminUsersByOrg(Organization org) {
		String hql = "from AdminUser b where b.state=1 AND b.org = ?";
		return this.getHibernateTemplate().find(hql, org);
	}

	/**
	 * 更新USER
	 * 
	 * @param adminUser
	 */
	public void saveOrUpdateAdminUser(AdminUser adminUser) {
		this.getHibernateTemplate().saveOrUpdate(adminUser);
	}

}

package com.wondertek.meeting.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wondertek.meeting.dao.OrganizationDao;
import com.wondertek.meeting.model.Organization;

/**
 * 机构
 * 
 * @author 金祝华
 */
public class OrganizationDaoImpl extends BaseDaoImpl<Organization, Long> implements OrganizationDao {
	Logger log = LoggerFactory.getLogger(this.getClass());

	public List<Organization> findAll() {
		String hql = "from Organization b where b.state = 1";

		@SuppressWarnings("unchecked")
		List<Organization> list = this.getHibernateTemplate().find(hql);

		return list;
	}

	/**
	 * 查询机构列表，一级机构的parentId为null
	 * 
	 * @param parentId
	 * @return
	 */
	public List<Organization> findOrgList(Long parentId) {
		String hql = "from Organization b where b.state = 1 and b.parentId = ?";

		@SuppressWarnings("unchecked")
		List<Organization> list = this.getHibernateTemplate().find(hql, parentId);

		return list;
	}

	/**
	 * 删除组织下属用户
	 * @param orgId
	 * @return
	 */
	public int delUsersByOrgId(Long orgId) {
		String sql = "update admin_user set state = 0 where org_id = :orgId";

		int result = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql)
				.setLong("orgId", orgId).executeUpdate();

		return result;
	}
}

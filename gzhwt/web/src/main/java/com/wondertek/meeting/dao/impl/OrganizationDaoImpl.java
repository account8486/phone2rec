package com.wondertek.meeting.dao.impl;

import java.util.ArrayList;
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
	 * 查询子机构列表，一级机构的parentId为null
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
	 * 查询子机构ID列表
	 * 
	 * @param parentId
	 * @return
	 */
	public List<Long> findChildOrgIdList(Long parentId) {
		String hql = "select id from Organization b where b.state = 1 and b.parentId = ?";

		@SuppressWarnings("unchecked")
		List<Long> list = this.getHibernateTemplate().find(hql, parentId);

		return list;
	}

	/**
	 * 查询子组织id列表（包含自身）
	 * 
	 * @param orgId
	 * @return
	 */
	public List<Long> getChildOrgIdListWithSelf(Long orgId) {

		List<Long> childOrgIdList = new ArrayList<Long>();

		// 加入自身
		childOrgIdList.add(orgId);

		this.getChildOrgIdList(orgId, childOrgIdList);

		return childOrgIdList;
	}

	/**
	 * 查询子组织id列表
	 * 
	 * @param parentId
	 * @param temp
	 * @return
	 */
	public List<Long> getChildOrgIdList(Long parentId, List<Long> temp) {
		if (temp == null) {
			temp = new ArrayList<Long>();
		}

		List<Long> list = this.findChildOrgIdList(parentId);

		if (list != null && list.size() > 0) {
			temp.addAll(list);
			for (Long id : list) {
				getChildOrgIdList(id, temp); // 获取子节点
			}
		}

		return temp;
	}

	/**
	 * 删除组织下属用户
	 * 
	 * @param orgId
	 * @return
	 */
	public int delUsersByOrgId(Long orgId) {
		String sql = "update admin_user set state = -1 where org_id = :orgId";

		int result = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql)
				.setLong("orgId", orgId).executeUpdate();

		return result;
	}
}

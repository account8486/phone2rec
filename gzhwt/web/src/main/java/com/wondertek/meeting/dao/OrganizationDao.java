package com.wondertek.meeting.dao;

import java.util.List;

import com.wondertek.meeting.model.Organization;

/**
 * 机构
 * 
 * @author 金祝华
 */
public interface OrganizationDao extends BaseDao<Organization, Long> {

	public List<Organization> findAll();

	/**
	 * 查询机构列表，一级机构的parentId为null
	 * 
	 * @param parentId
	 * @return
	 */
	public List<Organization> findOrgList(Long parentId);
	
	/**
	 * 查询子组织id列表（包含自身）
	 * 
	 * @param orgId
	 * @return
	 */
	public List<Long> getChildOrgIdListWithSelf(Long orgId);
	
	/**
	 * 删除组织下属用户
	 * @param orgId
	 * @return
	 */
	public int delUsersByOrgId(Long orgId);
}

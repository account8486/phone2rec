package com.wondertek.meeting.service;

import java.util.List;

import com.wondertek.meeting.client.view.TreeView;
import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.Organization;

/**
 * 机构
 * 
 * @author 金祝华
 */
public interface OrganizationService extends BaseService<Organization, Long> {

	/**
	 * 分页列表
	 * 
	 * @param user
	 * @param currentPage
	 * @param pageSize
	 * @return
	 * @throws ServiceException
	 */
	public Pager<Organization> listPager(Organization user, List<Long> idList, int currentPage, int pageSize)
			throws ServiceException;

	public List<Organization> findAll();

	public List<TreeView> getTree(Long id, boolean checked, boolean expand);
	/**
	 * 
	 * @param orgId
	 * @param checked
	 * @param expand
	 * @return
	 */
	public List<TreeView> getTreeWithUser(Long orgId, boolean checked, boolean expand);
	

	/**
	 * 查询孩子机构列表
	 * 
	 * @param parentId
	 * @return
	 */
	public List<Organization> findChildren(Long parentId, List<Organization> temp);

	/**
	 * 删除组织下属用户
	 * 
	 * @param orgId
	 * @return
	 */
	public int delUsersByOrgId(Long orgId);
	
	/**
	 * 删除组织机构
	 * @param orgId
	 */
	public int delOrg(Long orgId);
}

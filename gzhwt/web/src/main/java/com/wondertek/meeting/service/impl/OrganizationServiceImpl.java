package com.wondertek.meeting.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wondertek.meeting.client.view.TreeView;
import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.dao.AdminUserDao;
import com.wondertek.meeting.dao.OrganizationDao;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.AdminUser;
import com.wondertek.meeting.model.Organization;
import com.wondertek.meeting.service.OrganizationService;
import com.wondertek.meeting.util.StringUtil;

/**
 * 机构
 * 
 * @author 金祝华
 */
public class OrganizationServiceImpl extends BaseServiceImpl<Organization, Long> implements OrganizationService {

	private OrganizationDao organizationDao;
	
	private AdminUserDao adminUserDao;

	/**
	 * 分页列表
	 * 
	 * @param user
	 * @param currentPage
	 * @param pageSize
	 * @return
	 * @throws ServiceException
	 */
	public Pager<Organization> listPager(Organization org, List<Long> idList, int currentPage, int pageSize)
			throws ServiceException {
		String hql = "from Organization where state = 1 and id in (:idList) and name like '%'||:name||'%' order by modifyTime desc";

		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("name", StringUtil.null2Str(org.getName()));
		properties.put("idList", idList);

		try {
			return organizationDao.findPager(hql, currentPage, pageSize, properties);
		} catch (Exception e) {
			final String errMsg = "Find meeting pager error ";
			log.error(errMsg, e);
			throw new ServiceException(errMsg, e);
		}
	}

	public List<Organization> findAll() {
		return organizationDao.findAll();
	}

	/**
	 * 查询孩子机构列表
	 * 
	 * @param parentId
	 * @return
	 */
	public List<Organization> findChildren(Long parentId, List<Organization> temp) {

		if (temp == null) {
			temp = new ArrayList<Organization>();
		}

		List<Organization> list = organizationDao.findOrgList(parentId);

		if (list != null && list.size() > 0) {
			temp.addAll(list);
			for (Organization o : list) {
				findChildren(o.getId(), temp); // 获取子节点
			}
		}

		return temp;
	}

	/**
	 * 删除组织机构
	 * 
	 * @param orgId
	 * @return 0:删除失败，1：删除成功
	 */
	public int delOrg(Long orgId) {
		Organization oldOrg = null;
		try {
			oldOrg = findById(orgId);
		} catch (ServiceException e) {
			log.error("查询组织失败，组织id=" + orgId, e);
		}

		if (oldOrg == null) {
			return 0;
		}

		oldOrg.setState(0);
		oldOrg.setModifyTime(new Date());

		// 更新
		try {
			modify(oldOrg);

			// 删除组织下属用户
			delUsersByOrgId(orgId);

			delChildren(orgId);// 删除下属组织
		} catch (ServiceException e) {
			log.debug("删除组织失败，组织id=" + orgId, e);
		}

		return 1;
	}

	/**
	 * 删除下属组织
	 * 
	 * @param parentId
	 */
	public void delChildren(Long parentId) {
		List<Organization> orgList = new ArrayList<Organization>();

		this.findChildren(parentId, orgList);

		// 循环删除
		if (orgList != null) {
			for (Organization org : orgList) {

				org.setState(0);
				org.setModifyTime(new Date());

				try {
					modify(org);

					// 删除组织下属用户
					delUsersByOrgId(org.getId());
				} catch (ServiceException e) {
					log.error("删除组织失败，组织id=" + org.getId(), e);
					continue;
				}
			}
		}
	}

	/**
	 * 
	 * @param orgId
	 * @param checked
	 * @param expand
	 * @return
	 */
	public List<TreeView> getTree(Long orgId, boolean checked, boolean expand) {
		List<TreeView> viewList = new ArrayList<TreeView>();

		Organization selfOrg = null;
		try {
			selfOrg = findById(orgId);
		} catch (ServiceException e) {
			log.error("获取组织树失败", e);
		}

		if (selfOrg == null) {
			log.error("获取组织树失败，根组织不存在，orgId=" + orgId);
			return null;
		}

		TreeView view = new TreeView();
		viewList.add(view);

		view.setId(String.valueOf(selfOrg.getId())); // id
		view.setText(selfOrg.getName()); // 名称
		view.setExpanded(String.valueOf(expand));

		view.setLeaf("false"); // 是否叶子
		view.setCls("folder");
		view.setChildren(getChildren(selfOrg.getId(), checked, expand)); // 获取子节点

		return viewList;
	}

	public List<TreeView> getChildren(Long parentId, boolean checked, boolean expand) {
		List<TreeView> viewList = new ArrayList<TreeView>();

		List<Organization> list = organizationDao.findOrgList(parentId);

		if (list != null && list.size() > 0) {
			for (Organization o : list) {
				TreeView view = new TreeView();
				viewList.add(view);

				view.setId(String.valueOf(o.getId())); // id
				view.setText(o.getName()); // 名称
				view.setExpanded(String.valueOf(expand));

				List<Organization> children = organizationDao.findOrgList(o.getId());
				if (children != null && children.size() > 0) {
					view.setLeaf("false"); // 是否叶子
					view.setCls("folder");
					view.setChildren(getChildren(o.getId(), checked, expand)); // 获取子节点
				} else {
					view.setLeaf("true"); // 是否叶子
					view.setCls("file");
				}
			}
		}

		return viewList;
	}
	
	/**
	 * 
	 * @param orgId
	 * @param checked
	 * @param expand
	 * @return
	 */
	public List<TreeView> getTreeWithUser(Long orgId, boolean checked, boolean expand) {
		List<TreeView> viewList = new ArrayList<TreeView>();

		Organization selfOrg = null;
		try {
			selfOrg = findById(orgId);
		} catch (ServiceException e) {
			log.error("获取组织树失败", e);
		}

		if (selfOrg == null) {
			log.error("获取组织树失败，根组织不存在，orgId=" + orgId);
			return null;
		}

		TreeView view = new TreeView();
		viewList.add(view);

		view.setId("o"+ String.valueOf(selfOrg.getId())); // id
		view.setText(selfOrg.getName()); // 名称
		view.setExpanded(String.valueOf(expand));

		view.setLeaf("false"); // 是否叶子
		view.setCls("folder");
		view.setChildren(getChildrenWithUser(selfOrg.getId(), checked, expand)); // 获取子节点

		return viewList;
	}

	public List<TreeView> getChildrenWithUser(Long parentId, boolean checked, boolean expand) {
		List<TreeView> viewList = new ArrayList<TreeView>();

		List<Organization> list = organizationDao.findOrgList(parentId);

		if (list != null && list.size() > 0) {
			for (Organization o : list) {
				TreeView view = new TreeView();
				viewList.add(view);

				view.setId("o"+ String.valueOf(o.getId())); // id
				view.setText(o.getName()); // 名称
				view.setExpanded(String.valueOf(expand));

				List<Organization> children = organizationDao.findOrgList(o.getId());
				if (children != null && children.size() > 0) {
					view.setLeaf("false"); // 是否叶子
					view.setCls("folder");
					view.setChildren(getChildrenWithUser(o.getId(), checked, expand)); // 获取子节点
				} else {
					view.setLeaf("true"); // 是否叶子
					view.setCls("file");
				}
			}
		}
		
		// 用户列表
		Organization org = new Organization();
		org.setId(parentId);
		List<AdminUser> adminUserList = adminUserDao.getAdminUsersByOrg(org);
		if (adminUserList != null && adminUserList.size() > 0)
		{
			for (AdminUser u: adminUserList)
			{
				TreeView view = new TreeView();
				viewList.add(view);

				view.setId("u" + String.valueOf(u.getId())); // id
				view.setText(u.getName()); // 名称
				view.setExpanded(String.valueOf(expand));

				view.setLeaf("true"); // 是否叶子
				view.setCls("person");
			}
		}

		return viewList;
	}

	/**
	 * 删除组织下属用户
	 * 
	 * @param orgId
	 * @return
	 */
	public int delUsersByOrgId(Long orgId) {
		return organizationDao.delUsersByOrgId(orgId);
	}

	public OrganizationDao getOrganizationDao() {
		return organizationDao;
	}

	public void setOrganizationDao(OrganizationDao organizationDao) {
		this.basicDao = organizationDao;
		this.organizationDao = organizationDao;
	}

	public AdminUserDao getAdminUserDao() {
		return adminUserDao;
	}

	public void setAdminUserDao(AdminUserDao adminUserDao) {
		this.adminUserDao = adminUserDao;
	}
}

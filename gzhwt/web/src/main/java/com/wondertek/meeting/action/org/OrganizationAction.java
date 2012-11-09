package com.wondertek.meeting.action.org;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.wondertek.meeting.action.base.BaseAction;
import com.wondertek.meeting.client.view.TreeView;
import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.model.AdminUser;
import com.wondertek.meeting.model.Organization;
import com.wondertek.meeting.service.OrganizationService;
import com.wondertek.meeting.util.StringUtil;

/**
 * 机构管理
 * 
 * @author 金祝华
 */
public class OrganizationAction extends BaseAction {

	private static final long serialVersionUID = -3102151360541524276L;

	private OrganizationService organizationService;

	private Organization org;
	private Long pOrgId;// 父组织id

	public List<TreeView> treeList;

	private String errMsg;

	/**
	 * 列表查询
	 * 
	 * @return
	 */
	public String list() {
		if (org == null) {
			org = new Organization();
		}

		// 管理员只能查看自己所属组织及下级组织的组织列表
		AdminUser sessionUser = this.getAdminUserFromSession();
		Long orgId = sessionUser.getOrg().getId();

		// 如果前台传入的组织id不为空，则以前台id为基准。（前台传入的组织id必定不高于管理员所属组织）
		if (pOrgId != null) {
			orgId = pOrgId;
		} else {
			pOrgId = orgId;// 回设给前台使用，基本上没用，因为前台过来总会带上org.id
		}

		// 1、查询管理员所属组织及下级组织的列表
		List<Organization> orgList = null;
		try {
			Organization selfOrg = organizationService.findById(orgId);

			orgList = new ArrayList<Organization>();
			orgList.add(selfOrg);

			organizationService.findChildren(orgId, orgList);

		} catch (ServiceException e1) {
			e1.printStackTrace();
		}

		// 2、列表id
		List<Long> idList = null;
		if (orgList != null) {
			idList = new ArrayList<Long>();
			for (Organization org : orgList) {
				idList.add(org.getId());
			}
		}

		if (idList == null) {
			return SUCCESS;
		}

		// 3、查询列表
		Pager<Organization> orgPager = null;
		try {
			orgPager = organizationService.listPager(org, idList, currentPage, pageSize);
		} catch (ServiceException e) {
			e.printStackTrace();
		}

		getRequest().setAttribute("pager", orgPager);

		return SUCCESS;
	}

	/**
	 * 列表查询
	 * 
	 * @return
	 */
	public String listLevel() {
		if (org == null) {
			org = new Organization();
		}

		AdminUser sessionUser = this.getAdminUserFromSession();
		Long orgId = sessionUser.getOrg().getId();

		treeList = organizationService.getTree(orgId, false, false);

		return SUCCESS;
	}
	
	public String getTreeWithUser() {

		AdminUser sessionUser = this.getAdminUserFromSession();
		Long orgId = sessionUser.getOrg().getId();

		treeList = organizationService.getTreeWithUser(orgId, false, true);

		return this.json2Resp(treeList);
	}

	/**
	 * 跳转到新增组织机构
	 * 
	 * @return
	 */
	public String goAdd() {

		/**
		 * // 只能添加管理员所属组织及下级组织 AdminUser sessionUser =
		 * this.getAdminUserFromSession(); Long orgId =
		 * sessionUser.getOrg().getId();
		 * 
		 * List<Organization> orgList = null; try { Organization selfOrg =
		 * organizationService.findById(orgId);
		 * 
		 * orgList = new ArrayList<Organization>(); orgList.add(selfOrg);
		 * 
		 * organizationService.findChildren(orgId, orgList);
		 * 
		 * } catch (ServiceException e1) { e1.printStackTrace(); }
		 * 
		 * this.getRequest().setAttribute("orgList", orgList);
		 * 
		 * // 查询管理员所属机构level this.getRequest().setAttribute("orgLevel",
		 * this.getOrgLevelFromSession());
		 */

		// 上级组织及上级组织级别
		Organization pOrg = null;
		try {
			pOrg = organizationService.findById(pOrgId);
		} catch (ServiceException e) {
			e.printStackTrace();
		}

		if (pOrg == null) {
			return ERROR;
		}

		this.getRequest().setAttribute("pOrg", pOrg);
		this.getRequest().setAttribute("orgLevel", pOrg.getLevel());

		return SUCCESS;
	}

	/**
	 * 新增机构
	 * 
	 * @return
	 */
	public String add() {
		if (org == null) {
			errMsg = "请输入机构信息。";
			return INPUT;
		}

		org.setModifyTime(new Date());
		org.setState(1);

		// 新增
		try {
			organizationService.add(org);
		} catch (ServiceException e) {
			log.error("org add failed!", e);
			errMsg = "新增机构失败。";
			return INPUT;
		}

		return SUCCESS;
	}

	/**
	 * 跳转到修改机构
	 * 
	 * @return
	 */
	public String goUpdate() {

		String id = this.getParameter("id");

		Long idL = StringUtil.stringToLong(id);

		Organization organization = null;
		try {
			organization = organizationService.findById(idL);
		} catch (ServiceException e) {
			String errCode = e.getMessage();
			log.error("errCode:" + errCode + "detail:" + e.toString());
		}

		this.getRequest().setAttribute("org", organization);

		// 上级组织及上级组织级别
		Organization pOrg = organization.getParent();

		if (pOrg == null) {
			return ERROR;
		}

		this.getRequest().setAttribute("pOrg", pOrg);
		this.getRequest().setAttribute("orgLevel", pOrg.getLevel());

		return SUCCESS;
	}

	/**
	 * 修改机构
	 * 
	 * @return
	 */
	public String update() {
		if (org == null) {
			this.addFieldError("errMsg", "请输入机构信息。");
			return INPUT;
		}

		Organization oldOrg = null;
		try {
			oldOrg = organizationService.findById(org.getId());
		} catch (ServiceException e1) {
			e1.printStackTrace();
		}

		if (oldOrg == null) {
			this.addFieldError("errMsg", getText("org.update.fail"));
			return INPUT;
		}

		oldOrg.setName(org.getName());
		oldOrg.setAddress(org.getAddress());
		oldOrg.setComments(org.getComments());
		oldOrg.setModifyTime(new Date());
		oldOrg.setState(org.getState());
		oldOrg.setParentId(org.getParentId());
		oldOrg.setLevel(org.getLevel());
		oldOrg.setLinker(org.getLinker());
		oldOrg.setLinkerTel(org.getLinkerTel());

		if (org.getId().equals(0L)) {
			oldOrg.setState(1);
			oldOrg.setParentId(null);
			oldOrg.setLevel(0);
		}

		// 更新
		try {
			organizationService.modify(oldOrg);

			// 更新为无效时，即删除该组织
			if (oldOrg.getState().equals(0)) {
				organizationService.delOrg(org.getId());
			}
		} catch (ServiceException e) {
			this.addFieldError("errMsg", getText("org.update.fail"));
			return INPUT;
		}

		return SUCCESS;
	}

	/**
	 * 删除机构
	 * 
	 * @return
	 */
	public String del() {

		// 组织id
		Long orgId = StringUtil.stringToLong(this.getParameter("id"));

		Long selfOrgId = getOrgIdFromSession();

		if (selfOrgId.equals(orgId)) {
			errMsg = "对不起，不能删除您自己所在的组织。";
			return SUCCESS;
		}

		int result = organizationService.delOrg(orgId);

		if (result == 1) {
			errMsg = "删除成功。";
		} else {
			errMsg = "对不起，删除失败。";
		}

		return SUCCESS;
	}

	public OrganizationService getOrganizationService() {
		return organizationService;
	}

	public void setOrganizationService(OrganizationService organizationService) {
		this.organizationService = organizationService;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public Long getPOrgId() {
		return pOrgId;
	}

	public void setPOrgId(Long pOrgId) {
		this.pOrgId = pOrgId;
	}

	public Organization getOrg() {
		return org;
	}

	public void setOrg(Organization org) {
		this.org = org;
	}
}

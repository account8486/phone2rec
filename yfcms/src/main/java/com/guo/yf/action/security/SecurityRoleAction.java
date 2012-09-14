package com.guo.yf.action.security;

import java.util.Date;
import java.util.List;

import com.guo.yf.model.security.SecurityRole;
import com.guo.yf.model.security.SecurityRoleUnit;
import com.guo.yf.service.SecurityRoleService;
import com.guo.yf.service.SecurityRoleUnitService;
import com.guo.yf.service.SecurityUnitService;
import com.guo.yf.vo.SecurityUnitView;
import com.wondertek.meeting.action.base.BaseAction;
import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.util.StringUtil;

/**
 * 角色权限用户管理
 * 角色
 */
public class SecurityRoleAction extends BaseAction {

	private static final long serialVersionUID = -6862873421962761725L;
	
	SecurityRoleService securityRoleService;
	SecurityUnitService securityUnitService;
	SecurityRoleUnitService securityRoleUnitService;

	public SecurityRoleUnitService getSecurityRoleUnitService() {
		return securityRoleUnitService;
	}

	public void setSecurityRoleUnitService(
			SecurityRoleUnitService securityRoleUnitService) {
		this.securityRoleUnitService = securityRoleUnitService;
	}

	public SecurityUnitService getSecurityUnitService() {
		return securityUnitService;
	}

	public void setSecurityUnitService(SecurityUnitService securityUnitService) {
		this.securityUnitService = securityUnitService;
	}

	public SecurityRoleService getSecurityRoleService() {
		return securityRoleService;
	}

	public void setSecurityRoleService(SecurityRoleService securityRoleService) {
		this.securityRoleService = securityRoleService;
	}

	
	public String list() {
		
		Pager<SecurityRole> pager=securityRoleService.getRolsPager(currentPage, pageSize);
		getRequest().setAttribute("pager",pager);
		return SUCCESS;
	}
	
	
	/**
	 * to add role 
	 * get security units list
	 * provider to choose
	 * @return
	 * @throws ServiceException
	 */
	public String toAddRole() throws ServiceException {
		//TODO
		//List<SecurityUnit> unitList=securityUnitService.find(SecurityUnit.class);
		//获取第一级菜单列表
		List<SecurityUnitView> unitViewList= securityUnitService.getCascadeUnitOfMenu();
		getRequest().setAttribute("unitViewList",unitViewList);
		return SUCCESS;
	}

	/**
	 * 角色添加
	 * 编辑
	 * @return
	 * @throws ServiceException
	 */
	public String addRole() throws ServiceException {
		String roleId = this.getParameter("roleId");
		String roleName = this.getParameter("roleName");
		String roleDescription = this.getParameter("roleDescription");
		String[] unitIds = getRequest().getParameterValues("unitId");
		SecurityRole role = new SecurityRole();
		if (StringUtil.isNotEmpty(roleId)) {
			role = securityRoleService.findById(Long.valueOf(roleId));
			role.setModifyTime(new Date());
			role.setModifier(this.getAdminUserIdFromSession());
		} else {
			role.setCreateTime(new Date());
			role.setCreator(this.getAdminUserIdFromSession());
		}
		// 修改值
		role.setRoleName(roleName);
		role.setRoleDescription(roleDescription);
		securityRoleService.saveOrUpdate(role);

		// 获取当前角色对应的菜单,把已经舍弃的菜单信息删除
		List<SecurityRoleUnit> roleUnitOldList = securityRoleUnitService
				.getRoleUnitsByRoleId(role.getId());

		if (roleUnitOldList != null && roleUnitOldList.size() > 0) {
			StringBuffer newIds = new StringBuffer();
			if (unitIds != null && unitIds.length > 0) {
				for (int i = 0; i < unitIds.length; i++) {
					newIds.append(unitIds[i] + ",");
				}
			}
			for (SecurityRoleUnit roleUnit : roleUnitOldList) {
				String roleUnitId = roleUnit.getUnitId() + ",";
				// 老的不在,delete it
				if (newIds.indexOf(roleUnitId) <= 0) {
					securityRoleUnitService.delete(roleUnit);
				}
			}
		}

		// 对于角色与菜单关联的表进行处理
		if (unitIds != null && unitIds.length > 0) {
			for (int i = 0; i < unitIds.length; i++) {
				// 通过roleId 与unit id来进行查询
				SecurityRoleUnit roleUnit = securityRoleUnitService
						.getRoleUnitByUniqueKey(role.getId(),
								Long.valueOf(unitIds[i]));
				if (roleUnit != null) {
					// 编辑
					roleUnit.setModifyTime(new Date());
					roleUnit.setModifier(this.getAdminUserIdFromSession());
				} else {
					// 新增
					roleUnit = new SecurityRoleUnit();
					roleUnit.setRoleId(role.getId());
					roleUnit.setUnitId(Long.valueOf(unitIds[i]));
					roleUnit.setCreateTime(new Date());
					roleUnit.setCreator(this.getAdminUserIdFromSession());
				}
				securityRoleUnitService.saveOrUpdate(roleUnit);
			}
		}

		this.resultMap.put("retMsg", "保存成功！");
		return SUCCESS;
	}
	
	
	
	
	public String toEditRole() throws NumberFormatException, ServiceException{
		String roleId=this.getParameter("roleId");
		SecurityRole role=securityRoleService.findById(Long.valueOf(roleId));
		getRequest().setAttribute("role",role);
		
		
		//获取第一级菜单列表
		List<SecurityUnitView> unitViewList= securityUnitService.getCascadeUnitOfMenu();
		getRequest().setAttribute("unitViewList",unitViewList);
		
		//获取当前角色对应的菜单
		 List<SecurityRoleUnit>  roleUnitList=  securityRoleUnitService.getRoleUnitsByRoleId(role.getId());
		 StringBuffer unitIds=new StringBuffer();
		 for(SecurityRoleUnit roleUnit: roleUnitList){
			 //role unit
			 unitIds.append(roleUnit.getUnitId()).append(",");
		 }
		 
		getRequest().setAttribute("unitIds",unitIds.toString());
		return SUCCESS;
		
	}
	
	/**
	 * 删除
	 * @return
	 * @throws NumberFormatException
	 * @throws ServiceException
	 */
	public String deleteRole() throws NumberFormatException, ServiceException {
		String roleId = this.getParameter("roleId");
		SecurityRole role = securityRoleService.findById(Long.valueOf(roleId));
		securityRoleService.delete(role);
		// 把对应的信息删除
		securityRoleUnitService.deleteRoleUnitsByRoleId(Long.valueOf(roleId));
		this.resultMap.put("retCode", "1");
		this.resultMap.put("retMsg", "删除成功");
		
		

		return this.list();
	}

	public String delete() {
		return SUCCESS;
	}

}

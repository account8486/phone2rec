package com.guo.yf.action.security;

import java.util.List;

import com.guo.yf.model.security.SecurityUnit;
import com.guo.yf.service.SecurityUnitService;
import com.wondertek.meeting.action.base.BaseAction;
import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.exception.ServiceException;

/**
 * 角色权限用户管理 角色
 */
public class SecurityUnitAction extends BaseAction {

	private static final long serialVersionUID = 7831361905693443490L;

	SecurityUnitService securityUnitService;

	public SecurityUnitService getSecurityUnitService() {
		return securityUnitService;
	}

	public void setSecurityUnitService(SecurityUnitService securityUnitService) {
		this.securityUnitService = securityUnitService;
	}

	SecurityUnit unit;

	public SecurityUnit getUnit() {
		return unit;
	}

	public void setUnit(SecurityUnit unit) {
		this.unit = unit;
	}

	/**
	 * 获取菜单列表
	 */
	public String list() {
		String unitName = this.getParameter("unitName");
		String unitType = this.getParameter("unitType");
		String unitParentId = this.getParameter("unitParentId");

		log.debug("unitName:" + unitName + ",unitType:" + unitType);
		// 获取菜单的分页信息
		Pager<SecurityUnit> pager = securityUnitService.getSecurityUnitPager(
				currentPage, pageSize, unitName, unitType, unitParentId);

		// 获取一级菜单的列表
		List<SecurityUnit> unitParentList = securityUnitService
				.getParentUnitList();
		this.setAttribute("unitParentList", unitParentList);

		// 返回到页面
		this.setAttribute("pager", pager);
		this.setAttribute("unitName", unitName);
		this.setAttribute("unitType", unitType);
		this.setAttribute("unitParentId", unitParentId);

		return SUCCESS;
	}

	/**
	 * goTo 编辑页面
	 * 
	 * @return
	 * @throws NumberFormatException
	 * @throws ServiceException
	 */
	public String toUpdateUnit() throws NumberFormatException, ServiceException {
		String id = this.getParameter("id");
		SecurityUnit securityUnit = securityUnitService.findById(Long
				.valueOf(id));

		// 获取一级菜单的列表
		List<SecurityUnit> unitParentList = securityUnitService
				.getParentUnitList();
		this.setAttribute("unitParentList", unitParentList);
		this.setAttribute("unit", securityUnit);

		return SUCCESS;

	}

	/**
	 * goTo 编辑页面
	 * 
	 * @return
	 * @throws NumberFormatException
	 * @throws ServiceException
	 */
	public String updateUnit() throws NumberFormatException, ServiceException {
		String id = this.getParameter("id");
		SecurityUnit securityUnit = securityUnitService.findById(Long
				.valueOf(id));
		this.setAttribute("securityUnit", securityUnit);
		return SUCCESS;
	}

	/**
	 * 删除当前菜单
	 * 
	 * @return
	 * @throws NumberFormatException
	 * @throws ServiceException
	 */
	public String deleteUnit() throws NumberFormatException, ServiceException {
		String id = this.getParameter("id");
		SecurityUnit securityUnit = securityUnitService.findById(Long
				.valueOf(id));
		securityUnitService.delete(securityUnit);
		
		return this.list();

	}

}

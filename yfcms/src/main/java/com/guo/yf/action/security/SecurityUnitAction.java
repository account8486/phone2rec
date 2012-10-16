package com.guo.yf.action.security;

import java.util.Date;
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
	 * 跳转到添加菜单的功能
	 * @return
	 * @throws NumberFormatException
	 * @throws ServiceException
	 */
	public String toAddUnit() throws NumberFormatException, ServiceException {
		// 获取当前的父菜单的信息
		SecurityUnit parentSecurityUnit = securityUnitService.findById(Long
				.valueOf(this.getParameter("unitParentId")));

		// 获取一级菜单的列表
		List<SecurityUnit> unitParentList = securityUnitService
				.getParentUnitList();

		this.setAttribute("unitParentList", unitParentList);
		this.setAttribute("parentSecurityUnit", parentSecurityUnit);
		
		return SUCCESS;
	}
	
	
	/**
	 * 添加
	 * @return
	 * @throws ServiceException 
	 */
	public String addUnit(){
		try{
		this.unit.setCreateTime(new Date());
		this.unit.setCreator(this.getAdminUserIdFromSession());
		securityUnitService.saveOrUpdate(this.unit);
		this.resultMap.put("retMsg", "新增成功！");
		}catch(ServiceException e){
			this.resultMap.put("retMsg", e.getMessage());
		}catch(Exception e){
			this.resultMap.put("retMsg", e.getMessage());
		}
		
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
	
		SecurityUnit securityUnit = securityUnitService.findById(this.unit.getId());

		String typeFlag = this.getParameter("typeFlag");
		// 默认更新的字段
		securityUnit.setUnitName(this.unit.getUnitName());
		securityUnit.setUnitDescription(this.getUnit().getUnitDescription());
		securityUnit.setModifyTime(new Date());
		securityUnit.setModifier(this.getAdminUserIdFromSession());
		securityUnit.setOrderCode(this.getUnit().getOrderCode());
		securityUnit.setModifyTime(new Date());
		securityUnit.setModifier(this.getAdminUserIdFromSession());
		
		if (!"0".equals(typeFlag)) {
			securityUnit.setUnitUrl(this.getUnit().getUnitUrl());
		}

		securityUnitService.saveOrUpdate(securityUnit);
		this.resultMap.put("retMsg", "保存成功！");

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
	
	/**
	 * 批量删除
	 * @return
	 * @throws ServiceException 
	 * @throws NumberFormatException 
	 */
	public String batchDelUnits() throws NumberFormatException,
			ServiceException {
		String ids = this.getParameter("ids");
		log.debug("ids:" + ids);
		String[] idsArr = ids.split(",");

		for (int i = 0; i < idsArr.length; i++) {
			SecurityUnit securityUnit = securityUnitService.findById(Long
					.valueOf(idsArr[i]));
			securityUnitService.delete(securityUnit);
		}

		return this.list();
	}
	
	

}

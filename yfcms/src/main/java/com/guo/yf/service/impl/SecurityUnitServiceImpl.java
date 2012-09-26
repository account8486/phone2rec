package com.guo.yf.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.guo.yf.dao.SecurityUnitDao;
import com.guo.yf.model.security.SecurityUnit;
import com.guo.yf.service.SecurityUnitService;
import com.guo.yf.vo.SecurityUnitView;
import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.exception.HibernateDaoSupportException;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.service.impl.BaseServiceImpl;
import com.wondertek.meeting.util.StringUtil;

public class SecurityUnitServiceImpl extends BaseServiceImpl<SecurityUnit, Long> implements
 SecurityUnitService {

	SecurityUnitDao securityUnitDao;

	public SecurityUnitDao getSecurityUnitDao() {
		return securityUnitDao;
	}

	public void setSecurityUnitDao(SecurityUnitDao securityUnitDao) {
		this.securityUnitDao = securityUnitDao;
		this.basicDao = securityUnitDao;
	}

	/**
	 * 获取基础菜单列表
	 * 
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public Pager<SecurityUnit> getSecurityUnitPager(int currentPage,
			int pageSize, String unitName, String unitType,String unitParentId) {
		Pager<SecurityUnit> unitPager = null;
		StringBuilder sb = new StringBuilder();
		sb.append("  select unit from SecurityUnit unit where 1=1 ");

		if (StringUtil.isNotEmpty(unitName)) {
			sb.append(" and unitName like '%" + unitName + "%'");
		}

		if (StringUtil.isNotEmpty(unitType)) {
			sb.append(" and unitType=:unitType ");
		}
		
		if(StringUtil.isNotEmpty(unitParentId)){
			sb.append(" and unitParentId=:unitParentId ");
		}
		
		sb.append(" order by unit.orderCode ");
		
		
		//参数
		Map<String, Object> properties=new HashMap<String, Object>();
		properties.put("unitType", unitType);
		properties.put("unitParentId", unitParentId);
		
		try {
			unitPager = securityUnitDao.findPager(sb.toString(), currentPage,
					pageSize, properties);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return unitPager;
	}
	
	/**
	 * 获取基础菜单中的一级菜单
	 * @return
	 */
	public List<SecurityUnit> getParentUnitList() {
		try {
			StringBuilder sb = new StringBuilder();
			// 查出一级根菜单
			sb.append(" select unit from  SecurityUnit unit where unit.unitType='0'  ");
			List<SecurityUnit> unitParentList;

			unitParentList = securityUnitDao.getObjects(sb.toString(), null);

			return unitParentList;
		} catch (HibernateDaoSupportException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public List<SecurityUnitView> getCascadeUnitOfMenu()
			throws HibernateDaoSupportException {
		StringBuilder sb = new StringBuilder();
		// 查出一级根菜单
		sb.append(" select unit from  SecurityUnit unit where unit.unitType='0'  ");
		List<SecurityUnit> unitParentList = securityUnitDao.getObjects(
				sb.toString(), null);

		List<SecurityUnitView> securityUnitViewLst = new ArrayList<SecurityUnitView>();
		for (SecurityUnit parentUnit : unitParentList) {
			// 查出二级根菜单
			SecurityUnitView securityUnitView = new SecurityUnitView();
			securityUnitView.setSecurityParentUnit(parentUnit);
			sb = new StringBuilder();
			sb.append(" select unit from  SecurityUnit unit where unit.unitType='1' and  unit.unitParentId="
					+ parentUnit.getId());
			List<SecurityUnit> sonUnitList = securityUnitDao.getObjects(
					sb.toString(), null);
			securityUnitView.setSonUnitList(sonUnitList);

			securityUnitViewLst.add(securityUnitView);

		}

		return securityUnitViewLst;

	}

	/**
	 * 通过管理员权限获取其菜单的权限
	 * 
	 * @return
	 * @throws HibernateDaoSupportException
	 */
	public List<SecurityUnitView> getMenuUnitsByRoleId(Integer roleId,
			String meetingId, String ctx) throws HibernateDaoSupportException {
		StringBuilder sb = new StringBuilder();
		// 查出一级根菜单
		sb.append(" SELECT unit FROM SecurityUnit unit,SecurityRoleUnit roleUnit WHERE unit.id=roleUnit.unitId and unit.unitType='0'  ");
		sb.append(" AND roleUnit.roleId=" + roleId);
		sb.append(" order by unit.orderCode asc ");
		List<SecurityUnit> unitParentList = securityUnitDao.getObjects(
				sb.toString(), null);

		List<SecurityUnitView> securityUnitViewLst = new ArrayList<SecurityUnitView>();
		for (SecurityUnit parentUnit : unitParentList) {
			// 查出二级根菜单
			SecurityUnitView securityUnitView = new SecurityUnitView();
			securityUnitView.setSecurityParentUnit(parentUnit);
			sb = new StringBuilder();
			sb.append(" SELECT unit FROM SecurityUnit unit,SecurityRoleUnit roleUnit WHERE unit.id=roleUnit.unitId and unit.unitType='1' and  unit.unitParentId="
					+ parentUnit.getId());
			sb.append(" AND roleUnit.roleId=" + roleId);
			//按照排序码排列
			sb.append(" order by unit.orderCode asc ");

			List<SecurityUnit> sonUnitList = securityUnitDao.getObjects(
					sb.toString(), null);
			for (SecurityUnit unit : sonUnitList) {
				if (StringUtil.isNotEmpty(unit.getUnitUrl())) {
					unit.setUnitUrl(unit.getUnitUrl()
							.replace("${ctx}", ctx));
				}
			}

			securityUnitView.setSonUnitList(sonUnitList);

			securityUnitViewLst.add(securityUnitView);

		}

		return securityUnitViewLst;

	}

}

package com.guo.yf.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.guo.yf.dao.SecurityUnitDao;
import com.guo.yf.model.security.SecurityUnit;
import com.guo.yf.service.SecurityUnitService;
import com.guo.yf.vo.SecurityUnitView;
import com.wondertek.meeting.exception.HibernateDaoSupportException;
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
		this.basicDao= securityUnitDao;
	}
	
	
	public List<SecurityUnitView> getCascadeUnitOfMenu() throws HibernateDaoSupportException {
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
	 * @return
	 * @throws HibernateDaoSupportException
	 */
	public List<SecurityUnitView> getMenuUnitsByRoleId(Integer roleId,
			String meetingId,String ctx) throws HibernateDaoSupportException {
		StringBuilder sb = new StringBuilder();
		// 查出一级根菜单
		sb.append(" SELECT unit FROM SecurityUnit unit,SecurityRoleUnit roleUnit WHERE unit.id=roleUnit.unitId and unit.unitType='0'  ");
		sb.append(" AND roleUnit.roleId=" + roleId);
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

			List<SecurityUnit> sonUnitList = securityUnitDao.getObjects(
					sb.toString(), null);
			for (SecurityUnit unit : sonUnitList) {
				if (StringUtil.isNotEmpty(unit.getUnitUrl())) {
					unit.setUnitUrl(unit.getUnitUrl().replace(
							"${meetingId}", meetingId).replace("${ctx}", ctx));
				}
			}

			securityUnitView.setSonUnitList(sonUnitList);

			securityUnitViewLst.add(securityUnitView);

		}

		return securityUnitViewLst;

	}
	
	
}

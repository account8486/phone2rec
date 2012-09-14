package com.guo.yf.vo;
import java.util.List;

import com.guo.yf.model.security.SecurityUnit;

public class SecurityUnitView {
	private static final long serialVersionUID = -7376301756898837310L;
	private List<SecurityUnit> sonUnitList;
	SecurityUnit securityParentUnit;

	public List<SecurityUnit> getSonUnitList() {
		return sonUnitList;
	}

	public void setSonUnitList(List<SecurityUnit> sonUnitList) {
		this.sonUnitList = sonUnitList;
	}

	public SecurityUnit getSecurityParentUnit() {
		return securityParentUnit;
	}

	public void setSecurityParentUnit(SecurityUnit securityParentUnit) {
		this.securityParentUnit = securityParentUnit;
	}

}

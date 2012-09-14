package com.guo.yf.model.security;

import java.util.Date;

import com.wondertek.meeting.model.BaseObject;

public class SecurityUnit extends BaseObject {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1673702930885017631L;
	private Long id;// 用户id
	private String unitName;
	private String unitDescription;
	private String unitType;
	private String unitUrl;
	private String unitParentId;
	private Date createTime;// 创建时间
	private Long creator;
	private Date modifyTime;// 修改时间
	private Long modifier;
	private Integer orderCode;
	private boolean returnDefaultMenu;
	
	
	public boolean isReturnDefaultMenu() {
		return returnDefaultMenu;
	}
	public void setReturnDefaultMenu(boolean returnDefaultMenu) {
		this.returnDefaultMenu = returnDefaultMenu;
	}
	public String getUnitUrl() {
		return unitUrl;
	}
	public void setUnitUrl(String unitUrl) {
		this.unitUrl = unitUrl;
	}
	public String getUnitParentId() {
		return unitParentId;
	}
	public void setUnitParentId(String unitParentId) {
		this.unitParentId = unitParentId;
	}
	public Integer getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(Integer orderCode) {
		this.orderCode = orderCode;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public String getUnitDescription() {
		return unitDescription;
	}
	public void setUnitDescription(String unitDescription) {
		this.unitDescription = unitDescription;
	}
	public String getUnitType() {
		return unitType;
	}
	public void setUnitType(String unitType) {
		this.unitType = unitType;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Long getCreator() {
		return creator;
	}
	public void setCreator(Long creator) {
		this.creator = creator;
	}
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	public Long getModifier() {
		return modifier;
	}
	public void setModifier(Long modifier) {
		this.modifier = modifier;
	}

	

}

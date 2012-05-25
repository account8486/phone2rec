package com.wondertek.meeting.model;

import java.util.Date;

/**
 * 管理后台菜单项
 * 
 * @author 金祝华
 */
public class AdminMenu extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7705732765630882150L;

	private Long id;

	// 菜单名称
	private String menuName;

	// 菜单Url
	private String menuUrl;

	// 菜单排序
	private Integer menuSort;

	// 菜单等级
	private Integer menuRank;

	// 父菜单id
	private Integer parentId;

	// 父菜单名称
	private String parentName;

	// 备注
	private String comments;

	// 状态 0：无效，1：有效
	private Integer state;

	// 创建时间
	private Date createTime;

	// 修改时间
	private Date modifyTime;

	// 创建人
	private String createId;

	// 创建人姓名
	private String createName;

	// 修改人
	private String modifyId;

	// 修改人姓名
	private String modifyName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuUrl() {
		return menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

	public Integer getMenuSort() {
		return menuSort;
	}

	public void setMenuSort(Integer menuSort) {
		this.menuSort = menuSort;
	}

	public Integer getMenuRank() {
		return menuRank;
	}

	public void setMenuRank(Integer menuRank) {
		this.menuRank = menuRank;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getCreateId() {
		return createId;
	}

	public void setCreateId(String createId) {
		this.createId = createId;
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public String getModifyId() {
		return modifyId;
	}

	public void setModifyId(String modifyId) {
		this.modifyId = modifyId;
	}

	public String getModifyName() {
		return modifyName;
	}

	public void setModifyName(String modifyName) {
		this.modifyName = modifyName;
	}
}

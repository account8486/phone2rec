package com.wondertek.meeting.model;

import java.util.Date;

/**
 * 组织机构
 * 
 * @author 金祝华
 */
public class Organization extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8103878837103734748L;

	private Long id;// id
	private String name;// 名称
	private Long parentId;// 上级机构ID
	private String linker;// 联系人
	private String linkerTel;// 联系人电话
	private Integer level;// 级别（目前只支持4级）
	private Long rootId; // 根机构ID
	private String address;// 地址
	private String comments;// 备注

	private Integer state;// 状态 0：无效，1：有效

	private Date createTime;// 创建时间
	private Date modifyTime;// 修改时间
	
	private Organization parent;// 上级机构ID

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLinker() {
		return linker;
	}

	public void setLinker(String linker) {
		this.linker = linker;
	}

	public String getLinkerTel() {
		return linkerTel;
	}

	public void setLinkerTel(String linkerTel) {
		this.linkerTel = linkerTel;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Long getRootId() {
		return rootId;
	}

	public void setRootId(Long rootId) {
		this.rootId = rootId;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public Organization getParent() {
		return parent;
	}

	public void setParent(Organization parent) {
		this.parent = parent;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
}

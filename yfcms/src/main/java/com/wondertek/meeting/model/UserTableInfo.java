package com.wondertek.meeting.model;

/**已分桌的用户座位信息*/
public class UserTableInfo extends BaseObject{
	private static final long serialVersionUID = -598716169856952929L;
	private Long id; //userId
	private String name; //用户名称
	private String mobile; //手机
	private String tableCode; //分桌号
	private Long dinnerId; //用餐号

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getTableCode() {
		return tableCode;
	}

	public void setTableCode(String tableCode) {
		this.tableCode = tableCode;
	}

	public Long getDinnerId() {
		return dinnerId;
	}

	public void setDinnerId(Long dinnerId) {
		this.dinnerId = dinnerId;
	}

}

package com.wondertek.meeting.model;


/**
 * 导入的分组计划分组信息数据
 * 
 * @author John Tang
 */
public class ImportGroupPlanData extends BaseObject {

	private String mobile;
	private String userName;
	private String groupName;
	private String groupLeader;
	private Integer showIndex;//组的显示顺序
	private String detail;
	private Integer memberSortCode;//组内成员的显示顺序 

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupLeader() {
		return groupLeader;
	}

	public void setGroupLeader(String groupLeader) {
		this.groupLeader = groupLeader;
	}


	public Integer getShowIndex() {
		return showIndex;
	}

	public void setShowIndex(Integer showIndex) {
		this.showIndex = showIndex;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public Integer getMemberSortCode() {
		return memberSortCode;
	}

	public void setMemberSortCode(Integer memberSortCode) {
		this.memberSortCode = memberSortCode;
	}

}

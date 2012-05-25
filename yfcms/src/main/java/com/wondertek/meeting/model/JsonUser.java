package com.wondertek.meeting.model;


/**
 * 用户信息JSON格式的
 * 
 * @author 金祝华
 */
public class JsonUser extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8103878837103734748L;

	private Long id;// 用户id
	private String mobile;// 手机号码即用户登入账号
	private String name;// 用户姓名
	private String isShowMobile; //是否显示手机号 0不显示 1显示
	private String job;// 职位
	private String isShowJob;//是否显示职位 0不显示 1显示
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public String getIsShowJob() {
		return isShowJob;
	}
	public void setIsShowJob(String isShowJob) {
		this.isShowJob = isShowJob;
	}
	public String getIsShowMobile() {
		return isShowMobile;
	}
	public void setIsShowMobile(String isShowMobile) {
		this.isShowMobile = isShowMobile;
	}
	
	
}

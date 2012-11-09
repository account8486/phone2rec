/** 
*  
*/
package com.wondertek.meeting.model;

import java.util.Date;

/** 
* @ClassName: LuckyHistory 
* @Description: 中奖历史信息
* @author zouxiaoming
* @date Mar 8, 2012 1:27:53 PM 
*  
*/
public class LuckyHistory {
	private Long id;
	private AdminUser adminUser;  // 摇奖人
	private User user; // 中奖人
	private LuckyDraw lucky; // 关联的奖项
	private Meeting meeting; // 关联的会议
	private Date createTime =new Date();  // 中奖时间
	
	/**
	 * @Description
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @Description
	 * @return the adminUser
	 */
	public AdminUser getAdminUser() {
		return adminUser;
	}
	/**
	 * @param adminUser
	 */
	public void setAdminUser(AdminUser adminUser) {
		this.adminUser = adminUser;
	}
	/**
	 * @Description
	 * @return the user
	 */
	public User getUser() {
		return user;
	}
	/**
	 * @param user
	 */
	public void setUser(User user) {
		this.user = user;
	}
	/**
	 * @Description
	 * @return the lucky
	 */
	public LuckyDraw getLucky() {
		return lucky;
	}
	/**
	 * @param lucky
	 */
	public void setLucky(LuckyDraw lucky) {
		this.lucky = lucky;
	}
	/**
	 * @Description
	 * @return the meeting
	 */
	public Meeting getMeeting() {
		return meeting;
	}
	/**
	 * @param meeting
	 */
	public void setMeeting(Meeting meeting) {
		this.meeting = meeting;
	}
	/**
	 * @param createTime
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * @Description
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}
	
}

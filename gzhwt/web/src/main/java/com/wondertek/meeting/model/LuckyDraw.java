/** 
*  
*/
package com.wondertek.meeting.model;

import java.util.Date;

/** 
 * @ClassName: LuckyDraw 
 * @Description: 抽奖规则信息
 * @author zouxiaoming
 * @date Mar 8, 2012 9:31:35 AM 
 *  
 */
public class LuckyDraw {
	private Long id;
	private String name;  // 抽奖名称 如一等奖 二等奖
	private String award; // 奖品
	private Date createTime=new Date();  // 创建时间
	private Integer state=1;  // 1:启用  2：不启用
	private Meeting meeting; // 关联的会议
	private Integer checked;  // 标识参与互动交流的人才可以抽奖  1:是  2：否
	
	
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @Description
	 * @return the award
	 */
	public String getAward() {
		return award;
	}
	/**
	 * @param award
	 */
	public void setAward(String award) {
		this.award = award;
	}
	/**
	 * @Description
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * @param createTime
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * @Description
	 * @return the state
	 */
	public Integer getState() {
		return state;
	}
	/**
	 * @param state
	 */
	public void setState(Integer state) {
		this.state = state;
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
	 * @param checked
	 */
	public void setChecked(Integer checked) {
		this.checked = checked;
	}
	/**
	 * @Description
	 * @return the checked
	 */
	public Integer getChecked() {
		return checked;
	}
	
	
	
}

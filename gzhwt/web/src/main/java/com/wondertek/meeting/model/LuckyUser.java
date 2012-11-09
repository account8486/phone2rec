/** 
*  
*/
package com.wondertek.meeting.model;

/** 
 * @ClassName: LuckyUser 
 * @Description: 可以参与抽奖人
 * @author zouxiaoming
 * @date Mar 20, 2012 4:22:30 PM 
 *  
 */
public class LuckyUser {
	private Long id;
	private User user;
	private LuckyDraw lucky;
	private Meeting meeting;
	
	
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
	 * @param meeting
	 */
	public void setMeeting(Meeting meeting) {
		this.meeting = meeting;
	}
	/**
	 * @Description
	 * @return the meeting
	 */
	public Meeting getMeeting() {
		return meeting;
	}
	
	
	
}

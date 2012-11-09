/** 
*  
*/
package com.wondertek.meeting.model;

/** 
 * @ClassName: VoteHistory 
 * @Description: 投票历史记录
 * @author zouxiaoming
 * @date Jan 31, 2012 3:08:42 PM 
 *  
 */
public class VoteHistory {
	private Long id;
	private User user ; // 参与投票的用户
	private VoteBaseInfo voteBase; // 参与的投票
	/**
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @Description
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param user
	 */
	public void setUser(User user) {
		this.user = user;
	}
	/**
	 * @Description
	 * @return the user
	 */
	public User getUser() {
		return user;
	}
	/**
	 * @param voteBase
	 */
	public void setVoteBase(VoteBaseInfo voteBase) {
		this.voteBase = voteBase;
	}
	/**
	 * @Description
	 * @return the voteBase
	 */
	public VoteBaseInfo getVoteBase() {
		return voteBase;
	}

}

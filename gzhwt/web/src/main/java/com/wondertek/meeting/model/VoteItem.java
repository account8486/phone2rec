package com.wondertek.meeting.model;

import java.util.Date;


/** 
 * @ClassName: VoteItem 
 * @Description: 投票选项
 * @author zouxiaoming
 * @date Jan 9, 2012 10:31:15 AM 
 *  
 */
public class VoteItem {
	private Long id;
	private String content;
	private Integer count=0;
	private Date createTime=new Date();
	private VoteBaseInfo vote;
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @Description
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @Description 选项被选择次数
	 * @return the count
	 */
	public Integer getCount() {
		return count;
	}
	/**
	 * @param count the count to set
	 */
	public void setCount(Integer count) {
		this.count = count;
	}
	/**
	 * @Description 一个投票选项对应的投票基本信息
	 * @return the vote
	 */
	public VoteBaseInfo getVote() {
		return vote;
	}
	/**
	 * @param vote the vote to set
	 */
	public void setVote(VoteBaseInfo vote) {
		this.vote = vote;
	}
	/**
	 * @param content
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * @Description  投票选项内容
	 * @return the content
	 */
	public String getContent() {
		return content;
	}
	/**
	 * @param createTime
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * @Description  创建时间
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

}

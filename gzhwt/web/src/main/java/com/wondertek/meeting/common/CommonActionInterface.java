package com.wondertek.meeting.common;

public interface CommonActionInterface {

	/**
	 * 获取列表
	 * 
	 * @return
	 */
	public String getListPager();

	/**
	 * 跳转到新增页面
	 * 
	 * @return
	 */
	public String toAdd();

	/**
	 * 新增操作
	 * 
	 * @return
	 */
	public String add();

	/**
	 * 跳转到更新页面
	 * 
	 * @return
	 */
	public String toUpdate();

	/**
	 * 执行更新操作
	 * 
	 * @return
	 */
	public String update();

	/**
	 * 进行删除操作
	 * 
	 * @return
	 */
	public String delete();

}

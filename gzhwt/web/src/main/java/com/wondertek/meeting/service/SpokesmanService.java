package com.wondertek.meeting.service;

import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.exception.HibernateDaoSupportException;
import com.wondertek.meeting.model.spokesman.Spokesman;

public interface SpokesmanService extends BaseService<Spokesman, Long> {

	/**
	 * 获取列表
	 * @param currentPage
	 * @param pageSize
	 * @param meetingId
	 * @param mobile
	 * @param spokesManname
	 * @return
	 * @throws HibernateDaoSupportException
	 */
	public Pager<Spokesman> getSpokesmanList(int currentPage, int pageSize,
			String meetingId, String mobile, String spokesManname)
			throws HibernateDaoSupportException;

}

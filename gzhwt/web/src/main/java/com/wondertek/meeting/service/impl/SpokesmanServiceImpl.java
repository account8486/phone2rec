package com.wondertek.meeting.service.impl;

import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.dao.SpokesmanDao;
import com.wondertek.meeting.exception.HibernateDaoSupportException;
import com.wondertek.meeting.model.spokesman.Spokesman;
import com.wondertek.meeting.service.SpokesmanService;
import com.wondertek.meeting.util.StringUtil;

/**
 * 发言人
 * 
 * @author GuoXu
 */
public class SpokesmanServiceImpl extends BaseServiceImpl<Spokesman, Long>
		implements SpokesmanService {

	private SpokesmanDao spokesmanDao;

	public SpokesmanDao getSpokesmanDao() {
		return spokesmanDao;
	}

	public void setSpokesmanDao(SpokesmanDao spokesmanDao) {
		this.spokesmanDao = spokesmanDao;
		this.basicDao=spokesmanDao;
	}

	/**
	 * 获取列表
	 * 
	 * @param meetingId
	 * @throws HibernateDaoSupportException
	 */
	public Pager<Spokesman> getSpokesmanList(int currentPage, int pageSize,
			String meetingId, String mobile, String spokesManname)
			throws HibernateDaoSupportException {
		Pager<Spokesman> pager = null;
		StringBuffer hql = new StringBuffer();
		hql.append("  from Spokesman where 1=1 and meetingId=" + meetingId);
		
		//条件查询
		if(StringUtil.isNotEmpty(mobile)){
			hql.append(" and  mobile like '%"+mobile+"%'");
		}
		if(StringUtil.isNotEmpty(spokesManname)){
			hql.append(" and name like '%"+spokesManname+"%'");
		}
		pager = spokesmanDao.findPager(hql.toString(), currentPage, pageSize,
				null);
		
		return pager;
	}

}

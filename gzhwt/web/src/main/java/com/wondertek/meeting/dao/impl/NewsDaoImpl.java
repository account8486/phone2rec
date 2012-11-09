/**
 * 新闻Dao实现类
 * Author: 张国敬
 * Copyrights: 版权归上海网达软件有限公司安徽分公司所有
 * Create at: 2012-3-6
 */
package com.wondertek.meeting.dao.impl;

import java.util.HashMap;
import java.util.Map;

import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.dao.NewsDao;
import com.wondertek.meeting.model.News;

public class NewsDaoImpl extends BaseDaoImpl<News, Integer> implements NewsDao {
	/*
	 * 查看新闻列表
	 * param: 查询条件，可为null，表示不设条件查询全部
	 */
	public Pager<News> findAllNewsPager(Long meetingId, News param, int currentPage, int pageSize) throws Exception {
		StringBuilder hql = new StringBuilder("from News n where n.meeting.id=:meetingId ");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("meetingId", meetingId);
		
		if(param != null && param.getTitle() != null) {
			hql.append(" and n.title like :title");
			map.put("title", "%" + param.getTitle() + "%");
		}
		if(param != null && param.getImageNews() != null) {
			hql.append(" and n.imageNews = :imageNews");
			map.put("imageNews", param.getImageNews());
		}
		if(param != null && param.getState() != null) {
			hql.append(" and n.state = :state");
			map.put("state", param.getState());
		}
		
		hql.append(" order by n.id desc");
		return this.findPager(hql.toString(), currentPage, pageSize, map);
	}
}

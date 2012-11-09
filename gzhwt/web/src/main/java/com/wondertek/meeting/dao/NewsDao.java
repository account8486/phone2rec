/**
 * 新闻Dao
 * Author: 张国敬
 * Copyrights: 版权归上海网达软件有限公司安徽分公司所有
 * Create at: 2012-3-6
 */
package com.wondertek.meeting.dao;

import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.model.News;

public interface NewsDao extends BaseDao<News, Integer> {
	/*
	 * 查看新闻列表
	 * param: 查询条件，可为null，表示不设条件查询全部
	 */
	public Pager<News> findAllNewsPager(Long meetingId, News param, int currentPage, int pageSize) throws Exception;
}

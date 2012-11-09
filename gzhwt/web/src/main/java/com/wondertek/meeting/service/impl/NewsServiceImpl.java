/**
 * 新闻服务实现类
 * Author: 张国敬
 * Copyrights: 版权归上海网达软件有限公司安徽分公司所有
 * Create at: 2012-3-6
 */
package com.wondertek.meeting.service.impl;

import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.dao.NewsDao;
import com.wondertek.meeting.model.News;
import com.wondertek.meeting.service.NewsService;

public class NewsServiceImpl extends BaseServiceImpl<News, Integer>
	implements NewsService {
	
	private NewsDao newsDao;
	
	/*
	 * 查看新闻列表
	 * param: 查询条件，可为null，表示不设条件查询全部
	 */
	public Pager<News> findAllNewsPager(Long meetingId, News param, int currentPage, int pageSize) throws Exception {
		return this.newsDao.findAllNewsPager(meetingId, param, currentPage, pageSize);
	}
	
	public NewsDao getNewsDao() {
		return newsDao;
	}

	public void setNewsDao(NewsDao newsDao) {
		super.setBaseDao(newsDao);
		this.newsDao = newsDao;
	}

}

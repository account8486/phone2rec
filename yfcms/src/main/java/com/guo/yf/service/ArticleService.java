package com.guo.yf.service;

import com.guo.yf.model.article.Article;
import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.service.BaseService;

public interface ArticleService extends BaseService<Article, Long> {

	/**
	 * get article list by pager
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public Pager<Article> getArticlePager(int currentPage, int pageSize,
			String title, String chanId);

}

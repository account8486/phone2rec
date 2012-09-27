package com.guo.yf.service.impl;

import com.guo.yf.dao.ArticleDao;
import com.guo.yf.model.article.Article;
import com.guo.yf.service.ArticleService;
import com.wondertek.meeting.common.Pager;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.service.impl.BaseServiceImpl;

public class ArticleServiceImpl extends BaseServiceImpl<Article, Long> implements ArticleService{
	
	ArticleDao articleDao;

	public ArticleDao getArticleDao() {
		return articleDao;
	}

	public void setArticleDao(ArticleDao articleDao) {
		this.basicDao=articleDao;
		this.articleDao = articleDao;
	}
	
	
	/**
	 * get article list by pager
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public Pager<Article> getArticlePager(int currentPage, int pageSize) {
		Pager<Article> articlePager = null;
		StringBuilder sb = new StringBuilder();
		sb.append("  select arti from Article arti ");

		try {
			articlePager = articleDao.findPager(sb.toString(), currentPage,
					pageSize, null);
		} catch (ServiceException e) {
			e.printStackTrace();
		}

		return articlePager;
	}
	

	
	

}

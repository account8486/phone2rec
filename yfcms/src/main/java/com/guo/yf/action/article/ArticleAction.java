package com.guo.yf.action.article;

import java.util.Date;

import com.guo.yf.model.article.Article;
import com.guo.yf.model.channel.Channel;
import com.guo.yf.service.ArticleService;
import com.guo.yf.service.ChannelService;
import com.wondertek.meeting.action.base.BaseAction;
import com.wondertek.meeting.exception.ServiceException;

public class ArticleAction extends BaseAction {
	
	private static final long serialVersionUID = 8149682758282299164L;
	
	ArticleService articleService;
	ChannelService channelService;
	Article article;
	
	public ChannelService getChannelService() {
		return channelService;
	}

	public void setChannelService(ChannelService channelService) {
		this.channelService = channelService;
	}

	public ArticleService getArticleService() {
		return articleService;
	}

	public void setArticleService(ArticleService articleService) {
		this.articleService = articleService;
	}
	
	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	/**
	 * 文章列表
	 * @return
	 */
	public String articleList() {
		
		this.setAttribute("pager",
				articleService.getArticlePager(currentPage, pageSize));
		return "list";
	}
	
	
	/**
	 * batch delete articles
	 * @return
	 */
	public String batchDelArticles(){
		
		String ids = this.getParameter("ids");
		log.debug("ids:" + ids);
		String[] idsArr = ids.split(",");

		for (int i = 0; i < idsArr.length; i++) {
			Article arti;
			try {
				arti = articleService.findById(Long.valueOf(idsArr[i]));
				articleService.delete(arti);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (ServiceException e) {
				e.printStackTrace();
			}
		}

		
		return this.articleList();
	}
	
	
	/**
	 * 
	 * @return
	 */
	public String toAddArticle() {
		try {
			this.setAttribute("channels", channelService.find(Channel.class));
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		
		return "toAddArti";
	}
	
	/**
	 * to Edit Article
	 * @return
	 */
	public String toEditArticle() {
		try {
			this.setAttribute("article", articleService.findById(Long
					.valueOf(this.getParameter("id"))));
			this.setAttribute("channels", channelService.find(Channel.class));

		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	
		
		return "toEditArti";
	}
	
	public String articlePreview(){
		try {
			this.setAttribute("article", articleService.findById(Long
					.valueOf(this.getParameter("id"))));
			
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		
		return "preview";
		
	}
	
	/**
	 * 
	 * @return
	 */
	public String saveOrUpdateArticle() {
		try {
			if (this.article != null) {
				if (this.article.getId() != null) {
					// 更新数据
					Article oldArti = articleService.findById(this.article
							.getId());
					oldArti.setTitle(article.getTitle());
					oldArti.setSubtitle(article.getSubtitle());
					oldArti.setContent(article.getContent());
					oldArti.setChanId(this.article.getChanId());
					oldArti.setModifyTime(new Date());
					oldArti.setModifier(this.getAdminUserIdFromSession());
					articleService.saveOrUpdate(oldArti);
				} else {
					// 保存数据
					article.setCreateTime(new Date());
					article.setCreator(this.getAdminUserIdFromSession());
					this.articleService.saveOrUpdate(article);
				}
			}
			
			this.resultMap.put("retMsg", "保存成功！");
		} catch (ServiceException e) {
			e.printStackTrace();
			this.resultMap.put("retMsg", e.getMessage());
		}

		return "updatedArticle";
	}
	
}

package com.guo.yf.action.article;

import java.text.ParseException;
import java.util.Date;

import com.guo.yf.model.article.Article;
import com.guo.yf.model.channel.Channel;
import com.guo.yf.service.ArticleService;
import com.guo.yf.service.ChannelService;
import com.wondertek.meeting.action.base.BaseAction;
import com.wondertek.meeting.exception.ServiceException;
import com.wondertek.meeting.util.DateUtil;
import com.wondertek.meeting.util.StringUtil;

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
		String title=StringUtil.trim(this.getParameter("title"));
		String chanId=StringUtil.trim(this.getParameter("chanId"));
		
		try {
			this.setAttribute("channels", channelService.find(Channel.class));
			this.setAttribute("pager",
					articleService.getArticlePager(currentPage, pageSize,title,chanId));
			
			this.setAttribute("title", title);
			this.setAttribute("chanId", chanId);
			
		} catch (ServiceException e) {
			e.printStackTrace();
		}

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
			// 默认赋值一个时间
			this.article = new Article();
		//	article.setPublishTime(DateUtil.convertStringToDate(
		//			"yyyy-MM-dd HH:mm:ss", DateUtil.formatDate(new Date())));
			article.setPublishTime(DateUtil.convertStringToDate(
								"yyyy-MM-dd HH:mm:ss", DateUtil.formatDate(new Date())));

		}  catch (ServiceException e) {
			e.printStackTrace();
		}catch (Exception e) {
			// TODO Auto-generated catch block
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
			
		    Date publishDate = DateUtil.convertStringToDate("yyyy-MM-dd HH:mm:ss", getParameter("article.publishTime")+":00");
		    log.debug("publishDate:"+publishDate);
		    
			if (this.article != null) {
				article.setPublishTime(publishDate);
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
					oldArti.setPublishTime(this.article.getPublishTime());
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
		}catch (ParseException e) {
			e.printStackTrace();
		}

		return "updatedArticle";
	}
	
}

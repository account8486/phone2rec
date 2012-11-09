/**
 * 页面主题信息Service实现类
 * Author: 张国敬
 * Copyrights: 版权归上海网达软件有限公司安徽分公司所有
 * Create at: 2011-12-29
 */
package com.wondertek.meeting.service.impl.custom;

import java.util.List;

import com.wondertek.meeting.dao.custom.PageThemeDao;
import com.wondertek.meeting.model.custom.PageTheme;
import com.wondertek.meeting.service.custom.PageThemeService;
import com.wondertek.meeting.service.impl.BaseServiceImpl;


public class PageThemeServiceImpl extends BaseServiceImpl<PageTheme, Long>
		implements PageThemeService {

	private PageThemeDao pageThemeDao;

	public PageThemeDao getPageThemeDao() {
		return pageThemeDao;
	}

	public void setPageThemeDao(PageThemeDao pageThemeDao) {
		super.setBaseDao(pageThemeDao);
		this.pageThemeDao = pageThemeDao;
	}
	
	/**
	 * 查询所有可用的页面主题
	 * 可用的条件：state==1
	 */
	public List<PageTheme> findAllUsablePageThemes() {
		return this.pageThemeDao.findAllUsablePageThemes();
	}
	
}

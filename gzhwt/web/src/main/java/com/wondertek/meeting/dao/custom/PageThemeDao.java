/**
 * 页面主题DAO
 * Author: 张国敬
 * Copyrights: 版权归上海网达软件有限公司安徽分公司所有
 * Create at: 2011-12-29
 */
package com.wondertek.meeting.dao.custom;

import java.util.List;

import com.wondertek.meeting.dao.BaseDao;
import com.wondertek.meeting.model.custom.PageTheme;

public interface PageThemeDao extends BaseDao<PageTheme, Long> {
	/**
	 * 查询所有可用的页面主题
	 * 可用的条件：state==1
	 */
	public List<PageTheme> findAllUsablePageThemes();
}
